<template>

  <div class="app-container">

    <div v-if="this.$route.params && this.$route.params.id"><h2 style="text-align: center;">修改课程信息</h2></div>
    <div v-else><h2 style="text-align: center;">发布新课程</h2></div>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <div>
      <el-button type="primary" @click="openChapterDialog()" icon="el-icon-edit" plain="true">添加章节</el-button>
    </div>

    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button style="" type="text" @click="openVideo(chapter.id)">添加课时</el-button>
            <el-button style="" type="text" @click="openEditChatper(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <el-tag v-if="!video.videoSourceId" size="mini" type="danger">
                {{ '尚未上传视频' }}
              </el-tag>
              <span class="acts">
                <!-- <el-tag v-if="video.isFree" size="mini" type="success">{{ '免费观看' }}</el-tag> -->
                <el-button style="" type="text" @click="openEditVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改小节表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree" >
            <el-radio :label="1">免费</el-radio>
            <el-radio :label="0">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
            <el-upload
            ref="upload"
            :auto-upload="false"
            :on-error="handleUploadError"
                :on-success="handleVodUploadSuccess"
                :on-remove="handleVodRemove"
                :before-remove="beforeVodRemove"
                :on-exceed="handleUploadExceed"
                :file-list="fileList"
                :action="BASE_API+'/eduvod/video/uploadAlyVideo'"
                :limit="1"
                class="upload-demo">
            <!-- <el-button size="small" type="primary">上传视频</el-button> -->
            <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
            <el-button
              :disabled="uploadBtnDisabled"
              style="margin-left: 10px;"
              size="small"
              type="success"
              @click="submitUpload()">上传</el-button>
            <el-tooltip placement="right-end">
                <div slot="content">最大支持1G，<br>
                    支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                    GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                    MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                    SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                <i class="el-icon-question"/>
            </el-tooltip>
            </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
  //引入调用chapter.js文件
  import chapterApi from '@/api/edu/chapter'
  //引入调用video.js文件
  import videoApi from '@/api/edu/video'
  export default {
    data() {
      return {
        saveBtnDisabled: false,
        courseId: '', //课程id
        chapterVideoList: [],
        chapter: { //封装章节数据
          title: '',
          sort: 0
        },
        video: {
          title: '',
          sort: 0,
          isFree: 1,
          videoSourceId: '',
          videoOriginalName: '', //视频名称
        },
        dialogChapterFormVisible: false, //章节弹框
        dialogVideoFormVisible: false ,//小节弹框
        fileList: [],//上传文件列表
        BASE_API: process.env.BASE_API ,// 接口API地址
        uploadBtnDisabled: false, //上传按钮
      }
    },

    created() {
      //获取路由中的课程id
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
        //根据课程id查询其章节和小节
        this.getChapterVideo()
      }
    },

    methods: {
      /* ---------------------------小节部分方法↓---------------------------------- */
      //添加小节按钮  弹出编辑框
      openVideo(chapterId) {
        //弹框
        this.dialogVideoFormVisible = true
        //表单数据清空
        this.video = {}
        this.fileList = []
        //设置章节id
        this.video.chapterId = chapterId
      },

      //添加小节
      addVideo() {
          //设置课程id
          this.video.courseId = this.courseId
          videoApi.addVideo(this.video)
              .then(response => {
                  //关闭弹框
                  this.dialogVideoFormVisible = false
                  //提示
                  this.$message({
                      type: 'success',
                      message: '添加小节成功! 😀'
                  });
                  //刷新页面
                  this.getChapterVideo()
              })
      },

      //修改小节
      updateVideo() {
        videoApi.updateVideo(this.video)
          .then(response => {
            //关闭弹框
            this.dialogVideoFormVisible = false
            //提示
            this.$message({
              type: 'success',
              message: '修改小节成功! 👍'
            });
            //刷新页面
            this.getChapterVideo()
          })
      },

      saveOrUpdateVideo(){
        if (!this.video.id) {
          this.addVideo()
        } else {
          this.updateVideo()
        }
      },

      //删除小节
      removeVideo(id) {
          this.$confirm('此操作将删除小节, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {  //点击确定，执行then方法
              //调用删除的方法
              videoApi.deleteVideo(id)
                  .then(response =>{//删除成功
                  //提示信息
                  this.$message({
                      type: 'success',
                      message: '删除小节成功!'
                  });
                  //刷新页面
                  this.getChapterVideo()
              })
          }) //点击取消，执行catch方法
      },

      //修改小节信息   弹框  数据回显
      openEditVideo(videoId) {
        //弹框
        this.dialogVideoFormVisible = true
        if (videoId) {
          videoApi.getVideo(videoId).then(response => {
            this.video = response.data.video
            // 回显
            if (this.video.videoOriginalName) {
              this.fileList = [{ 'name': this.video.videoOriginalName }]
            }
          })
        }
      },

      // 上传
      submitUpload() {
        this.uploadBtnDisabled = true// 禁用按钮
        this.$refs.upload.submit() // 提交上传请求
      },

      //上传视频成功调用的方法
      handleVodUploadSuccess(response, file, fileList) {
        this.uploadBtnDisabled = false
        if (response.success) {
          //上传视频id赋值
          this.video.videoSourceId = response.data.videoId
          //上传视频名称赋值
          this.video.videoOriginalName = file.name
        } else {
          this.$message.error('上传失败（非20000）')
        }
      },

      // 失败回调
      handleUploadError() {
        this.uploadBtnDisabled = false
        this.$message.error('上传失败（http）')
      },

      handleUploadExceed() {
          this.$message.warning('想要重新上传视频，请先删除已上传的视频')
      },

      //删除小节中的视频 点击×调用这个方法
      beforeVodRemove(file,fileList) {
          return this.$confirm(`确定移除 ${ file.name }？`);
      },

      //删除小节中的视频 点击确定调用的方法
      handleVodRemove() {
          //调用接口的删除视频的方法
          videoApi.deleteAlyVod(this.video.videoSourceId)
              .then(response => {
                  //提示信息
                  this.$message({
                      type: 'success',
                      message: '删除视频成功! 😄'
                  });
                  //把文件列表清空
                  this.fileList = []
                  //把video视频id和视频名称值清空
                  //上传视频id赋值
                  this.video.videoSourceId = ''
                  //上传视频名称赋值
                  this.video.videoOriginalName = ''
                  // 删除视频的同时更新video数据信息
                  videoApi.updateVideo(this.video)
                  this.$message.success(response.message)
              })
      },



      /* ---------------------------章节部分的方法↓---------------------------------- */
      //添加章节
      addChapter() {
        //设置课程id
        this.chapter.courseId = this.courseId
        chapterApi.addChapter(this.chapter)
          .then(response => {
            //关闭弹框
            this.dialogChapterFormVisible = false
            //提示
            this.$message({
              type: 'success',
              message: '添加章节成功! 😊'
            });
            //刷新页面
            this.getChapterVideo()
          })
      },

      //修改章节
      updateChapter() {
        chapterApi.updateChapter(this.chapter)
          .then(response => {
            //关闭弹框
            this.dialogChapterFormVisible = false
            //提示
            this.$message({
              type: 'success',
              message: '修改章节成功! 😂'
            });
            //刷新页面
            this.getChapterVideo()
          })
      },

      saveOrUpdate() {
        if (!this.chapter.id) {
          this.addChapter()
        } else {
          this.updateChapter()
        }
      },

      //删除章节
      removeChapter(chapterId) {
        this.$confirm('此操作将删除章节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { //点击确定，执行then方法
          //调用删除的方法
          chapterApi.deleteChapter(chapterId)
            .then(response => { //删除成功
              //提示信息
              this.$message({
                type: 'success',
                message: '删除成功! 😀'
              });
              //刷新页面
              this.getChapterVideo()
            })
        }) //点击取消，执行catch方法
        //此处无需进行提示取消
        //框架在./utils/request.js封装好了提示error的方法
      },

      //修改章节信息   弹框  数据回显
      openEditChatper(chapterId) {
        //弹框
        this.dialogChapterFormVisible = true
        //调用接口
        chapterApi.getChapter(chapterId)
          .then(response => {
            this.chapter = response.data.chapter
          })
      },

      //弹框弹出添加章节页面
      openChapterDialog() {
        //弹框
        this.dialogChapterFormVisible = true
        //表单数据清空
        this.chapter.title = ''
        this.chapter.sort = 0
      },

      //根据课程id查询章节和小节
      getChapterVideo() {
        chapterApi.getAllChapterVideo(this.courseId)
          .then(response => {
            this.chapterVideoList = response.data.allChapterVideo
          })
      },
      previous() { //上一步
        this.$router.push({
          path: '/course/info/' + this.courseId
        })
      },

      next() { //下一步
        this.$router.push({
          path: '/course/publish/' + this.courseId
        })
      }
    }
  }
</script>

<!-- 章节列表的样式 -->
<style scoped>
  .chanpterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .chanpterList li {
    position: relative;
  }

  .chanpterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
  }

  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
