<template>
  <el-dropdown>
    <el-button type="primary">
      Author List<i class="el-icon-arrow-down el-icon--right"></i>
    </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :value="author.id" v-for="author in authors" :key="author.id">{{ author.name }}</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import BlogService from "@/services/BlogService";

export default {
  name: "Authors-list",
  data(){
    return{
      authors: [],
    }
  },
  mounted()  {
    BlogService.getAuthors().then(response => {
      this.authors = response.data
    });
  }
}
</script>

<style scoped>

</style>