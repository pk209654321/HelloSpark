package dao;

import bean.AiAccountResigterInfo;

import java.util.List;

/**
 * Created by lenovo on 2018/7/11.
 */
public interface IAiAccountResigterInfoDao {
    public List<AiAccountResigterInfo> selectAiAccountResigterInfoList(String sql,String[] objects);
}
