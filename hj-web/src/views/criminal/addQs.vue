<template>
	<div class="app-container">
		<!-- 亲属新增或编辑 -->
		<el-card shadow="always" style="width: 50%; margin-left: 25%;">
			<el-form :rules="rulesQs" :model="dataQsForm" ref="dataQsForm" label-position="right" label-width="120px" style='width: 440px; margin-left:12%;' >
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
		        	<span v-if="ie==1">
		        		<video id="video" autoplay width="150" height="110" controls>
						</video>
						<canvas id="canvas" width="150" height="110"></canvas>
		        	</span>
		        	<span v-if="ie==0">
		        		<!-- IE浏览器 flash控件 调用摄像头 -->
				        
				        <!--<object style="z-index: 100" id="My_Cam" align="middle" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
					                codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"
					                height="400" viewastext="在线拍照" width="500">
					                <param name="allowScriptAccess" value="sameDomain" />
					                <param name="movie" value="../../js/MyCamera.swf" />
					                <param name="quality" value="high" />
					                <param name="bgcolor" value="#ffffff" />
					                <param name="wmode" value="transparent" />
					                <embed style="z-index: 100" align="middle" allowscriptaccess="sameDomain" bgcolor="#ffffff" height="400"
					                    name="My_Cam" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high"  wmode="transparent"
					                    src="../../js/MyCamera.swf" type="application/x-shockwave-flash" width="500"></embed>
					   	</object>-->
		        		<span id="zp" style="width:160,height:176"></span>
		        	</span>
					<div>
					  <button id="capture" @click="paizhao">拍照</button>
					</div>
		        </el-form-item>
		        <el-form-item label="证件号码" prop="qsSfz">
		          <el-input v-model="dataQsForm.qsSfz"></el-input>
		        </el-form-item>
		        <el-form-item label="罪犯编号" prop="frNo">
		          <el-input v-model="dataQsForm.frNo"></el-input>
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
        
        
		<button hidden="hidden" id="shibie1" @click="shibie()"></button>
    </div>
</template>

<script>
import { findQsPojo, findQsOne, RequestQsAdd, RequestQsEdit, RequestQsDelete, findGxList } from '@/api/criminal'

export default {
  name: 'addQs',
  data() {
    return {
      sfzImg: '/static/image/zpbj.jpg',
      // 新增或编辑弹窗
      dataQsForm: { 
        webId: undefined,
        frNo: this.$route.query.frNo,
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
      scriptAddHjDj : undefined, //身份证读卡器时间节点
      
      ie: 0,
      track: undefined,
      
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
  	this.getGxList()
  	this.isIe()
  },
  mounted() {
    //this.openPort()
    this.openVideo()

  },
  destroyed(){
  	//this.colsePort()
  	//this.colseVideo()
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
          RequestQsAdd(this.dataQsForm).then(() => {
          	Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
            this.returnPrevious()
          }).catch(error => {
	      })
        }
      })
    },
    returnPrevious(){ // 返回上一页
    	this.$router.push({ path: '/criminal/relatives' })
    },
    handleQsUpdate(row) {
    	let param = {
    		id: row.webId
    	}
    	findQsOne(param).then((res) =>{
    	this.dataQsForm.webId = res.data.webId
        this.dataQsForm.frNo = this.$route.params.frNo
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
        this.dataQsForm.jzUrl = res.data.jzUrl
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
          	Message({
		        message: res.errMsg,
			    type: 'success',
			    duration: 5 * 1000
		    });
            this.dialogQsFormVisible = false
            this.getQsList()
          }).catch(error => {
		        this.dialogQsFormVisible = false
		      })
        }
      })
    },
    isIe() {
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){
    		this.ie=0
    	}else{
    		this.ie=1
    	}
    },
    
    openVideo(){
		let video = document.getElementById('video');
		if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1) ){ // IE浏览器
			document.getElementById("camera").start(160,176); // 打开flash拍照控件
		}else{ // 非IE浏览器
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
		}
	    

    },
    paizhao(){
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){ // IE浏览器
    		document.getElementById("camera").savefile("D:\\temp.jpg",150,176);
			this.dataQsForm.jzBase64=document.getElementById("camera").jpegbase64;
			//document.getElementById("jz").value = document.getElementById("MyFlexApps").paserbytes();
			//document.getElementById("zp").innerHTML="<img src=\"D:\\\\temp.jpg\"/>";
    	}else{
    		let video = document.getElementById('video');
		    let canvas = document.getElementById('canvas');
		    let context = canvas.getContext('2d');
		    context.drawImage(video, 0, 0, 150, 110); 
		      
			//从画布上获取照片数据
			var imgData = canvas.toDataURL("image/png");
			  
			//将图片转换为Base64
			//var BASE64= imgData.substr(22);
			this.dataQsForm.jzBase64= imgData.substr(22);
    	}
      
    },
    colseVideo() {
    	console.log("colse video")
    	document.getElementById("camera").stop()
    	
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