$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
        layer=layui.layer;
        var cols = [[
            {field: 'id', title: 'ID', sort: true, width: 100}
            , {field: 'account', title: 'Account', sort: true}
            , {field: 'password', title: 'Password',minWidth:200, templet: "#psTpl"}
            , {field: 'email', title: 'Email', sort: true}
            , {field: 'roleName', title: 'Role Name', sort: true}
            , {field: 'phone', title: 'Phone', sort: true}
            , {field: 'location', title: 'Location', sort: true}
            , {field: 'createTime', title: 'Create Time', sort: true}
            , {field: '', title: 'Opertion', toolbar: '#bar'}
        ]]

        table.render({
            elem: '#userTable'
            , url: '/manage/user/all' //数据接口
            , page: true //开启分页
            , cols: cols
        });

        mymod.renderLaydate('start', 'end', 'datetime');

        ajax("/role/all", null, function (res) {
            mymod.renderSelect('roleId', res.data, 'id', 'name', '资源名称');
        })

        ajax("/resource/all",null, function (res) {
            mymod.renderSelect('resId1', res.data, 'id', 'name', '资源名称');
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
                title:'添加口令',
                content:$('#addTab'),
                resize:true,
                success:function (layero, index) {
                    //监听提交
                    form.on('submit(add1)', function (data) {
                        data.field.expireTime = new Date(data.field.expireTime).getTime();
                        ajax('/password/list/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    $("#account1").val("");
                                    $("#password1").val("");
                                    $("#expireTime1").val("");
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

                    $('#cancel1').click(function () {
                        layer.close(index);
                        $("#account1").val("");
                        $("#password1").val("");
                        $("#expireTime1").val("");
                        return false;
                    })
                }
            })
        })

        //监听搜索
        form.on('submit(search)', function (data) {
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
            var layEvent = obj.event;
            if (layEvent === 'delete') { //删除
                layer.confirm('是否删除？', {icon: 3, title: '提示'}, function (index) {
                    ajax('/password/list/delete', {id: data.id}, function (msg) {
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
                $('#id').val(data.id);
                $("#account").val(data.account);
                $("#password").val(data.password);
                $("#expireTime").val(data.expireTime);
                layer.open({
                    type: 1,
                    title: '编辑口令',
                    content: $('#detail'),
                    resize:true,
                    success: function (layero, index) {
                        //监听提交
                        form.on('submit(add)', function (data) {
                            data.field.expireTime = new Date(data.field.expireTime).getTime();
                            ajax('/password/list/update', data.field, function (res) {
                                if (res.result == "OK") {
                                    layer.msg('修改成功!', {time: 500}, function () {
                                        //关闭当前frame
                                        layer.close(index);
                                        $('#id').val(0);
                                        $("#account").val("");
                                        $("#password").val("");
                                        $("#expireTime").val("");
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
                            $("#account").val("");
                            $("#password").val("");
                            $("#expireTime").val("");
                            return false;
                        })
                    }
                })
            }
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

function show(that,id) {
    if ($("#password"+id).attr('isShow') == 0) {
        $("#password"+id).attr('type', 'text');
        $("#password"+id).attr('isShow', 1);
    }
    else {
        $("#password"+id).attr('type', 'password');
        $("#password"+id).attr('isShow', 0);
    }
}

function resetExpireTime(id) {
    $('#id2').val(id);
    layer.open({
        type: 1,
        title: '设置失效日期',
        content: $('#resetExpireTime'),
        resize:true,
        success: function (layero, index) {
            //监听提交
            form.on('submit(add2)', function (data) {
                data.field.expireTime = new Date(data.field.expireTime).getTime();
                ajax('/password/list/update', data.field, function (res) {
                    if (res.result == "OK") {
                        layer.msg('修改成功!', {time: 500}, function () {
                            //关闭当前frame
                            layer.close(index);
                            $('#id2').val(0);
                            $("#expireTime2").val("");
                            $(".layui-laypage-btn").click();
                        })
                    } else {
                        layer.msg(res.msg, {time: 500});
                    }
                })
                return false;
            });

            $('#cancel2').click(function () {
                layer.close(index);
                $('#id2').val(0);
                $("#expireTime2").val("");
                return false;
            })
        }
    })
}