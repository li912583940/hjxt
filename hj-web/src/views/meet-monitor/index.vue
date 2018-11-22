<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row max-height="700"
      style="width: 100%">
      <el-table-column width="140px" align="center" label="服务器名称">
        <template slot-scope="scope">
          <span>{{scope.row.jy}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="通道">
        <template slot-scope="scope">
          <span>{{scope.row.lineNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="座位号">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="状态">
        <template slot-scope="scope">
          <span v-if="scope.row.monitorState =='通话'">通话</span>
          <span v-if="scope.row.monitorState =='空闲'">空闲</span>
          <span v-if="scope.row.monitorState =='应答'">应答</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="罪犯监区">
        <template slot-scope="scope">
          <span>{{scope.row.monitorJq}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.monitorFr}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300px" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.monitorQs}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="剩余时间">
        <template slot-scope="scope">
          <span>{{scope.row.monitorTime}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.editPermission==1 || buttonRole.deletePermission==1" align="center" :label="$t('criminal.actions')" width="180">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.editPermission==1" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">监听</el-button>
          <el-button v-if="buttonRole.deletePermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">停止监听</el-button>
          <el-button v-if="buttonRole.deletePermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">切断</el-button>
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
import { findPojo } from '@/api/meetMonitor'

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
