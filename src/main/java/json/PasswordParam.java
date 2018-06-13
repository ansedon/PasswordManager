package json;

import java.sql.Timestamp;
import java.util.List;

public class PasswordParam {
    public int id;
    public int groupId;
    public List<Integer> ids;
    public int resId;
    public String account;
    public Timestamp expireTime;
    public Timestamp startTime;
    public Timestamp endTime;
    public int page;
    public int limit;
    public int isDeleted;
    public int isExpired;
}
