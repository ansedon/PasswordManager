package json;

import model.ResourceTypeEntity;

public class ResourceTypeResponse {
    public int id;
    public String name;
    public String description;
    public String createTime;

    public ResourceTypeResponse(ResourceTypeEntity resourceTypeEntity){
        this.id=resourceTypeEntity.getId();
        this.name=resourceTypeEntity.getName();
        this.createTime=resourceTypeEntity.getCreateTime().toString().substring(0,19);
        this.description=resourceTypeEntity.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
