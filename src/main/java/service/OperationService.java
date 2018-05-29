package service;

import model.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.OperationRepository;

import java.util.List;

@Service
public class OperationService {
    @Autowired
    OperationRepository operationRepository;

    public void update(OperationEntity operationEntity){
        operationRepository.saveAndFlush(operationEntity);
    }

    public List<OperationEntity> findByPawId(int id){
        return operationRepository.findAllByPawIdOrderByModifiedTimeDesc(id);
    }
}
