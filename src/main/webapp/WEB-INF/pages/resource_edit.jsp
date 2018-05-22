<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑资源</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script type="text/javascript" src="/js/mymod.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="x-body layui-anim layui-anim-up">
    <form class="layui-form">
        <input type="hidden" name="id" lay-verify="required" value="${resourceEntity.id}" class="layui-input">
        <div class="layui-form-item">
            <label class="layui-form-label">name</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required" class="layui-input"
                       value="${resourceEntity.name}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">ip</label>
            <div class="layui-input-inline">
                <input type="text" id="ip" name="ip" lay-verify="required" value="${resourceEntity.ip}"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">type</label>
            <div class="layui-input-inline">
                <input type="text" id="type" name="type" lay-verify="required"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">description</label>
            <div class="layui-input-block">
                <textarea type="textarea" id="description" name="description"
                          class="layui-textarea">${resourceEntity.description}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">url</label>
            <div class="layui-input-inline">
                <input type="text" id="url" name="url"  value="${resourceEntity.url}"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">location</label>
            <div class="layui-input-inline">
                <input type="text" id="location" name="location"
                       value="${resourceEntity.location}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">password policy</label>
            <div class="layui-input-inline">
                <input type="text" id="pwdPolicy" name="pwdPolicy"
                       value="${resourceEntity.pwdPolicy}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">superkey</label>
            <div class="layui-input-block">
                <textarea type="text" id="superkey" name="superkey"
                          class="layui-textarea">${resourceEntity.superkey}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <%--<label for="L_repass" class="layui-form-label">--%>
            <%--</label>--%>
            <button class="layui-btn" lay-filter="edit" lay-submit="">
                修改
            </button>
            <button class="layui-btn" lay-filter="cancel" lay-submit="">
                取消
            </button>
        </div>
    </form>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer;

        //自定义验证规则
        form.verify({
            nikename: function (value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            }
            , pass: [/(.+){6,12}$/, '密码必须6到12位']
            , repass: function (value) {
                if ($('#L_pass').val() != $('#L_repass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        //监听提交
        form.on('submit(add)', function (data) {
            console.log(data);
            //发异步，把数据提交给php
            layer.alert("增加成功", {icon: 6}, function () {
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
            });
            return false;
        });


    });
</script>
</body>
</html>