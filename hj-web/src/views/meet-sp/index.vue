<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="160" align="center" label="所属监区">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="审批申请人">
        <template slot-scope="scope">
          <span>{{scope.row.spTjUser}}[{{scope.row.spTjUserName}}]</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="申请时间">
        <template slot-scope="scope">
          <span>{{scope.row.spTjTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="审批部门">
        <template slot-scope="scope">
          <span>{{scope.row.spGroupName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="审批人">
        <template slot-scope="scope">
          <span>{{scope.row.spUserName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300px" align="center" label="审批状态">
        <template slot-scope="scope">
          <span v-if="scope.row.spState==1">待审批</span>
          <span v-else-if="scope.row.spState==2">通过</span>
          <span v-else-if="scope.row.spState==3">不通过</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="seeDetails(scope.row)">查看详情</el-button>
          <el-button v-if="scope.row.spState==1" type="primary" size="mini" @click="toSp(scope.row)">我要审批</el-button>
          <span v-if="scope.row.spState!=1" style="margin-left: 3px;">审批完毕</span>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>


		 <el-dialog title="详情" :visible.sync="dialogSpVisible"  width="70%">
		 		<div class="filter-container">
		 			<div v-if="jlHjSp!=null">
		    	<span v-if="jlHjSp.jqName!=null">监区 <el-tag color="#FFEFD5">{{jlHjSp.jqName}}</el-tag></span>
		    	<span v-if="jlHjSp.frNo!=null">罪犯编号 <el-tag color="#FFEFD5">{{jlHjSp.frNo}}</el-tag></span>
		    	<span v-if="jlHjSp.frName!=null">罪犯姓名 <el-tag color="#FFEFD5">{{jlHjSp.frName}}</el-tag></span>
		    	<span v-if="jlHjSp.spReason!=null">提交审批时原因 <el-tag color="#FFEFD5">{{jlHjSp.spReason}}</el-tag></span>
		    	<span v-if="jlHjSp.spRemarks!=null">审批备注 <el-tag color="#FFEFD5">{{jlHjSp.spRemarks}}</el-tag></span>
		      </div>
		    </div>
		 		<el-table :key='spQstableKey' :data="spQsList"   border fit highlight-current-row
		      style="width: 100%">
		      <el-table-column width="160" align="center" label="亲属姓名">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsName}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="120" align="center" label="证件类别">
		        <template slot-scope="scope">
		          <span v-if="scope.row.qsZjlb==1">身份证</span>
		          <span v-if="scope.row.qsZjlb==2">警官证</span>
		          <span v-if="scope.row.qsZjlb==3">工作证</span>
		          <span v-if="scope.row.qsZjlb==4">其他</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="160px" align="center" label="证件号码">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsSFZ}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="100px" align="center" label="关系">
		        <template slot-scope="scope">
		          <span>{{scope.row.qsGx}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="140px" align="center" label="是否特批亲属">
		        <template slot-scope="scope">
		          <span v-if="scope.row.special==0">否</span>
		          <span v-if="scope.row.special==1">是</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="300px" align="center" label="审批原因">
		        <template slot-scope="scope">
		          <span>{{scope.row.spReason}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="300px" align="center" label="提交审批时备注">
		        <template slot-scope="scope">
		          <span>{{scope.row.spbz}}</span>
		        </template>
		      </el-table-column>
		      <el-table-column width="300px" align="center" label="审批备注">
		        <template slot-scope="scope">
		          <span>{{scope.row.spRemarks}}</span>
		        </template>
		      </el-table-column>
		    </el-table>
		    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogSpVisible = false">关 闭</el-button>
      </div>
		 </el-dialog>
  </div>
</template>

<script>
import { findPojo, FindDetails } from '@/api/meetSp'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


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
      
      /** 查看详情开始 */
      dialogSpVisible: false,
      spQstableKey: 1,
      spQsList: null,
      jlHjSp: null
      /** 查看详情结束 */
     
    }
  },
  filters: {
    
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
    
    /** 查看详情开始 */
    refSpQsList(){ // 重置详情数据
    	if(this.spQsList != null){
    		this.spQsList= null
    	}
    	if(this.jlHjSp!= null){
    		this.jlHjSp= null
    	}
    },
    
    seeDetails(row){ //查看详情
    	this.refSpQsList()
    	
    	this.dialogSpVisible= true
    	let param= {
    		spId:row.spId
    	}
    	FindDetails(param).then(res =>{
    		this.spQsList = res.jlHjSpQsList
    		this.jlHjSp =res.jlHjSp
    	})
    },
    /** 查看详情结束 */
   
    /** 审批 开始*/
    toSp(row){
    	
    },
    /** 审批 结束*/
   
    dateFormat(row, column) {
			//时间格式化  
	    let date = row[column.property];  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
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
