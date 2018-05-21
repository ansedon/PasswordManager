package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "cp_group", schema = "passworddata", catalog = "")
public class CpGroupEntity {
    private int id;
    private String name;
    private String description;
    private String location;
    private Integer fatherGroupId;
    private int creatorId;
    private Timestamp createTime;
    private Timestamp modifiedTime;
    private Integer modifierId;
    private Byte isDeleted;
    private UserEntity userByCreatorId;
    private UserEntity userByCreatorId_0;
    private UserEntity userByModifierId;
    private UserEntity userByModifierId_0;
    private Collection<ResourceGroupEntity> resourceGroupsById;
    private Collection<ResourceGroupEntity> resourceGroupsById_0;
    private Collection<UserGroupEntity> userGroupsById;
    private Collection<UserGroupEntity> userGroupsById_0;

    @Id
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
    @Column(name = "location", nullable = true, length = 200)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "father_group_id", nullable = true)
    public Integer getFatherGroupId() {
        return fatherGroupId;
    }

    public void setFatherGroupId(Integer fatherGroupId) {
        this.fatherGroupId = fatherGroupId;
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
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

        CpGroupEntity that = (CpGroupEntity) o;

        if (id != that.id) return false;
        if (creatorId != that.creatorId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (fatherGroupId != null ? !fatherGroupId.equals(that.fatherGroupId) : that.fatherGroupId != null)
            return false;
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
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (fatherGroupId != null ? fatherGroupId.hashCode() : 0);
        result = 31 * result + creatorId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        result = 31 * result + (modifierId != null ? modifierId.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
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

    @OneToMany(mappedBy = "cpGroupByGroupId")
    public Collection<ResourceGroupEntity> getResourceGroupsById() {
        return resourceGroupsById;
    }

    public void setResourceGroupsById(Collection<ResourceGroupEntity> resourceGroupsById) {
        this.resourceGroupsById = resourceGroupsById;
    }

    @OneToMany(mappedBy = "cpGroupByGroupId_0")
    public Collection<ResourceGroupEntity> getResourceGroupsById_0() {
        return resourceGroupsById_0;
    }

    public void setResourceGroupsById_0(Collection<ResourceGroupEntity> resourceGroupsById_0) {
        this.resourceGroupsById_0 = resourceGroupsById_0;
    }

    @OneToMany(mappedBy = "cpGroupByGroupId")
    public Collection<UserGroupEntity> getUserGroupsById() {
        return userGroupsById;
    }

    public void setUserGroupsById(Collection<UserGroupEntity> userGroupsById) {
        this.userGroupsById = userGroupsById;
    }

    @OneToMany(mappedBy = "cpGroupByGroupId_0")
    public Collection<UserGroupEntity> getUserGroupsById_0() {
        return userGroupsById_0;
    }

    public void setUserGroupsById_0(Collection<UserGroupEntity> userGroupsById_0) {
        this.userGroupsById_0 = userGroupsById_0;
    }
}
