package bean.share;

/**
 * Created by lenovo on 2018/9/18.
 */
public class AiShareResult {
    /*id	bigint
    app_channel	varchar
    recommend_id	int
    share_scene	int
    share_creative	varchar
    share_channel	int
    superior_name	varchar
    up_uv	int
    up_pv	int
    first_persion	int
    read_uv	int
    read_pv	int
    return_user	int
    new_register_user	int
    bak	varchar
    insert_time	datetime*/
    private Long id;
    private String app_channel;
    private Integer recommend_id;
    private Integer share_scene;
    private String share_creative;
    private Integer share_channel;
    private String superior_name;
    private Integer up_uv;
    private Integer up_pv;
    private Integer first_persion;
    private Integer read_uv;
    private Integer read_pv;
    private Integer return_user;
    private Integer new_register_user;
    private String bak;
    private String insert_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp_channel() {
        return app_channel;
    }

    public void setApp_channel(String app_channel) {
        this.app_channel = app_channel;
    }

    public Integer getRecommend_id() {
        return recommend_id;
    }

    public void setRecommend_id(Integer recommend_id) {
        this.recommend_id = recommend_id;
    }

    public Integer getShare_scene() {
        return share_scene;
    }

    public void setShare_scene(Integer share_scene) {
        this.share_scene = share_scene;
    }

    public String getShare_creative() {
        return share_creative;
    }

    public void setShare_creative(String share_creative) {
        this.share_creative = share_creative;
    }

    public Integer getShare_channel() {
        return share_channel;
    }

    public void setShare_channel(Integer share_channel) {
        this.share_channel = share_channel;
    }

    public String getSuperior_name() {
        return superior_name;
    }

    public void setSuperior_name(String superior_name) {
        this.superior_name = superior_name;
    }

    public Integer getUp_uv() {
        return up_uv;
    }

    public void setUp_uv(Integer up_uv) {
        this.up_uv = up_uv;
    }

    public Integer getUp_pv() {
        return up_pv;
    }

    public void setUp_pv(Integer up_pv) {
        this.up_pv = up_pv;
    }

    public Integer getFirst_persion() {
        return first_persion;
    }

    public void setFirst_persion(Integer first_persion) {
        this.first_persion = first_persion;
    }

    public Integer getRead_uv() {
        return read_uv;
    }

    public void setRead_uv(Integer read_uv) {
        this.read_uv = read_uv;
    }

    public Integer getRead_pv() {
        return read_pv;
    }

    public void setRead_pv(Integer read_pv) {
        this.read_pv = read_pv;
    }

    public Integer getReturn_user() {
        return return_user;
    }

    public void setReturn_user(Integer return_user) {
        this.return_user = return_user;
    }

    public Integer getNew_register_user() {
        return new_register_user;
    }

    public void setNew_register_user(Integer new_register_user) {
        this.new_register_user = new_register_user;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }
}
