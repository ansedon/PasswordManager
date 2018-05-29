package repository;

import model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer>,JpaSpecificationExecutor<RoleEntity>{
    RoleEntity findById(int id);

    @Modifying
    @Transactional
    @Query("update RoleEntity role set role.isDeleted=1 where role.id=?1")
    int deleteRoleById(int id);

    List<RoleEntity> findAllByLevelGreaterThan(int level);
}
