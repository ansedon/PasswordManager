$(function  () {
    layui.use(['form','table', 'laydate'], function () {
        var form=layui.form,laydate = layui.laydate,table = layui.table,mymod=layui.mymod;

        var cols=[[
            {field: 'id', title: 'ID',  sort: true,width:100}
            ,{field: 'name', title: '资源名称',sort:true}
            ,{field: 'ip', title: 'IP地址', sort: true}
            ,{field: 'typeName', title: '资源类型',sort:true}
            ,{field: 'url', title: 'URL',templet:function(d){
                if(d.url!=null)
                    return "<a href=\""+d.url+"\" class=\"layui-table-link\" target=\"_blank,_parent\">"+d.url+"</a>"
                return "";
            }}
            ,{field: 'createTime', title: '创建时间',  sort: true}
            ,{field: '', title: '操作',toolbar:'#bar'}
        ]]

        table.render({
            elem: '#resourceTable'
            ,url: '/resource/list/all' //数据接口
            ,where:{typeId:$('#typeId').val()}
            ,page: true //开启分页
            ,cols: cols
        });
        mymod.renderLaydate('start','end','datetime');
        ajax("/resource/type/all",null,function (res) {
            mymod.renderSelect('typeId',res.data,'id','name','资源类型');
        })

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
            'state': {"key": "demo0"},
            'plugins': ["state", "wholerow", "themes"]
        });

        $('#jstree').on("changed.jstree", function (e, data) {
            var id = data.instance.get_node(data.selected).id;
            if(id<=0)
                $('#typeId').val(0);
            else
                $('#typeId').val(id);
            table.render({
                elem: '#resourceTable'
                ,url: '/resource/list/all' //数据接口
                ,where:{typeId:$('#typeId').val()}
                ,page: true //开启分页
                ,cols: cols
            });
        });

        //监听搜索
        form.on('submit(search)',function (data) {
            table.reload('resourceTable',{
                url: '/resource/list/all' //数据接口
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
                            layer.msg("删除成功！",{time:500},function () {
                            $(".layui-laypage-btn").click();
                        })
                        else
                            layer.msg(msg.msg);
                    })
                });
            } else if(layEvent === 'edit'){ //编辑
                layer_show("编辑资源","/resource/list/edit/"+data.id);
            }
        });
    });
})