<!--
监区
-->
<template>
  <div class="app-container">
  	<div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="监区名称" v-model="listQuery.jqName" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">添加</el-button>
    	<el-button v-if="isAdmin==1" class="filter-item" style="margin-left: 10px;" @click="createAll" type="primary" icon="el-icon-circle-plus-outline">批量添加会见星期</el-button>
  	</div>
    
    <el-table :key='tableKey' :data="list"   border fit highlight-current-row
      style="width: 1031px">
      <el-table-column width="100" align="center" :label="$t('currency.jqNo')">
        <template slot-scope="scope">
          <span>{{scope.row.jqNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <!--<el-table-column width="100" align="center" label="楼层">
        <template slot-scope="scope">
          <span>{{scope.row.floor}}</span>
        </template>
      </el-table-column>-->
      <el-table-column width="350" align="center" label="会见星期时间">
        <template slot-scope="scope">
          <span>{{scope.row.jqWeek}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="特殊监区">
        <template slot-scope="scope">
          <span v-if="scope.row.isTs==0">否</span>
          <span v-if="scope.row.isTs==1" style="color: red;">是</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.editPermission==1 || buttonRole.deletePermission==1 || buttonRole.holidayPermission==1" align="center" :label="$t('criminal.actions')" width="320" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.editPermission==1" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="buttonRole.deletePermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button v-if="buttonRole.holidayPermission==1" size="mini" type="info" icon="el-icon-setting" @click="openWeek(scope.row)">设置会见星期</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

	<!-- 新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="600px" :modal-append-to-body="false">
      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:10%;' >
        <el-form-item label="监区编号" prop="jqNo">
          <el-input v-if="dialogStatus=='update'" v-model="dataForm.jqNo" :disabled="true"></el-input>
          <el-input v-if="dialogStatus=='create'" v-model="dataForm.jqNo"></el-input>
        </el-form-item>
        <el-form-item label="监区名称" prop="jqName">
          <el-input v-model="dataForm.jqName"></el-input>
        </el-form-item>
        <!--<el-form-item label="楼层" prop="floor">
          <el-input v-model="dataForm.floor"></el-input>
        </el-form-item>-->
        <el-form-item label="特殊监区">
          <el-radio-group v-model="dataForm.isTs">
		    <el-radio :label="0">否</el-radio>
		    <el-radio :label="1">是</el-radio>
		  </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确 定</el-button>
        <el-button v-else type="primary" @click="updateData">确 定</el-button>
      </div>
    </el-dialog>
    
    <!-- 设置会见星期 -->
		<el-dialog title="设置会见星期" :visible.sync="dialogWeekVisible" width="740px" :modal-append-to-body="false">
			<el-card style="width: 540px; margin-left: 10%;">
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
	
	  <el-dialog title="批量添加会见星期" :visible.sync="dialogAllWeekVisible" width="740px" :modal-append-to-body="false">
			<el-card style="width: 540px; margin-left: 10%;">
				<el-transfer
			    v-model="allWeekValue"
			    :data="weekData"
			    :titles="['未拥有', '已拥有']">
			  </el-transfer>
		  </el-card>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogAllWeekVisible = false">取 消</el-button>
		    <el-button type="primary" @click="updateAllWeekData">确 定</el-button>
		  </div>
		</el-dialog>
  </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, GetCheckedWeek, AddJqWeek, AddJqAllWeek} from '@/api/jqSet'

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
        pageSize: 10
      },
      // 新增或编辑弹窗
      dataForm: { 
        webid: undefined,
        jqNo: undefined,
        jqName: undefined,
        isTs: 0,
        //floor: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编 辑',
        create: '新 增'
      },
       rules: {
       	jqNo: [{ required: true, message: '监区编号不能为空', trigger: 'blur' }],
        jqName: [{ required: true, message: '监区名称不能为空', trigger: 'blur' }]
      },
      /**--------------------设置会见星期日   开始--------------------------*/
      jqNo: undefined,
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
      ],
      /**---------------------设置会见星期日   结束--------------------------*/
			
			//按钮权限   1：有权限， 0：无权限
			isAdmin: 0,
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	editPermission: 0,
      	deletePermission: 0,
      	holidayPermission: 0,
      },
      
      dialogAllWeekVisible:false,
      allWeekValue: [],
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
  mounted() {
    this.setButtonRole()
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
    		this.dataForm.webid = res.data.webid
	        this.dataForm.jqNo =  res.data.jqNo
	        this.dataForm.jqName = res.data.jqName
	        this.dataForm.isTs = res.data.isTs
	        //this.dataForm.floor = res.data.floor
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
	/**------------------ 设置会见星期日开始 ----------------------*/
	resetCheckedRole(){ //重置
		this.weekValue = []
	},
	openWeek(row){
		this.resetCheckedRole()
		
		this.dialogWeekVisible = true
		
		this.jqNo = row.jqNo
		
		// 获取当前监区的会见星期日
		let param ={
	 		jqNo: this.jqNo
	 	}
	 	GetCheckedWeek(param).then(res => {
	 		this.weekValue = res.data
	 	})
	},
	// 添加会见星期日
	updateWeekData(){
		let weeks = this.weekValue.join()
		let param = {
			jqNo: this.jqNo,
			weeks: weeks
		}
		AddJqWeek(param).then(res => {
			this.dialogWeekVisible = false
			this.getList()
		})
	},
	/**------------------ 设置会见星期日结束 ----------------------*/
  
  setButtonRole() { //设置按钮的权限
		let roles = sessionStorage.getItem("roles")
		if(roles.includes('admin')){
			this.isAdmin=1
			this.buttonRole.addPermission= 1
			this.buttonRole.editPermission= 1
			this.buttonRole.deletePermission= 1
			this.buttonRole.holidayPermission= 1
		}else{
			let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
			let jqSet = buttonRoles.jqSet
			if(jqSet.length>0){
				for(let value of jqSet){
					if(value=='addPermission'){
						this.buttonRole.addPermission= 1
					}else if(value=='editPermission'){
						this.buttonRole.editPermission= 1
					}else if(value=='deletePermission'){
						this.buttonRole.deletePermission= 1
					}else if(value=='holidayPermission'){
						this.buttonRole.holidayPermission= 1
					}
				}
			}
		}
	},
	createAll(){
		this.allWeekValue = []
		this.dialogAllWeekVisible=true
	},
	// 批量添加会见星期日
	updateAllWeekData(){
		let weeks = this.allWeekValue.join()
		let param = {
			weeks: weeks
		}
		AddJqAllWeek(param).then(res => {
			this.dialogAllWeekVisible = false
			this.getList()
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
