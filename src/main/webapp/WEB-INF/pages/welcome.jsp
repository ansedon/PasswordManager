<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>欢迎页面-Password Manager</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/xadmin.js"></script>
</head>
<body>
<div class="x-body layui-anim layui-anim-up">
    <blockquote class="layui-elem-quote">欢迎：${user.account}！登录时间:${lastLoginTime}</blockquote>
    <fieldset class="layui-elem-field">
        <legend>数据统计</legend>
        <div class="layui-field-box">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-carousel x-admin-carousel x-admin-backlog" lay-anim=""
                             lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 90px;">
                            <div carousel-item="">
                                <ul class="layui-row layui-col-space10 layui-this">
                                    <li class="layui-col-xs2">
                                        <a onclick="tabClick('资源列表')" class="x-admin-backlog-body">
                                            <h3>资源数</h3>
                                            <p>
                                                <cite>${resNum}</cite></p>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a onclick="tabClick('口令列表')" class="x-admin-backlog-body">
                                            <h3>口令数</h3>
                                            <p>
                                                <cite>${psNum}</cite></p>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a onclick="tabClick('部门列表')" class="x-admin-backlog-body">
                                            <h3>部门数</h3>
                                            <p>
                                                <cite>${groupNum}</cite></p>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a onclick="tabClick('用户管理')" class="x-admin-backlog-body">
                                            <h3>用户数</h3>
                                            <p>
                                                <cite>${userNum}</cite></p>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>系统通知</legend>
        <div class="layui-field-box">
            <table class="layui-table" lay-skin="line">
                <tbody>
                <tr>
                    <td>
                        <a class="x-a" onclick="tabClick('口令列表')">${psExpired}条口令已经失效！</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a class="x-a" onclick="tabClick('口令找回')">${psNumDeleted}条口令已被删除!</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
</div>
</body>
</html>