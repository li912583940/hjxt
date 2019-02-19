<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入名称" v-model="listQuery.name" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">{{$t('criminal.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 1371px">
      <el-table-column width="100" align="center"  :label="$t('criminal.id')">
        <template slot-scope="scope">
          <span>{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" align="center" label="名称">
        <template slot-scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column width="350" align="center" label="描述">
        <template slot-scope="scope">
          <span>{{scope.row.description}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="创建时间" >
        <template slot-scope="scope">
          <span>{{scope.row.createTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" align="center" label="创建人">
        <template slot-scope="scope">
          <span>{{scope.row.createUserName}}</span>
        </template>
      </el-table-column>
      <!--<el-table-column width="150" align="center" label="菜单权限">
        <template slot-scope="scope">
          <span v-if="scope.row.authorityResource==1">已设置</span>
          <span v-if="scope.row.authorityResource==0">未设置</span>
        </template>
      </el-table-column>-->
      <!--<el-table-column width="150" align="center" label="监区权限">
        <template slot-scope="scope">
          <span v-if="scope.row.authorityJq==1">已设置</span>
          <span v-if="scope.row.authorityJq==0">未设置</span>
        </template>
      </el-table-column>-->
      <el-table-column align="center" :label="$t('criminal.actions')" width="200" >
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="权限设置" width="260" >
        <template slot-scope="scope">
          <el-button size="mini" type="info" icon="el-icon-setting" @click="openAuthority(scope.row)">分配权限</el-button>
          <el-button type="info" size="mini" icon="el-icon-plus" @click="openUser(scope.row)">添加用户</el-button>
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
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="dataForm.description"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
    
    
    <!-- 设置权限 --> <!--default-expand-all-->
    <el-dialog title="设置权限" :visible.sync="dialogAuthorityVisible">
    	<el-row >
    		<el-col :span="12">
		      <el-card style="width: 85%; margin-left: 30px;">
				    <div slot="header" >
				        <span>为角色分配菜单权限</span>
				    </div>
				    <div class="text item">
				        <el-tree
						  :data="menuData"
						  show-checkbox
						  check-on-click-node
						  :default-checked-keys="menuCheckedKeys"
						  node-key="id"
						  ref="menuDataTree"
						  highlight-current
						  :props="defaultProps">
					    </el-tree>
				    </div>
			    </el-card>
	      </el-col>
	        <el-col :span="12">
			  <el-card style="width: 85%; margin-left: 15px;">
				    <div slot="header" >
				        <span>为角色分配监区权限</span>
				    </div>
				    <div class="text item">
			            <el-tree
						  :data="jqData"
						  show-checkbox
						  default-expand-all
						  check-on-click-node
						  :default-checked-keys="jqCheckedKeys"
						  node-key="id"
						  ref="jqDataTree"
						  highlight-current
						  :props="defaultProps">
					    </el-tree>
				    </div>
			  </el-card>
			</el-col>
	  </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAuthorityVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAuthorityData">确 定</el-button>
      </div>
    </el-dialog>


    <!-- 添加用户 -->
		<el-dialog title="添加用户" :visible.sync="dialogUserVisible">
			<!--<el-card style="width: 80%; margin-left: 30px;">-->
				<el-transfer style="width: 522px; margin-left: 19%;"
			    filterable
			    :filter-method="userFilter"
			    filter-placeholder="请输入关键字搜索"
			    v-model="userValue"
			    :data="userData"
			    :titles="['未拥有用户', '拥有用户']">
			  </el-transfer>
		  <!--</el-card>-->
		  <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUserData">确 定</el-button>
      </div>
		</el-dialog>
		
  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, //角色的增删改查
	GetMenuTree,GetCheckedMenu, GetJqTree, GetCheckedJq, AddRoleMenu, AddRoleJq, // 设置权限相关
  FindUserList, GetCheckedUser, AddRoleUser } from '@/api/sysRoles'


import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'sysRoles',
  directives: {
    waves
  },
  data() {
    return {
    	/**---------------------------角色增删改查开始-1--------------------------*/
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        name: undefined
      },
      // 新增或编辑弹窗
      dataForm: { 
        id: undefined,
        name: '',
        description: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
      rules: {
        name: [{ required: true, message: '名称不能为空', trigger: 'blur' }]
      },
      /** 角色增删改查结束 */
      
      roleId: undefined,  //设置权限时的id
      /** 设置权限开始 */
      dialogAuthorityVisible: false, // 设置权限弹框
      // 树形节点
      defaultProps: {
		    children: 'children',
		    label: 'label'
		  },
		  menuData: [], // 目录树形结构
		  menuCheckedKeys: [], //  目录默认选中节点的值
		  jqData: [], // 监区树形结构
		  jqCheckedKeys : [], // 监区默认选中节点的值
		  /** 设置权限结束 */
		 
		  /** 添加用户开始 */
		  dialogUserVisible: false, // 添加用户弹框
		  userData: [],
		  userValue: [],
		  /** 添加用户结束 */
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
  methods: {
  	/** 角色增删改查开始 */
    getList() {
      this.listLoading = true
      if(!this.listQuery.name){
      	this.listQuery.name = undefined
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
			this.dataForm.id = undefined
    },
    handleCreate() {
      this.dialogStatus = 'create'
      this.resetForm('dataForm')
      this.dialogFormVisible = true
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
    		id: row.id
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.id = res.data.id,
	        this.dataForm.name =  res.data.name,
	        this.dataForm.description = res.data.description
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
    		id: row.id
    	}
			RequestDelete(param).then(() => {
    		this.getList()
    	}).catch(error => {
        this.dialogFormVisible = false
      })
		})
	},
	/**---------------------------角色增删改查结束-1--------------------------*/
	
	/**---------------------------设置权限开始-2--------------------------*/
	resetCheckedTree(){ //重置
		this.menuCheckedKeys = []
		this.jqCheckedKeys = []
		if(this.$refs.menuDataTree){ //确保树加载完毕，再调用清空方法
			this.$refs.menuDataTree.setCheckedKeys([])
		}
		if(this.$refs.jqDataTree){ //确保树加载完毕，再调用清空方法
			this.$refs.jqDataTree.setCheckedKeys([])
		}
		
	},
	openAuthority(row){ //打开权限弹框
		this.resetCheckedTree()
		
		if(this.menuData.length === 0){ // 只查询一次
			this.getMenuTree()
		}
		if(this.jqData.length === 0){ // 只查询一次
			this.getJqTree()
		}
		
		this.roleId = row.id
		// 获取当前角色的目录
		this.getCheckedMenu(this.roleId)
		// 获取当前角色的监区
		this.getCheckedJq(this.roleId)
		
		this.dialogAuthorityVisible = true
	},
	getMenuTree() { // 获取目录树形结构
      GetMenuTree({}).then((res) => {
      	 this.menuData = res.data
      	 
      }).catch(error => {
      })
  },
  getCheckedMenu(roleId) { // 获取当前角色选中的目录 数组格式
  	  let param = {
  	  	roleId: roleId
  	  }
      GetCheckedMenu(param).then((res) => {
      	 this.menuCheckedKeys = res.data
      }).catch(error => {
      })
  },
  getJqTree() { // 获取监区树形结构
      GetJqTree({}).then((res) => {
      	 this.jqData = res.data
      }).catch(error => {
      })
  },
  getCheckedJq(roleId) { // 获取当前角色选中的监区 数组格式
  	let param = {
  	  	roleId: roleId
  	  }
      GetCheckedJq(param).then((res) => {
      	 this.jqCheckedKeys = res.data
      }).catch(error => {
      })
  },
  updateAuthorityData(){ // 提交权限选择
  	this.addRoleMenu() 
  	this.dialogAuthorityVisible = false
  	
  },
  addRoleMenu(){ // 添加角色菜单关联关系
  	let menus = this.$refs.menuDataTree.getCheckedKeys().join()
  	let param = {
  		roleId: this.roleId,
  		menus: menus
  	}
  	AddRoleMenu(param).then(res => {
  		this.addRoleJq()
  	})
  },
  addRoleJq(){ // 添加角色监区关联关系
  	let jqs = this.$refs.jqDataTree.getCheckedKeys().join()
  	let param = {
  		roleId: this.roleId,
  		jqs: jqs
  	}
  	AddRoleJq(param).then(res => {
  		this.getList()
  	})
  },
  /**---------------------------设置权限结束-2--------------------------*/
 
 
  /**---------------------------添加用户开始-3--------------------------*/
  resetCheckedUser(){ //重置
		this.userValue = []
	},
	openUser(row){ //打开用户弹框
		this.resetCheckedUser()
		
	 	this.dialogUserVisible = true
	 	
	 	if(this.userData.length ==0){ // 只查询一次
	 		//查询系统用户并设置到穿梭框中
		 	FindUserList({}).then(res => {
		 		let list = res.list
		 		if(list == undefined){
		 			return false;
		 		}
		 		list.forEach((item, index) => {
		 			if(item.isSuper==0){
		 				let name = item.userName +"-"+item.userDepart
			 			this.userData.push({
			 				label: name,
			 				key:item.webId
			 			})
		 			}
		 		})
		 		
		 	})
	 	}
	 	
	 	this.roleId = row.id
	 	
	 	// 获取当前角色的用户信息
	 	let param ={
	 		roleId: this.roleId
	 	}
	 	GetCheckedUser(param).then(res => {
	 		this.userValue = res.data
	 	})
	},
	updateUserData(){ // 添加角色用户
		let users = this.userValue.join()
		let param = {
			roleId: this.roleId,
			users: users
		}
		AddRoleUser(param).then(res => {
			this.dialogUserVisible = false
		})
	},
	
  userFilter(query, item){ //穿梭框搜索功能
  	return item.label.indexOf(query) > -1;
  },
  /**---------------------------添加用户结束-3-------------------------- */
 
	dateFormats: function (val) {
		if(!val){
			return undefined
		}
		return moment(val).format("YYYY-MM-DD HH:mm:ss");
	},
  }
}
</script>
