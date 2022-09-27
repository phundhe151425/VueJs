<template>
<!--  <el-form :model="blog" id="form-blog" :rules="rules" ref="blog" class="demo-blog">-->
<!--    <div>{{blog}}</div>-->
<!--    <el-form-item label="Title" prop="title">-->
<!--      <el-input name="title" v-model="blog.title"></el-input>-->
<!--    </el-form-item>-->
<!--    <el-form-item label="Content" prop="content">-->
<!--      <el-input type="textarea" name="content" v-model="blog.content"></el-input>-->
<!--    </el-form-item>-->
<!--    <el-form-item label="Blog covers" prop="cover">-->
<!--      <input multiple type="file" name="files">-->

<!--    </el-form-item>-->
<!--    <el-form-item label="Category" prop="category">-->
<!--      <el-select name="category" v-model="blog.category" placeholder="Category">-->
<!--        <el-option :value="cate.id" v-for="cate in categories"  :key="cate.id">{{cate.name}}</el-option>-->
<!--      </el-select>-->
<!--    </el-form-item>-->
<!--    <el-form-item label="Author" prop="author">-->
<!--      <el-select name="author" v-model="blog.author" placeholder="Author">-->
<!--        <el-option :value="author.id" v-for="author in authors"  :key="author.id">{{author.name}}</el-option>-->
<!--      </el-select>-->
<!--    </el-form-item>-->
<!--    <el-form-item>-->
<!--      <el-button type="primary" @click="submit('blog')" round>Update</el-button>-->
<!--      <el-button @mouseup="resetForm('blog')" round>Reset</el-button>-->
<!--    </el-form-item>-->
<!--  </el-form>-->
  <pre>{{blogId}}</pre>
</template>

<script>
// import BlogService from "@/services/BlogService";
export default {
  name: "EditBlog",
  data() {
    return {
      blogId: this.$route.params.blogId,
      blog: {}

    }
  },
  // mounted() {
  //   BlogService.getBlogById(blogId).then(response =>{
  //     this.blog = response.data
  //   })
  // },
  methods:{

  }
}
</script>

<style scoped>

</style>