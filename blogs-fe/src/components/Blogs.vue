<template>
  <el-container>

    <el-main>

      <el-input
          size="medium"
          placeholder="Search"
          v-model="search"
      style="width: 20%">
      </el-input>

      &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
      <Authors/>
      &ensp;&ensp;&ensp;&ensp;&ensp;
      <Cates/>

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
          <el-button type="danger" icon="el-icon-delete" circle @click="btnDelete(data.row)"></el-button>
          </template>
        </el-table-column>


      </el-table>
    </el-main>

  </el-container>


</template>

<style>
.el-table .warning-row {
  background:#95a6c9 ;
  color: black;
}

.el-table .success-row {
  background: #93b9bd;
  color: black;
}
</style>

<script>
import BlogService from "@/services/BlogService";
import Cates from "@/components/Cates";
import Authors from "@/components/Authors";

export default {
  name: "BlogList",
  components: {
    Cates,
    Authors
  },
  data() {
    return {
      blogs: [],
      search: ''
    }

  },
  beforeCreate() {
    BlogService.getAll().then(response => {
      this.blogs = response.data
    })
  },
  mounted() {
    this.getBlogs
  },
  methods: {
    tableRowClassName({rowIndex}) {
      if (rowIndex % 2 == 0) {
        return 'warning-row';
      }
      else{
        return 'success-row';
      }
      // return '';
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
  computed:{
    getBlogs(){
      if(this.search){
        return this.blogs.filter((item)=>{
          return this.search.toLowerCase().split(' ').every(v => item.title.toLowerCase().includes(v))
        })
      }else{
        return this.blogs
      }
    }
  }

}


</script>

