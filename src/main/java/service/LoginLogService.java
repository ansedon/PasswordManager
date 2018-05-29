package service;

import json.ParentParam;
import model.LoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import repository.LoginLogRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginLogService {
    @Autowired
    LoginLogRepository loginLogRepository;

    public List<LoginLogEntity> findByCondition(ParentParam param) {
        Pageable pageable = new PageRequest(param.page, param.limit, new Sort(Sort.Direction.DESC, "createTime"));
        Page<LoginLogEntity> passwordEntityPage = loginLogRepository.findAll(new Specification<LoginLogEntity>() {
            @Override
            public Predicate toPredicate(Root<LoginLogEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), param.id));
                }
                if (param.startTime != null) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if (param.endTime != null) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return passwordEntityPage.getContent();
    }

    public int countByCondition(ParentParam param) {
        return (int) loginLogRepository.count(new Specification<LoginLogEntity>() {
            @Override
            public Predicate toPredicate(Root<LoginLogEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.distinct(true);
                List<Predicate> list = new ArrayList<Predicate>();
                if (param.startTime != null) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.startTime));
                }
                if (param.id != 0) {
                    list.add(criteriaBuilder.equal(root.get("id").as(Integer.class), param.id));
                }
                if (param.endTime != null) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class), param.endTime));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
    }

    public LoginLogEntity findOneByUserId(int userId) {
        return loginLogRepository.findDistinctFirstByUserIdOrderByCreateTimeDesc(userId);
    }

    public LoginLogEntity insert(LoginLogEntity loginLogEntity) {
        return loginLogRepository.saveAndFlush(loginLogEntity);
    }
}
