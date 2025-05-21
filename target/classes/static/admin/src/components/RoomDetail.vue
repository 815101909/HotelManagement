<template>
  <div class="room-detail-container" v-if="room">
    <h2 class="room-title">房间详情</h2>
    <div class="room-info">
      <p><strong>房号：</strong>{{ room.roomNumber }}</p>
      <p><strong>类型：</strong>{{ room.type }}</p>
      <p><strong>状态：</strong>{{ room.status }}</p>
      <p><strong>价格：</strong>¥{{ room.price }}/晚</p>
      <p><strong>楼层：</strong>{{ room.floor }}</p>
      <p><strong>描述：</strong>{{ room.description }}</p>
    </div>
    <button class="btn" @click="goBack">返回</button>
  </div>
  <div v-else class="loading">加载中...</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '../api'

const route = useRoute()
const router = useRouter()
const room = ref(null)

const fetchRoom = async () => {
  const res = await api.get(`/room/${route.params.id}`)
  room.value = res.data
}

const goBack = () => {
  router.back()
}

onMounted(fetchRoom)
</script>

<style scoped>
.room-detail-container {
  max-width: 500px;
  margin: 2rem auto;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 2rem;
}
.room-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
  text-align: center;
}
.room-info p {
  font-size: 1.1rem;
  margin: 0.5rem 0;
}
.btn {
  margin-top: 2rem;
  background: #e11d48;
  color: #fff;
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
}
.btn:hover {
  background: #be123c;
}
.loading {
  text-align: center;
  margin-top: 3rem;
  color: #888;
}
</style> 