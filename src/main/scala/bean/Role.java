package bean;

import org.apache.ibatis.type.Alias;

/**
 * Created by lenovo on 2018/8/1.
 */
@Alias("Role")
public class Role {
    private Long id;
    private String roleName;
    private String note;


    public Role() {
        super();
    }
    public Role(String roleName, String note) {
        super();
        this.roleName = roleName;
        this.note = note;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
