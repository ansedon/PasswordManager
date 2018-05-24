package controller;

import interceptor.SessionManageListener;
import json.ParentResponse;
import json.Session;
import model.LoginLogEntity;
import model.UserEntity;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.LoginLogService;
import service.RoleService;
import service.UserGroupService;
import service.UserService;
import tool.ForceLogoutUtils;
import tool.IpAddressUtils;
import tool.RSAUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    LoginLogService loginLogService;

    @Autowired
    UserGroupService userGroupService;

    @Value("${privateKey}")
    private String privateKey;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    @ResponseBody
    @RequestMapping(value = "/checklogin", method = RequestMethod.POST)
    public ParentResponse checkLogin(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletRequest request) {
        ParentResponse checkLoginResponse = new ParentResponse();
        String username = loginRequest.username;
        String password = loginRequest.password;
        if (username == null || password == null) {
            checkLoginResponse.result = "FAIL";
            checkLoginResponse.msg = "用户名或密码错误！";
            return checkLoginResponse;
        }
        UserEntity userEntity = userService.findByAccount(username);
        if (userEntity == null) {
            checkLoginResponse.result = "FAIL";
            checkLoginResponse.msg = "用户名不存在！";
            return checkLoginResponse;
        } else {
            try {
//                System.out.println("miwen:"+RSAUtils.encryptByPublicKey("123456",publicKey));
                if (!password.equals(RSAUtils.decryptByPrivateKey(userEntity.getPassword(), privateKey))) {
                    checkLoginResponse.result = "FAIL";
                    checkLoginResponse.msg = "密码错误！";
                    return checkLoginResponse;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //登陆日志
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //获取浏览器信息
        String ua = request.getHeader("User-Agent");
        //转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
        //系统名称
        String system = os.getName();
        //浏览器名称
        String browserName = browser.getName();
        //ip地址
        String ip = IpAddressUtils.getIpAdrress(request);
        loginLogEntity.setBrowserInfo(browserName);
        loginLogEntity.setIp(ip);
        loginLogEntity.setOsInfo(system);
        loginLogEntity.setUserId(userEntity.getId());
        loginLogEntity.setRoleId(userEntity.getRoleId());
        loginLogService.insert(loginLogEntity);
        //该账号已经被登陆
        if (null != SessionManageListener.sessionMap.get(userEntity.getAccount())) {
            //如果不是同一个浏览器
            if(SessionManageListener.sessionMap.get(userEntity.getAccount()).getId()!=session.getId())
                // 将已经登陆的信息拿掉,将新的用户登录信息放进去
                ForceLogoutUtils.forceUserLogout(userEntity.getAccount());
        }
        session.setAttribute(Session.USER, userEntity);
        session.setAttribute(Session.GROUPID,userGroupService.findByUserId(userEntity.getId()).getGroupId());
        SessionManageListener.sessionMap.put(userEntity.getAccount(), session);
        checkLoginResponse.result = "OK";
        return checkLoginResponse;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
