$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
        layer = layui.layer;
        var cols = [[
            {field: 'id', title: 'ID', sort: true, width: 100}
            , {field: 'account', title: 'Account', sort: true}
            , {field: 'password', title: 'Password', minWidth: 200, templet: "#psTpl"}
            , {field: 'email', title: 'Email', sort: true}
            , {field: 'roleName', title: 'Role Name', sort: true}
            , {field: 'groupName', title: 'Group Name', sort: true}
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

        ajax("/manage/role/select", {self:1}, function (res) {
            mymod.renderSelect('roleId', res.data, 'id', 'name', '角色名称');
        })
        ajax("/group/all", {self:1}, function (res) {
            mymod.renderSelect('groupId', res.data, 'id', 'name', '群组名称');
        })

        form.verify({
            selected: function (value) {
                if (value == 0) {
                    return '请选择一项';
                }
            }
        });

        $('#addBtn').click(function () {
            layer.open({
                type: 1,
                title: '添加用户',
                content: $('#detail'),
                resize: true,
                success: function (layero, index) {
                    ajax("/manage/role/select", {self:0}, function (res) {
                        mymod.renderSelect('roleId1', res.data, 'id', 'name', '角色名称');
                    })
                    ajax("/group/all", null, function (res) {
                        mymod.renderSelect('groupId1', res.data, 'id', 'name', '群组名称');
                    })
                    $('#add')[0].innerHTML="添加";
                    //监听提交
                    form.on('submit(add)', function (data) {
                        ajax('/manage/user/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    $("#account").val("");
                                    $("#password").val("");
                                    $("#email").val("");
                                    $("#phone").val("");
                                    $("#location").val("");
                                    $('#add')[0].innerHTML="修改";
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
                        $("#account").val("");
                        $("#password").val("");
                        $("#email").val("");
                        $("#phone").val("");
                        $("#location").val("");
                        $('#add')[0].innerHTML="修改";
                        return false;
                    })
                }
                ,cancel:function (index) {
                    layer.close(index);
                    $("#account").val("");
                    $("#password").val("");
                    $("#email").val("");
                    $("#phone").val("");
                    $("#location").val("");
                    $('#add')[0].innerHTML="修改";
                    return false;
                }
            })
        })

        //监听搜索
        form.on('submit(search)', function (data) {
            table.reload('userTable', {
                url: '/manage/user/all' //数据接口
                , where: data.field
                , page: true //开启分页
                , cols: cols
            })
            return false;
        })

        //监听工具条
        table.on('tool(userTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'delete') { //删除
                layer.confirm('是否删除？', {icon: 3, title: '提示'}, function (index) {
                    ajax('/manage/user/delete', {id: data.id}, function (msg) {
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
                $("#email").val(data.email);
                $("#phone").val(data.phone);
                $("#location").val(data.location);
                layer.open({
                    type: 1,
                    title: '编辑用户',
                    content: $('#detail'),
                    resize: true,
                    success: function (layero, index) {
                        ajax("/manage/role/select", {self:1}, function (res) {
                            mymod.renderSelect('roleId1', res.data, 'id', 'name', '角色名称', data.roleId);
                        })
                        ajax("/group/all", null, function (res) {
                            mymod.renderSelect('groupId1', res.data, 'id', 'name', '群组名称',data.groupId);
                        })
                        //监听提交
                        form.on('submit(add)', function (data) {
                            data.field.expireTime = new Date(data.field.expireTime).getTime();
                            ajax('/manage/user/update', data.field, function (res) {
                                if (res.result == "OK") {
                                    layer.msg('修改成功!', {time: 500}, function () {
                                        //关闭当前frame
                                        layer.close(index);
                                        $('#id').val(0);
                                        $("#account").val("");
                                        $("#password").val("");
                                        $("#email").val("");
                                        $("#phone").val("");
                                        $("#location").val("");
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
                            $("#email").val("");
                            $("#phone").val("");
                            $("#location").val("");
                            return false;
                        })
                    }
                    ,cancel:function (index) {
                        layer.close(index);
                        $('#id').val(0);
                        $("#account").val("");
                        $("#password").val("");
                        $("#email").val("");
                        $("#phone").val("");
                        $("#location").val("");
                        return false;
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

function show(that) {
    if ($("#password").attr('isShow') == 0) {
        $("#password").attr('type', 'text');
        $("#password").attr('isShow', 1);
    }
    else {
        $("#password").attr('type', 'password');
        $("#password").attr('isShow', 0);
    }
}