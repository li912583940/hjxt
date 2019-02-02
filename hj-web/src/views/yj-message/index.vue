<template>
  <div class="app-container">
  	<div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="警察编号" v-model="listQuery.yjNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="警察姓名" v-model="listQuery.yjName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.deptName" placeholder="部门">
        <el-option v-for="item in deptNames" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">{{$t('criminal.add')}}</el-button>
    </div>
    
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 1001px">
      <el-table-column width="200" align="center" label="警察编号" >
        <template slot-scope="scope">
          <span>{{scope.row.yjNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="警察姓名">
        <template slot-scope="scope">
          <span>{{scope.row.yjName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="警察IC卡号">
        <template slot-scope="scope">
          <span>{{scope.row.yjCard}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="部门">
        <template slot-scope="scope">
          <span>{{scope.row.deptName}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.editPermission==1 || buttonRole.deletePermission==1" align="center" :label="$t('criminal.actions')" width="200" >
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
        <el-form-item label="警察编号" prop="yjNo">
        	<el-input v-if="dialogStatus=='update'" v-model="dataForm.yjNo" :disabled="true"></el-input>
          <el-input v-if="dialogStatus=='create'" v-model="dataForm.yjNo"></el-input>
        </el-form-item>
        <el-form-item label="警察姓名" prop="yjName">
          <el-input v-model="dataForm.yjName"></el-input>
        </el-form-item>
        <el-form-item label="警察IC卡号" prop="yjCard">
          <el-input v-model="dataForm.yjCard"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="deptName">
          <el-select class="filter-item" v-model="dataForm.deptName" placeholder="请选择">
            <el-option v-for="item in  deptNames" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
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
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, findDeptNameList} from '@/api/yjMessage'

import moment from 'moment'
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
      	yjNo: undefined,
      	yjName: undefined,
      	deptName: undefined,
        pageNum: 1,
        pageSize: 20
      },
      // 新增或编辑弹窗
      dataForm: { 
        webId: undefined,
        yjNo: undefined,
        yjName: undefined,
        yjCard: undefined,
        deptName: undefined
      },
      deptNames : [ 
      
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
       rules: {
        yjNo: [{ required: true, message: '警察编号不能为空', trigger: 'blur' }],
        yjName: [{ required: true, message: '警察姓名不能为空', trigger: 'blur' }]
      },
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	editPermission: 0,
      	deletePermission: 0
      }
      
    }
  },
  filters: {
    dateFormat(row, column) {
			//时间格式化  
	    let date = row[column.property];  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
		}
  },
  created() {
    this.getList()
    this.getDeptNameList()
  },
  mounted() {
    this.setButtonRole()
  },
  methods: {
    getList() {
//  	if(!this.listQuery.yjNo){
//    	this.listQuery.yjNo = undefined
//    }
//    if(!this.listQuery.yjName){
//    	this.listQuery.yjName = undefined
//    }
//    if(!this.listQuery.deptName){
//    	this.listQuery.deptName = undefined
//    }
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
    
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.editPermission= 1
    		this.buttonRole.deletePermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let yjMessage = buttonRoles.yjMessage
    		if(yjMessage.length>0){
    			for(let value of yjMessage){
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
    
    
    getDeptNameList() { //警察部门下拉框
    	if(this.deptNames.length === 0) {
    		findDeptNameList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.deptName
					  value.name = x.deptName
					  this.deptNames.push(value)
					}
	    	})
    	}
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
		        //this.dialogFormVisible = false
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
        this.dataForm.yjNo =  res.data.yjNo,
        this.dataForm.yjName = res.data.yjName,
        this.dataForm.yjCard = res.data.yjCard,
        this.dataForm.deptName = res.data.deptName
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
	          //this.dialogFormVisible = false
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
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
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
