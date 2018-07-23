package bean;

/**
 * Created by lenovo on 2018/7/2.
 */
public class TSpecialist {
   /* id	bigint
    ExpertName	varchar
    ExpertType	varchar
    ExpertNumber	bigint
    ExpertClick	bigint
    ExpertNewUser	bigint
    ExpertUser	bigint
    ExpertAggregateUser	bigint*/
    private Long id;
    private String ExpertName;
    private String ExpertType;
    private Long ExpertNumber;
    private Long ExpertClick;
    private Long ExpertNewUser;
    private Long ExpertUser;
    private Long ExpertAggregateUser;
    private Long Adid;
    private String InsertTime;

    public Long getAdid() {
        return Adid;
    }

    public void setAdid(Long adid) {
        Adid = adid;
    }

    public String getInsertTime() {
        return InsertTime;
    }

    public void setInsertTime(String insertTime) {
        InsertTime = insertTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpertName() {
        return ExpertName;
    }

    public void setExpertName(String expertName) {
        ExpertName = expertName;
    }

    public String getExpertType() {
        return ExpertType;
    }

    public void setExpertType(String expertType) {
        ExpertType = expertType;
    }

    public Long getExpertNumber() {
        return ExpertNumber;
    }

    public void setExpertNumber(Long expertNumber) {
        ExpertNumber = expertNumber;
    }

    public Long getExpertClick() {
        return ExpertClick;
    }

    public void setExpertClick(Long expertClick) {
        ExpertClick = expertClick;
    }

    public Long getExpertNewUser() {
        return ExpertNewUser;
    }

    public void setExpertNewUser(Long expertNewUser) {
        ExpertNewUser = expertNewUser;
    }

    public Long getExpertUser() {
        return ExpertUser;
    }

    public void setExpertUser(Long expertUser) {
        ExpertUser = expertUser;
    }

    public Long getExpertAggregateUser() {
        return ExpertAggregateUser;
    }

    public void setExpertAggregateUser(Long expertAggregateUser) {
        ExpertAggregateUser = expertAggregateUser;
    }
}
