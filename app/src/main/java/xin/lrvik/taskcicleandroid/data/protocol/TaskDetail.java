package xin.lrvik.taskcicleandroid.data.protocol;


import java.sql.Timestamp;
import java.util.ArrayList;
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState;
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskType;

public class TaskDetail {

    private String id;
    private Long userId;
    private String name;
    private String context;
    private String username;
    private String headImg;
    private Float money;
    private TaskState state;
    private TaskType type;
    private Integer peopleNumber;
    private Timestamp createTime;
    private Timestamp beginTime;
    private Timestamp deadline;
    private Timestamp auditTime;
    private Timestamp adminAuditTime;
    private Timestamp issueTime;
    private Float originalMoney;
    private Float compensateMoney;
    private Integer permitAbandonMinute;
    private Double longitude;
    private Double latitude;
    private Boolean isTaskRework;
    private Boolean isCompensate;

    private ArrayList<TaskClass> taskClassifyAppDtos;
    private ArrayList<TaskStep> taskSteps;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    public Timestamp getAdminAuditTime() {
        return adminAuditTime;
    }

    public void setAdminAuditTime(Timestamp adminAuditTime) {
        this.adminAuditTime = adminAuditTime;
    }

    public Timestamp getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Timestamp issueTime) {
        this.issueTime = issueTime;
    }

    public Float getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(Float originalMoney) {
        this.originalMoney = originalMoney;
    }

    public Float getCompensateMoney() {
        return compensateMoney;
    }

    public void setCompensateMoney(Float compensateMoney) {
        this.compensateMoney = compensateMoney;
    }

    public Integer getPermitAbandonMinute() {
        return permitAbandonMinute;
    }

    public void setPermitAbandonMinute(Integer permitAbandonMinute) {
        this.permitAbandonMinute = permitAbandonMinute;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getTaskRework() {
        return isTaskRework;
    }

    public void setTaskRework(Boolean taskRework) {
        isTaskRework = taskRework;
    }

    public Boolean getCompensate() {
        return isCompensate;
    }

    public void setCompensate(Boolean compensate) {
        isCompensate = compensate;
    }

    public ArrayList<TaskClass> getTaskClassifyAppDtos() {
        return taskClassifyAppDtos;
    }

    public void setTaskClassifyAppDtos(ArrayList<TaskClass> taskClassifyAppDtos) {
        this.taskClassifyAppDtos = taskClassifyAppDtos;
    }

    public ArrayList<TaskStep> getTaskSteps() {
        return taskSteps;
    }

    public void setTaskSteps(ArrayList<TaskStep> taskSteps) {
        this.taskSteps = taskSteps;
    }
}
