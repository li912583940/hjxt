<template>
  <div class="app-container">
    <div class="filter-container">
    	<el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员编号" v-model="listQuery.frNo">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入服刑人员姓名" v-model="listQuery.frName">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="输入亲属姓名" v-model="listQuery.qsName">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">{{$t('criminal.search')}}</el-button>
      <el-button v-if="buttonRole.addPermission==1" class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">添加会见登记</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row
      style="width: 100%">
      <el-table-column width="140" align="center"  label="监区">
        <template slot-scope="scope">
          <span>{{scope.row.jqName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见编号">
        <template slot-scope="scope">
          <span>{{scope.row.hjIndex}}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="罪犯编号">
        <template slot-scope="scope">
          <span>{{scope.row.frNo}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="罪犯姓名">
        <template slot-scope="scope">
          <span>{{scope.row.frName}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="亲属">
        <template slot-scope="scope">
          <span>{{scope.row.qsInfo1}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见时长">
        <template slot-scope="scope">
          <span>{{scope.row.hjTime}}分钟</span>
        </template>
      </el-table-column>
      <el-table-column width="180" align="center" label="登记时间">
        <template slot-scope="scope">
          <span>{{scope.row.djTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="登记人">
        <template slot-scope="scope">
          <span>{{scope.row.djUser}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="座位">
        <template slot-scope="scope">
          <span>{{scope.row.zw}}</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见状态">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0">未完成</span>
          <span v-else-if="scope.row.state==1">已完成</span>
          <span v-else-if="scope.row.state==2">已取消</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见类型">
        <template slot-scope="scope">
          <span v-if="scope.row.hjType==1">电话会见</span>
          <span v-if="scope.row.hjType==2">面对面会见</span>
          <span v-if="scope.row.hjType==3">视频会见</span>
          <span v-if="scope.row.hjType==4">帮教</span>
          <span v-if="scope.row.hjType==5">提审</span>
        </template>
      </el-table-column>
      <el-table-column width="140" align="center" label="会见说明">
        <template slot-scope="scope">
          <span>{{scope.row.hjInfo}}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="buttonRole.printXpPermission==1 || buttonRole.cancelDjPermission==1" align="center" :label="$t('criminal.actions')" width="240" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="buttonRole.printXpPermission==1" type="primary" size="mini" icon="el-icon-download" @click="printXp(scope.row)">打印小票</el-button>
          <el-button v-if="buttonRole.cancelDjPermission==1" type="danger"  size="mini" icon="el-icon-delete" @click="handleDelete(scope.row)">取消登记</el-button>
        </template>
      </el-table-column>
    </el-table>

		<!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.pageNum" :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>


    <!-- 打印小票 -->
    <el-dialog title="" :visible.sync="dialogFormVisible">
      <div id="wrap" class="wrap">
		  	<span v-for="x in this.printList">
		  	  <li>{{ x}}</li>
		  	</span>
		  </div>
      <div slot="footer" class="dialog-footer">
      	<el-button type="primary" @click="print">打 印</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        
      </div>
    </el-dialog>
    
  </div>
</template>

<script>
import { findPojo, RequestPrintXp, RequestCancelDj } from '@/api/meetRegister'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { parseTime } from '@/utils'

export default {
  name: 'meetRegister',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        frNo: undefined,
        frName: undefined,
        qsName: undefined
      },
      
      dialogFormVisible: false,
      printList : [],
      
      //按钮权限   1：有权限， 0：无权限
      buttonRole: { 
      	queryPermission: 1, 
      	addPermission: 0,
      	printXpPermission: 0,
      	cancelDjPermission: 0
      }
      
    }
  },
  filters: {
  	dateFormat(date) {
		  //时间格式化  
	    if (date == undefined) {  
	      return "";  
	    }  
	    return moment(date).format("YYYY-MM-DD HH:mm:ss");  
	  }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.setButtonRole()
  },
  methods: {
    getList() {
      this.listLoading = true
      if(!this.listQuery.frName){
      	this.listQuery.frName = undefined
      }
      if(!this.listQuery.frNo){
      	this.listQuery.frNo = undefined
      }
      if(!this.listQuery.qsName){
      	this.listQuery.qsName = undefined
      }
      findPojo(this.listQuery).then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      	 this.listLoading = false
      }).catch(error => {
          this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val
      this.getList()
    },
    
    setButtonRole() { //设置按钮的权限
    	let roles = sessionStorage.getItem("roles")
    	if(roles.includes('admin')){
    		this.buttonRole.addPermission= 1
    		this.buttonRole.printXpPermission= 1
    		this.buttonRole.cancelDjPermission= 1
    	}else{
    		let buttonRoles = JSON.parse(sessionStorage.getItem("buttonRoles"))
    		let meetRegister = buttonRoles.meetRegister
    		if(meetRegister != undefined && meetRegister.length>0){
    			for(let value of meetRegister){
    				if(value=='addPermission'){
    					this.buttonRole.addPermission= 1
    				}else if(value=='printXpPermission'){
    					this.buttonRole.printXpPermission= 1
    				}else if(value=='cancelDjPermission'){
    					this.buttonRole.cancelDjPermission= 1
    				}
    			}
    		}
    	}
    },
    
    // 添加新的会见等级， 跳转至会见等级页面
    handleCreate() {
    	this.$router.push({ path: '/addHjDj' })
    },
    // 打印小票
    printXp(row) {
    	let param = {
    		hjid: row.hjid
    	}
    	RequestPrintXp(param).then((res) => {
          this.printList = res.list
      }).catch(error => {
	    })
			this.dialogFormVisible = true
    },
    print(){
    	var newstr = document.getElementsByClassName('wrap')[0].innerHTML
    	document.body.innerHTML = newstr
      window.print()
      // 重新加载页面，以刷新数据
      window.location.reload()
    },
    //取消登记
		handleDelete(row) {
			this.$confirm('确认取消该会见吗?', '提示', {
				type: 'warning'
			}).then(() => {
				this.listLoading = true;
				let param = {
	    		id: row.hjid
	    	}
				RequestCancelDj(param).then((res) => {
	    		if(res.data == 0) {
          	this.$notify({
		          title: '成功',
		          message: '会见登记取消成功',
		          position: 'top-right',
		          type: 'success'
		        });
          }else if(res.data == 1) {
          	this.$notify({
		          title: '警告',
		          message: '已处会见通话状态，无法取消',
		          position: 'top-right',
		          type: 'warning'
		        });
          }
          this.getList()
	    	}).catch(error => {
	    		this.listLoading = false;
	      })
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
