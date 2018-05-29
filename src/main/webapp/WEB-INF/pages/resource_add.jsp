<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加资源</title>
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
        <input type="hidden" name="id" lay-verify="required" value="0" class="layui-input">
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>资源名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>ip</label>
            <div class="layui-input-inline">
                <input type="text" id="ip" name="ip" lay-verify="required"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>资源类型</label>
            <div class="layui-input-inline">
                <select class="layui-select" name="typeId" id="typeId" lay-verify="selected"></select>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">资源说明</label>
            <div class="layui-input-block">
                <textarea type="textarea" id="description" name="description"
                          class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">url</label>
            <div class="layui-input-inline">
                <input type="text" id="url" name="url"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">位置</label>
            <div class="layui-input-inline">
                <input type="text" id="location" name="location"
                        class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码政策</label>
            <div class="layui-input-inline">
                <input type="text" id="pwdPolicy" name="pwdPolicy"
                        class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密钥</label>
            <div class="layui-input-block">
                <textarea type="text" id="superkey" name="superkey"
                          class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-filter="add" lay-submit>
                添加
            </button>
            <button class="layui-btn" id="cancel">
                取消
            </button>
        </div>
    </form>
</div>
<script>
    $(function  () {
        layui.use(['form', 'layer','mymod'], function () {
            $ = layui.jquery;
            form = layui.form
            layer = layui.layer;
            mymod = layui.mymod;
            ajax('/resource/type/all', null, function (res) {
                mymod.renderSelect("typeId", res.data, "id", "name", "资源类型", $('#typeId').attr('data'));
            })

            form.verify({
                selected: function (value) {
                    if (value== 0) {
                        return '请选择一项';
                    }
                }
            });

            //监听提交
            form.on('submit(add)', function (data) {
                ajax('/resource/list/update', data.field, function (res) {
                    if (res.result == "OK") {
                        layer.msg('添加成功!', {time: 500}, function () {
                            //获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            $(".layui-laypage-btn",parent.document).click();
                        })
                    } else {
                        layer.msg(res.msg, {time: 500});
                    }
                })
                return false;
            });

            $('#cancel').click(function () {
                //获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
                return false;
            })

        });
    })
</script>
</body>
</html>