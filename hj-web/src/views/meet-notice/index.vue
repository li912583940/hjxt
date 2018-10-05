<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" label="罪犯监区" width="140">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="罪犯编号">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="会见窗口">
        <template slot-scope="scope">
          <span v-if="scope.row.fpFlag !=0">未分配</span>
          <span else>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="会见通知 接收状态">
        <template slot-scope="scope">
          <span v-if="cope.row.pageTzState==0" @click="sdNotice">未接收</span>
          <span else>已接收</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="会见类型">
        <template slot-scope="scope">
          <span>{{scope.row.hjType}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300px" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="会见状态">
        <template slot-scope="scope">
          <span>{{scope.row.fpFlag}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="会见通知 接收人">
        <template slot-scope="scope">
          <span>{{scope.row.pageTzUserName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="接收 时间">
        <template slot-scope="scope">
          <span>{{scope.row.pageTzTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="接收通知 是否超时">
        <template slot-scope="scope">
          <span>{{scope.row.isOverTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('criminal.actions')" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button  size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
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
import { findPojo } from '@/api/relatives'

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
