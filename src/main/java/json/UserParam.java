package json;

import java.sql.Timestamp;
import java.util.List;

public class UserParam {
    public int id;
    public int groupId;
    public String account;
    public String password;
    public List<Integer> ids;
    public String email;
    public String phone;
    public String location;
    public int roleId;
    public Timestamp startTime;
    public Timestamp endTime;
    public int page;
    public int limit;
}
