package bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/7/20.
 */
public class ActivitiesOperation implements Serializable{
    /*id	int
    user_id	int
    mark_user	int
    device	varchar
    page_code	varchar
    product_code	varchar
    event_id	varchar
    phone	varchar
    timestamp	timestamp*/
    private Integer id;
    private Integer userId;
    private Integer markUser;
    private String device;
    private String pageCode;
    private String produceCode;
    private String eventId;
    private String phone;
    private String timestamp;

    public Integer getMarkUser() {
        return markUser;
    }

    public void setMarkUser(Integer markUser) {
        this.markUser = markUser;
    }

    public ActivitiesOperation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
