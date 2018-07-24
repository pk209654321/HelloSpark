package bean;

/**
 * Created by lenovo on 2018/7/3.
 */
public class SingleUser {
    /*id	bigint
    single_user_id	bigint
    single_user_name	varchar
    single_user_phone	varchar
    single_desire	varchar
    single_region	varchar
    single_employ_time	bigint
    single_click	bigint
    single_reflux_day	bigint
    single_record_date	varchar
    single_buy	varchar
    quantum_reflux_day	bigint
    quantum_buy	bigint
    average_day	bigint
    insert_time	datetime
    last_time	datetime
    course_id	bigint
    page_type	bigint
    ad_id	bigint*/

    private Long id;
    private Long SingleUserID;
    private String SingleUserName;
    private String SingleUserPhone;
    private String SingleDesire;
    private String SingleRegion;
    private Long SingleEmployTime;
    private Long SingleClick;
    private Long SingleRefluxDay;
    private String SingleRecordDate;
    private String SingleBuy;
    private Long QuantumRefluxDay;
    private Long QuantumBuy;
    private Long AverageDay;
    private Long courseId;
    private String insertTime;
    private String lastTime;
    private Long pageType;
    private Long adId;

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Long getPageType() {
        return pageType;
    }

    public void setPageType(Long pageType) {
        this.pageType = pageType;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getQuantumRefluxDay() {
        return QuantumRefluxDay;
    }

    public void setQuantumRefluxDay(Long quantumRefluxDay) {
        QuantumRefluxDay = quantumRefluxDay;
    }

    public Long getQuantumBuy() {
        return QuantumBuy;
    }

    public void setQuantumBuy(Long quantumBuy) {
        QuantumBuy = quantumBuy;
    }

    public Long getAverageDay() {
        return AverageDay;
    }

    public void setAverageDay(Long averageDay) {
        AverageDay = averageDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSingleUserID() {
        return SingleUserID;
    }

    public void setSingleUserID(Long singleUserID) {
        SingleUserID = singleUserID;
    }

    public String getSingleUserName() {
        return SingleUserName;
    }

    public void setSingleUserName(String singleUserName) {
        SingleUserName = singleUserName;
    }

    public String getSingleUserPhone() {
        return SingleUserPhone;
    }

    public void setSingleUserPhone(String singleUserPhone) {
        SingleUserPhone = singleUserPhone;
    }

    public String getSingleDesire() {
        return SingleDesire;
    }

    public void setSingleDesire(String singleDesire) {
        SingleDesire = singleDesire;
    }

    public String getSingleRegion() {
        return SingleRegion;
    }

    public void setSingleRegion(String singleRegion) {
        SingleRegion = singleRegion;
    }

    public Long getSingleRefluxDay() {
        return SingleRefluxDay;
    }

    public void setSingleRefluxDay(Long singleRefluxDay) {
        SingleRefluxDay = singleRefluxDay;
    }

    public String getSingleRecordDate() {
        return SingleRecordDate;
    }

    public void setSingleRecordDate(String singleRecordDate) {
        SingleRecordDate = singleRecordDate;
    }

    public String getSingleBuy() {
        return SingleBuy;
    }

    public void setSingleBuy(String singleBuy) {
        SingleBuy = singleBuy;
    }

    public Long getSingleEmployTime() {
        return SingleEmployTime;
    }

    public void setSingleEmployTime(Long singleEmployTime) {
        SingleEmployTime = singleEmployTime;
    }

    public Long getSingleClick() {
        return SingleClick;
    }

    public void setSingleClick(Long singleClick) {
        SingleClick = singleClick;
    }
}
