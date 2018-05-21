package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "resource", schema = "passworddata", catalog = "")
public class ResourceEntity {
    private int id;
    private String name;
    private String ip;
    private String description;
    private String url;
    private String location;
    private Integer pwdPolicy;
    private String superkey;
    private int typeId;
    private Timestamp createTime;
    private Byte isDeleted;
    private Collection<PasswordEntity> passwordsById;
    private Collection<PasswordEntity> passwordsById_0;
    private ResourceTypeEntity resourceTypeByTypeId;
    private ResourceTypeEntity resourceTypeByTypeId_0;
    private Collection<ResourceGroupEntity> resourceGroupsById;
    private Collection<ResourceGroupEntity> resourceGroupsById_0;
    private Collection<ResourceGroupEntity> resourceGroupsById_1;

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
    @Column(name = "ip", nullable = false, length = 20)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
    @Column(name = "url", nullable = true, length = 200)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    @Column(name = "pwd_policy", nullable = true)
    public Integer getPwdPolicy() {
        return pwdPolicy;
    }

    public void setPwdPolicy(Integer pwdPolicy) {
        this.pwdPolicy = pwdPolicy;
    }

    @Basic
    @Column(name = "superkey", nullable = true, length = -1)
    public String getSuperkey() {
        return superkey;
    }

    public void setSuperkey(String superkey) {
        this.superkey = superkey;
    }

    @Basic
    @Column(name = "type_id", nullable = false)
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

        ResourceEntity that = (ResourceEntity) o;

        if (id != that.id) return false;
        if (typeId != that.typeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (pwdPolicy != null ? !pwdPolicy.equals(that.pwdPolicy) : that.pwdPolicy != null) return false;
        if (superkey != null ? !superkey.equals(that.superkey) : that.superkey != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (pwdPolicy != null ? pwdPolicy.hashCode() : 0);
        result = 31 * result + (superkey != null ? superkey.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "resourceByResourceId")
    public Collection<PasswordEntity> getPasswordsById() {
        return passwordsById;
    }

    public void setPasswordsById(Collection<PasswordEntity> passwordsById) {
        this.passwordsById = passwordsById;
    }

    @OneToMany(mappedBy = "resourceByResourceId_0")
    public Collection<PasswordEntity> getPasswordsById_0() {
        return passwordsById_0;
    }

    public void setPasswordsById_0(Collection<PasswordEntity> passwordsById_0) {
        this.passwordsById_0 = passwordsById_0;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceTypeEntity getResourceTypeByTypeId() {
        return resourceTypeByTypeId;
    }

    public void setResourceTypeByTypeId(ResourceTypeEntity resourceTypeByTypeId) {
        this.resourceTypeByTypeId = resourceTypeByTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceTypeEntity getResourceTypeByTypeId_0() {
        return resourceTypeByTypeId_0;
    }

    public void setResourceTypeByTypeId_0(ResourceTypeEntity resourceTypeByTypeId_0) {
        this.resourceTypeByTypeId_0 = resourceTypeByTypeId_0;
    }

    @OneToMany(mappedBy = "resourceByResId")
    public Collection<ResourceGroupEntity> getResourceGroupsById() {
        return resourceGroupsById;
    }

    public void setResourceGroupsById(Collection<ResourceGroupEntity> resourceGroupsById) {
        this.resourceGroupsById = resourceGroupsById;
    }

    @OneToMany(mappedBy = "resourceByResId_0")
    public Collection<ResourceGroupEntity> getResourceGroupsById_0() {
        return resourceGroupsById_0;
    }

    public void setResourceGroupsById_0(Collection<ResourceGroupEntity> resourceGroupsById_0) {
        this.resourceGroupsById_0 = resourceGroupsById_0;
    }

    @OneToMany(mappedBy = "resourceByResId_1")
    public Collection<ResourceGroupEntity> getResourceGroupsById_1() {
        return resourceGroupsById_1;
    }

    public void setResourceGroupsById_1(Collection<ResourceGroupEntity> resourceGroupsById_1) {
        this.resourceGroupsById_1 = resourceGroupsById_1;
    }
}
