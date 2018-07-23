<!-- 会见登记 -->
<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" :placeholder="$t('criminal.title')" v-model="frListQuery.title">
      </el-input>
      <el-select clearable style="width: 90px" class="filter-item" v-model="frListQuery.importance" :placeholder="$t('criminal.importance')">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item">
        </el-option>
      </el-select>
      <el-select clearable class="filter-item" style="width: 130px" v-model="frListQuery.type" :placeholder="$t('criminal.type')">
        <el-option v-for="item in  calendarTypeOptions" :key="item.key" :label="item.display_name+'('+item.key+')'" :value="item.key">
        </el-option>
      </el-select>
      <el-select @change='handleFilter' style="width: 140px" class="filter-item" v-model="frListQuery.sort">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-checkbox class="filter-item" style='margin-left:15px;' @change='tableKey=tableKey+1' v-model="showReviewer">{{$t('criminal.reviewer')}}</el-checkbox>
    </div>

		<!-- 服刑人员开始 -->
		<el-card class="box-card">
	    <el-table :key='frTableKey' ref="frMultipleTable" :data="frList" v-loading="frListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	       @row-click="frRowClick" @select="frSelectionChang" @select-all="frAllSelectionChang" style="width: 100%">
	      <el-table-column align="center" :label="$t('currency.select')" type="selection" width="65">
	      </el-table-column>
	      <el-table-column align="center" :label="$t('criminal.id')" width="65">
	        <template slot-scope="scope">
	          <span>{{scope.row.id}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('currency.number')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160px" align="center" :label="$t('currency.fullName')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.sex')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.age')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.prisonArea')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160" align="center" :label="$t('criminal.nativePlace')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" :label="$t('criminal.entryTime')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.numberOfRelatives')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	
	      <el-table-column align="center" :label="$t('criminal.actions')" width="230" class-name="small-padding fixed-width">
	        <template slot-scope="scope">
	          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('criminal.edit')}}</el-button>
	          <el-button  size="mini" type="danger" @click="handleModifyStatus(scope.row,'deleted')">{{$t('criminal.delete')}}
	          </el-button>
	        </template>
	      </el-table-column>
	    </el-table>
	
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="frListQuery.page" :page-sizes="[5,10]" :page-size="frListQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="frTotal">
	      </el-pagination>
	    </div>
	  </el-card>
		<!-- 服刑人员结束 -->
	
		<!-- 亲属开始 -->
		<el-card class="box-card">
	    <el-table :key='qsTableKey' ref="qsMultipleTable" :data="qsList" v-loading="qsListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	      @selection-change="qsAllSelectionChange" @row-click="qsRowClick" style="width: 100%">
	      <el-table-column align="center" type="selection" :label="$t('currency.select')" width="65">
	      </el-table-column>
	      <el-table-column align="center" :label="$t('criminal.id')" width="65">
	        <template slot-scope="scope">
	          <span>{{scope.row.id}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('currency.number')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160px" align="center" :label="$t('currency.fullName')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.sex')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.age')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.prisonArea')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160" align="center" :label="$t('criminal.nativePlace')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" :label="$t('criminal.entryTime')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" :label="$t('criminal.numberOfRelatives')">
	        <template slot-scope="scope">
	          <span>{{scope.row.author}}</span>
	        </template>
	      </el-table-column>
	
	      <el-table-column align="center" :label="$t('criminal.actions')" width="230" class-name="small-padding fixed-width">
	        <template slot-scope="scope">
	          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('criminal.edit')}}</el-button>
	          <el-button  size="mini" type="danger" @click="handleModifyStatus(scope.row,'deleted')">{{$t('criminal.delete')}}
	          </el-button>
	        </template>
	      </el-table-column>
	    </el-table>
	
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="qsListQuery.page" :page-sizes="[5,10]" :page-size="qsListQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="qsTotal">
	      </el-pagination>
	    </div>
	  </el-card>
    <!-- 亲属结束 -->
  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj ,such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

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
        page: 1,
        limit: 5,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      
      qsTableKey: 1,
      qsList: null,
      qsTotal: null,
      qsListLoading: true,
      qsListQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      
      formdata: {
      	frSelections: [],
      	qsSelections: [],
      },
      
      
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [{ label: 'ID升序', key: '+id' }, { label: 'ID降序', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
     
      rules: {
        name: [{ required: true, message: this.$t('criminal.name'), trigger: 'blur' }],
        sex: [{ required: true, message: this.$t('criminal.sex'), trigger: 'change' }],
        entryTime: [{ type: 'date', required: true, message: this.$t('criminal.entryTime'), trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      }
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  created() {
   
  },
  mounted(){
      this.getFrList();
      this.getQsFrList();
  },
  methods: {
    getFrList() {
      this.frListLoading = true
      fetchList(this.frListQuery).then(response => {
        this.frList = response.data.items
        this.frTotal = response.data.total
        this.frListLoading = false
      })
    },
    getQsFrList() {
      this.qsListLoading = true
      fetchList(this.qsListQuery).then(response => {
        this.qsList = response.data.items
        this.qsTotal = response.data.total
        this.qsListLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
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