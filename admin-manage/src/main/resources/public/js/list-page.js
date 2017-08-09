/**
 * Created by xieqiang on 2016/11/20.
 */
$.ajaxSetup({
	headers : {
		'X-CSRF-TOKEN' : $("#csrf_token").attr("content")
	}
});

$(function() {
	ajaxClick('lock', 'PUT');
	ajaxClick('trash', 'DELETE');
});

function ajaxClick(name, type) {
	$("span[name='" + name + "']").click(function() {
		var urlStr = $(this).attr("data")
		if (type == 'DELETE') {
			parent.layer.confirm('是否真的删除此记录？', {
				skin : 'layer-ext-moon',
				title : '确 认'
			}, function(index, layero) {
				$.ajax({
					type : type,
					url : urlStr,
					success : function(data) {
						parent.layer.close(index);
						if (data) {
							alert(data);
						} else {
							window.location.reload();
						}
					}

				})
			})
		} else {
			$.ajax({
				type : type,
				url : $(this).attr("data"),
				success : function(data) {
					if (data) {
						alert(data);
					} else {
						window.location.reload();
					}
				}
			})
		}
	})
}