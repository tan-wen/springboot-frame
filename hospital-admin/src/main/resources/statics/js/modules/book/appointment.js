$(function () {
   //
});

var vm = new Vue({
	el:'.container',
	data:{
		showList: true,
		title: null,
        patientList: []
	},
	methods: {
        refresh: function () {
			var startDate = $("#startDate").val();
			if(!startDate){
               return ;
			}
            vm.getInfo(startDate,"");
            $("#patIdOrName").val("");
		},
		query: function () {
			var startDate = $("#startDate").val();
			var patIdOrName = $("#patIdOrName").val();
			if(!startDate){
               return ;
			}
			if(!patIdOrName){
                patIdOrName="";
			}

            vm.getInfo(startDate,patIdOrName);
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.baseOrder = {};
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

        getInfo: function(startDate,keywords){
            $.ajax({
                url:baseURL+"book/outpatient/list",
                data:{"startDate":startDate,"keywords":keywords},
                type:"post",
                dataType:"json",
                async:false,
                success:function(r){
                    if(r){
                        vm.patientList = r.patientList;
                    }else{
                       alert("该医生在选中日期无任何待预约病人");
                    }
                },
                error:function(){
                    alert("查询出错,请联系管理员");
                }
            })

        },





		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});