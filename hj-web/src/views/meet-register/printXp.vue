<template>
  <div>
  	<span v-for="x in this.list">
  	  {{ x}}
  	</span>
  </div>
</template>

<script>
import {RequestPrintXp } from '@/api/meetRegister'


export default {
  name: 'printXp',
  data() {
    return {
      list : []
    }
  },
  filters: {
    
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
    		var url="/jlHjDj/printXp?hjid="+row.hjid;
		val=window.open(url,"","width=360,height=150,left=1120,top=720,dependent=yes,scroll:no,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,directories=no,status=no");

      this.listLoading = true
    		
      RequestPrintXp().then((res) => {
      	 this.list = res.pojo.list
      	 this.total = res.pojo.count
      	 this.listLoading = false
      }).catch(error => {
          this.listLoading = false
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