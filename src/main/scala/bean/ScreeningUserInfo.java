package bean;

/**
 * Created by lenovo on 2018/8/2.
 */
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
