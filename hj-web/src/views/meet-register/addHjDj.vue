<!-- 会见登记 -->
<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="frListQuery.frNo" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="frListQuery.frName" clearable>
      </el-input>
      <el-select clearable style="width: 200px" class="filter-item" v-model="frListQuery.jq" placeholder="选择监区">
        <el-option v-for="item in jqs" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属身份证" v-model="frListQuery.qsSfz" clearable>
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属姓名" v-model="frListQuery.qsName" clearable>
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button class="filter-item" type="success" v-waves  @click="addHjdj">提交登记</el-button>
      <el-button class="filter-item" type="primary" v-waves  @click="returnPrevious">返回</el-button>
      <!--<el-button class="filter-item" type="primary" v-waves  @click="colsePort">删除节点</el-button>-->
    </div>
    
    <div class="filter-container">
      <el-select style="width: 200px"  v-model="formdata.hjType" placeholder="选择会见类型">
        <el-option v-for="item in hjTypes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-select style="width: 200px" v-model="formdata.hjMode" placeholder="选择会见方式">
        <el-option v-for="item in hjModes" :key="item.id" :label="item.name" :value="item.id">
        </el-option>
      </el-select>
      <el-input style="width: 200px;"  placeholder="输入会见说明" v-model="formdata.hjInfo" clearable>
      </el-input>
      
    </div>
    
		<!-- 服刑人员开始 -->
		<el-card class="box-card">
	    <el-table :key='frTableKey' ref="frMultipleTable" :data="frList" v-loading="frListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	       @row-click="frRowClick" @row-dblclick="handleSearchQs" @select="frSelectionChang" @select-all="frAllSelectionChang" style="width: 100%">
	      <el-table-column align="center" type="selection" width="70" fixed="left">
	      </el-table-column>
	      <el-table-column align="center" label="监区" width="70">
	        <template slot-scope="scope">
	          <span>{{scope.row.jqName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="100px" align="center" label="罪犯编号">
	        <template slot-scope="scope">
	          <span>{{scope.row.frNo}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="100px" align="center" label="罪犯姓名">
	        <template slot-scope="scope">
	          <span>{{scope.row.frName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="本月已见次数">
	        <template slot-scope="scope">
	          <span>{{scope.row.hjUse}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="上次会见时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.hjLastTime}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="上次会见家属信息">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="160" align="center" label="入监时间">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoRjsj}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="90px" align="center" label="分管等级">
	        <template slot-scope="scope">
	          <span>{{scope.row.jbName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="刑期">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoXq}}</span>
	        </template>
	      </el-table-column>
				<el-table-column width="150px" align="center" label="罪名">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoZm}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="320px" align="center" label="家庭住址">
	        <template slot-scope="scope">
	          <span>{{scope.row.infoHome}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="上次会见所在监区">
	        <template slot-scope="scope">
	          <span>{{scope.row.formerJQName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="90px" align="center" label="重点罪犯">
	        <template slot-scope="scope">
	          <span v-if="scope.row.stateZdzf=='0'">否</span>
	          <span v-if="scope.row.stateZdzf=='1'">是</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="备注">
	        <template slot-scope="scope">
	          <span>{{scope.row.zdzfType}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="国籍">
	        <template slot-scope="scope">
	          <span>{{scope.row.frGj}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="是否禁止/禁止时间">
	        <template slot-scope="scope">
	          <span v-if="scope.row.hjJb=='-1'">是/{{scope.row.hjStopTime}}</span>
	          <span v-if="scope.row.hjJb!='-1'">否</span>
	        </template>
	      </el-table-column>
	      <el-table-column v-if="buttonRole.addQsPermission==1" align="center" :label="$t('criminal.actions')" width="150"  fixed="right">
	        <template slot-scope="scope">
	          <el-button v-if="buttonRole.addQsPermission==1" type="primary" size="mini" @click="handleAddQs(scope.row)">添加亲属</el-button>
	          </el-button>
	        </template>
	      </el-table-column>
	    </el-table>
	
	    <div class="pagination-container">
	      <el-pagination background @size-change="handleFrSizeChange" @current-change="handleFrCurrentChange" :current-page="frListQuery.pageNum" :page-sizes="[5,10]" :page-size="frListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="frTotal">
	      </el-pagination>
	    </div>
	  </el-card>
		<!-- 服刑人员结束 -->
	
		<!-- 亲属开始 -->
		<el-card class="box-card">
	    <el-table :key='qsTableKey' ref="qsMultipleTable" :data="qsList" v-loading="qsListLoading" element-loading-text="给我一点时间" border fit highlight-current-row
	     @selection-change="qsAllSelectionChange"  @row-click="qsRowClick" @row-dblclick="toEditQs" style="width: 100%">
	      <el-table-column align="center" type="selection"  width="70" fixed="left">
	      </el-table-column>
	      <el-table-column align="center" label="亲属姓名" width="100">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsName}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column align="center" label="亲属相片" width="100">
	        <template slot-scope="scope">
	          <span><img :src="sfzImg" id="zp" name="zp" width="25px" height="31px" @click="clickImg($event)"/></span>
	        </template>
	      </el-table-column>
	      <el-table-column width="100px" align="center" label="关系">
	        <template slot-scope="scope">
	          <span>{{scope.row.gx}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="100px" align="center" label="证件类别">
	        <template slot-scope="scope">
	          <span v-if="scope.row.qsZjlb==1">身份证</span>
	          <span v-if="scope.row.qsZjlb==2">警官证</span>
	          <span v-if="scope.row.qsZjlb==3">工作证</span>
	          <span v-if="scope.row.qsZjlb==4">其他</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="证件号码">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsSfz}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="150px" align="center" label="证件物理号">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsSfzWlh}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="IC卡编号">
	        <template slot-scope="scope">
	          <span>{{scope.row.qsCard}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="90" align="center" label="性别">
	        <template slot-scope="scope">
	          <span>{{scope.row.xb}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="120px" align="center" label="附件">
	        <template slot-scope="scope">
	           <span v-if="scope.row.enclosureUrl != undefined">
	           	  <el-button type="primary"  size="mini" v-waves icon="el-icon-download" @click="wordDownload(scope.row)" >下载附件</el-button>
	           </span>
	           <span else></span>
	        </template>
	      </el-table-column>
	      <el-table-column width="320px" align="center" label="地址">
	        <template slot-scope="scope">
	          <span>{{scope.row.dz}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="110px" align="center" label="电话">
	        <template slot-scope="scope">
	          <span>{{scope.row.tele}}</span>
	        </template>
	      </el-table-column>
				<el-table-column width="127px" align="center" label="备注">
	        <template slot-scope="scope">
	          <span>{{scope.row.bz}}</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="100px" align="center" label="审批状态">
	        <template slot-scope="scope">
	          <span v-if="scope.row.spState==1">已通过</span>
	          <span v-if="scope.row.spState==0">未通过</span>
	        </template>
	      </el-table-column>
	      <el-table-column width="140px" align="center" label="是否禁止/禁止时间">
	        <template slot-scope="scope">
	          <span v-if="scope.row.hjStopTime!=null">是/{{scope.row.hjStopTime}}</span>
	          <span v-if="scope.row.hjStopTime==null">否/{{scope.row.hjStopTime}}</span>
	        </template>
	      </el-table-column>
	      
	    </el-table>
	
	    <!--<div class="pagination-container">
	      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="qsListQuery.pageNum" :page-sizes="[5,10]" :page-size="qsListQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="qsTotal">
	      </el-pagination>
	    </div>-->
	  </el-card>
    
    <!-- 亲属结束 -->
    <button hidden="hidden" id="shibie1" @click="shibie()"></button>
    <!-- 放大图片 -->
    <big-img v-if="showImg" @clickit="viewImg" :imgSrc="imgSrc"></big-img>
  </div>
</template>

<script>
import { findFrPojo, findQsPojo, findJqList, RequestAddHjdj, WordDownload } from '@/api/meetRegister'
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'
import BigImg from './components/BigImg'

export default {
  name: 'addHjDj',
  directives: {
    waves
  },
  data() {
    return {
      frTableKey: 0,
      frList: null,
      frTotal: null,
      frListLoading: true,
      frListQuery: {
        pageNum: 1,
        pageSize: 5,
        frNo: undefined,
        frName: undefined,
        jq: undefined,
        qsSfz: undefined, // 亲属身份证
        qsName: undefined // 亲属姓名
      },
      
      
      sfzImg: '/static/image/zpbj.jpg',
      showImg: false,
      imgSrc: '',
        
      qsTableKey: 1,
      qsList: null,
      qsTotal: null,
      qsListLoading: false,
      qsListQuery: {
        pageNum: 1,
        pageSize: 10,
        frNo: undefined
      },
      
      jqs: [ // 监区下拉选框
      
      ],
      
      formdata: {// 提交会见登记表单
      	frNo: undefined,
      	qsIds: [],
      	hjType: 1,
      	hjMode: 1,
      	hjInfo: undefined
      },
      qsSelections: [],
      
      scriptAddHjDj : undefined, //身份证读卡器事件节点
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	addQsPermission: 0
      },
      
      hjTypes:[
        {
      		id: 1,
      		name: '亲属会见'
      	},
      	{
      		id: 2,
      		name: '监护人会见'
      	},
      	{
      		id: 3,
      		name: '律师会见'
      	},
      	{
      		id: 4,
      		name: '使领馆探视'
      	},
      	{
      		id: 5,
      		name: '提审会见'
      	},
      	{
      		id: 6,
      		name: '公务会见'
      	},
      	{
      		id: 9,
      		name: '特批会见'
      	},
      	{
      		id: 99,
      		name: '其他会见'
      	},
      ],
      
      hjModes: [
      	{
      		id: 1,
      		name: '隔离会见'
      	},
      	{
      		id: 2,
      		name: '非隔离会见'
      	},
      	{
      		id: 3,
      		name: '远程视频会见'
      	},
      	{
      		id: 9,
      		name: '其他方式'
      	},
      ],
      
      frNoQuery: this.$route.query.frNoQuery,
    }
  },
  components: {
      'big-img': BigImg
    },
  filters: {

  },
  created() {
   
  },
  mounted() {
  		this.setButtonRole()
  		
      //this.getFrList()
      this.noFrSearch()
      
      this.getJqList()
      
      this.openPort()
  },

  destroyed(){
  	this.colsePort()
  },
  methods: {
  	noFrSearch() {
  		this.total=0
  		if(this.frNoQuery!= undefined){
  			let param = {
  				frNo: this.frNoQuery
  			}
  			findFrPojo(param).then((res) => {
	      	 this.frList = res.pojo.list
	      	 this.frTotal = res.pojo.count
	      	 this.frListLoading = false

						let qsparam={
							 frNo:this.frNoQuery
						}
						findQsPojo(qsparam).then((res) => {
			      	 this.qsList = res.pojo.list
			      	 this.qsTotal = res.pojo.count
			      	 this.qsListLoading = false
			      }).catch(error => {
			         this.qsListLoading = false
			      })
	      }).catch(error => {
	          this.frListLoading = false
	      })
  			this.getQsFrList()
  		}
  		this.frListLoading = false
  	},
    getFrList() {
      this.frListLoading = true
      if(!this.frListQuery.frName){
      	this.frListQuery.frName = undefined
      }
      if(!this.frListQuery.frNo){
      	this.frListQuery.frNo = undefined
      }
      if(!this.frListQuery.jq){
      	this.frListQuery.jq = undefined
      }
      if(!this.frListQuery.qsSfz){
      	this.frListQuery.qsSfz = undefined
      }
      if(!this.frListQuery.qsName){
      	this.frListQuery.qsName = undefined
      }
      findFrPojo(this.frListQuery).then((res) => {
      	 this.frList = res.pojo.list
      	 this.frTotal = res.pojo.count
      	 this.frListLoading = false
      	 if( res.pojo.count ==1){ //默认查询第一个罪犯的亲属信息
      	   this.qsListQuery.frNo = res.pojo.list[0].frNo
      	   this.getQsFrList()
      	 }else{
      	 	this.qsList = null
      	 	this.qsTotal = null
      	 }
      }).catch(error => {
          this.frListLoading = false
      })
    },
    getQsFrList() {
      this.qsListLoading = true
      if(!this.qsListQuery.frNo){
      	this.qsListQuery.frNo = undefined
      }
      findQsPojo(this.qsListQuery).then((res) => {
      	 this.qsList = res.pojo.list
      	 this.qsTotal = res.pojo.count
      	 this.qsListLoading = false
      }).catch(error => {
         this.qsListLoading = false
      })
    },
    handleFilter() { // 罪犯查询
      this.frListQuery.pageNum = 1
      this.getFrList()
    },
    handleFrSizeChange(val) { // 罪犯分页
      this.frListQuery.pageSize = val
      this.getFrList()
    },
    handleFrCurrentChange(val) { // 罪犯分页
      this.frListQuery.pageNum = val
      this.getFrList()
    },
    handleQsSizeChange(val) { // 亲属分页
      this.qsListQuery.limit = val
      this.getQsList()
    },
    handleQsCurrentChange(val) { // 亲属分页
      this.qsListQuery.page = val
      this.getQsList()
    },
    
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addQsPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetRegister = buttonRoles.meetRegister
    		if(meetRegister.length>0){
    			for(let value of meetRegister){
    					if(value=='addQsPermission'){
    					this.buttonRole.addQsPermission= 1
    				}
    			}
    		}
    	}
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
			console.log(str)
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
			console.log(this.scriptAddHjDj)
			document.getElementById("IDCard2").SetReadType(0);
		//	document.getElementById("WM1711").FunCloseCard();
		},
		
    addHjdj() { //提交会见登记
    	if(!this.formdata.frNo) {
    		Message({
	        message: '提交登记时，必须选择一位服刑人员',
		      type: 'error',
		      duration: 5 * 1000
	      });
	      return false;
    	}
    	if(this.qsSelections.length == 0) {
    		Message({
	        message: '提交登记时，至少选择一位家属',
		      type: 'error',
		      duration: 5 * 1000
	      });
	      return false;
    	}
    	let qsid = ''
    	for(let x of this.qsSelections) {
    		qsid = qsid==''?x.webId:qsid+','+x.webId
    	}
    	this.formdata.qsIds = qsid
    	const loading = this.$loading({
	      lock: true,
	      text: 'Loading',
	      spinner: 'el-icon-loading',
	      background: 'rgba(0, 0, 0, 0.7)'
	    })
    	RequestAddHjdj(this.formdata).then((res) => {
    		loading.close();
        Message({
	        message: res.errMsg,
		      type: 'success',
		      duration: 5 * 1000
	      });
      	this.frListQuery.frName = undefined
      	this.frListQuery.frNo = undefined
      	this.frListQuery.jq = undefined
      	this.frListQuery.qsSfz = undefined
      	this.frListQuery.qsName = undefined
    		//history.go(-1) 
    		//let timestamp=new Date().getTime()
    		//this.$router.push({ path: '/meetRegister/index', query: {refreshZ:timestamp} })
    	}).catch(error =>{
    		loading.close();
    	})
    },
    returnPrevious(){ // 返回上一页
    	let timestamp=new Date().getTime()
    	this.$router.push({ path: '/meetRegister/index', query: {refreshZ:timestamp} })
    	//history.go(-1)
    },
    handleSearchQs(row) { //双击罪犯表格查询家属
    	this.qsListQuery.frNo = row.frNo
    	this.getQsFrList()
    },
    // 罪犯与家属多选框事件
    frSelectionChang(rows,row){
    	this.$refs.frMultipleTable.clearSelection();
    	this.$refs.frMultipleTable.toggleRowSelection(row);
    },
    frAllSelectionChang(){
    	this.$refs.frMultipleTable.clearSelection();
    },
    frRowClick(row){ //单机罪犯表格某一行， 查询家属信息
    	this.$refs.frMultipleTable.clearSelection();
    	this.$refs.frMultipleTable.toggleRowSelection(row);
    	
    	this.qsListQuery.frNo = row.frNo
    	this.getQsFrList()
    	
    	this.formdata.frNo= row.frNo 
    	this.qsSelections = []
    },
    qsRowClick(row){ // 单击亲属表格得某一行  让多选框处于选中事件
      this.$refs.qsMultipleTable.toggleRowSelection(row);
    },
  	qsAllSelectionChange(rows){ // 亲属表格 多选框选中触发这个事件
  		this.qsSelections = rows;
  	},
  	clickImg(e) {
	    this.showImg = true
	    // 获取当前图片地址
	    this.imgSrc = e.currentTarget.src
    },
	  viewImg() {
	    this.showImg = false
	  },
  	toEditQs(row){ // 双击亲属行进入编辑亲属的页面
  		this.$router.push({ path: '/addQs', query: {frNo: row.frNo, frName:row.frName, qsWebId:row.webId} })
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
  		console.log('shibie start')
    	var IDCard2=document.getElementById("IDCard2");
    	console.log(IDCard2.CardNo)
		  IDCard2.SetPhotoName(2);
		  //let a = IDCard2.Base64Photo;
		//document.getElementById("base64").value=a;
		  this.frListQuery.qsSfz = IDCard2.CardNo
		  this.frListQuery.qsName = IDCard2.NameA
//		  document.getElementById("sfzzzz").value=b;
//	  	document.getElementById("qsName").value=IDCard2.NameA;
//	  	document.getElementById("qsSfz").value=IDCard2.CardNo;
//	  	document.getElementById("dz").value=IDCard2.Address;
	  	//var sex=IDCard2.Sex;
	  	this.getFrList() //查询罪犯信息
	  	
  	},
  	handleAddQs(row) { //打开亲属
    	this.$router.push({ path: '/addQs', query: {frNo: row.frNo, frName:row.frName} })
		},
		
		wordDownload(row){
    	let param ={
    		enclosureUrl: row.enclosureUrl
    	}
    	let filename= row.enclosureUrl
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

    
    
  }
  
   
}


/*<script for="IDCard2" event="CardIn( State );">
{ 
if(State == 1)
{
	consol.log(1334)
		var IDCard2=document.getElementById("IDCard2");
		  	  	IDCard2.SetPhotoName(2);
		  	  	var a = IDCard2.Base64Photo;
		//	  	  	document.getElementById("base64").value=a;
		  	  	var b = IDCard2.CardNo;
		  	  	document.getElementById("sfzzzz").value=b;
		  	document.getElementById("qsName").value=IDCard2.NameA;
		  	document.getElementById("qsSfz").value=IDCard2.CardNo;
		  	document.getElementById("dz").value=IDCard2.Address;
		  	var sex=IDCard2.Sex;
		  	if(sex==2){
		 	for(var i=0;i<xb.length;i++){
		if(xb.options[i].value=='女'){
		xb.options[i].selected=true;
		break;
		}
		}
		  	}else{
		  	for(var i=0;i<xb.length;i++){
		if(xb.options[i].value=='男'){
		xb.options[i].selected=true;
		break;
		}
		}
		  	}
//		a=encodeURIComponent(encodeURIComponent(a));
//		  	$.ajax({
//		      type:"POST",
//		      url:"hjdj.do",
//		      data:"method=GenerateImage&sfzzBase64="+a+"&sfz="+b,
//		      dataType:"json",
//		      success:aaaa,
//		      error:bbbb
//		});
}
}
   <script>*/


</script>

<style>
.box-card {
  margin: 10px;
}
 
</style>