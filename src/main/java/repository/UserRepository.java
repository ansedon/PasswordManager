package repository;

import model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity>{
    List<UserEntity> findAllByIsDeleted(byte value);

    UserEntity findByAccountAndIsDeleted(String account,byte value);

    UserEntity findUserEntityByIdAndIsDeleted(int id,byte value);

    @Modifying
    @Transactional
    @Query("update UserEntity user set user.isDeleted=1 where user.id=?1")
    int deleteUserById(int id);

    int countAllByIsDeleted(byte value);
}
