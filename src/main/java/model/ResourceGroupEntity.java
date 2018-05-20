package model;

import javax.persistence.*;

@Entity
@Table(name = "resource_group", schema = "passworddata", catalog = "")
@IdClass(ResourceGroupEntityPK.class)
public class ResourceGroupEntity {
    private int resId;
    private int groupId;

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
}
