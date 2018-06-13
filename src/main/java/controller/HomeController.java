package controller;

import json.*;
import model.CpGroupEntity;
import model.LoginLogEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        //资源数
        ResourceParam resourceParam = new ResourceParam();
        resourceParam.groupList=new ArrayList<>();
        if (resourceParam.page > 0)
            resourceParam.page -= 1;
        resourceParam.groupId = (int) session.getAttribute(Session.GROUPID);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(resourceParam.groupId);
        resourceParam.groupList.add(resourceParam.groupId);
        if (resourceParam.groupId != 1) {
            List<CpGroupEntity> lists = groupService.findAllByFatherGroupIds(ids);
            ids.clear();
            while (lists != null && lists.size() != 0) {
                for (CpGroupEntity g : lists) {
                    ids.add(g.getId());
                    resourceParam.groupList.add(g.getId());
                }
                lists = groupService.findAllByFatherGroupIds(ids);
                ids.clear();
            }
        }
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceResponse>();
        modelMap.put("resNum",resourceService.countByCondition(resourceParam));
        //口令数
        PasswordParam passwordParam = new PasswordParam();
        if (passwordParam.page > 0)
            passwordParam.page -= 1;
        passwordParam.groupId = (int) session.getAttribute(Session.GROUPID);
        modelMap.put("psNum",passwordService.countByCondition(passwordParam));
        //过期口令数
        passwordParam.isExpired=1;
        modelMap.put("psExpired",passwordService.countByCondition(passwordParam));
        //已删除口令数
        passwordParam.isDeleted=1;
        passwordParam.isExpired=0;
        modelMap.put("psNumDeleted",passwordService.countByCondition(passwordParam));
        //部门数
        modelMap.put("groupNum",groupService.countAll());
        //用户数
        modelMap.put("userNum",userService.countAll());
        LoginLogEntity loginLogEntity=loginLogService.findOneByUserId(userEntity.getId());
        if(loginLogEntity!=null)
            modelMap.put("lastLoginTime",loginLogEntity.getCreateTime().toString().substring(0,19));
        return "welcome";
    }

}
