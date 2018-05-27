package controller;

import json.*;
import model.CpGroupEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

    @ResponseBody
    @RequestMapping(value = "/group/tree",method = RequestMethod.POST)
    public ResponseEntity<Object> getGroupTree(HttpSession session){
//        int groupId=(int)session.getAttribute(Session.GROUPID);
        int groupId=2;
        List<CpGroupEntity>groupEntities=groupService.findAll();
        List<TreeResponse> tree=new ArrayList<>();
        List<Integer> groupIds=new ArrayList<>();
        List<Integer> ids=new ArrayList<>();
        ids.add(groupId);
        if(groupId!=1){
            groupIds.add(groupId);
            List<CpGroupEntity> list=groupService.findAllByFatherGroupIds(ids);
            while(list!=null&&list.size()>0){
                for(CpGroupEntity cpGroupEntity:list){
                    ids.clear();
                    groupIds.add(cpGroupEntity.getId());
                    ids.add(cpGroupEntity.getId());
                }
                list=groupService.findAllByFatherGroupIds(ids);
            }
        }
        for(CpGroupEntity cpGroupEntity:groupEntities){
            TreeResponse treeResponse =new TreeResponse(cpGroupEntity);
            treeResponse.state=new TreeState();
            treeResponse.state.disabled=true;
            if(groupId!=1){
                if(groupIds.contains(cpGroupEntity.getId()))
                    treeResponse.state.disabled=false;
                if(cpGroupEntity.getId()==groupId)
                    treeResponse.state.selected=true;
            }
            treeResponse.state.opened=true;
            tree.add(treeResponse);
        }
        return new ResponseEntity<Object>(tree,HttpStatus.OK);
    }

    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public String index() {
        return "group";
    }

    @RequestMapping(value = "/group/list/{childId}",method = RequestMethod.GET)
    public String grant(@PathVariable("childId")int childId,ModelMap modelMap){
        modelMap.put("childId",childId);
        return "resource_allot";
    }

    @ResponseBody
    @RequestMapping(value = "/group/list/one", method = RequestMethod.POST)
    public ResponseEntity<Object> getGroupById(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        resp.data=new ArrayList<GroupResponse>();
        CpGroupEntity cpGroupEntity=groupService.findGroupById(idRequest.id);
        GroupResponse groupResponse=new GroupResponse(cpGroupEntity);
        groupResponse.creatorName=cpGroupEntity.getUserByCreatorId().getAccount();
        groupResponse.modifierName=cpGroupEntity.getUserByModifierId().getAccount();
        resp.data.add(groupResponse);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/group/list/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody CpGroupEntity groupEntity, HttpSession session) {
        ParentResponse resp = new ParentResponse();
        UserEntity user = (UserEntity) session.getAttribute(Session.USER);
        //修改
        if (groupEntity.getId() != 0) {
            CpGroupEntity groupEntity1 = groupService.findGroupById(groupEntity.getId());
            groupEntity.setCreatorId(groupEntity1.getCreatorId());
            groupEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            groupEntity.setModifierId(user.getId());
            groupEntity.setCreateTime(groupEntity1.getCreateTime());
            groupEntity.setIsDeleted(groupEntity1.getIsDeleted());
            if(groupEntity.getFatherGroupId()==null||groupEntity.getFatherGroupId()==0){
                groupEntity.setFatherGroupId(groupEntity1.getFatherGroupId());
            }else{
                //拖拽改变父部门
                groupEntity.setName(groupEntity1.getName());
                groupEntity.setDescription(groupEntity1.getDescription());
                groupEntity.setLocation(groupEntity1.getLocation());
            }
        } else {//添加
            groupEntity.setCreatorId(user.getId());
            groupEntity.setModifierId(user.getId());
            groupEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            groupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            groupEntity.setIsDeleted((byte) 0);
        }
        groupService.update(groupEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    //获取当前群组下的其他群组
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
        List<Integer> ids=new ArrayList<Integer>();
        ids.add(id);
        if(id!=1){
            resp.data.add(new GroupResponse(groupService.findGroupById(id)));
            List<CpGroupEntity> lists=groupService.findAllByFatherGroupIds(ids);
            while(lists!=null&&lists.size()!=0){
                ids.clear();
                for(CpGroupEntity g : lists){
                    ids.add(g.getId());
                    GroupResponse res = new GroupResponse(g);
                    resp.data.add(res);
                }
                lists=groupService.findAllByFatherGroupIds(ids);
            }
        }else{
            List<CpGroupEntity> cpGroupEntities=groupService.findAll();
            if (cpGroupEntities != null&&cpGroupEntities.size()!=0) {
                for (CpGroupEntity cpGroupEntity : cpGroupEntities) {
                    GroupResponse res = new GroupResponse(cpGroupEntity);
                    resp.data.add(res);
                }
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
        if(groupParam.fatherGroupId==0){
            int id = (int) session.getAttribute(Session.GROUPID);
            if(id!=1){
                groupParam.ids=new ArrayList<Integer>();
                List<Integer> ids=new ArrayList<Integer>();
                ids.add(id);
                List<CpGroupEntity> lists=groupService.findAllByFatherGroupIds(ids);
                while(lists!=null&&lists.size()!=0){
                    ids.clear();
                    for(CpGroupEntity g : lists){
                        ids.add(g.getId());
                        groupParam.ids.add(g.getId());
                    }
                    lists=groupService.findAllByFatherGroupIds(ids);
                }
            }
        }
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
