package service;

import model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public RoleEntity getRoleEntityById(int id){
        return roleRepository.getRoleEntityById(id);
    }
}
