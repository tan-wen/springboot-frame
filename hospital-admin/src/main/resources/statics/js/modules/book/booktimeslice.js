$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'book/booktimeslice/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '开始时间', name: 'startTime', index: 'start_time', width: 80 }, 			
			{ label: '结束时间', name: 'endTime', index: 'end_time', width: 80 }, 			
			{ label: '时间间隔（秒）', name: 'interval', index: 'interval', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

	laydate.render({
		elem: "#start-time-input"
		,type: 'time'
		,max: "23:59:58"
		,done: function(value){
			vm.bookTimeSlice.startTime = value;
			vm.calcInterval();
		}
	});

	laydate.render({
		elem: "#end-time-input"
		, type: "time"
		, min: $("#start-time-input").val()
		, max: "23:59:59"
		, done: function (value) {
			vm.bookTimeSlice.endTime = value;
			vm.calcInterval();
		}
	});
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		bookTimeSlice: {},
		q:{name:null}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bookTimeSlice = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.bookTimeSlice.id == null ? "book/booktimeslice/save" : "book/booktimeslice/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.bookTimeSlice),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "book/booktimeslice/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "book/booktimeslice/info/"+id, function(r){
                vm.bookTimeSlice = r.bookTimeSlice;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		},
		calcInterval: function () {
			if(vm.bookTimeSlice.startTime && vm.bookTimeSlice.endTime) {
				vm.bookTimeSlice.interval = time2Sec(vm.bookTimeSlice.endTime) - time2Sec(vm.bookTimeSlice.startTime);
				vm.$forceUpdate();
			}
		}
	}
});