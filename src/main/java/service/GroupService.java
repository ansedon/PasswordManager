package service;

import json.GroupParam;
import model.CpGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.GroupRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    public List<CpGroupEntity> findAll(){
        return groupRepository.findAllByIsDeleted((byte)0);
    }

    public void update(CpGroupEntity groupEntity) {
        groupRepository.saveAndFlush(groupEntity);
    }

    public CpGroupEntity findGroupById(int id){
        return groupRepository.findCpGroupEntityById(id);
    }

    public List<CpGroupEntity> findAllByFatherGroupIds(List<Integer>ids){
        return groupRepository.findAllByFatherGroupIdInAndIsDeleted(ids,(byte)0);
    }

    public List<CpGroupEntity> findByCondition(GroupParam param) {
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<CpGroupEntity> groupEntityPage = groupRepository.findAll(new Specification<CpGroupEntity>() {
            @Override
            public Predicate toPredicate(Root<CpGroupEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if(param.ids!=null&&param.ids.size()>0){
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
                    for (Integer id : param.ids) {
                        in.value(id);
                    }
                    list.add(in);
                }
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.name != null && param.name != "") {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), '%' + param.name + '%'));
                }
                if (param.fatherGroupId!= 0) {
                    list.add(criteriaBuilder.equal(root.get("fatherGroupId").as(Integer.class), param.fatherGroupId));
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
        return groupEntityPage.getContent();
    }

    public int countByCondition(GroupParam param) {
        return (int) groupRepository.count(new Specification<CpGroupEntity>() {
            @Override
            public Predicate toPredicate(Root<CpGroupEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.fatherGroupId!= 0) {
                    list.add(criteriaBuilder.equal(root.get("fatherGroupId").as(Integer.class), param.fatherGroupId));
                }
                if(param.ids!=null&&param.ids.size()>0){
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
                        for (Integer id : param.ids) {
                            in.value(id);
                        }
                        list.add(in);
                }
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
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
        });
    }

    public int deleteGroupById(int id) {
        return groupRepository.deleteGroupById(id);
    }
}
