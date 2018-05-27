package json;

import model.CpGroupEntity;

public class GroupTree {
        public String id;
        public String parent;
        public String text;
        public String icon;
        public TreeState state;

        public GroupTree(CpGroupEntity groupEntity){
            this.id=String.valueOf(groupEntity.getId());
            if(groupEntity.getFatherGroupId()==1&&groupEntity.getId()==1)
                this.parent="#";
            else
                this.parent=String.valueOf(groupEntity.getFatherGroupId());
            this.text=groupEntity.getName();
        }
}
