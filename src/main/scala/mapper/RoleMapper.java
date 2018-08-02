package mapper;

import bean.Role;

/**
 * Created by lenovo on 2018/8/1.
 */
public interface RoleMapper {
    public Role getRole(Long id);
    public int insertRole(Role role);
    public int deleteRole(Long id);
}
