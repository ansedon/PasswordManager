package controller;

import json.LoginLogResponse;
import json.ParentParam;
import json.ParentResponse;
import model.LoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.LoginLogService;
import service.RoleService;
import service.UserService;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LogController {
    @Autowired
    LoginLogService loginLogService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/manage/loginlog", method = RequestMethod.GET)
    public String index() {
        return "loginlog";
    }

    @ResponseBody
    @RequestMapping(value="/manage/loginlog/all",method = RequestMethod.GET)
    public ResponseEntity<Object> getLoginLogList(HttpServletRequest request){
        ParentParam parentParam=new ParentParam();
        ReflectUtils.convert(parentParam,request);
        ParentResponse resp=new ParentResponse();
        if(parentParam.page>0){
            parentParam.page-=1;
        }
        resp.count=loginLogService.countByCondition(parentParam);
        if(resp.count<0){
            resp.result="Fail";
            resp.msg="参数错误";
            return new ResponseEntity<Object>(resp,HttpStatus.OK);
        }
        resp.data=new ArrayList<LoginLogResponse>();
        List<LoginLogEntity> loginLogEntityList=loginLogService.findByCondition(parentParam);
        for(LoginLogEntity loginLogEntity:loginLogEntityList){
            LoginLogResponse loginLogResponse=new LoginLogResponse(loginLogEntity);
            loginLogResponse.userName= userService.findUserEntityById(loginLogEntity.getUserId()).getAccount();
            loginLogResponse.roleName=roleService.getRoleEntityById(loginLogEntity.getRoleId()).getName();
            resp.data.add(loginLogResponse);
        }
        resp.result="OK";
        return new ResponseEntity<Object>(resp,HttpStatus.OK);
    }

}
