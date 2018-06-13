package repository;

import model.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupEntity,Integer>{
    UserGroupEntity findByUserId(int userId);

    @Transactional
    @Modifying
    @Query("update UserGroupEntity user set user.groupId=?2 where user.userId=?1")
    void update(int userId,int groupId);

    int countAllByGroupId(int id);
}
