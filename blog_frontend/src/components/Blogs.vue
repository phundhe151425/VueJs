<template>
  <el-container>

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


      <el-form :model="category" ref="cate">
        <el-select v-model="category.id" placeholder="Select Category" @change="getBlogs">
          <el-option value="0" >All</el-option>
          <el-option
              v-for="cate in categories"
              :key="cate.id"
              :label="cate.name"
              :value="cate.id">
          {{cate.name}}</el-option>
        </el-select>
      </el-form>
      &ensp;&ensp;&ensp;&ensp;&ensp;



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
            <router-link :to="`/blogs/${data.row.id}`">
              <el-button type="primary" icon="el-icon-edit" circle></el-button>
            </router-link>
            <el-button type="danger" icon="el-icon-delete" circle @click="btnDelete(data.row)"></el-button>
          </template>
        </el-table-column>


      </el-table>
      <el-pagination
          small
          layout="prev, pager, next"
          :total="totalItems"
          :page-size="pageSize"
          @current-change="handlePageChange">
      </el-pagination>
    </el-main>

  </el-container>


</template>

<style>
.el-table .warning-row {
  background: #95a6c9;
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
      category: {
        id: '',
        name: ''
      },

      totalItems: 0,
      page: 0,
      pageSize: 5,

    }

  },
  Created() {
    // this.getBlogs()
  },

  mounted() {
    this.getBlogs();
    BlogService.getCates().then(response => {
      this.categories = response.data
    });
  },
  methods: {
    // getRequestParams( page, pageSize) {
    //   let params = {};
    //   if (page) {
    //     params["page"] = page - 1;
    //   }
    //
    //   if (pageSize) {
    //     params["pageSize"] = pageSize;
    //   }
    //
    //   return params;
    // },
    getBlogs() {
      const params = {
        'page': this.page,
        'pageSize': this.pageSize,
        'cateid': this.category.id,

      }
      BlogService.getAll(params).then(response => {
        console.log("a" + this.category.id)
        const {blogs, totalItems, page, cateid} = response.data;
        this.blogs = blogs;
        this.totalItems = totalItems;
        this.page = page;
        this.category.id = cateid
        console.log("b" + this.category.id)
      })
          .catch(error => {
            console.log(error);
          })
    },
    tableRowClassName({rowIndex}) {
      if (rowIndex % 2 == 0) {
        return 'warning-row';
      } else {
        return 'success-row';
      }
      // return '';
    },
    handlePageChange(value) {
      this.page = value - 1;
      if (this.search !== null) {
        this.searchBlogs();
      }
      if (this.category !== null) {
        this.getBlogs();
      }else {
        this.getBlogs();
      }
    },


    searchBlogs() {
      const params = {
        'page': this.page,
        'pageSize': this.pageSize,
        'key': this.search
      }
      console.log(params)
      BlogService.search(params).then(response => {
        this.blogs = response.data.blogs
        this.totalItems = response.data.totalItems
        this.page = response.data.page
      })
          .catch(error => {
            console.log(error);
          })
    },

    blogsByCate() {
      // BlogService.getAll(this.cateid).then(response => {
      //   console.log(this.cateid)
      //   this.blogs = response.data
      // })

    },

    btnDelete(row) {
      console.log(row.id)
      BlogService.delete(row.id)
          .then(() => {
            this.getBlogs();
          })
    }
  },
  computed: {

    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  }

}


</script>

