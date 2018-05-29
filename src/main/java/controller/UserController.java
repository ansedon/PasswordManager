package controller;

import json.*;
import model.CpGroupEntity;
import model.UserEntity;
import model.UserGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GroupService;
import service.UserGroupService;
import service.UserService;
import tool.RSAUtils;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Value("${privateKey}")
    private String privateKey;
    @Value("${publicKey}")
    private String publicKey;

    @Autowired
    UserService userService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/manage/user", method = RequestMethod.GET)
    public String index() {
        return "user";
    }

    @ResponseBody
    @RequestMapping(value = "/manage/user/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody UserEntity userEntity) {
        ParentResponse resp = new ParentResponse();
        if (userEntity.getId() != 0) {
            UserEntity userEntity1 = userService.findUserEntityById(userEntity.getId());
            userEntity.setCreateTime(userEntity1.getCreateTime());
            userEntity.setIsDeleted(userEntity1.getIsDeleted());
            try {
                userEntity.setPassword(RSAUtils.encryptByPublicKey(userEntity.getPassword(), publicKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
            userService.update(userEntity);
            UserGroupEntity userGroupEntity = userGroupService.findByUserId(userEntity.getId());
            //更新用户所在群组
            if (userGroupEntity.getGroupId() != userEntity.getGroupId()) {
                userGroupEntity.setGroupId(userEntity.getGroupId());
                userGroupEntity.setUserId(userEntity.getId());
                userGroupService.update(userGroupEntity);
            }
        } else {
            try {
                userEntity.setPassword(RSAUtils.encryptByPublicKey(userEntity.getPassword(), publicKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
            userEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userEntity.setIsDeleted((byte) 0);
            userEntity = userService.update(userEntity);
            //用户归群
            UserGroupEntity userGroupEntity = new UserGroupEntity();
            userGroupEntity.setGroupId(userEntity.getGroupId());
            userGroupEntity.setUserId(userEntity.getId());
            userGroupService.insert(userGroupEntity);
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/manage/user/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserList(HttpServletRequest request, HttpSession session) {
        UserParam userParam = new UserParam();
        ReflectUtils.convert(userParam, request);
        if (userParam.page > 0)
            userParam.page -= 1;
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<UserResponse>();
        int id = (int) session.getAttribute(Session.GROUPID);
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "发生异常，请重试！";
        }
        List<Integer> ids = new ArrayList<Integer>();
        userParam.ids = new ArrayList<>();
        if (userParam.groupId != 0) {
            userParam.ids.add(userParam.groupId);
            userParam.groupId = 2;
        } else {
            ids.add(id);
            if (id != 1) {
                List<CpGroupEntity> lists = groupService.findAllByFatherGroupIds(ids);
                while (lists != null && lists.size() != 0) {
                    ids.clear();
                    for (CpGroupEntity g : lists) {
                        ids.add(g.getId());
                        userParam.ids.add(g.getId());
                    }
                    lists = groupService.findAllByFatherGroupIds(ids);
                }
            }
        }
        int count = userService.countByCondition(userParam);
        if (count > 0)
            resp.count = count;
        List<UserEntity> userEntities = userService.findByCondition(userParam);
        if (userEntities != null) {
            for (UserEntity user : userEntities) {
                try {
                    user.setPassword(RSAUtils.decryptByPrivateKey(user.getPassword(), privateKey));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UserResponse userResponse = new UserResponse(user);
                userResponse.roleName = user.getRoleByRoleId().getName();
                userResponse.groupId = userGroupService.findByUserId(user.getId()).getGroupId();
                userResponse.groupName = groupService.findGroupById(userResponse.groupId).getName();
                resp.data.add(userResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "manage/user/select", method = RequestMethod.POST)
    public ResponseEntity<Object> getUser() {
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<UserResponse>();
        List<UserEntity> userEntities=userService.findAll();
        for(UserEntity userEntity:userEntities){
            UserResponse userResponse=new UserResponse(userEntity);
            resp.data.add(userResponse);
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/manage/user/delete", method = RequestMethod.POST)
    public ParentResponse deletePassword(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "删除失败！";
            return resp;
        }
        int result = userService.deleteUserById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "删除失败！";
        return resp;
    }
}
