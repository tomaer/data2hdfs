package com.hand.zhishinet.assessment.vo;

import java.util.Date;

/**
 * @author zong.liu01@hand-china.com  2018/9/18 17:56
 * @version 1.0
 * @name zhishinet-bigData
 * @description
 */
public class UBHomeworkAssessment extends BaseDTO{

    private Long homeworkAssessmentId;
    private Integer tenantId;
    private Boolean isTimerOn;
    private Short timerMode;
    private Integer minimumPassPercentage;
    private Integer assessmentQuestions;
    private Date beginDate;
    private Date endDate;
    private Integer assessmentBuilderId;
    private Integer templateType;
    private Boolean isOptionRandom;
    private Integer assessmentClassification;
    private Integer duration;
    private Boolean allowBack;
    private Boolean allowSkip;
    private Boolean disableFeedback;
    private Integer assessmentBuilderType;
    private Integer subjectId;
    private Boolean isOral;
    private Boolean showSubTitle;
    private Integer displayOrder;
    private String homeworkAssessmentJson;

    public Long getHomeworkAssessmentId() {
        return homeworkAssessmentId;
    }

    public void setHomeworkAssessmentId(Long homeworkAssessmentId) {
        this.homeworkAssessmentId = homeworkAssessmentId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getTimerOn() {
        return isTimerOn;
    }

    public void setTimerOn(Boolean timerOn) {
        isTimerOn = timerOn;
    }

    public Short getTimerMode() {
        return timerMode;
    }

    public void setTimerMode(Short timerMode) {
        this.timerMode = timerMode;
    }

    public Integer getMinimumPassPercentage() {
        return minimumPassPercentage;
    }

    public void setMinimumPassPercentage(Integer minimumPassPercentage) {
        this.minimumPassPercentage = minimumPassPercentage;
    }

    public Integer getAssessmentQuestions() {
        return assessmentQuestions;
    }

    public void setAssessmentQuestions(Integer assessmentQuestions) {
        this.assessmentQuestions = assessmentQuestions;
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

    public Integer getAssessmentBuilderId() {
        return assessmentBuilderId;
    }

    public void setAssessmentBuilderId(Integer assessmentBuilderId) {
        this.assessmentBuilderId = assessmentBuilderId;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Boolean getOptionRandom() {
        return isOptionRandom;
    }

    public void setOptionRandom(Boolean optionRandom) {
        isOptionRandom = optionRandom;
    }

    public Integer getAssessmentClassification() {
        return assessmentClassification;
    }

    public void setAssessmentClassification(Integer assessmentClassification) {
        this.assessmentClassification = assessmentClassification;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getAllowBack() {
        return allowBack;
    }

    public void setAllowBack(Boolean allowBack) {
        this.allowBack = allowBack;
    }

    public Boolean getAllowSkip() {
        return allowSkip;
    }

    public void setAllowSkip(Boolean allowSkip) {
        this.allowSkip = allowSkip;
    }

    public Boolean getDisableFeedback() {
        return disableFeedback;
    }

    public void setDisableFeedback(Boolean disableFeedback) {
        this.disableFeedback = disableFeedback;
    }

    public Integer getAssessmentBuilderType() {
        return assessmentBuilderType;
    }

    public void setAssessmentBuilderType(Integer assessmentBuilderType) {
        this.assessmentBuilderType = assessmentBuilderType;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getOral() {
        return isOral;
    }

    public void setOral(Boolean oral) {
        isOral = oral;
    }

    public Boolean getShowSubTitle() {
        return showSubTitle;
    }

    public void setShowSubTitle(Boolean showSubTitle) {
        this.showSubTitle = showSubTitle;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getHomeworkAssessmentJson() {
        return homeworkAssessmentJson;
    }

    public void setHomeworkAssessmentJson(String homeworkAssessmentJson) {
        this.homeworkAssessmentJson = homeworkAssessmentJson;
    }
}
