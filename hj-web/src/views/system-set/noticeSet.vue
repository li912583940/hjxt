<template>
	<div id="" style="margin-left: 10%;margin-top: 40px;">
		<el-card shadow="never" style="width: 50%;">
			<el-switch
			  style="display: block"
			  v-model="noticeValue"
			  @change="noticeChange"
			  inactive-color="#ff4949"
			  active-color="#13ce66"
			  inactive-text="身份验证成功发起会见通知"
			  active-text="登记完成发起会见通知"
			 >
			</el-switch>
		</el-card>
	</div>
</template>

<script>
import { FindNotice, NoticeChange } from '@/api/noticeSet'

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令


export default {
  name: 'noticeSet',
  directives: {
    waves
  },
  data() {
    return {
      noticeValue: true,
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

  },
  destroyed() {
  },
  methods: {
    getList() {
      FindNotice().then((res) => {
      	let data = res.data
      	data.hjNotice==0?this.noticeValue=true:this.noticeValue=false
      	 
      })
    },
    noticeChange() {
    	let param ={
    		noticeValue: this.noticeValue==true?0:1
    	}
    	NoticeChange(param).then(res =>{
    		
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