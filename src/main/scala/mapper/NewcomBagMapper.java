package mapper;

import bean.newcombag.NewcomBag;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/8/8.
 */
public interface NewcomBagMapper {
    public List<NewcomBag> selectNewcomBagList(Map<String,Object> map);
}
