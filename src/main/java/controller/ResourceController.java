package controller;

import json.*;
import model.CpGroupEntity;
import model.ResourceEntity;
import model.ResourceGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.GroupService;
import service.ResourceGroupService;
import service.ResourceService;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResourceController {
    @Autowired
    ResourceService resourceService;

    @Autowired
    GroupService groupService;

    @Autowired
    ResourceGroupService resourceGroupService;

    @RequestMapping(value = "/resource/list", method = RequestMethod.GET)
    public String resource() {
        return "resource_list";
    }

    @RequestMapping(value = "/resource/list/add", method = RequestMethod.GET)
    public String add() {
        return "resource_add";
    }

    @RequestMapping(value = "/resource/list/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, ModelMap modelMap) {
        ResourceEntity resourceEntity = resourceService.findResourceEntityById(id);
        ResourceResponse resourceResponse = new ResourceResponse(resourceEntity);
        modelMap.put("resourceEntity", resourceResponse);
        return "resource_edit";
    }

    @RequestMapping(value = "/resource/list/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody ResourceEntity resourceEntity) {
        ParentResponse resp = new ParentResponse();
        if (resourceEntity.getId() != 0) {
            ResourceEntity resourceEntity1 = resourceService.findResourceEntityById(resourceEntity.getId());
            resourceEntity.setCreateTime(resourceEntity1.getCreateTime());
            resourceEntity.setIsDeleted(resourceEntity1.getIsDeleted());
        } else {
            resourceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            resourceEntity.setIsDeleted((byte) 0);
        }
        resourceService.update(resourceEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/list/one", method = RequestMethod.POST)
    public ResponseEntity<Object> getResourceById(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "id错误！";
        }
        resp.data.add(new ResourceResponse(resourceService.findResourceEntityById(id)));
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/list/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getResourceList(HttpServletRequest request, HttpSession session) {
        ResourceParam resourceParam = new ResourceParam();
        ReflectUtils.convert(resourceParam, request);
        if (resourceParam.page > 0)
            resourceParam.page -= 1;
        resourceParam.groupId = (int) session.getAttribute(Session.GROUPID);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(resourceParam.groupId);
        if (resourceParam.groupId != 1) {
            List<CpGroupEntity> lists = groupService.findAllByFatherGroupIds(ids);
            while (lists != null && lists.size() != 0) {
                ids.clear();
                for (CpGroupEntity g : lists) {
                    ids.add(g.getId());
                    resourceParam.groupList.add(g.getId());
                }
                lists = groupService.findAllByFatherGroupIds(ids);
            }
        }
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        int count = resourceService.countByCondition(resourceParam);
        if (count > 0)
            resp.count = count;
        List<ResourceEntity> resourceEntities = resourceService.findByCondition(resourceParam);
        if (resourceEntities != null) {
            for (ResourceEntity res : resourceEntities) {
                ResourceResponse resourceResponse = new ResourceResponse(res);
                resourceResponse.typeName = res.getResourceTypeByTypeId().getName();
                resp.data.add(resourceResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/all", method = RequestMethod.POST)
    public ResponseEntity<Object> getResourceListByGroupId(HttpSession session) {
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        int id = (int) session.getAttribute(Session.GROUPID);
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "发生异常，请重试！";
        }
        List<Integer> groupIds = new ArrayList<Integer>();
        List<Integer> ids = new ArrayList<Integer>();
        groupIds.add(id);
        ids.add(id);
        if (id != 1) {
            List<CpGroupEntity> lists = groupService.findAllByFatherGroupIds(ids);
            while (lists != null && lists.size() != 0) {
                ids.clear();
                for (CpGroupEntity g : lists) {
                    ids.add(g.getId());
                    groupIds.add(g.getId());
                }
                lists = groupService.findAllByFatherGroupIds(ids);
            }
            List<ResourceEntity> resourceEntities = resourceService.getResourceEntitiesByGroupIds(groupIds);
            if (resourceEntities != null) {
                for (ResourceEntity resourceEntity : resourceEntities) {
                    ResourceResponse res = new ResourceResponse(resourceEntity);
                    resp.data.add(res);
                }
            }
        } else {
            List<ResourceEntity> resourceEntities = resourceService.findAll();
            if (resourceEntities != null) {
                for (ResourceEntity resourceEntity : resourceEntities) {
                    ResourceResponse res = new ResourceResponse(resourceEntity);
                    resp.data.add(res);
                }
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/list/delete", method = RequestMethod.POST)
    public ParentResponse deleteResource(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "删除失败！";
            return resp;
        }
        int result = resourceService.deleteResourceById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "删除失败！";
        return resp;
    }

    //获取本部门拥有而下级部门未拥有的资源（即待分配资源）
    @ResponseBody
    @RequestMapping(value = "/resource/list/allot", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllotedResource(HttpServletRequest request, HttpSession session) {
        ResourceParam resourceParam = new ResourceParam();
        ReflectUtils.convert(resourceParam, request);
        if (resourceParam.page > 0)
            resourceParam.page -= 1;
        int groupId=resourceParam.groupId;
        //已拥有资源
        resourceParam.resIds=new ArrayList<>();
        resourceParam.groupList=new ArrayList<>();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(resourceParam.groupId);
        resourceParam.groupList.add(resourceParam.groupId);
        List<CpGroupEntity> lists = groupService.findAllByFatherGroupIds(ids);
        while (lists != null && lists.size() != 0) {
            ids.clear();
            for (CpGroupEntity g : lists) {
                ids.add(g.getId());
                resourceParam.groupList.add(g.getId());
            }
            lists = groupService.findAllByFatherGroupIds(ids);
        }
        List<ResourceEntity> resourceEntities = resourceService.findByCondition(resourceParam);
        if (resourceEntities != null) {
            for (ResourceEntity res : resourceEntities) {
                resourceParam.resIds.add(res.getId());
            }
        }
        //计算未拥有资源
        resourceParam.groupId=groupId;
        int childId = resourceParam.groupId;
        resourceParam.groupId = (int) session.getAttribute(Session.GROUPID);
        resourceParam.groupList=new ArrayList<>();
        ids = new ArrayList<Integer>();
        ids.add(resourceParam.groupId);
        resourceParam.groupList.add(resourceParam.groupId);
        lists = groupService.findAllByFatherGroupIds(ids);
        while (lists != null && lists.size() != 0) {
            ids.clear();
            for (CpGroupEntity g : lists) {
                if (g.getId() != childId && g.getId() != 1) {
                    ids.add(g.getId());
                    resourceParam.groupList.add(g.getId());
                }
            }
            lists = groupService.findAllByFatherGroupIds(ids);
        }
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        int count = resourceService.countByCondition(resourceParam);
        if (count > 0)
            resp.count = count;
        resourceEntities = resourceService.findByCondition(resourceParam);
        if (resourceEntities != null) {
            for (ResourceEntity res : resourceEntities) {
                ResourceResponse resourceResponse = new ResourceResponse(res);
                resourceResponse.typeName = res.getResourceTypeByTypeId().getName();
                resp.data.add(resourceResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    //获取群组已拥有的可能要移除的资源（即待移除资源）
    @ResponseBody
    @RequestMapping(value = "/resource/list/remove", method = RequestMethod.GET)
    public ResponseEntity<Object> getRemovedResource(HttpServletRequest request, HttpSession session) {
        ResourceParam resourceParam = new ResourceParam();
        ReflectUtils.convert(resourceParam, request);
        if (resourceParam.page > 0)
            resourceParam.page -= 1;
        resourceParam.groupList=new ArrayList<>();
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(resourceParam.groupId);
        resourceParam.groupList.add(resourceParam.groupId);
        List<CpGroupEntity> lists = groupService.findAllByFatherGroupIds(ids);
        while (lists != null && lists.size() != 0) {
            ids.clear();
            for (CpGroupEntity g : lists) {
                ids.add(g.getId());
                resourceParam.groupList.add(g.getId());
            }
            lists = groupService.findAllByFatherGroupIds(ids);
        }
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        int count = resourceService.countByCondition(resourceParam);
        if (count > 0)
            resp.count = count;
        List<ResourceEntity> resourceEntities = resourceService.findByCondition(resourceParam);
        if (resourceEntities != null) {
            for (ResourceEntity res : resourceEntities) {
                ResourceResponse resourceResponse = new ResourceResponse(res);
                resourceResponse.typeName = res.getResourceTypeByTypeId().getName();
                resp.data.add(resourceResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    public static class ChangeRequest{
        public int resId;
        public int groupId;
        public int isRemove;
    }

    //从群组中分配或移除资源
    @ResponseBody
    @RequestMapping(value = "/resource/list/change", method = RequestMethod.POST)
    public ResponseEntity<Object> getRemovedResource(@RequestBody ChangeRequest changeRequest, HttpSession session) {
        ParentResponse resp = new ParentResponse();
       if(changeRequest.isRemove==0){
           if(resourceGroupService.findByResIdAndGroupId(changeRequest.resId,changeRequest.groupId)!=null){
               resp.result="Fail";
               resp.msg="已拥有该资源";
               return new ResponseEntity<Object>(resp, HttpStatus.OK);
           }
           ResourceGroupEntity resourceGroupEntity=new ResourceGroupEntity();
           resourceGroupEntity.setResId(changeRequest.resId);
           resourceGroupEntity.setGroupId(changeRequest.groupId);
           resourceGroupService.insert(resourceGroupEntity);
       }else{
           if(resourceGroupService.findByResIdAndGroupId(changeRequest.resId,changeRequest.groupId)==null){
               resp.result="Fail";
               resp.msg="未拥有该资源";
               return new ResponseEntity<Object>(resp, HttpStatus.OK);
           }
           resourceGroupService.deleteByResIdAndGroupId(changeRequest.resId,changeRequest.groupId);
       }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }
}
