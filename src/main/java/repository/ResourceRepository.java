package repository;

import model.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity,Integer>,JpaSpecificationExecutor<ResourceEntity>{
    @Query("select resource from ResourceEntity resource left join ResourceGroupEntity  resourcegroup on resource.id=resourcegroup.resId where resourcegroup.groupId=?1")
    List<ResourceEntity> getResourceEntitiesByGroupId(int groupId);
}
