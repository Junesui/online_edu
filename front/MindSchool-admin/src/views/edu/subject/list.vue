<template>
  <div class="app-container">
    <!-- 检索功能 输入项 -->
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
//引入调用subject.js文件
import subjectApi from '@/api/edu/subject'
export default {

  data() {
    return {
      filterText: '',
      data2: [],   //返回所有分类的数据
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },

  created() {
    this.getAllSubjectList()
  },

  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    //课程列表分类的方法
    getAllSubjectList(){
      subjectApi.getSubjectList()
      .then(response => {
        this.data2 = response.data.list
      })
      .catch()
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1 //toLowerCase()不区分大小写
    }
  }
}
</script>
