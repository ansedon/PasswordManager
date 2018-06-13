<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>资源分配</title>
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
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="layui-anim layui-anim-up">
<div class="x-body">
    <input type="hidden" value="${childId}" id="id">
    <table class="layui-table" id="table" lay-filter="table"></table>
    <table class="layui-table" id="table1" lay-filter="table1"></table>
</div>

<script id="bar" type="text/html">
    <a class="layui-btn" lay-event="allot">分配</a>
</script>

<script id="bar1" type="text/html">
    <a class="layui-btn layui-btn-danger" lay-event="cancel">移除</a>
</script>

<script>
    $(function () {
        layui.use(['form', 'layer', 'table'], function () {
            $ = layui.jquery;
            form = layui.form
            layer = layui.layer;
            table=layui.table;

            var cols = [[
                {field: 'id', title: 'ID', sort: true, width: 100}
                , {field: 'name', title: '资源名称', sort: true}
                , {field: 'ip', title: 'IP', sort: true}
                , {field: 'typeName', title: '资源类型', sort: true}
                , {field: '', title: '操作', toolbar: '#bar'}
            ]]
            var cols1 = [[
                {field: 'id', title: 'ID', sort: true, width: 100}
                , {field: 'name', title: '资源名称', sort: true}
                , {field: 'ip', title: 'IP', sort: true}
                , {field: 'typeName', title: '资源类型', sort: true}
                , {field: '', title: '操作', toolbar: '#bar1'}
            ]]

            var table1=table.render({
                elem: '#table'
                , url: '/resource/list/allot' //数据接口
                ,where:{groupId:$('#id').val()}
                , page: true //开启分页
                , cols: cols
                ,limit:5
            });

            var table2=table.render({
                elem: '#table1'
                , url: '/resource/list/remove' //数据接口
                ,where:{groupId:$('#id').val()}
                , page: true //开启分页
                , cols: cols1
                ,limit:5
            });

            table.on('tool(table)',function (obj) {
                var data=obj.data;
                layer.confirm("确定要将"+data.name+"分配给该群组？",{title:'提示'},function (index) {
                    layer.close(index);
                    ajax('/resource/list/change',{groupId:$('#id').val(),resId:data.id,isRemove:0},function (msg) {
                        if(msg.result=="OK")
                            layer.msg("分配成功！",{time:500},function () {
                                table1.reload();
                                table2.reload();
                            })
                        else
                            layer.msg(msg.msg,{time:1000});
                    })
                })
            })

            table.on('tool(table1)',function (obj) {
                var data=obj.data;
                layer.confirm("确定要将"+data.name+"从该群组移除？",{title:'提示'},function (index) {
                    layer.close(index);
                    ajax('/resource/list/change',{groupId:$('#id').val(),resId:data.id,isRemove:1},function (msg) {
                        if(msg.result=="OK")
                            layer.msg("移除成功！",{time:500},function () {
                                table1.reload();
                                table2.reload();
                            })
                        else
                            layer.msg(msg.msg,{time:1000});
                    })
                })
            })
        });
    })
</script>
</body>
</html>