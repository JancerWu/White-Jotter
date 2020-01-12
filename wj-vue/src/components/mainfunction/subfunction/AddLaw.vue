<template>
  <div>
    <div style="margin-top: 15px;">
      <el-upload
        class="upload-demo"
        drag
        action="http://localhost:8443/api/upload"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AddLaw',
  data () {
    return {
      law: {
        law_body: '',
        law_tittle: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
