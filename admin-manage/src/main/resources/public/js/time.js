	var now = new Date(); //当前日期
        var nowDayOfWeek = now.getDay(); //今天本周的第几天
        var nowDay = now.getDate(); //当前日
        var nowMonth = now.getMonth()+1; //当前月
        var nowYear = now.getFullYear(); //当前年
       


	//获取当前日期
	function getNewDay(){
		var newFullDay=nowYear+"-"+nowMonth+"-"+nowDay;
	return newFullDay
	}
	//获取当前七天之前日期
	function getBeforeDay(){
	        var myDate = new Date();
			myDate.setDate(myDate.getDate() - 6);
			var day=myDate.getMonth()+1; 
			var beforeDay=myDate.getFullYear()+"-"+day+"-"+myDate.getDate();
		return beforeDay;
	}	
	
	//获取当前一个月之前日期
	function getBeforeMothDay(){
	       var myDate = new Date();
			myDate.setDate(myDate.getDate() - 29);
			var day=myDate.getMonth()+1; 
			var beforeDay=myDate.getFullYear()+"-"+day+"-"+myDate.getDate();
		return beforeDay;
	}
	
  
        //获得某月的天数
        function getMonthDays(myMonth){
            var monthStartDate = new Date(nowYear, myMonth, 1);
            var monthEndDate = new Date(nowYear, myMonth + 1, 1);
            var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
            return   days;
        }
  //获得本周的开端日期
        function getWeekStartDate() {
            var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1);
            return formatDate(weekStartDate);
        }

        //获得本周的停止日期
        function getWeekEndDate() {
            var weekEndDate = new Date(nowYear, nowMonth, nowDay + (7 - nowDayOfWeek));
            return formatDate(weekEndDate);
        }

        //获得本月的开端日期
        function getMonthStartDate(){
            var monthStartDate = new Date(nowYear, nowMonth, 1);
            return formatDate(monthStartDate);
        }

        //获得本月的停止日期
        function getMonthEndDate(){
            var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
            return formatDate(monthEndDate);
        }
  //获取本年的开始日期
  function getYearStartDate(){
   var monthStartDate = new Date(nowYear, 0, 1);
            return formatDate(monthStartDate);
  }
  //获得某年某月的天数
        function getMonthDaysNew(year,month){
            var monthStartDate = new Date(year, month, 1);
            var monthEndDate = new Date(year,month + 1, 1);
            var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
            return   days;
        }
  //获取某一天
  function getDateNew(year,month,newDay) {
            var weekStartDate = new Date(year, month, newDay);
            return formatDate(weekStartDate);
        }
  //获取周的开始日期
  function getWeekStartDateNew(year,month,newDay,newDayOfWeek) {
            var weekStartDate = new Date(year, month, newDay - newDayOfWeek + 1);
            return formatDate(weekStartDate);
        }
  //获得周的停止日期
        function getWeekEndDateNew(year,month,newDay,newDayOfWeek) {
            var weekEndDate = new Date(year, month, newDay + (7 - newDayOfWeek));
            return formatDate(weekEndDate);
        }
  //获得月的开端日期
        function getMonthStartDateNew(year,month,newDay,newDayOfWeek){
            var monthStartDate = new Date(year, month, 1);
            return formatDate(monthStartDate);
        }
   //获得月的停止日期
        function getMonthEndDateNew(year,month,newDay,newDayOfWeek){
            var monthEndDate = new Date(year, month, getMonthDaysNew(year,month));
            return formatDate(monthEndDate);
        }
  //获取年的开始日期
  function getYearStartDateNew(year,month,newDay,newDayOfWeek){
   var monthStartDate = new Date(year, 0, 1);
            return formatDate(monthStartDate);
  }
  //获取年的结束日期
  function getYearEndDateNew(year,month,newDay,newDayOfWeek){
   var monthStartDate = new Date(year, 11, 31);
            return formatDate(monthStartDate);
  }
  
 
 var yugi = function(year) {
    var d = new Date(year, 0, 1);
    while (d.getDay() != 0) {
        d.setDate(d.getDate() + 1);
    }
    var to = new Date(year + 1, 0, 1);
    var i = 1;
    for (var from = d; from < to;) {
    
        
        document.write(year + "年第" + i + "周 " + (from.getMonth() + 1) + "月" + from.getDate() + "日 - ");
        from.setDate(from.getDate() + 6);
        
        if (from < to) {
            document.write((from.getMonth() + 1) + "月" + from.getDate() + "日<br / >");
            from.setDate(from.getDate() + 1);
        } else {
            to.setDate(to.getDate() - 1);
            document.write((to.getMonth() + 1) + "月" + to.getDate() + "日<br / >");
        }
     
        i++;
    }
}

  //获取半年内的每周时间
  function getHalfYear(){
     
  
  
  }
  
  //隐藏或显示列表
  function open(id){

	$(".display").each(function(index,domEle){
	           $(domEle).css("display","none");
	  });
  if("oneId"==id){
     $('[name="oneId"]').each(function(index,domEle){   
          $(domEle).removeAttr("style");
      }); 
  }else if("twoId"==id){
   $('[name="twoId"]').each(function(index,domEle){   
          $(domEle).removeAttr("style");
      }); 
  }else if("threeId"==id){
     $('[name="threeId"]').each(function(index,domEle){   
          $(domEle).removeAttr("style");
      }); 
  }else if("fourId"==id){
     $('[name="fourId"]').each(function(index,domEle){   
          $(domEle).removeAttr("style");
      }); 
  }else if("fiveId"==id){
    $('[name="fiveId"]').each(function(index,domEle){   
          $(domEle).removeAttr("style");
      }); 
  }else{
     $('[name="oneId"]').each(function(index,domEle){   
          $(oneId).removeAttr("style");
      });
  }
 }
 
 
