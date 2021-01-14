<template>
  <!-- 内容区域 -->
  <div class="bg-fa of">
    <section class="container">
      <div class="u-body mt40">
        <menu class="col-3 fl uMenu">
          <header class="comm-title"><h2 class="fl tac"><span class="c-333">个人中心</span></h2></header>
          <dl>
            <dd class="u-m-dd">
              <ul>
                <li>
                  <span>Wo的资料</span>
                  <ol>
                    <router-link to="/ucenter/info/basic" tag="li" active-class="current" exact>
                      <a>基本资料</a>
                    </router-link>
                    <router-link to="/ucenter/info/password" tag="li" active-class="current" exact>
                      <a>密码设置</a>
                    </router-link>
                  </ol>
                </li>
              </ul>
              <ul>
                <li>
                  <span>Wo的学习</span>
                  <ol>
                    <router-link to="/ucenter/study/order" tag="li" active-class="current" exact>
                      <a>我的订单</a>
                    </router-link>
                    <router-link to="/ucenter/study/collect" tag="li" active-class="current" exact>
                      <a>我的收藏</a>
                    </router-link>
                  </ol>
                </li>
              </ul>

             <!-- <ul>
                <li>
                  <span>Wo的问答</span>
                  <ol>
                    <li>
                      <a href="uc_question.html" title="">
                        Wo的提问
                      </a>
                    </li>
                    <li>
                      <a href="uc_anwser.html" title="">
                        Wo的回答
                      </a>
                    </li>
                  </ol>
                </li>
              </ul> -->

              <ul>
                <li>
                  <span>Wo的消息</span>
                  <ol>
                    <router-link to="/ucenter/msg/message" tag="li" active-class="current" exact>
                      <a>系统消息</a>
                    </router-link>
                  </ol>
                </li>
              </ul>
            </dd>
          </dl>
        </menu>

        <nuxt-child />

        <div class="clear"/>
      </div>
    </section>
  </div>
  <!-- /内容区域 -->
</template>

<script>
  export default {

  }
</script>
