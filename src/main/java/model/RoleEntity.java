package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "role", schema = "passworddata", catalog = "")
public class RoleEntity {
    private int id;
    private String name;
    private String description;
    private Timestamp createTime;
    private int creatorId;
    private Timestamp modifiedTime;
    private Integer modifierId;
    private int privilegeId;
    private Byte isDeleted;
    private Collection<LoginLogEntity> loginLogsById;
    private Collection<LoginLogEntity> loginLogsById_0;
    private Collection<LoginLogEntity> loginLogsById_1;
    private UserEntity userByCreatorId;
    private UserEntity userByCreatorId_0;
    private UserEntity userByCreatorId_1;
    private UserEntity userByModifierId;
    private UserEntity userByModifierId_0;
    private UserEntity userByModifierId_1;
    private PrivilegeEntity privilegeByPrivilegeId;
    private PrivilegeEntity privilegeByPrivilegeId_0;
    private Collection<UserEntity> usersById;
    private Collection<UserEntity> usersById_0;
    private Collection<UserEntity> usersById_1;
    private int level;

    private int typeAdd;
    private int typeEdit;
    private int typeDelete;
    private int resAdd;
    private int resEdit;
    private int resDelete;
    private int pwdAdd;
    private int pwdEdit;
    private int pwdDelete;
    private int userAdd;
    private int userEdit;
    private int userDelete;
    private int groupAdd;
    private int groupEdit;
    private int groupDelete;

    @Transient
    public int getTypeAdd() {
        return typeAdd;
    }

    public void setTypeAdd(int typeAdd) {
        this.typeAdd = typeAdd;
    }

    @Transient
    public int getTypeEdit() {
        return typeEdit;
    }

    public void setTypeEdit(int typeEdit) {
        this.typeEdit = typeEdit;
    }

    @Transient
    public int getTypeDelete() {
        return typeDelete;
    }

    public void setTypeDelete(int typeDelete) {
        this.typeDelete = typeDelete;
    }

    @Transient
    public int getResAdd() {
        return resAdd;
    }

    public void setResAdd(int resAdd) {
        this.resAdd = resAdd;
    }

    @Transient
    public int getResEdit() {
        return resEdit;
    }

    public void setResEdit(int resEdit) {
        this.resEdit = resEdit;
    }

    @Transient
    public int getResDelete() {
        return resDelete;
    }

    public void setResDelete(int resDelete) {
        this.resDelete = resDelete;
    }

    @Transient
    public int getPwdAdd() {
        return pwdAdd;
    }

    public void setPwdAdd(int pwdAdd) {
        this.pwdAdd = pwdAdd;
    }

    @Transient
    public int getPwdEdit() {
        return pwdEdit;
    }

    public void setPwdEdit(int pwdEdit) {
        this.pwdEdit = pwdEdit;
    }

    @Transient
    public int getPwdDelete() {
        return pwdDelete;
    }

    public void setPwdDelete(int pwdDelete) {
        this.pwdDelete = pwdDelete;
    }

    @Transient
    public int getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(int userAdd) {
        this.userAdd = userAdd;
    }

    @Transient
    public int getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(int userEdit) {
        this.userEdit = userEdit;
    }

    @Transient
    public int getUserDelete() {
        return userDelete;
    }

    public void setUserDelete(int userDelete) {
        this.userDelete = userDelete;
    }

    @Transient
    public int getGroupAdd() {
        return groupAdd;
    }

    public void setGroupAdd(int groupAdd) {
        this.groupAdd = groupAdd;
    }

    @Transient
    public int getGroupEdit() {
        return groupEdit;
    }

    public void setGroupEdit(int groupEdit) {
        this.groupEdit = groupEdit;
    }

    @Transient
    public int getGroupDelete() {
        return groupDelete;
    }

