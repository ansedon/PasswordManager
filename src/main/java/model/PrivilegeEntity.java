package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "privilege", schema = "passworddata", catalog = "")
public class PrivilegeEntity {
    private int id;
    private Byte resAdd;
    private Byte resEdit;
    private Byte resDelete;
    private Byte pwdAdd;
    private Byte pwdEdit;
    private Byte pwdDelete;
    private Byte groupAdd;
    private Byte groupEdit;
    private Byte groupDelete;
    private Collection<RoleEntity> rolesById;
    private Collection<RoleEntity> rolesById_0;
    private Byte userAdd;
    private Byte userEdit;
    private Byte userDelete;
    private Byte typeAdd;
    private Byte typeEdit;
    private Byte typeDelete;

    public PrivilegeEntity(){

    }

    public PrivilegeEntity(RoleEntity roleEntity){
        this.resAdd=(byte)roleEntity.getResAdd();
        this.resEdit=(byte)roleEntity.getResEdit();
        this.resDelete=(byte)roleEntity.getResDelete();
        this.typeAdd=(byte)roleEntity.getTypeAdd();
        this.typeEdit=(byte)roleEntity.getTypeEdit();
        this.typeDelete=(byte)roleEntity.getTypeDelete();
        this.pwdAdd=(byte)roleEntity.getPwdAdd();
        this.pwdEdit=(byte)roleEntity.getPwdEdit();
        this.pwdDelete=(byte)roleEntity.getPwdDelete();
        this.userAdd=(byte)roleEntity.getUserAdd();
        this.userEdit=(byte)roleEntity.getUserEdit();
        this.userDelete=(byte)roleEntity.getUserDelete();
        this.groupAdd=(byte)roleEntity.getGroupAdd();
        this.groupEdit=(byte)roleEntity.getGroupEdit();
        this.groupDelete=(byte)roleEntity.getGroupDelete();
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
    @Column(name = "pwd_add", nullable = true)
    public Byte getPwdAdd() {
        return pwdAdd;
    }

    public void setPwdAdd(Byte pwdAdd) {
        this.pwdAdd = pwdAdd;
    }

    @Basic
    @Column(name = "pwd_edit", nullable = true)
    public Byte getPwdEdit() {
        return pwdEdit;
    }

    public void setPwdEdit(Byte pwdEdit) {
        this.pwdEdit = pwdEdit;
    }

    @Basic
    @Column(name = "pwd_delete", nullable = true)
    public Byte getPwdDelete() {
        return pwdDelete;
    }

    public void setPwdDelete(Byte pwdDelete) {
        this.pwdDelete = pwdDelete;
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
        if (pwdAdd != null ? !pwdAdd.equals(that.pwdAdd) : that.pwdAdd != null) return false;
        if (pwdEdit != null ? !pwdEdit.equals(that.pwdEdit) : that.pwdEdit != null) return false;
        if (pwdDelete != null ? !pwdDelete.equals(that.pwdDelete) : that.pwdDelete != null) return false;
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
        result = 31 * result + (pwdAdd != null ? pwdAdd.hashCode() : 0);
        result = 31 * result + (pwdEdit != null ? pwdEdit.hashCode() : 0);
        result = 31 * result + (pwdDelete != null ? pwdDelete.hashCode() : 0);
        result = 31 * result + (groupAdd != null ? groupAdd.hashCode() : 0);
        result = 31 * result + (groupEdit != null ? groupEdit.hashCode() : 0);
        result = 31 * result + (groupDelete != null ? groupDelete.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "privilegeByPrivilegeId")
    public Collection<RoleEntity> getRolesById() {
        return rolesById;
    }

    public void setRolesById(Collection<RoleEntity> rolesById) {
        this.rolesById = rolesById;
    }

    @OneToMany(mappedBy = "privilegeByPrivilegeId_0")
    public Collection<RoleEntity> getRolesById_0() {
        return rolesById_0;
    }

    public void setRolesById_0(Collection<RoleEntity> rolesById_0) {
        this.rolesById_0 = rolesById_0;
    }

    @Basic
    @Column(name = "user_add", nullable = true)
    public Byte getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(Byte userAdd) {
        this.userAdd = userAdd;
    }

    @Basic
    @Column(name = "user_edit", nullable = true)
    public Byte getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(Byte userEdit) {
        this.userEdit = userEdit;
    }

    @Basic
    @Column(name = "user_delete", nullable = true)
    public Byte getUserDelete() {
        return userDelete;
    }

    public void setUserDelete(Byte userDelete) {
        this.userDelete = userDelete;
    }

    @Basic
    @Column(name = "type_add", nullable = true)
    public Byte getTypeAdd() {
        return typeAdd;
    }

    public void setTypeAdd(Byte typeAdd) {
        this.typeAdd = typeAdd;
    }

    @Basic
    @Column(name = "type_edit", nullable = true)
    public Byte getTypeEdit() {
        return typeEdit;
    }

    public void setTypeEdit(Byte typeEdit) {
        this.typeEdit = typeEdit;
    }

    @Basic
    @Column(name = "type_delete", nullable = true)
    public Byte getTypeDelete() {
        return typeDelete;
    }

    public void setTypeDelete(Byte typeDelete) {
        this.typeDelete = typeDelete;
    }
}
