package json;

import java.sql.Timestamp;
import java.util.List;

public class ResourceParam{
    public int page;
    public int limit;
    public Timestamp startTime;
    public Timestamp endTime;
    public String name;
    public int typeId;
    public int groupId;
    public List<Integer> groupList;
    public List<Integer> resIds;
}
