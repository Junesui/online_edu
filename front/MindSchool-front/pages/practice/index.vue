<template>
  <div class="app-container">
    <div id="headline">
      <div>字形练习</div>
    </div>

    <!-- 上传图片组件 -->
    <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" :on-preview="handlePreview"
      :on-remove="handleRemove" :file-list="fileList" list-type="picture">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>

    <!-- 在线识别按钮 -->
    <el-button type="primary" plain style="margin-top: 1%;margin-bottom: 1%;">在线识别</el-button>

    <!-- 手动评分 -->
    <div class="block">
      <span class="demonstration">自我评分</span>
      <el-rate v-model="value2" :colors="colors">
      </el-rate>
    </div>

    <!-- 评语 -->
    <div id="selfComment">
      自我评价
      <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入内容" v-model="textarea">
      </el-input>
    </div>


    <!-- 提交按钮 -->
    <div id="submit">
       <el-button type="success" plain ><a @click="submit">提交</a></el-button>
    </div>




    <!-- 示范字形展示 -->
    <div id="showFont">优秀字形展示</div>
    <div id="showFontBar">
    <el-row :gutter="0">
    			<el-carousel :interval="0" arrow="never" height="310px" type="card">
    				<el-carousel-item v-for="item in dataimg" :key="item">
    					<div class="grid-content">
    						<el-col :md="12" :offset="6">
    							<div>
    								<img :src="item.src">
    							</div>
    						</el-col>
    					</div>
    				</el-carousel-item>
    			</el-carousel>
    		</el-row>
        </div>
    <!-- <div id="showFontBar">
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item v-for="(item, index) in imageList" :key="index">
          <img :src="item.src" :alt="item.alt" />
        </el-carousel-item>
      </el-carousel>
    </div> -->
  </div>
</template>

<script>
  export default {
    data() {
      return {
        //上传图片组件
        fileList: [{
            name: 'food.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          },
          {
            name: 'food2.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          }
        ],
        //评语
        textarea: '',
        // 评分
        value2: null,
        colors: ['#99A9BF', '#F7BA2A', '#FF9900'], // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }，
        dataimg: [{
        		src: require('../../assets/font/20.jpg'),

        	},
        	{
        		src: require('../../assets/font/22.jpg'),

        	},
        	{
        		src: require('../../assets/font/26.jpg'),

        	}
        ]


      };
    },
    methods: {
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      submit(){
        this.$router.push({path:'/practice/success/'})
      }

    }
  }
</script>


export default {
		name: 'AboutUs',
		data() {
			return {

				dataimg: [{
						src: require('../assets/img/img01_03.png'),
						txt: '"Sed semper lorem at felis. Vestibulum volutpat, lacus a ultrices sagittis, mi neque euismod dui, eu pulvinar nunc sapien ornare nisl. Phasellus pede arcu, dapibus eu, fermentum et, dapibus sed, urna."',
						txt2: '一站式服务',
						txt3: 'Miami, FL'
					},
					{
						src: require('../assets/img/img02_03.jpg'),
						txt: '"Sed egestas, ante et vulputate volutpat, eros pede semper est, vitae luctus metus libero eu augue. Morbi purus libero, faucibus adipiscing, commodo quis, gravida id, est. Sed lectus. Praesent elementum hendrerit tortor."',
						txt2: '国际服务',
						txt3: 'Greensboro, NC'
					},
					{
						src: require('../assets/img/img02_05.jpg'),
						txt: '"Praesent elementum hendrerit tortor. Sed semper lorem at felis. Vestibulum volutpat, lacus a ultrices sagittis eu pulvinar nunc sapien ornare nisl. Phasellus pede arcu, dapibus eu, fermentum et, dapibus sed, urna."',
						txt2: '全网渠道',
						txt3: 'Charlotte, NC'
					}
				]
			}
		}
	}

<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  #showFont {
    text-align: center;
    font-weight: 600;
    font-size: 25px;
    margin-bottom: 20px;
  }

  #headline {
    font-size: 25px;
    font-weight: 600;
    text-align: center;
    margin-top: 20px;
    margin-bottom: 20px;
  }

  #uploadFont {
    text-align: center;
    margin-bottom: 20px;
  }

  #showFontBar {
    margin-bottom: 30px;
  }

  .app-container {
    margin-left: 10%;
    margin-right: 10%;
  }

  #selfComment{
    margin-top: 20px;
    margin-bottom: 20px;
  }

  #submit{
    margin-top: 20px;
    margin-bottom: 20px;
  }
</style>
