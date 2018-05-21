package json;

import model.ResourceEntity;

import java.sql.Timestamp;

public class ResourceResponse{
    public int id;
    public String name;
    public String ip;
    public String description;
    public String url;
    public String location;
    public Integer pwdPolicy;
    public String superkey;
    public int typeId;
    public Timestamp createTime;
    public Byte isDeleted;
    public ResourceResponse(ResourceEntity resourceEntity){
        this.id=resourceEntity.getId();
        this.createTime=resourceEntity.getCreateTime();
        this.description=resourceEntity.getDescription();
        this.ip=resourceEntity.getIp();
        this.isDeleted=resourceEntity.getIsDeleted();
        this.location=resourceEntity.getLocation();
        this.name=resourceEntity.getName();
        this.url=resourceEntity.getUrl();
        this.pwdPolicy=resourceEntity.getPwdPolicy();
        this.superkey=resourceEntity.getSuperkey();
        this.typeId=resourceEntity.getTypeId();
    }
}
