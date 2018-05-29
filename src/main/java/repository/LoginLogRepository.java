package repository;

import model.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLogEntity,Integer>,JpaSpecificationExecutor<LoginLogEntity>{
    LoginLogEntity findDistinctFirstByUserIdOrderByCreateTimeDesc(int userId);
}
