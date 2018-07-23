package dao.impl;

import bean.AiAccountResigterInfo;
import dao.IAiAccountResigterInfoDao;
import jdbc.JDBCHelper;
import util.TimeUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/7/11.
 */
public class AiAccountResigterInfoDaoImpl implements IAiAccountResigterInfoDao {
    @Override
    public List<AiAccountResigterInfo> selectAiAccountResigterInfoList(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        final ArrayList<AiAccountResigterInfo> aiAccountResigterInfos = new ArrayList<>();
        instance.executeQuery(sql, objects, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    AiAccountResigterInfo aiAccountResigterInfo = new AiAccountResigterInfo();
                    aiAccountResigterInfo.setiAccountId(rs.getInt(1));
                    aiAccountResigterInfo.setsUserName(rs.getString(2));
                    aiAccountResigterInfo.setsPhone(rs.getString(3));
                    aiAccountResigterInfo.setsWbOpenId(rs.getString(4));
                    aiAccountResigterInfo.setsQQOpenId(rs.getString(5));
                    aiAccountResigterInfo.setsQqUnionId(rs.getString(6));
                    aiAccountResigterInfo.setsWxUnionId(rs.getString(7));
                    aiAccountResigterInfo.setsProvince(rs.getString(8));
                    aiAccountResigterInfo.setsCity(rs.getString(9));
                    aiAccountResigterInfo.setiFrom(rs.getInt(10));
                    aiAccountResigterInfo.setiUserType(rs.getInt(11));
                    aiAccountResigterInfo.setsDUA(rs.getString(12));
                    aiAccountResigterInfo.setsIMEI(rs.getString(13));
                    //将timeStamp时间int 转换成为yyyy-MM-dd 字符串
                    aiAccountResigterInfo.setTimeStr(TimeUtil.timeStamp2DateStr(rs.getInt(14)+"",""));
                    aiAccountResigterInfo.setsIP(rs.getString(15));
                    aiAccountResigterInfos.add(aiAccountResigterInfo);
                }
            }
        });
        return aiAccountResigterInfos;
    }
}
