package bean.newcombag;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by lenovo on 2018/8/8.
 */
@Alias("NewcomBag")
public class NewcomBag implements Serializable{
   /* id	int
    user_id	int
    user_type	int
    user_source	int
    out_source	int
    page_id	int
    banner_id	int
    share	int
    event_id	int
    event_name	varchar
    buried_time	datetime
    start_time	datetime
    end_time	datetime
    register	int*/

    private Integer id;
    private String userId;
    private String userType;
    private String userSource;
    private String source;
    private String pageId;
    private String bannerId;
    private String share;
    private String eventId;
    private String eventName;
    private String buriedTime;
    private String startTime;
    private String endTime;
    private String device;
    private String phone;
    private String activityName;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getBuriedTime() {
        return buriedTime;
    }

    public void setBuriedTime(String buriedTime) {
        this.buriedTime = buriedTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
