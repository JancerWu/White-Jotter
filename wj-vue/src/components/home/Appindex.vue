<template>
  <div>
    <el-input
  type="textarea"
  :autosize="{ minRows: 10, maxRows: 10}"
  placeholder="请输入内容"
  v-model="laws">
  </el-input>
    <el-button type="primary" plain v-on:click="process">整合处理</el-button>
  </div>
</template>

<script>
export default {
  name: 'AppIndex',
  data () {
    return {
      laws: ''
    }
  },
  methods: {
    process () {
      this.$axios
        .post('/index', {
          laws: this.laws
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$router.replace({path: '/index'})
          }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>
<style scoped>
</style>
