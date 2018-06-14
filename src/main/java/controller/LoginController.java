package controller;

import interceptor.SessionManageListener;
import json.ParentResponse;
import json.Session;
import model.LoginLogEntity;
import model.UserEntity;
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
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        String user = userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS Info=======================
        if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            os = "Windows";
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            os = "Mac";
        } else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
            os = "Unix";
        } else if (userAgent.toLowerCase().indexOf("android") >= 0) {
            os = "Android";
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }
        //===============Browser===========================
        if (user.contains("edge")) {
            browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera")) {
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr")) {
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }

        } else if (user.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) ||
                (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) ||
                (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
        } else {
            browser = "UnKnown, More-Info: " + userAgent;
        }


//        //转成UserAgent对象
//        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
//        //获取浏览器信息
//        Browser browser = userAgent.getBrowser();
//        //获取系统信息
//        OperatingSystem os = userAgent.getOperatingSystem();
//        //系统名称
//        String system = os.getName();
//        //浏览器名称
//        String browserName = browser.getName();
        //ip地址
        String ip = IpAddressUtils.getIpAdrress(request);
        loginLogEntity.setBrowserInfo(browser);
        loginLogEntity.setIp(ip);
        loginLogEntity.setOsInfo(os);
        loginLogEntity.setUserId(userEntity.getId());
        loginLogEntity.setRoleId(userEntity.getRoleId());
        loginLogService.insert(loginLogEntity);
        //该账号已经被登陆
        if (null != SessionManageListener.sessionMap.get(userEntity.getAccount())) {
            //如果不是同一个浏览器
            if (SessionManageListener.sessionMap.get(userEntity.getAccount()).getId() != session.getId())
                // 将已经登陆的信息拿掉,将新的用户登录信息放进去
                ForceLogoutUtils.forceUserLogout(userEntity.getAccount());
        }
        session.setAttribute(Session.USER, userEntity);
        session.setAttribute(Session.GROUPID, userGroupService.findByUserId(userEntity.getId()).getGroupId());
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
