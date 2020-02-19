$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'book/bookitem/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '编码', name: 'code', index: 'code', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '医技科室', name: 'detectionDepName', index: 'detection_dep_id', width: 80 }
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		bookItem: {
			diffByWard: false
		},
		detectionDeps:[],
		trs:[],
		weeks:[],
		q:{name:null}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bookItem = {diffByWard: false};
			vm.getDetectionDeps();
			vm.getWeeks();
			vm.trs = [];
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id);
			vm.getDetectionDeps();
			vm.getWeeks();
		},
		saveOrUpdate: function (event) {
			var url = vm.bookItem.id == null ? "book/bookitem/save" : "book/bookitem/update";
			vm.bookItem.itemWeeks = vm.trs;
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.bookItem),
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
				    url: baseURL + "book/bookitem/delete",
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
			$.get(baseURL + "book/bookitem/info/"+id, function(r){
                vm.bookItem = r.bookItem;
                vm.trs = r.itemWeeks;
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
		getDetectionDeps: function () {
			$.get(baseURL + "base/basedetectiondep/all", function (r) {
				vm.detectionDeps = r.detectionDeps;
			})
		},
		getWeeks: function() {
			$.get(baseURL + "book/bookweek/all", function (r) {
				vm.weeks = r.weeks;
			})
		},
		insertWeek: function() {
            vm.trs.push({});
		},
		removeWeek: function (idx, weekId) {
			if (weekId) {
				var weekIds = [];
				weekIds.push(weekId);
				$.ajax({
					url: baseURL + "book/bookitemweek/delete",
					type: "POST",
					contentType: "application/json",
					data:JSON.stringify(weekIds),
					success: function(r){
						if(r.code != 0){
							alert(r.msg);
						} else {
							vm.trs.splice(idx, 1);
							toastr.success("删除成功");
						}
					}
				})
			} else {
				vm.trs.splice(idx, 1);
			}
		}
	}
});