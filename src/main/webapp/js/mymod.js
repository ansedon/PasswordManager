//config的设置是全局的
layui.config({
    base: '/js/'
}).extend({ //设定模块别名
});

layui.define(['laydate', 'form'], function (exports) {
    laydate = layui.laydate;
    form=layui.form;
    nowTime = new Date().valueOf();
    max = null;
    var obj = {
        //初始化日期范围选择器
        renderLaydate: function (startId, endId, type) {
            //开始时间
            var start = laydate.render({
                elem: '#' + startId,
                type: type || 'datetime',
                max: nowTime,
                btns: ['clear', 'confirm'],
                done: function (value, date) {
                    endMax = end.config.max;
                    end.config.min = date;
                    end.config.min.month = date.month - 1;
                }
            });
            //截止时间
            var end = laydate.render({
                elem: '#' + endId,
                type: type || 'datetime',
                max: nowTime,
                done: function (value, date) {
                    if ($.trim(value) == '') {
                        var curDate = new Date();
                        date = {
                            'date': curDate.getDate(),
                            'month': curDate.getMonth() + 1,
                            'year': curDate.getFullYear()
                        };
                    }
                    start.config.max = date;
                    start.config.max.month = date.month - 1;
                }
            })
        },

        //初始化下拉选择框
        renderSelect: function (elementId, data, valueColumn, textColumn,name, selectedValue) {
            if (data != null) {
                $('#' + elementId).append("<option value='0'>请选择"+name+"</option>");
                for (var i = 0; i < data.length; i++) {
                    if (data[i][valueColumn] == selectedValue) {
                        $('#' + elementId).append("<option value='" + data[i][valueColumn] + "' selected>" + data[i][textColumn] + "</option>");
                    } else
                        $('#' + elementId).append("<option value='" + data[i][valueColumn] + "'>" + data[i][textColumn] + "</option>");
                }
            }
            form.render('select');
        }
    };

    //输出接口
    exports('mymod', obj);
});