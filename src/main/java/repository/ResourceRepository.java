package repository;

import model.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity,Integer>,JpaSpecificationExecutor<ResourceEntity>{
    @Query("select resource from ResourceEntity resource left join ResourceGroupEntity  resourcegroup on resource.id=resourcegroup.resId where resource.isDeleted=0 and resourcegroup.groupId=?1")
    List<ResourceEntity> getResourceEntitiesByGroupId(int groupId);

    @Query("select distinct resource from ResourceEntity resource left join ResourceGroupEntity  resourcegroup on resource.id=resourcegroup.resId where resource.isDeleted=0 and resourcegroup.groupId in ?1")
    List<ResourceEntity> getResourceEntitiesByGroupIds(List<Integer> groupIds);

    @Modifying
    @Transactional
    @Query("update ResourceEntity res set res.isDeleted=1 where res.id=?1")
    int deleteResourceById(int id);

    ResourceEntity findResourceEntityById(int id);

    List<ResourceEntity> findAllByIsDeleted(byte value);

    int countAllByIsDeleted(byte value);

    int countAllByTypeIdAndIsDeleted(int id,byte value);
}
