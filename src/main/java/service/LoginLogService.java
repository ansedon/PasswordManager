package service;

import model.LoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LoginLogRepository;

@Service
public class LoginLogService {
    @Autowired
    LoginLogRepository loginLogRepository;

    public LoginLogEntity findOneByUserId(int userId){
        return loginLogRepository.findDistinctFirstByUserIdOrderByCreateTimeDesc(userId);
    }

    public LoginLogEntity insert(LoginLogEntity loginLogEntity) {
        return loginLogRepository.saveAndFlush(loginLogEntity);
    }
}
