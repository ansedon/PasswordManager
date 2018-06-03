package json;

import model.OperationEntity;

public class OperationResponse {
    public int id;
    public int pwdId;
    public String originAccount;
    public String originPwd;
    public String modifiedTime;
    public int modifierId;
    public String modifierName;

    public OperationResponse(OperationEntity operationEntity){
        this.id=operationEntity.getId();
        this.pwdId=operationEntity.getPwdId();
        this.originAccount=operationEntity.getOriginAccount();
        this.originPwd=operationEntity.getOriginPwd();
        this.modifiedTime=operationEntity.getModifiedTime().toString().substring(0,19);
        this.modifierId=operationEntity.getModifierId();
    }
}
