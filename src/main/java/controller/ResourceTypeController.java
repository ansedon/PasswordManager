package controller;

import json.IdRequest;
import json.ParentParam;
import json.ParentResponse;
import json.ResourceTypeResponse;
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
    @RequestMapping(value = "/resource/type/update",method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody ResourceTypeEntity resourceTypeEntity){
        ParentResponse resp = new ParentResponse();
        if(resourceTypeEntity.getId()!=0){
            ResourceTypeEntity resourceTypeEntity1=resourceTypeService.findResourceTypeEntityById(resourceTypeEntity.getId());
            resourceTypeEntity.setCreateTime(resourceTypeEntity1.getCreateTime());
            resourceTypeEntity.setIsDeleted(resourceTypeEntity1.getIsDeleted());
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
