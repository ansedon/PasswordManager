package service;

import json.UserParam;
import model.UserEntity;
import model.UserGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public int countAll(){
        return userRepository.countAllByIsDeleted((byte)0);
    }

    public UserEntity findByAccount(String account) {
        return userRepository.findByAccountAndIsDeleted(account, (byte) 0);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAllByIsDeleted((byte) 0);
    }

    public UserEntity update(UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    public UserEntity findUserEntityById(int id) {
        return userRepository.findUserEntityByIdAndIsDeleted(id, (byte) 0);
    }

    public List<UserEntity> findByCondition(UserParam param) {
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<UserEntity> userEntityPage = userRepository.findAll(new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.groupId != 0 && param.groupId != 1) {
                    Join<UserEntity, UserGroupEntity> join = root.join("userGroupsById", JoinType.LEFT);
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(join.get("groupId"));
                    for (Integer id : param.ids) {
                        in.value(id);
                    }
                    list.add(in);
                }
                if(param.roleId!=0){
                    list.add(criteriaBuilder.equal(root.get("roleId"),param.roleId));
                }
                if (param.account != null && param.account != "") {
                    list.add(criteriaBuilder.like(root.get("account").as(String.class), '%' + param.account + '%'));
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
        return userEntityPage.getContent();
    }

    public int countByCondition(UserParam param) {
        return (int) userRepository.count(new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.account != null && param.account != "") {
                    list.add(criteriaBuilder.like(root.get("account").as(String.class), '%' + param.account + '%'));
                }
                if (param.groupId != 0 && param.groupId != 1) {
                    Join<UserEntity, UserGroupEntity> join = root.join("userGroupsById", JoinType.LEFT);
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(join.get("groupId"));
                    for (Integer id : param.ids) {
                        in.value(id);
                    }
                    list.add(in);
                }
                if(param.roleId!=0){
                  list.add(criteriaBuilder.equal(root.get("roleId"),param.roleId));
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

    public int deleteUserById(int id) {
        return userRepository.deleteUserById(id);
    }
}
