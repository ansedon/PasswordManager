package json;

import model.CpGroupEntity;

public class GroupResponse {
    public int id;
    public String name;
    public String description;
    public String location;
    public Integer fatherGroupId;
    public String fatherGroupName;
    public int creatorId;
    public String creatorName;
    public String createTime;
    public String modifiedTime;
    public Integer modifierId;

    public GroupResponse(CpGroupEntity cpGroupEntity){
        this.id= cpGroupEntity.getId();
        this.name= cpGroupEntity.getName();
        this.description= cpGroupEntity.getDescription();
        this.location=cpGroupEntity.getLocation();
        this.fatherGroupId=cpGroupEntity.getFatherGroupId();
        this.createTime= cpGroupEntity.getCreateTime().toString().substring(0,19);
        this.creatorId= cpGroupEntity.getCreatorId();
        this.modifiedTime= cpGroupEntity.getModifiedTime().toString().substring(0,19);
        this.modifierId= cpGroupEntity.getModifierId();
    }
}
