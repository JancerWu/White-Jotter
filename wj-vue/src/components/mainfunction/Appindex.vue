<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu></SideMenu>
    </el-aside>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>
    <!-- <el-input
    placeholder="法律名称"
    v-model="law.law_tittle">
    </el-input> -->
     <!-- <el-input
  type="textarea"
  :autosize="{ minRows: 10, maxRows: 10}"
  placeholder="请输入内容"
  v-model="law.law_body">
  </el-input> -->
    <!-- <el-button type="primary" plain v-on:click="process">整合处理</el-button> -->

<script>
import SideMenu from './SideMenu'
export default {
  name: 'AppIndex',
  components: {SideMenu},
  data () {
    return {
      law: {
        law_body: '',
        law_tittle: ''
      }
    }
  },
  methods: {
    // process () {
    //   this.$axios
    //     .post('/index', {
    //       law_body: this.law.law_body,
    //       law_tittle: this.law.law_tittle
    //     })
    //     .then(successResponse => {
    //       if (successResponse.data.code === 200) {
    //         this.$router.replace({path: '/index'})
    //       }
    //     })
    //     .catch(failResponse => {
    //     })
    // }
  }
}
</script>
<style scoped>
</style>
