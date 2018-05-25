package repository;

import model.ResourceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ResourceGroupRepository extends JpaRepository<ResourceGroupEntity,Integer>{
    ResourceGroupEntity findByResIdAndGroupId(int resId,int groupId);

    @Transactional
    @Modifying
    @Query("delete from ResourceGroupEntity res where res.resId=?1 and res.groupId=?2")
    int deleteByResIdAndGroupId(int resId,int group);
}
