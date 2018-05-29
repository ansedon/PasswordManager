<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>口令找回</title>
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
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a>口令管理</a>
        <a>
          <cite>口令找回</cite></a>
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
                <select name="resId" id="resId" lay-verify="select"></select>
            </div>
            <input class="layui-input" placeholder="开始日" name="startTime" id="start">
            <input class="layui-input" placeholder="截止日" name="endTime" id="end">
            <button class="layui-btn" lay-submit lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
        </form>
    </div>
    <xblock>
    </xblock>
    <table class="layui-table" id="passwordTable" lay-filter="passwordTable"></table>
</div>

<script id="bar" type="text/html">
    <a class="layui-btn" lay-event="retrive">找回</a>
</script>

<script id="psTpl" type="text/html">
    <span data="{{d.password}}">******</span>
    <a onclick="showPassword(this)">
        <i class="iconfont">&#xe6e6;</i>
    </a>
</script>

<script>
    $(function () {
        layui.use(['form', 'table', 'laydate'], function () {
            var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
            layer = layui.layer;
            var cols = [[
                {field: 'id', title: 'ID', sort: true, width: 100}
                , {field: 'account', title: '账户', sort: true}
                , {field: 'password', title: '口令', minWidth: 200, templet: "#psTpl"}
                , {field: 'resourceName', title: '资源名称', sort: true}
                , {field: 'modifiedTime', title: '删除时间', sort: true}
                , {field: '', title: '操作', toolbar: '#bar'}
            ]]

            table.render({
                elem: '#passwordTable'
                , url: '/password/list/all' //数据接口
                ,where:{isDeleted:1}
                , page: true //开启分页
                , cols: cols
            });

            mymod.renderLaydate('start', 'end', 'datetime');

            ajax("/resource/all", null, function (res) {
                mymod.renderSelect('resId', res.data, 'id', 'name', '资源名称');
            })

            //监听搜索
            form.on('submit(search)', function (data) {
                data.field['isDeleted']=1;
                table.reload('passwordTable', {
                    url: '/password/list/all' //数据接口
                    , where: data.field
                    , page: true //开启分页
                    , cols: cols
                })
                return false;
            })

            //监听工具条
            table.on('tool(passwordTable)', function (obj) {
                var data = obj.data;
                layer.confirm('是否找回？', {icon: 3, title: '提示'}, function (index) {
                    ajax('/password/retrive/one', {id: data.id}, function (msg) {
                        layer.close(index);
                        if (msg.result == "OK")
                            layer.msg("成功找回！", {time: 500}, function () {
                                $(".layui-laypage-btn").click();
                            })
                        else
                            layer.msg(msg.msg);
                    })
                });
            });
        });
    })

    function showPassword(that) {
        var span = $(that).parent('div').find('span');
        //显示
        if (span.attr("isShow") == null || span.attr("isShow") == 0) {
            span.html(span.attr("data"));
            span.attr("isShow", 1);
        }
        else {
            span.attr("isShow", 0);
            span.html("******");
        }
    }
</script>

</body>
</html>