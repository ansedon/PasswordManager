package controller;

import json.Session;
import model.LoginLogEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    LoginLogService loginLogService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordService passwordService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    GroupService groupService;

    @RequestMapping(value="/welcome",method = RequestMethod.GET)
    public String welcome(ModelMap modelMap, HttpSession session){
        UserEntity userEntity= (UserEntity) session.getAttribute(Session.USER);
        if(userEntity==null)
            return "login";
        modelMap.put("user",userEntity);
        modelMap.put("resNum",resourceService.countAll());
        modelMap.put("psNum",passwordService.countAll());
        modelMap.put("groupNum",groupService.countAll());
        modelMap.put("userNum",userService.countAll());
        LoginLogEntity loginLogEntity=loginLogService.findOneByUserId(userEntity.getId());
        if(loginLogEntity!=null)
            modelMap.put("lastLoginTime",loginLogEntity.getCreateTime().toString().substring(0,19));
        return "welcome";
    }

}
