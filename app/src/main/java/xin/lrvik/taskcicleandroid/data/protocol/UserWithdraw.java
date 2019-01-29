package xin.lrvik.taskcicleandroid.data.protocol;


import java.sql.Timestamp;

import xin.lrvik.taskcicleandroid.data.protocol.enums.WithdrawState;
import xin.lrvik.taskcicleandroid.data.protocol.enums.WithdrawType;

public class UserWithdraw {

    private String id;
    private Long userId;
    private Float money;
    private Float realityMoney;
    private String context;
    private WithdrawState state;
    private WithdrawType type;
    private Timestamp createTime;
    private Timestamp adminAuditTime;
    private Timestamp auditPassTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getRealityMoney() {
        return realityMoney;
    }

    public void setRealityMoney(Float realityMoney) {
        this.realityMoney = realityMoney;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public WithdrawState getState() {
        return state;
    }

    public void setState(WithdrawState state) {
        this.state = state;
    }

    public WithdrawType getType() {
        return type;
    }

    public void setType(WithdrawType type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getAdminAuditTime() {
        return adminAuditTime;
    }

    public void setAdminAuditTime(Timestamp adminAuditTime) {
        this.adminAuditTime = adminAuditTime;
    }

    public Timestamp getAuditPassTime() {
        return auditPassTime;
    }

    public void setAuditPassTime(Timestamp auditPassTime) {
        this.auditPassTime = auditPassTime;
    }
}
