<template>
  <el-form :model="user" :rules="rules" ref="user" label-width="120px" id="register-form">
    <el-form-item label="User Name" prop="user_name">
      <el-input type="text" v-model="user.user_name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input type="password" v-model="user.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="Re-Password" prop="password_confirm">
      <el-input type="password" v-model="user.password_confirm" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="Role" prop="role">
      <el-select name="role" v-model="user.role.id" placeholder="Role">
        <el-option :value="role.id" v-for="role in roles" :key="role.id">{{ role.roleName }}</el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit('user')">Submit</el-button>
      <el-button @click="resetForm('user')">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import AuthService from "@/services/AuthService";

export default {
  name: "Register-Form",
  data() {

    return {

      rules: {
        user_name: [
          {required: true, message: 'Please enter User Name', trigger: 'blur'},
          {min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please enter password', trigger: 'blur'}
        ],
        password_confirm: [
          {required: true, message: 'Please enter password again', trigger: 'blur'}
        ],
        role: [
          {required: true, message: 'Please select Category ', trigger: 'change'}
        ],
      },
      user: {
        user_name: '',
        password: '',
        password_confirm: '',
        role: {
          id: ''
        }
      },
      roles: []
    }

  },
  mounted() {
    AuthService.getRoles().then(response => {
      console.log(response)
      this.roles = response.data
    })
  },
  methods: {
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && this.user.password===this.user.password_confirm) {
          console.log(this.user)
          AuthService.registerUser(this.user)
          alert('Register Successfully!');
          this.$router.push('/login')
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