//获取半年前时间
function getPastHalfYear() {
    // 先获取当前时间
    var curDate = new Date();
    
    curDate.setMonth(curDate.getMonth()-6)
    // 将半年的时间单位换算成毫秒
   // var halfYear = 365 / 2 * 24 * 3600 * 1000;
    //var pastResult = curDate - halfYear;  // 半年前的时间（毫秒单位）
 
    // 日期函数，定义起点为半年前
     
    var  pastYear = curDate.getFullYear(),
           pastMonth = curDate.getMonth() + 1,
           pastDay = curDate.getDate();
 
    return pastYear + '-' + pastMonth + '-' + pastDay;
   // return getWeekNumber(pastYear,pastMonth,pastDay);
}





 /**
   * 判断年份是否为润年
   *
   * @param {Number} year
   */
  function isLeapYear(year) {
      return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
 }
 /**
  * 获取某一年份的某一月份的天数
  *
  * @param {Number} year
  * @param {Number} month
  */
 function getMonthDays(year, month) {
     return [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month] || (isLeapYear(year) ? 29 : 28);
 }
  /**
  * 获取某年的某天是第几周
  * @param {Number} y
  * @param {Number} m
  * @param {Number} d
  * @returns {Number}
  */
 function getWeekNumber(y, m, d) {
     var now = new Date(y, m - 1, d),
         year = now.getFullYear(),
         month = now.getMonth(),
         days = now.getDate();
     //那一天是那一年中的第多少天
     for (var i = 0; i < month; i++) {
         days += getMonthDays(year, i);
     }
 
     //那一年第一天是星期几
     var yearFirstDay = new Date(year, 0, 1).getDay() || 7;
 
     var week = null;
     if (yearFirstDay == 7) {
         week = Math.ceil(days / yearFirstDay);
     } else {
         days -= (7 - yearFirstDay);
         week = Math.ceil(days / 7) + 1;
     }
 
     return week;
 }	