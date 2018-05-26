<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>欢迎页面-Password Manager</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
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
                            <div class="layui-carousel x-admin-carousel x-admin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 90px;">
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
                                            <a onclick="tabClick('群组列表')" class="x-admin-backlog-body">
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
                            <td >
                                <a class="x-a" onclick="tabClick('口令列表')">17条口令已经失效！</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>系统信息</legend>
            <div class="layui-field-box">
                <table class="layui-table">
                    <tbody>
                        <tr>
                            <th>服务器版本</th>
                            <td>2.4</td></tr>
                        <tr>
                            <th>服务器地址</th>
                            <td>x.xxx.com</td></tr>
                        <tr>
                            <th>操作系统</th>
                            <td>WINNT</td></tr>
                        <tr>
                            <th>运行环境</th>
                            <td>Apache/2.4.23 (Win32) OpenSSL/1.0.2j mod_fcgid/2.3.9</td></tr>
                        <tr>
                            <th>PHP版本</th>
                            <td>5.6.27</td></tr>
                        <tr>
                            <th>PHP运行方式</th>
                            <td>cgi-fcgi</td></tr>
                        <tr>
                            <th>MYSQL版本</th>
                            <td>5.5.53</td></tr>
                        <tr>
                            <th>ThinkPHP</th>
                            <td>5.0.18</td></tr>
                        <tr>
                            <th>上传附件限制</th>
                            <td>2M</td></tr>
                        <tr>
                            <th>执行时间限制</th>
                            <td>30s</td></tr>
                        <tr>
                            <th>剩余空间</th>
                            <td>86015.2M</td></tr>
                    </tbody>
                </table>
            </div>
        </fieldset>
        <%--<fieldset class="layui-elem-field">--%>
            <%--<legend>开发团队</legend>--%>
            <%--<div class="layui-field-box">--%>
                <%--<table class="layui-table">--%>
                    <%--<tbody>--%>
                        <%--<tr>--%>
                            <%--<th>版权所有</th>--%>
                            <%--<td>xxxxx(xxxx)--%>
                                <%--<a href="http://www.xxx.com/" class='x-a' target="_blank">访问官网</a></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<th>开发者</th>--%>
                            <%--<td>马志斌(113664000@qq.com)</td></tr>--%>
                    <%--</tbody>--%>
                <%--</table>--%>
            <%--</div>--%>
        <%--</fieldset>--%>
        <%--<blockquote class="layui-elem-quote layui-quote-nm">感谢layui,百度Echarts,jquery,本系统由x-admin提供技术支持。</blockquote>--%>
    </div>
        <script>
        var _hmt = _hmt || [];
        (function() {
          var hm = document.createElement("script");
          hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
          var s = document.getElementsByTagName("script")[0]; 
          s.parentNode.insertBefore(hm, s);
        })();
        </script>
    </body>
</html>