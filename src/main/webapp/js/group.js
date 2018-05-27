$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form, laydate = layui.laydate, table = layui.table, mymod = layui.mymod;
        layer = layui.layer;


        $("#jstree").jstree({
            'core': {
                'themes': {
                    'icons': false,
                    'responsive': false
                },
                'check_callback': true,
                'data': function (obj, callback) {
                    ajax("/group/tree", '', function (res) {
                        callback.call(this, res);
                    })
                }
            },
            'types': {
                'default': { //设置默认的icon 图
                    "icon": "layui-icon layui-icon-face-smile",
                }
            },
            'state': {"key": "demo2"},
            'plugins': ["dnd", "state", "types", "wholerow", "themes"]
        });

        $('#jstree').on("changed.jstree", function (e, data) {
            var id = data.instance.get_node(data.selected).id;
            $('#nodeId').val(id);
            if (id != false && id != undefined)
                ajax('/group/list/one', {id: id}, function (res) {
                    $('#name').val(res.data[0].name);
                    $('#description').val(res.data[0].description);
                    $('#location').val(res.data[0].location);
                    $('#createTime').val(res.data[0].createTime);
                    $('#creatorName').val(res.data[0].creatorName);
                    $('#modifiedTime').val(res.data[0].modifiedTime);
                    $('#modifier').val(res.data[0].modifierName);
                })
        });

        //拖拽事件
        $("#jstree").on('move_node.jstree', function (e, data) {
            layer.confirm('确定移动部门？', {icon: 3, title: '提示'}, function (index) {
                var d = {id: data.node.id, fatherGroupId: data.parent};
                ajax('/group/list/update', d, function (res) {
                    layer.msg("移动成功", {time: 500});
                })
            }, function (index) {
                ajax("/group/tree", '', function (json) {
                    $("#jstree").jstree(true).settings.core.data = json;
                    $("#jstree").jstree(true).refresh();
                })
            });
        })

        $('#addBtn').click(function () {
            layer.open({
                type: 1,
                title: '添加部门',
                content: $('#detail'),
                resize: true,
                success: function (layero, index) {
                    //监听提交
                    form.on('submit(add)', function (data) {
                        data.field['fatherGroupId'] = $('#nodeId').val();
                        ajax('/group/list/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    $("#name1").val("");
                                    $("#description1").val("");
                                    $("#location1").val("");
                                    //关闭当前frame
                                    layer.close(index);
                                })
                            } else {
                                $("#name1").val("");
                                $("#description1").val("");
                                $("#location1").val("");
                                //关闭当前frame
                                layer.close(index);
                                layer.msg(res.msg, {time: 500});
                            }
                            ajax("/group/tree", '', function (json) {
                                $("#jstree").jstree(true).settings.core.data = json;
                                $("#jstree").jstree(true).refresh();
                            })
                        })
                        return false;
                    });

                    $('#cancel').click(function () {
                        $("#name1").val("");
                        $("#description1").val("");
                        $("#location1").val("");
                        //关闭当前frame
                        layer.close(index);
                        return false;
                    })
                }
                , cancel: function () {
                    $("#name1").val("");
                    $("#description1").val("");
                    $("#location1").val("");
                }
            })
        })

        $('#edit').click(function () {
            layer.open({
                type: 1,
                title: '修改部门信息',
                content: $('#detail'),
                resize: true,
                success: function (layero, index) {
                    $('#id1').val($('#nodeId').val());
                    $('#name1').val($('#name').val());
                    $('#description1').val($('#description').val());
                    $('#location1').val($('#location').val());
                    $('#add')[0].innerHTML = "修改";
                    //监听提交
                    form.on('submit(add)', function (data) {
                        ajax('/group/list/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    $("#name1").val("");
                                    $("#description1").val("");
                                    $("#location1").val("");
                                    $('#add')[0].innerHTML = "添加";
                                    //关闭当前frame
                                    layer.close(index);
                                })
                            } else {
                                $("#name1").val("");
                                $("#description1").val("");
                                $("#location1").val("");
                                $('#add')[0].innerHTML = "添加";
                                //关闭当前frame
                                layer.close(index);
                                layer.msg(res.msg, {time: 500});
                            }
                            ajax("/group/tree", '', function (json) {
                                $("#jstree").jstree(true).settings.core.data = json;
                                $("#jstree").jstree(true).refresh();
                            })
                        })
                        return false;
                    });

                    $('#cancel').click(function () {
                        $("#name1").val("");
                        $("#description1").val("");
                        $("#location1").val("");
                        $('#add')[0].innerHTML = "添加";
                        //关闭当前frame
                        layer.close(index);
                        return false;
                    })
                }
                , cancel: function () {
                    $("#name1").val("");
                    $("#description1").val("");
                    $("#location1").val("");
                    $('#add')[0].innerHTML = "添加";
                }
            })
        })

        $('#delete').click(function () {
            layer.confirm('是否删除？', {icon: 3, title: '提示'}, function (index) {
                ajax('/group/list/delete', {id: $('#nodeId').val()}, function (msg) {
                    layer.close(index);
                    if (msg.result == "OK")
                        layer.msg("删除成功！", {time: 500})
                    else
                        layer.msg(msg.msg);
                    ajax("/group/tree", '', function (json) {
                        $("#jstree").jstree(true).settings.core.data = json;
                        $("#jstree").jstree(true).refresh();
                    })
                })
            });
        })

        $('#allot').click(function () {
           layer_show($('#name').val()+"-资源分配",'/group/list/'+$('#nodeId').val());
        })

    })
})