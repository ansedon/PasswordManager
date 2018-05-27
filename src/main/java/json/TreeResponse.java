package json;

import model.CpGroupEntity;
import model.ResourceTypeEntity;

public class TreeResponse {
        public String id;
        public String parent;
        public String text;
        public TreeState state;

        public TreeResponse(CpGroupEntity groupEntity){
            this.id=String.valueOf(groupEntity.getId());
            if(groupEntity.getFatherGroupId()==1&&groupEntity.getId()==1)
                this.parent="#";
            else
                this.parent=String.valueOf(groupEntity.getFatherGroupId());
            this.text=groupEntity.getName();
        }

        public TreeResponse(ResourceTypeEntity resourceTypeEntity){
            this.id=String.valueOf(resourceTypeEntity.getId());
            if(resourceTypeEntity.getFatherTypeId()<0)
                this.parent="#";
            else
                this.parent=String.valueOf(resourceTypeEntity.getFatherTypeId());
            this.text=resourceTypeEntity.getName();
        }

}
