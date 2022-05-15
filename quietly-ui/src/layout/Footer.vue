<script setup>
import { Icon } from '@vicons/utils'
import {QqCircleFilled} from '@vicons/antd'
import {EmailFilled} from '@vicons/material'

const currentYear=new Date().getFullYear()
</script>

<template>
  <div>
      <span style="color: #f313b9; font-size: 20px; width: 100px; margin-right: 20px;">QuickBoot UI</span>
      <span>联系作者: </span>
      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1002284406&site=qq&menu=yes" style="margin-right: 10px;">
          <Icon><QqCircleFilled /></Icon>
      </a>
      The Apache License 2.0 Copyright © 2018~<span>{{currentYear}}</span> Meng Wei Jin
		  
      <a href="mailto:mengweijin.work@foxmail.com" style="color:black; text-decoration:none;">
        <Icon color="black" size="16"><EmailFilled /></Icon>:mengweijin.work@foxmail.com
      </a>
  </div>
</template>

<script>
export default {
  name: 'Footer'
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* svg 字体图标默认位置偏上，无法与文字对齐，这里设置一下 */
svg {
    position: relative; 
    top: 4px;
}

</style>