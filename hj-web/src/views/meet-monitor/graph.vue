<template>
	<div id="pro_form" style="position: relative;width: 100%;margin-top: 50px; margin-bottom: 50px;">
        <el-row :gutter="12">
            <el-col :span="4" v-for="(item, index) in list" :key="index" :offset="1" style="margin-bottom:40px">
                <el-card  :body-style="{ padding: '0px', height:'360px'}" shadow="always" style="width: 260px;height: 320px;border:2px solid #CDC9C9; max-height: 500px;">
                	<div slot="header" class="clearfix">
					    <span style="float: left;">窗口：{{item.zw}}</span>
					    <span v-if="item.monitorState=='通话'">
					    	<el-button v-if="jtState==2" style="float: right; padding: 3px 0;" type="text">切断</el-button>
					    	<el-button v-if="jtState==2" style="float: right; padding: 3px 0;margin-right: 6px;" type="text">停止监听</el-button>
					    	<el-button v-if="jtState==1" style="float: right; padding: 3px 0" type="text">监听</el-button>
					    </span>
					    <span v-else-if="item.monitorState=='应答'" style="float: right; padding: 3px 10; color: #FF9900;">应答</span>
					    <span v-else style="float: right; padding: 3px 10; color: #33CC00;">空闲</span>
					</div>
                    <div style="padding: 6px;height: 310px;">
                    	<div style=" background:rgba(0,0,0,.5); position:relative; top:0px; color:#FFF; font-size:12px; padding:6px 0;">
					    	<span style="margin-left: 50px;"><a @click="chahua(item)">插话</a></span>
					    	<span style="margin-left: 20px;"><a @click="zhushi(item)">注释</a></span>
					    	<span style="margin-left: 20px;"><a @click="xiugaiTime(item)">修改时间</a></span>
					    </div>
                    	<div v-if="item.monitorState=='' ">
	                        <div class="text item" style="margin-top: 10px;">监区：一监区 {{item.monitorJq}}</div>
						    <div class="text item">姓名：张三 {{item.monitorFr}}</div>
						    <span v-for="(o, i) in item.qsList">
						    	<div class="text item">亲属{{i+1}}：{{o}}</div>
						    </span>
						    <div class="text item">通话：4分钟20秒 {{item.monitorFr}}</div>
						    <div class="text item">剩余：26分钟40秒 {{item.monitorTime}}</div>
					    </div>
					    
					    
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <!-- 分页 -->
	    <div class="pagination-container" style="margin-left: 5%;">
	      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
	      </el-pagination>
	    </div>
    
    	<el-dialog title="修改时间" :visible.sync="dialogSJVisible">
	      <el-form :rules="rules" :model="dataForm" ref="dataForm" label-position="right" label-width="120px" style='width: 400px; margin-left:25%;' >
	        <el-form-item label="姓名" >
	          <el-input v-model="dataForm.frName" :disabled="true"></el-input>
	        </el-form-item>
	        <el-form-item label="时间" prop="timeUp" >
	          <el-input v-model="dataForm.timeUp" placeholder="10：延长10分钟，-10：减少10分钟"></el-input>
	        </el-form-item>
	      </el-form>
	      <div slot="footer" class="dialog-footer">
	        <el-button @click="dialogSJVisible = false">取 消</el-button>
	        <el-button type="primary" @click="updateSJ">确 定</el-button>
	      </div>
	    </el-dialog>
    	
    	<div v-for="(item, index) in sysHjServerList">
    		<object id="${item.serverName }" name="${item.serverName}" codebase="../../ocx/TeleQqOcx.ocx#version=1,0,0,1" classid="clsid:561E476B-6C4F-4FCC-A8CE-A85C7F781620" 
 		 		width="0" height="0">
			</object>
    	</div>
    	
	</div>
</template>

<script>
import { findPojo, UpdateSJ, FindHjServerList } from '@/api/meetMonitor'

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
      listQuery: {
        pageNum: 1,
        pageSize: 20
      },
      jtState: 1,
      
      dialogSJVisible: false,
      
      dataForm: {
      	webId: undefined,
      	frName: undefined,
      	timeUp: undefined
      },
      rules: {
        timeUp: [{ required: true, message: '时间不能为空', trigger: 'blur' }]
      },
      
      sysHjServerList: null,
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      })
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
	resetForm() {
		this.dataForm.webId = undefined
		this.dataForm.frName = undefined
		this.dataForm.timeUp = undefined
    },
	xiugaiTime(row){ //修改时间
		
		this.resetForm()
		this.dialogSJVisible =true
		this.dataForm.webId = row.webId
		this.dataForm.frName = row.monitorFr
	},
	updateSJ(){ //修改时间
		UpdateSJ(this.dataForm).then(res => {
		  Message({
          message: res.errMsg,
          type: 'success',
          duration: 5 * 1000
        });
		})
		this.dialogSJVisible =false
	},
	
	chahua(row){
		FindHjServerList({}).then(res => {
			
		})
	},
	
    dateFormat(row, column) {
			//时间格式化  
	    let date = row[column.property];  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
	},
	dateFormats(val) {
		if(!val){
			return undefined
		}
		return moment(val).format("YYYY-MM-DD HH:mm:ss");
	},
  }
}
</script>

<style>
  .text {
    font-size: 14px;
  }

  .item {
  	margin-left: 20px;
    margin-bottom: 15px;
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
