$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
        layer=layui.layer;
        var cols = [[
            {field: 'id', title: 'ID', sort: true, width: 100}
            ,{field: 'name', title: 'Name', sort: true,templet:function(d){
                return "<a href=\"javascript:void(0)\" class=\"layui-table-link\" onclick=\"layer_show(\'"+d.name+"-资源分配\',\'\/group\/list\/"+d.id+"\')\">"+d.name+"</a>";
            }}
        , {field: 'fatherGroupName', title: 'Father Group'}
            , {field: 'description', title: 'Description'}
            , {field: 'location', title: 'Location'}
            , {field: 'creatorName', title: 'Creator'}
            , {field: 'createTime', title: 'Create Time', sort: true}
            , {field: '', title: 'Opertion', toolbar: '#bar'}
        ]]

        table.render({
            elem: '#groupTable'
            , url: '/group/list/all' //数据接口
            , page: true //开启分页
            , cols: cols
        });

        mymod.renderLaydate('start', 'end', 'datetime');

        ajax("/group/all", null, function (res) {
            mymod.renderSelect('fatherGroupId', res.data, 'id', 'name', '上级群组');
        })

        form.verify({
            selected: function (value) {
                if (value== 0) {
                    return '请选择一项';
                }
            }
        });

        $('#addBtn').click(function () {
            layer.open({
                type:1,
                title:'添加群组',
                content:$('#detail'),
                resize:true,
                success:function (layero, index) {
                    ajax("/group/all", null, function (res) {
                        mymod.renderSelect('fatherGroupId1', res.data, 'id', 'name', '上级群组');
                    })
                    //监听提交
                    form.on('submit(add)', function (data) {
                        ajax('/group/list/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    $("#name").val("");
                                    $("#description").val("");
                                    $("#fatherGroupId1").val(0);
                                    $("#location").val("");
                                    $(".layui-laypage-btn").click();
                                    //关闭当前frame
                                    layer.close(index);
                                })
                            } else {
                                layer.msg(res.msg, {time: 500});
                            }
                        })
                        return false;
                    });

                    $('#cancel').click(function () {
                        layer.close(index);
                        $("#name").val("");
                        $("#description").val("");
                        $("#fatherGroupId1").val(0);
                        $("#location").val("");
                        return false;
                    })
                }
                , cancel: function(){
                    $("#name").val("");
                    $("#description").val("");
                    $("#fatherGroupId1").val(0);
                    $("#location").val("");
                }
            })
        })

        //监听搜索
        form.on('submit(search)', function (data) {
            table.reload('groupTable', {
                url: '/group/list/all' //数据接口
                , where: data.field
                , page: true //开启分页
                , cols: cols
            })
            return false;
        })

        //监听工具条
        table.on('tool(groupTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') { //删除
                layer.confirm('是否删除？', {icon: 3, title: '提示'}, function (index) {
                    ajax('/group/list/delete', {id: data.id}, function (msg) {
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
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#description").val(data.description);
                ajax("/group/all", null, function (res) {
                    mymod.renderSelect('fatherGroupId1', res.data, 'id', 'name', '上级群组',data.fatherGroupId,data.id);
                })
                $("#location").val(data.location);
                $("#add")[0].innerHTML="修改";
                layer.open({
                    type: 1,
                    title: '编辑群组',
                    content: $('#detail'),
                    resize:true,
                    success: function (layero, index) {
                        //监听提交
                        form.on('submit(add)', function (data) {
                            ajax('/group/list/update', data.field, function (res) {
                                if (res.result == "OK") {
                                    layer.msg('修改成功!', {time: 500}, function () {
                                        //关闭当前frame
                                        layer.close(index);
                                        $('#id').val(0);
                                        $("#name").val("");
                                        $("#description").val("");
                                        $("#fatherGroupId1").val(0);
                                        $("#location").val("");
                                        $("add").val("添加");
                                        $(".layui-laypage-btn").click();
                                    })
                                } else {
                                    layer.msg(res.msg, {time: 500});
                                }
                            })
                            return false;
                        });

                        $('#cancel').click(function () {
                            layer.close(index);
                            $('#id').val(0);
                            $("#name").val("");
                            $("#description").val("");
                            $("#fatherGroupId1").val(0);
                            $("#location").val("");
                            $("#add")[0].innerHTML="添加";
                            return false;
                        })
                    }
                    , cancel: function(){
                        $('#id').val(0);
                        $("#name").val("");
                        $("#description").val("");
                        $("#fatherGroupId1").val(0);
                        $("#location").val("");
                        $("#add")[0].innerHTML="添加";
                    }
                })
            }
        });
    });
})