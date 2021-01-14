<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item label="日期">
        <el-date-picker v-model="day" type="date" placeholder="选择要统计的日期" value-format="yyyy-MM-dd" />
      </el-form-item>


      <el-button :disabled="btnDisabled" plain="true" type="primary" @click="create()">生成</el-button>
    </el-form>

  </div>
</template>
<script>
  //引入statistics.js文件
  import statisticsApi from '@/api/sta/statistics'
  export default {
    data() {
      return {
        day: '',
        btnDisabled: false
      }
    },
    created() {

    },
    methods: {
      create() {
        statisticsApi.createStaData(this.day)
          .then(response => {
            //提示信息
            this.$message({
              type: 'success',
              message: '生成数据成功! 😊'
            })
            //跳转到图表显示页面
            this.$router.push({
              path: '/statistics/showLog'
            })
          })
      }
    }
  }
</script>
