<template>
  <div id="app">
    <el-header>
      <navbar-component/>
    </el-header>

    <br>



    <el-main>
      <router-view/>
    </el-main>

  </div>
</template>

<script>

import NavbarComponent from "@/components/Navbar";
export default {
  name: 'App',
  components: {
    NavbarComponent,

  }
}
</script>

<style>
#app {
  text-decoration: none;

}



</style>
