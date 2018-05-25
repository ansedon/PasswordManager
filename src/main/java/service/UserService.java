package service;

import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserEntity findByAccount(String account){
        return userRepository.findByAccountAndIsDeleted(account,(byte)0);
    }

    public List<UserEntity> findAll(){
        return userRepository.findAllByIsDeleted((byte)0);
    }

    public void update(UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
    }

    public UserEntity findUserEntityById(int id) {
        return userRepository.findUserEntityByIdAndIsDeleted(id,(byte)0);
    }

//    public List<UserEntity> findByCondition(UserParam param) {
//        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
//        Page<UserEntity> userEntityPage = userRepository.findAll(new Specification<UserEntity>() {
//            @Override
//            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//  criteriaQuery.distinct(true);
// List<Predicate> list = new ArrayList<Predicate>();
//                if (param.id != 0) {
//                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
//                }
//                if (param.groupId != 0&&param.groupId!=1) {
//                    Join<UserEntity, ResourceEntity> join = root.join("resourceByResourceId", JoinType.LEFT);
//                    Join<ResourceEntity, ResourceGroupEntity> join1 = join.join("resourceGroupsById", JoinType.LEFT);
//                    list.add(criteriaBuilder.equal(join1.get("groupId"), param.groupId));
//                    list.add(criteriaBuilder.or(join1.get("groupId").equals(param.id),));
//                }
//                if (param.resId != 0) {
//                    list.add(criteriaBuilder.equal(root.get("resourceId").as(Integer.class), param.resId));
//                }
//                if (param.expireTime != null) {
//                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expireTime").as(Timestamp.class), param.expireTime));
//                }
//                if (param.account != null && param.account != "") {
//                    list.add(criteriaBuilder.like(root.get("account").as(String.class), '%' + param.account + '%'));
//                }
//                if (param.startTime != null) {
//                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
//                }
//                if (param.endTime != null) {
//                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
//                }
//                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), 0));
//                Predicate[] p = new Predicate[list.size()];
//                return criteriaBuilder.and(list.toArray(p));
//            }
//        }, pageable);
//        return userEntityPage.getContent();
//    }
//
//    public int countByCondition(UserParam param) {
//        return (int) userRepository.count(new Specification<UserEntity>() {
//            @Override
//            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//  criteriaQuery.distinct(true);
// List<Predicate> list = new ArrayList<Predicate>();
//                if (param.id != 0) {
//                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
//                }
//                if (param.groupId != 0&&param.groupId!=1) {
//                    Join<UserEntity, ResourceEntity> join = root.join("resourceByResourceId", JoinType.LEFT);
//                    Join<ResourceEntity, ResourceGroupEntity> join1 = join.join("resourceGroupsById", JoinType.LEFT);
//                    list.add(criteriaBuilder.equal(join1.get("groupId"), param.groupId));
//                }
//                if (param.resId != 0) {
//                    list.add(criteriaBuilder.equal(root.get("resourceId").as(Integer.class), param.resId));
//                }
//                if (param.expireTime != null) {
//                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expireTime").as(Timestamp.class), param.expireTime));
//                }
//                if (param.account != null && param.account != "") {
//                    list.add(criteriaBuilder.like(root.get("account").as(String.class), '%' + param.account + '%'));
//                }
//                if (param.endTime != null) {
//                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
//                }
//                if (param.startTime != null) {
//                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
//                }
//                list.add(criteriaBuilder.equal(root.get("isDeleted").as(Byte.class), 0));
//                Predicate[] p = new Predicate[list.size()];
//                return criteriaBuilder.and(list.toArray(p));
//            }
//        });
//    }

    public int deleteUserById(int id) {
        return userRepository.deleteUserById(id);
    }
}
