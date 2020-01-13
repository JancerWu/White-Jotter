<template>
    <div>
        <el-select v-model="valueLaw" placeholder="请选择" size="medium"
         ref="selectLaw" @change="changeLaw($event)">
        <el-option
        v-for="item in optionsLaw"
        :key="item.value"
        :label="item.label"
        :value="item.value">
        </el-option>
        </el-select>
        <el-select v-model="valueChapter" placeholder="请选择"
        size="medium"  ref="selectChapter" @change="changeChapter($event)">
        <el-option
        v-for="item in optionsChapter"
        :key="item.value"
        :label="item.label"
        :value="item.value">
        </el-option>
        </el-select>
        <el-tag
        v-for="item in instruct"
        :key="item.label"
        :type="item.type"
        effect="plain">
        {{ item.label }}
        </el-tag>
        <el-button slot="append" icon="el-icon-search" v-on:click = 'search'></el-button>

          <el-table
    height="250"
    :data="tableData"
    style="width: 100%">
    <el-table-column
      fixed="left"
      label="备注"
      width="100">
      <template slot-scope="content">
        <el-popover trigger="hover" placement="top">
          <p>chapterId: {{content.row.chapterId}}</p>
          <p>sectionState: {{ content.row.sectionState }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ content.row.id }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column label="内容" width="1800" prop="sectionContent">
    </el-table-column>
    <el-table-column label="操作" width="180" fixed="right">
      <template slot-scope="content">
        <el-button
          size="mini"
          @click="handleEdit(content.$index, content.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(content.$index, content.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
    </div>
</template>

<script>
export default {
  name: 'Show',
  data () {
    return {
      optionsLaw: [],
      valueLaw: '',
      optionsChapter: [],
      valueChapter: '',
      instruct: [{
        type: 'info', label: '法律'
      }],
      tableData: []
    }
  },
  created: function () {
    console.log('this is a test')
    this.$axios
      .post('/showLaw')
      .then(successResponse => {
        var arr = successResponse.data
        arr.map(function (x) {
          x.value = x.id
          //   delete x.id
          x.label = x.lawTitle
          //   delete x.lawTitle
        })
        // console.log(arr)
        this.optionsLaw = arr
      })
      .catch(failResponse => {
      })
  },
  methods: {
    changeLaw (e) {
      console.log('changeLaw!!')
      console.log(e)
      //   将章节清空
      this.optionsChapter = []
      //   更改标签
      this.instruct[0].label = '章节'
      this.valueChapter = ''
      this.$axios
        .post('/selectLaw', {valueLaw: this.valueLaw})
        .then(successResponse => {
          var arr = successResponse.data
          //   console.log(JSON.stringify(arr))
          arr.map(function (x) {
            x.value = x.id
            x.label = x.chapterTittle
          })
          //   console.log(arr)
          this.optionsChapter = arr
        })
        .catch(failResponse => {
        })
    },
    changeChapter (e) {
      console.log('changeChapter!!')
      //   更改标签
      this.instruct[0].label = '条款'
      console.log(this.valueChapter)
    },
    search () {
      console.log('这是法律id:' + this.valueLaw)
      console.log('这是章节id:' + this.valueChapter)
      this.$axios
        .post('/searchSections', {law_id: this.valueLaw, chapter_id: this.valueChapter})
        .then(successResponse => {
          console.log('search sections successfully!!')
          var arr = successResponse.data
          //   console.log(JSON.stringify(arr))
          this.tableData = arr
        })
        .catch(failResponse => {
        })
    },
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    }
  }
}
</script>

<style scoped>

</style>
