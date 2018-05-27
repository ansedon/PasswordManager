package json;

import model.ResourceEntity;

public class ResourceResponse{
    public int id;
    public String name;
    public String ip;
    public String description;
    public String url;
    public String location;
    public String pwdPolicy;
    public String superkey;
    public int typeId;
    public String typeName;
    public String createTime;
    public Byte isDeleted;
    public ResourceResponse(ResourceEntity resourceEntity){
        this.id=resourceEntity.getId();
        this.createTime=resourceEntity.getCreateTime().toString().substring(0,19);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPwdPolicy() {
        return pwdPolicy;
    }

    public void setPwdPolicy(String pwdPolicy) {
        this.pwdPolicy = pwdPolicy;
    }

    public String getSuperkey() {
        return superkey;
    }

    public void setSuperkey(String superkey) {
        this.superkey = superkey;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}
