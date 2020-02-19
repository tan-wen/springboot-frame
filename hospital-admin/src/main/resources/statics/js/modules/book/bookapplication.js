$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'base/bookapplication/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '就诊id', name: 'treatmentId', index: 'treatment_id', width: 80 }, 			
			{ label: '预约记录id', name: 'bookRecordId', index: 'book_record_id', width: 80 }, 			
			{ label: '缴费状态', name: 'payStatus', index: 'pay_status', width: 80 }, 			
			{ label: '执行状态', name: 'exeStatus', index: 'exe_status', width: 80 }, 			
			{ label: '病历号', name: 'medicareno', index: 'medicareNo', width: 80 }, 			
			{ label: '卡号', name: 'cardNo', index: 'card_no', width: 80 }, 			
			{ label: '医保号', name: 'insuranceNo', index: 'insurance_no', width: 80 }, 			
			{ label: '科室名', name: 'ward', index: 'ward', width: 80 }, 			
			{ label: '就诊号', name: 'regNo', index: 'reg_no', width: 80 }, 			
			{ label: '患者姓名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '患者性别', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '床号', name: 'bedNo', index: 'bed_no', width: 80 }, 			
			{ label: '患者出生日期', name: 'dob', index: 'dob', width: 80 }, 			
			{ label: '患者年龄', name: 'age', index: 'age', width: 80 }, 			
			{ label: '转入科室', name: 'inLoc', index: 'in_loc', width: 80 }, 			
			{ label: '开单医生', name: 'appDoc', index: 'app_doc', width: 80 }, 			
			{ label: '接收科室', name: 'recLoc', index: 'rec_loc', width: 80 }, 			
			{ label: '申请日期', name: 'appDate', index: 'app_date', width: 80 }, 			
			{ label: '病人表RowId', name: 'inPatientNo', index: 'in_patient_no', width: 80 }, 			
			{ label: '电话', name: 'telNo', index: 'tel_no', width: 80 }, 			
			{ label: '住址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '医嘱项目ID', name: 'ordId', index: 'ord_id', width: 80 }, 			
			{ label: '检查项目名', name: 'ordName', index: 'ord_name', width: 80 }, 			
			{ label: '检查费用', name: 'price', index: 'price', width: 80 }, 			
			{ label: '有无模板', name: 'perTempl', index: 'per_templ', width: 80 }, 			
			{ label: '医嘱ID', name: 'oeOrditemId', index: 'oe_orditem_id', width: 80 }, 			
			{ label: '是否紧急', name: 'ungent', index: 'ungent', width: 80 }, 			
			{ label: '现状', name: 'patientNow', index: 'patient_now', width: 80 }, 			
			{ label: '主诊断', name: 'mainDiagose', index: 'main_diagose', width: 80 }, 			
			{ label: '执行项目', name: 'purpose', index: 'purpose', width: 80 }, 			
			{ label: '内容一', name: 'content1', index: 'content_1', width: 80 }, 			
			{ label: '内容二', name: 'content2', index: 'content_2', width: 80 }, 			
			{ label: '内容三', name: 'content3', index: 'content_3', width: 80 }, 			
			{ label: '内容四', name: 'content4', index: 'content_4', width: 80 }, 			
			{ label: '内容五', name: 'content5', index: 'content_5', width: 80 }, 			
			{ label: '内容六', name: 'content6', index: 'content_6', width: 80 }, 			
			{ label: '内容七', name: 'content7', index: 'content_7', width: 80 }, 			
			{ label: '预约日期', name: 'hopeDate', index: 'hope_date', width: 80 }, 			
			{ label: '创建人', name: 'createBy', index: 'create_by', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '最后修改人', name: 'lastModifyBy', index: 'last_modify_by', width: 80 }, 			
			{ label: '最后修改时间', name: 'lastModifyTime', index: 'last_modify_time', width: 80 }, 			
			{ label: '已删除', name: 'deleted', index: 'deleted', width: 80 }			
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
		bookApplication: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bookApplication = {};
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
			var url = vm.bookApplication.id == null ? "base/bookapplication/save" : "base/bookapplication/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.bookApplication),
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
				    url: baseURL + "base/bookapplication/delete",
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
			$.get(baseURL + "base/bookapplication/info/"+id, function(r){
                vm.bookApplication = r.bookApplication;
            });
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