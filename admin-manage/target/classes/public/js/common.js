/**
    *把日期格式化成 2017-01-12 18:24:54形式
    *
    */
	var formatDate =  function (date) {
		/*<![CDATA[*/
	    if (date !== null && date != "" ) {
	    /*]]>*/
	        var javascriptDate = new Date(date);
	        javascriptDate = javascriptDate.getFullYear() + "-"  + ("0" + (javascriptDate.getMonth() + 1)).slice(-2) + "-" + ("0" + javascriptDate.getDate()).slice(-2)
	        	+ " " + ("0" + javascriptDate.getHours()).slice(-2) + ":" + ("0" + javascriptDate.getMinutes()).slice(-2) + ":" + ("0" + javascriptDate.getSeconds()).slice(-2);
	        return javascriptDate;
	    } else {
	        return "";
	    }
	}
	
	/**
	 * 功能：返回两个日期之间相距的天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @returns 相距天数
	 */
	function GetDateDiff(startDate,endDate)  
	{  
	    var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();     
	    var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
	    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
	    return  dates;    
	}
	
	/**
	 * 标准的部门选择器
	 * @param config selected  当前选中的部门
	 * @param config parentFunc  返回后父容器的回调函数，在这儿你可以取出返回的选择部门，做你想要的操作
	 * 例如  var parentFunc = function (data){
			if (data){
			    $("#inputDepartment").val(data.nodeId);//data.nodeId选中的部门id
			    $("#inputDepartmentName").val(data.nodeText);//data.nodeText选中的部门名称
			}
	    }
	 * @returns 一个部门选择器
	 */
    Departselector = function(config){
    	    var departmentSelector = new Object();
    	    var defaultParam = {};
    	    defaultParam.selected=null;
    	    defaultParam.width='700px';
    	    defaultParam.height='530px';
    	    defaultParam.url = '/admin/department/selector/list?selected=';
    	    defaultParam.parentFunc = function(){
    	    	
    	    };
   	    
    	    config.url = defaultParam.url+config.selected;
    	    
    	    $.extend(defaultParam,config);
	    	departmentSelector.open = function (){
	    		  layer.open({
	    			  type: 2,
	    			  title:'选择部门',
	    			  area: [defaultParam.width, defaultParam.height],
	    			  fixed: false, //不固定
	    			  maxmin: true,
	    			  content: defaultParam.url,
	    			  btn:['确定','取消'],
	    			  yes:function(index){
	    				  //当点击‘确定’按钮的时候，获取弹出层返回的值
	    				  var res = window["layui-layer-iframe" + index].callbackdata();
	    				  //打印返回的值，看是否有我们想返回的值。
	    				  parentFunc(res);
	    				  //最后关闭弹出层
	    				  layer.close(index);
	    				  
	    			  },
	    			  btn2:function(){
	    			  }
	    		  });
	    		  
	    	  }
	    	 return departmentSelector;
	}
    /**
     *datatable服务器端错误时显示错误提示 
     */
	var  handleAjaxError = function( xhr, textStatus, error ) {  //捕获错误消息
	    if ( xhr.responseText === '请登录' ) {  
	    	layer.alert('用户已失效，请重新登录!', {
				icon : 2,
				title : '出错了！',
				skin : 'layer-ext-moon'
			}); 
	    } else {
	    	if (textStatus == "parsererror"){
		    	layer.alert('服务器返回json格式有误,请检查！', {
					icon : 2,
					title : '出错了！',
					skin : 'layer-ext-moon'
				});  
	    	} else {
	    		layer.alert('服务器错误，请联系开发人员！', {
					icon : 2,
					title : '出错了！',
					skin : 'layer-ext-moon'
				});
	    	}
	    }  
	  
	    $('.dataTable').dataTable().fnProcessingIndicator(false);  
	}
    $(function () {
    	
    	/**
    	 * 为ajax请求添加csrf ajax开始时添加遮罩，ajax结束关闭遮罩
    	 */
    	$.ajaxSetup({
    		headers:{'X-CSRF-TOKEN':$("#csrf_token").attr("content")},
    		beforeSend:function(){
    			layer.load(0);//开启遮罩
            },
            complete:function(){
            	layer.closeAll("loading");//关闭遮罩
            }
    	});
    });