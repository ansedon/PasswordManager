package controller;

import json.ParentResponse;
import json.ResourceParam;
import json.ResourceResponse;
import model.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ResourceService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ResourceController {
    @Autowired
    ResourceService resourceService;

    @ResponseBody
    @RequestMapping(value = "/resource/list", method = RequestMethod.GET)
    public ResponseEntity<Object> getResourceList() {
        ResourceParam resourceParam = new ResourceParam();
        resourceParam.page = 0;
        resourceParam.limit = 10;
        resourceParam.name = "MySql";
        resourceParam.groupId=1;
        resourceParam.typeId=1;
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        List<ResourceEntity> resourceEntities = resourceService.findByConditions(resourceParam);
        if (resourceEntities != null) {
            for (ResourceEntity res : resourceEntities) {
                ResourceResponse resourceResponse=new ResourceResponse(res);
                resp.data.add(resourceResponse);
            }
            resp.count = resourceEntities.size();
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }
}
