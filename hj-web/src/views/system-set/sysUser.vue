<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入姓名" v-model="listQuery.userName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.deptId" placeholder="选择部门">
        <el-option v-for="item in depts" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">{{$t('criminal.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 981px">
      <el-table-column width="80" align="center"  :label="$t('criminal.id')">
        <template slot-scope="scope">
          <span>{{scope.row.webid}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="登陆账号">
        <template slot-scope="scope">
          <span>{{scope.row.userNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="用户姓名">
        <template slot-scope="scope">
          <span>{{scope.row.userName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="部门">
        <template slot-scope="scope">
          <span>{{scope.row.deptName}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('criminal.actions')" width="300">
        <template slot-scope="scope">
        	<span v-if="scope.row.isSuper==1">超级管理员不能更改</span>
          <el-button v-if="scope.row.isSuper==0" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="scope.row.isSuper==0" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button v-if="scope.row.isSuper==0" size="mini" type="info" icon="el-icon-setting" @click="openRole(scope.row)">添加角色</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="600px">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
        <el-form-item label="登陆账号" prop="userNo">
          <el-input v-model="dataForm.userNo"></el-input>
        </el-form-item>
        <el-form-item label="用户姓名" prop="userName">
          <el-input v-model="dataForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="部门" >
          <el-select class="filter-item" v-model="dataForm.deptId" placeholder="请选择部门">
            <el-option v-for="item in depts" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>

		 <!-- 添加角色 -->
		<el-dialog title="添加角色" :visible.sync="dialogRoleVisible" width="740px">
			<el-card style="width: 540px; margin-left: 10%;">
				<el-transfer
			    filterable
			    :filter-method="roleFilter"
			    filter-placeholder="请输入关键字搜索"
			    v-model="roleValue"
			    :data="roleData"
			    :titles="['未拥有角色', '拥有角色']">
			  </el-transfer>
		  </el-card>
		  <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRoleVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRoleData">确 定</el-button>
      </div>
		</el-dialog>

  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, FindUserDepartList, FindRoleList, GetCheckedRole, AddUserRole, GetDeptList } from '@/api/sysUser'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'relatives',
  directives: {
    waves
  },
  data() {
    return {
    	/**------------用户增删改查开始-1-----------*/
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        userName: undefined,
        deptId: undefined
      },
   		depts: [], // 部门下拉选框
      
      // 新增或编辑弹窗
      dataForm: { 
        webid: undefined,
        userNo: undefined,
        userName: undefined,
        deptId: undefined
      },
     
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
      
      rules: {
        userNo: [{ required: true, message: '登陆账号不能为空', trigger: 'blur' }],
        userName: [{ required: true, message: '用户姓名不能为空', trigger: 'blur' }]
      },
      
      /**------------用户增删改查结束-1-----------*/
     
      
      /**------------添加角色开始-2-----------*/
      userId: undefined, //用户的webid
		  dialogRoleVisible: false, // 添加用户弹框
		  roleData: [],
		  roleValue: [],
		  /**------------添加角色结束-2-----------*/
   		
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
    this.getDeptList()
  },
  methods: {
  	/**------------用户增删改查开始-1-----------*/
    getList() {
      this.listLoading = true
      if(!this.listQuery.userName){
      	this.listQuery.userName = undefined
      }
      if(!this.listQuery.deptId){
      	this.listQuery.deptId = undefined
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
    getDeptList() { //部门下拉框
    	if(this.depts.length === 0) {
    		GetDeptList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.id
					  value.name = x.deptName
					  this.depts.push(value)
					}
	    	})
    	}
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
        this.dataForm.userNo =  res.data.userNo,
        this.dataForm.userName = res.data.userName,
        this.dataForm.deptId = res.data.deptId
       
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
		/**------------用户增删改查结束-1-----------*/
		
		
		/**------------添加角色开始-2-----------*/
		resetCheckedRole(){ //重置
			this.roleValue = []
		},
	  openRole(row){ //打开用户弹框
			this.resetCheckedRole()
			
		 	this.dialogRoleVisible = true
		 	
		 	if(this.roleData.length ==0){ // 只查询一次
		 		//查询系统用户并设置到穿梭框中
			 	FindRoleList({}).then(res => {
			 		let list = res.list
			 		if(list == undefined){
			 			return false;
			 		}
			 		list.forEach((item, index) => {
			 			if(item.isAdmin!=-1){
			 				let name = item.name +"-"+item.description
				 			this.roleData.push({
				 				label: name,
				 				key:item.id
				 			})
			 			}
			 		})
			 		
			 	})
		 	}
		 	
		 	this.userId = row.webid
		 	
		 	// 获取当前角色的用户信息
		 	let param ={
		 		userId: this.userId
		 	}
		 	GetCheckedRole(param).then(res => {
		 		this.roleValue = res.data
		 	})
		},
		updateRoleData(){ // 添加角色用户
			let roles = this.roleValue.join()
			let param = {
				userId: this.userId,
				roles: roles
			}
			AddUserRole(param).then(res => {
				this.dialogRoleVisible = false
			})
		},
		roleFilter(query, item){ //穿梭框搜索功能
	  	return item.label.indexOf(query) > -1;
	  },
	  
	  /**------------添加角色结束-2-----------*/
	 
		dateFormats: function (val) {
			if(!val){
				return undefined
			}
			return moment(val).format("YYYY-MM-DD HH:mm:ss");
		},
  }
}
</script>
