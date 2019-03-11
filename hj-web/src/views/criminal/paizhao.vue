<template>
	<div class="app-container">
		<!-- 亲属新增或编辑 -->
		<el-card shadow="always" style="width: 50%; margin-left: 25%;">
			<el-form :rules="rulesQs" :model="dataQsForm" ref="dataQsForm" label-position="right" label-width="120px" style='width: 440px; margin-left:12%;' >
		        <el-form-item label="近照" >
		        	<span v-if="ie==1">
		        		<video id="video" autoplay width="150" height="110" controls>
						</video>
						<canvas id="canvas" width="150" height="110"></canvas>
		        	</span>
		        	<!-- IE浏览器 flash控件 调用摄像头 -->
		        	<span v-if="ie==0">
		        		<span  id="zp" style="width:160,height:176"></span>
		        	</span>
					<div>
					  <button id="capture" @click="paizhao">拍照</button>
					</div>
		        </el-form-item>
		        <el-form-item>
				    <el-button @click="returnPrevious">返 回</el-button>
			        <el-button type="primary" @click="createQsData">确 定</el-button>
				</el-form-item>
	        </el-form>
	        
        </el-card>
        
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
        jzBase64: undefined,
        zpBase64: undefined,
        jzUrl: undefined,
      },

      ie: 0,
      
    }
  },
  filters: {

  },
  created() {
  	this.isIe()
  },
  mounted() {
    this.openVideo()
  },
  destroyed(){
  	this.colseVideo()
  },
  methods: {

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
    	this.$router.push({ path: '/addCriQs' })
    },

    isIe() {
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){
    		this.ie=0
    	}else{
    		this.ie=1
    	}
    },
    
    openVideo(){
		if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1) ){ // IE浏览器
			document.getElementById('zp').innerHTML='<object id=\"camera\" classid=\"clsid:792FD9B8-5917-45D2-889D-C49FD174D4E0\" codebase=\"../../ocx/capProj1.ocx#version=1,0,0,0\" width=160 height=176 hspace=0 vspace=0></object>';
            document.getElementById("camera").start(160,176); // 打开flash拍照控件
		}else{ // 非IE浏览器
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
		}
	    

    },
    paizhao(){
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){ // IE浏览器
    		document.getElementById("camera").savefile("D:\\temp.jpg",150,176);
    		console.log(12133)
    		this.dataQsForm.jzBase64=document.getElementById("camera").jpegbase64;
    		this.returnPrevious()
			//this.dataQsForm.jzBase64=document.getElementById("camera").jpegbase64;
			//document.getElementById("jz").value = document.getElementById("MyFlexApps").paserbytes();
			//document.getElementById("zp").innerHTML="<img src=\"D:\\\\temp.jpg\"/>";
//			window.onbeforeunload = function (e) {
//			  e = e || window.event;
//			
//			  // 兼容IE8和Firefox 4之前的版本
//			  if (e) {
//			    e.returnValue = '关闭提示';
//			  }
//			
//			  // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
//			  return '关闭提示';
//			};
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
    	if(navigator.appVersion.indexOf("MSIE") != -1 || (navigator.appVersion.toLowerCase().indexOf("trident") > -1 && navigator.appVersion.indexOf("rv") > -1)){ // IE浏览器
    		document.getElementById("zp").innerHTML='';
    	}
    	
    },



  }
}
</script>

<style>
</style>