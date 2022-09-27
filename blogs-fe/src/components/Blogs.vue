<template>
  <el-container>
    <el-main>
      <el-table
          :data="blogs"
          style="width: 100%"
          :row-class-name="tableRowClassName">
        <el-table-column
            prop="id"
            label="ID"
            width="180">
        </el-table-column>
        <el-table-column
            prop="title"
            label="Title"
            width="180">
        </el-table-column>
        <el-table-column
            prop="author.name"
            label="Author">
        </el-table-column>
        <el-table-column
            v-slot="scope"
            label="Cover">
          <template>
            <img v-for="(cover, index) in scope.row.covers" :key="index"
                 v-bind:src="'http://localhost:2001/image/' + cover.name" width="50px">
          </template>
        </el-table-column>
        <el-table-column
            prop="content"
            label="Content">
        </el-table-column>
        <el-table-column
            prop="category.name"
            label="Category">
        </el-table-column>
        <el-table-column
            label="Option">
          <template v-slot:="data">
          <router-link :to="`/blogs/${data.row.id}`"><el-button type="primary" icon="el-icon-edit" circle ></el-button></router-link>
<!--            <a [routerLink]="['/blogs/']" [queryParams]="{id: data.row.id}"></a>-->
          <el-button type="danger" icon="el-icon-delete" circle @mouseup="btnDelete(data.row)"></el-button>
          </template>
        </el-table-column>


      </el-table>
    </el-main>

  </el-container>


</template>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>
import BlogService from "@/services/BlogService";

export default {
  name: "BlogList",
  data() {
    return {
      blogs: [],
    }

  },
  mounted: async function () {
    BlogService.getAll().then(response => {
      this.blogs = response.data
    })
  },
  methods: {

    tableRowClassName({rowIndex}) {
      if (rowIndex % 2 == 0) {
        return 'warning-row';
      }
      return '';
    },

    btnDelete(row){
      console.log(row.id)
      BlogService.delete(row.id)
          .then(() =>{
            BlogService.getAll().then(response => {
              this.blogs = response.data
            })
          })
    }


  },

}


</script>

