<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入罪犯编号" v-model="listQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入罪犯姓名" v-model="listQuery.frName" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属姓名" v-model="listQuery.qsName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.jqNo" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.state" placeholder="选择会见状态">
        <el-option v-for="item in states" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.hjType" placeholder="选择会见类型">
        <el-option v-for="item in hjTypes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.hjMode" placeholder="选择会见类型">
        <el-option v-for="item in hjModes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="100" align="center"  :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center"  :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="400" align="center" label="亲属">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="会见编号">
        <template slot-scope="scope">
          <span>{{scope.row.hjIndex}}</span>
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
      <el-table-column width="140" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="物品">
        <template slot-scope="scope">
          <span>{{scope.row.infoWp}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见时长(分钟)">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}</span>
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
import { findPojo, findOne, findJqList, exportExcel} from '@/api/registerLog'

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
        qsName: undefined,
        state: undefined,
        hjType: undefined,
        jqNo: undefined,
        hjMode: undefined,
      },
      downloadLoading: false,
      
      states:[
        {
      		id: 0,
      		name: '未完成会见'
      	},
      	{
      		id: 1,
      		name: '已完成会见'
      	},
      	{
      		id: 2,
      		name: '已取消会见 '
      	}
      ],
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
      jqs: [ // 监区下拉选框
      
      ],
      hjModes:[
      {
      		id: 1,
      		name: '隔离会见'
      	},
      	{
      		id: 2,
      		name: '非隔离会见'
      	},
      	{
      		id: 3,
      		name: '远程视频会见'
      	},
      	{
      		id: 9,
      		name: '其他方式'
      	},
      ],
      
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
      if(this.listQuery.frName== undefined || this.listQuery.frName== ''){
      	this.listQuery.frName = undefined
      }
      if(this.listQuery.frNo== undefined || this.listQuery.frNo==''){
      	this.listQuery.frNo = undefined
      }
      if(this.listQuery.qsName== undefined || this.listQuery.qsName== ''){
      	this.listQuery.qsName = undefined
      }
      if(this.listQuery.state==undefined || this.listQuery.state==''){
      	this.listQuery.state = undefined
      }
      if(this.listQuery.hjType==undefined || this.listQuery.hjType==''){
      	this.listQuery.hjType = undefined
      }
      if(this.listQuery.jqNo==undefined || this.listQuery.jqNo==''){
      	this.listQuery.jqNo = undefined
      }
      if(this.listQuery.hjMode==undefined || this.listQuery.hjMode==''){
      	this.listQuery.hjMode = undefined
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
      if(this.listQuery.frNo== undefined || this.listQuery.frNo==''){
      	this.listQuery.frNo = undefined
      }
      if(this.listQuery.qsName== undefined || this.listQuery.qsName== ''){
      	this.listQuery.qsName = undefined
      }
      if(this.listQuery.state==undefined || this.listQuery.state==''){
      	this.listQuery.state = undefined
      }
      if(this.listQuery.hjType==undefined || this.listQuery.hjType==''){
      	this.listQuery.hjType = undefined
      }
      if(this.listQuery.jqNo==undefined || this.listQuery.jqNo==''){
      	this.listQuery.jqNo = undefined
      }
      if(this.listQuery.hjMode==undefined || this.listQuery.hjMode==''){
      	this.listQuery.hjMode = undefined
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
  }
}
</script>
