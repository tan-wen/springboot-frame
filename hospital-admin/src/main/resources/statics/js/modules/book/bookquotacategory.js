$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'book/bookquotacategory/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 },
			{ label: '医技科室', name: 'detectionDepEntity.name', index: 'detection_dep_id', width: 80 },
            { label: '就诊类型', name: 'category', index:'category',width: 80 },
			{ label: '排序', name: 'position', index: 'position', width: 80 }
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
		bookQuotaCategory: {},
		detectionDeps:[],
		trs:[],
		deps:[],
		inpatient:{},
		q:{name:null}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bookQuotaCategory = {};
			vm.getDetectionDeps();
			vm.getDeps();
			vm.trs=[];
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
			vm.getDetectionDeps();
			vm.getDeps();
		},
		saveOrUpdate: function (event) {
			var position=vm.bookQuotaCategory.position;
			var url = vm.bookQuotaCategory.id == null ? "book/bookquotacategory/save" : "book/bookquotacategory/update";
			vm.bookQuotaCategory.deps = vm.trs;
			if(position==null){
				toastr.error("请输入排序")
			}else if(position<200||position>300) {
				toastr.error("请输入200~300之间的数");
				vm.bookQuotaCategory = {position: null};
			}else {
				$.ajax({
					type: "POST",
					url: baseURL + url,
					contentType: "application/json",
					data: JSON.stringify(vm.bookQuotaCategory),
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
			}
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "book/bookquotacategory/delete",
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
			$.get(baseURL + "book/bookquotacategory/info/"+id, function(r){
                vm.bookQuotaCategory = r.bookQuotaCategory;
                vm.trs = r.deps;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'name':vm.q.name},
                page:page
            }).trigger("reloadGrid");
		},
		getDetectionDeps: function () {
			$.get(baseURL + "base/basedetectiondep/all", function (r) {
				vm.detectionDeps = r.detectionDeps;
			})
		},
		insertDep: function () {
			vm.trs.push({});
		},
		removeDep: function (idx, id) {
			if (id) {
				$.ajax({
					url: baseURL + "book/bookquotacategory/deletedepbygroupId",
					type: "POST",
					contentType: "application/json",
					data:JSON.stringify(id),
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
		},
		getDeps:function() {
			$.get(baseURL + "base/basedep/all", function (r) {
				vm.deps = r.deps;
			});
		},
		judge:function () {
				var position=vm.bookQuotaCategory.position;
				if(position<200||position>300){
					toastr.error("请输入200~300之间的数");
					vm.bookQuotaCategory={position:null};
				}
		}
	}
});