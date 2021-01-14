<template>
  <div>
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.8/skins/default/aliplayer-min.css" >
    <!-- 阿里云视频播放器组件 -->
    <script charset="utf-8" src="https://player.alicdn.com/aliplayer/presentation/js/aliplayercomponents.min.js"></script>
    <!-- 阿里云视频播放器样式 -->
    <script charset="utf-8" src="https://g.alicdn.com/de/prismplayer/2.8.8/aliplayer-min.js"></script>
    <!-- 阿里云视频播放器脚本 -->
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />

    <!-- 定义播放器dom -->
    <div>
      <div id="J_prismPlayer" class="prism-player" />
    </div>

  </div>
</template>
<script>
//引入调用vod.js文件
import vodApi from '@/api/vod'

export default {
    layout: 'default',//应用video布局
    asyncData({ params, error }) {
       return vodApi.getPlayAuth(params.videoId)
        .then(response => {
            return {
                playAuth: response.data.data.playAuth,
                videoId: params.videoId
            }
        })
    },
    //页面渲染之后
    mounted() {
        new Aliplayer({
            id: 'J_prismPlayer',
            vid: this.videoId, // 视频id
            playauth: this.playAuth, // 播放凭证
            encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
            width: '100%',
            height: '500px',
            // 以下可选设置
            cover: 'https://edu-zjm.oss-cn-beijing.aliyuncs.com/banner/videoplayer.jpg', // 封面
            qualitySort: 'asc', // 清晰度排序

            mediaType: 'video', // 返回音频还是视频
            autoplay: false, // 自动播放
            isLive: false, // 直播
            rePlay: false, // 循环播放
            preload: true,
            controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
            useH5Prism: true, // 播放器类型：html5
            components: [{
                name: 'BulletScreenComponent', // 跑马灯组件
                type: AliPlayerComponent.BulletScreenComponent,
                /** 跑马灯组件三个参数 text, style, bulletPosition
                   * text: 跑马灯文字内容
                   * style: 跑马灯样式
                   * bulletPosition: 跑马灯位置, 可选的值为 'top' (顶部), 'bottom' (底部), 'random' (随机), 不传值默认为 'random'
                   */
                args: ['欢迎来到MindSchool', { fontSize: '18px', color: '#ffaa00' }, 'random']
            },]
        }, function(player) {
            console.log('播放器创建成功')
        })
    }
}

</script>
