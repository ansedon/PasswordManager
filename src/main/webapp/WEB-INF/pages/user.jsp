<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>口令列表</title>
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
  <script type="text/javascript" src="/js/user.js"></script>
  <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
  <!--[if lt IE 9]>
  <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
  <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body class="layui-anim layui-anim-up">
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a>管理员管理</a>
        <a>
          <cite>用户管理</cite></a>
      </span>
  <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
     href="javascript:location.replace(location.href);" title="刷新">
    <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
  <div class="layui-row">
    <form class="layui-form layui-col-md12 x-so">
      <input type="text" name="account" placeholder="请输入账户" autocomplete="off" class="layui-input">
      <div class="layui-input-inline">
        <select name="roleId" id="roleId" lay-verify="select"></select>
      </div>
      <input class="layui-input" placeholder="开始日" name="startTime" id="start">
      <input class="layui-input" placeholder="截止日" name="endTime" id="end">
      <button class="layui-btn" lay-submit lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
    </form>
  </div>
  <xblock>
    <button class="layui-btn" id="addBtn"><i class="layui-icon"></i>添加
    </button>
  </xblock>
  <table class="layui-table" id="userTable" lay-filter="userTable"></table>
</div>

<div id="detail" style="display: none;padding-top:20px;">
  <form class="layui-form">
    <input type="hidden" name="id" id="id" value="0" class="layui-input">
    <div class="layui-form-item">
      <label class="layui-form-label">账号</label>
      <div class="layui-input-inline">
        <input type="text" id="account" name="account" lay-verify="required" class="layui-input">
      </div>
    </div>
    <div class="layui-col-md12 x-so">
      <label class="layui-form-label">密码</label>
      <div class="layui-input-inline">
        <input type="password" id="password0" name="password" lay-verify="required" class="layui-input"
               isshow="0">
        <a class="layui-btn" onclick="show(this,0)">
          <i class="iconfont">&#xe6e6;</i>
        </a>
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">邮箱</label>
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="email" name="email">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">手机号</label>
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="phone" name="phone">
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">所属角色</label>
      <div class="layui-input-inline">
        <select name="roleId" id="roleId1" lay-verify="select"></select>
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">位置</label>
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="location" name="location">
      </div>
    </div>
    <div class="layui-form-item" style="text-align:center">
      <button class="layui-btn" lay-filter="add" lay-submit id="add">
        修改
      </button>
      <button class="layui-btn" id="cancel">
        取消
      </button>
    </div>
  </form>
</div>

<%--<div id="addTab" style="display: none;padding-top:20px;">--%>
  <%--<form class="layui-form">--%>
    <%--<div class="layui-form-item">--%>
      <%--<label class="layui-form-label">账号</label>--%>
      <%--<div class="layui-input-inline">--%>
        <%--<input type="text" id="account" name="account" lay-verify="required" class="layui-input">--%>
      <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-col-md12 x-so">--%>
      <%--<label class="layui-form-label">密码</label>--%>
      <%--<div class="layui-input-inline">--%>
        <%--<input type="password" id="password0" name="password" lay-verify="required" class="layui-input"--%>
               <%--isshow="0">--%>
        <%--<a class="layui-btn" onclick="show(this,0)">--%>
          <%--<i class="iconfont">&#xe6e6;</i>--%>
        <%--</a>--%>
      <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
      <%--<label class="layui-form-label">邮箱</label>--%>
      <%--<div class="layui-input-inline">--%>
        <%--<input type="text" class="layui-input" id="email" name="email">--%>
      <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
      <%--<label class="layui-form-label">手机号</label>--%>
      <%--<div class="layui-input-inline">--%>
        <%--<input type="text" class="layui-input" id="phone" name="phone">--%>
      <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
      <%--<label class="layui-form-label">所属角色</label>--%>
      <%--<div class="layui-input-inline">--%>
        <%--<select name="roleId" id="roleId1" lay-verify="select"></select>--%>
      <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item">--%>
      <%--<label class="layui-form-label">位置</label>--%>
      <%--<div class="layui-input-inline">--%>
        <%--<input type="text" class="layui-input" id="location" name="location">--%>
      <%--</div>--%>
    <%--</div>--%>
    <%--<div class="layui-form-item" style="text-align:center">--%>
      <%--<button class="layui-btn" lay-filter="add" lay-submit id="add">--%>
        <%--添加--%>
      <%--</button>--%>
      <%--<button class="layui-btn" id="cancel">--%>
        <%--取消--%>
      <%--</button>--%>
    <%--</div>--%>
  <%--</form>--%>
<%--</div>--%>

<script id="bar" type="text/html">
  <a title="编辑" lay-event="edit">
    <i class="layui-icon">&#xe642;</i>
  </a>
  <a title="删除" lay-event="delete">
    <i class="layui-icon">&#xe640;</i>
  </a>
</script>

<script id="psTpl" type="text/html">
  <span data="{{d.password}}">******</span>
  <a onclick="showPassword(this)">
    <i class="iconfont">&#xe6e6;</i>
  </a>
</script>

</body>
</html>