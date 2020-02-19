$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'base/basemember/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '姓名', name: 'name', index: 'name', width: 80 },
            { label: '病人登记号', name: 'patNo', index: 'pat_no', width: 80 },
            { label: '性别', name: 'genderDesc', index: 'gender', width: 80 },
			{ label: '出生日期', name: 'birthday', index: 'birthday', width: 80 },
            { label: '手机号码', name: 'phone', index: 'phone', width: 80 },
            { label: '备注', name: 'remark', index: 'remark', width: 80 },
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
		elem: "#birthday-input",
		min: "1900-01-01",
		max: 0,
		done: function(value){
			vm.baseMember.birthday = value;
		}
	})
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			name: null
		},
		showList: true,
		title: null,
		baseMember: {},
		genders:null
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.baseMember = {phone:"",patNo:""};
			vm.getGenders();
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
			vm.getGenders();
            vm.getInfo(id);
		},
		saveOrUpdate: function (event) {
			var Empty=validator.isEmpty(vm.baseMember.patNo);
			var isMobilePhone=validator.isMobilePhone(vm.baseMember.phone,'zh-CN');
			if(Empty){
				toastr.error("登记号不得为空");
				return false;
			}else if(!isMobilePhone){
				toastr.error("请输入正确格式的手机号");
                return false;
			}else{
				var url = vm.baseMember.id == null ? "base/basemember/save" : "base/basemember/update";
				$.ajax({
					type: "POST",
					url: baseURL + url,
					contentType: "application/json",
					data: JSON.stringify(vm.baseMember),
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
				    url: baseURL + "base/basemember/delete",
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
			$.get(baseURL + "base/basemember/info/"+id, function(r){
                vm.baseMember = r.baseMember;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'keyword': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		},
		getGenders: function() {
			$.get(baseURL + "sys/dict/sex", function (r) {
				vm.genders = r.sex;
			})
		}
	}
});