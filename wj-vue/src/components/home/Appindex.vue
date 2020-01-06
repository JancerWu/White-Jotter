<template>
  <div>
    <!-- <el-input
  type="textarea"
  :autosize="{ minRows: 10, maxRows: 10}"
  placeholder="请输入内容"
  v-model="law_body">
  </el-input> -->
    <el-upload
      class="upload-demo"
      drag
      action="http://localhost:8443/api/upload"
      multiple>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
    <!-- <el-button type="primary" plain v-on:click="process">整合处理</el-button> -->
  </div>
</template>

<script>
export default {
  name: 'AppIndex',
  data () {
    return {
      law_body: ''
    }
  },
  methods: {
    process () {
      // var lawVo = {
      //   text: this.law_body
      // }
      // this.$axios
      //   .post('/index', lawVo)
      //   .then(successResponse => {
      //     if (successResponse.data.code === 200) {
      //       this.$router.replace({path: '/index'})
      //     }
      //   })
      //   .catch(failResponse => {
      //   })

      // this.$axios
      //   .post('/upload', lawVo)
      //   .then(successResponse => {
      //     if (successResponse.data.code === 200) {
      //       this.$router.replace({path: '/index'})
      //     }
      //   })
      //   .catch(failResponse => {
      //   })
    }
  }
}
</script>
<style scoped>
</style>
