<template>
    <div>
        <el-select v-model="value" placeholder="请选择" size="medium" clearable>
        <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
        </el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" v-on:click = 'search'></el-button>
        <el-tree
        :data="tree"
        :props="defaultProps"
        accordion
        @node-click="handleNodeClick">
</el-tree>
    </div>
</template>

<script>
export default {
  name: 'Show',
  data () {
    return {
      options: [],
      value: '',
      tree: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  created: function () {
    console.log('this is a test')
    this.$axios
      .post('/showLaw')
      .then(successResponse => {
        var arr = successResponse.data
        arr.map(function (x) {
          //   x.value = x.id.toString()
          x.value = x.id
          delete x.id
          //   x.label = x.lawTitle.toString()
          x.label = x.lawTitle
          delete x.lawTitle
        })
        this.options = arr
      })
      .catch(failResponse => {
      })
  },
  methods: {
    search () {
      console.log(this.value)
      this.$axios
        .post('/show', {value: this.value})
        .then(successResponse => {
          this.tree = successResponse.data.map_list
        })
        .catch(failResponse => {
        })
    },
    handleNodeClick (tree) {
      console.log(tree)
    }
  }
}
</script>

<style scoped>

</style>
