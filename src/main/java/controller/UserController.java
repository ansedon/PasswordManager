package controller;

import json.*;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
                userEntity.setPassword(RSAUtils.encryptByPublicKey(userEntity.getPassword(),publicKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                userEntity.setPassword(RSAUtils.encryptByPublicKey(userEntity.getPassword(),publicKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
            userEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userEntity.setIsDeleted((byte) 0);
        }
        userService.update(userEntity);
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
        userParam.groupId = (int) session.getAttribute(Session.GROUPID);
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceTypeResponse>();
        int count = userService.countByCondition(userParam);
        if (count > 0)
            resp.count = count;
        List<UserEntity> passwordEntities = userService.findByCondition(userParam);
        if (passwordEntities != null) {
            for (UserEntity user : passwordEntities) {
                try {
                    user.setPassword(RSAUtils.decryptByPrivateKey(user.getPassword(), privateKey));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UserResponse userResponse = new UserResponse(user);
                userResponse.roleName = user.getRoleByRoleId().getName();
                resp.data.add(userResponse);
            }
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
