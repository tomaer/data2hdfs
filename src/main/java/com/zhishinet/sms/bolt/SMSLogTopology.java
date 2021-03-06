package com.zhishinet.sms.bolt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.zhishinet.MyConfig;
import com.zhishinet.sms.Field;
import com.zhishinet.sms.UBUserSMSLog;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.rotation.FileRotationPolicy;
import org.apache.storm.hdfs.bolt.rotation.FileSizeRotationPolicy;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class SMSLogTopology {

    public static final String TOPIC = "UBUserSMSLog";
    public static final String SPOUTID = "ubusersmslogstorm";
    public static final String TOPOLOGY_NAME = "SMSLogTopology";
    private final static ObjectMapper mapper = new ObjectMapper();

    public static class SplitDataBolt extends BaseRichBolt {

        private OutputCollector outputCollector;
        private final static Logger logger = LoggerFactory.getLogger(SplitDataBolt.class);

        @Override
        public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
            this.outputCollector = outputCollector;
        }

        @Override
        public void execute(Tuple tuple) {

            final String json = tuple.getString(0);
            UBUserSMSLog log = null;
            try {
                log = mapper.readValue(json,UBUserSMSLog.class);
            } catch (IOException e) {
                logger.error("Convert json to object UBUserSMSLog errror",e);
                this.outputCollector.fail(tuple);
            }
            if(!Objects.isNull(log)) {
                Values values = new Values();

                if(null == log.getId() || log.getId() <= 0) {
                    logger.error("The message from kafka id is inValidate : {}", json);
                    this.outputCollector.fail(tuple);
                }
                values.add(log.getId());

                if(StringUtils.isBlank(log.getKey())) {
                    logger.error("The message from kafka key is inValidate : {}", json);
                    this.outputCollector.fail(tuple);
                }
                values.add(log.getKey());

                if(StringUtils.isBlank(log.getMobilePhoneNo())) {
                    logger.error("The message from kafka mobilePhoneNo is inValidate : {}", json);
                    this.outputCollector.fail(tuple);
                }
                values.add(log.getMobilePhoneNo());

                if(StringUtils.isBlank(log.getCode())) {
                    logger.error("The message from kafka code is inValidate : {}", json);
                    this.outputCollector.fail(tuple);
                }
                values.add(log.getCode());

                if(null == log.getState() || log.getState() <= 0) {
                    logger.error("The message from kafka state is inValidate : {}", json);
                    this.outputCollector.fail(tuple);
                }
                values.add(log.getState());

                values.add(StringUtils.isNotBlank(log.getReturnMsg()) ? log.getReturnMsg() : "\\N");
                values.add(StringUtils.isNotBlank(log.getPostTime()) ? log.getPostTime() : "\\N");
                values.add(StringUtils.isNotBlank(log.getCreatedOn()) ? log.getCreatedOn() : "\\N");
                values.add((!Objects.isNull(log.getCreatedBy())) ? log.getCreatedBy() : "\\N");
//                values.add(null != log.getModifiedOn() ? Utils.formatDate2String(log.getModifiedOn()) : "\\N");
//                values.add((!Objects.isNull(log.getModifiedBy())) ? log.getModifiedBy() : "\\N");
//                values.add(null != log.getDeletedOn() ? Utils.formatDate2String(log.getDeletedOn()) : "\\N");
//                values.add((!Objects.isNull(log.getDeletedBy())) ? log.getDeletedBy() : "\\N");
                values.add(log.isDeleted());
                values.add(StringUtils.isNotBlank(log.getCallbackId()) ? log.getCallbackId() : "\\N");
//                values.add(StringUtils.isNotBlank(log.getOpenId()) ? log.getOpenId(): "\\N");

                this.outputCollector.ack(tuple);
                this.outputCollector.emit(values);

            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(Field.kafkaMessageFields);
        }
    }

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        SpoutConfig spoutConfig = MyConfig.getKafkaSpoutConfig(TOPIC, MyConfig.ZK_HOSTS,MyConfig.ZK_ROOT, SPOUTID);

        RecordFormat format = new DelimitedRecordFormat().withFieldDelimiter(MyConfig.FIELD_DELIMITER);
        SyncPolicy syncPolicy = new CountSyncPolicy(MyConfig.PARTITION_COUNT_SYNC_POLICY);
        FileRotationPolicy rotationPolicy = new FileSizeRotationPolicy(MyConfig.FILE_SIZE, FileSizeRotationPolicy.Units.MB);
        FileNameFormat fileNameFormat = new DefaultFileNameFormat().withPath("/tmp/storm/UserSMSLog/").withExtension(".txt");
        HdfsBolt hdfsBolt = new HdfsBolt().withFsUrl(MyConfig.HDFS_URL).withFileNameFormat(fileNameFormat)
                .withRecordFormat(format).withRotationPolicy(rotationPolicy).withSyncPolicy(syncPolicy);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout",new KafkaSpout(spoutConfig));
        builder.setBolt("splitDataBolt",new SplitDataBolt()).shuffleGrouping("kafkaSpout");
        builder.setBolt("hdfsBolt",hdfsBolt).shuffleGrouping("splitDataBolt");

        Config config = MyConfig.getConfigWithKafkaConsumerProps(false,MyConfig.KAFKA_BROKERS);

        if(null != args && args.length > 0) {
//            config.setNumWorkers(3);
            StormSubmitter.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(TOPOLOGY_NAME,config,builder.createTopology());
        }
    }

}



