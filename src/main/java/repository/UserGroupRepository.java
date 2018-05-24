package repository;

import model.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupEntity,Integer>{
    UserGroupEntity findByUserId(int userId);
}
