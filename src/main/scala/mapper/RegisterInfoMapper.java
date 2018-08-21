package mapper;

import bean.RegisterInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/8/21.
 */
public interface RegisterInfoMapper {
    public List<RegisterInfo> selectRegisterInfoList(Map<String,Object> map);
}
