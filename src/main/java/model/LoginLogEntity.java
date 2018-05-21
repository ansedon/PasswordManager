package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "login_log", schema = "passworddata", catalog = "")
public class LoginLogEntity {
    private int id;
    private int userId;
    private int roleId;
    private String ip;
    private String browserInfo;
    private String osInfo;
    private Timestamp createTime;
    private UserEntity userByUserId;
    private UserEntity userByUserId_0;
    private UserEntity userByUserId_1;
    private RoleEntity roleByRoleId;
    private RoleEntity roleByRoleId_0;
    private RoleEntity roleByRoleId_1;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "ip", nullable = true, length = 45)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "browser_info", nullable = true, length = 45)
    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    @Basic
    @Column(name = "os_info", nullable = true, length = 45)
    public String getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(String osInfo) {
        this.osInfo = osInfo;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginLogEntity that = (LoginLogEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (roleId != that.roleId) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (browserInfo != null ? !browserInfo.equals(that.browserInfo) : that.browserInfo != null) return false;
        if (osInfo != null ? !osInfo.equals(that.osInfo) : that.osInfo != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + roleId;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (browserInfo != null ? browserInfo.hashCode() : 0);
        result = 31 * result + (osInfo != null ? osInfo.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByUserId_0() {
        return userByUserId_0;
    }

    public void setUserByUserId_0(UserEntity userByUserId_0) {
        this.userByUserId_0 = userByUserId_0;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByUserId_1() {
        return userByUserId_1;
    }

    public void setUserByUserId_1(UserEntity userByUserId_1) {
        this.userByUserId_1 = userByUserId_1;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public RoleEntity getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(RoleEntity roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public RoleEntity getRoleByRoleId_0() {
        return roleByRoleId_0;
    }

    public void setRoleByRoleId_0(RoleEntity roleByRoleId_0) {
        this.roleByRoleId_0 = roleByRoleId_0;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public RoleEntity getRoleByRoleId_1() {
        return roleByRoleId_1;
    }

    public void setRoleByRoleId_1(RoleEntity roleByRoleId_1) {
        this.roleByRoleId_1 = roleByRoleId_1;
    }
}
