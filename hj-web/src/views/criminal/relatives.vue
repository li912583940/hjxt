<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属姓名" v-model="listQuery.qsName" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">{{$t('criminal.add')}}</el-button>
      <el-button v-if="buttonRole.exportPermission==1" class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>
      <el-checkbox class="filter-item" style='margin-left:15px;' @change='tableKey=tableKey+1' v-model="showReviewer">{{$t('criminal.reviewer')}}</el-checkbox>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="80" align="center"  :label="$t('criminal.id')">
        <template slot-scope="scope">
          <span>{{scope.row.webId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="亲属姓名">
        <template slot-scope="scope">
          <span>{{scope.row.qsName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="关系">
        <template slot-scope="scope">
          <span>{{scope.row.gx}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="性别">
        <template slot-scope="scope">
          <span>{{scope.row.xb}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="电话号码">
        <template slot-scope="scope">
          <span>{{scope.row.tele}}</span>
        </template>
      </el-table-column>
      <el-table-column width="300" align="center" label="地址">
        <template slot-scope="scope">
          <span>{{scope.row.dz}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="备注">
        <template slot-scope="scope">
          <span>{{scope.row.bz}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.editPermission==1 || buttonRole.deletePermission==1" align="center" :label="$t('criminal.actions')" width="180" class-name="small-padding fixed-width">
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
      	<el-input v-if="false" v-model="dataForm.frNo" ></el-input>
        <el-form-item :label="$t('currency.frName')" prop="frName">
          <el-input v-model="dataForm.frName"></el-input>
        </el-form-item>
        <el-form-item label="证件类别" prop="qsZjlb">
          <el-select class="filter-item" v-model="dataForm.qsZjlb" placeholder="请选择">
            <el-option v-for="item in qsZjlbs" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="qsSfz">
          <el-input v-model="dataForm.qsSfz"></el-input>
          <el-button  size="mini" type="primary" @click="handleDistinguish()">识别</el-button>
        </el-form-item>
        <el-form-item label="亲属姓名" prop="qsName">
          <el-input v-model="dataForm.qsName"></el-input>
        </el-form-item>
        <el-form-item label="关系" prop="gx">
          <el-select class="filter-item" v-model="dataForm.gx" placeholder="请选择">
            <el-option v-for="item in gxs" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="IC卡号" prop="qsCard">
          <el-input v-model="dataForm.qsCard"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="dz">
          <el-input v-model="dataForm.dz"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
        	<el-radio-group v-model="dataForm.xb">
        		<el-radio :label="'男'">男</el-radio>
				    <el-radio :label="'女'">女</el-radio>
				  </el-radio-group>
        </el-form-item>
        <el-form-item label="电话号码" prop="tele">
          <el-input v-model="dataForm.tele"></el-input>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-input v-model="dataForm.spState" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="dataForm.bz"></el-input>
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
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete } from '@/api/relatives'

import moment from 'moment'
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'


export default {
  name: 'relatives',
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
        qsName: undefined
      },
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	editPermission: 0,
      	deletePermission: 0,
      	exportPermission: 0,
      },
      
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      // 新增或编辑弹窗
      dataForm: { 
        webId: undefined,
        frNo: '',
        frName: undefined,
        qsZjlb: 1,
        qsSfz: undefined,
        qsName: undefined,
        gx: undefined,
        qsCard: undefined,
        dz: undefined,
        xb: undefined,
        tele: undefined,
        spState: undefined,
        bz: undefined
      },
      gxs: [ // 关系
      	
      ],
      qsZjlbs: [
        {
        	id: 1,
        	name: '身份证'
        },
      	{
      		id: 2,
      		name: '警官证'
      	},
      	{
      		id: 3,
      		name: '工作证'
      	},
      	{
      		id: 4,
      		name: '其他'
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
        qsName: [{ required: true, message: '亲属姓名不能为空', trigger: 'blur' }]
      },
      downloadLoading: false
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
      this.listLoading = true
      if(!this.listQuery.frName){
      	this.listQuery.frName = undefined
      }
      if(!this.listQuery.frNo){
      	this.listQuery.frNo = undefined
      }
      if(!this.listQuery.qsName){
      	this.listQuery.qsName = undefined
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
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.editPermission= 1
    		this.buttonRole.deletePermission= 1
    		this.buttonRole.exportPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let relatives = buttonRoles.relatives
    		if(relatives.length>0){
    			for(let value of relatives){
    				if(value=='addPermission'){
    					this.buttonRole.addPermission= 1
    				}else if(value=='editPermission'){
    					this.buttonRole.editPermission= 1
    				}else if(value=='deletePermission'){
    					this.buttonRole.deletePermission= 1
    				}else if(value=='exportPermission'){
    					this.buttonRole.exportPermission= 1
    				}
    			}
    		}
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
    		id: row.webId
    	}
    	findOne(param).then((res) =>{
    		this.dataForm.webId = res.data.webId,
        this.dataForm.frName =  res.data.frName,
        this.dataForm.frNo = res.data.frNo,
        this.dataForm.qsZjlb = res.data.qsZjlb,
        this.dataForm.qsSfz = res.data.qsSfz,
        this.dataForm.qsName = res.data.qsName,
        this.dataForm.gx = res.data.gx,
        this.dataForm.qsCard = res.data.qsCard,
        this.dataForm.dz = res.data.dz,
        this.dataForm.xb = res.data.xb,
        this.dataForm.tele = res.data.tele,
        this.dataForm.spState = res.data.spState,
        this.dataForm.bz = res.data.bz
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
