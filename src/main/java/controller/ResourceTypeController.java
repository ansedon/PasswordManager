package controller;

import json.*;
import model.ResourceTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ResourceTypeService;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResourceTypeController {
    @Autowired
    ResourceTypeService resourceTypeService;

    @RequestMapping(value="/resource/type",method = RequestMethod.GET)
    public String index(){
        return "type";
    }

    @ResponseBody
    @RequestMapping(value = "/resource/type/one",method = RequestMethod.POST)
    public ResponseEntity<Object> getTypeById(@RequestBody IdRequest idRequest){
        ResourceTypeEntity resourceTypeEntity= resourceTypeService.findResourceTypeEntityById(idRequest.id);
        ResourceTypeResponse resp=new ResourceTypeResponse(resourceTypeEntity);
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/type/tree",method = RequestMethod.POST)
    public ResponseEntity<Object> getGroupTree(HttpSession session){
        List<ResourceTypeEntity> typeEntities=resourceTypeService.findAll();
        List<TreeResponse> tree=new ArrayList<>();
        ResourceTypeEntity resourceTypeEntity=new ResourceTypeEntity();
        resourceTypeEntity.setId(0);
        resourceTypeEntity.setFatherTypeId(-1);
        resourceTypeEntity.setName("root");
        TreeResponse resp=new TreeResponse(resourceTypeEntity);
        resp.state=new TreeState();
        resp.state.opened=true;
        tree.add(resp);
        for(ResourceTypeEntity typeEntity:typeEntities){
            TreeResponse treeResponse =new TreeResponse(typeEntity);
            treeResponse.state=new TreeState();
            treeResponse.state.disabled=false;
            treeResponse.state.opened=true;
            tree.add(treeResponse);
        }
        return new ResponseEntity<Object>(tree,HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/type/update",method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody ResourceTypeEntity resourceTypeEntity){
        ParentResponse resp = new ParentResponse();
        if(resourceTypeEntity.getId()!=0){
            ResourceTypeEntity resourceTypeEntity1=resourceTypeService.findResourceTypeEntityById(resourceTypeEntity.getId());
            resourceTypeEntity.setCreateTime(resourceTypeEntity1.getCreateTime());
            resourceTypeEntity.setIsDeleted(resourceTypeEntity1.getIsDeleted());
            if(resourceTypeEntity.getFatherTypeId()==-1){
                resourceTypeEntity.setFatherTypeId(0);
            }else if(resourceTypeEntity.getFatherTypeId()==0){
                resourceTypeEntity.setFatherTypeId(resourceTypeEntity1.getFatherTypeId());
            }else{
                resourceTypeEntity.setName(resourceTypeEntity1.getName());
                resourceTypeEntity.setDescription((resourceTypeEntity1.getDescription()));
            }
        }else{
            resourceTypeEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            resourceTypeEntity.setIsDeleted((byte)0);
        }
        resourceTypeService.update(resourceTypeEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/type/list",method = RequestMethod.GET)
    public ResponseEntity<Object> getTypeList(HttpServletRequest request){
        ParentParam parentParam = new ParentParam();
        ReflectUtils.convert(parentParam, request);
        if (parentParam.page > 0)
            parentParam.page -= 1;
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceTypeResponse>();
        int count = resourceTypeService.countByCondition(parentParam);
        if (count > 0)
            resp.count = count;
        List<ResourceTypeEntity> resourceTypeEntities = resourceTypeService.findByCondition(parentParam);
        if (resourceTypeEntities != null) {
            for (ResourceTypeEntity res : resourceTypeEntities) {
                ResourceTypeResponse resourceTypeResponse = new ResourceTypeResponse(res);
                resp.data.add(resourceTypeResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/type/all",method = RequestMethod.POST)
    public ResponseEntity<Object> getAllType(){
        ParentResponse resp=new ParentResponse();
        resp.data=new ArrayList<ResourceTypeResponse>();
        List<ResourceTypeEntity> resourceTypeEntities=resourceTypeService.findAll();
        if(resourceTypeEntities!=null){
            for(ResourceTypeEntity type:resourceTypeEntities){
                ResourceTypeResponse resourceTypeResponse=new ResourceTypeResponse(type);
                resp.data.add(resourceTypeResponse);
            }
        }
        resp.result="OK";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/resource/type/delete", method = RequestMethod.POST)
    public ParentResponse deleteResourceType(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "删除失败！";
            return resp;
        }
        int result = resourceTypeService.deleteResourceTypeById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "删除失败！";
        return resp;
    }
}
