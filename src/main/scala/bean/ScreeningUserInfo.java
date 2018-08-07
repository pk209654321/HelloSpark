package bean;

import org.apache.ibatis.type.Alias;

/**
 * Created by lenovo on 2018/8/2.
 */

@Alias("ScreeningUserInfo")
public class ScreeningUserInfo {
   /* id	int
    user_id	int
    operation_count	int
    user_online_time	bigint
    create_time	datetime
    backup	varchar*/

    private Integer id;
    private Integer userId;
    private Integer operationCount;
    private Long userOnlineTime;
    private String createTime;
    private String backup;
    private Integer pageType;
    private Integer dayFlag;//天数标志;

    public Integer getDayFlag() {
        return dayFlag;
    }

    public void setDayFlag(Integer dayFlag) {
        this.dayFlag = dayFlag;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
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

    public Integer getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(Integer operationCount) {
        this.operationCount = operationCount;
    }

    public Long getUserOnlineTime() {
        return userOnlineTime;
    }

    public void setUserOnlineTime(Long userOnlineTime) {
        this.userOnlineTime = userOnlineTime;
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
}
