<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加角色</title>
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
<div class="x-body">
    <form class="layui-form layui-form-pane">
        <input type="hidden" name="id" lay-verify="required" value="${role.id}" class="layui-input">
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>角色名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required" class="layui-input" value="${role.name}">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">角色介绍</label>
            <div class="layui-input-block">
                <textarea type="textarea" id="description" name="description"
                          class="layui-textarea">${role.description}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>角色等级</label>
            <div class="layui-input-inline">
                <input type="text" id="level" name="level"
                       class="layui-input" lay-verify="level" value="${role.level}">
            </div>
            <div class="layui-form-mid layui-word-aux">值越小权限越高</div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">
                拥有权限
            </label>
            <table  class="layui-table layui-input-block">
                <tbody>
                <tr>
                    <td>
                        资源类型
                    </td>
                    <td>
                        <div class="layui-input-block">
                            <input name="typeAdd" lay-skin="primary" type="checkbox" title="添加" value="1" <c:if test="${privilege.typeAdd=='1'}">checked</c:if>>
                            <input name="typeEdit" lay-skin="primary" type="checkbox" value="1" title="修改" <c:if test="${privilege.typeEdit=='1'}">checked</c:if>>
                            <input name="typeDelete" lay-skin="primary" type="checkbox" value="1" title="删除" <c:if test="${privilege.typeDelete=='1'}">checked</c:if>>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        资源
                    </td>
                    <td>
                        <div class="layui-input-block">
                            <input name="resAdd" lay-skin="primary" type="checkbox" title="添加" value="1" <c:if test="${privilege.resAdd=='1'}">checked</c:if>>
                            <input name="resEdit" lay-skin="primary" type="checkbox" value="1" title="修改" <c:if test="${privilege.resEdit=='1'}">checked</c:if>>
                            <input name="resDelete" lay-skin="primary" type="checkbox" value="1" title="删除" <c:if test="${privilege.resDelete=='1'}">checked</c:if>>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        口令
                    <td>
                        <div class="layui-input-block">
                            <input name="pawAdd" lay-skin="primary" type="checkbox" title="添加" value="1" <c:if test="${privilege.pawAdd=='1'}">checked</c:if>>
                            <input name="pawEdit" lay-skin="primary" type="checkbox" value="1" title="修改" <c:if test="${privilege.pawEdit=='1'}">checked</c:if>>
                            <input name="pawDelete" lay-skin="primary" type="checkbox" value="1" title="删除" <c:if test="${privilege.pawDelete=='1'}">checked</c:if>>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        用户
                    </td>
                    <td>
                        <div class="layui-input-block">
                            <input name="userAdd" lay-skin="primary" type="checkbox" title="添加" value="1" <c:if test="${privilege.userAdd=='1'}">checked</c:if>>
                            <input name="userEdit" lay-skin="primary" type="checkbox" value="1" title="修改" <c:if test="${privilege.userEdit=='1'}">checked</c:if>>
                            <input name="userDelete" lay-skin="primary" type="checkbox" value="1" title="删除" <c:if test="${privilege.userDelete=='1'}">checked</c:if>>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        部门
                    </td>
                    <td>
                        <div class="layui-input-block">
                            <input name="groupAdd" lay-skin="primary" type="checkbox" title="添加" value="1" <c:if test="${privilege.groupAdd=='1'}">checked</c:if>>
                            <input name="groupEdit" lay-skin="primary" type="checkbox" value="1" title="修改" <c:if test="${privilege.groupEdit=='1'}">checked</c:if>>
                            <input name="groupDelete" lay-skin="primary" type="checkbox" value="1" title="删除" <c:if test="${privilege.groupDelete=='1'}">checked</c:if>>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="layui-form-item">
            <button class="layui-btn" lay-filter="edit" lay-submit>
                修改
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

            form.verify({
                selected: function (value) {
                    if (value== 0) {
                        return '请选择一项';
                    }
                },
                level: [/^[0-9]+$/,'角色等级为正数']
            });

            //监听提交
            form.on('submit(edit)', function (data) {
                ajax('/manage/role/update', data.field, function (res) {
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