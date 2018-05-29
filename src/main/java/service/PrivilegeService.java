package service;

import model.PrivilegeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PrivilegeRepository;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;

    public PrivilegeEntity update(PrivilegeEntity privilegeEntity){
        return privilegeRepository.saveAndFlush(privilegeEntity);
    }

    public PrivilegeEntity findById(int id){
        return privilegeRepository.findById(id);
    }
}