    public void setGroupDelete(int groupDelete) {
        this.groupDelete = groupDelete;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "creator_id", nullable = false)
    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "modified_time", nullable = true)
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Basic
    @Column(name = "modifier_id", nullable = true)
    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    @Basic
    @Column(name = "privilege_id", nullable = false)
    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
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

        RoleEntity that = (RoleEntity) o;

        if (id != that.id) return false;
        if (creatorId != that.creatorId) return false;
        if (privilegeId != that.privilegeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null) return false;
        if (modifierId != null ? !modifierId.equals(that.modifierId) : that.modifierId != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + creatorId;
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        result = 31 * result + (modifierId != null ? modifierId.hashCode() : 0);
        result = 31 * result + privilegeId;
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<LoginLogEntity> getLoginLogsById() {
        return loginLogsById;
    }

    public void setLoginLogsById(Collection<LoginLogEntity> loginLogsById) {
        this.loginLogsById = loginLogsById;
    }

    @OneToMany(mappedBy = "roleByRoleId_0")
    public Collection<LoginLogEntity> getLoginLogsById_0() {
        return loginLogsById_0;
    }

    public void setLoginLogsById_0(Collection<LoginLogEntity> loginLogsById_0) {
        this.loginLogsById_0 = loginLogsById_0;
    }

    @OneToMany(mappedBy = "roleByRoleId_1")
    public Collection<LoginLogEntity> getLoginLogsById_1() {
        return loginLogsById_1;
    }

    public void setLoginLogsById_1(Collection<LoginLogEntity> loginLogsById_1) {
        this.loginLogsById_1 = loginLogsById_1;
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByCreatorId() {
        return userByCreatorId;
    }

    public void setUserByCreatorId(UserEntity userByCreatorId) {
        this.userByCreatorId = userByCreatorId;
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByCreatorId_0() {
        return userByCreatorId_0;
    }

    public void setUserByCreatorId_0(UserEntity userByCreatorId_0) {
        this.userByCreatorId_0 = userByCreatorId_0;
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByCreatorId_1() {
        return userByCreatorId_1;
    }

    public void setUserByCreatorId_1(UserEntity userByCreatorId_1) {
        this.userByCreatorId_1 = userByCreatorId_1;
    }

    @ManyToOne
    @JoinColumn(name = "modifier_id", referencedColumnName = "id",insertable = false,updatable = false)
    public UserEntity getUserByModifierId() {
        return userByModifierId;
    }

    public void setUserByModifierId(UserEntity userByModifierId) {
        this.userByModifierId = userByModifierId;
    }

    @ManyToOne
    @JoinColumn(name = "modifier_id", referencedColumnName = "id",insertable = false,updatable = false)
    public UserEntity getUserByModifierId_0() {
        return userByModifierId_0;
    }

    public void setUserByModifierId_0(UserEntity userByModifierId_0) {
        this.userByModifierId_0 = userByModifierId_0;
    }

    @ManyToOne
    @JoinColumn(name = "modifier_id", referencedColumnName = "id",insertable = false,updatable = false)
    public UserEntity getUserByModifierId_1() {
        return userByModifierId_1;
    }

    public void setUserByModifierId_1(UserEntity userByModifierId_1) {
        this.userByModifierId_1 = userByModifierId_1;
    }

    @ManyToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public PrivilegeEntity getPrivilegeByPrivilegeId() {
        return privilegeByPrivilegeId;
    }

    public void setPrivilegeByPrivilegeId(PrivilegeEntity privilegeByPrivilegeId) {
        this.privilegeByPrivilegeId = privilegeByPrivilegeId;
    }

    @ManyToOne
    @JoinColumn(name = "privilege_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public PrivilegeEntity getPrivilegeByPrivilegeId_0() {
        return privilegeByPrivilegeId_0;
    }

    public void setPrivilegeByPrivilegeId_0(PrivilegeEntity privilegeByPrivilegeId_0) {
        this.privilegeByPrivilegeId_0 = privilegeByPrivilegeId_0;
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }

    @OneToMany(mappedBy = "roleByRoleId_0")
    public Collection<UserEntity> getUsersById_0() {
        return usersById_0;
    }

    public void setUsersById_0(Collection<UserEntity> usersById_0) {
        this.usersById_0 = usersById_0;
    }

    @OneToMany(mappedBy = "roleByRoleId_1")
    public Collection<UserEntity> getUsersById_1() {
        return usersById_1;
    }

    public void setUsersById_1(Collection<UserEntity> usersById_1) {
        this.usersById_1 = usersById_1;
    }

    @Basic
    @Column(name = "level", nullable = false)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
