<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>口令评估</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script type="text/javascript" src="/js/mymod.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="layui-anim layui-anim-up">
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a>口令管理</a>
        <a>
          <cite>口令评估</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <form class="layui-form layui-form-pane">
        <div class="layui-form-item">
            <label class="layui-form-label">请输入口令</label>
            <div class="layui-input-inline">
                <input type="text" name="password" id="password" class="layui-input" lay-verify="required" autocomplete="off">
            </div>
            <div id="strength" class="layui-form-mid" style="color:red;"></div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">排除字典集(以逗号分隔)</label>
            <div class="layui-input-block">
                <textarea type="text" class="layui-textarea" name="exclude" id="exclude"></textarea>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">建议</label>
            <div class="layui-input-block">
                <textarea type="text" class="layui-textarea" id="suggestion" readonly></textarea>
            </div>
        </div>
        <button class="layui-btn" lay-filter="eval" lay-submit>评估</button>
        <button class="layui-btn" id="save">上传字典集</button>
    </form>
</div>
<script>
    $(function () {
        layui.use(['form', 'layer', 'mymod'], function () {
            $ = layui.jquery;
            form = layui.form
            layer = layui.layer;
            mymod = layui.mymod;

            //监听提交
            form.on('submit(eval)', function (d) {
                var data = {};
                data['password'] = d.field.password;
                data['exclude'] = d.field.exclude;
                ajax('/password/eval/one', data, function (res) {
                    if (res.result == "OK") {
                        $('#suggestion')[0].innerHTML = res.msg;
                        if(res.count<10){
                            $('#strength')[0].innerHTML='非常弱';
                            $('#strength').css('color',"green");
                        }else if(res.count<20){
                            $('#strength')[0].innerHTML='弱';
                            $('#strength').css('color',"green");
                        }else if(res.count<30){
                            $('#strength')[0].innerHTML='一般';
                            $('#strength').css('color',"green");
                        }else if(res.count<40){
                            $('#strength')[0].innerHTML='强';
                            $('#strength').css('color',"green");
                        }else{
                            $('#strength')[0].innerHTML='非常强';
                            $('#strength').css('color',"green");
                        }
                    } else {
                        layer.msg(res.msg, {time: 500});
                    }
                })
                return false;
            });

            //监听提交
            $('#save').click( function () {
                var data = {};
                data['exclude'] = $('#exclude').val();
                if(data.exclude==null||data.exclude==''){
                    layer.msg('请输入排除字典集',{time:500});
                    return false;
                }
                ajax('/password/eval/save', data, function (res) {
                    if (res.result == "OK") {
                        layer.msg("上传成功！",{time:500});
                    }
                })
                return false;
            });


        });
    })
</script>
</body>
</html>