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
    <el-menu-item index="2" style="width: 100px;">
      <router-link to="/blogs" class="nav-link"><i class="el-icon-s-home">  Home</i></router-link>
    </el-menu-item>
    <el-menu-item index="3">
      <router-link to="/add" class="nav-link"> <i class="el-icon-plus"></i></router-link>
    </el-menu-item>
    <el-menu-item index="6" style="width: 120px; float: right" v-if="!loggedIn">
      <router-link  to="/login" class="nav-link"> <i class="el-icon-s-promotion">  Login</i></router-link>
    </el-menu-item>
    <el-menu-item index="6" style="width: 120px; float: right" v-if="!loggedIn">
      <router-link to="/register" class="nav-link"> <i class="el-icon-user-solid">  Register</i></router-link>
    </el-menu-item>
    <el-menu-item index="6" style="width: 120px; float: right" v-if="loggedIn">

<!--        <i  class="el-icon-user-solid"> </i> Logout-->
        <a class="nav-link" href @click="logOut" style="text-decoration: none">
          <i class="el-icon-ice-cream-round">  Logout</i>
        </a>

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
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },

  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logOut() {
      this.$store.dispatch('auth/logout');
      // AuthService.logout()
      this.$router.push('/login');
    }
  },

}
</script>