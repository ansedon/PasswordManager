package service;

import model.ResourceGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ResourceGroupRepository;

@Service
public class ResourceGroupService {
    @Autowired
    ResourceGroupRepository resourceGroupRepository;

    public int deleteByResIdAndGroupId(int resId,int groupId){
        return resourceGroupRepository.deleteByResIdAndGroupId(resId,groupId);
    }

    public ResourceGroupEntity findByResIdAndGroupId(int resId,int groupId){
        return resourceGroupRepository.findByResIdAndGroupId(resId,groupId);
    }

    public ResourceGroupEntity insert(ResourceGroupEntity resourceGroupEntity){
        return resourceGroupRepository.saveAndFlush(resourceGroupEntity);
    }
}
