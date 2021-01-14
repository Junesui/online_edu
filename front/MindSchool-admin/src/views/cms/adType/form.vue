<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">
      <el-form-item label="推荐位名称">
        <el-input v-model="adType.title" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" plain="true" @click="saveOrUpdate()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import adTypeApi from '@/api/cms/adType'

export default {
  data() {
    return {
      adType: {},
      saveBtnDisabled: false // 保存按钮是否禁用，防止表单重复提交
    }
  },

  // 页面渲染成功
  created() {
    if (this.$route.params.id) {
      this.fetchDataById(this.$route.params.id)
    }
  },

  methods: {

    saveOrUpdate() {
      // 禁用保存按钮
      this.saveBtnDisabled = true
      if (!this.adType.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      // debugger
      adTypeApi.save(this.adType).then(response => {
        this.$message.success(response.message)
        this.$router.push({ path: '/cms/adType/list' })
      })
    },

    // 根据id更新记录
    updateData() {
      // teacher数据的获取
      adTypeApi.updateById(this.adType).then(response => {
        this.$message.success(response.message)
        this.$router.push({ path: '/cms/adType/list' })
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      adTypeApi.getById(id).then(response => {
        this.adType = response.data.item
      })
    }
  }
}
</script>
