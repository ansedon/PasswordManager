package controller;

import json.*;
import model.CpGroupEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.GroupService;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {
    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public String index() {
        return "group";
    }

    @ResponseBody
    @RequestMapping(value = "/group/list/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody CpGroupEntity groupEntity, HttpSession session) {
        ParentResponse resp = new ParentResponse();
        UserEntity user = (UserEntity) session.getAttribute(Session.USER);
        if (groupEntity.getId() != 0) {
            CpGroupEntity groupEntity1 = groupService.findGroupById(groupEntity.getId());
            groupEntity.setCreatorId(groupEntity1.getCreatorId());
            groupEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            groupEntity.setModifierId(user.getId());
            groupEntity.setCreateTime(groupEntity1.getCreateTime());
            groupEntity.setIsDeleted(groupEntity1.getIsDeleted());
        } else {
            groupEntity.setCreatorId(user.getId());
            groupEntity.setModifierId(user.getId());
            groupEntity.setFatherGroupId((int) session.getAttribute(Session.GROUPID));
            groupEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            groupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            groupEntity.setIsDeleted((byte) 0);
        }
        groupService.update(groupEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/group/all", method = RequestMethod.POST)
    public ResponseEntity<Object> getChildGroup(HttpSession session) {
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<GroupResponse>();
        int id=(int)session.getAttribute(Session.GROUPID);
        if(id<=0){
            resp.result = "Fail";
            resp.msg = "发生异常，请重试！";
        }
        List<CpGroupEntity> cpGroupEntities =groupService.findAllByFatherGroupId(id);
        if (cpGroupEntities != null) {
            for (CpGroupEntity cpGroupEntity : cpGroupEntities) {
                GroupResponse res = new GroupResponse(cpGroupEntity);
                resp.data.add(res);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/group/list/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getGroupList(HttpServletRequest request, HttpSession session) {
        GroupParam groupParam = new GroupParam();
        ReflectUtils.convert(groupParam, request);
        if (groupParam.page > 0)
            groupParam.page -= 1;
        if(groupParam.fatherGroupId==0)
            groupParam.fatherGroupId = (int) session.getAttribute(Session.GROUPID);
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<GroupResponse>();
        int count = groupService.countByCondition(groupParam);
        if (count > 0)
            resp.count = count;
        List<CpGroupEntity> groupEntities = groupService.findByCondition(groupParam);
        if (groupEntities != null) {
            for (CpGroupEntity g : groupEntities) {
                GroupResponse groupResponse = new GroupResponse(g);
                groupResponse.fatherGroupName = groupService.findGroupById(groupResponse.fatherGroupId).getName();
                groupResponse.creatorName=g.getUserByCreatorId().getAccount();
                resp.data.add(groupResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/group/list/delete", method = RequestMethod.POST)
    public ParentResponse deleteGroup(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "删除失败！";
            return resp;
        }
        int result = groupService.deleteGroupById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "删除失败！";
        return resp;
    }
}
