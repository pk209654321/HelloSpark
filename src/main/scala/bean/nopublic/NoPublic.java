package bean.nopublic;/**
 * Created by lenovo on 2018/9/20.
 */

import org.apache.ibatis.type.Alias;

/**
 * @ClassName NoPublic
 * @Description TODO
 * @Author lenovo
 * @Date 2018/9/2017:48
 **/

@Alias("NoPublic")
public class NoPublic {
   /* id	bigint
    ophone	varchar
    nick_name	varchar
    wx_name	varchar
    live_browse	int
    special_read	int
    source_user	int
    teacher_id	int
    live_day	int
    stay_time	int
    column_name	int
    read_day	int
    read_time	int
    add_browse	int
    create_time	datetime*/

    private Long id;
    private String phone;
    private String nick_name;
    private String wx_name;
    private Integer live_browse;
    private Integer special_read;
    private Integer source_user;
    private Integer teacher_id;
    private Integer live_day;
    private Integer stay_time;
    private Integer column_name;
    private Integer read_day;
    private Integer read_time;
    private Integer add_browse;
    private String create_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getWx_name() {
        return wx_name;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }

    public Integer getLive_browse() {
        return live_browse;
    }

    public void setLive_browse(Integer live_browse) {
        this.live_browse = live_browse;
    }

    public Integer getSpecial_read() {
        return special_read;
    }

    public void setSpecial_read(Integer special_read) {
        this.special_read = special_read;
    }

    public Integer getSource_user() {
        return source_user;
    }

    public void setSource_user(Integer source_user) {
        this.source_user = source_user;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Integer getLive_day() {
        return live_day;
    }

    public void setLive_day(Integer live_day) {
        this.live_day = live_day;
    }

    public Integer getStay_time() {
        return stay_time;
    }

    public void setStay_time(Integer stay_time) {
        this.stay_time = stay_time;
    }

    public Integer getColumn_name() {
        return column_name;
    }

    public void setColumn_name(Integer column_name) {
        this.column_name = column_name;
    }

    public Integer getRead_day() {
        return read_day;
    }

    public void setRead_day(Integer read_day) {
        this.read_day = read_day;
    }

    public Integer getRead_time() {
        return read_time;
    }

    public void setRead_time(Integer read_time) {
        this.read_time = read_time;
    }

    public Integer getAdd_browse() {
        return add_browse;
    }

    public void setAdd_browse(Integer add_browse) {
        this.add_browse = add_browse;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
