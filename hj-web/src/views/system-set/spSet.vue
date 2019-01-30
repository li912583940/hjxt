<!--描述： 审批设置 -->
<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 1081px">
      <el-table-column width="280" align="center" label="审批流程名称">
        <template slot-scope="scope">
          <span>{{scope.row.spName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="审批流程说明">
        <template slot-scope="scope">
          <span>{{scope.row.spExplain}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="启用状态">
        <template slot-scope="scope">
          <span v-if="scope.row.usable==1">启用</span>
          <span v-if="scope.row.usable==0" style="color: red;">禁用</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="审批总阶段">
        <template slot-scope="scope">
          <span>{{scope.row.maxNum}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('criminal.actions')" width="200">
        <template slot-scope="scope">
        <!--	<el-button type="primary" size="mini" icon="el-icon-edit" @click="search(scope.row)">查看</el-button>-->
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleConf(scope.row)">配置</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <!--<div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>-->

  <!--描述：查看流程 -->
  <!--<el-dialog title="查看流程" :visible.sync="dialogSearchVisible">
  	<el-tabs type="border-card" style="margin-left: 14px;">
			  <el-tab-pane label="审批部门" v-if="is_seeDept==1">
			    <el-table :key='tableKey' :data="seeDeptlist"   border fit highlight-current-row style="width: 281px">
			      <el-table-column width="280" align="center" label="部门名称">
			        <template slot-scope="scope">
			          <span>{{scope.row.spDeptName}}</span>
			        </template>
			      </el-table-column>
			    </el-table>
			  </el-tab-pane>
			  <el-tab-pane label="审批用户" v-if="is_seeUser==1">
			  	<el-table :key='tableKey' :data="seeUserslist"   border fit highlight-current-row style="width: 401px">
			      <el-table-column width="200" align="center" label="用户编号">
			        <template slot-scope="scope">
			          <span>{{scope.row.spUserNo}}</span>
			        </template>
			      </el-table-column>
			      <el-table-column width="200" align="center" label="用户姓名">
			        <template slot-scope="scope">
			          <span>{{scope.row.spUserName}}</span>
			        </template>
			      </el-table-column>
			    </el-table>
			  </el-tab-pane>
			</el-tabs>
  </el-dialog>-->
  
	<!-- 新增或编辑 -->
    <el-dialog title="配 置" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="180px" style='width: 420px; margin-left:16%;' >
        <el-form-item label="审批流程名称" prop="spName">
          <el-input v-model="dataForm.spName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="审批流程说明" prop="spExplain">
          <el-input v-model="dataForm.spExplain" ></el-input>
        </el-form-item>
        <el-form-item label="是否启用" prop="usable">
        	<el-radio-group v-model="dataForm.usable">
				    <el-radio :label="0">关闭</el-radio>
				    <el-radio :label="1">开启</el-radio>
				  </el-radio-group>
        </el-form-item>
        <el-form-item v-if="dataForm.spNo==5 || dataForm.spNo==6" label="审批亲属关系">
        	<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="gxValue"
					    :data="gxData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference">添加关系</el-button>
					</el-popover>
        </el-form-item>
        
        <el-form-item label="一级审批人">
        	<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="deptValue1"
					    :data="deptData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference">添加部门</el-button>
					</el-popover>
					<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="userValue1"
					    :data="userData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference" style="margin-left: 20px;">添加用户</el-button>
					</el-popover>
        </el-form-item>
        
        <el-form-item label="二级审批人">
        	<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="deptValue2"
					    :data="deptData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference">添加部门</el-button>
					</el-popover>
					<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="userValue2"
					    :data="userData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference" style="margin-left: 20px;">添加用户</el-button>
					</el-popover>
        </el-form-item>
        
        <el-form-item label="三级审批人">
        	<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="deptValue3"
					    :data="deptData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference">添加部门</el-button>
					</el-popover>
					<el-popover
					  placement="bottom"
					  width="522"
					  trigger="click">
					  <el-transfer
					  	filterable
					    :filter-method="transferFilter"
					    filter-placeholder="请输入关键字搜索"
					    v-model="userValue3"
					    :data="userData"
					    :titles="['未拥有', '已拥有']">
					  </el-transfer>
					  <el-button slot="reference" style="margin-left: 20px;">添加用户</el-button>
					</el-popover>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
    
	
  </div>
</template>

<script>
import { findPojo, findDetails, RequestSpConf, GetDeptList, GetUserList, GetGxList} from '@/api/spSet'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'criminal',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listQuery: {
      	jqName: undefined,
        pageNum: 1,
        pageSize: 20
      },
      /** 查看流程 开始 */
    	add:'adffg',
      dialogSearchVisible: false,
      searchActive: 0,
      /** 查看流程 结束 */
     
     
      // 新增或编辑弹窗
      dataForm: { 
        id: undefined,
        spNo: undefined,
        spName: undefined,
        spExplain: undefined,
        usable: 0
      },
      dialogFormVisible: false,
      gxData: [], //审批家属配置
      gxValue: [], //审批家属配置
      
    	deptData: [],
    	userData: [],
    	
    	deptValue1: [],
	    userValue1: [],
	    deptValue2: [],
	    userValue2: [],
	    deptValue3: [],
	    userValue3: [],
        
      rules: {
        
      },
    }
  },
  filters: {
    dateFormat(date) {
		  //时间格式化  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
	  }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.getDeptList()
    this.getUserList()
    this.getGxList()
  },
  methods: {
    getList() {
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
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
    
    getGxList() {
    	if(this.gxData.length === 0) {
    		GetGxList({}).then((res) => {
	    		let list = res.list
	    		if(list == undefined){
			 			return false;
			 		}
	    		list.forEach((item, index) => {
				 			this.gxData.push({
				 				label: item.qsGx,
				 				key: item.qsGx
				 			})
				 	})
	    		
	    	})
    	}
    },
    getDeptList() {
    	if(this.deptData.length ==0){ // 只查询一次
    		GetDeptList({}).then(res =>{
	    		let list = res.list
	    		if(list == undefined){
			 			return false;
			 		}
	    		
	    		list.forEach((item, index) => {
				 			this.deptData.push({
				 				label: item.deptName,
				 				key: item.id
				 			})
				 	})
	    	})
    	}
    },
    
    getUserList() {
    	if(this.userData.length ==0){ // 只查询一次
    		GetUserList({}).then(res =>{
	    		let list = res.list
	    		if(list == undefined){
			 			return false;
			 		}
	    		
	    		list.forEach((item, index) => {
				 			this.userData.push({
				 				label: item.userName,
				 				key: item.userNo
				 			})
				 	})
	    	})
    	}
    },
    search() { //查看流程
    	this.dialogSearchVisible = true
    	
    },
    //重置表单
	  resetForm() {
			this.dataForm.id = undefined
			this.dataForm.spName = undefined
			this.dataForm.spExplain = undefined
			this.dataForm.usable = 0
			
			this.gxValue=[]
			
			this.deptValue1= []
	    this.userValue1= []
	    this.deptValue2= []
	    this.userValue2= []
	    this.deptValue3= []
	    this.userValue3= []
    },
    handleConf(row) { // 打开配置面板
    	//this.resetForm()
    	
    	let param = {
    		id: row.id
    	}
    	findDetails(param).then((res) =>{
    		let jlHjSpSet = res.jlHjSpSet
    		this.dataForm.id = jlHjSpSet.id
    		this.dataForm.spNo = jlHjSpSet.spNo
        this.dataForm.spName =  jlHjSpSet.spName
        this.dataForm.spExplain =  jlHjSpSet.spExplain
        this.dataForm.usable =  jlHjSpSet.usable
        
        this.gxValue=res.gxList!=undefined?res.gxList:[]
      
        this.deptValue1 = res.deptList1
        this.userValue1 = res.userList1
        this.deptValue2 = res.deptList2
        this.userValue2 = res.userList2
        this.deptValue3 = res.deptList3
        this.userValue3 = res.userList3
        this.dialogFormVisible = true
    	}).catch(() =>{
    		this.dialogFormVisible = true
    	})
	    
    },
    updateData() { // 配置
    	if(this.dataForm.usable==1){
    		if((this.dataForm.spNo==5 || this.dataForm.spNo==6) && this.gxValue.length==0){ //亲属关系
    			Message({
		        message: '启用审批设置时，必须为当前配置添加需要审批的亲属关系',
			      type: 'error',
			      duration: 5 * 1000
		      });
		      return false;
    		}
    		if(this.deptValue1.length==0 && this.userValue1.length==0){
    			Message({
		        message: '启用审批设置时，必须设置审批人',
			      type: 'error',
			      duration: 5 * 1000
		      });
		      return false;
    		}
    	}
    	if(this.deptValue2.length>0 || this.userValue2.length>0){
    		if(this.deptValue1.length==0 && this.userValue1.length==0){
    			Message({
		        message: '您已设置二级审批人，但一级审批未设置。请设置一级审批人',
			      type: 'error',
			      duration: 5 * 1000
		      });
		      return false;
    		}
    	}
    	if(this.deptValue3.length>0 || this.userValue3.length>0){
    		if(this.deptValue2.length==0 && this.userValue2.length==0){
    			Message({
		        message: '您已设置三级审批人，但二级审批未设置。请设置二级审批人',
			      type: 'error',
			      duration: 5 * 1000
		      });
		      return false;
    		}
    	}
    	
    	let param = {
    		id: this.dataForm.id,
        spName: this.dataForm.spName,
        spExplain: this.dataForm.spExplain,
        usable: this.dataForm.usable,
        gxValue : this.gxValue!=undefined?this.gxValue.join():"",
    		deptValue1 : this.deptValue1!=undefined?this.deptValue1.join():"",
        userValue1 : this.userValue1!=undefined?this.userValue1.join():"",
        deptValue2 : this.deptValue2!=undefined?this.deptValue2.join():"",
        userValue2 : this.userValue2!=undefined?this.userValue2.join():"",
        deptValue3 : this.deptValue3!=undefined?this.deptValue3.join():"",
        userValue3 : this.userValue3!=undefined?this.userValue3.join():""
    	}
    	
      RequestSpConf(param).then(() => {
        this.dialogFormVisible = false
        this.getList()
      }).catch(error => {
        this.dialogFormVisible = false
      })
     
    },
		transferFilter(query, item){ //穿梭框搜索功能
	  	return item.label.indexOf(query) > -1;
	  },
	  
		dateFormats: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD HH:mm:ss");
		}
  }
}
</script>
