<!--
	作者：912583940@qq.com
	时间：2018-11-01
	描述： 罪犯级别
-->
<template>
  <div class="app-container">
  	<div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="级别名称" v-model="listQuery.jbName" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('criminal.add')}}</el-button>
    </div>
    
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 1201px">
      <el-table-column width="200" align="center" label="级别编号" >
        <template slot-scope="scope">
          <span>{{scope.row.jbNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="级别名称" >
        <template slot-scope="scope">
          <span>{{scope.row.jbName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="每月会见次数">
        <template slot-scope="scope">
          <span>{{scope.row.hjCount}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="每次会见时长（分钟）">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="复听录音超时时间（天）">
        <template slot-scope="scope">
          <span>{{scope.row.recordOverTime}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.editPermission==1 || buttonRole.deletePermission==1" align="center" :label="$t('criminal.actions')" width="200">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.editPermission==1" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="buttonRole.deletePermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

	<!-- 新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="650px" :modal-append-to-body="false">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="180px" style='width: 400px; margin-left:10%;' >
         <el-form-item label="级别编号" prop="jbNo">
          <el-input v-if="dialogStatus=='update'" v-model="dataForm.jbNo" :disabled="true"></el-input>
          <el-input v-if="dialogStatus=='create'" v-model="dataForm.jbNo"></el-input>
        </el-form-item>
        <el-form-item label="级别名称" prop="jbName">
          <el-input v-model="dataForm.jbName"></el-input>
        </el-form-item>
        <el-form-item label="每月会见次数" prop="hjCount">
          <el-input v-model="dataForm.hjCount"></el-input>
        </el-form-item>
        <el-form-item label="每次会见时长（分钟）" prop="hjTime">
          <el-input v-model="dataForm.hjTime"></el-input>
        </el-form-item>
        <el-form-item label="复听录音超时时间（天）" prop="recordOverTime">
          <el-input v-model="dataForm.recordOverTime"></el-input>
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
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete} from '@/api/criminalLevel'

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
      	jbName: undefined,
        pageNum: 1,
        pageSize: 10
      },
      // 新增或编辑弹窗
      dataForm: { 
        webid: undefined,
        jbNo: undefined,
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
       	jbNo: [{ required: true, message: '级别编号不能为空', trigger: 'blur' }],
        jbName: [{ required: true, message: '级别名称不能为空', trigger: 'blur' }]
      },
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	editPermission: 0,
      	deletePermission: 0,
      },
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
  },
  mounted() {
    this.setButtonRole()
  },
  methods: {
    getList() {
    	if(!this.listQuery.jbName){
      	this.listQuery.jbName = undefined
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
		this.dataForm.webid = undefined
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
    		id: row.webid
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.webid = res.data.webid,
    		this.dataForm.jbNo = res.data.jbNo,
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
    			id: row.webid
    		}
			RequestDelete(param).then(() => {
    		this.getList()
    	}).catch(error => {
	        this.dialogFormVisible = false
	      })
		})
	},
	setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.editPermission= 1
    		this.buttonRole.deletePermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let criminalLevel = buttonRoles.criminalLevel
    		if(criminalLevel.length>0){
    			for(let value of criminalLevel){
    				if(value=='addPermission'){
    					this.buttonRole.addPermission= 1
    				}else if(value=='editPermission'){
    					this.buttonRole.editPermission= 1
    				}else if(value=='deletePermission'){
    					this.buttonRole.deletePermission= 1
    				}
    			}
    		}
    	}
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
