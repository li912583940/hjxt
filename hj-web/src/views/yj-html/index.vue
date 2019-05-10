<template>
  <div class="app-container">
    <el-button type="primary" size="mini" @click="toYjHtml()">前往应急网页</el-button>
  </div>
</template>

<script>

import moment from 'moment';
import waves from '@/directive/waves' // 水波纹指令
import { Message, MessageBox } from 'element-ui'

export default {
  name: 'yjHtml',
  directives: {
    waves
  },
  data() {
    return {
      
      
    }
  },
  filters: {
   
  },
  created() {
   	this.toYjHtml()
  },
  methods: {
 	toYjHtml(){
 		window.open('http://10.44.69.5:9009/HjSystem', '_blank',
                'width=' + (window.screen.availWidth - 100) +
                ',height=' + (window.screen.availHeight - 100) +
                ',top=50,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');

 	},
    
  }
}
</script>
