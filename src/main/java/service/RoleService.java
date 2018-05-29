package service;

import json.RoleParam;
import model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.RoleRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public RoleEntity getRoleEntityById(int id){
        return roleRepository.findById(id);
    }

    public void update(RoleEntity roleEntity) {
        roleRepository.saveAndFlush(roleEntity);
    }

    public List<RoleEntity> findByCondition(RoleParam param) {
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<RoleEntity> roleEntityPage = roleRepository.findAll(new Specification<RoleEntity>() {
            @Override
            public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.level != 0) {
                    if(param.level==1)
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), param.level));
                    else
                        list.add(criteriaBuilder.greaterThan(root.get("level"), param.level));
                }
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), '%' + param.name + '%'));
                }
                if (param.startTime != null) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if (param.endTime != null) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), 0));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return roleEntityPage.getContent();
    }

    public int countByCondition(RoleParam param) {
        return (int) roleRepository.count(new Specification<RoleEntity>() {
            @Override
            public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), '%' + param.name + '%'));
                }
                if (param.level != 0) {
                    if(param.level==1)
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), param.level));
                    else
                        list.add(criteriaBuilder.greaterThan(root.get("level"), param.level));
                }
                if (param.startTime != null) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if (param.endTime != null) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), 0));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
    }

    public List<RoleEntity> findAllByLevelGreaterThan(int level){
        return roleRepository.findAllByLevelGreaterThan(level);
    }

    public int deleteRoleById(int id) {
        return roleRepository.deleteRoleById(id);
    }
}
