$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
        layer=layui.layer;
        var cols = [[
            {field: 'id', title: 'ID', sort: true, width: 100}
            , {field: 'name', title: '角色名称', sort: true}
            , {field: 'description', title: '角色介绍'}
            , {field: 'level', title: '角色级别'}
            , {field: 'createTime', title: '创建时间', sort: true}
            , {field: '', title: '操作', toolbar: '#bar'}
        ]]

        table.render({
            elem: '#roleTable'
            , url: '/manage/role/all' //数据接口
            , page: true //开启分页
            , cols: cols
        });

        mymod.renderLaydate('start', 'end', 'datetime');

        $('#addBtn').click(function () {
            layer_show("添加角色",'/manage/role/add');
        })

        //监听搜索
        form.on('submit(search)', function (data) {
            table.reload('roleTable', {
                url: '/manage/role/all' //数据接口
                , where: data.field
                , page: true //开启分页
                , cols: cols
            })
            return false;
        })

        //监听工具条
        table.on('tool(roleTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') { //删除
                layer.confirm('是否删除？', {icon: 3, title: '提示'}, function (index) {
                    ajax('/manage/role/delete', {id: data.id}, function (msg) {
                        layer.close(index);
                        if (msg.result == "OK")
                            layer.msg("删除成功！", {time: 500}, function () {
                                $(".layui-laypage-btn").click();
                            })
                        else
                            layer.msg(msg.msg);
                    })
                });
            } else if (layEvent === 'edit') { //编辑
                layer_show("编辑角色",'/manage/role/edit/'+data.id);
            }
        });
    });
})
