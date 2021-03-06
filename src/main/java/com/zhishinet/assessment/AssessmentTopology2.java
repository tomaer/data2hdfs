package com.zhishinet.assessment;


import com.zhishinet.assessment.baseStateUpdater.CustomMongoStateUpdater;
import com.zhishinet.assessment.state.CustomMongoState;
import com.zhishinet.assessment.stateFactory.CustomMongoStateFactory;
import com.zhishinet.homeworkcenter.processdata.PreProcessLauch2Tracking;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.hbase.trident.mapper.SimpleTridentHBaseMapMapper;
import org.apache.storm.hbase.trident.state.HBaseMapState;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.trident.TransactionalTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.mongodb.common.mapper.MongoMapper;
import org.apache.storm.mongodb.common.mapper.SimpleMongoMapper;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * tomaer
 */
public class AssessmentTopology2 {

    private static Logger logger = LoggerFactory.getLogger(AssessmentTopology2.class);


    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        BrokerHosts boBrokerHosts = new ZkHosts(Conf.ZOOKEEPER_LIST);
        final String spoutId = "HomeworkCenter_storm";
        TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(boBrokerHosts, Conf.TOPIC_HOMEWORKCENTER, spoutId);
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        // Mongo落盘方式
        String url = "mongodb://dev:dev@10.213.0.42:37017/dev";
        String collection = "HomeworkAssessmentAllInfo";
        MongoMapper mapper = new SimpleMongoMapper()
                .withFields(Field.ASSESSMENTID, Field.SESSIONID, Field.SUM, Field.COUNT);
        CustomMongoState.Options options = new CustomMongoState.Options()
                .withUrl(url)
                .withCollectionName(collection)
                .withMapper(mapper);
        StateFactory factory = new CustomMongoStateFactory(options);


        //HBase存储中间结果
        //HBaseState使用HBaseMapState
        HBaseMapState.Options sumOptions = new HBaseMapState.Options();
        sumOptions.tableName = "Assessment";
        sumOptions.columnFamily = "CompletePercent";
        sumOptions.mapMapper = new SimpleTridentHBaseMapMapper("sum");

        HBaseMapState.Options countOptions = new HBaseMapState.Options();
        countOptions.tableName = "Assessment";
        countOptions.columnFamily = "CompletePercent";
        countOptions.mapMapper = new SimpleTridentHBaseMapMapper("count");


        //0. 声明拓扑
        TridentTopology topology = new TridentTopology();

        //1. 流计算
        //1.1 整理出流中需要的信息
        Stream stream = topology.newStream("HomeworkCenter_topology", new TransactionalTridentKafkaSpout(kafkaConfig));


        Stream stream1 = stream
                // 先把Kafka中的数据解析成对象,包括 Field.SESSIONUSERTRACKINGID,Field.SUBJECT_ID, Field.ASSESSMENTID, Field.SESSIONID, Field.SCORE, Field.USERID 字段
                .each(new Fields("str"), new PreProcessLauch2Tracking(), new Fields(Field.SESSIONUSERTRACKINGID, Field.SUBJECT_ID, Field.ASSESSMENTID, Field.SESSIONID, Field.SCORE, Field.USERID));

        //1.2 分流计算Sum和Count , 最后join到一起.  并持久化到mongo中.
        logger.info("===============定义 分流到两个stream =============");
        TridentState tridentState = topology.join(
                // TODO: 中间结果持久化到HBase
                //算Count
                stream1.groupBy(new Fields(Field.ASSESSMENTID, Field.SESSIONID))
                        .persistentAggregate(HBaseMapState.transactional(countOptions), new Fields(Field.ASSESSMENTID, Field.SESSIONID), new Count(), new Fields(Field.COUNT))
                        .newValuesStream(),
                new Fields(Field.ASSESSMENTID, Field.SESSIONID),

                //算Sum
                stream1.groupBy(new Fields(Field.ASSESSMENTID, Field.SESSIONID))
                        .persistentAggregate(HBaseMapState.transactional(sumOptions), new Fields(Field.SCORE), new Sum(), new Fields(Field.SUM))
                        .newValuesStream(),
                new Fields(Field.ASSESSMENTID, Field.SESSIONID),

                //聚合
                new Fields(Field.ASSESSMENTID, Field.SESSIONID, Field.SUM, Field.COUNT)

        ).partitionPersist(
                factory,
                new Fields(Field.ASSESSMENTID, Field.SESSIONID, Field.SUM, Field.COUNT),
                new CustomMongoStateUpdater(),
                new Fields()
        );


        logger.info("===============提交topology =============");

        Config config = new Config();
        Config conf = new Config();
        conf.setDebug(true);
        Properties props = new Properties();
        props.put("producer.type", "async");
        props.put("linger.ms", "1500");
        props.put("batch.size", "16384");
        props.put("request.required.acks", "1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        conf.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE, 32);
        conf.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 16384);
        conf.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE, 16384);
        conf.put("kafka.broker.properties", props);



        if(null != args && args.length > 0) {
            StormSubmitter.submitTopology("TridentTopology", config, topology.build());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("TridentTopology",config,topology.build());
        }

    }
}
