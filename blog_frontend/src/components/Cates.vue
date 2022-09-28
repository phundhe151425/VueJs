<template>
  <el-dropdown>
    <el-button type="primary">
      Category List<i class="el-icon-arrow-down el-icon--right"></i>
    </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :value="cate.id" v-for="cate in categories" :key="cate.id">{{ cate.name }}</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>

</template>

<script>
import BlogService from "@/services/BlogService";
export default {
  name: "Cates-list",
  data(){
    return{
      categories: [],
    }
  },
  mounted()  {
    BlogService.getCates().then(response => {
      this.categories = response.data
    });

  }
}
</script>

<style scoped>

</style>