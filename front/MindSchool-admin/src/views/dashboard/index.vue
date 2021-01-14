<template>
  <div class="dashboard-container">
    <img src="../../../../MindSchool-front/assets/img/logo.png" />
    <div class="dashboard-text">Welcome to MindSchool backend !</div>
    <div class="dashboard-text">用户名:{{ name }}</div>
    <div class="dashboard-text">用户角色:<span v-for="role in roles" :key="role">{{ role }}</span></div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 52px;
    color: #a85a11;
    margin-left: 30px;
    margin-top: 50px;
  }
}
</style>
