package repository;

import model.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity,Integer>{
    List<OperationEntity> findAllByPawIdOrderByModifiedTimeDesc(int id);
}
