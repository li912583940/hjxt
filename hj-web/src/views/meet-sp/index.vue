<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="280" align="center" label="审批名称">
        <template slot-scope="scope">
          <span>{{scope.row.setName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="审批类型">
        <template slot-scope="scope">
          <span v-if="scope.row.type==1">会见登记</span>
          <span v-if="scope.row.type==2">添加亲属</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="监区名称">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="280" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="亲属附件">
        <template slot-scope="scope">
           <span v-if="scope.row.enclosureUrl != undefined">
		       	  <el-button type="primary"  size="mini" v-waves icon="el-icon-download" @click="wordDownload(scope.row)" >下载附件</el-button>
		       </span>
		       <span else></span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="申请时间">
        <template slot-scope="scope">
          <span>{{scope.row.tjTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="审批总阶段">
        <template slot-scope="scope">
          <span>{{scope.row.maxNum}}</span>
        </template>
      </el-table-column>
      <el-table-column width="110" align="center" label="当前审批阶段">
        <template slot-scope="scope">
          <span>{{scope.row.speedProgress}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="当前阶段能审批者">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="seeSpUser(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="seeDetails(scope.row)">查看详情</el-button>
          <el-button v-if="scope.row.spPermission==1" type="primary" size="mini" @click="toSp(scope.row)">我要审批</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

		<!-- 当前阶段的审批者 -->
		<el-dialog title="当前阶段的审批者" :visible.sync="dialogSpUserVisible" width="500px">
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
		</el-dialog>	
		
		 <el-dialog title="详情" :visible.sync="dialogSpDetailsVisible"  width="1000px">
		 		<el-table :key='tableKey' :data="spDetailsList"   border fit highlight-current-row
		      style="941px">
		      <el-table-column width="110px" align="center" label="审批人编号">
		        <template slot-scope="scope">
		          <span>{{scope.row.userNo}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="129px" align="center" label="审批人姓名">
		        <template slot-scope="scope">
		          <span>{{scope.row.userName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160px" align="center" label="审批人部门">
		        <template slot-scope="scope">
		          <span>{{scope.row.deptName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="100px" align="center" label="审批状态">
		        <template slot-scope="scope">
		          <span v-if="scope.row.state==1">通过</span>
		          <span v-if="scope.row.state==0" style="color: red;">不通过</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="200px" align="center" label="审批说明">
		        <template slot-scope="scope">
		          <span>{{scope.row.explain}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160px" align="center" label="审批时间 ">
		        <template slot-scope="scope">
		          <span>{{scope.row.spTime | dateFormat }}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="100px" align="center" label="审批阶段">
		        <template slot-scope="scope">
		          <span>{{scope.row.speedProgress}}</span>
		        </template>
		      </el-table-column>
		    </el-table>
		    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogSpDetailsVisible = false">关 闭</el-button>
      </div>
		 </el-dialog>
		 
		 <el-dialog title="审批" :visible.sync="dialogSpVisible"  width="1000px">
		 		<el-table :key='tableKey' :data="toSpDetailsList"   border fit highlight-current-row
		      style="941px">
		      <el-table-column width="110px" align="center" label="审批人编号">
		        <template slot-scope="scope">
		          <span>{{scope.row.userNo}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="129px" align="center" label="审批人姓名">
		        <template slot-scope="scope">
		          <span>{{scope.row.userName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160px" align="center" label="审批人部门">
		        <template slot-scope="scope">
		          <span>{{scope.row.deptName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="100px" align="center" label="审批状态">
		        <template slot-scope="scope">
		          <span v-if="scope.row.state==1">通过</span>
		          <span v-if="scope.row.state==0" style="color: red;">不通过</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="200px" align="center" label="审批说明">
		        <template slot-scope="scope">
		          <span>{{scope.row.explain}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160px" align="center" label="审批时间 ">
		        <template slot-scope="scope">
		          <span>{{scope.row.spTime | dateFormat }}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="100px" align="center" label="审批阶段">
		        <template slot-scope="scope">
		          <span>{{scope.row.speedProgress}}</span>
		        </template>
		      </el-table-column>
		    </el-table>
		    <div style="margin-top: 15px;"></div>
		    <div class="filter-container">
		    	<span style="font-size: 18px;">审批说明: </span><el-input style="width: 500px;" class="filter-item" placeholder="输入审批说明" v-model="spDataForm.explain" clearable>
		      </el-input>
		      <span style="font-size: 18px; margin-left: 40px;">审批结果: </span><el-select  style="width: 200px" class="filter-item" v-model="spDataForm.state" placeholder="选择审批结果">
		        <el-option v-for="item in states" :key="item.id" :label="item.name" :value="item.id">
		        </el-option>
		      </el-select>
		      
		    </div>
		    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogSpVisible = false">关 闭</el-button>
        <el-button type="primary" @click="updateSpData">确 定</el-button>
      </div>
		 </el-dialog>
  </div>
</template>

<script>
import { findPojo, FindDetails, FindSpeedSpUser, RequestSpData, WordDownload  } from '@/api/meetSp'

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
        pageNum: 1,
        pageSize: 20
      },
      /** 查看当前阶段的审批者 开始 */
      dialogSpUserVisible: false,
      is_seeDept: 0,
      is_seeUser: 0,
      seeDeptlist: null,
      seeUserslist: null,
      /** 查看当前阶段的审批者 结束 */
     
      /** 查看详情 开始 */
      dialogSpDetailsVisible: false,
      spDetailsList: null,
      /** 查看详情 结束 */
     
      /** 我要审批 开始 */
      dialogSpVisible: false,
      toSpDetailsList: null,
      spDataForm: {
      	spId: undefined,
      	speedProgress: undefined,
      	explain: undefined,
      	state: 1,
      },
      states: [
      	{
      		id: 1,
      		name: '通过'
      	},
      	{
      		id: 0,
      		name: '不通过'
      	}
      ],
      /** 我要审批 结束 */
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
  methods: {
    getList() {
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
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
    
    /** 查看当前阶段的审批者 开始 */
    seeSpUser(row) {
    	this.dialogSpUserVisible = true
    	let param ={
    		spSetNo: row.setNo,
    		speedProgress: row.speedProgress
    	}
    	FindSpeedSpUser(param).then(res =>{
    		let dept = res.deptList
    		if(dept.length>0){
    			this.is_seeDept=1
    			this.seeDeptlist=dept
    		}
    		let user = res.userList
    		if(user.length>0){
    			this.is_seeUser=1
    			this.seeUserslist=user
    		}
    	})
    },
    /** 查看当前阶段的审批者 结束 */
   
    /** 查看详情开始 */
    
    seeDetails(row){ //查看详情
    	this.dialogSpDetailsVisible= true
    	let param= {
    		spId:row.id
    	}
    	FindDetails(param).then(res =>{
    		this.spDetailsList = res.list
    	})
    },
    /** 查看详情结束 */
   
    /** 审批 开始*/
    toSp(row){
    	this.dialogSpVisible= true
    	let param= {
    		spId:row.id
    	}
    	FindDetails(param).then(res =>{
    		this.toSpDetailsList = res.list
    	})
    	this.spDataForm.spId=row.id
    	this.spDataForm.speedProgress=row.speedProgress
    },
    updateSpData() {
    	RequestSpData(this.spDataForm).then(res =>{
    		Message({
	        message: res.errMsg,
		      type: 'success',
		      duration: 5 * 1000
	      });
	      this.getList()
    	})
    	this.dialogSpVisible= false
    },
    /** 审批 结束*/

    wordDownload(row){
    	let param ={
    		enclosureUrl: row.enclosureUrl
    	}
    	let filename= row.enclosureUrl
    	filename="亲属附件"+ filename.substring(filename.lastIndexOf("."))
    	WordDownload(param).then(res => {
	      var blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
	     	if (window.navigator && window.navigator.msSaveOrOpenBlob) { // IE浏览器
        	window.navigator.msSaveOrOpenBlob(blob, filename);
    		}else{ //非IE浏览器
	     		var downloadElement = document.createElement('a')
		     	var href = window.URL.createObjectURL(blob)
		     	downloadElement.href = href
		     	//downloadElement.download = '亲属附件.docx'
		     	downloadElement.download = filename
		     	document.body.appendChild(downloadElement)
		     	downloadElement.click()
	     		document.body.removeChild(downloadElement) // 下载完成移除元素
		     	window.URL.revokeObjectURL(href) // 释放掉blob对象
	     	}
	    }).catch(error => {
	        console.log(error)
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
