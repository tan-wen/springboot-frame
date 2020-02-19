$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'base/baseorder/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', index: 'id', width: 20, key: true,align:'center'},
			{ label: 'HIS医嘱项目id', name: 'hisId', index: 'his_id', width: 40 ,align:'center'},
			{ label: '所属预约项目', name: 'bookItemEntity.name', index: 'b_name', width: 50 },
			{ label: '名称', name: 'name', index: 'name', width: 100 },
			{ label: '子分类id', name: 'arcicRowId', index: 'arcic_row_id', width: 30 ,align:'center'},
			{ label: '子分类',name: 'arcicName', index: 'arcic_name', width: 60 },
			{ label: '大类id', name: 'orcatRowId', index: 'orcat_row_id', width: 30 ,align:'center'},
			{ label: '大类', name: 'orcatName', index: 'orcat_name', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 50,
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
		baseOrder: {},
		bookItems:[],
		q:{keyword:null}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.baseOrder = {};
			vm.getBookItems();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			vm.getBookItems();
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.baseOrder.id == null ? "base/baseorder/save" : "base/baseorder/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.baseOrder),
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
				    url: baseURL + "base/baseorder/delete",
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
			$.get(baseURL + "base/baseorder/info/"+id, function(r){
                vm.baseOrder = r.baseOrder;
            });
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{"keyword":vm.q.keyword},
                page:page
            }).trigger("reloadGrid");
		},
		getBookItems:function() {
			$.get(baseURL + "book/bookitem/all", function (r) {
				vm.bookItems = r.bookItems;
			})
		}
	}
});