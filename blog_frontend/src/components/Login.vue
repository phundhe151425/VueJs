<template>
  <el-form :model="user" :rules="rules" ref="user" label-width="120px" id="register-form">
    <el-form-item label="User Name" prop="user_name">
      <el-input type="text" v-model="user.user_name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input type="password" v-model="user.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit('user')">Login</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import UserService from "@/services/UserService";
import axios from "axios";
export default {
  name: "Login-Form",
  data() {

    return {

      rules: {
        user_name: [
          {required: true, message: 'Please enter User Name', trigger: 'blur'},
          // {min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please enter password', trigger: 'blur'}
        ],

      },
      user: {
        user_name: '',
        password: '',
      },
    }

  },
  methods: {
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const response = UserService.loginUser(this.user)
          axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
          // this.$router.push('/blogs')
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>