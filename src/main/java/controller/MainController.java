package controller;

import json.Session;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String home(ModelMap modelMap,HttpSession session){
        modelMap.addAttribute("user",session.getAttribute(Session.USER));
        return "index";
    }

    @RequestMapping(value="/member-list",method = RequestMethod.GET)
    public String home1(){
        return "member-list";
    }

    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    public @ResponseBody List<UserEntity> getAllUser(){
       List<UserEntity> list= userService.findAll();
       //modelMap.addAttribute("user",list);
       return list;
    }
}
