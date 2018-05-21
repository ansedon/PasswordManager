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

    public List<ResourceEntity> getResourceEntitiesByGroupId(int groupId) {
        return resourceRepository.getResourceEntitiesByGroupId(groupId);
    }

    public List<ResourceEntity> findByConditions(ResourceParam param) {
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<ResourceEntity> resourceEntityPage = resourceRepository.findAll(new Specification<ResourceEntity>() {
            @Override
            public Predicate toPredicate(Root<ResourceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.groupId != 0) {
                    Join<ResourceEntity, ResourceGroupEntity> join = root.join("resourceGroupsById", JoinType.LEFT);
                    list.add(criteriaBuilder.equal(join.get("groupId"), param.groupId));
                }
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), param.name));
                }
                if (param.typeId != 0) {
                    list.add(criteriaBuilder.equal(root.get("typeId").as(Integer.class), param.typeId));
                }
                if (param.startTime != null && param.endTime != null) {
                    list.add(criteriaBuilder.between(root.get("createTime").as(Timestamp.class), param.startTime, param.endTime));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class),0));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return resourceEntityPage.getContent();
    }
}
