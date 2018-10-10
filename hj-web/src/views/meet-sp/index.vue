<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="140" align="center" label="所属监区">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="审批申请人">
        <template slot-scope="scope">
          <span>{{scope.row.spTjUser}}[{{scope.row.spTjUserName}}]</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="申请时间">
        <template slot-scope="scope">
          <span>{{scope.row.spTjTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="审批部门">
        <template slot-scope="scope">
          <span>{{scope.row.spGroupName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="审批人">
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
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">查看详情</el-button>
          <el-button v-if="scope.row.spState==1" type="primary" size="mini" @click="handleDelete(scope.row)">我要审批</el-button>
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
import { findPojo } from '@/api/meetSp'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'


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
      }
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
