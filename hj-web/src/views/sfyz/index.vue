<template>
	<div class="app-container">
		<!-- 亲属新增或编辑 -->
        <el-card shadow="always" style="width: 250px; margin-left: 40%;margin-top: 100px;">
	        <img :src="sfzImg" id="zp" name="zp"  width="200px" height="252px">
		      <div style="padding: 14px;margin-left: 40px;">
		        <span>{{qs.qsName}}</span>
		        <span>{{qs.xb}}</span>
		        <span>{{qs.sfz}}</span>
		        <span v-if="qs.state=='验证失败'" style="color: red;">{{qs.state}}</span>
		        <span v-if="qs.state=='验证成功'" style="color: green;">{{qs.state}}</span>
		      </div>
        </el-card>
        
        
        <object width="0px" height="0px" id="IDCard2" name="IDCard2"  codebase="../../ocx/SynCardOcx.CAB#version=1,0,0,1" classid="clsid:4B3CB088-9A00-4D24-87AA-F65C58531039">
		</object>
    </div>
</template>

<script>
import { findQsPojo, findQsOne, RequestQsAdd, RequestQsEdit, RequestQsDelete, findGxList  } from '@/api/sfyz'

export default {
  name: 'addQs',
  data() {
    return {
      sfzImg: '/static/image/zpbj.jpg',
      qs: {
      	qsName: undefined,
      	xb: undefined,
      	sfz: undefined,
      	state: '验证失败'
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
</style>