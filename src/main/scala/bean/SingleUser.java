package bean;

/**
 * Created by lenovo on 2018/7/3.
 */
public class SingleUser {
 /*   id	bigint
    SingleUserID	bigint
    SingleUserName	varchar
    SingleUserPhone	varchar
    SingleDesire	varchar
    SingleRegion	varchar
    SingleEmployTime	bigint
    SingleClick	bigint
    SingleRefluxDay	bigint
    SingleRecordDate	varchar
    SingleBuy	varchar
    QuantumRefluxDay	bigint
QuantumBuy	bigint
AverageDay	bigint
*/
    private Long id;
    private Long SingleUserID;
    private String SingleUserName;
    private String SingleUserPhone;
    private String SingleDesire;
    private String SingleRegion;
    private String SingleEmployTime;
    private String SingleClick;
    private Long SingleRefluxDay;
    private String SingleRecordDate;
    private String SingleBuy;
    private Long QuantumRefluxDay;
    private Long QuantumBuy;
    private Long AverageDay;
    private Long courseId;

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

    public String getSingleEmployTime() {
        return SingleEmployTime;
    }

    public void setSingleEmployTime(String singleEmployTime) {
        SingleEmployTime = singleEmployTime;
    }

    public String getSingleClick() {
        return SingleClick;
    }

    public void setSingleClick(String singleClick) {
        SingleClick = singleClick;
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
}
