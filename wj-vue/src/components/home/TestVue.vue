<template>
    <div>
      <el-menu
      :default-active="'/index'"
      router
      mode="horizontal"
      background-color="white"
      text-color="#222"
      active-text-color="red"
      style="min-width: 1300px">
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
      </el-menu-item>
      <a href="#nowhere" style="color: #222;float: right;padding: 20px;">更多功能</a>
      <i class="el-icon-menu" style="float:right;font-size: 45px;color: #222;padding-top: 8px"></i>
      <span style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">White Jotter - Your Mind Palace</span>
    </el-menu>
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
      <el-tree :data="tree" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
    </div>
</template>

<script>
export default {
  name: 'NavMenu',
  data () {
    return {
      navList: [
        {name: '/index', navItem: '首页'},
        {name: '/jotter', navItem: '笔记本'},
        {name: '/library', navItem: '图书馆'},
        {name: '/admin', navItem: '个人中心'}
      ],
      tree: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  methods: {
    login () {
      // this.tree.push({label: 'dd'})
      this.$axios
        .post('/test')
        .then(successResponse => {
          // console.log('test')
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
