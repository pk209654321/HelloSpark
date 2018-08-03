package bean;

/**
 * Created by lenovo on 2018/8/2.
 */
public class ScreeningCourseInfo {
    /*id	int
    user_id	int
    course_id	int
    course_click_count	int
    course_stay_time	bigint
    create_time	datetime
    backup	varchar
    page_type	int*/

    private Integer id;
    private Integer userId;
    private Integer courseId;
    private Integer courseClickCount;
    private Long courseStayTime;
    private String createTime;
    private String backup;
    private Integer pageType;

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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseClickCount() {
        return courseClickCount;
    }

    public void setCourseClickCount(Integer courseClickCount) {
        this.courseClickCount = courseClickCount;
    }

    public Long getCourseStayTime() {
        return courseStayTime;
    }

    public void setCourseStayTime(Long courseStayTime) {
        this.courseStayTime = courseStayTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }
}
