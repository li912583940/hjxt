<template>
	<div class="app-container">
		<!-- 亲属新增或编辑 -->
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
	        <el-form-item>
			    <el-button @click="dialogQsFormVisible = false">取 消</el-button>
		        <el-button v-if="dialogStatus=='create'" type="primary" @click="createQsData">确 定</el-button>
		        <el-button v-else type="primary" @click="updateQsData">确 定</el-button>
			</el-form-item>
        </el-form>
    </div>
</template>

<script>
import { findPojo, findOne, RequestAdd, RequestEdit, RequestDelete, exportExcel, findJqList, findJbList, findQsPojo, findQsOne, RequestQsAdd, RequestQsEdit, RequestQsDelete, findGxList  } from '@/api/criminal'

export default {
  name: 'criminal',
  directives: {
    waves
  },
  data() {
    return {
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
      }
    }
  },
  filters: {
    
  },
  created() {
  	this.getGxList()
  },
  mounted() {
	this.openPort()
  },
  destroyed(){
  	this.colsePort()
  },
  methods: {
	getGxList() { // 获取关系
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
    //重置表单
	resetQsForm(formName) {
		if(this.$refs[formName] !== undefined){
			this.$refs[formName].resetFields();
		}
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

<style>
</style>