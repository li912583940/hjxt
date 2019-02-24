<template>
	<div class="app-container">
		<div class="filter-container">
	      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属身份证号" v-model="listQuery.qsSfz" clearable>
	      </el-input>
	      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
	    </div>
		<!-- 亲属新增或编辑 -->
        <el-card shadow="always" style="width: 250px; margin-left: 40%;margin-top: 20px;">
	        <img :src="sfzImg" id="zp" name="zp"  width="200px" height="252px">
	        <div style="padding: 14px;margin-left: 40px;">
		        <span>{{qs.qsName}}</span>
		        <span>{{qs.xb}}</span>
		        <span>{{qs.sfz}}</span>
		        <span v-if="qs.state==1" style="color: red;">身份验证失败</span>
		        <span v-if="qs.state==0" style="color: green;">身份验证成功</span>
	        </div>
	    </el-card>
	    <el-card shadow="always" style="width: 900px; margin-left: 15%;margin-top: 5px;">    
	        <div style="padding: 14px;margin-left: 40px;">
		        <el-table :key='tableKey' :data="list" element-loading-text="给我一点时间" border fit highlight-current-row
			      style="width: 100%">
			      <el-table-column width="100" align="center"  :label="$t('currency.frNo')">
			        <template slot-scope="scope">
			          <span>{{scope.row.frNo}}</span>
			        </template>
			      </el-table-column>
			      <el-table-column width="160" align="center" :label="$t('currency.frName')">
			        <template slot-scope="scope">
			          <span>{{scope.row.frName}}</span>
			        </template>
			      </el-table-column>
			      <el-table-column width="180" align="center" :label="$t('currency.jqName')">
			        <template slot-scope="scope">
			          <span>{{scope.row.jqName}}</span>
			        </template>
			      </el-table-column>
			      <el-table-column width="100" align="center" label="分管等级">
			        <template slot-scope="scope">
			          <span>{{scope.row.jbName}}</span>
			        </template>
			      </el-table-column>
			      <el-table-column width="200" align="center" label="罪名">
			        <template slot-scope="scope">
			          <span>{{scope.row.infoZm}}</span>
			        </template>
			      </el-table-column>
			    </el-table>
	        </div>
        </el-card>
        <button hidden="hidden" id="shibie1" @click="shibie()"></button>
    </div>
</template>

<script>
import { RequestSfyz } from '@/api/sfyz'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'sfyz',
  directives: {
    waves
  },
  data() {
    return {
      sfzImg: '/static/image/zpbj.jpg',
      tableKey: 0,
      list: null,
      listQuery: {
        qsSfz: undefined,
      },
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
  	getList() {
      RequestSfyz(this.listQuery).then((res) => {
      	 this.list = res.jlFrList
      	 this.qs.state = res.state
      }).catch(error => {
      })
    },
    handleFilter() {
      this.getList()
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
    	var IDCard2=document.getElementById("IDCard2");
    	
		IDCard2.SetPhotoName(2);
		//let a = IDCard2.Base64Photo;
		//document.getElementById("base64").value=a;
		this.qs.qsSfz = IDCard2.CardNo
		this.qs.qsName = IDCard2.NameA
		this.qs.dz = IDCard2.Address
		this.qs.xb = IDCard2.Sex==2?'女':'男'
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