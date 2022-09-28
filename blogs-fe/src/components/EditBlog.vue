<template>
  <div style="width: 60%">
    <el-form :model="blog" id="form-blog" :rules="rules" ref="blog" class="demo-blog">
      <!--    <div>{{blog}}</div>-->
      <el-form-item label="ID" prop="id">
        <el-input name="id" v-model="blog.id" readonly></el-input>
      </el-form-item>
      <el-form-item label="Title" prop="title">
        <el-input name="title" v-model="blog.title"></el-input>
      </el-form-item>
      <el-form-item label="Content" prop="content">
        <el-input type="textarea" name="content" v-model="blog.content"></el-input>
      </el-form-item>
      <el-form-item label="Blog covers" prop="files" >
        <template>
          <input multiple type="file" name="files">
          <img v-for="(cover, index) in blog.covers" :key="index"
               v-bind:src="'http://localhost:2001/image/' + cover.name" width="50px">
        </template>
      </el-form-item>
      <el-form-item label="Category" prop="category">
        <el-select name="category" v-model="blog.category" placeholder="Category">
          <el-option :value="cate.id" v-for="cate in categories" :key="cate.id">{{ cate.name }}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Author" prop="author">
        <el-select name="author" v-model="blog.author" placeholder="Author">
          <el-option :value="author.id" v-for="author in authors" :key="author.id">{{ author.name }}</el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit('blog')" round>Update</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
import BlogService from "@/services/BlogService";

export default {
  name: "EditBlog",
  data() {
    return {
      blogId: this.$route.params.blogId,
      blog: {},
      categories: [],
      authors: [],
      fileList: [],
      rules: {
        title: [
          {required: true, message: 'Please input title blog', trigger: 'blur'},
          {min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur'}
        ],
        categoryCode: [
          {required: true, message: 'Please select Category ', trigger: 'change'}
        ],
        content: [
          {required: true, message: 'Please input content blog', trigger: 'blur'}
        ]
      }
    }
  },

  mounted() {
    BlogService.getBlogById(this.blogId).then(response => {

      this.blog = response.data
      this.fileList = response.data.covers
    });
    BlogService.getCates().then(response => {
      this.categories = response.data
    });
    BlogService.getAuthors().then(response => {
      this.authors = response.data
    });
  },
  methods: {
    submit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let form = document.querySelector('#form-blog');
          BlogService.update(this.blogId, form)
          this.$router.push('/blogs')
        } else {
          return false;
        }
      })
    },


  }
}
</script>

<style scoped>

</style>