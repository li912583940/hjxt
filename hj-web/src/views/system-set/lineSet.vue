<!--
	作者：912583940@qq.com
	时间：2018-11-01
	描述： 线路设置
-->
<template>
  <div class="app-container">
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 1001px">
      <el-table-column width="100" align="center" label="线路编号">
        <template slot-scope="scope">
          <span>{{scope.row.lineNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="线路状态">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" style="color: red;">关闭</span>
          <span v-if="scope.row.state==1">开启</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="线路模式">
        <template slot-scope="scope">
          <span v-if="scope.row.model==0">正常</span>
          <span v-if="scope.row.model==1" style="color: red;">特殊</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="服务器名称">
        <template slot-scope="scope">
          <span>{{scope.row.jy}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="座位名称">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="读卡器">
        <template slot-scope="scope">
          <span>{{scope.row.dkq}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="IC卡号">
        <template slot-scope="scope">
          <span>{{scope.row.cardNo}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('criminal.actions')" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">配置</el-button>
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
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="180px" style='width: 400px; margin-left:25%;' >
        <el-form-item label="线路编号">
          <el-input v-model="dataForm.lineNo" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="线路逻辑号">
          <el-input v-model="dataForm.line" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="服务器名称">
          <el-input v-model="dataForm.jy" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="座位名称">
          <el-input v-model="dataForm.zw" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="读卡器">
          <el-input v-model="dataForm.dkq"></el-input>
        </el-form-item>
        <el-form-item label="线路状态">
          <el-radio-group v-model="dataForm.state">
		    <el-radio :label="0">关闭</el-radio>
		    <el-radio :label="1">开启</el-radio>
		  </el-radio-group>
        </el-form-item>
        <el-form-item label="线路模式">
          <el-radio-group v-model="dataForm.model">
		    <el-radio :label="0">正常</el-radio>
		    <el-radio :label="1">特殊</el-radio>
		  </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
    
	
  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, findDeptNameList} from '@/api/lineSet'

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
      	jqName: undefined,
        pageNum: 1,
        pageSize: 20
      },
      // 新增或编辑弹窗
      dataForm: { 
        webId: undefined,
        dkq: undefined,
        state: 1,
        model: 0
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '配 置',
        create: '新 增'
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
    	if(!this.listQuery.jqName){
      	this.listQuery.jqName = undefined
      }
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
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
		this.dataForm.webId = undefined
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
    		id: row.webId
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.webId = res.data.webId
	        this.dataForm.lineNo =  res.data.lineNo
	        this.dataForm.line = res.data.line
	        this.dataForm.jy = res.data.jy
	        this.dataForm.zw = res.data.zw
	        this.dataForm.dkq = res.data.dkq
	        this.dataForm.state = res.data.state
	        this.dataForm.model = res.data.model
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
    			id: row.webId
    		}
			RequestDelete(param).then(() => {
    		this.getList()
    	}).catch(error => {
	        this.dialogFormVisible = false
	      })
		})
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
