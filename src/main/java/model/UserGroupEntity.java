package model;

import javax.persistence.*;

@Entity
@Table(name = "user_group", schema = "passworddata", catalog = "")
@IdClass(UserGroupEntityPK.class)
public class UserGroupEntity {
    private int userId;
    private int groupId;
    private UserEntity userByUserId;
    private UserEntity userByUserId_0;
    private UserEntity userByUserId_1;
    private CpGroupEntity cpGroupByGroupId;
    private CpGroupEntity cpGroupByGroupId_0;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

        UserGroupEntity that = (UserGroupEntity) o;

        if (userId != that.userId) return false;
        if (groupId != that.groupId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + groupId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByUserId_0() {
        return userByUserId_0;
    }

    public void setUserByUserId_0(UserEntity userByUserId_0) {
        this.userByUserId_0 = userByUserId_0;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public UserEntity getUserByUserId_1() {
        return userByUserId_1;
    }

    public void setUserByUserId_1(UserEntity userByUserId_1) {
        this.userByUserId_1 = userByUserId_1;
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
