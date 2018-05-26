package json;

import model.UserEntity;

public class UserResponse {
    public int id;
    public String account;
    public String password;
    public String email;
    public String phone;
    public String location;
    public String createTime;
    public int roleId;
    public String roleName;
    public int groupId;
    public String groupName;

    public UserResponse(UserEntity userEntity){
        this.id=userEntity.getId();
        this.account=userEntity.getAccount();
        this.password=userEntity.getPassword();
        this.email=userEntity.getEmail();
        this.phone=userEntity.getPhone();
        this.location=userEntity.getLocation();
        this.createTime=userEntity.getCreateTime().toString().substring(0,19);
        this.roleId=userEntity.getRoleId();
    }
}
