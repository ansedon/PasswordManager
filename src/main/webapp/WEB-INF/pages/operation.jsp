<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改记录</title>
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
    <input type="hidden" value="${id}" id="id">
    <table class="layui-table" id="table" lay-filter="table"></table>
</div>

<script id="bar" type="text/html">
    <button class="layui-btn" lay-event="allot">恢复</button>
</script>

<script id="psTpl" type="text/html">
    <span data="{{d.originPwd}}">******</span>
    <a onclick="showPassword(this)">
        <i class="iconfont">&#xe6e6;</i>
    </a>
</script>

<script>
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

    $(function () {
        layui.use(['form', 'layer', 'table'], function () {
            $ = layui.jquery;
            form = layui.form
            layer = layui.layer;
            table = layui.table;

            var cols = [[
                {field: 'id', title: 'ID', sort: true, width: 100}
                , {field: 'originAccount', title: '原账号', sort: true}
                , {field: 'originPwd', title: '原密码', sort: true, templet: '#psTpl'}
                , {field: 'modifierName', title: '修改人', sort: true}
                , {field: 'modifiedTime', title: '修改时间'}
                , {field: '', title: '操作', toolbar: '#bar'}
            ]]

            var table1 = table.render({
                elem: '#table'
                , url: '/password/operation' //数据接口
                , where: {id: $('#id').val()}
                , cols: cols
            });

            table.on('tool(table)', function (obj) {
                var data = obj.data;
                layer.confirm("确定恢复？", {title: '提示'}, function (index) {
                    layer.close(index);
                    ajax('/password/list/recover', {
                        id: $('#id').val(),
                        account: data.originAccount,
                        password: data.originPwd
                    }, function (msg) {
                        if (msg.result == "OK")
                            layer.msg("恢复成功！", {time: 500}, function () {
                                $('.layui-laypage-btn', parent.document).click();
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                            })
                        else
                            layer.msg(msg.msg);
                    })
                })
            })
        });
    })
</script>

</body>
</html>