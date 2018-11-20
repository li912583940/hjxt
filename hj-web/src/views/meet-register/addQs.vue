<template>
	<div class="app-container">
		<!-- 亲属新增或编辑 -->
		<el-card shadow="always" style="width: 40%; margin-left: 30%;">
			<el-form :rules="rulesQs" :model="dataQsForm" ref="dataQsForm" label-position="right" label-width="120px" style='width: 400px; margin-left:12%;' >
		        <el-form-item label="证件类别" prop="qsZjlb">
		          <el-select class="filter-item" v-model="dataQsForm.qsZjlb" placeholder="请选择">
		            <el-option v-for="item in qsZjlbs" :key="item.id" :label="item.name" :value="item.id"></el-option>
		          </el-select>
		        </el-form-item>
		        <el-form-item label="证件图像" prop="zp">
		          <img :src="sfzImg" id="zp" name="zp" width="100px" height="126px" />
		        </el-form-item>
		        <el-form-item label="">
		          <el-button id="shibie1" name="shibie1" size="mini" type="primary" @click="shibie">识别</el-button>
		        </el-form-item>
		        <el-form-item label="证件号码" prop="qsSfz">
		          <el-input v-model="dataQsForm.qsSfz"></el-input>
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
				    <el-button @click="returnPrevious">返 回</el-button>
			        <el-button type="primary" @click="createQsData">确 定</el-button>
				</el-form-item>
	        </el-form>
        </el-card>
        
        <object width="0px" height="0px" id="IDCard2" name="IDCard2"  codebase="../../ocx/SynCardOcx.CAB#version=1,0,0,1" classid="clsid:4B3CB088-9A00-4D24-87AA-F65C58531039">
		</object>
    </div>
</template>

<script>
import { findQsPojo, findQsOne, RequestQsAdd, RequestQsEdit, RequestQsDelete, findGxList  } from '@/api/criminal'

export default {
  name: 'addQs',
  data() {
    return {
      sfzImg: '/static/image/zpbj.jpg',
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
      scriptAddHjDj : undefined //身份证读卡器时间节点
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
    returnPrevious(){ // 返回上一页
    	history.go(-1)
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
    openPort(){ // 打开读卡器驱动
    	console.log('打开port')
		//document.all.qsCard.focus();
	//	var isSuc=false;
	//	for(var i=1;i<10;i++){
	//		 isSuc=document.getElementById("WM1711").OpenPort1(i,"115200");
	//		 if(isSuc==true){
	//		 	break;
	//		 }
	//	}
		//reID.ReadCardID(4, "baud=9600 parity=N data=8 stop=1");
		let str=document.getElementById("IDCard2").FindReader()
		if(str>1000){
			document.getElementById("IDCard2").SetloopTime(1000);
	  		document.getElementById("IDCard2").SetReadType(1);
	  		document.getElementById("IDCard2").SetPhotoType(1);
		}
		
		this.cardEvent()
	},
	
	colsePort(){ // 关闭读卡器驱动
		console.log('关闭port')
		if(this.scriptAddHjDj){ // 删除节点
			document.body.removeChild(this.scriptAddHjDj);
			console.log('节点删除成功')
		}
		document.getElementById("IDCard2").SetReadType(0);
	//	document.getElementById("WM1711").FunCloseCard();
	},
  	cardEvent() {// 设置读卡器监听事件  并根据亲属身份证信息查询犯人
  		console.log('cardEvent start')
		let handler =	document.createElement("script")
		handler.setAttribute("for", "IDCard2");
		handler.setAttribute("event","CardIn(State);")
		handler.appendChild(document.createTextNode("{"))
		handler.appendChild(document.createTextNode("if(State == 1){"))
		handler.appendChild(document.createTextNode("document.getElementById('shibie1').click();"))
		handler.appendChild(document.createTextNode("}"))
		handler.appendChild(document.createTextNode("}"))
		document.body.appendChild(handler)
		
		this.scriptAddHjDj = handler
  	},
  	shibie(){ // 识别身份证信息并查询
  		console.log('shibieQs start')
  		this.dataQsForm.qsSfz = 'sadadadaff'
  		console.log(this.dataQsForm.qsSfz)
    	var IDCard2=document.getElementById("IDCard2");
    	
		IDCard2.SetPhotoName(2);
		//let a = IDCard2.Base64Photo;
		//document.getElementById("base64").value=a;
		this.dataQsForm.qsSfz = IDCard2.CardNo
		this.dataQsForm.qsName = IDCard2.NameA
		this.dataQsForm.dz = IDCard2.Address
		this.dataQsForm.xb = IDCard2.Sex==2?'女':'男'
//		document.getElementById("sfzzzz").value=b;
	  	
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