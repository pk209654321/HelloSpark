package bean.login;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/9/4.
 */
@Alias("LoginData")
public class LoginData implements Serializable{
    private Long id;
    private Integer user_id;
    private String mobile;
    private String start_time;
    private String end_time;

    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
