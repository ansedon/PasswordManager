<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Password Manager</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="/lib/jstree/themes/default/style.min.css"/>
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script type="text/javascript" src="/js/mymod.js"></script>
    <script type="text/javascript" src="/js/resource_list.js"></script>
    <script type="text/javascript" src="/lib/jstree/jstree.min.js"></script>
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
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md2">
            <div class="layui-form-item" style="text-align:center">
                <h2>资源类型</h2>
            </div>
            <div id="jstree"></div>
        </div>
        <div class="layui-col-md10">
            <div class="layui-row">
                <form class="layui-form layui-col-md12 x-so">
                    <input type="text" name="name" placeholder="请输入资源名" autocomplete="off" class="layui-input">
                    <input type="hidden" id="typeId" class="layui-input" value="0" name="typeId">
                    <input class="layui-input" placeholder="开始日" name="startTime" id="start">
                    <input class="layui-input" placeholder="截止日" name="endTime" id="end">
                    <button class="layui-btn" lay-submit lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
                </form>
            </div>
            <c:if test="${privilege.resAdd==1}">
                <xblock>
                    <button class="layui-btn" onclick="layer_show('添加','/resource/list/add')"><i class="layui-icon"></i>添加
                    </button>
                </xblock>
            </c:if>
            <table class="layui-table" id="resourceTable" lay-filter="resourceTable"></table>
        </div>
    </div>
</div>

<div id="detail" style="display: none;padding-top:20px;">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">资源名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">ip</label>
            <div class="layui-input-inline">
                <input type="text" id="ip" name="ip" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">资源类型</label>
            <div class="layui-input-inline">
                <input type="text" id="type" name="type" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">资源说明</label>
            <div class="layui-input-block">
                <textarea type="textarea" id="description" name="description" disabled
                          class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">url</label>
            <div class="layui-input-inline">
                <input type="text" id="url" name="url" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">位置</label>
            <div class="layui-input-inline">
                <input type="text" id="location" name="location" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">口令政策</label>
            <div class="layui-input-inline">
                <input type="text" id="pwdPolicy" name="pwdPolicy" disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密钥</label>
            <div class="layui-input-inline">
                <input type="text" id="superkey" name="superkey"  disabled class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-inline">
                <input type="text" id="createTime" name="createTime"  disabled class="layui-input">
            </div>
        </div>
    </form>
</div>

<script id="bar" type="text/html">
    <a title="查看" lay-event="view">
        <i class="iconfont">&#xe6e6;</i>
    </a>
    <c:if test="${privilege.resEdit==1}">
        <a title="编辑" lay-event="edit">
            <i class="layui-icon">&#xe642;</i>
        </a>
    </c:if>
    <c:if test="${privilege.resDelete==1}">
        <a title="删除" lay-event="delete">
            <i class="layui-icon">&#xe640;</i>
        </a>
    </c:if>
</script>
</body>
</html>