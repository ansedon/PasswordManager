package service;

import json.ParentParam;
import model.ResourceTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.ResourceTypeRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceTypeService {
    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    public List<ResourceTypeEntity> findAll(){
        return resourceTypeRepository.findAll();
    }

    public void update(ResourceTypeEntity resourceTypeEntity){
        resourceTypeRepository.saveAndFlush(resourceTypeEntity);
    }

    public ResourceTypeEntity findResourceTypeEntityById(int id){
        return resourceTypeRepository.findResourceTypeEntityById(id);
    }

    public List<ResourceTypeEntity> findByCondition(ParentParam param){
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<ResourceTypeEntity> resourceTypeEntityPage = resourceTypeRepository.findAll(new Specification<ResourceTypeEntity>() {
            @Override
            public Predicate toPredicate(Root<ResourceTypeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), '%'+param.name+'%'));
                }
                if (param.startTime != null ) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if(param.endTime != null){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class),0));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return resourceTypeEntityPage.getContent();
    }

    public int countByCondition(ParentParam param){
        return(int) resourceTypeRepository.count(new Specification<ResourceTypeEntity>() {
            @Override
            public Predicate toPredicate(Root<ResourceTypeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.startTime != null ) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if(param.endTime != null){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), '%'+param.name+'%'));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class),0));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
    }

    public int deleteResourceTypeById(int id){
        return resourceTypeRepository.deleteResourceTypeById(id);
    }
}
