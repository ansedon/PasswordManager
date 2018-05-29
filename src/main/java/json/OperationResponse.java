package json;

import model.OperationEntity;

public class OperationResponse {
    public int id;
    public int pawId;
    public String originAccount;
    public String originPaw;
    public String modifiedTime;
    public int modifierId;
    public String modifierName;

    public OperationResponse(OperationEntity operationEntity){
        this.id=operationEntity.getId();
        this.pawId=operationEntity.getPawId();
        this.originAccount=operationEntity.getOriginAccount();
        this.originPaw=operationEntity.getOriginPaw();
        this.modifiedTime=operationEntity.getModifiedTime().toString().substring(0,19);
        this.modifierId=operationEntity.getModifierId();
    }
}
