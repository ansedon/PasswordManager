package controller;

import json.*;
import model.OperationEntity;
import model.PasswordEntity;
import model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.OperationService;
import service.PasswordService;
import service.UserService;
import tool.PasswordUtils.Nbvcxz;
import tool.PasswordUtils.resources.Configuration;
import tool.PasswordUtils.resources.ConfigurationBuilder;
import tool.PasswordUtils.resources.Dictionary;
import tool.PasswordUtils.resources.DictionaryBuilder;
import tool.PasswordUtils.scoring.Result;
import tool.RSAUtils;
import tool.ReflectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PasswordController {
    @Value("${privateKey}")
    private String privateKey;
    @Value("${publicKey}")
    private String publicKey;

    @Autowired
    PasswordService passwordService;

    @Autowired
    OperationService operationService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/password/eval", method = RequestMethod.GET)
    public String eval() {
        return "password_eval";
    }

    public static class PasswordRequest {
        public String password;
        public String exclude;
    }

    @ResponseBody
    @RequestMapping(value = "/password/eval/one", method = RequestMethod.POST)
    public ParentResponse evalPassword(@RequestBody PasswordRequest passwordRequest) {
        ParentResponse resp = new ParentResponse();
        if (passwordRequest.password == null || passwordRequest.password == "") {
            resp.result = "Fail";
            resp.msg = "口令为空！";
            return resp;
        }
        String password = passwordRequest.password.trim();
        List<Dictionary> dictionaryList = ConfigurationBuilder.getDefaultDictionaries();
        if (passwordRequest.exclude != null && passwordRequest.exclude != "") {
            String[] stringList = passwordRequest.exclude.split(",|，");
            DictionaryBuilder dictionaryBuilder = new DictionaryBuilder()
                    .setDictionaryName("exclude")
                    .setExclusion(true);
            for (int i = 0; i < stringList.length; i++) {
                dictionaryBuilder.addWord(stringList[i], 0);
            }
            dictionaryList.add(dictionaryBuilder.createDictionary());
        }
        Configuration configuration = new ConfigurationBuilder()
                .setMinimumEntropy(40d)
                .setDictionaries(dictionaryList)
                .createConfiguration();

        Nbvcxz nbvcxz = new Nbvcxz(configuration);
        Result result = nbvcxz.estimate(password);
        resp.result = "OK";
        resp.msg = "";
        resp.count = result.getEntropy().intValue();
        if (result.getFeedback().getSuggestion().size() == 0)
            resp.msg = "无";
        else {
            List<String> strs = result.getFeedback().getSuggestion();
            for (int i = 0; i < strs.size(); i++) {
                String s = (i + 1) + ". " + strs.get(i) + "\n";
                resp.msg += s;
            }
        }
        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/password/eval/save", method = RequestMethod.POST)
    public ParentResponse saveExclude(@RequestBody PasswordRequest passwordRequest,HttpServletRequest request) {
        ParentResponse resp = new ParentResponse();
        if (passwordRequest.exclude == null || passwordRequest.exclude == "") {
            resp.result = "Fail";
            resp.msg = "排除字典为空！";
            return resp;
        }
        String exclude = passwordRequest.exclude.trim();
        String[] stringList = exclude.split(",|，");
        String path=this.getClass().getResource("/dictionaries/customs.txt").getPath();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path, true);
            for (String s : stringList)
                fos.write((s + "\n").getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.result = "OK";
        resp.msg = "添加成功！";
        return resp;
    }

    @RequestMapping(value = "/password/retrive", method = RequestMethod.GET)
    public String retrive() {
        return "password_retrive";
    }

    @ResponseBody
    @RequestMapping(value = "/password/retrive/one", method = RequestMethod.POST)
    public ParentResponse retrivePassword(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "找回失败！";
            return resp;
        }
        int result = passwordService.retrivePasswordById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "找回失败！";
        return resp;
    }

    @RequestMapping(value = "/password/operation/{id}", method = RequestMethod.GET)
    public String operation(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "operation";
    }

    @ResponseBody
    @RequestMapping(value = "/password/operation", method = RequestMethod.GET)
    public ParentResponse getOperation(HttpServletRequest request) {
        IdRequest idRequest = new IdRequest();
        ReflectUtils.convert(idRequest, request);
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<OperationResponse>();
        if (idRequest.id <= 0) {
            resp.result = "Fail";
            resp.msg = "参数错误！";
            return resp;
        }
        List<OperationEntity> operationEntities = operationService.findByPwdId(idRequest.id);
        for (OperationEntity operationEntity : operationEntities) {
            OperationResponse operationResponse = new OperationResponse(operationEntity);
            String ps = "";
            try {
                ps = RSAUtils.decryptByPrivateKey(operationEntity.getOriginPwd(), privateKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ps != "")
                operationResponse.originPwd = ps;
            operationResponse.modifierName = userService.findUserEntityById(operationEntity.getModifierId()).getAccount();
            resp.data.add(operationResponse);
        }
        resp.result = "OK";
        return resp;
    }


    @RequestMapping(value = "/password/list", method = RequestMethod.GET)
    public String index() {
        return "password_list";
    }

    @ResponseBody
    @RequestMapping(value = "/password/list/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody PasswordEntity passwordEntity, HttpSession session) {
        ParentResponse resp = new ParentResponse();
        UserEntity user = (UserEntity) session.getAttribute(Session.USER);
        if (passwordEntity.getId() != 0) {
            PasswordEntity passwordEntity1 = passwordService.findPasswordEntityById(passwordEntity.getId());
            passwordEntity.setCreatorId(passwordEntity1.getCreatorId());
            passwordEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            passwordEntity.setModifierId(user.getId());
            passwordEntity.setResourceId(passwordEntity1.getResourceId());
            passwordEntity.setCreateTime(passwordEntity1.getCreateTime());
            passwordEntity.setIsDeleted(passwordEntity1.getIsDeleted());
            //单独修改过期时间
            if (passwordEntity.getAccount() == null) {
                passwordEntity.setAccount(passwordEntity1.getAccount());
                passwordEntity.setPassword(passwordEntity1.getPassword());
                passwordService.update(passwordEntity);
            } else {
                String originAccount = passwordEntity1.getAccount();
                String originPs = passwordEntity1.getPassword();
                String ps = "";
                try {
                    ps = RSAUtils.decryptByPrivateKey(passwordEntity1.getPassword(), privateKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!passwordEntity.getPassword().equals(ps) || !passwordEntity.getAccount().equals(passwordEntity1.getAccount())) {
                    try {
                        passwordEntity.setPassword(RSAUtils.encryptByPublicKey(passwordEntity.getPassword(), publicKey));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    passwordService.update(passwordEntity);
                    //插入修改记录
                    OperationEntity operationEntity = new OperationEntity();
                    operationEntity.setPwdId(passwordEntity.getId());
                    operationEntity.setOriginAccount(originAccount);
                    operationEntity.setOriginPwd(originPs);
                    operationEntity.setModifierId(user.getId());
                    operationEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
                    operationService.update(operationEntity);
                }
            }
        } else {
            try {
                passwordEntity.setPassword(RSAUtils.encryptByPublicKey(passwordEntity.getPassword(), publicKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
            passwordEntity.setCreatorId(user.getId());
            passwordEntity.setModifierId(user.getId());
            passwordEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
            passwordEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            passwordEntity.setIsDeleted((byte) 0);
            passwordService.update(passwordEntity);
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/password/list/recover", method = RequestMethod.POST)
    public ResponseEntity<Object> recover(@RequestBody PasswordEntity passwordEntity, HttpSession session) {
        ParentResponse resp = new ParentResponse();
        UserEntity user = (UserEntity) session.getAttribute(Session.USER);
        PasswordEntity passwordEntity1 = passwordService.findPasswordEntityById(passwordEntity.getId());
        passwordEntity.setCreatorId(passwordEntity1.getCreatorId());
        passwordEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
        passwordEntity.setModifierId(user.getId());
        passwordEntity.setResourceId(passwordEntity1.getResourceId());
        passwordEntity.setCreateTime(passwordEntity1.getCreateTime());
        passwordEntity.setIsDeleted(passwordEntity1.getIsDeleted());
        passwordEntity.setExpireTime(passwordEntity1.getExpireTime());
        passwordService.update(passwordEntity);
        //插入修改记录
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setPwdId(passwordEntity1.getId());
        operationEntity.setOriginAccount(passwordEntity1.getAccount());
        operationEntity.setOriginPwd(passwordEntity1.getPassword());
        operationEntity.setModifierId(user.getId());
        operationEntity.setModifiedTime(new Timestamp(System.currentTimeMillis()));
        operationService.update(operationEntity);
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/password/list/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getPasswordList(HttpServletRequest request, HttpSession session) {
        PasswordParam passwordParam = new PasswordParam();
        ReflectUtils.convert(passwordParam, request);
        if (passwordParam.page > 0)
            passwordParam.page -= 1;
        passwordParam.groupId = (int) session.getAttribute(Session.GROUPID);
        ParentResponse resp = new ParentResponse();
        resp.data = new ArrayList<ResourceTypeResponse>();

        int count = passwordService.countByCondition(passwordParam);
        if (count > 0)
            resp.count = count;
        List<PasswordEntity> passwordEntities = passwordService.findByCondition(passwordParam);
        if (passwordEntities != null) {
            for (PasswordEntity ps : passwordEntities) {
                try {
                    ps.setPassword(RSAUtils.decryptByPrivateKey(ps.getPassword(), privateKey));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PasswordResponse passwordResponse = new PasswordResponse(ps);
                passwordResponse.resourceName = ps.getResourceByResourceId().getName();
                passwordResponse.modifierName = ps.getUserByModifierId().getAccount();
                resp.data.add(passwordResponse);
            }
        }
        resp.result = "OK";
        resp.msg = "操作成功！";
        return new ResponseEntity<Object>(resp, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/password/list/delete", method = RequestMethod.POST)
    public ParentResponse deletePassword(@RequestBody IdRequest idRequest) {
        ParentResponse resp = new ParentResponse();
        int id = idRequest.id;
        if (id <= 0) {
            resp.result = "Fail";
            resp.msg = "删除失败！";
            return resp;
        }
        int result = passwordService.deletePasswordById(id);
        if (result > 0)
            resp.result = "OK";
        else
            resp.msg = "删除失败！";
        return resp;
    }
}
