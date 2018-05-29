<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>群组管理</title>
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
    <script type="text/javascript" src="/js/group.js"></script>
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
        <a>群组管理</a>
        <a>
          <cite>群组列表</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md2">
            <div class="layui-form-item" style="text-align:center">
                <h2>部门结构</h2>
            </div>
            <div id="jstree"></div>
        </div>
        <div class="layui-col-md10">
            <xblock>
                <button class="layui-btn" id="addBtn">添加</button>
                <button class="layui-btn" id="edit">
                    修改
                </button>
                <button class="layui-btn layui-btn-danger" id="delete">
                    删除
                </button>
                <button class="layui-btn" id="allot">
                    分配资源
                </button>
            </xblock>
            <form class="layui-form layui-form-pane">
                <input type="hidden" name="id" id="nodeId" class="layui-input">
                <div class="layui-form-item">
                    <label class="layui-form-label">部门名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="name" name="name" disabled class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">部门介绍</label>
                    <div class="layui-input-block">
                        <textarea type="text" id="description" name="description" disabled
                                  class="layui-textarea"></textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">部门位置</label>
                    <div class="layui-input-block">
                        <input type="text" id="location" name="location" disabled class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">创建人</label>
                    <div class="layui-input-inline">
                        <input type="text" id="creatorName" name="creatorName" disabled class="layui-input">
                    </div>
                    <label class="layui-form-label">创建时间</label>
                    <div class="layui-input-inline">
                        <input type="text" id="createTime" name="createTime" disabled class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">最后修改者</label>
                    <div class="layui-input-inline">
                        <input type="text" id="modifier" name="modifier" disabled class="layui-input">
                    </div>
                    <label class="layui-form-label">修改时间</label>
                    <div class="layui-input-inline">
                        <input type="text" id="modifiedTime" name="modifiedTime" disabled class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">

                </div>
            </form>
        </div>
    </div>
</div>
<div id="detail" style="display:none;padding-top: 20px">
    <form class="layui-form">
        <input type="hidden" name="id" id="id1" value="0" class="layui-input">
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>部门名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name1" name="name" lay-verify="required" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">部门介绍</label>
            <div class="layui-input-inline">
                        <textarea type="text" id="description1" name="description"
                                  class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">部门位置</label>
            <div class="layui-input-inline">
                <input type="text" id="location1" name="location" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="text-align:center">
            <button class="layui-btn" lay-filter="add" lay-submit id="add">
                添加
            </button>
            <button class="layui-btn" id="cancel">
                取消
            </button>
        </div>
    </form>
</div>
</body>
</html>