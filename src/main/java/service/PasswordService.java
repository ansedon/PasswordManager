package service;

import json.PasswordParam;
import model.PasswordEntity;
import model.ResourceEntity;
import model.ResourceGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.PasswordRepository;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordService {
    @Autowired
    PasswordRepository passwordRepository;

    public int countExpired(){
        return passwordRepository.countAllByIsDeletedAndExpireTimeBefore((byte)0,new Timestamp(System.currentTimeMillis()));
    }

    public int countAll(int isDeleted) {
        return passwordRepository.countAllByIsDeleted((byte) isDeleted);
    }

    public void update(PasswordEntity passwordEntity) {
        passwordRepository.saveAndFlush(passwordEntity);
    }

    public PasswordEntity findPasswordEntityById(int id) {
        return passwordRepository.findPasswordEntityById(id);
    }

    public List<PasswordEntity> findByCondition(PasswordParam param) {
        Pageable pageable;
        if (param.isDeleted == 1)
            pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "modifiedTime"));
        else
            pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<PasswordEntity> passwordEntityPage = passwordRepository.findAll(new Specification<PasswordEntity>() {
            @Override
            public Predicate toPredicate(Root<PasswordEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.groupId != 0 && param.groupId != 1) {
                    Join<PasswordEntity, ResourceEntity> join = root.join("resourceByResourceId", JoinType.LEFT);
                    Join<ResourceEntity, ResourceGroupEntity> join1 = join.join("resourceGroupsById", JoinType.LEFT);
                    list.add(criteriaBuilder.equal(join1.get("groupId"), param.groupId));
                }
                if (param.resId != 0) {
                    list.add(criteriaBuilder.equal(root.get("resourceId").as(Integer.class), param.resId));
                }
                if (param.expireTime != null) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expireTime").as(Timestamp.class), param.expireTime));
                }
                if (param.account != null && param.account != "") {
                    list.add(criteriaBuilder.like(root.get("account").as(String.class), '%' + param.account + '%'));
                }
                if (param.isDeleted == 1) {
                    if (param.startTime != null) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("modifiedTime").as(Timestamp.class), param.startTime));
                    }
                    if (param.endTime != null) {
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("modifiedTime").as(Timestamp.class), param.endTime));
                    }
                } else {
                    if (param.startTime != null) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                    }
                    if (param.endTime != null) {
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                    }
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), (byte) param.isDeleted));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return passwordEntityPage.getContent();
    }

    public int countByCondition(PasswordParam param) {
        return (int) passwordRepository.count(new Specification<PasswordEntity>() {
            @Override
            public Predicate toPredicate(Root<PasswordEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.groupId != 0 && param.groupId != 1) {
                    Join<PasswordEntity, ResourceEntity> join = root.join("resourceByResourceId", JoinType.LEFT);
                    Join<ResourceEntity, ResourceGroupEntity> join1 = join.join("resourceGroupsById", JoinType.LEFT);
                    list.add(criteriaBuilder.equal(join1.get("groupId"), param.groupId));
                }
                if (param.resId != 0) {
                    list.add(criteriaBuilder.equal(root.get("resourceId").as(Integer.class), param.resId));
                }
                if (param.expireTime != null) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expireTime").as(Timestamp.class), param.expireTime));
                }
                if (param.account != null && param.account != "") {
                    list.add(criteriaBuilder.like(root.get("account").as(String.class), '%' + param.account + '%'));
                }
                if (param.isDeleted == 1) {
                    if (param.endTime != null) {
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("modifiedTime").as(Timestamp.class), param.endTime));
                    }
                    if (param.startTime != null) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("modifiedTime").as(Timestamp.class), param.startTime));
                    }
                } else {
                    if (param.startTime != null) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                    }
                    if (param.endTime != null) {
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                    }
                }
                if(param.isExpired==1){
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("expireTime").as(Timestamp.class),new Timestamp(System.currentTimeMillis())));
                }
                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), (byte) param.isDeleted));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
    }

    public int deletePasswordById(int id) {
        return passwordRepository.deletePasswordById(id);
    }

    public int retrivePasswordById(int id) {
        return passwordRepository.retirvePasswordById(id);
    }
}
