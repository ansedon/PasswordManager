package json;

import java.sql.Timestamp;
import java.util.List;

public class GroupParam {
    public int id;
    public String name;
    public int fatherGroupId;
    public List<Integer> ids;
    public Timestamp startTime;
    public Timestamp endTime;
    public int page;
    public int limit;
}
