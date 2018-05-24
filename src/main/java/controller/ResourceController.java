package controller;

import json.*;
import model.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/resource/list", method = RequestMethod.GET)
    public String resource() {
        return "resource_list";
    }

    @RequestMapping(value = "/resource/list/add",method = RequestMethod.GET)
    public String add(){
        return "resource_add";
    }

    @RequestMapping(value = "/resource/list/edit/{id}",method = RequestMethod.GET)
    public String edit(@PathVariable("id")int id, ModelMap modelMap){
        ResourceEntity resourceEntity=resourceService.findResourceEntityById(id);
        ResourceResponse resourceResponse=new ResourceResponse(resourceEntity);
        modelMap.put("resourceEntity",resourceResponse);
        return "resource_edit";
    }

    @RequestMapping(value = "/resource/list/update",method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody ResourceEntity resourceEntity){
        ParentResponse resp = new ParentResponse();
        if(resourceEntity.getId()!=0){
            ResourceEntity resourceEntity1=resourceService.findResourceEntityById(resourceEntity.getId());
            resourceEntity.setCreateTime(resourceEntity1.getCreateTime());
            resourceEntity.setIsDeleted(resourceEntity1.getIsDeleted());
        }else{
            resourceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            resourceEntity.setIsDeleted((byte)0);
        }
        resourceService.update(resourceEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/resource/list/one",method = RequestMethod.POST)
    public ResponseEntity<Object> getResourceById(@RequestBody IdRequest idRequest){
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        int id=idRequest.id;
        if(id<=0){
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
    public ResponseEntity<Object> getResourceList(HttpServletRequest request,HttpSession session) {
        ResourceParam resourceParam = new ResourceParam();
        ReflectUtils.convert(resourceParam, request);
        if (resourceParam.page > 0)
            resourceParam.page -= 1;
        resourceParam.groupId=(int)session.getAttribute(Session.GROUPID);
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
        int id=(int)session.getAttribute(Session.GROUPID);
        if(id<=0){
            resp.result = "Fail";
            resp.msg = "发生异常，请重试！";
        }
        List<ResourceEntity> resourceEntities =resourceService.getResourceEntitiesByGroupId(id);
        if (resourceEntities != null) {
            for (ResourceEntity resourceEntity : resourceEntities) {
                ResourceResponse res = new ResourceResponse(resourceEntity);
                resp.data.add(res);
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
}
