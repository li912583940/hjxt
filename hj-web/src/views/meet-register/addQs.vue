<template>
	<div class="app-container">
		<!-- 亲属新增或编辑 -->
		<el-card shadow="always" style="width: 50%; margin-left: 25%;">
			<el-form :rules="rulesQs" :model="dataQsForm" ref="dataQsForm" label-position="right" label-width="120px" style='width: 440px; margin-left:12%;' >
		        <el-form-item label="罪犯姓名" prop="frName">
		          <el-input v-model="dataQsForm.frName" :disabled="true"></el-input>
		        </el-form-item>
		        <el-form-item label="亲属姓名" prop="qsName">
		          <el-input v-model="dataQsForm.qsName"></el-input>
		        </el-form-item>
		        <el-form-item label="证件类别" prop="qsZjlb">
		          <el-select class="filter-item" v-model="dataQsForm.qsZjlb" placeholder="请选择">
		            <el-option v-for="item in qsZjlbs" :key="item.id" :label="item.name" :value="item.id"></el-option>
		          </el-select>
		        </el-form-item>
		        <el-form-item label="证件图像" prop="zp">
		          <img :src="sfzImg" id="zp" name="zp" width="100px" height="126px" />
		          <span v-if="dataQsForm.jzUrl!=null || dataQsForm.jzUrl!=undefined">
		          	<img src="dataQsForm.jzUrl"  width="100px" height="126px" />
		          </span>
		        </el-form-item>
		        <el-form-item label="近照" >
		         <video id="video" autoplay width="150" height="110" controls>
				</video>
				<canvas id="canvas" width="150" height="110"></canvas>
				<div>
				  <button id="capture" @click="paizhao">拍照</button>
				</div>
		        </el-form-item>
		        <el-form-item label="证件号码" prop="qsSfz">
		          <el-input v-model="dataQsForm.qsSfz"></el-input>
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
		        <el-form-item label="附件上传">
			        <el-upload
			    		ref="upload"
						  :action="wordPath"
						  :on-success="wordSuccess"
						  :on-error="wordError"
						  multiple>
						 <el-button icon="el-icon-upload2"  type="primary">上传附件</el-button>
					</el-upload>
					
				</el-form-item>
				<el-form-item label="附件下载">
		          <el-button v-if="uploadBoolean==1"  type="primary" v-waves icon="el-icon-download" @click="wordDownload" >下载附件</el-button>
		        </el-form-item>
		        <!--<el-form-item label="审批状态">
		          <el-input v-model="dataQsForm.spState" :disabled="true"></el-input>
		        </el-form-item>-->
		        <el-form-item label="备注" prop="bz">
		          <el-input v-model="dataQsForm.bz"></el-input>
		        </el-form-item>
		        <el-form-item>
				    <el-button @click="returnPrevious">返 回</el-button>
			        <el-button type="primary" @click="createQsData">确 定</el-button>
				</el-form-item>
	        </el-form>
	        
        </el-card>
        <button hidden="hidden" id="shibie1" @click="shibie()"></button>
    </div>
</template>

