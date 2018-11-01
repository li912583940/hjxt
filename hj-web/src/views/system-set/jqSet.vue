<!--
	作者：912583940@qq.com
	时间：2018-11-01
	描述： 服刑人员管理
-->
<template>
  <div class="app-container">
  	<div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="监区名称" v-model="listQuery.jqName" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">添加</el-button>
    </div>
    
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="200" align="center" label="服务器名称" >
        <template slot-scope="scope">
          <span>{{scope.row.jy}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="监区编号">
        <template slot-scope="scope">
          <span>{{scope.row.jqNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="监区名称">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="会见星期时间">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="特殊监区">
        <template slot-scope="scope">
          <span v-if="scope.row.isTs==0">否</span>
          <span v-if="scope.row.isTs==1">是</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('criminal.actions')" width="360">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button size="mini" type="info" icon="el-icon-setting" @click="openWeek(scope.row)">设置会见星期</el-button>
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
        <el-form-item label="服务器名称" prop="jy">
          <el-input v-model="dataForm.jy"></el-input>
        </el-form-item>
        <el-form-item label="监区编号" prop="jqNo">
          <el-input v-model="dataForm.jqNo"></el-input>
        </el-form-item>
        <el-form-item label="监区名称" prop="jqName">
          <el-input v-model="dataForm.jqName"></el-input>
        </el-form-item>
        <el-form-item label="特殊监区" prop="isTs">
          <el-input v-model="dataForm.isTs"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
    
    <!-- 设置会见星期 -->
	<el-dialog title="设置会见星期" :visible.sync="dialogWeekVisible">
		<el-card style="width: 62%; margin-left: 19%;">
			<el-transfer
		    v-model="weekValue"
		    :data="weekData"
		    :titles="['未拥有', '已拥有']">
		  </el-transfer>
	  </el-card>
	  <div slot="footer" class="dialog-footer">
	    <el-button @click="dialogWeekVisible = false">取 消</el-button>
	    <el-button type="primary" @click="updateWeekData">确 定</el-button>
	  </div>
	</el-dialog>
	
  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, findDeptNameList} from '@/api/jqSet'

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
        jbName: undefined,
        hjCount: undefined,
        hjTime: undefined,
        recordOverTime: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
       rules: {
        jqName: [{ required: true, message: '级别名称不能为空', trigger: 'blur' }]
      },
      dialogWeekVisible: false,
      weekValue: [],
      weekData: [
      	{label: '星期一',key:1},
      	{label: '星期二',key:2},
      	{label: '星期三',key:3},
      	{label: '星期四',key:4},
      	{label: '星期五',key:5},
      	{label: '星期六',key:6},
      	{label: '星期日',key:7}
      ]
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
    		this.dataForm.webId = res.data.webId,
	        this.dataForm.jbName =  res.data.jbName,
	        this.dataForm.hjCount = res.data.hjCount,
	        this.dataForm.hjTime = res.data.hjTime,
	        this.dataForm.recordOverTime = res.data.recordOverTime
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
	
	openWeek(row){
		this.dialogWeekVisible = true
	},
	updateWeekData(){
		
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
