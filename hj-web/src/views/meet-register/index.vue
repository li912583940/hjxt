<!-- 会见登记 -->
<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="frListQuery.frNo">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="frListQuery.frName">
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="frListQuery.jq" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
    </div>

		<!-- 服刑人员开始 -->
		<el-card class="box-card">
	    <el-table :key='frTableKey' ref="frMultipleTable" :data="frList" v-loading="frListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	       @row-click="frRowClick" @row-dblclick="handleSearchQs" @select="frSelectionChang" @select-all="frAllSelectionChang" style="width: 100%">
	      <el-table-column align="center" type="selection" width="70" fixed="left">
	      </el-table-column>
	      <el-table-column align="center" label="监区" width="70">
	        <template slot-scope="scope">
	          <span>{{scope.row.jqName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="罪犯编号">
	        <template slot-scope="scope">
	          <span>{{scope.row.frNo}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160px" align="center" label="罪犯姓名">
	        <template slot-scope="scope">
	          <span>{{scope.row.frName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="本月已见次数">
	        <template slot-scope="scope">
	          <span>{{scope.row.hjUse}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="上次会见时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.hjLastTime}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="上次会见家属信息">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160" align="center" label="入监时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoRjsj}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="分管等级">
	        <template slot-scope="scope">
	          <span>{{scope.row.jbName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="刑期">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoXq}}</span>
	        </template>
	      </el-table-column>
				<el-table-column width="150px" align="center" label="罪名">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoZm}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="家庭住址">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoHome}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="上次会见所在监区">
	        <template slot-scope="scope">
	          <span>{{scope.row.formerJQName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="重点罪犯">
	        <template slot-scope="scope">
	          <span v-if="scope.row.stateZdzf=='0'">否</span>
	          <span v-if="scope.row.stateZdzf=='1'">是</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="备注">
	        <template slot-scope="scope">
	          <span>{{scope.row.zdzfType}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="国籍">
	        <template slot-scope="scope">
	          <span>{{scope.row.frGj}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="是否禁止/禁止时间">
	        <template slot-scope="scope">
	          <span v-if="scope.row.hjJb=='-1'">是/{{scope.row.hjStopTime}}</span>
	          <span v-if="scope.row.hjJb!='-1'">否</span>
	        </template>
	      </el-table-column>
	      <el-table-column align="center" :label="$t('criminal.actions')" width="150" class-name="small-padding fixed-width" fixed="right">
	        <template slot-scope="scope">
	          <el-button type="primary" size="mini" @click="handleOpenQs(scope.row)">亲属</el-button>
	          </el-button>
	        </template>
	      </el-table-column>
	    </el-table>
	
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleFrSizeChange" @current-change="handleFrCurrentChange" :current-page="frListQuery.pageNum" :page-sizes="[5,10]" :page-size="frListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="frTotal">
	      </el-pagination>
	    </div>
	  </el-card>
		<!-- 服刑人员结束 -->
	
		<!-- 亲属开始 -->
		<el-card class="box-card">
	    <el-table :key='qsTableKey' ref="qsMultipleTable" :data="qsList" v-loading="qsListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	      @selection-change="qsAllSelectionChange" @row-click="qsRowClick" style="width: 100%">
	      <el-table-column align="center" type="selection"  width="70" fixed="left">
	      </el-table-column>
	      <el-table-column align="center" label="亲属姓名" width="100">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="关系">
	        <template slot-scope="scope">
	          <span>{{scope.row.gx}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="证件类别">
	        <template slot-scope="scope">
	          <span v-if="scope.row.qsZjlb==1">身份证</span>
	          <span v-if="scope.row.qsZjlb==2">警官证</span>
	          <span v-if="scope.row.qsZjlb==3">工作证</span>
	          <span v-if="scope.row.qsZjlb==4">其他</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="证件号码">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsSfz}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="证件物理号">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsSfzWlh}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="IC卡编号">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsCard}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160" align="center" label="性别">
	        <template slot-scope="scope">
	          <span>{{scope.row.xb}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="地址">
	        <template slot-scope="scope">
	          <span>{{scope.row.dz}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="电话">
	        <template slot-scope="scope">
	          <span>{{scope.row.tele}}</span>
	        </template>
	      </el-table-column>
				<el-table-column width="127px" align="center" label="备注">
	        <template slot-scope="scope">
	          <span>{{scope.row.bz}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="审批状态">
	        <template slot-scope="scope">
	          <span v-if="scope.row.spState==1">已通过</span>
	          <span v-if="scope.row.spState==0">未通过</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="140px" align="center" label="是否禁止/禁止时间">
	        <template slot-scope="scope">
	          <span v-if="scope.row.hjStopTime!=null">是/{{scope.row.hjStopTime}}</span>
	          <span v-if="scope.row.hjStopTime==null">否/{{scope.row.hjStopTime}}</span>
	        </template>
	      </el-table-column>
	      
	    </el-table>
	
	    <!--<div class="pagination-container">
	      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="qsListQuery.pageNum" :page-sizes="[5,10]" :page-size="qsListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="qsTotal">
	      </el-pagination>
	    </div>-->
	  </el-card>
    <!-- 亲属结束 -->
  </div>
</template>

<script>
import { findFrPojo, findQsPojo, findJqList } from '@/api/meetRegister'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'


export default {
  name: 'criminal',
  directives: {
    waves
  },
  data() {
    return {
      frTableKey: 0,
      frList: null,
      frTotal: null,
      frListLoading: true,
      frListQuery: {
        pageNum: 1,
        pageSize: 5,
        frNo: undefined,
        frName: undefined,
        jq: undefined
      },
      
      qsTableKey: 1,
      qsList: null,
      qsTotal: null,
      qsListLoading: false,
      qsListQuery: {
        pageNum: 1,
        pageSize: 10,
        frNo: undefined
      },
      
      jqs: [ // 监区下拉选框
      
      ],
      
      formdata: {
      	frSelections: [],
      	qsSelections: [],
      }
    }
  },
  filters: {

  },
  created() {
   
  },
  mounted() {
      this.getFrList()
      this.getJqList()
  },
  methods: {
    getFrList() {
      this.frListLoading = true
      if(!this.frListQuery.frName){
      	this.frListQuery.frName = undefined
      }
      if(!this.frListQuery.frNo){
      	this.frListQuery.frNo = undefined
      }
      if(!this.frListQuery.jq){
      	this.frListQuery.jq = undefined
      }
      findFrPojo(this.frListQuery).then((res) => {
      	 this.frList = res.pojo.list
      	 this.frTotal = res.pojo.count
      	 this.frListLoading = false
      }).catch(error => {
          this.frListLoading = false
      })
    },
    getQsFrList() {
      this.qsListLoading = true
      if(!this.qsListQuery.frNo){
      	this.qsListQuery.frNo = undefined
      }
      findQsPojo(this.qsListQuery).then((res) => {
      	 this.qsList = res.pojo.list
      	 this.qsTotal = res.pojo.count
      	 this.qsListLoading = false
      }).catch(error => {
         this.qsListLoading = false
      })
    },
    handleFilter() { // 罪犯查询
      this.frListQuery.page = 1
      this.getFrList()
    },
    handleFrSizeChange(val) { // 罪犯分页
      this.frListQuery.limit = val
      this.getFrList()
    },
    handleFrCurrentChange(val) { // 罪犯分页
      this.frListQuery.page = val
      this.getFrList()
    },
    handleQsSizeChange(val) { // 亲属分页
      this.qsListQuery.limit = val
      this.getQsList()
    },
    handleQsCurrentChange(val) { // 亲属分页
      this.qsListQuery.page = val
      this.getQsList()
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
    handleSearchQs(row) { //双击罪犯表格查询家属
    	this.qsListQuery.frNo = row.frNo
    	this.getQsFrList()
    },
    // 罪犯与家属多选框事件
    frSelectionChang(rows,row){
    	this.$refs.frMultipleTable.clearSelection();
    	this.$refs.frMultipleTable.toggleRowSelection(row);
    },
    frAllSelectionChang(){
    	this.$refs.frMultipleTable.clearSelection();
    },
    frRowClick(row){
    	this.$refs.frMultipleTable.clearSelection();
    	this.$refs.frMultipleTable.toggleRowSelection(row);
    },
    qsRowClick(row){ // 单击亲属表格行 多选框选中事件
      this.$refs.qsMultipleTable.toggleRowSelection(row);
    },
  	qsAllSelectionChange(rows){ // 亲属表格 全选事件
  		this.formdata.qsSelections = rows;
  	},
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

<style>
.box-card {
  margin: 10px;
  }
</style>