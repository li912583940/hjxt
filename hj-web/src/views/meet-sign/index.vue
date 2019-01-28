<template>
  <div class="app-container">

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column v-if="buttonRole.distributionPermission==1 || buttonRole.cancelDistributionPermission==1 || buttonRole.artificialPermission==1" align="center" :label="$t('criminal.actions')" width="300" fixed="left" >
        <template slot-scope="scope">
          <el-button v-if="buttonRole.distributionPermission==1" type="primary" size="mini" @click="fpZw(scope.row)">自动分配</el-button>
          <el-button v-if="buttonRole.cancelDistributionPermission==1" type="primary" size="mini" @click="qxFpZw(scope.row)">取消分配</el-button>
          <el-button v-if="buttonRole.artificialPermission==1" type="primary" size="mini" @click="getSurplusZw(scope.row)">人工分配</el-button>
        </template>
      </el-table-column>
      
      <el-table-column width="100" align="center" label="罪犯编号">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="分管等级">
        <template slot-scope="scope">
          <span>{{scope.row.jbName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="重点犯">
        <template slot-scope="scope">
          <span v-if="scope.row.stateZdzf=='否'">否</span>
          <span v-if="scope.row.stateZdzf!='否'">是</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="座位">
        <template slot-scope="scope">
          <span v-if="scope.row.fpFlag ==0">未分配</span>
          <span else>{{scope.row.zw}}</span>
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
      <el-table-column width="90" align="center" label="监区">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="亲属">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="会见时长">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}分钟</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="授权状态">
        <template slot-scope="scope">
          <span v-if="scope.row.shState=='1'">已授权</span>
          <span v-if="scope.row.shState=='0'">未授权</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.grantPermission==1 || buttonRole.cancelGrantPermission==1" width="180" align="center" label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.grantPermission==1" type="primary" size="mini" @click="grantCall(scope.row)">授权</el-button>
          <el-button v-if="buttonRole.cancelGrantPermission==1" type="primary" size="mini" @click="cancelGrantCall(scope.row)">取消授权</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 人工分配座位 -->
		<el-dialog title="人工分配座位" :visible.sync="dialogRgFpVisible">
			<el-form :rules="rules" :model="rgFpDataForm" ref="rgFpDataForm" label-position="right" label-width="120px" style='width: 300px; margin-left:25%;' >
        <el-form-item label="服刑人员姓名" prop="frName">
          <el-input v-model="rgFpDataForm.frName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="座位" prop="lineNo">
          <el-select class="filter-item" v-model="rgFpDataForm.lineNo" placeholder="请选择座位">
            <el-option v-for="item in zws" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
		  <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRgFpVisible = false">取 消</el-button>
        <el-button type="primary" @click="rgFpZw">确 定</el-button>
      </div>
		</el-dialog>

  </div>
</template>

<script>
import { findPojo, FpZw, QxFpZw, GetSurplusZw, RgFpZw, GrantCall, CancelGrantCall } from '@/api/meetSign'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'meetSign',
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
        pageSize: 20
      },
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	distributionPermission: 0, 
      	cancelDistributionPermission: 0,
      	artificialPermission: 0,
      	grantPermission : 0,
      	cancelGrantPermission: 0,
      },
      
      rgFpDataForm: {
      	hjId: undefined,
      	frName: undefined,
      	lineNo: undefined
      },
      
      zws:[],
      dialogRgFpVisible: false,
      
      rules: {
        lineNo: [{ required: true, message: '请选择座位', trigger: 'blur' }]
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
	  },
	  hjTimeFilter(d){ //会见时长
	  	if(d == undefined){
	  		return "";
	  	}
	  	return d
	  }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.setButtonRole()
  },
  methods: {
    getList() {
      this.listLoading = true
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      	 this.listLoading = false
      }).catch(error => {
          this.listLoading = false
      })
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
    		this.buttonRole.distributionPermission= 1
    		this.buttonRole.cancelDistributionPermission= 1
    		this.buttonRole.artificialPermission= 1
    		this.buttonRole.grantPermission= 1
    		this.buttonRole.cancelGrantPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetSign = buttonRoles.meetSign
    		if(meetSign.length>0){
    			for(let value of meetSign){
    				if(value=='distributionPermission'){
    					this.buttonRole.distributionPermission= 1
    				}else if(value=='cancelDistributionPermission'){
    					this.buttonRole.cancelDistributionPermission= 1
    				}else if(value=='artificialPermission'){
    					this.buttonRole.artificialPermission= 1
    				}else if(value=='grantPermission'){
    					this.buttonRole.grantPermission= 1
    				}else if(value=='cancelGrantPermission'){
    					this.buttonRole.cancelGrantPermission= 1
    				}
    			}
    		}
    	}
    },
    
    fpZw(row) {
    	let param = {
    		hjId: row.hjid
    	}
    	FpZw(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      this.getList()
    	})
    },
    
    qxFpZw(row){
    	let param ={
    		hjId: row.hjid
    	}
    	QxFpZw(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      
	      this.getList()
    	})
    },
    
    getSurplusZw(row) {
    	this.zws = []
    	
    	this.dialogRgFpVisible=true
    	
    	let param ={
    		hjId: row.hjid
    	}
    	GetSurplusZw(param).then(res => {
    		let jlHjDj = res.jlHjDj
    		this.rgFpDataForm.hjId = jlHjDj.hjid
    		this.rgFpDataForm.frName = jlHjDj.frName
    		
    		let sysHjLineList = res.sysHjLineList
    		for(let x of sysHjLineList){
    			let value = {}
    			value.id= x.lineNo
    			value.name = x.zw
    			this.zws.push(value)
    		}
    	})
    	
    	
    },
    
    rgFpZw() {
    	RgFpZw(this.rgFpDataForm).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      
	      this.dialogRgFpVisible=false
    	  this.getList();
    	})
    	
    },
    
    grantCall(row) {
    	let param ={
    		hjId: row.hjid
    	}
    	GrantCall(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
	      
    		this.getList();
    	})
    },
    
    cancelGrantCall(row){
    	let param ={
    		hjId: row.hjid
    	}
    	CancelGrantCall(param).then(res => {
    		Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
	      });
    		this.getList();
    	})
    	
    },
    
		dateFormats: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD HH:mm:ss");
		},
  }
}
</script>
