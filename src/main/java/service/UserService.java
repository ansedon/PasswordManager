package service;

import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserEntity findByAccount(String account){
        return userRepository.findByAccount(account);
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }
}
