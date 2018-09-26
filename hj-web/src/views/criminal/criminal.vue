<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入编号" v-model="listQuery.frNo">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入姓名" v-model="listQuery.frName">
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.jq" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('criminal.add')}}</el-button>
      <el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>
      <el-checkbox class="filter-item" style='margin-left:15px;' @change='tableKey=tableKey+1' v-model="showReviewer">{{$t('criminal.reviewer')}}</el-checkbox>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" :label="$t('criminal.id')" width="80">
        <template slot-scope="scope">
          <span>{{scope.row.webId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" :label="$t('currency.number')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" :label="$t('criminal.name')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" :label="$t('criminal.prisonArea')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="级别">
        <template slot-scope="scope">
          <span>{{scope.row.jbName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="当月会见次数">
        <template slot-scope="scope">
          <span>{{scope.row.hjUse}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="当月剩余次数">
        <template slot-scope="scope">
          <span>{{scope.row.hjLeft}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="重点罪犯">
        <template slot-scope="scope">
          <span>{{scope.row.infoZdzf}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160px" align="center" label="入监时间" :formatter="dateFormat">
        <template slot-scope="scope">
          <span>{{scope.row.infoRjsj}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140px" align="center" label="会见级别">
        <template slot-scope="scope">
          <span>{{scope.row.hjJb}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" :label="$t('criminal.actions')" width="" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{$t('criminal.edit')}}</el-button>
          <el-button  size="mini" type="danger" @click="handleDelete(scope.row)">{{$t('criminal.delete')}}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="80px" style='width: 400px; margin-left:25%;' >
        <el-form-item label="编号" prop="frNo">
          <el-input v-if="dialogStatus=='create'" v-model="dataForm.frNo"></el-input>
          <el-input v-else v-model="dataForm.frNo" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="frName">
          <el-input v-model="dataForm.frName"></el-input>
        </el-form-item>
        <el-form-item label="IC卡号" prop="frCard">
          <el-input v-model="dataForm.frCard"></el-input>
        </el-form-item>
        <el-form-item label="监区" prop="jq">
          <el-select class="filter-item" v-model="dataForm.jq" placeholder="请选择">
            <el-option v-for="item in  jqs" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="犯人级别" prop="jbNo">
          <el-input v-model="dataForm.jbNo"></el-input>
        </el-form-item>
        <el-form-item label="会见级别" prop="hjJb">
          <el-select class="filter-item" v-model="dataForm.hjJb" placeholder="请选择">
            <el-option v-for="item in  hjJbs" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="入监时间" prop="infoRjsj">
          <el-date-picker v-model="dataForm.infoRjsj" type="datetime" placeholder="请选取时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="罪名" prop="infoZm">
          <el-input v-model="dataForm.infoZm"></el-input>
        </el-form-item>
        <el-form-item label="刑期" prop="infoXq">
          <el-input v-model="dataForm.infoXq"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" prop="infoCsrq">
          <el-date-picker v-model="dataForm.infoCsrq" type="datetime" placeholder="请选取时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="住址" prop="infoHome">
          <el-input v-model="dataForm.infoHome"></el-input>
        </el-form-item>
        <el-form-item label="重点监控" >
        	<el-radio-group v-model="dataForm.monitorFlag">
				    <el-radio :label="'0'">否</el-radio>
				    <el-radio :label="'1'">是</el-radio>
				  </el-radio-group>
        </el-form-item>
        <el-form-item label="重点罪犯" >
        	<el-radio-group v-model="dataForm.stateZdzf">
				    <el-radio :label="0">否</el-radio>
				    <el-radio :label="1">是</el-radio>
				  </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="zdzfType">
          <el-input v-model="dataForm.zdzfType"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Reading statistics" :visible.sync="dialogPvVisible">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel"> </el-table-column>
        <el-table-column prop="pv" label="Pv"> </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{$t('criminal.confirm')}}</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete } from '@/api/criminal'

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
        pageSize: 20,
        frNo: undefined,
        frName: undefined,
        jq: undefined
      },
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      // 新增或编辑弹窗
      dataForm: { 
        webId: undefined,
        frName: undefined,
        frNo: '',
        frCard: undefined,
        jy: '测试监狱',
        jq: '-1',
        jbNo: undefined,
        hjJb: 1,
        infoRjsj: undefined,
        infoZm: undefined,
        infoXq: undefined,
        infoCsrq: undefined,
        infoHome: undefined,
        monitorFlag: '0',
        stateZdzf: 1
      },
      jqs: [ // 监区下拉选框
      	{
      		id: '-1',
      		name: '未分配监区'
      	}
      ],
      hjJbs: [
        {
        	id: 1,
        	name: '正常'
        },
      	{
      		id: -1,
      		name: '禁止'
      	}
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
      	frNo: [{ required: true, message: '编号不能为空', trigger: 'blur'}],
        frName: [{ required: true, message: this.$t('criminal.name'), trigger: 'blur' }],
        jbNo: [{ required: true, message: '监区不能为空', trigger: 'change' }],
        infoRjsj: [{  required: true, message: '请选择入监时间', trigger: 'change' }]
      },
      downloadLoading: false
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
      if(!this.listQuery.frName){
      	this.listQuery.frName = undefined
      }
      if(!this.listQuery.frNo){
      	this.listQuery.frNo = undefined
      }
      if(!this.listQuery.jq){
      	this.listQuery.jq = undefined
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
    //重置表单
		resetForm(formName) {
			if(this.$refs[formName] !== undefined){
				this.$refs[formName].resetFields();
			}
	  },
    handleCreate() {
      this.dialogStatus = 'create'
      this.resetForm('dataForm')
      this.dialogFormVisible = true
//    this.$nextTick(() => {
//      this.$refs['dataForm'].clearValidate()
//    })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
        	if(this.dataForm.infoRjsj){
        		this.dataForm.infoRjsj = this.dateFormats(this.dataForm.infoRjsj);
        	}
        	if(this.dataForm.infoCsrq){
        		this.dataForm.infoCsrq = this.dateFormats(this.dataForm.infoCsrq);
        	}
          RequestAdd(this.dataForm).then(() => {
            this.dialogFormVisible = false
            this.getList()
          }).catch(error => {
		        this.dialogFormVisible = false
		      })
        }
      })
    },
    handleUpdate(row) {
    	let param = {
    		webId: row.webId
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.webId = res.data.webId,
        this.dataForm.frName =  res.data.frName,
        this.dataForm.frNo = res.data.frNo,
        this.dataForm.frCard = res.data.frCard,
        this.dataForm.jy = res.data.jy,
        this.dataForm.jq = res.data.jq,
        this.dataForm.jbNo = res.data.jbNo,
        this.dataForm.hjJb = res.data.hjJb,
        this.dataForm.infoRjsj = res.data.infoRjsj,
        this.dataForm.infoZm = res.data.infoZm,
        this.dataForm.infoXq = res.data.infoXq,
        this.dataForm.infoCsrq = res.data.infoCsrq,
        this.dataForm.infoHome = res.data.infoHome,
        this.dataForm.monitorFlag = res.data.monitorFlag,
        this.dataForm.stateZdzf = res.data.stateZdzf
    	})
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
        	if(this.dataForm.infoRjsj){
        		this.dataForm.infoRjsj = this.dateFormats(this.dataForm.infoRjsj);
        	}
        	if(this.dataForm.infoCsrq){
        		this.dataForm.infoCsrq = this.dateFormats(this.dataForm.infoCsrq);
        	}
          RequestEdit(this.dataForm).then(() => {
            this.dialogFormVisible = false
            this.getList()
          }).catch(error => {
		        this.dialogFormVisible = false
		      })
        }
      })
    },
    //删除
		handleDelete(row) {
			this.$confirm('确认删除该记录吗?', '提示', {
				type: 'warning'
			}).then(() => {
				this.listLoading = true;
				let param = {
	    		webId: row.webId
	    	}
				RequestDelete(param).then(() => {
	    		this.getList()
	    	}).catch(error => {
	        this.dialogFormVisible = false
	      })
			})
		},
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
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
  }
}
</script>
