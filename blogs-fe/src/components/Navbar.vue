<template>
  <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
<!--    <el-menu-item index="1">-->
<!--      <router-link to="/" class="nav-link">Home</router-link>-->
<!--    </el-menu-item>-->
    <el-menu-item index="2" >
      <router-link to="/blogs" class="nav-link"><i class="el-icon-s-home"></i></router-link>
    </el-menu-item>
    <el-menu-item index="3">
      <router-link to="/add" class="nav-link"> <i class="el-icon-plus"></i></router-link>
    </el-menu-item>
    <el-menu-item index="6">
      <router-link to="/register" class="nav-link"> <i class="">Register</i></router-link>
    </el-menu-item>
  </el-menu>

</template>
<script>

export default {
  name: "NavbarComponent",
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1',

    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },

}
</script>