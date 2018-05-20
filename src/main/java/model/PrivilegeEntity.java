package model;

import javax.persistence.*;

@Entity
@Table(name = "privilege", schema = "passworddata", catalog = "")
public class PrivilegeEntity {
    private int id;
    private Byte resAdd;
    private Byte resEdit;
    private Byte resDelete;
    private Byte pawAdd;
    private Byte pawEdit;
    private Byte pawDelete;
    private Byte groupAdd;
    private Byte groupEdit;
    private Byte groupDelete;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "res_add", nullable = true)
    public Byte getResAdd() {
        return resAdd;
    }

    public void setResAdd(Byte resAdd) {
        this.resAdd = resAdd;
    }

    @Basic
    @Column(name = "res_edit", nullable = true)
    public Byte getResEdit() {
        return resEdit;
    }

    public void setResEdit(Byte resEdit) {
        this.resEdit = resEdit;
    }

    @Basic
    @Column(name = "res_delete", nullable = true)
    public Byte getResDelete() {
        return resDelete;
    }

    public void setResDelete(Byte resDelete) {
        this.resDelete = resDelete;
    }

    @Basic
    @Column(name = "paw_add", nullable = true)
    public Byte getPawAdd() {
        return pawAdd;
    }

    public void setPawAdd(Byte pawAdd) {
        this.pawAdd = pawAdd;
    }

    @Basic
    @Column(name = "paw_edit", nullable = true)
    public Byte getPawEdit() {
        return pawEdit;
    }

    public void setPawEdit(Byte pawEdit) {
        this.pawEdit = pawEdit;
    }

    @Basic
    @Column(name = "paw_delete", nullable = true)
    public Byte getPawDelete() {
        return pawDelete;
    }

    public void setPawDelete(Byte pawDelete) {
        this.pawDelete = pawDelete;
    }

    @Basic
    @Column(name = "group_add", nullable = true)
    public Byte getGroupAdd() {
        return groupAdd;
    }

    public void setGroupAdd(Byte groupAdd) {
        this.groupAdd = groupAdd;
    }

    @Basic
    @Column(name = "group_edit", nullable = true)
    public Byte getGroupEdit() {
        return groupEdit;
    }

    public void setGroupEdit(Byte groupEdit) {
        this.groupEdit = groupEdit;
    }

    @Basic
    @Column(name = "group_delete", nullable = true)
    public Byte getGroupDelete() {
        return groupDelete;
    }

    public void setGroupDelete(Byte groupDelete) {
        this.groupDelete = groupDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivilegeEntity that = (PrivilegeEntity) o;

        if (id != that.id) return false;
        if (resAdd != null ? !resAdd.equals(that.resAdd) : that.resAdd != null) return false;
        if (resEdit != null ? !resEdit.equals(that.resEdit) : that.resEdit != null) return false;
        if (resDelete != null ? !resDelete.equals(that.resDelete) : that.resDelete != null) return false;
        if (pawAdd != null ? !pawAdd.equals(that.pawAdd) : that.pawAdd != null) return false;
        if (pawEdit != null ? !pawEdit.equals(that.pawEdit) : that.pawEdit != null) return false;
        if (pawDelete != null ? !pawDelete.equals(that.pawDelete) : that.pawDelete != null) return false;
        if (groupAdd != null ? !groupAdd.equals(that.groupAdd) : that.groupAdd != null) return false;
        if (groupEdit != null ? !groupEdit.equals(that.groupEdit) : that.groupEdit != null) return false;
        if (groupDelete != null ? !groupDelete.equals(that.groupDelete) : that.groupDelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (resAdd != null ? resAdd.hashCode() : 0);
        result = 31 * result + (resEdit != null ? resEdit.hashCode() : 0);
        result = 31 * result + (resDelete != null ? resDelete.hashCode() : 0);
        result = 31 * result + (pawAdd != null ? pawAdd.hashCode() : 0);
        result = 31 * result + (pawEdit != null ? pawEdit.hashCode() : 0);
        result = 31 * result + (pawDelete != null ? pawDelete.hashCode() : 0);
        result = 31 * result + (groupAdd != null ? groupAdd.hashCode() : 0);
        result = 31 * result + (groupEdit != null ? groupEdit.hashCode() : 0);
        result = 31 * result + (groupDelete != null ? groupDelete.hashCode() : 0);
        return result;
    }
}