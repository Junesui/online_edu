<template>
  <div class="app-container">
    <div align="center" style="line-height: 40px; font-weight: 700; font-size: 22px; color: #a85a11;">Banner列表</div>

    <!-- 表格 -->
    <el-table :data="list" v-loading="listLoading" element-loading-text="数据加载中" border fit highlight-current-row>

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="标题" width="80" />

      <el-table-column prop="imageUrl" label="图片地址" />

      <el-table-column prop="linkUrl" label="链接地址" width="200"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="sort" label="排序" width="60" />

      <!-- 操作按钮 -->
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/banner/update/'+scope.row.id">
            <el-button type="primary" plain="true" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" plain="true" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="getList" />
  </div>
</template>
<script>
  //引入调用banner.js文件
  import bannerApi from '@/api/cms/banner.js'
  export default {
    //写核心代码的位置
    data() { //定义变量和初始值
      return {
        list: null, //查询之后接口返回集合
        page: 1, //当前页
        limit: 10, //每页记录数
        total: 0, //总记录数
      }
    },
    created() { //页面渲染之前执行，调用method定义的方法
      //调用
      this.getList()
    },
    methods: { //创建具体的方法，调用teacher.js定义的方法
      getList(page = 1) { //banner列表的方法
        this.page = page
        bannerApi.getBannerList(this.page, this.limit)
          .then(response => { //请求成功
            //response接口返回的数据
            this.list = response.data.items
            this.total = response.data.total
          })
          .catch(error => { //请求失败
            console.log(error)
          })
      },

      removeDataById(id) { //删除banner按钮的方法
        this.$confirm('此操作将永久删除此Banner, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          bannerApi.deleteBannerById(id)
            .then(response => { //删除成功
              //提示信息
              this.$message({
                type: 'success',
                message: '删除成功🤭'
              });
              //回到列表页面
              this.getList()
            })
        })
          //点取消执行catch方法  用户体验角度
          //此处无需进行提示取消
          //框架在./utils/request.js封装好了提示error的方法
      }
    }
  }
</script>
