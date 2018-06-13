package service;

import model.UserGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserGroupRepository;

@Service
public class UserGroupService {
    @Autowired
    UserGroupRepository userGroupRepository;

    public int countAllByGroupId(int id){
        return userGroupRepository.countAllByGroupId(id);
    }

    public UserGroupEntity findByUserId(int userId){
        return userGroupRepository.findByUserId(userId);
    }

    public void update(UserGroupEntity userGroupEntity){
        userGroupRepository.update(userGroupEntity.getUserId(),userGroupEntity.getGroupId());
    }

    public  UserGroupEntity insert(UserGroupEntity userGroupEntity){
        return userGroupRepository.saveAndFlush(userGroupEntity);
    }
}
