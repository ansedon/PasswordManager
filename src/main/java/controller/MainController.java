package controller;

import json.Session;
import model.PrivilegeEntity;
import model.RoleEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PrivilegeService;
import service.RoleService;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PrivilegeService privilegeService;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String home(ModelMap modelMap,HttpSession session){
        UserEntity userEntity=(UserEntity)session.getAttribute(Session.USER);
        RoleEntity roleEntity=roleService.findById(userEntity.getRoleId());
        PrivilegeEntity privilegeEntity=privilegeService.findById(roleEntity.getPrivilegeId());
        modelMap.addAttribute("user",session.getAttribute(Session.USER));
        modelMap.addAttribute("role",roleEntity);
        modelMap.addAttribute("privilege",privilegeEntity);
        return "index";
    }

    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    public @ResponseBody List<UserEntity> getAllUser(){
       List<UserEntity> list= userService.findAll();
       return list;
    }
}