<script>
import { findQsPojo, findQsOne, RequestQsAdd, RequestQsEdit, RequestQsDelete, findGxList, WordDownload  } from '@/api/criminal'
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'addQs',
  data() {
    return {
      sfzImg: '/static/image/zpbj.jpg',
      wordPath: process.env.BASE_API+"/jlQs/uploadWord", //上传亲属附件
      // 新增或编辑弹窗
      dataQsForm: { 
        webId: this.$route.query.qsWebId,
        frNo: this.$route.query.frNo,
        frName: this.$route.query.frName,
        qsZjlb: 1,
        qsSfz: undefined,
        qsName: undefined,
        gx: undefined,
        qsCard: undefined,
        dz: undefined,
        xb: '男',
        tele: undefined,
        spState: undefined,
        bz: undefined,
        jzBase64: undefined,
        zpBase64: undefined,
        jzUrl: undefined,
        enclosureUrl: undefined,
      },
      uploadBoolean: 0,
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
        gx: [{ required: true, message: '亲属关系必选', trigger: 'blur' }],
        qsSfz: [{ required: true, message: '亲属身份证不能为空', trigger: 'blur' }]
      },
      scriptAddHjDj : undefined, //身份证读卡器时间节点
      
      photo: undefined,
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
  	this.qsWebIdSearch()
  },
  mounted() {
  	this.getGxList()
    this.openPort()
    //this.openVideo()
  },
  destroyed(){
  	this.colsePort()
  },
  methods: {
  	qsWebIdSearch(){ // 如果亲属webId不是undefined此页面就是修改亲属
  		if(this.dataQsForm.webId != undefined){
  			let param ={
  				id:this.dataQsForm.webId
  			}
  			findQsOne(param).then(res =>{
  				let x = res.data
  				this.dataQsForm.frName= x.frName
  				this.dataQsForm.qsZjlb = x.qsZjlb
  				this.dataQsForm.qsSfz = x.qsSfz
  				this.dataQsForm.qsName = x.qsName
  				this.dataQsForm.gx = x.gx
  				this.dataQsForm.qsCard = x.qsCard
  				this.dataQsForm.dz = x.dz
  				this.dataQsForm.xb = x.xb
  				this.dataQsForm.tele = x.tele
  				this.dataQsForm.spState = x.spState
  				this.dataQsForm.bz = x.bz
  				this.dataQsForm.jzUrl = x.jzUrl
  				this.dataQsForm.enclosureUrl = x.enclosureUrl
  				if(x.enclosureUrl){
  					this.uploadBoolean=1
  				}
  			})
  		}
  	},
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
        	if(this.dataQsForm.webId != undefined){
        		RequestQsEdit(this.dataQsForm).then(res => {
		          	Message({
				        message: res.errMsg,
					    type: 'success',
					    duration: 5 * 1000
				    });
		            this.returnPrevious()
		        }).catch(error => {
			    })
        	}else{
        		RequestQsAdd(this.dataQsForm).then(res => {
		          	Message({
				        message: res.errMsg,
					    type: 'success',
					    duration: 5 * 1000
				    });
		            this.returnPrevious()
		        }).catch(error => {
			    })
        	}
          
        }
      })
    },
    returnPrevious(){ // 返回上一页
    	let frNo = this.dataQsForm.frNo
    	this.$router.push({ path: '/addHjDj', query: {frNoQuery:frNo} })
    },
    
     openVideo(){
		let video = document.getElementById('video');
	    if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
	      if (navigator.mediaDevices.getUserMedia) {
	        //最新的标准API
	        //调用用户媒体设备, 访问摄像头
		      navigator.mediaDevices.getUserMedia({video : {width: 150, height: 110}}).then(function(stream){
			      //兼容webkit核心浏览器
			      var URL = window.URL || window.webkitURL;
			      //将视频流设置为video元素的源
			      //video.src = URL.createObjectURL(stream);
			      video.srcObject = stream;
			      video.play();
		    	 }).catch(function(error){
		      	console.log(error);
		    	 });
	      } else if (navigator.webkitGetUserMedia) {
	        //webkit核心浏览器
	        navigator.webkitGetUserMedia({video : {width: 150, height: 110}}, 
	        	function(stream){
	        		//兼容webkit核心浏览器
				      var URL = window.URL || window.webkitURL;
				      //将视频流设置为video元素的源
				      //video.src = URL.createObjectURL(stream);
				      video.srcObject = stream;
				      video.play();
	          }, 
	          function(error){
	          	console.log(error);
	          })
	      } else if (navigator.mozGetUserMedia) {
	        //firfox浏览器
	        navigator.mozGetUserMedia({video : {width: 150, height: 110}}, 
	        function(stream){
	        		//兼容webkit核心浏览器
				      var URL = window.URL || window.webkitURL;
				      //将视频流设置为video元素的源
				      //video.src = URL.createObjectURL(stream);
				      video.srcObject = stream;
				      video.play();
	          }, 
	          function(error){
	          	console.log(error);
	          });
	      } else if (navigator.getUserMedia) {
	        //旧版API
	        navigator.getUserMedia({video : {width: 150, height: 110}}, 
	        function(stream){
	        		//兼容webkit核心浏览器
				      var URL = window.URL || window.webkitURL;
				      //将视频流设置为video元素的源
				      //video.src = URL.createObjectURL(stream);
				      video.srcObject = stream;
				      video.play();
	          }, 
	          function(error){
	          	console.log(error);
	          });
	      }
	    } else {
	      alert('不支持访问用户媒体');
	    }

    },
    paizhao(){
      let video = document.getElementById('video');
      let canvas = document.getElementById('canvas');
      let context = canvas.getContext('2d');
      context.drawImage(video, 0, 0, 150, 110); 
      
	  //从画布上获取照片数据
	  var imgData = canvas.toDataURL("image/png");
	  
	  //将图片转换为Base64
	  //var BASE64= imgData.substr(22);
	  this.dataQsForm.jzBase64= imgData.substr(22);
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
		this.dataQsForm.qsSfz = IDCard2.CardNo
		this.dataQsForm.qsName = IDCard2.NameA
		this.dataQsForm.dz = IDCard2.Address
		this.dataQsForm.xb = IDCard2.Sex==2?'女':'男'
//		document.getElementById("sfzzzz").value=b;
		var zpAddress=IDCard2.PhotoName
		
		var image = new Image();
		image.src = zpAddress;
		//var canvas = document.createElement("canvas");
		var canvas = document.getElementById('canvas');
	    canvas.width = 100;
	    canvas.height = 126;
	    var ctx = canvas.getContext("2d");
	    ctx.drawImage(image, 0, 0, 100, 126);
	    var imgData = canvas.toDataURL("image/jpg");
		this.dataQsForm.zpBase64=imgData.substr(22);
		//document.getElementById("zp").src=zpAddress;
		this.sfzImg=zpAddress
		console.log(zpAddress)
  	},

    wordSuccess(res) {
    	if(res.errCode===0){
    		this.dataQsForm.enclosureUrl = res.data.relativeFilePath
    	}
    	this.$refs.upload.clearFiles()
    	Message({
        message: '附件已上传至服务器',
	      type: 'success',
	      duration: 5 * 1000
      });
      
    },
    wordError() {
    	this.$refs.upload.clearFiles()
    	Message({
        message: '附件上传失败，请检查文件是否为word文档！',
	      type: 'error',
	      duration: 5 * 1000
      });
    },
    wordDownload(){
    	let param ={
    		enclosureUrl: this.dataQsForm.enclosureUrl
    	}
    	let filename= this.dataQsForm.enclosureUrl
    	filename="亲属附件"+ filename.substring(filename.lastIndexOf("."))
    	WordDownload(param).then(res => {
	      var blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
	     	if (window.navigator && window.navigator.msSaveOrOpenBlob) { // IE浏览器
        	window.navigator.msSaveOrOpenBlob(blob, filename);
    		}else{ //非IE浏览器
	     		var downloadElement = document.createElement('a')
		     	var href = window.URL.createObjectURL(blob)
		     	downloadElement.href = href
		     	//downloadElement.download = '亲属附件.docx'
		     	downloadElement.download = filename
		     	document.body.appendChild(downloadElement)
		     	downloadElement.click()
	     		document.body.removeChild(downloadElement) // 下载完成移除元素
		     	window.URL.revokeObjectURL(href) // 释放掉blob对象
	     	}
	    }).catch(error => {
	        console.log(error)
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

<style>
</style>