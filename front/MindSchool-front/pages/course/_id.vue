<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a :href="'/course?subjectParentId='+courseWebVo.subjectLevelOneId" class="c-333 fsize14">{{courseWebVo.subjectLevelOne}}</a>
        \
        <a :href="'/course?subjectParentId='+courseWebVo.subjectLevelOneId+'&subjectId='+courseWebVo.subjectLevelTwoId" class="c-333 fsize14">{{courseWebVo.subjectLevelTwo}}</a>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img height="357px" :src="courseWebVo.cover" :alt="courseWebVo.title" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseWebVo.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseWebVo.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseWebVo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <!-- 收藏功能 -->
            <section class="c-attr-mt of">
                <span v-if="isCollect" class="ml10 vam sc-end">
                    <em class="icon18 scIcon"/>
                    <a
                       style="cursor:pointer"
                       class="c-fff vam"
                       title="取消收藏"
                       @click="removeCollect(courseWebVo.id)">已收藏</a>
                </span>
                <span v-else class="ml10 vam">
                    <em class="icon18 scIcon"/>
                    <span
                       style="cursor:pointer"
                       class="c-fff vam"
                       title="收藏"
                       @click="init2(courseWebVo.id)">收藏</span>
                </span>
            </section>
            <section v-if="isBought|| Number(courseWebVo.price) === 0" class="c-attr-mt">
              <a href="javascript:void(0);"
                 title="立即观看"
                 class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section v-else class="c-attr-mt">
              <a @click="init()"
                 href="javascript:void(0);"
                 title="立即购买"
                 class="comm-btn c-btn-3">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebVo.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ courseWebVo.lessonNum }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{ courseWebVo.viewCount }}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <!-- v-html 翻译web语言 -->
                      <p v-html="courseWebVo.description">{{courseWebVo.description}}</p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li class="lh-menu-stair" v-for="chapter in chapterVideoList" :key="chapter.id">
                            <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                            </a>

                            <ol class="lh-menu-ol" style="display: block;">
                              <li class="lh-menu-second ml30" v-for="video in chapter.children" :key="video.id">

                                <a :href="'/player/'+video.videoSourceId"
                                   v-if="isBought || Number(courseWebVo.price) === 0"
                                   :title="video.title">
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>

                                <a v-else-if="video.free === true"
                                   :href="'/player/'+video.videoSourceId"
                                   :title="video.title">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>

                                <a
                                   v-else
                                   :title="video.title">
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>

                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="courseWebVo.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" :href="'/teacher/'+courseWebVo.teacherId">{{courseWebVo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseWebVo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>

      <!-- 课程评论 -->
      <div class="mt50 commentHtml"><div>
            <h6 class="c-c-content c-infor-title" id="i-art-comment">
              <span class="commentTitle">课程评论</span>
            </h6>
            <section class="lh-bj-list pr mt20 replyhtml">
              <ul>
                <li class="unBr">
                  <aside class="noter-pic">
                    <img width="50" height="50" class="picImg" src="~/assets/img/loading.gif">
                    </aside>
                  <div class="of">
                    <section class="n-reply-wrap">
                      <fieldset>
                        <textarea name="" v-model="comment.content" placeholder="输入您要评论的文字" id="commentContent"></textarea>
                      </fieldset>
                      <p class="of mt5 tar pl10 pr10">
                        <span class="fl "><tt class="c-red commentContentmeg" style="display: none;"></tt></span>
                        <input type="button" @click="init3()" value="回复" class="lh-reply-btn">
                      </p>
                    </section>
                  </div>
                </li>
              </ul>
            </section>
            <section class="">
                <section class="question-list lh-bj-list pr">
                  <ul class="pr10">
                    <li v-for="(comment,index) in data.items" v-bind:key="index">
                        <aside class="noter-pic">
                          <img width="50" height="50" class="picImg" :src="comment.avatar">
                          </aside>
                        <div class="of">
                          <span class="fl">
                          <font class="fsize12 c-blue">
                            {{comment.nickname}}</font>
                          <font class="fsize12 c-999 ml5">评论：</font>
                          </span>
                        </div>
                        <div class="noter-txt mt5">
                          <p>{{comment.content}}</p>
                        </div>
                        <div class="of mt5">
                          <div style="text-align: right;">
                            <input type="button" @click="deleteComment(comment.id)" value="删除" class="lh-reply-btn">
                          </div>
                        </div>

                        <div class="of mt5">
                          <span class="fr"><font class="fsize12 c-999 ml5">{{comment.gmtCreate}}</font></span>
                        </div>

                      </li>

                    </ul>
                </section>
              </section>

              <!-- 公共分页 开始 -->
              <div class="paging">
                  <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                  <a
                  :class="{undisable: !data.hasPrevious}"
                  href="#"
                  title="首页"
                  @click.prevent="gotoPage(1)">首</a>
                  <a
                  :class="{undisable: !data.hasPrevious}"
                  href="#"
                  title="前一页"
                  @click.prevent="gotoPage(data.current-1)">&lt;</a>
                  <a
                  v-for="page in data.pages"
                  :key="page"
                  :class="{current: data.current == page, undisable: data.current == page}"
                  :title="'第'+page+'页'"
                  href="#"
                  @click.prevent="gotoPage(page)">{{ page }}</a>
                  <a
                  :class="{undisable: !data.hasNext}"
                  href="#"
                  title="后一页"
                  @click.prevent="gotoPage(data.current+1)">&gt;</a>
                  <a
                  :class="{undisable: !data.hasNext}"
                  href="#"
                  title="末页"
                  @click.prevent="gotoPage(data.pages)">末</a>
                  <div class="clear"/>
              </div>
              <!-- 公共分页 结束 -->
            </div>
          </div>

    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>
  //引入调用course.js文件
  import courseApi from '@/api/course'
  //引入调用collect.js文件
  import collectApi from '@/api/collect'
  //引入调用order.js文件
  import orderApi from '@/api/order'
  //引入调用comment.js文件
  import commentApi from '@/api/comment'
  //引入调用js-cookie
  import cookie from 'js-cookie'
  export default {

    asyncData({ params, error }) {
        return courseApi.getCourseInfo(params.id)
            .then(response => {
                return {
                    courseWebVo: response.data.data.courseWebVo,
                    chapterVideoList: response.data.data.chapterVideoList
                }
            })
    },

    data() {
      return {
        isBought: false,  // 是否已购买
        isCollect: false ,// 是否已收藏
        isComment: false ,// 是否是该用户的评论
        // 分页组件
        data:{},
        page:1,
        limit:4,
        total:10,
        // 评论
        comment:{
          content:'',
          courseId:'',
          nickname:'',
          avatar:'',
          },
      }
    },

    created() {
      const token = cookie.get('MindSchool_token')
      // 如果未登录，则isBought=false
      // 如果已登录，则判断是否已购买
      if (token) {
        orderApi.isBought(this.courseWebVo.id).then(response => {
          this.isBought = response.data.data.isBought
        })
        //判断是否收藏
        collectApi.isCollect(this.courseWebVo.id).then(response => {
          this.isCollect = response.data.data.isCollect
        })
      }

      this.initComment()
    },

    methods: {
      init(){
        //判断是否登录
        const token = cookie.get('MindSchool_token')
        // 如果未登录，提示登录
        if (token) {
          this.createOrders()
        }else{
            this.$message({
              type: 'error',
              message: '请先登录️再进行下一步操作'
            });
            //vue路由跳转
            this.$router.push({
              path: '/login'
            })
        }
      },

      //生成订单
      createOrders() {
        orderApi.createOrders(this.$route.params.id)
          .then(response => {
            //获取返回订单号
            //生成订单之后，跳转订单显示页面
            this.$router.push({
              path: '/order/' + response.data.data.orderId
            })
          })
      },

      init2(){
        //判断是否登录
        const token = cookie.get('MindSchool_token')
        // 如果未登录，提示登录
        if (token) {
          this.addCollect()
        }else{
            this.$message({
              type: 'error',
              message: '请先登录️再进行下一步操作'
            });
            //vue路由跳转
            this.$router.push({
              path: '/login'
            })
        }
      },

      //收藏
      addCollect(courseId) {
        collectApi.addCollect(this.courseWebVo.id).then(response => {
          this.isCollect = true
          this.$message({
            type: 'success',
            message: '收藏成功'
          })
        })
      },

      //取消收藏
      removeCollect(courseId) {
        collectApi.removeCollectById(this.courseWebVo.id).then(response => {
          this.isCollect = false
          this.$message({
            type: 'success',
            message: '取消收藏成功'
          })
        })
      },

      //初始化评论,获取评论列表
      initComment(){
             commentApi.getPageList(this.page, this.limit, this.courseWebVo.id).then(response => {
                 this.data = response.data.data
             })

          },

      init3(){
        //判断是否登录
        const token = cookie.get('MindSchool_token')
        // 如果未登录，提示登录
        if (token) {
          this.addComment()
        }else{
            this.$message({
              type: 'error',
              message: '请先登录️再进行下一步操作'
            });
            //vue路由跳转
            this.$router.push({
              path: '/login'
            })
        }
      },

      //添加评论
      addComment(){
        this.comment.courseId = this.courseWebVo.id
        this.comment.teacherId = this.courseWebVo.teacherId
        commentApi.addComment(this.comment).then(response => {
          if(response.data.success){
            this.comment.content = ''
            this.initComment()
                }
              })
            },

       //删除评论
       deleteComment(commentId){
         this.$confirm('确认要删除当前评论吗?', '提示', {
           confirmButtonText: '确定',
           cancelButtonText: '取消',
           type: 'warning'
         }).then(() => {
           return commentApi.deleteComment(commentId)
         }).then((response) => {
           this.initComment()
           this.$message({
             type: 'success',
             message: '删除成功✌',
           })
         }).catch(error => {
           if (error === 'cancel') {
             this.$message({
               message: '取消删除'
             })
           }
         })
      },

      //跳转页面
      gotoPage(page){
        commentApi.getPageList(page, this.limit,this.courseId).then(response => {
          this.data = response.data.data
            })
          },
    },
  }
</script>
