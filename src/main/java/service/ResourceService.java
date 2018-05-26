package service;

import json.ResourceParam;
import model.ResourceEntity;
import model.ResourceGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.ResourceRepository;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    public int countAll(){
        return resourceRepository.countAllByIsDeleted((byte)0);
    }

    public void update(ResourceEntity resourceEntity){
        resourceRepository.saveAndFlush(resourceEntity);
    }

    public List<ResourceEntity> getResourceEntitiesByGroupId(int groupId) {
        return resourceRepository.getResourceEntitiesByGroupId(groupId);
    }

    public List<ResourceEntity> findAll(){
        return resourceRepository.findAllByIsDeleted((byte)0);
    }

    public List<ResourceEntity> getResourceEntitiesByGroupIds(List<Integer> groupIds) {
        return resourceRepository.getResourceEntitiesByGroupIds(groupIds);
    }

    public int deleteResourceById(int id){
        return resourceRepository.deleteResourceById(id);
    }

    public List<ResourceEntity> findByCondition(ResourceParam param) {
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<ResourceEntity> resourceEntityPage = resourceRepository.findAll(new Specification<ResourceEntity>() {
            @Override
            public Predicate toPredicate(Root<ResourceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if(param.resIds!=null&&param.resIds.size()>0){
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
                    for (Integer id : param.resIds) {
                        in.value(id);
                    }
                    list.add(criteriaBuilder.not(in));
                }
                if (param.groupId != 0&&param.groupId!=1) {
                    Join<ResourceEntity, ResourceGroupEntity> join = root.join("resourceGroupsById", JoinType.LEFT);
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(join.get("groupId"));
                    for (Integer id : param.groupList) {
                        in.value(id);
                    }
                    list.add(in);
                }
                if (param.typeId != 0) {
                    list.add(criteriaBuilder.equal(root.get("typeId").as(Integer.class), param.typeId));
                }
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
        return resourceEntityPage.getContent();
    }

    public int countByCondition(ResourceParam param) {
        return (int) resourceRepository.count(new Specification<ResourceEntity>() {
            @Override
            public Predicate toPredicate(Root<ResourceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                criteriaQuery.distinct(true);
                if (param.groupId != 0&&param.groupId!=1) {
                    Join<ResourceEntity, ResourceGroupEntity> join = root.join("resourceGroupsById", JoinType.LEFT);
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(join.get("groupId"));
                    for (Integer id : param.groupList) {
                        in.value(id);
                    }
                    list.add(in);
                }
                if(param.resIds!=null&&param.resIds.size()>0){
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
                    for (Integer id : param.resIds) {
                        in.value(id);
                    }
                    list.add(criteriaBuilder.not(in));
                }
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), '%'+param.name+'%'));
                }
                if (param.typeId != 0) {
                    list.add(criteriaBuilder.equal(root.get("typeId").as(Integer.class), param.typeId));
                }
                if (param.startTime != null ) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if(param.endTime != null){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), 0));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
    }

    public ResourceEntity findResourceEntityById(int id){
        return resourceRepository.findResourceEntityById(id);
    }
}
