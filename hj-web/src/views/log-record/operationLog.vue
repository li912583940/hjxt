<template>
  <div class="app-container">
    <div class="filter-container">
      	<el-date-picker
    		class="filter-item"
	      v-model="listQuery.callTimeStart"
	      align="right"
	      type="date"
	      placeholder="选择开始日期"
	      :picker-options="pickerOptionsStart">
	    </el-date-picker>
	    <el-date-picker
	    	class="filter-item"
	      v-model="listQuery.callTimeEnd"
	      align="right"
	      type="date"
	      placeholder="选择结束日期">
	    </el-date-picker>
	    <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.model" placeholder="选择模块">
	        <el-option v-for="item in models" :key="item.id" :label="item.name" :value="item.id">
	        </el-option>
        </el-select>
	    <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.type" placeholder="选择级别">
	        <el-option v-for="item in types" :key="item.id" :label="item.name" :value="item.id">
	        </el-option>
        </el-select>
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="用户编号" v-model="listQuery.userNo" clearable>
        </el-input>
	    <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="用户姓名" v-model="listQuery.userName" clearable>
	    </el-input>
	    <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="来源IP" v-model="listQuery.userIp" clearable>
	    </el-input>
	    <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
	    <!--<el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>-->
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="180" align="center"  label="时间">
        <template slot-scope="scope">
          <span>{{scope.row.logTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center"  label="级别">
        <template slot-scope="scope">
          <span>{{scope.row.type}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="模块">
        <template slot-scope="scope">
          <span>{{scope.row.model}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="操作">
        <template slot-scope="scope">
          <span>{{scope.row.op}}</span>
        </template>
      </el-table-column>
      <el-table-column width="400" align="center" label="内容">
        <template slot-scope="scope">
          <span>{{scope.row.info}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="用户编号">
        <template slot-scope="scope">
          <span>{{scope.row.userNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="用户姓名">
        <template slot-scope="scope">
          <span>{{scope.row.userName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="来源IP">
        <template slot-scope="scope">
          <span>{{scope.row.userIp}}</span>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>


  </div>
</template>

<script>
import { findPojo, findOne} from '@/api/operationLog'

import moment from 'moment'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'


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
        pageSize: 20,
        callTimeStart: undefined,
        callTimeEnd: undefined,
        model: undefined,
        type: undefined,
        userNo: undefined,
        userName: undefined,
        userIp: undefined
      },
      downloadLoading: false,
      models: [
      	{
        	id: '用户登录',
        	name: '用户登录'
        },
      	{
      		id: '罪犯管理',
      		name: '罪犯管理'
      	},
      	{
      		id: '亲属管理',
      		name: '亲属管理'
      	}
      ],
      types: [
		{
        	id: '正常',
        	name: '正常'
        },
      	{
      		id: '警告',
      		name: '警告'
      	},
      	{
      		id: '严重',
      		name: '严重'
      	}
	  ],
	  pickerOptionsStart: {
	      shortcuts: [{
	        text: '今天',
	        onClick(picker) {
	          picker.$emit('pick', new Date());
	        }
	      }, {
	        text: '昨天',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近一周',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近一个月',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近三个月',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 90);
	          picker.$emit('pick', date);
	        }
	      }, {
	        text: '最近一年',
	        onClick(picker) {
	          const date = new Date();
	          date.setTime(date.getTime() - 3600 * 1000 * 24 * 365);
	          picker.$emit('pick', date);
	        }
	      }]
	    },
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
  },
  mounted() {
  },
  methods: {
    getList() {
      this.listLoading = true
       if(!this.listQuery.callTimeStart){
      	this.listQuery.callTimeStart = undefined
      }else{
      	this.listQuery.callTimeStart = this.dateFormatYMD(this.listQuery.callTimeStart)+" 00:00:00";
      }
      if(!this.listQuery.callTimeEnd){
      	this.listQuery.callTimeEnd = undefined
      }else{
      	this.listQuery.callTimeEnd = this.dateFormatYMD(this.listQuery.callTimeEnd)+" 23:59:59";
      }
      if(!this.listQuery.model){
      	this.listQuery.model = undefined
      }
      if(!this.listQuery.type){
      	this.listQuery.type = undefined
      }
      if(!this.listQuery.userNo){
      	this.listQuery.userNo = undefined
      }
      if(!this.listQuery.userName){
      	this.listQuery.userName = undefined
      }
      if(!this.listQuery.userIp){
      	this.listQuery.userIp = undefined
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
    
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp']
        const filterVal = ['timestamp']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
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
	dateFormatYMD: function (val) {
		if(!val){
			return undefined
		}
		return moment(val).format("YYYY-MM-DD");
	},
  }
}
</script>
