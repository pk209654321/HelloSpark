package mapper;

import bean.AccountDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/9/10.
 */
public interface AccountDetailMapper extends Mapper{
    public List<AccountDetail> selectAccountDetailList(Map<String,Object> map);

    public List<AccountDetail> selectSduaList(Map<String,Object> map);
}
