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
        ref="law"
        :expand-on-click-node="false"
        :data="tree"
        :props="defaultProps"
        :highlight-current="true"
        accordion
        @node-click="handleNodeClick">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span>{{ node.label }}</span>
            <span>
            <el-button
                type="text"
                size="mini"
                @click="() => append(data)">
                Append
            </el-button>
            <el-button
                type="text"
                size="mini"
                @click="() => remove(node, data)">
                Delete
            </el-button>
            </span>
            </span>
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
      console.log('Click+1')
      console.log(JSON.stringify(this.$refs.law.getCurrentNode()))
    },
    append () {
      console.log('append+1')
    },
    remove () {
      console.log('remove+1')
    }
  }
}
</script>

<style scoped>

</style>
