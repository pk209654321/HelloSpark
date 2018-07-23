package bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/7/9.
 */
public class AiUserActionInfo implements Serializable{
    /*id	bigint
    user_id	varchar
    plate_id	varchar
    page_id	varchar
    page_url	varchar
    action	varchar
    action_content	varchar
    create_time	timestamp
    enter_time	datetime
    leave_time	datetime
    af_first	varchar
    af_second	varchar*/
    private Long id;
    private String userId;
    private String plateId;
    private String pageId;
    private String pageUrl;
    private String action;
    private String actionContent;
    private String createTime;
    private String enterTime;
    private String leaveTime;
    private String afFirst;
    private String afSecond;
    private String operatType;

    public String getOperatType() {
        return operatType;
    }

    public void setOperatType(String operatType) {
        this.operatType = operatType;
    }

    public AiUserActionInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlateId() {
        return plateId;
    }

    public void setPlateId(String plateId) {
        this.plateId = plateId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getAfFirst() {
        return afFirst;
    }

    public void setAfFirst(String afFirst) {
        this.afFirst = afFirst;
    }

    public String getAfSecond() {
        return afSecond;
    }

    public void setAfSecond(String afSecond) {
        this.afSecond = afSecond;
    }
}
