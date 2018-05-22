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
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script type="text/javascript" src="/js/mymod.js"></script>
    <script type="text/javascript" src="/js/resource_list.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="layui-anim layui-anim-up">
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a>资源管理</a>
        <a>
          <cite>资源列表</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
            <input type="text" name="name" placeholder="请输入资源名" autocomplete="off" class="layui-input">
            <input class="layui-input" placeholder="开始日" name="start" id="start">
            <input class="layui-input" placeholder="截止日" name="end" id="end">
            <div class="layui-input-inline">
                <select name="select" id="select" lay-verify="select"></select>
            </div>
            <button class="layui-btn" lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
        </form>
    </div>
    <xblock>
        <button class="layui-btn" onclick="x_admin_show('添加','./resource_edit.jsp',600,400)"><i class="layui-icon"></i>添加
        </button>
    </xblock>
    <table class="layui-table" id="resourceTable" lay-filter="resourceTable"></table>
</div>

<div id="detail" style="display: none;padding-top:20px;">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">name</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">ip</label>
            <div class="layui-input-inline">
                <input type="text" id="ip" name="ip" lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">type</label>
            <div class="layui-input-inline">
                <input type="text" id="type" name="type"  lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">description</label>
            <div class="layui-input-block">
                <textarea type="textarea" id="description" name="description"  lay-verify="required" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">url</label>
            <div class="layui-input-inline">
                <input type="text"id="url" name="url"  lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">location</label>
            <div class="layui-input-inline">
                <input type="text" id="location" name="location"  lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">password policy</label>
            <div class="layui-input-inline">
                <input type="text" id="pwdPolicy" name="pwdPolicy"  lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">superkey</label>
            <div class="layui-input-inline">
                <input type="text" id="superkey" name="superkey"  lay-verify="required" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">create time</label>
            <div class="layui-input-inline">
                <input type="text" id="createTime" name="createTime"  lay-verify="required" disabled class="layui-input">
            </div>
        </div>
    </form>
</div>

<script id="bar" type="text/html">
    <a title="查看" lay-event="view">
        <i class="iconfont">&#xe6e6;</i>
    </a>
    <a title="编辑" lay-event="edit">
        <i class="layui-icon">&#xe642;</i>
    </a>
    <a title="删除" lay-event="delete">
        <i class="layui-icon">&#xe640;</i>
    </a>
</script>
</body>
</html>