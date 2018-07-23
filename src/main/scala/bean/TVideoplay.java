package bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/6/28.
 */
public class TVideoplay implements Serializable{
    /*id	bigint
    CourseName	varchar
    TeacherName	varchar
    CourseType	varchar
    CourseGrade	varchar
    PlayDay	int
    CoursePrice	double
    PurchaseNumber	int
    CourseClick	int
    CapitaMin	varchar
    CourseDailyClick	int
    CourseNewUser	varchar
    CourseUser	varchar
    CourseAggregateUser	int
    BroadcastName	varchar
    BroadcastType	varchar
    BroadcastGrade	varchar
    PageType	int*/

    private Long id;
    private String CourseName;
    private String TeacherName;
    private String CourseType;
    private String CourseGrade;
    private Integer  PlayDay;
    private Double CoursePrice;
    private Integer PurchaseNumber;
    private Integer CourseClick;
    private String CapitaMin;
    private Integer CourseDailyClick;
    private String CourseNewUser;
    private String CourseUser;
    private Integer CourseAggregateUser;
    private String BroadcastName;
    private String BroadcastType;
    private String BroadcastGrade;
    private Integer PageType;
    private String InsertTime;
    private Integer CourseId;

    public String getInsertTime() {
        return InsertTime;
    }

    public void setInsertTime(String insertTime) {
        InsertTime = insertTime;
    }

    public Integer getCourseId() {
        return CourseId;
    }

    public void setCourseId(Integer courseId) {
        CourseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getCourseType() {
        return CourseType;
    }

    public void setCourseType(String courseType) {
        CourseType = courseType;
    }

    public String getCourseGrade() {
        return CourseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        CourseGrade = courseGrade;
    }

    public Integer getPlayDay() {
        return PlayDay;
    }

    public void setPlayDay(Integer playDay) {
        PlayDay = playDay;
    }

    public Double getCoursePrice() {
        return CoursePrice;
    }

    public void setCoursePrice(Double coursePrice) {
        CoursePrice = coursePrice;
    }

    public Integer getPurchaseNumber() {
        return PurchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        PurchaseNumber = purchaseNumber;
    }

    public Integer getCourseClick() {
        return CourseClick;
    }

    public void setCourseClick(Integer courseClick) {
        CourseClick = courseClick;
    }

    public String getCapitaMin() {
        return CapitaMin;
    }

    public void setCapitaMin(String capitaMin) {
        CapitaMin = capitaMin;
    }

    public Integer getCourseDailyClick() {
        return CourseDailyClick;
    }

    public void setCourseDailyClick(Integer courseDailyClick) {
        CourseDailyClick = courseDailyClick;
    }

    public String getCourseNewUser() {
        return CourseNewUser;
    }

    public void setCourseNewUser(String courseNewUser) {
        CourseNewUser = courseNewUser;
    }

    public String getCourseUser() {
        return CourseUser;
    }

    public void setCourseUser(String courseUser) {
        CourseUser = courseUser;
    }

    public Integer getCourseAggregateUser() {
        return CourseAggregateUser;
    }

    public void setCourseAggregateUser(Integer courseAggregateUser) {
        CourseAggregateUser = courseAggregateUser;
    }

    public String getBroadcastName() {
        return BroadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        BroadcastName = broadcastName;
    }

    public String getBroadcastType() {
        return BroadcastType;
    }

    public void setBroadcastType(String broadcastType) {
        BroadcastType = broadcastType;
    }

    public String getBroadcastGrade() {
        return BroadcastGrade;
    }

    public void setBroadcastGrade(String broadcastGrade) {
        BroadcastGrade = broadcastGrade;
    }

    public Integer getPageType() {
        return PageType;
    }

    public void setPageType(Integer pageType) {
        PageType = pageType;
    }

    public static void main(String[] args) {
        System.out.println("aaaaaaaa");
    }
}
