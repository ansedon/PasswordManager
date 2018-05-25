package repository;

import model.ResourceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceTypeEntity,Integer>,JpaSpecificationExecutor<ResourceTypeEntity>{
    List<ResourceTypeEntity> findAllByIsDeleted(byte value);

    ResourceTypeEntity findResourceTypeEntityById(int id);

    @Modifying
    @Transactional
    @Query("update ResourceTypeEntity res set res.isDeleted=1 where res.id=?1")
    int deleteResourceTypeById(int id);
}
