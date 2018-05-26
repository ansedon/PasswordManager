package json;

import model.RoleEntity;

public class RoleResponse {
    public int id;
    public String name;
    public String description;
    public int level;
    public String createTime;

    public RoleResponse(RoleEntity roleEntity){
        this.id=roleEntity.getId();
        this.name=roleEntity.getName();
        this.description=roleEntity.getDescription();
        this.level=roleEntity.getLevel();
        this.createTime=roleEntity.getCreateTime().toString().substring(0,19);
    }
}
