<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">添加会见登记</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="100" align="center" :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="会见编号">
        <template slot-scope="scope">
          <span>{{scope.row.hjIndex}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" min-width align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="亲属">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="会见时长">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime | hjTimeFilter}}分钟</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="登记人">
        <template slot-scope="scope">
          <span>{{scope.row.djUser}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="座位">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="审批状态">
        <template slot-scope="scope">
          <span v-if="scope.row.state==3" style="color: red;">待审批</span>
          <span v-else>审批通过</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.hjType==1">亲属会见</span>
          <span v-else-if="scope.row.hjType==2">监护人会见</span>
          <span v-else-if="scope.row.hjType==3">律师会见</span>
          <span v-else-if="scope.row.hjType==4">使领馆探视</span>
          <span v-else-if="scope.row.hjType==5">提审会见</span>
          <span v-else-if="scope.row.hjType==6">公务会见</span>
          <span v-else-if="scope.row.hjType==9">特批会见</span>
          <span v-else-if="scope.row.hjType==99">其他会见</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="会见方式">
        <template slot-scope="scope">
          <span v-if="scope.row.hjMode==1">隔离会见</span>
          <span v-else-if="scope.row.hjMode==2">非隔离会见</span>
          <span v-else-if="scope.row.hjMode==3">远程视频会见</span>
          <span v-else-if="scope.row.hjMode==9">其他方式</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.printXpPermission==1 || buttonRole.editDjPermission==1 || buttonRole.cancelDjPermission==1 " align="center" :label="$t('criminal.actions')" width="220" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.printXpPermission==1" type="primary" size="mini"  @click="printXp(scope.row)">打印</el-button>
          <el-button v-if="buttonRole.editDjPermission==1" type="primary"  size="mini"  @click="handleOpenEditDj(scope.row)">修改</el-button>
          <el-button v-if="buttonRole.cancelDjPermission==1" type="danger"  size="mini" @click="handleDelete(scope.row)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>


    <!-- 打印小票 -->
    <el-dialog title="" :visible.sync="dialogFormVisible">
      <div id="wrap" class="wrap">
		  	<span v-for="x in this.printList">
		  	  <li>{{ x}}</li>
		  	</span>
		  </div>
      <div slot="footer" class="dialog-footer">
      	<el-button type="primary" @click="print">打 印</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        
      </div>
    </el-dialog>
    
    <!-- 新增或编辑 -->
    <el-dialog title="修改会见登记" :visible.sync="dialogDjVisible">
      <div class="filter-container">
	    	<span>{{$t('currency.frNo')}}</span> <el-input style="width: 200px;" v-model="dataForm.frNo" :disabled="true"></el-input>
	      <span style="margin-left: 20px;">{{$t('currency.frName')}}</span> <el-input style="width: 200px;" v-model="dataForm.frName":disabled="true"></el-input>
	      <span style="margin-left: 20px;">监区名称</span> <el-input  style="width: 200px;" v-model="dataForm.jqName":disabled="true"></el-input>
	    </div>
	    <div class="filter-container">
	    	<span>会见时长</span> <el-input style="width: 200px;" v-model="dataForm.hjTime"></el-input>
	      <span style="margin-left: 20px;">会见类型</span> 
	        <el-select  v-model="dataForm.hjType" placeholder="请选择会见类型">
            <el-option v-for="item in hjTypes" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
	      <span style="margin-left: 20px;">会见说明</span> <el-input  style="width: 200px;" v-model="dataForm.hjInfo"></el-input>
	    </div>
      <!-- 亲属开始 -->
			<el-card class="box-card">
		    <el-table :key='qsTableKey' ref="qsMultipleTable" :data="qsList" v-loading="qsListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
		      @selection-change="qsAllSelectionChange" @row-click="qsRowClick" style="width: 100%">
		      <el-table-column align="center" type="selection"  width="70" fixed="left">
		      </el-table-column>
		      <el-table-column align="center" label="亲属姓名" width="100">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="110px" align="center" label="关系">
		        <template slot-scope="scope">
		          <span>{{scope.row.gx}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="110px" align="center" label="证件类别">
		        <template slot-scope="scope">
		          <span v-if="scope.row.qsZjlb==1">身份证</span>
		          <span v-if="scope.row.qsZjlb==2">警官证</span>
		          <span v-if="scope.row.qsZjlb==3">工作证</span>
		          <span v-if="scope.row.qsZjlb==4">其他</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="150px" align="center" label="证件号码">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsSfz}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="150px" align="center" label="证件物理号">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsSfzWlh}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="110px" align="center" label="IC卡编号">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsCard}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160" align="center" label="性别">
		        <template slot-scope="scope">
		          <span>{{scope.row.xb}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="150px" align="center" label="地址">
		        <template slot-scope="scope">
		          <span>{{scope.row.dz}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="110px" align="center" label="电话">
		        <template slot-scope="scope">
		          <span>{{scope.row.tele}}</span>
		        </template>
		      </el-table-column>
					<el-table-column width="127px" align="center" label="备注">
		        <template slot-scope="scope">
		          <span>{{scope.row.bz}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="110px" align="center" label="审批状态">
		        <template slot-scope="scope">
		          <span v-if="scope.row.spState==1">已通过</span>
		          <span v-if="scope.row.spState==0">未通过</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="140px" align="center" label="是否禁止/禁止时间">
		        <template slot-scope="scope">
		          <span v-if="scope.row.hjStopTime!=null">是/{{scope.row.hjStopTime}}</span>
		          <span v-if="scope.row.hjStopTime==null">否/{{scope.row.hjStopTime}}</span>
		        </template>
		      </el-table-column>
		      
		    </el-table>
		    <!--<div class="pagination-container">
		      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="qsListQuery.pageNum" :page-sizes="[5,10]" :page-size="qsListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="qsTotal">
		      </el-pagination>
		    </div>-->
		  </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDjVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { findPojo, RequestPrintXp, RequestEditDj, RequestCancelDj, findQsPojo, GetQsIdsByHjid } from '@/api/meetRegister'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'meetRegister',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        frNo: undefined,
        frName: undefined
      },
      
      dialogFormVisible: false,
      printList : [],
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	printXpPermission: 0,
      	editDjPermission: 0,
      	editScPermission: 0, //修改会见时长权限
      	cancelDjPermission: 0
      },
      
      dialogDjVisible: false,
      qsSelections: [],
      dataForm: {
      	hjid: undefined,
      	frNo: undefined,
      	frName: undefined,
      	jqName: undefined,
      	hjTime: undefined,
      	hjType: undefined,
      	hjInfo: undefined,
      	qsIds: []
      },
      hjTypes:[
        {
      		id: 1,
      		name: '亲属会见'
      	},
      	{
      		id: 2,
      		name: '监护人会见'
      	},
      	{
      		id: 3,
      		name: '律师会见'
      	},
      	{
      		id: 4,
      		name: '使领馆探视'
      	},
      	{
      		id: 5,
      		name: '提审会见'
      	},
      	{
      		id: 6,
      		name: '公务会见'
      	},
      	{
      		id: 9,
      		name: '特批会见'
      	},
      	{
      		id: 99,
      		name: '其他会见'
      	},
      ],
      
      /** 修改会见登记  亲属表格 开始 */
      qsTableKey: 1,
      qsList: null,
      qsTotal: null,
      qsListLoading: false,
      qsListQuery: {
        pageNum: 1,
        pageSize: 10,
        frNo: undefined
      },
      /** 修改会见登记  亲属表格 结束 */
     
     refreshZ: this.$route.query.refreshZ, //监控页面是否刷新
    }
  },
  filters: {
  	dateFormat(date) {
		  //时间格式化  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
	  },
	  hjTimeFilter(d){ //会见时长
	  	if(d == undefined){
	  		return "";
	  	}
	  	return d/60
	  }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.setButtonRole()
  },
  watch: {
      refreshZ(now, before){
        if(now!=before){
   				this.reload()
        }
      }
  },
  methods: {
    getList() {
      this.listLoading = true
      if(!this.listQuery.frName){
      	this.listQuery.frName = undefined
      }
      if(!this.listQuery.frNo){
      	this.listQuery.frNo = undefined
      }
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      	 this.listLoading = false
      }).catch(error => {
          this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val
      this.getList()
    },
    
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.printXpPermission= 1
    		this.buttonRole.editDjPermission= 1
    		this.buttonRole.editScPermission= 1
    		this.buttonRole.cancelDjPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetRegister = buttonRoles.meetRegister
    		if(meetRegister != undefined && meetRegister.length>0){
    			for(let value of meetRegister){
    				if(value=='addPermission'){
    					this.buttonRole.addPermission= 1
    				}else if(value=='printXpPermission'){
    					this.buttonRole.printXpPermission= 1
    				}else if(value=='editDjPermission'){
    					this.buttonRole.editDjPermission= 1
    				}else if(value=='editScPermission'){
    					this.buttonRole.editScPermission= 1
    				}else if(value=='cancelDjPermission'){
    					this.buttonRole.cancelDjPermission= 1
    				}
    			}
    		}
    	}
    },
    
    // 添加新的会见等级， 跳转至会见等级页面
    handleCreate() {
    	this.$router.push({ path: '/addHjDj' })
    },
    // 打印小票
    printXp(row) {
    	let param = {
    		hjid: row.hjid
    	}
    	RequestPrintXp(param).then((res) => {
          this.printList = res.list
      }).catch(error => {
	    })
			this.dialogFormVisible = true
    },
    print(){
    	var newstr = document.getElementsByClassName('wrap')[0].innerHTML
    	document.body.innerHTML = newstr
      window.print()
      // 重新加载页面，以刷新数据
      window.location.reload()
    },
    
    refDataForm(){ //清空表单
    	this.dataForm.hjid= undefined
    	this.dataForm.frNo =undefined
    	this.dataForm.frName = undefined
    	this.dataForm.jqName = undefined
    	this.dataForm.hjTime = undefined
    	this.dataForm.hjType = undefined
    	this.dataForm.hjInfo = undefined
    	
    	this.qsListQuery.frNo = undefined
    },
    handleOpenEditDj(row) { //打开修改会见
    	this.refDataForm()
    	
    	this.dialogDjVisible= true
    	this.dataForm.hjid= row.hjid
    	this.dataForm.frNo =row.frNo
    	this.dataForm.frName = row.frName
    	this.dataForm.jqName = row.jqName
    	this.dataForm.hjTime = row.hjTime!=null?row.hjTime/60:0
    	this.dataForm.hjType = row.hjType
    	this.dataForm.hjInfo = row.hjInfo
    	
    	this.qsListQuery.frNo= row.frNo
    	this.getQsFrList()
    	
//  	GetQsIdsByHjid({hjid:row.hjid}).then(res=> {
//  		let rows= res.list
//  		if (rows) {
//        rows.forEach(row => {
//          this.$refs.qsMultipleTable.toggleRowSelection(0);
//        });
//      }else {
//        this.$refs.qsMultipleTable.clearSelection();
//      }
//  	})
    },
    updateData() {
    	if(this.qsSelections.length == 0) {
    		this.$notify.error({
          title: '错误',
          message: '提交登记时，至少选择一位家属'
        })
    		return false
    	}
    	let qsid = ''
    	for(let x of this.qsSelections) {
    		qsid = qsid=='' || qsid==undefined?x.webId:qsid+','+x.webId
    	}
    	this.dataForm.qsIds = qsid
    	const loading = this.$loading({
	      lock: true,
	      text: 'Loading',
	      spinner: 'el-icon-loading',
	      background: 'rgba(0, 0, 0, 0.7)'
	    })
    	RequestEditDj(this.dataForm).then(res =>{
    		this.getList()
    	})
    	this.dialogDjVisible=false
    	loading.close();
    },
    //取消登记
		handleDelete(row) {
			this.$confirm('确认取消该会见吗?', '提示', {
				type: 'warning'
			}).then(() => {
				this.listLoading = true;
				let param = {
	    		id: row.hjid
	    	}
				RequestCancelDj(param).then((res) => {
	    		if(res.data == 0) {
          	this.$notify({
		          title: '成功',
		          message: '会见登记取消成功',
		          position: 'top-right',
		          type: 'success'
		        });
          }else if(res.data == 1) {
          	this.$notify({
		          title: '警告',
		          message: '已处会见通话状态，无法取消',
		          position: 'top-right',
		          type: 'warning'
		        });
          }
          this.getList()
	    	}).catch(error => {
	    		this.listLoading = false;
	      })
			})
		},
		/** 修改会见登记  亲属表格 开始 */
		getQsFrList() {
      this.qsListLoading = true
      if(!this.qsListQuery.frNo){
      	this.qsListQuery.frNo = undefined
      }
      findQsPojo(this.qsListQuery).then((res) => {
      	 this.qsList = res.pojo.list
      	 this.qsTotal = res.pojo.count
      	 this.qsListLoading = false
      }).catch(error => {
         this.qsListLoading = false
      })
    },
		qsRowClick(row){ // 单击亲属表格得某一行  让多选框处于选中事件
      this.$refs.qsMultipleTable.toggleRowSelection(row);
    },
  	qsAllSelectionChange(rows){ // 亲属表格 全选事件
  		this.qsSelections = rows;
  	},
  	/** 修改会见登记  亲属表格 结束 */
		dateFormats: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD HH:mm:ss");
		},
  }
}
</script>
