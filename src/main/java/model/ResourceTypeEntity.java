package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "resource_type", schema = "passworddata", catalog = "")
public class ResourceTypeEntity {
    private int id;
    private String name;
    private String icon;
    private Timestamp createTime;
    private Collection<ResourceEntity> resourcesById;
    private Collection<ResourceEntity> resourcesById_0;

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
    @Column(name = "icon", nullable = false, length = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
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

        ResourceTypeEntity that = (ResourceTypeEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "resourceTypeByTypeId")
    public Collection<ResourceEntity> getResourcesById() {
        return resourcesById;
    }

    public void setResourcesById(Collection<ResourceEntity> resourcesById) {
        this.resourcesById = resourcesById;
    }

    @OneToMany(mappedBy = "resourceTypeByTypeId_0")
    public Collection<ResourceEntity> getResourcesById_0() {
        return resourcesById_0;
    }

    public void setResourcesById_0(Collection<ResourceEntity> resourcesById_0) {
        this.resourcesById_0 = resourcesById_0;
    }
}
