package mapper;

import bean.login.LoginData;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/9/4.
 */
public interface LoginDataMapper {
    public List<LoginData> selectLoginDataList(Map<String,Object> map);
}
