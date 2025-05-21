<script setup>
import { ref } from 'vue';
import EmployeeList from './components/EmployeeList.vue';
import RoomList from './components/RoomList.vue';
import Login from './components/Login.vue';
import Dashboard from './components/Dashboard.vue';

const view = ref('dashboard');
const isLogin = ref(false);

function handleLoginSuccess() {
  isLogin.value = true;
}
</script>

<template>
  <router-view />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
nav { margin-bottom: 20px; }
button { margin-right: 10px; }
</style>
