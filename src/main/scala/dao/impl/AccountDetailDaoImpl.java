package dao.impl;

import bean.AccountDetail;
import dao.IAccountDetailDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/7/12.
 */
public class AccountDetailDaoImpl implements IAccountDetailDao{
    @Override
    public List<AccountDetail> selectAccountDetaiList(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();

        final ArrayList<AccountDetail> accountDetails = new ArrayList<>();
        instance.executeQuery(sql, objects, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    AccountDetail accountDetail = new AccountDetail();
                    accountDetail.setiAccountId(rs.getInt(1));
                    accountDetail.setsUserName(rs.getString(2));
                    accountDetail.setsPhone(rs.getString(3));
                    accountDetail.setsWxOpenId(rs.getString(4));
                    accountDetail.setsQqOpenId(rs.getString(5));
                    accountDetail.setsWbOpenId(rs.getString(6));
                    accountDetail.setsWxUnionId(rs.getString(7));
                    accountDetail.setsQqUnionId(rs.getString(8));
                    accountDetail.setsFaceUrl(rs.getString(9));
                    accountDetail.setiUpdateTime(rs.getString(10));
                    accountDetail.setiGender(rs.getInt(11));
                    accountDetail.setsProvince(rs.getString(12));
                    accountDetail.setsCity(rs.getString(13));
                    accountDetail.setsAddress(rs.getString(14));
                    accountDetail.setsProfile(rs.getString(15));
                    accountDetail.setsVerifyDesc(rs.getString(16));
                    accountDetail.setiUserType(rs.getInt(17));
                    accountDetail.setStMember(rs.getString(18));
                    accountDetail.setsUserRealName(rs.getString(19));
                    accountDetail.setsUserIDNumber(rs.getString(20));
                    accountDetail.setiGenTimeStamp(rs.getString(21));
                    accountDetail.setsXcxPhoneNum(rs.getString(22));
                    accountDetail.setiSharesAge(rs.getInt(23));
                    accountDetail.setsEmail(rs.getString(24));
                    accountDetail.setsSource(rs.getString(25));
                    accountDetail.setTimeStr(rs.getString(26));
                    accountDetails.add(accountDetail);
                }
            }
        });
        return accountDetails;
    }
}
