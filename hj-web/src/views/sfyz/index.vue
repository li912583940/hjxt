<template>
	<div class="app-container">
        <div class="filter-container">
	      <el-input @keyup.enter.native="handleFilter" style="width: 200px; margin-left: 10%;" class="filter-item" placeholder="输入亲属身份证号" v-model="listQuery.qsSfz" clearable>
	      </el-input>
	      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
	    </div>
	    
	    <div style="width: 1200px; margin-left: 10%;">
	    	<table class="gridtable" >
	      	 	<tr>
	      	 		<td width="30%" colspan="2">亲属姓名&nbsp;&nbsp;&nbsp;：<el-input style="width: 200px;" size="mini" v-model="qs.qsName" :disabled="true"></el-input></td>
	      	 		<td width="20%" colspan="3" rowspan="4">
	      	 			<span >
	      	 				<img :src="sfzImg" id="zp" name="zp"  width="150px" height="176px">
	      	 			</span>
	      	 		</td>
	      	 	</tr>
	      	 	<tr>
	      	 		<td width="30%" colspan="2">亲属身份证：<el-input style="width: 200px;" size="mini" v-model="qs.sfz" :disabled="true"></el-input></td>
	      	 	</tr>
	      	 	<tr>
	      	 		<td width="30%" colspan="2">亲属性别&nbsp;&nbsp;&nbsp;：<el-input style="width: 200px;" size="mini" v-model="qs.xb" :disabled="true"></el-input></td>
	      	 	</tr>
	      	 	<tr>
	      	 		<td width="30%" colspan="2">亲属地址&nbsp;&nbsp;&nbsp;：<el-input style="width: 200px;" size="mini" v-model="qs.dz" :disabled="true"></el-input></td>
	      	 	</tr>
	      	</table>
	      	<div style="margin-top: 14px;">
	      		<table  class="gridtable">
		      	 	<tr>
		      	 		<th width="15%">罪犯编号</th>
		      	 		<th width="15%">罪犯姓名</th>
		      	 		<th width="15%">监区名称</th>
		      	 		<th width="15%">座位</th>
		      	 		<th width="15%">分管等级</th>
		      	 		<th width="25%">罪名</th>
		      	 	</tr>
		      	 	<tr v-if="jlFr!=null">
		      	 		<td width="15%"><font color="red"> {{jlFr.frNo}} </font> </td>
		      	 		<td width="15%"><font color="red"> {{jlFr.frName}} </font> </td>
		      	 		<td width="15%"><font color="red"> {{jlFr.jqName}} </font> </td>
		      	 		<td width="15%"><font color="red"> {{jlFr.zw}} </font> </td>
		      	 		<td width="15%"><font color="red"> {{jlFr.jbName}} </font> </td>
		      	 		<td width="25%"><font color="red"> {{jlFr.infoZm}} </font> </td>
		      	 	</tr>
		      	 	<tr v-if="jlFr==null">
		      	 		<td colspan="6" ><font color="red" >没有相关信息</font></td>
		      	 	</tr>
		      	</table>
	        </div>
        </div>
        <button hidden="hidden" id="shibie1" @click="shibie()"></button>
    </div>
</template>

<script>
import { RequestSfyz } from '@/api/sfyz'
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'sfyz',
  directives: {
    waves
  },
  data() {
    return {
      sfzImg: '/static/image/zpbj.jpg',
      jlFr: null,	
      listQuery: {
        qsSfz: undefined,
      },
      qs: {
      	qsName: undefined,
      	xb: undefined,
      	sfz: undefined,
      	dz: undefined
      },
      scriptAddHjDj : undefined, //身份证读卡器时间节点
    }
  },
  filters: {
    
  },
  created() {
  	
  },
  mounted() {
    this.openPort()
  },
  destroyed(){
  	this.colsePort()
  },
  methods: {
  	getList() {
      RequestSfyz(this.listQuery).then((res) => {
      	 this.jlFr = res.jlFr
      	 if(res.jlQs){
      	 	this.qs.qsName=res.jlQs.qsName
      	 	this.qs.xb=res.jlQs.xb
      	 	this.qs.sfz=res.jlQs.qsSfz
      	 	this.qs.dz=res.jlQs.dz
      	 	if(res.jlQs.zpUrl){
      	 		this.sfzImg = res.jlQs.zpUrl
      	 	}else{
      	 		this.sfzImg = '/static/image/zpbj.jpg'
      	 	}
      	 }else{
      	 	this.qs.qsName=undefined
      	 	this.qs.xb=undefined
      	 	this.qs.sfz=undefined
      	 	this.qs.dz=undefined
      	 }
      	 if(res.state==0){
      	 	Message({
		        message: '身份验证成功',
			    type: 'success',
			    duration: 5 * 1000
		    });
      	 }else if(res.state==1){
      	 	Message({
		        message: '身份验证失败',
			    type: 'error',
			    duration: 5 * 1000
		    });
      	 }
      }).catch(error => {
      })
    },
    handleFilter() {
      this.getList()
    },

    openPort(){ // 打开读卡器驱动
    	console.log('打开port')
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1) ){ // IE浏览器
			let str=document.getElementById("IDCard2").FindReader()
			if(str>1000){
				document.getElementById("IDCard2").SetloopTime(1000);
		  		document.getElementById("IDCard2").SetReadType(1);
		  		document.getElementById("IDCard2").SetPhotoType(1);
			}
			this.cardEvent()
    	}
	},
	
	colsePort(){ // 关闭读卡器驱动
		if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){ // IE浏览器
			if(this.scriptAddHjDj){ // 删除节点
				document.body.removeChild(this.scriptAddHjDj);
			}
			document.getElementById("IDCard2").SetReadType(0);
    	}
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
    	var IDCard2=document.getElementById("IDCard2");
    	
		IDCard2.SetPhotoName(2);
		//let a = IDCard2.Base64Photo;
		//document.getElementById("base64").value=a;
		this.qs.qsSfz = IDCard2.CardNo
		this.qs.qsName = IDCard2.NameA
		this.qs.dz = IDCard2.Address
		this.qs.xb = IDCard2.Sex==2?'女':'男'
//		document.getElementById("sfzzzz").value=b;
		var zpAddress=IDCard2.PhotoName
		document.getElementById("zp").src=zpAddress
		
		this.listQuery.qsSfz=this.qs.qsSfz
	  	this.getList()
  	},

  }
}
</script>

<style>
 .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
  
table.gridtable {
font-family: verdana,arial,sans-serif;
font-size:15px;
color:#333333;
border-width: 1px;
border-color: #76a5af;
width: 1100px;
border-collapse: collapse;
}
table.gridtable th {
border-width: 1px;
padding: 8px;
border-style: solid;
border-color: #76a5af;
background-color: #dedede;
}
table.gridtable td {
border-width: 1px;
padding: 8px;
border-style: solid;
border-color: #76a5af;
background-color: #ffffff;
text-align:center;
}
</style>