<template>
    <div>
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
      <el-tree :data="tree" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
    </div>
</template>

<script>
export default {
  // name: 'NavMenu',
  data () {
    return {
      tree: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  created: function () {
    console.log('this is a test')
  },
  methods: {
    login () {
      // fetch('http://localhost:8443/api/test')
      //   .then(response => response.json())
      //   .then(json => {
      //     this.tree = json.map_list
      //     // this.tree.push(json)
      //   })
      // console.log('gei wo chu lai!!')
      this.$axios
        .post('/test')
        .then(successResponse => {
          this.tree = successResponse.data.map_list
          console.log(this.tree)
        })
        .catch(failResponse => {
        })
      // this.navList.pop({name: '/admin', navItem: '个人中心'})
      // this.navList.push({name: '/ddd', navItem: '测试'})
    },
    handleNodeClick (data) {
      console.log(data)
    }
  }
}
</script>

<style scoped>
  a{
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }
</style>
