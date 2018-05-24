package repository;

import model.CpGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<CpGroupEntity,Integer>,JpaSpecificationExecutor<CpGroupEntity>{
    CpGroupEntity findCpGroupEntityById(int id);

    List<CpGroupEntity> findAllByFatherGroupId(int id);

    @Modifying
    @Transactional
    @Query("update CpGroupEntity g set g.isDeleted=1 where g.id=?1")
    int deleteGroupById(int id);
}
