package json;

import model.LoginLogEntity;

public class LoginLogResponse {
    public int id;
    public String userName;
    public int userId;
    public int roleId;
    public String roleName;
    public String ip;
    public String browserInfo;
    public String osInfo;
    public String createTime;

    public LoginLogResponse(LoginLogEntity loginLogEntity){
        this.id=loginLogEntity.getId();
        this.userId=loginLogEntity.getUserId();
        this.roleId=loginLogEntity.getRoleId();
        this.ip=loginLogEntity.getIp();
        this.browserInfo=loginLogEntity.getBrowserInfo();
        this.osInfo=loginLogEntity.getOsInfo();
        this.createTime=loginLogEntity.getCreateTime().toString().substring(0,19);
    }
}
