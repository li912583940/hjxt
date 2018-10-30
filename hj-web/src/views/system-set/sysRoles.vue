<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入名称" v-model="listQuery.name" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('criminal.add')}}</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 1231px">
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
          <span>{{scope.row.createUserId}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('criminal.actions')" width="320" >
        <template slot-scope="scope">
          <span v-if="scope.row.isAdmin ==-1">超级管理员不能更改</span>
          <el-button v-if="scope.row.isAdmin !=-1" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="scope.row.isAdmin !=-1"  size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button v-if="scope.row.isAdmin !=-1"  size="mini" type="danger" icon="el-icon-delete" @click="openAuthority(scope.row)">设置权限</el-button>
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
    
    
    <!-- 设置权限 -->
    <el-dialog title="设置权限" :visible.sync="dialogAuthorityVisible">
    	<el-row :gutter="12">
    		<el-col :span="12">
		        <el-card style="width: 400px; margin-left: 15px;">
				    <div slot="header" >
				        <span>为角色分配菜单权限</span>
				    </div>
				    <div class="text item">
				        <el-tree
						  :data="menuData"
						  show-checkbox
						  default-expand-all
						  node-key="id"
						  ref="menuDataTree"
						  highlight-current
						  :props="defaultProps">
					    </el-tree>
				    </div>
			    </el-card>
	        </el-col>
	        <el-col :span="12">
			  <el-card style="width: 400px; margin-left: 15px;">
				    <div slot="header" >
				        <span>为角色分配监区权限</span>
				    </div>
				    <div class="text item">
			            <el-tree
						  :data="jqData"
						  show-checkbox
						  default-expand-all
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
        <el-button type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete } from '@/api/sysRoles'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'sysRoles',
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
      dialogAuthorityVisible: false, // 设置权限弹框
      // 树形节点
      defaultProps: {
	    children: 'children',
	    label: 'label'
	  },
	  menuData: [{
          id: 1,
          label: '全选',
          children: [
          	{
	            id: 4,
	            label: '服刑人员',
            },
            {
	            id: 5,
	            label: '会见登记',
            },
            {
	            id: 5,
	            label: '会见监控',
            },
          ]
        }
	  ],
	  jqData: [
	  	{
          id: 0,
          label: '全选',
          children: [{
              id: 1,
              label: '一监区'
            }, {
              id: 10,
              label: '二监区'
            },
            {
              id: 11,
              label: '三监区'
            }
          ]
       },
       
	  ]
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
	openAuthority(row){ //打开权限弹框
		this.dialogAuthorityVisible = true
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
