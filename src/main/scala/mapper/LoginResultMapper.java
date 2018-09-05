package mapper;

import bean.login.LoginResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2018/9/4.
 */
public interface LoginResultMapper extends Serializable{
    public int insertLoginResult(List<LoginResult> loginResultList);
}
