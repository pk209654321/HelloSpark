package bean.share;

/**
 * Created by lenovo on 2018/9/18.
 */
public class ShareBusiness {
    /*分享场景(根据所定的枚举进行传值)	int	share_scene
    分享文案	varchar	share_creative
    分享渠道(根据所定的枚举进行传值)	int	share_channel
    上级页面id(分享文案的上一级入口id)	int	supreior_page_id
    用户来源(站内/站外)	int	user_source
    上级入口名称	varchar	superior_name
    分享后阅读(站内老用户传user_id,其他传浏览器唯一id)	varchar	read_user_id
    是否是第一人称(0:是1:否)	int	first_persion*/

    private Integer share_scene;//分享场景
    private String share_creative;//分享文案
    private Integer share_channel;//分享渠道
    private Integer supreior_page_id;//上一级页面id
    private Integer user_source;//用户来源(站内/站外)
    private String superior_name;//上一级入口名称
    private String read_user_id;//分享后阅读(站内老用户传user_id,其他传浏览器唯一id)
    private Integer first_persion;//是否是第一人称(0:是1:否)

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

    public Integer getSupreior_page_id() {
        return supreior_page_id;
    }

    public void setSupreior_page_id(Integer supreior_page_id) {
        this.supreior_page_id = supreior_page_id;
    }

    public Integer getUser_source() {
        return user_source;
    }

    public void setUser_source(Integer user_source) {
        this.user_source = user_source;
    }

    public String getSuperior_name() {
        return superior_name;
    }

    public void setSuperior_name(String superior_name) {
        this.superior_name = superior_name;
    }

    public String getRead_user_id() {
        return read_user_id;
    }

    public void setRead_user_id(String read_user_id) {
        this.read_user_id = read_user_id;
    }

    public Integer getFirst_persion() {
        return first_persion;
    }

    public void setFirst_persion(Integer first_persion) {
        this.first_persion = first_persion;
    }
}
