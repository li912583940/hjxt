<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="listQuery.jq" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-circle-plus-outline">{{$t('criminal.add')}}</el-button>
      <el-button v-if="buttonRole.exportPermission==1" class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">{{$t('criminal.export')}}</el-button>
      <el-checkbox class="filter-item" style='margin-left:15px;' @change='tableKey=tableKey+1' v-model="showReviewer">{{$t('criminal.reviewer')}}</el-checkbox>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column align="center" :label="$t('criminal.id')" width="80">
        <template slot-scope="scope">
          <span>{{scope.row.webId}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" :label="$t('currency.frNo')">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" :label="$t('currency.frName')">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" :label="$t('currency.jqName')">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="级别">
        <template slot-scope="scope">
          <span>{{scope.row.jbName}}</span>
        </template>
      </el-table-column>
      
      <el-table-column width="140" align="center" label="当月会见次数">
        <template slot-scope="scope">
          <span>{{scope.row.hjUse}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="当月剩余次数">
        <template slot-scope="scope">
          <span>{{scope.row.hjLeft}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="入监时间">
        <template slot-scope="scope">
          <span>{{scope.row.infoRjsj}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="亲属人数">
        <template slot-scope="scope">
          <span>{{scope.row.qsNum}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="重点罪犯">
        <template slot-scope="scope">
          <span>{{scope.row.infoZdzf}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="会见级别">
        <template slot-scope="scope">
          <span>{{scope.row.hjJb}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.queryQsPermission==1 || buttonRole.editPermission==1 || buttonRole.deletePermission==1" align="center" :label="$t('criminal.actions')" width="240" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
        	<el-button v-if="buttonRole.queryQsPermission==1" type="primary" size="mini" @click="handleQsManage(scope.row)">亲属</el-button>
          <el-button v-if="buttonRole.editPermission==1" type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row)">{{$t('criminal.edit')}}</el-button>
          <el-button v-if="buttonRole.deletePermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">{{$t('criminal.delete')}}
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
        <el-form-item :label="$t('currency.frNo')" prop="frNo">
          <el-input v-if="dialogStatus=='create'" v-model="dataForm.frNo"></el-input>
          <el-input v-else v-model="dataForm.frNo" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item :label="$t('currency.frName')" prop="frName">
          <el-input v-model="dataForm.frName"></el-input>
        </el-form-item>
        <el-form-item label="IC卡号" prop="frCard">
          <el-input v-model="dataForm.frCard"></el-input>
        </el-form-item>
        <el-form-item :label="$t('currency.jqName')" prop="jq">
          <el-select class="filter-item" v-model="dataForm.jq" placeholder="请选择">
            <el-option v-for="item in  jqs" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="犯人级别" prop="jbNo">
          <el-select class="filter-item" v-model="dataForm.jbNo" placeholder="请选择">
            <el-option v-for="item in  jbNos" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
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

    <!-- 亲属弹框  -->
    <el-dialog :title="qs_frname" :visible.sync="dialogQsVisible" width="70%">
    	<div class="filter-container">
	      <el-button v-if="buttonRole.addQsPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleQsCreate" type="primary" icon="el-icon-edit">{{$t('criminal.add')}}</el-button>
	    </div>
      <el-table :key='qsTableKey' :data="qsList" v-loading="qsListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	      style="width: 100%">
	      <el-table-column align="center" :label="$t('criminal.id')" width="80">
	        <template slot-scope="scope">
	          <span>{{scope.row.webId}}</span>
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
	      <el-table-column v-if="buttonRole.editQsPermission==1 || buttonRole.deleteQsPermission==1" align="center" :label="$t('criminal.actions')" width="180" class-name="small-padding fixed-width" fixed="right">
	        <template slot-scope="scope">
	          <el-button v-if="buttonRole.editQsPermission==1" type="primary" size="mini" icon="el-icon-edit" @click="handleQsUpdate(scope.row)">编辑</el-button>
	          <el-button v-if="buttonRole.deleteQsPermission==1" size="mini" type="danger" icon="el-icon-delete" @click="handleQsDelete(scope.row)">删除</el-button>
	        </template>
	      </el-table-column>
	    </el-table>
	    <!-- 分页 -->
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleQsSizeChange" @current-change="handleQsCurrentChange" :current-page="qsListQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="qsListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="qsTotal">
	      </el-pagination>
	    </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogQsVisible = false">关闭</el-button>
      </span>
    </el-dialog>

		<!-- 亲属新增或编辑 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogQsFormVisible">
      <el-form :rules="rulesQs" :model="dataQsForm" ref="dataQsForm" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
        <el-form-item label="证件类别" prop="qsZjlb">
          <el-select class="filter-item" v-model="dataQsForm.qsZjlb" placeholder="请选择">
            <el-option v-for="item in qsZjlbs" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号码" prop="qsSfz">
          <el-input v-model="dataQsForm.qsSfz"></el-input>
          <el-button  size="mini" type="primary" @click="handleDistinguish()">识别</el-button>
        </el-form-item>
        <el-form-item label="亲属姓名" prop="qsName">
          <el-input v-model="dataQsForm.qsName"></el-input>
        </el-form-item>
        <el-form-item label="关系" prop="gx">
          <el-select class="filter-item" v-model="dataQsForm.gx" placeholder="请选择">
            <el-option v-for="item in gxs" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="IC卡号" prop="qsCard">
          <el-input v-model="dataQsForm.qsCard"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="dz">
          <el-input v-model="dataQsForm.dz"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
        	<el-radio-group v-model="dataQsForm.xb">
        		<el-radio :label="'男'">男</el-radio>
				    <el-radio :label="'女'">女</el-radio>
				  </el-radio-group>
        </el-form-item>
        <el-form-item label="电话号码" prop="tele">
          <el-input v-model="dataQsForm.tele"></el-input>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-input v-model="dataQsForm.spState" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="bz">
          <el-input v-model="dataQsForm.bz"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogQsFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createQsData">确 定</el-button>
        <el-button v-else type="primary" @click="updateQsData">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle } from '@/api/article'
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, exportExcel, findJqList, findJbList, findQsPojo, findQsOne, RequestQsAdd, RequestQsEdit, RequestQsDelete, findGxList  } from '@/api/criminal'

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
        pageSize: 10,
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
      
      ],
      jbNos: [ //犯人级别下拉框
      
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
      rules: {
      	frNo: [{ required: true, message: '编号不能为空', trigger: 'blur'}],
        frName: [{ required: true, message: this.$t('criminal.name'), trigger: 'blur' }],
        jbNo: [{ required: true, message: '级别不能为空', trigger: 'change' }],
        infoRjsj: [{  required: true, message: '请选择入监时间', trigger: 'change' }]
      },
      downloadLoading: false,
      
      // 亲属
      qs_frname: undefined, // 亲属弹框左上角显示犯人姓名
      dialogQsFormVisible: false,
      dialogQsVisible: false,
      qsTableKey: 0,
      qsList: null,
      qsTotal: null,
      qsListLoading: true,
      qsListQuery: {
        pageNum: 1,
        pageSize: 10,
        frNo: undefined
      },
      // 新增或编辑弹窗
      dataQsForm: { 
        webId: undefined,
        frNo: undefined,
        qsZjlb: 1,
        qsSfz: undefined,
        qsName: undefined,
        gx: undefined,
        qsCard: undefined,
        dz: undefined,
        xb: '男',
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
      rulesQs:{
        qsName: [{ required: true, message: '亲属姓名不能为空', trigger: 'blur' }],
      },
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	editPermission: 0,
      	deletePermission: 0,
      	exportPermission: 0,
      	
      	//亲属相关的
      	queryQsPermission: 0,
      	addQsPermission: 0,
      	editQsPermission: 0,
      	deleteQsPermission: 0
      }
      
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
    this.getJqList()
    this.getJbList()
  },
  mounted() {
     this.setButtonRole()
  },
  methods: {
    getList() { // 犯人列表
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
    getJqList() { //监区下拉框
    	if(this.jqs.length === 0) {
    		findJqList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.jqNo
					  value.name = x.jqName
					  this.jqs.push(value)
					}
	    	})
    	}
    },
    getJbList() { //犯人级别下拉框
    	if(this.jbNos.length === 0) {
    		findJbList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.jbNo
					  value.name = x.jbName
					  this.jbNos.push(value)
					}
	    	})
    	}
    },
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.editPermission= 1
    		this.buttonRole.deletePermission= 1
    		this.buttonRole.exportPermission= 1
    		
    		this.buttonRole.queryQsPermission= 1
    		this.buttonRole.addQsPermission= 1
    		this.buttonRole.editQsPermission= 1
    		this.buttonRole.deleteQsPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let criminal = buttonRoles.criminal
    		if(criminal.length>0){
    			for(let value of criminal){
    					if(value=='addPermission'){
    					this.buttonRole.addPermission= 1
    				}else if(value=='editPermission'){
    					this.buttonRole.editPermission= 1
    				}else if(value=='deletePermission'){
    					this.buttonRole.deletePermission= 1
    				}else if(value=='exportPermission'){
    					this.buttonRole.exportPermission= 1
    				}
    				else if(value=='queryQsPermission'){
    					this.buttonRole.queryQsPermission= 1
    				}else if(value=='addQsPermission'){
    					this.buttonRole.addQsPermission= 1
    					this.buttonRole.queryQsPermission= 1
    				}else if(value=='editQsPermission'){
    					this.buttonRole.editQsPermission= 1
    					this.buttonRole.queryQsPermission= 1
    				}else if(value=='deleteQsPermission'){
    					this.buttonRole.deleteQsPermission= 1
    					this.buttonRole.queryQsPermission= 1
    				}
    			}
    		}
    	}
    	
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
//			if(this.$refs[formName] !== undefined){
//				this.$refs[formName].resetFields();
//			}
			this.dataForm.webId= undefined,
	    this.dataForm.frName= undefined,
	    this.dataForm.frNo= '',
	    this.dataForm.frCard= undefined,
	    this.dataForm.jy= '测试监狱',
	    this.dataForm.jq= this.jqs.length === 0?undefined:this.jqs[0].id ,
	    this.dataForm.jbNo= this.jbNos.length === 0?undefined:this.jbNos[0].id,
	    this.dataForm.hjJb= 1,
	    this.dataForm.infoRjsj= undefined,
	    this.dataForm.infoZm= undefined,
	    this.dataForm.infoXq= undefined,
	    this.dataForm.infoCsrq= undefined,
	    this.dataForm.infoHome= undefined,
	    this.dataForm.monitorFlag= '0',
	    this.dataForm.stateZdzf= 1
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
			if(!this.listQuery.frName){
      	this.listQuery.frName = undefined
      }
      if(!this.listQuery.frNo){
      	this.listQuery.frNo = undefined
      }
      if(!this.listQuery.jq){
      	this.listQuery.jq = undefined
      }
      
			exportExcel(this.listQuery).then(res => {
				console.log(res)
	      const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
	     	const downloadElement = document.createElement('a')
	     	const href = window.URL.createObjectURL(blob)
	     	downloadElement.href = href
	     	downloadElement.download = '服刑人员记录.xls'
	     	document.body.appendChild(downloadElement)
	     	downloadElement.click()
     		document.body.removeChild(downloadElement) // 下载完成移除元素
	     	window.URL.revokeObjectURL(href) // 释放掉blob对象
			}).catch(error => {
         console.log(error)
      })


    },
    
    
    // 亲属方法开始
    getQsList() { 
    	this.qsListLoading = true
      findQsPojo(this.qsListQuery).then((res) => {
      	 this.qsList = res.pojo.list
      	 this.qsTotal = res.pojo.count
      	 this.qsListLoading = false
      }).catch(error => {
         this.qsListLoading = false
      })
    },
    getGxList() {
    	if(this.gxs.length === 0) {
    		findGxList({}).then((res) => {
	    		let list = res.list
	    		for(let x of list){
					  let value = {}
					  value.id = x.qsGx
					  value.name = x.qsGx
					  this.gxs.push(value)
					}
	    	})
    	}
    },
    handleQsManage(row) { //打开亲属弹框
    	this.qsListQuery.frNo = row.frNo
    	this.qs_frname = row.frName
			this.dialogQsVisible = true
			this.getQsList()
			this.getGxList()
		},
		handleQsSizeChange(val) {
      this.qsListQuery.pageSize = val
      this.getQsList()
    },
    handleQsCurrentChange(val) {
      this.qsListQuery.pageNum = val
      this.getQsList()
    },
    //重置表单
		resetQsForm(formName) {
			if(this.$refs[formName] !== undefined){
				this.$refs[formName].resetFields();
			}
			this.dataForm.webId= undefined
			this.dataQsForm.webId= undefined
	  },
    handleQsCreate() {
      this.dialogStatus = 'create'
      this.resetQsForm('dataQsForm')
      this.dialogQsFormVisible = true
    },
    createQsData() {
      this.$refs['dataQsForm'].validate((valid) => {
        if (valid) {
        	this.dataQsForm.frNo = this.qsListQuery.frNo
          RequestQsAdd(this.dataQsForm).then(() => {
            this.dialogQsFormVisible = false
            this.getQsList()
          }).catch(error => {
		        this.dialogQsFormVisible = false
		      })
        }
      })
    },
    handleQsUpdate(row) {
    	let param = {
    		id: row.webId
    	}
    	findQsOne(param).then((res) =>{
    		this.dataQsForm.webId = res.data.webId
        this.dataQsForm.frNo = this.qsListQuery.frNo
        this.dataQsForm.qsZjlb = res.data.qsZjlb
        this.dataQsForm.qsSfz = res.data.qsSfz
        this.dataQsForm.qsName = res.data.qsName
        this.dataQsForm.gx = res.data.gx
        this.dataQsForm.qsCard = res.data.qsCard
        this.dataQsForm.dz = res.data.dz
        this.dataQsForm.xb = res.data.xb
        this.dataQsForm.tele = res.data.tele
        this.dataQsForm.spState = res.data.spState
        this.dataQsForm.bz = res.data.bz
    	})
      this.dialogStatus = 'update'
      this.dialogQsFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataQsForm'].clearValidate()
      })
    },
    updateQsData() {
      this.$refs['dataQsForm'].validate((valid) => {
        if (valid) {
          RequestQsEdit(this.dataQsForm).then(() => {
            this.dialogQsFormVisible = false
            this.getQsList()
          }).catch(error => {
		        this.dialogQsFormVisible = false
		      })
        }
      })
    },
    //删除
		handleQsDelete(row) {
			this.$confirm('确认删除该记录吗?', '提示', {
				type: 'warning'
			}).then(() => {
				this.qsListLoading = true;
				let param = {
	    		id: row.webId
	    	}
				RequestQsDelete(param).then(() => {
	    		this.getQsList()
	    	}).catch(error => {
	        this.dialogQsFormVisible = false
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
