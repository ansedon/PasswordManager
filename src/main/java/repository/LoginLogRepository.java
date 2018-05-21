package repository;

import model.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLogEntity,Integer>{
    LoginLogEntity findDistinctFirstByUserIdOrderByCreateTimeDesc(int userId);


}
