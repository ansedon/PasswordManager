package json;

import model.PasswordEntity;

public class PasswordResponse {
    public int id;
    public String account;
    public String password;
    public String createTime;
    public int creatorId;
    public String creatorName;
    public String modifiedTime;
    public Integer modifierId;
    public String modifierName;
    public String expireTime;
    public int resourceId;
    public String resourceName;

    public PasswordResponse(PasswordEntity passwordEntity){
        this.id=passwordEntity.getId();
        this.account=passwordEntity.getAccount();
        this.password=passwordEntity.getPassword();
        this.createTime=passwordEntity.getCreateTime().toString().substring(0,19);
        this.creatorId=passwordEntity.getCreatorId();
        this.modifiedTime=passwordEntity.getModifiedTime().toString().substring(0,19);
        this.modifierId=passwordEntity.getModifierId();
        if(passwordEntity.getExpireTime()!=null)
            this.expireTime=passwordEntity.getExpireTime().toString().substring(0,19);
        this.resourceId=passwordEntity.getResourceId();
    }
}
