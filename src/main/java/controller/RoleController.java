package controller;

import json.*;
import model.PrivilegeEntity;
import model.RoleEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.PrivilegeService;
import service.RoleService;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PrivilegeService privilegeService;

    @RequestMapping(value = "/manage/role", method = RequestMethod.GET)
    public String index() {
        return "role";
    }

    @RequestMapping(value = "/manage/role/add", method = RequestMethod.GET)
    public String add() {
        return "role_add";
    }

    @RequestMapping(value = "/manage/role/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id")int id, ModelMap modelMap) {
        RoleEntity roleEntity=roleService.getRoleEntityById(id);
        PrivilegeEntity privilegeEntity=privilegeService.findById(roleEntity.getPrivilegeId());
        modelMap.put("role",roleEntity);
        modelMap.put("privilege",privilegeEntity);
        return "role_edit";
    }

    @ResponseBody
    @RequestMapping(value = "/manage/role/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody RoleEntity roleEntity,HttpSession session) {
        ParentResponse resp = new ParentResponse();
        UserEntity user=(UserEntity)session.getAttribute(Session.USER);
        if (roleEntity.getId() != 0) {
            RoleEntity roleEntity1 = roleService.getRoleEntityById(roleEntity.getId());
            roleEntity.setCreateTime(roleEntity1.getCreateTime());
            roleEntity.setCreatorId(roleEntity1.getCreatorId());
            roleEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            roleEntity.setModifierId(user.getId());
            roleEntity.setIsDeleted(roleEntity1.getIsDeleted());
        } else {
            roleEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            roleEntity.setCreatorId(user.getId());
            roleEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            roleEntity.setModifierId(user.getId());
            roleEntity.setIsDeleted((byte) 0);
        }
        //更新权限
        PrivilegeEntity privilegeEntity=new PrivilegeEntity(roleEntity);
        privilegeEntity=privilegeService.update(privilegeEntity);
        roleEntity.setPrivilegeId(privilegeEntity.getId());
        roleService.update(roleEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/manage/role/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserList(HttpServletRequest request, HttpSession session) {
        RoleParam roleParam = new RoleParam();
        ReflectUtils.convert(roleParam, request);
        if (roleParam.page > 0)
            roleParam.page -= 1;
        UserEntity userEntity=(UserEntity)session.getAttribute(Session.USER);
        RoleEntity roleEntity=roleService.getRoleEntityById(userEntity.getRoleId());
        roleParam.level=roleEntity.getLevel();
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<RoleResponse>();
        int count = roleService.countByCondition(roleParam);
        if (count > 0)
            resp.count = count;
        List<RoleEntity> roleEntities = roleService.findByCondition(roleParam);
        if (roleEntities != null) {
            for (RoleEntity user : roleEntities) {
                RoleResponse roleResponse = new RoleResponse(user);
                resp.data.add(roleResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    public static class SelectRequest{
        public Integer self;
    }

    @ResponseBody
    @RequestMapping(value = "/manage/role/select", method = RequestMethod.POST)
    public ResponseEntity<Object> select(@RequestBody SelectRequest selectRequest, HttpSession session) {
        UserEntity userEntity=(UserEntity)session.getAttribute(Session.USER);
        RoleEntity roleEntity=roleService.getRoleEntityById(userEntity.getRoleId());
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<RoleResponse>();
        if(selectRequest.self==1){
            resp.data.add(new RoleResponse(roleEntity));
        }
        List<RoleEntity> roleEntities = roleService.findAllByLevelGreaterThan(roleEntity.getLevel());
        if (roleEntities != null) {
            for (RoleEntity role : roleEntities) {
                RoleResponse roleResponse = new RoleResponse(role);
                resp.data.add(roleResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/manage/role/delete", method = RequestMethod.POST)
    public ParentResponse deletePassword(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "删除失败！";
            return resp;
        }
        int result = roleService.deleteRoleById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "删除失败！";
        return resp;
    }
}
