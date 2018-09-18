package mapper;

import bean.collect.TotalUserActionInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/9/5.
 */
public interface TotalUserActionInfoMapper {
    public List<TotalUserActionInfo> selectTotalUserActionInfoList(Map<String,Object> map);
}
