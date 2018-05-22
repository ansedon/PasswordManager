$(function  () {
    layui.use(['form','table', 'laydate'], function () {
        var form=layui.form,laydate = layui.laydate,table = layui.table,mymod=layui.mymod;
        var cols=[[
            {field: 'id', title: 'ID',  sort: true,width:100}
            ,{field: 'name', title: 'Name',sort:true}
            ,{field: 'ip', title: 'IP', sort: true}
            ,{field: 'typeName', title: 'Type',sort:true}
            ,{field: 'url', title: 'URL',templet:function(d){
                if(d.url!=null)
                    return "<a href=\""+d.url+"\" class=\"layui-table-link\" target=\"_blank,_parent\">"+d.url+"</a>"
                return "";
            }}
            ,{field: 'createTime', title: 'Create Time',  sort: true}
            ,{field: '', title: 'Opertion',toolbar:'#bar'}
        ]]
        //第一个实例
        table.render({
            elem: '#resourceTable'
            ,url: '/resource/list/all' //数据接口
            ,where:{typeId:1,groupId:1}
            ,page: true //开启分页
            ,cols: cols
        });
        mymod.renderLaydate('start','end','datetime');
        mymod.renderSelect('select',[{id:1,name:'a'},{id:2,name:'b'}],'id','name','资源类型');

        //监听工具条
        table.on('tool(resourceTable)', function(obj){
            var data = obj.data;
            var layEvent = obj.event;
            var tr = obj.tr;

            if(layEvent === 'view'){ //查看
                layer.open({
                    type:1,
                    title:'资源详情',
                    // area:'500px',
                    content:$('#detail'),
                    success:function (layero, index) {
                        ajax('/resource/list/one',{id:data.id},function (res) {
                            $('#name').val(res.data[0].name);
                            $('#ip').val(res.data[0].ip);
                            $('#description').val(res.data[0].description);
                            $('#url').val(res.data[0].url);
                            $('#location').val(res.data[0].location);
                            $('#pwdPolicy').val(res.data[0].pwdPolicy);
                            $('#superkey').val(res.data[0].superkey);
                            $('#type').val(data.typeName);
                            $('#createTime').val(data.createTime);
                        })
                    }
                })
            } else if(layEvent === 'delete'){ //删除
                layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){
                    ajax('/resource/list/delete',{id:data.id},function (msg) {
                        layer.close(index);
                        if(msg.result=="OK")
                            layer.msg("删除成功！",function () {
                            $(".layui-laypage-btn").click();
                        })
                        else
                            layer.msg(msg.msg);
                    })
                });
            } else if(layEvent === 'edit'){ //编辑
                x_admin_show("编辑资源","/resource/list/edit/"+data.id,500,600);
            }
        });
    });


    // /*用户-停用*/
    // function member_stop(obj, id) {
    //     layer.confirm('确认要停用吗？', function (index) {
    //
    //         if ($(obj).attr('title') == '启用') {
    //
    //             //发异步把用户状态进行更改
    //             $(obj).attr('title', '停用')
    //             $(obj).find('i').html('&#xe62f;');
    //
    //             $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
    //             layer.msg('已停用!', {icon: 5, time: 1000});
    //
    //         } else {
    //             $(obj).attr('title', '启用')
    //             $(obj).find('i').html('&#xe601;');
    //
    //             $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
    //             layer.msg('已启用!', {icon: 5, time: 1000});
    //         }
    //
    //     });
    // }
    //
    // /*用户-删除*/
    // function member_del(obj, id) {
    //     layer.confirm('确认要删除吗？', function (index) {
    //         //发异步删除数据
    //         $(obj).parents("tr").remove();
    //         layer.msg('已删除!', {icon: 1, time: 1000});
    //     });
    // }
    //
    //
    // function delAll(argument) {
    //
    //     var data = tableCheck.getData();
    //
    //     layer.confirm('确认要删除吗？' + data, function (index) {
    //         //捉到所有被选中的，发异步进行删除
    //         layer.msg('删除成功', {icon: 1});
    //         $(".layui-form-checked").not('.header').parents('tr').remove();
    //     });
    // }
})