package controller;

import Json.ParentResponse;
import Json.Session;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;
import tool.RSAUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

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
    public ParentResponse checkLogin(@RequestBody LoginRequest loginRequest,HttpSession session) {
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
                if (!password.equals(RSAUtils.decryptByPrivateKey(userEntity.getPassword(),privateKey))) {
                    checkLoginResponse.result = "FAIL";
                    checkLoginResponse.msg = "密码错误！";
                    return checkLoginResponse;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.setAttribute(Session.USER,userEntity);
        checkLoginResponse.result = "OK";
        return checkLoginResponse;
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(HttpSession session){
         session.invalidate();
        return "login";
    }


}
