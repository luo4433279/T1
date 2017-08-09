/**
 * Created by xieqiang on 2016/12/18.
 */
$(function () {

    
    $("form").submit(function (event) {
        var form=$(this);
        var data = form.serializeJSON();
        console.log(JSON.stringify(data, null, 4));
        $.ajax({
            url:form.attr("action"),
            type:'post',
            dataType:'json',
            success:function (data) {
                if (data.success){
                    alert("操作成功");
                }else{
                    alert(data.msg);
                }
            },
            error:function (e) {
                alert("未知错误");
            }
        });
        event.preventDefault();
    })
});
