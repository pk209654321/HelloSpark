package bean;

/**
 * Created by lenovo on 2018/8/2.
 */
public class ScreeningAdInfo {
    /*id	int
    user_id	int
    ad_id	int
    ad_click_count	int
    ad_stay_time	bigint
    create_time	datetime
    backup	varchar
    page_type	int*/

    private Integer id;
    private Integer userId;
    private Integer adId;
    private Integer adClickCount;
    private Long adStayTime;
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

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Integer getAdClickCount() {
        return adClickCount;
    }

    public void setAdClickCount(Integer adClickCount) {
        this.adClickCount = adClickCount;
    }

    public Long getAdStayTime() {
        return adStayTime;
    }

    public void setAdStayTime(Long adStayTime) {
        this.adStayTime = adStayTime;
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
