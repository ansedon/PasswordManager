package model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ResourceGroupEntityPK implements Serializable {
    private int resId;
    private int groupId;

    @Column(name = "res_id", nullable = false)
    @Id
    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Column(name = "group_id", nullable = false)
    @Id
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

        ResourceGroupEntityPK that = (ResourceGroupEntityPK) o;

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
