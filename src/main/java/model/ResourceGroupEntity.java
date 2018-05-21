package model;

import javax.persistence.*;

@Entity
@Table(name = "resource_group", schema = "passworddata", catalog = "")
@IdClass(ResourceGroupEntityPK.class)
public class ResourceGroupEntity {
    private int resId;
    private int groupId;
    private ResourceEntity resourceByResId;
    private ResourceEntity resourceByResId_0;
    private ResourceEntity resourceByResId_1;
    private CpGroupEntity cpGroupByGroupId;
    private CpGroupEntity cpGroupByGroupId_0;

    @Id
    @Column(name = "res_id", nullable = false)
    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Id
    @Column(name = "group_id", nullable = false)
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceGroupEntity that = (ResourceGroupEntity) o;

        if (resId != that.resId) return false;
        if (groupId != that.groupId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resId;
        result = 31 * result + groupId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "res_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceEntity getResourceByResId() {
        return resourceByResId;
    }

    public void setResourceByResId(ResourceEntity resourceByResId) {
        this.resourceByResId = resourceByResId;
    }

    @ManyToOne
    @JoinColumn(name = "res_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceEntity getResourceByResId_0() {
        return resourceByResId_0;
    }

    public void setResourceByResId_0(ResourceEntity resourceByResId_0) {
        this.resourceByResId_0 = resourceByResId_0;
    }

    @ManyToOne
    @JoinColumn(name = "res_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public ResourceEntity getResourceByResId_1() {
        return resourceByResId_1;
    }

    public void setResourceByResId_1(ResourceEntity resourceByResId_1) {
        this.resourceByResId_1 = resourceByResId_1;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public CpGroupEntity getCpGroupByGroupId() {
        return cpGroupByGroupId;
    }

    public void setCpGroupByGroupId(CpGroupEntity cpGroupByGroupId) {
        this.cpGroupByGroupId = cpGroupByGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public CpGroupEntity getCpGroupByGroupId_0() {
        return cpGroupByGroupId_0;
    }

    public void setCpGroupByGroupId_0(CpGroupEntity cpGroupByGroupId_0) {
        this.cpGroupByGroupId_0 = cpGroupByGroupId_0;
    }
}
