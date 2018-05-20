$(function  () {
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(login)', function(data){
            var func=function(res){
                if(res.result=="OK"){
                    location.href='index';
                }else{
                    layer.msg(res.msg);
                }
            }
            ajax("checklogin",data.field,func);
            return false;
        });
    });
})