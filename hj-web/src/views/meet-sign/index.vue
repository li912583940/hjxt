<template>
  <div class="app-container">

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" :label="$t('criminal.actions')" width="200" fixed="left" >
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">自动分配</el-button>
          <!--<el-button type="primary" size="mini" @click="handleUpdate(scope.row)">取消分配</el-button>-->
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">人工分配</el-button>
        </template>
      </el-table-column>
      
      <el-table-column width="140" align="center" label="监区">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="分管等级">
        <template slot-scope="scope">
          <span>{{scope.row.jbName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="服刑人员姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="重点犯">
        <template slot-scope="scope">
          <span v-if="scope.row.stateZdzf=='否'">否</span>
          <span v-if="scope.row.stateZdzf!='否'">是</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.hjType ==1">电话会见</span>
          <span v-else-if="scope.row.hjType ==2">面对面会见</span>
          <span v-else-if="scope.row.hjType ==3">视频会见</span>
          <span v-else-if="scope.row.hjType ==4">帮教</span>
          <span v-else-if="scope.row.hjType ==5">提审</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="座位">
        <template slot-scope="scope">
          <span v-if="scope.row.fpFlag ==0">未分配</span>
          <span else>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      
      <el-table-column width="140" align="center" label="罪犯编号">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="亲属">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见时长">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}分钟</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="授权状态">
        <template slot-scope="scope">
          <span v-if="scope.row.shState=='1'">已授权</span>
          <span v-if="scope.row.shState=='0'">未授权</span>
        </template>
      </el-table-column>
      <el-table-column width="180" align="center" label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">授权</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">取消授权</el-button>
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
import { findPojo } from '@/api/meetSign'

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
      listLoading: true,
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
