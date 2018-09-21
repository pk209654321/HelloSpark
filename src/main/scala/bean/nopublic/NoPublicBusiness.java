package bean.nopublic;/**
 * Created by lenovo on 2018/9/20.
 */


/**
 * @ClassName NoPublicBusiness
 * @Description TODO
 * @Author lenovo
 * @Date 2018/9/20 20:27
 **/
public class NoPublicBusiness {
   /* int	couser_id
    int	catalog_id
    varchar	business_name
    varchar	phone_user
    varchar	name_user
    varchar	wechat_name
    varchar	live_name
    datetime	start_time
    datetime	end_time*/
    private Integer teacher_id;
    private Integer couser_id;
    private Integer catalog_id;
    private String business_name;
    private String phone_user;
    private String name_user;
    private String wechar_name;
    private String live_name;
    private Integer watch_time;

    public Integer getWatch_time() {
        return watch_time;
    }

    public void setWatch_time(Integer watch_time) {
        this.watch_time = watch_time;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Integer getCouser_id() {
        return couser_id;
    }

    public void setCouser_id(Integer couser_id) {
        this.couser_id = couser_id;
    }

    public Integer getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(Integer catalog_id) {
        this.catalog_id = catalog_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getPhone_user() {
        return phone_user;
    }

    public void setPhone_user(String phone_user) {
        this.phone_user = phone_user;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getWechar_name() {
        return wechar_name;
    }

    public void setWechar_name(String wechar_name) {
        this.wechar_name = wechar_name;
    }

    public String getLive_name() {
        return live_name;
    }

    public void setLive_name(String live_name) {
        this.live_name = live_name;
    }
}
