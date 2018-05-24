package service;

import model.UserGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserGroupRepository;

@Service
public class UserGroupService {
    @Autowired
    UserGroupRepository userGroupRepository;

    public UserGroupEntity findByUserId(int userId){
        return userGroupRepository.findByUserId(userId);
    }
}
