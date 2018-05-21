package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "passworddata", catalog = "")
public class UserEntity {
    private int id;
    private String account;
    private String password;
    private String email;
    private String phone;
    private String location;
    private Timestamp createTime;
    private int roleId;
    private Byte isDeleted;
    private Collection<CpGroupEntity> cpGroupsById;
    private Collection<CpGroupEntity> cpGroupsById_0;
    private Collection<CpGroupEntity> cpGroupsById_1;
    private Collection<CpGroupEntity> cpGroupsById_2;
    private Collection<LoginLogEntity> loginLogsById;
    private Collection<LoginLogEntity> loginLogsById_0;
    private Collection<LoginLogEntity> loginLogsById_1;
    private Collection<OperationEntity> operationsById;
    private Collection<OperationEntity> operationsById_0;
    private Collection<OperationEntity> operationsById_1;
    private Collection<PasswordEntity> passwordsById;
    private Collection<PasswordEntity> passwordsById_0;
    private Collection<PasswordEntity> passwordsById_1;
    private Collection<PasswordEntity> passwordsById_2;
    private Collection<PasswordEntity> passwordsById_3;
    private Collection<PasswordEntity> passwordsById_4;
    private Collection<RoleEntity> rolesById;
    private Collection<RoleEntity> rolesById_0;
    private Collection<RoleEntity> rolesById_1;
    private Collection<RoleEntity> rolesById_2;
    private Collection<RoleEntity> rolesById_3;
    private Collection<RoleEntity> rolesById_4;
    private RoleEntity roleByRoleId;
    private RoleEntity roleByRoleId_0;
    private RoleEntity roleByRoleId_1;
    private Collection<UserGroupEntity> userGroupsById;
    private Collection<UserGroupEntity> userGroupsById_0;
    private Collection<UserGroupEntity> userGroupsById_1;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "account", nullable = false, length = 50)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 2000)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 50)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
    @Column(name = "is_deleted", nullable = true)
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + roleId;
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByCreatorId")
    public Collection<CpGroupEntity> getCpGroupsById() {
        return cpGroupsById;
    }

    public void setCpGroupsById(Collection<CpGroupEntity> cpGroupsById) {
        this.cpGroupsById = cpGroupsById;
    }

    @OneToMany(mappedBy = "userByCreatorId_0")
    public Collection<CpGroupEntity> getCpGroupsById_0() {
        return cpGroupsById_0;
    }

    public void setCpGroupsById_0(Collection<CpGroupEntity> cpGroupsById_0) {
        this.cpGroupsById_0 = cpGroupsById_0;
    }

    @OneToMany(mappedBy = "userByModifierId")
    public Collection<CpGroupEntity> getCpGroupsById_1() {
        return cpGroupsById_1;
    }

    public void setCpGroupsById_1(Collection<CpGroupEntity> cpGroupsById_1) {
        this.cpGroupsById_1 = cpGroupsById_1;
    }

    @OneToMany(mappedBy = "userByModifierId_0")
    public Collection<CpGroupEntity> getCpGroupsById_2() {
        return cpGroupsById_2;
    }

    public void setCpGroupsById_2(Collection<CpGroupEntity> cpGroupsById_2) {
        this.cpGroupsById_2 = cpGroupsById_2;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<LoginLogEntity> getLoginLogsById() {
        return loginLogsById;
    }

    public void setLoginLogsById(Collection<LoginLogEntity> loginLogsById) {
        this.loginLogsById = loginLogsById;
    }

    @OneToMany(mappedBy = "userByUserId_0")
    public Collection<LoginLogEntity> getLoginLogsById_0() {
        return loginLogsById_0;
    }

    public void setLoginLogsById_0(Collection<LoginLogEntity> loginLogsById_0) {
        this.loginLogsById_0 = loginLogsById_0;
    }

    @OneToMany(mappedBy = "userByUserId_1")
    public Collection<LoginLogEntity> getLoginLogsById_1() {
        return loginLogsById_1;
    }

    public void setLoginLogsById_1(Collection<LoginLogEntity> loginLogsById_1) {
        this.loginLogsById_1 = loginLogsById_1;
    }

    @OneToMany(mappedBy = "userByModifierId")
    public Collection<OperationEntity> getOperationsById() {
        return operationsById;
    }

    public void setOperationsById(Collection<OperationEntity> operationsById) {
        this.operationsById = operationsById;
    }

    @OneToMany(mappedBy = "userByModifierId_0")
    public Collection<OperationEntity> getOperationsById_0() {
        return operationsById_0;
    }

    public void setOperationsById_0(Collection<OperationEntity> operationsById_0) {
        this.operationsById_0 = operationsById_0;
    }

    @OneToMany(mappedBy = "userByModifierId_1")
    public Collection<OperationEntity> getOperationsById_1() {
        return operationsById_1;
    }

    public void setOperationsById_1(Collection<OperationEntity> operationsById_1) {
        this.operationsById_1 = operationsById_1;
    }

    @OneToMany(mappedBy = "userByCreatorId")
    public Collection<PasswordEntity> getPasswordsById() {
        return passwordsById;
    }

    public void setPasswordsById(Collection<PasswordEntity> passwordsById) {
        this.passwordsById = passwordsById;
    }

    @OneToMany(mappedBy = "userByCreatorId_0")
    public Collection<PasswordEntity> getPasswordsById_0() {
        return passwordsById_0;
    }

    public void setPasswordsById_0(Collection<PasswordEntity> passwordsById_0) {
        this.passwordsById_0 = passwordsById_0;
    }

    @OneToMany(mappedBy = "userByCreatorId_1")
    public Collection<PasswordEntity> getPasswordsById_1() {
        return passwordsById_1;
    }

    public void setPasswordsById_1(Collection<PasswordEntity> passwordsById_1) {
        this.passwordsById_1 = passwordsById_1;
    }

    @OneToMany(mappedBy = "userByModifierId")
    public Collection<PasswordEntity> getPasswordsById_2() {
        return passwordsById_2;
    }

    public void setPasswordsById_2(Collection<PasswordEntity> passwordsById_2) {
        this.passwordsById_2 = passwordsById_2;
    }

    @OneToMany(mappedBy = "userByModifierId_0")
    public Collection<PasswordEntity> getPasswordsById_3() {
        return passwordsById_3;
    }

    public void setPasswordsById_3(Collection<PasswordEntity> passwordsById_3) {
        this.passwordsById_3 = passwordsById_3;
    }

    @OneToMany(mappedBy = "userByModifierId_1")
    public Collection<PasswordEntity> getPasswordsById_4() {
        return passwordsById_4;
    }

    public void setPasswordsById_4(Collection<PasswordEntity> passwordsById_4) {
        this.passwordsById_4 = passwordsById_4;
    }

    @OneToMany(mappedBy = "userByCreatorId")
    public Collection<RoleEntity> getRolesById() {
        return rolesById;
    }

    public void setRolesById(Collection<RoleEntity> rolesById) {
        this.rolesById = rolesById;
    }

    @OneToMany(mappedBy = "userByCreatorId_0")
    public Collection<RoleEntity> getRolesById_0() {
        return rolesById_0;
    }

    public void setRolesById_0(Collection<RoleEntity> rolesById_0) {
        this.rolesById_0 = rolesById_0;
    }

    @OneToMany(mappedBy = "userByCreatorId_1")
    public Collection<RoleEntity> getRolesById_1() {
        return rolesById_1;
    }

    public void setRolesById_1(Collection<RoleEntity> rolesById_1) {
        this.rolesById_1 = rolesById_1;
    }

    @OneToMany(mappedBy = "userByModifierId")
    public Collection<RoleEntity> getRolesById_2() {
        return rolesById_2;
    }

    public void setRolesById_2(Collection<RoleEntity> rolesById_2) {
        this.rolesById_2 = rolesById_2;
    }

    @OneToMany(mappedBy = "userByModifierId_0")
    public Collection<RoleEntity> getRolesById_3() {
        return rolesById_3;
    }

    public void setRolesById_3(Collection<RoleEntity> rolesById_3) {
        this.rolesById_3 = rolesById_3;
    }

    @OneToMany(mappedBy = "userByModifierId_1")
    public Collection<RoleEntity> getRolesById_4() {
        return rolesById_4;
    }

    public void setRolesById_4(Collection<RoleEntity> rolesById_4) {
        this.rolesById_4 = rolesById_4;
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

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserGroupEntity> getUserGroupsById() {
        return userGroupsById;
    }

    public void setUserGroupsById(Collection<UserGroupEntity> userGroupsById) {
        this.userGroupsById = userGroupsById;
    }

    @OneToMany(mappedBy = "userByUserId_0")
    public Collection<UserGroupEntity> getUserGroupsById_0() {
        return userGroupsById_0;
    }

    public void setUserGroupsById_0(Collection<UserGroupEntity> userGroupsById_0) {
        this.userGroupsById_0 = userGroupsById_0;
    }

    @OneToMany(mappedBy = "userByUserId_1")
    public Collection<UserGroupEntity> getUserGroupsById_1() {
        return userGroupsById_1;
    }

    public void setUserGroupsById_1(Collection<UserGroupEntity> userGroupsById_1) {
        this.userGroupsById_1 = userGroupsById_1;
    }
}
