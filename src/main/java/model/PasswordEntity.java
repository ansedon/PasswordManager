package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "password", schema = "passworddata", catalog = "")
public class PasswordEntity {
    private int id;
    private String account;
    private String password;
    private Timestamp createTime;
    private int creatorId;
    private Timestamp modifiedTime;
    private Integer modifierId;
    private Timestamp expireTime;
    private int resourceId;
    private Byte isDeleted;
    private Collection<OperationEntity> operationsById;
    private Collection<OperationEntity> operationsById_0;
    private UserEntity userByCreatorId;
    private UserEntity userByCreatorId_0;
    private UserEntity userByCreatorId_1;
    private UserEntity userByModifierId;
    private UserEntity userByModifierId_0;
    private UserEntity userByModifierId_1;
    private ResourceEntity resourceByResourceId;
    private ResourceEntity resourceByResourceId_0;

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
    @Column(name = "password", nullable = false, length = 200)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "expire_time", nullable = true)
    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    @Basic
    @Column(name = "resource_id", nullable = false)
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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

        PasswordEntity that = (PasswordEntity) o;

        if (id != that.id) return false;
        if (creatorId != that.creatorId) return false;
        if (resourceId != that.resourceId) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null) return false;
        if (modifierId != null ? !modifierId.equals(that.modifierId) : that.modifierId != null) return false;
        if (expireTime != null ? !expireTime.equals(that.expireTime) : that.expireTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + creatorId;
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        result = 31 * result + (modifierId != null ? modifierId.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + resourceId;
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "passwordByPawId")
    public Collection<OperationEntity> getOperationsById() {
        return operationsById;
    }

    public void setOperationsById(Collection<OperationEntity> operationsById) {
        this.operationsById = operationsById;
    }

    @OneToMany(mappedBy = "passwordByPawId_0")
    public Collection<OperationEntity> getOperationsById_0() {
        return operationsById_0;
    }

    public void setOperationsById_0(Collection<OperationEntity> operationsById_0) {
        this.operationsById_0 = operationsById_0;
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
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceEntity getResourceByResourceId() {
        return resourceByResourceId;
    }

    public void setResourceByResourceId(ResourceEntity resourceByResourceId) {
        this.resourceByResourceId = resourceByResourceId;
    }

    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceEntity getResourceByResourceId_0() {
        return resourceByResourceId_0;
    }

    public void setResourceByResourceId_0(ResourceEntity resourceByResourceId_0) {
        this.resourceByResourceId_0 = resourceByResourceId_0;
    }
}
