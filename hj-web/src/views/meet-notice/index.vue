<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 100%">
       <el-table-column  align="center" :label="$t('criminal.actions')" width="160" fixed="left" >
        <template slot-scope="scope">
        	<span v-if="scope.row.pageTzState==0"><el-button type="primary" size="mini" @click="sdNotice(scope.row)">接收</el-button></span>
	        <span v-if="scope.row.pageTzState==1"><el-button type="danger" size="mini" @click="sdNotice(scope.row)">取消接收</el-button></span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('currency.jqName')" width="90">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="会见窗口">
        <template slot-scope="scope">
          <span v-if="scope.row.fpFlag ==0">未分配</span>
          <span else>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100px" align="center" label="会见通知">
        <template slot-scope="scope">
          <span v-if="scope.row.pageTzState==0" style="color: red;">未接收</span>
          <span v-if="scope.row.pageTzState==1">已接收</span>
        </template>
      </el-table-column>
      <el-table-column width="110px" align="center" label="会见类型">
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
      <el-table-column width="90" align="center" label="会见时长">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}分钟</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="亲属信息">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="会见状态">
        <template slot-scope="scope">
          <span v-if="scope.row.fpFlag==2">已在会见</span>
          <span v-if="scope.row.fpFlag!=2">未在会见</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="接收人">
        <template slot-scope="scope">
          <span>{{scope.row.pageTzUserName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="接收时间">
        <template slot-scope="scope">
          <span>{{scope.row.pageTzTime| dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="通知超时">
        <template slot-scope="scope">
          <span v-if="scope.row.isOverTime==0">未超时</span>
          <span v-if="scope.row.isOverTime==1" style="color: red;">已超时</span>
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
import { findPojo, RequestEditTz } from '@/api/meetNotice'

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
      }
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
		sdNotice(row){
			let tz=1
			if(row.pageTzState==1){
				tz=0
			}
			let param = {
    		hjid: row.hjid,
				pageTzState: tz
    	}
			RequestEditTz(param).then((res) =>{
				this.getList()
			})
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
