$(function  () {
    layui.use(['form','table', 'laydate'], function () {
        var form=layui.form,laydate = layui.laydate,table = layui.table,mymod=layui.mymod;

        var cols=[[
            {field: 'id', title: 'ID',  sort: true,width:100}
            ,{field: 'name', title: 'Name',sort:true}
            ,{field: 'description', title: 'Description'}
            ,{field: 'createTime', title: 'Create Time',  sort: true}
            ,{field: '', title: 'Opertion',toolbar:'#bar',width:100}
        ]]

        table.render({
            elem: '#resourceTable'
            ,url: '/resource/type/list' //数据接口
            ,page: true //开启分页
            ,cols: cols
        });
        mymod.renderLaydate('start','end','datetime');

        $('#addBtn').click(function () {
            layer.open({
                type:1,
                title:'添加资源类型',
                // area:'500px',
                content:$('#detail'),
                success:function (layero, index) {
                    //监听提交
                    form.on('submit(add)', function (data) {
                        ajax('/resource/type/update', data.field, function (res) {
                            if (res.result == "OK") {
                                layer.msg('添加成功!', {time: 500}, function () {
                                    //关闭当前frame
                                    layer.close(index);
                                    $("#name").val("");
                                    $("#description").val("");
                                    $(".layui-laypage-btn").click();
                                })
                            } else {
                                layer.msg(res.msg, {time: 500});
                            }
                        })
                        return false;
                    });

                    $('#cancel').click(function() {
                        layer.close(index);
                        $("#name").val("");
                        $("#description").val("");
                        return false;
                    })
                }
                , cancel: function(){
                    $("#name").val("");
                    $("#description").val("");
                }
            })
        })

        //监听搜索
        form.on('submit(search)',function (data) {
            table.reload('resourceTable',{
                url: '/resource/type/list' //数据接口
                ,where:data.field
                ,page: true //开启分页
                ,cols: cols
            })
            return false;
        })

        //监听工具条
        table.on('tool(resourceTable)', function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            if(layEvent === 'delete'){ //删除
                layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){
                    ajax('/resource/type/delete',{id:data.id},function (msg) {
                        layer.close(index);
                        if(msg.result=="OK")
                            layer.msg("删除成功！",{time:500},function () {
                                $(".layui-laypage-btn").click();
                            })
                        else
                            layer.msg(msg.msg);
                    })
                });
            } else if(layEvent === 'edit'){ //编辑
                $('#id').val(data.id);
                $("#name").val(data.name);
                $("#description").val(data.description);
                $("#add")[0].innerHTML="修改";
                layer.open({
                    type:1,
                    title:'编辑资源类型',
                    // area:'500px',
                    content:$('#detail'),
                    success:function (layero, index) {
                        //监听提交
                        form.on('submit(add)', function (data) {
                            ajax('/resource/type/update', data.field, function (res) {
                                if (res.result == "OK") {
                                    layer.msg('修改成功!', {time: 500}, function () {
                                        //关闭当前frame
                                        layer.close(index);
                                        $('#id').val(0);
                                        $("#name").val("");
                                        $("#description").val("");
                                        $("#add")[0].innerHTML="添加";
                                        $(".layui-laypage-btn").click();
                                    })
                                } else {
                                    layer.msg(res.msg, {time: 500});
                                }
                            })
                            return false;
                        });

                        $('#cancel').click(function() {
                            layer.close(index);
                            $('#id').val(0);
                            $("#name").val("");
                            $("#description").val("");
                            $("#add")[0].innerHTML="添加";
                            return false;
                        })
                    }
                    , cancel: function(){
                        $('#id').val(0);
                        $("#name").val("");
                        $("#description").val("");
                        $("#add")[0].innerHTML="添加";
                    }
                })
            }
        });
    });
})