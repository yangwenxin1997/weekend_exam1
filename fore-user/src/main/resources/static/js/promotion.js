$(function() {
	$.ajax({
		type: "get",
		//url: "data/promotion.json"
		url: "/customer/queryPromotionByPage.html",
		async: false,//不是异步加载,让ajax先执行
		dataType: "json",
		success: function(data) {
			$.each(data.pageData, function(i, item) {
				var str = '<div class="result col-md-3">';
				str += '<div class="thumbnail">';
				str += '<img src=' + '"' + item.titleImg + '"' + '>';
				str += '<div class="caption"><p>' + item.title + '</p>';
				str += '<p class="text-right status"><span>' + (item.status==1?"进行中":"已结束") + '</span></p>';
				str += '<p class="text-right grey">' + item.startDate + '-' + item.endDate + '</p>';
				str += '<p class="text-right grey">' + item.activescope + '</p>';
				str += '</div></div></div>';
				$("#hiddenpromotion").append(str);
			});
		}
	});
	
	//不要放在ajax里面,页面还要用到的函数
	var page_every = 4; //每页显示个数
			
	var num_entries = $("#hiddenpromotion div.result").length;
	$("#Pagination").pagination(num_entries, {
		num_edge_entries: 1, //边缘页数
		num_display_entries: 4, //主体页数
		callback: pageselectCallback,
		items_per_page: page_every, //每页显示个数							
		prev_text: "前一页",
		next_text: "后一页"
	});		
			
	function pageselectCallback(page_index,jq){
		$("#promotionresult").empty();
		var page_end = page_index * page_every;
		for (var i = 0;i<page_every;i++){
//					console.log(page_end);
        var new_content = $('#hiddenpromotion div.result:eq('+page_end+')').clone();
		//这个id没改,所以加载不到数据!!!!!!!!!!!!!!!
		$("#promotionresult").append(new_content);
		page_end ++;
		}	
	}
});


/*$(function () {
	var pageCount=0;  // 分页总数量
	
	$.ajax({
		type: "get",
		url: "/customer/promotionListSize.html",
		async: false,//不是异步加载,让ajax先执行
		dataType: "json",
		success: function(data) {
			pageCount = data;
		}
	});
	
	
    // $("#pagination").pagination(pageCount); //简单初始化方法
    $("#promotionresult").pagination(pageCount,    // 分布总数量，必须参数
	   {
	     callback: PageCallback,  // PageCallback() 为翻页调用次函数。
         prev_text: "« 上一页",
         next_text: "下一页 »",
         items_per_page:10,
         num_edge_entries: 2,       // 两侧首尾分页条目数
         num_display_entries: 10,    // 连续分页主体部分分页条目数
         current_page: 0,   // 当前页索引
         link_to: "?page=__id__"  // 分页的js中会自动把"__id__"替换为当前的数。0
	});
});

function PageCallback(page_index,jq)
{
     $.ajax({   
        type: "get",  
        dataType: "text",  
        url: '/customer/queryPromotionByPage.html',      // 提交到一般处理程序请求数据
        data: "page=" + page_index,          // 提交两个参数：pageIndex(页面索引)，pageSize(显示条数)
        success: function(data) {
                   // 后台服务返回数据，重新加载数据
        	alert(data);
        }  
   }); 
}	*/
		

















