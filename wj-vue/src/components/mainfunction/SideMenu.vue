<template>
  <el-menu
    :default-active="'/addlaw'"
    router
    class="categories"
    active-text-color="red">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'SideMenu',
  data () {
    return {
      navList: [
        {name: '/addlaw', navItem: '导入'},
        {name: '/show', navItem: '展示'},
        {name: '/update', navItem: '未完'}
        // {name: '/admin', navItem: '待开发'}
      ]
    }
  }
}
</script>

<style scoped>
  .categories {
    position: fixed;
    margin-left: 50%;
    left: -600px;
    top: 100px;
    width: 150px;
  }
</style>
