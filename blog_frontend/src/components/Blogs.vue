<template>
  <el-container >

    <el-main>

      <el-input
          size="medium"
          placeholder="Search"
          v-model="search"
          @input="searchBlogs"
      style="width: 20%">
      </el-input>
<!--      <el-button type="primary" icon="el-icon-search" @click="searchBlogs">Search</el-button>-->

      &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
      <Authors/>
      &ensp;&ensp;&ensp;&ensp;&ensp;
      <el-form :model="cateid" id="cates" ref="cates">
        <el-dropdown>
          <el-button type="primary">
            Category List<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown" v-model="cateid" @change="blogsByCate">
            <el-dropdown-item :value="cate.id" v-for="cate in categories" :key="cate.id">{{ cate.name }}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-form>


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
import Authors from "@/components/Authors";

export default {
  name: "BlogList",
  components: {
    Authors
  },
  data() {
    return {
      blogs: [],
      search: '',
      categories: [],
      cateid: null
    }

  },
  beforeCreate() {
    BlogService.getAll().then(response => {
      this.blogs = response.data
    })
  },

  mounted() {
    BlogService.getCates().then(response => {
      this.categories = response.data
      console.log(this.cateid)
    });
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
    searchBlogs(){
      BlogService.search(this.search).then(response => {
        this.blogs = response.data
      })

    },

    blogsByCate(){
      BlogService.getAll(this.cateid).then(response => {
        console.log(this.cateid)
        this.blogs = response.data
      })

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

    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  }

}


</script>

