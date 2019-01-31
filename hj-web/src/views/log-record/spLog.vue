<!--
	描述：审批记录
-->
<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入罪犯编号" v-model="listQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入罪犯姓名" v-model="listQuery.frName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.jqNo" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.state" placeholder="选择审批状态">
        <el-option v-for="item in states" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
     <!-- <el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>-->
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
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
      <el-table-column width="110" align="center" label="罪犯编号">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="280" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="申请时间">
        <template slot-scope="scope">
          <span>{{scope.row.tjTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="最后审批时间">
        <template slot-scope="scope">
          <span>{{scope.row.lastSpTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="审批状态">
        <template slot-scope="scope">
          <span v-if="scope.row.state==1">审批通过</span>
          <span v-if="scope.row.state==2" style="color: red;">审批不通过</span>
          <span v-if="scope.row.state==3">取消审批</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="审批总阶段">
        <template slot-scope="scope">
          <span>{{scope.row.maxNum}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="审批阶段">
        <template slot-scope="scope">
          <span>{{scope.row.speedProgress}}</span>
        </template>
      </el-table-column>
	  <el-table-column width="150px" align="center" label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="seeDetails(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>


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

  </div>
</template>

<script>
import { findPojo, findJqList, exportExcel, FindDetails} from '@/api/spLog'

import moment from 'moment'
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'relatives',
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
        pageSize: 10,
        frNo: undefined,
        frName: undefined,
        state: undefined,
        jqNo: undefined
      },
      downloadLoading: false,
	  states: [
	  	{
	  	  id:1,
	  	  name: '审批通过'
	  	},
	  	{
	  	  id:2,
	  	  name: '审批不通过'
	  	},
	  	{
	  	  id:3,
	  	  name: '取消审批'
	  	}
	  ],
	  jqs: [ // 监区下拉选框
      
      ],
      
	  /** 查看详情 开始 */
      dialogSpDetailsVisible: false,
      spDetailsList: null,
      /** 查看详情 结束 */
      
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
    this.getJqList()
  },
  mounted() {
  },
  methods: {
    getList() {
      this.listLoading = true
      if(this.listQuery.frNo== undefined || this.listQuery.frNo== ''){
      	this.listQuery.frNo = undefined
      }
  	  if(this.listQuery.frName== undefined || this.listQuery.frName== ''){
      	this.listQuery.frName = undefined
      }
  	  if(this.listQuery.state== undefined || this.listQuery.state== ''){
      	this.listQuery.state = undefined
      }
  	  if(this.listQuery.jqNo== undefined || this.listQuery.jqNo== ''){
      	this.listQuery.jqNo = undefined
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
    getJqList() { //监区下拉框
    	if(this.jqs.length === 0) {
    		findJqList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
				  let value = {}
				  value.id = x.jqNo
				  value.name = x.jqName
				  this.jqs.push(value)
				}
	    	})
    	}
    },
    handleDownload() {
      if(this.listQuery.frName== undefined || this.listQuery.frName== ''){
      	this.listQuery.frName = undefined
      }

		exportExcel(this.listQuery).then(res => {
	      var blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
	     	if (window.navigator && window.navigator.msSaveOrOpenBlob) { // IE浏览器
        	window.navigator.msSaveOrOpenBlob(blob, '登记记录.xls');
    		}else{ //非IE浏览器
    			var downloadElement = document.createElement('a')
		     	var href = window.URL.createObjectURL(blob)
		     	downloadElement.href = href
		     	downloadElement.download = '登记记录.xls'
		     	document.body.appendChild(downloadElement)
		     	downloadElement.click()
	     		document.body.removeChild(downloadElement) // 下载完成移除元素
		     	window.URL.revokeObjectURL(href) // 释放掉blob对象
    		}
	     	
			}).catch(error => {
         console.log(error)
      })
    },
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
   
	dateFormats: function (val) {
		if(!val){
			return undefined
		}
		return moment(val).format("YYYY-MM-DD HH:mm:ss");
	},
  }
}
</script>
