package repository;

import model.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity,Integer>,JpaSpecificationExecutor<PasswordEntity>{
    PasswordEntity findPasswordEntityById(int id);

    @Modifying
    @Transactional
    @Query("update PasswordEntity ps set ps.isDeleted=1,ps.modifiedTime=current_timestamp where ps.id=?1")
    int deletePasswordById(int id);

    @Modifying
    @Transactional
    @Query("update PasswordEntity ps set ps.isDeleted=0 where ps.id=?1")
    int retirvePasswordById(int id);

    int countAllByIsDeleted(byte value);

    int countAllByIsDeletedAndExpireTimeBefore(byte isDeleted, Timestamp time);

}
