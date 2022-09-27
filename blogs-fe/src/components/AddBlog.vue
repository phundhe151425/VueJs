<template>

  <el-form :model="blog" id="form-blog" :rules="rules" ref="blog" class="demo-blog">
    <div>{{blog}}</div>
    <el-form-item label="Title" prop="title">
      <el-input name="title" v-model="blog.title"></el-input>
    </el-form-item>
    <el-form-item label="Content" prop="content">
      <el-input type="textarea" name="content" v-model="blog.content"></el-input>
    </el-form-item>
    <el-form-item label="Blog covers" prop="cover">
      <input multiple type="file" name="files">

    </el-form-item>
    <el-form-item label="Category" prop="category">
      <el-select name="category" v-model="blog.category" placeholder="Category">
        <el-option :value="cate.id" v-for="cate in categories"  :key="cate.id">{{cate.name}}</el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="Author" prop="author">
      <el-select name="author" v-model="blog.author" placeholder="Author">
        <el-option :value="author.id" v-for="author in authors"  :key="author.id">{{author.name}}</el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit('blog')" round>Create</el-button>
      <el-button @mouseup="resetForm('blog')" round>Reset</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
import BlogService from "@/services/BlogService";
export default {
  name: "AddBlog",
  data(){
    return{
      blog:{
        title: '',
        content: '',
        cover: [],
        category: '',
        author: ''
      },
      categories:[],
      authors: [],
      rules: {
        title: [
          { required: true, message: 'Please input title blog', trigger: 'blur' },
          { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' }
        ],
        categoryCode: [
          { required: true, message: 'Please select Category ', trigger: 'change' }
        ],
        content: [
          { required: true, message: 'Please input content blog', trigger: 'blur' }
        ]
      }
    }
  },
  created: async function () {
      let response = await BlogService.getCates();
      this.categories = response.data
  },
  mounted: async function () {
    let response = await BlogService.getAuthors();
    this.authors = response.data
  },
  methods:{

    submit(formName){
      this.$refs[formName].validate((valid) => {
        if(valid){
          let form = document.querySelector('#form-blog');
          console.log(form)
          BlogService.create(form)
          this.$refs[formName].resetFields()
        }
        else{
          return false;
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3);
    },


  }
}
</script>

<style scoped>

</style>