<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
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
          <span v-if="scope.row.fpFlag ==0">未分配</span>
          <span else>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="会见通知 接收状态">
        <template slot-scope="scope">
          <span v-if="scope.row.pageTzState==0" @click="sdNotice">未接收</span>
          <span v-if="scope.row.pageTzState==1">已接收</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.hjType ==1">电话会见</span>
          <span v-else-if="scope.row.hjType ==2">面对面会见</span>
          <span v-else-if="scope.row.hjType ==3">视频会见</span>
          <span v-else-if="scope.row.hjType ==4">帮教</span>
          <span v-else-if="scope.row.hjType ==5">提审</span>
        </template>
      </el-table-column>
      <el-table-column width="300px" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="登记时间" :formatter="dateFormat">
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
          <span v-if="scope.row.fpFlag==2">已在会见</span>
          <span v-if="scope.row.fpFlag!=2">未在会见</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="会见通知 接收人">
        <template slot-scope="scope">
          <span>{{scope.row.pageTzUserName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="接收 时间" :formatter="dateFormat">
        <template slot-scope="scope">
          <span>{{scope.row.pageTzTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200px" align="center" label="接收通知 是否超时">
        <template slot-scope="scope">
          <span v-if="scope.row.isOverTime==0">未超时</span>
          <span v-if="scope.row.isOverTime==1">已超时</span>
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
import { findPojo } from '@/api/meetNotice'

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
  mounted() {
  	if(this.timer){
  		this.clearInterval(this.timer)
  	}else{
  		this.timer = setInterval(() =>{
  			this.getList()
  		}, 10000)
  	}
  },
  destroyed() {
  	clearInterval(this.timer)
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
