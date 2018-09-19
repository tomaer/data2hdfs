package com.hand.zhishinet.assessment.vo;
/**
 * Created by jianpeng.liu@hand-china.com on 2017/5/16.
 */


import java.util.Date;

/**
 * @author jianpeng.liu@hand-china.com	2018/9/18 16:17
 * @description
 */


public class UBHomeworkSessionUserTracking extends BaseDTO {

    private Long homeworkSessionUserTrackingId;

    private Integer sessionId;

    private Long homeworkAssessmentId;

    private Integer userId;

    private Integer	noOfVisits;

    private Date lastViewedOn;

    private Integer statusId;

    private Date completedOn;

    private Float score;

    private Float percentScore;

    private Integer completeAttempts;

    private Date beginDate;

    private Date endDate;

    private String timeSpent;

    private String interactionTimer;

    private Integer articleLocation;

    private String location;

    private Boolean isChecked;

    private Short forLearnerStatus;

    private String questionIndexs;

    private String emendStatus;

    private Boolean IsRequiredEmend;

    private Integer subjectId;

    private Integer readCount;

    private Boolean showSubTitle;

    private String emendTypeCode;

    private Integer sessionGroupId;

    private Integer displayOrder;

    public UBHomeworkSessionUserTracking() {
    }

    public Long getHomeworkSessionUserTrackingId() {
        return homeworkSessionUserTrackingId;
    }

    public void setHomeworkSessionUserTrackingId(Long homeworkSessionUserTrackingId) {
        this.homeworkSessionUserTrackingId = homeworkSessionUserTrackingId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Long getHomeworkAssessmentId() {
        return homeworkAssessmentId;
    }

    public void setHomeworkAssessmentId(Long homeworkAssessmentId) {
        this.homeworkAssessmentId = homeworkAssessmentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNoOfVisits() {
        return noOfVisits;
    }

    public void setNoOfVisits(Integer noOfVisits) {
        this.noOfVisits = noOfVisits;
    }

    public Date getLastViewedOn() {
        return lastViewedOn;
    }

    public void setLastViewedOn(Date lastViewedOn) {
        this.lastViewedOn = lastViewedOn;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getPercentScore() {
        return percentScore;
    }

    public void setPercentScore(Float percentScore) {
        this.percentScore = percentScore;
    }

    public Integer getCompleteAttempts() {
        return completeAttempts;
    }

    public void setCompleteAttempts(Integer completeAttempts) {
        this.completeAttempts = completeAttempts;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getInteractionTimer() {
        return interactionTimer;
    }

    public void setInteractionTimer(String interactionTimer) {
        this.interactionTimer = interactionTimer;
    }

    public Integer getArticleLocation() {
        return articleLocation;
    }

    public void setArticleLocation(Integer articleLocation) {
        this.articleLocation = articleLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Short getForLearnerStatus() {
        return forLearnerStatus;
    }

    public void setForLearnerStatus(Short forLearnerStatus) {
        this.forLearnerStatus = forLearnerStatus;
    }

    public String getQuestionIndexs() {
        return questionIndexs;
    }

    public void setQuestionIndexs(String questionIndexs) {
        this.questionIndexs = questionIndexs;
    }

    public String getEmendStatus() {
        return emendStatus;
    }

    public void setEmendStatus(String emendStatus) {
        this.emendStatus = emendStatus;
    }

    public Boolean getRequiredEmend() {
        return IsRequiredEmend;
    }

    public void setRequiredEmend(Boolean requiredEmend) {
        IsRequiredEmend = requiredEmend;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Boolean getShowSubTitle() {
        return showSubTitle;
    }

    public void setShowSubTitle(Boolean showSubTitle) {
        this.showSubTitle = showSubTitle;
    }

    public String getEmendTypeCode() {
        return emendTypeCode;
    }

    public void setEmendTypeCode(String emendTypeCode) {
        this.emendTypeCode = emendTypeCode;
    }

    public Integer getSessionGroupId() {
        return sessionGroupId;
    }

    public void setSessionGroupId(Integer sessionGroupId) {
        this.sessionGroupId = sessionGroupId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}