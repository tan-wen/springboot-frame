$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'book/bookweek/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '星期', name: 'weekDesc', index: 'week', width: 80 }
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
		bookWeek: {},
		weeks:{},
		allSlices:[],
		trs:[],
		q:{name:null}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bookWeek = {};
			vm.trs = [];
			vm.getWeeks();
			vm.getSlices();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			vm.getWeeks();
			vm.getSlices();
            vm.getInfo(id);
		},
		saveOrUpdate: function (event) {
			var url = vm.bookWeek.id == null ? "book/bookweek/save" : "book/bookweek/update";
			vm.bookWeek.slices = vm.trs;
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.bookWeek),
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
				    url: baseURL + "book/bookweek/delete",
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
			$.get(baseURL + "book/bookweek/info/"+id, function(r){
                vm.bookWeek = r.bookWeek;
                vm.trs = r.slices;
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
		getWeeks: function() {
			$.get(baseURL + "sys/dict/week", function (r) {
				vm.weeks = r.week;
			})
		},
		getSlices: function() {
			$.get(baseURL + "book/booktimeslice/all", function (r) {
				vm.allSlices = r.slices;
			})
		},
		insertSlice: function() {
			vm.trs.push({});
		},
		removeSlice: function (idx, sliceId) {
			if (sliceId) {
				var sliceIds = [];
				sliceIds.push(sliceId);
				$.ajax({
					url: baseURL + "book/bookweektimeslice/delete",
					type: "POST",
					contentType: "application/json",
					data:JSON.stringify(sliceIds),
					success: function(r){
						if(r.code != 0){
							alert(r.msg);
						} else {
							vm.trs.splice(idx, 1);
						}
					}
				})
			} else {
				vm.trs.splice(idx, 1);
			}
		}
	}
});