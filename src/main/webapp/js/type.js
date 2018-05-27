$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;

        $("#jstree").jstree({
            'core': {
                'themes': {
                    'icons': false,
                    'responsive': false
                },
                'check_callback': true,
                'data': function (obj, callback) {
                    ajax("/resource/type/tree", '', function (res) {
                        callback.call(this, res);
                    })
                }
            },
            'state': {"key": "demo2"},
            'plugins': ["dnd", "state", "wholerow", "themes"]
        });

        $('#jstree').on("changed.jstree", function (e, data) {
            var id = data.instance.get_node(data.selected).id;
            $('#nodeId').val(id);
            if (id != false && id != undefined)
                ajax('/resource/type/one', {id: id}, function (res) {
                    $('#name').val(res.name);
                    $('#description').val(res.description);
                    $('#createTime').val(res.createTime);
                })
        });

        //拖拽事件
        $("#jstree").on('move_node.jstree', function (e, data) {
            layer.confirm('确定移动该资源类型？', {icon: 3, title: '提示'}, function (index) {
                var d = {id: data.node.id, fatherTypeId: data.parent};
                ajax('/resource/type/update', d, function (res) {
                    layer.msg("移动成功", {time: 500});
                })
            }, function (index) {
                ajax("/resource/type/tree", '', function (json) {
                    $("#jstree").jstree(true).settings.core.data = json;
                    $("#jstree").jstree(true).refresh();
                })
            });
        })

        $('#addBtn').click(function () {
            layer.open({
                type: 1,
                title: '添加资源类型',
                content: $('#detail'),
                resize: true,
                success: function (layero, index) {
                    //监听提交
                    form.on('submit(add)', function (data) {
                        data.field['fatherTypeId'] = $('#nodeId').val();
                        ajax('/resource/type/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    $("#name1").val("");
                                    $("#description1").val("");
                                    //关闭当前frame
                                    layer.close(index);
                                })
                            } else {
                                $("#name1").val("");
                                $("#description1").val("");
                                //关闭当前frame
                                layer.close(index);
                                layer.msg(res.msg, {time: 500});
                            }
                            ajax("/resource/type/tree", '', function (json) {
                                $("#jstree").jstree(true).settings.core.data = json;
                                $("#jstree").jstree(true).refresh();
                            })
                        })
                        return false;
                    });

                    $('#cancel').click(function () {
                        $("#name1").val("");
                        $("#description1").val("");
                        //关闭当前frame
                        layer.close(index);
                        return false;
                    })
                }
                , cancel: function () {
                    $("#name1").val("");
                    $("#description1").val("");
                }
            })
        })

        $('#edit').click(function () {
            layer.open({
                type: 1,
                title: '修改资源类型信息',
                content: $('#detail'),
                resize: true,
                success: function (layero, index) {
                    $('#id1').val($('#nodeId').val());
                    $('#name1').val($('#name').val());
                    $('#description1').val($('#description').val());
                    $('#add')[0].innerHTML = "修改";
                    //监听提交
                    form.on('submit(add)', function (data) {
                        ajax('/resource/type/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('修改成功!', {time: 500}, function () {
                                    $('#id1').val(0);
                                    $("#name1").val("");
                                    $("#description1").val("");
                                    $('#add')[0].innerHTML = "添加";
                                    //关闭当前frame
                                    layer.close(index);
                                })
                            } else {
                                $('#id1').val(0);
                                $("#name1").val("");
                                $("#description1").val("");
                                $('#add')[0].innerHTML = "添加";
                                //关闭当前frame
                                layer.close(index);
                                layer.msg(res.msg, {time: 500});
                            }
                            ajax("/resource/type/tree", '', function (json) {
                                $("#jstree").jstree(true).settings.core.data = json;
                                $("#jstree").jstree(true).refresh();
                            })
                        })
                        return false;
                    });

                    $('#cancel').click(function () {
                        $('#id1').val(0);
                        $("#name1").val("");
                        $("#description1").val("");
                        $('#add')[0].innerHTML = "添加";
                        //关闭当前frame
                        layer.close(index);
                        return false;
                    })
                }
                , cancel: function () {
                    $('#id1').val(0);
                    $("#name1").val("");
                    $("#description1").val("");
                    $('#add')[0].innerHTML = "添加";
                }
            })
        })

        $('#delete').click(function () {
            layer.confirm('是否删除？', {icon: 3, title: '提示'}, function (index) {
                ajax('/resource/type/delete', {id: $('#nodeId').val()}, function (msg) {
                    layer.close(index);
                    if (msg.result == "OK")
                        layer.msg("删除成功！", {time: 500})
                    else
                        layer.msg(msg.msg);
                    ajax("/resource/type/tree", '', function (json) {
                        $("#jstree").jstree(true).settings.core.data = json;
                        $("#jstree").jstree(true).refresh();
                    })
                })
            });
        })

    })
})