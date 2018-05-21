package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "operation", schema = "passworddata", catalog = "")
public class OperationEntity {
    private int id;
    private int pawId;
    private String originAccount;
    private String originPaw;
    private Timestamp modifiedTime;
    private int modifierId;
    private PasswordEntity passwordByPawId;
    private PasswordEntity passwordByPawId_0;
    private UserEntity userByModifierId;
    private UserEntity userByModifierId_0;
    private UserEntity userByModifierId_1;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "paw_id", nullable = false)
    public int getPawId() {
        return pawId;
    }

    public void setPawId(int pawId) {
        this.pawId = pawId;
    }

    @Basic
    @Column(name = "origin_account", nullable = false, length = 50)
    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    @Basic
    @Column(name = "origin_paw", nullable = false, length = 200)
    public String getOriginPaw() {
        return originPaw;
    }

    public void setOriginPaw(String originPaw) {
        this.originPaw = originPaw;
    }

    @Basic
    @Column(name = "modified_time", nullable = false)
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Basic
    @Column(name = "modifier_id", nullable = false)
    public int getModifierId() {
        return modifierId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationEntity that = (OperationEntity) o;

        if (id != that.id) return false;
        if (pawId != that.pawId) return false;
        if (modifierId != that.modifierId) return false;
        if (originAccount != null ? !originAccount.equals(that.originAccount) : that.originAccount != null)
            return false;
        if (originPaw != null ? !originPaw.equals(that.originPaw) : that.originPaw != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + pawId;
        result = 31 * result + (originAccount != null ? originAccount.hashCode() : 0);
        result = 31 * result + (originPaw != null ? originPaw.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        result = 31 * result + modifierId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "paw_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public PasswordEntity getPasswordByPawId() {
        return passwordByPawId;
    }

    public void setPasswordByPawId(PasswordEntity passwordByPawId) {
        this.passwordByPawId = passwordByPawId;
    }

    @ManyToOne
    @JoinColumn(name = "paw_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public PasswordEntity getPasswordByPawId_0() {
        return passwordByPawId_0;
    }

    public void setPasswordByPawId_0(PasswordEntity passwordByPawId_0) {
        this.passwordByPawId_0 = passwordByPawId_0;
    }

    @ManyToOne
    @JoinColumn(name = "modifier_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByModifierId() {
        return userByModifierId;
    }

    public void setUserByModifierId(UserEntity userByModifierId) {
        this.userByModifierId = userByModifierId;
    }

    @ManyToOne
    @JoinColumn(name = "modifier_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByModifierId_0() {
        return userByModifierId_0;
    }

    public void setUserByModifierId_0(UserEntity userByModifierId_0) {
        this.userByModifierId_0 = userByModifierId_0;
    }

    @ManyToOne
    @JoinColumn(name = "modifier_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByModifierId_1() {
        return userByModifierId_1;
    }

    public void setUserByModifierId_1(UserEntity userByModifierId_1) {
        this.userByModifierId_1 = userByModifierId_1;
    }
}
