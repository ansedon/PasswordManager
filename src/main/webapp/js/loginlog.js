$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
        layer = layui.layer;
        var cols = [[
            {field: 'id', title: 'ID', sort: true, width: 100}
            , {field: 'userName', title: '用户名称', sort: true}
            , {field: 'roleName', title: '用户角色', srot: true}
            , {field: 'ip', title: '登录IP', sort: true}
            , {field: 'osInfo', title: '操作系统', sort: true}
            , {field: 'browserInfo', title: '浏览器', sort: true}
            , {field: 'createTime', title: '登录时间'}
        ]]

        table.render({
            elem: '#logTable'
            , url: '/manage/loginlog/all' //数据接口
            , page: true //开启分页
            , cols: cols
        });

        mymod.renderLaydate('start', 'end', 'datetime');

        ajax("/manage/user/select", null, function (res) {
            mymod.renderSelect('id', res.data, 'id', 'account', '用户名称');
        })

        //监听搜索
        form.on('submit(search)', function (data) {
            table.reload('logTable', {
                url: '/manage/loginlog/all' //数据接口
                , where: data.field
                , page: true //开启分页
                , cols: cols
            })
            return false;
        })
    })
})