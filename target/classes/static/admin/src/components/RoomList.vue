<template>
  <div class="page-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">房间管理</h1>
      <div class="header-actions">
        <button class="btn btn-primary" @click="showAddRoomModal = true">
          <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 5V19M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          添加房间
        </button>
        <button class="btn btn-secondary">
          <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M17 17H17.01M17.4 14H18C18.9319 14 19.3978 14 19.7654 14.1522C20.2554 14.3552 20.6448 14.7446 20.8478 15.2346C21 15.6022 21 16.0681 21 17C21 17.9319 21 18.3978 20.8478 18.7654C20.6448 19.2554 20.2554 19.6448 19.7654 19.8478C19.3978 20 18.9319 20 18 20H6C5.06812 20 4.60218 20 4.23463 19.8478C3.74458 19.6448 3.35523 19.2554 3.15224 18.7654C3 18.3978 3 17.9319 3 17C3 16.0681 3 15.6022 3.15224 15.2346C3.35523 14.7446 3.74458 14.3552 4.23463 14.1522C4.60218 14 5.06812 14 6 14H6.6M12 14H9C7.11438 14 6.17157 14 5.58579 13.4142C5 12.8284 5 11.8856 5 10V8C5 6.11438 5 5.17157 5.58579 4.58579C6.17157 4 7.11438 4 9 4H15C16.8856 4 17.8284 4 18.4142 4.58579C19 5.17157 19 6.11438 19 8V10C19 11.8856 19 12.8284 18.4142 13.4142C17.8284 14 16.8856 14 15 14H12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          打印报表
        </button>
      </div>
    </div>
    
    <!-- 统计卡片 -->
    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon total-icon">
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 12L5 10M5 10L12 3L19 10M5 10V20C5 20.5523 5.44772 21 6 21H9M19 10L21 12M19 10V20C19 20.5523 18.5523 21 18 21H15M9 21C9.55228 21 10 20.5523 10 20V16C10 15.4477 10.4477 15 11 15H13C13.5523 15 14 15.4477 14 16V20C14 20.5523 14.4477 21 15 21M9 21H15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">总房间数</h3>
          <p class="stat-value">{{ totalRooms }}</p>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon available-icon">
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M9 12L11 14L15 10M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">可用房间</h3>
          <p class="stat-value">{{ availableRooms }}</p>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon occupied-icon">
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M16 7C16 9.20914 14.2091 11 12 11C9.79086 11 8 9.20914 8 7C8 4.79086 9.79086 3 12 3C14.2091 3 16 4.79086 16 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M12 14C8.13401 14 5 17.134 5 21H19C19 17.134 15.866 14 12 14Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">已入住</h3>
          <p class="stat-value">{{ occupiedRooms }}</p>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon maintenance-icon">
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M10.3427 14.3427L3.34266 21.3427M10.3427 14.3427L14.3427 10.3427M10.3427 14.3427L6.34266 10.3427L2.34266 6.34266M14.3427 10.3427L21.3427 3.34266M14.3427 10.3427L18.3427 14.3427L22.3427 18.3427" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">维修中</h3>
          <p class="stat-value">{{ maintenanceRooms }}</p>
        </div>
      </div>
    </div>
    
    <!-- 筛选和搜索 -->
    <div class="filter-section">
      <div class="filter-group">
        <label for="room-type" class="filter-label">房间类型</label>
        <select id="room-type" class="filter-select">
          <option value="">全部类型</option>
          <option value="standard">标准间</option>
          <option value="deluxe">豪华间</option>
          <option value="suite">套房</option>
          <option value="family">家庭房</option>
        </select>
      </div>
      
      <div class="filter-group">
        <label for="room-status" class="filter-label">房间状态</label>
        <select id="room-status" class="filter-select">
          <option value="">全部状态</option>
          <option value="available">可用</option>
          <option value="occupied">已入住</option>
          <option value="reserved">已预订</option>
          <option value="maintenance">维修中</option>
          <option value="cleaning">清洁中</option>
        </select>
      </div>
      
      <div class="filter-group">
        <label for="room-floor" class="filter-label">楼层</label>
        <select id="room-floor" class="filter-select">
          <option value="">全部楼层</option>
          <option value="1">1楼</option>
          <option value="2">2楼</option>
          <option value="3">3楼</option>
          <option value="4">4楼</option>
          <option value="5">5楼</option>
        </select>
      </div>
      
      <div class="search-group">
        <input type="text" class="search-input" placeholder="搜索房间号或描述..." v-model="searchQuery">
        <button class="search-button">
          <svg class="search-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
      </div>
    </div>
    
    <!-- 房间列表 -->
    <div class="room-grid">
      <div v-for="room in filteredRooms" :key="room.id || room.roomNumber" class="room-card" :class="getRoomActualStatus(room)">
        <div class="room-header">
          <h3 class="room-number">{{ room.roomNumber }}</h3>
          <span class="room-status-badge">{{ getStatusText(getRoomActualStatus(room)) }}</span>
        </div>
        <div class="room-details">
          <p class="room-type">{{ room.type }}</p>
          <p class="room-price">¥{{ room.price }}/晚</p>
          <p class="room-desc">{{ room.description }}</p>
        </div>
        <div class="room-actions">
          <button class="btn btn-sm btn-outline" @click="viewRoomDetails(room)">查看详情</button>
          <button class="btn btn-sm" :class="getRoomActualStatus(room) === '空闲' ? 'btn-success' : 'btn-secondary'" @click="handleRoomAction(room)">
            {{ getRoomActionText(getRoomActualStatus(room)) }}
          </button>
        </div>
      </div>
    </div>
    
    <!-- 添加房间模态框 -->
    <div class="modal" v-if="showAddRoomModal" @click="closeAddRoomModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h2 class="modal-title">添加新房间</h2>
          <button class="modal-close" @click="showAddRoomModal = false">
            <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label for="new-room-number" class="form-label">房间号</label>
            <input type="text" id="new-room-number" class="form-input" v-model="newRoom.roomNumber">
          </div>
          <div class="form-group">
            <label for="new-room-type" class="form-label">房间类型</label>
            <select id="new-room-type" class="form-select" v-model="newRoom.type">
              <option value="标准间">标准间</option>
              <option value="豪华间">豪华间</option>
              <option value="套房">套房</option>
              <option value="家庭房">家庭房</option>
            </select>
          </div>
          <div class="form-group">
            <label for="new-room-price" class="form-label">价格（每晚）</label>
            <input type="number" id="new-room-price" class="form-input" v-model="newRoom.price">
          </div>
          <div class="form-group">
            <label for="new-room-floor" class="form-label">楼层</label>
            <select id="new-room-floor" class="form-select" v-model="newRoom.floor">
              <option value="1">1楼</option>
              <option value="2">2楼</option>
              <option value="3">3楼</option>
              <option value="4">4楼</option>
              <option value="5">5楼</option>
            </select>
          </div>
          <div class="form-group">
            <label for="new-room-description" class="form-label">描述</label>
            <textarea id="new-room-description" class="form-textarea" v-model="newRoom.description"></textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" @click="showAddRoomModal = false">取消</button>
          <button class="btn btn-primary" @click="addNewRoom">保存</button>
        </div>
      </div>
    </div>

    <!-- 房间详情模态框 -->
    <div class="modal" v-if="showRoomDetailModal" @click="closeRoomDetailModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h2 class="modal-title">房间详情 - {{ currentRoomDetail.roomNumber }}</h2>
          <button class="modal-close" @click="showRoomDetailModal = false">
            <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </button>
        </div>
        <div class="modal-body">
          <div class="room-detail-info">
            <div class="room-detail-row">
              <span class="detail-label">类型:</span>
              <span class="detail-value">{{ currentRoomDetail.type }}</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">价格:</span>
              <span class="detail-value">¥{{ currentRoomDetail.price }}/晚</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">楼层:</span>
              <span class="detail-value">{{ currentRoomDetail.floor }}楼</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">状态:</span>
              <span class="detail-value">{{ getStatusText(getRoomActualStatus(currentRoomDetail)) }}</span>
            </div>
          </div>
          
          <div class="room-guest-info" v-if="currentBookingDetail">
            <h3 class="guest-info-title">当前入住信息</h3>
            <div class="room-detail-row">
              <span class="detail-label">客人:</span>
              <span class="detail-value">{{ currentBookingDetail.customerName }}</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">入住时间:</span>
              <span class="detail-value">{{ currentBookingDetail.checkinDate }}</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">离店时间:</span>
              <span class="detail-value">{{ currentBookingDetail.checkoutDate }}</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">预订状态:</span>
              <span class="detail-value">{{ getBookingStatusText(currentBookingDetail.status) }}</span>
            </div>
            <div class="room-detail-row">
              <span class="detail-label">支付状态:</span>
              <span class="detail-value payment-badge" :class="currentBookingDetail.paymentStatus === 'paid' || currentBookingDetail.paymentStatus === '已付款' ? 'paid' : 'unpaid'">
                {{ getPaymentStatusText(currentBookingDetail.paymentStatus) }}
              </span>
            </div>
          </div>
          
          <div class="no-guest-info" v-else>
            <p class="no-guest-text">当前无客人入住</p>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-sm" :class="getRoomActualStatus(currentRoomDetail) === '空闲' ? 'btn-success' : 'btn-secondary'" @click="handleRoomAction(currentRoomDetail)">
            {{ getRoomActionText(getRoomActualStatus(currentRoomDetail)) }}
          </button>
          <button class="btn btn-secondary btn-sm" @click="showRoomDetailModal = false">关闭</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import api from '../api'
import { useRouter } from 'vue-router'
import axios from 'axios'

// 搜索查询
const searchQuery = ref('')

// 添加房间模态框
const showAddRoomModal = ref(false)
const newRoom = ref({
  roomNumber: '',
  type: '标准间',
  price: 299,
  floor: 1,
  description: '',
  status: '空闲'
})

// 房间数据
const rooms = ref([])

// axios 请求自动带 token
axios.interceptors.request.use(config => {
  const user = JSON.parse(localStorage.getItem('user') || sessionStorage.getItem('user') || 'null')
  if (user && user.token) {
    config.headers.Authorization = `Bearer ${user.token}`
  }
  return config
})

// 拉取房间数据
const fetchRooms = async () => {
  try {
    const res = await api.get('/api/rooms')
    rooms.value = Array.isArray(res.data) ? res.data : (res.data.data || [])
  } catch (e) {
    if (e.response && (e.response.status === 401 || e.response.status === 403)) {
      router.push('/login')
    }
  }
}

onMounted(() => {
  const user = localStorage.getItem('user') || sessionStorage.getItem('user')
  if (!user) {
    router.push('/login')
    return
  }
  fetchRooms()
  fetchBookings()
  
  // 设置定时器，每60秒刷新一次预订状态
  const refreshInterval = setInterval(() => {
    fetchBookings()
  }, 60000)
  
  // 页面卸载时清除定时器
  return () => clearInterval(refreshInterval)
})

// 根据搜索过滤房间
const filteredRooms = computed(() => {
  return (rooms.value || []).filter(room => room && room.roomNumber)
    .filter(room => {
      if (!searchQuery.value) return true
      const query = searchQuery.value.toLowerCase()
      return (
        room.roomNumber.toLowerCase().includes(query) ||
        (room.type && room.type.toLowerCase().includes(query))
      )
    })
})

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    '空闲': '可用',
    '已入住': '已入住',
    '已预订': '已预订',
    '维修': '维修中',
    'cleaning': '清洁中'
  }
  return statusMap[status] || status
}

// 获取操作按钮文本
const getRoomActionText = (status) => {
  const actionMap = {
    '空闲': '预订',
    '已入住': '查看',
    '已预订': '入住',
    '维修': '完成维修',
    'cleaning': '完成清洁'
  }
  return actionMap[status] || '操作'
}

// 查看房间详情
const router = useRouter()
const showRoomDetailModal = ref(false)
const currentRoomDetail = ref({})
const currentBookingDetail = ref(null)

const viewRoomDetails = async (room) => {
  currentRoomDetail.value = { ...room }
  
  // 刷新预订数据以确保最新状态
  try {
    const res = await axios.get('/api/bookings')
    bookings.value = res.data || []
  } catch (e) {
    console.error('获取预订数据失败', e)
  }
  
  // 查找当前入住的客人
  const today = new Date().toISOString().slice(0, 10)
  currentBookingDetail.value = bookings.value.find(booking => {
    return booking.roomId === room.id && 
           booking.checkinDate <= today && 
           booking.checkoutDate >= today
  })
  
  showRoomDetailModal.value = true
}

// 获取预订数据
const bookings = ref([])
const fetchBookings = async () => {
  try {
    const res = await axios.get('/api/bookings')
    bookings.value = res.data || []
  } catch (e) {
    console.error('获取预订数据失败', e)
}
}

// 处理房间操作
const handleRoomAction = (room) => {
  const status = getRoomActualStatus(room)
  
  if (status === '空闲') {
    // 空闲房间可以预订，跳转到预订页面
    router.push({
      path: '/bookings',
      query: { action: 'create', roomId: room.id, roomNumber: room.roomNumber }
    })
  } else if (status === '已入住' || status === '已预订') {
    // 已入住或已预订，查看相关预订
    const booking = bookings.value.find(b => b.roomId === room.id)
    if (booking) {
      // 打开预订详情页面
      router.push(`/bookings?id=${booking.id}`)
    } else {
      alert('未找到该房间的预订信息')
    }
  } else if (status === '维修' || status === 'cleaning') {
    // 完成维修或清洁，可以在这里添加更改房间状态的逻辑
    if (confirm(`确定${status === '维修' ? '完成维修' : '完成清洁'}吗？`)) {
      // 更新房间状态
      const updatedRoom = { ...room, status: '空闲' }
      api.put(`/room/${room.id}`, updatedRoom)
        .then(() => {
          fetchRooms()
        })
        .catch(err => {
          alert('操作失败: ' + err.message)
        })
    }
  }
}

// 检查房间实际状态（根据预订状态）
const getRoomActualStatus = (room) => {
  // 如果房间处于维修中或清洁中，直接返回其状态
  if (room.status === '维修' || room.status === 'cleaning') {
    return room.status
  }
  
  // 查找当前日期的房间入住情况
  const today = new Date().toISOString().slice(0, 10)
  const activeBooking = bookings.value.find(booking => {
    return booking.roomId === room.id && 
           (booking.status === 'checked-in' || booking.status === '已入住') &&
           booking.checkinDate <= today && 
           booking.checkoutDate >= today
  })
  
  if (activeBooking) {
    return '已入住'
  }
  
  // 查找预订但未入住的情况
  const upcomingBooking = bookings.value.find(booking => {
    return booking.roomId === room.id && 
           (booking.status === 'confirmed' || booking.status === 'pending') &&
           booking.checkinDate >= today
  })
  
  if (upcomingBooking) {
    return '已预订'
  }
  
  return '空闲'
}

// 添加新房间
const addNewRoom = async () => {
  await api.post('/room', newRoom.value)
  fetchRooms()
  newRoom.value = {
    roomNumber: '',
    type: '标准间',
    price: 299,
    floor: 1,
    description: '',
    status: '空闲'
  }
  showAddRoomModal.value = false
}

// 获取预订状态文本
const getBookingStatusText = (status) => {
  const statusMap = {
    'pending': '待确认',
    'confirmed': '已确认',
    'checked-in': '已入住',
    'checked-out': '已退房',
    'cancelled': '已取消'
  }
  return statusMap[status] || status
}

// 获取支付状态文本
const getPaymentStatusText = (status) => {
  const statusMap = {
    'paid': '已付款',
    'unpaid': '未付款',
    'partial': '部分付款',
    'refunded': '已退款',
    '已付款': '已付款',
    '未付款': '未付款'
  }
  return statusMap[status] || status
}

// 获取房间的预订信息
const getRoomBookingInfo = (room) => {
  if (getRoomActualStatus(room) !== '已入住') return null
  
  const today = new Date().toISOString().slice(0, 10)
  return bookings.value.find(booking => {
    return booking.roomId === room.id && 
           (booking.status === 'checked-in' || booking.status === '已入住') &&
           booking.checkinDate <= today && 
           booking.checkoutDate >= today
  })
}

// 删除房间（如有需要可补充）

const totalRooms = computed(() => rooms.value.length)
const availableRooms = computed(() => rooms.value.filter(room => getRoomActualStatus(room) === '空闲').length)
const occupiedRooms = computed(() => rooms.value.filter(room => getRoomActualStatus(room) === '已入住').length)
const maintenanceRooms = computed(() => rooms.value.filter(room => getRoomActualStatus(room) === '维修').length)

// 关闭房间详情模态框（点击外部区域）
const closeRoomDetailModal = () => {
  showRoomDetailModal.value = false
}

// 关闭添加房间模态框（点击外部区域）
const closeAddRoomModal = () => {
  showAddRoomModal.value = false
}
</script>

<style scoped>
/* 页面容器 */
.page-container {
  padding: 1.5rem;
  max-width: 1200px;
  margin: 0 auto;
}

/* 页面标题 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.page-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.header-actions {
  display: flex;
  gap: 0.75rem;
}

/* 按钮样式 */
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
}

.btn-icon {
  width: 1rem;
  height: 1rem;
  margin-right: 0.5rem;
}

.btn-primary {
  background-color: #e11d48;
  color: white;
}

.btn-primary:hover {
  background-color: #be123c;
}

.btn-secondary {
  background-color: #f1f5f9;
  color: #1e293b;
  border: 1px solid #e2e8f0;
}

.btn-secondary:hover {
  background-color: #e2e8f0;
}

.btn-success {
  background-color: #10b981;
  color: white;
}

.btn-success:hover {
  background-color: #059669;
}

.btn-outline {
  background-color: transparent;
  border: 1px solid #cbd5e1;
  color: #64748b;
}

.btn-outline:hover {
  background-color: #f8fafc;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.75rem;
}

/* 统计卡片 */
.stats-row {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.stat-card {
  background-color: white;
  border-radius: 0.5rem;
  padding: 1rem;
  display: flex;
  align-items: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 0.375rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1rem;
}

.total-icon {
  background-color: #ede9fe;
  color: #6d28d9;
}

.available-icon {
  background-color: #dcfce7;
  color: #16a34a;
}

.occupied-icon {
  background-color: #fef3c7;
  color: #d97706;
}

.maintenance-icon {
  background-color: #fee2e2;
  color: #dc2626;
}

.stat-svg {
  width: 1.25rem;
  height: 1.25rem;
}

.stat-info {
  flex: 1;
}

.stat-title {
  font-size: 0.875rem;
  color: #64748b;
  margin: 0 0 0.25rem 0;
}

.stat-value {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

/* 筛选和搜索 */
.filter-section {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 1.5rem;
  background-color: white;
  padding: 1rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.filter-group {
  display: flex;
  flex-direction: column;
  min-width: 150px;
}

.filter-label {
  font-size: 0.75rem;
  color: #64748b;
  margin-bottom: 0.25rem;
}

.filter-select {
  padding: 0.5rem;
  border: 1px solid #cbd5e1;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  color: #1e293b;
}

.search-group {
  display: flex;
  align-items: center;
  flex: 1;
  min-width: 200px;
}

.search-input {
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #cbd5e1;
  border-radius: 0.375rem 0 0 0.375rem;
  font-size: 0.875rem;
}

.search-button {
  background-color: #e11d48;
  color: white;
  border: none;
  padding: 0.5rem;
  border-radius: 0 0.375rem 0.375rem 0;
  cursor: pointer;
}

.search-icon {
  width: 1rem;
  height: 1rem;
}

/* 房间列表 */
.room-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.room-card {
  background-color: white;
  border-radius: 0.5rem;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  border-top: 4px solid #94a3b8;
}

.room-card.空闲 {
  border-top-color: #10b981;
}

.room-card.已入住 {
  border-top-color: #f59e0b;
}

.room-card.已预订 {
  border-top-color: #6366f1;
}

.room-card.维修 {
  border-top-color: #ef4444;
}

.room-card.cleaning {
  border-top-color: #3b82f6;
}

.room-header {
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e2e8f0;
}

.room-number {
  font-size: 1.125rem;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.room-status-badge {
  font-size: 0.75rem;
  padding: 0.25rem 0.5rem;
  border-radius: 9999px;
  background-color: #f1f5f9;
  color: #64748b;
}

.空闲 .room-status-badge {
  background-color: #dcfce7;
  color: #16a34a;
}

.已入住 .room-status-badge {
  background-color: #fef3c7;
  color: #d97706;
}

.已预订 .room-status-badge {
  background-color: #e0e7ff;
  color: #4f46e5;
}

.维修 .room-status-badge {
  background-color: #fee2e2;
  color: #dc2626;
}

.cleaning .room-status-badge {
  background-color: #dbeafe;
  color: #2563eb;
}

.room-details {
  padding: 1rem;
}

.room-type {
  font-size: 0.875rem;
  color: #1e293b;
  margin: 0 0 0.5rem 0;
}

.room-price {
  font-size: 1rem;
  font-weight: 600;
  color: #e11d48;
  margin: 0 0 0.5rem 0;
}

.room-desc {
  font-size: 0.875rem;
  color: #64748b;
  margin: 0;
}

.room-booking-info {
  margin-top: 0.75rem;
  padding-top: 0.75rem;
  border-top: 1px dashed #e2e8f0;
}

.room-guest {
  font-size: 0.875rem;
  font-weight: 500;
  color: #1e293b;
  margin: 0 0 0.25rem 0;
}

.room-dates {
  font-size: 0.75rem;
  color: #64748b;
  margin: 0 0 0.5rem 0;
}

.payment-badge {
  display: inline-block;
  padding: 0.2rem 0.5rem;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 500;
}

.payment-badge.paid {
  background-color: #dcfce7;
  color: #16a34a;
}

.payment-badge.unpaid {
  background-color: #fee2e2;
  color: #dc2626;
}

.room-actions {
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  border-top: 1px solid #e2e8f0;
}

/* 模态框 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  cursor: pointer;
}

.modal-content {
  background-color: white;
  border-radius: 0.5rem;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  cursor: default;
}

.modal-header {
  padding: 0.75rem 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e2e8f0;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.modal-close {
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
}

.modal-close-icon {
  width: 1.25rem;
  height: 1.25rem;
}

.modal-body {
  padding: 0.75rem 1rem;
  overflow: hidden;
}

.modal-footer {
  padding: 0.75rem 1rem;
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  border-top: 1px solid #e2e8f0;
}

/* 表单样式 */
.form-group {
  margin-bottom: 0.75rem;
}

.form-row {
  display: flex;
  margin-left: -0.5rem;
  margin-right: -0.5rem;
  flex-wrap: wrap;
}

.form-group-half {
  flex: 0 0 50%;
  padding: 0 0.5rem;
  min-width: 150px;
  margin-bottom: 0.5rem;
}

.form-label {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  color: #1e293b;
  margin-bottom: 0.25rem;
}

.form-input,
.form-select,
.form-textarea {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #cbd5e1;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  color: #1e293b;
}

.form-textarea {
  min-height: 60px;
  resize: none;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .stats-row {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 640px) {
  .stats-row {
    grid-template-columns: 1fr;
  }
  
  .filter-section {
    flex-direction: column;
  }
  
  .filter-group,
  .search-group {
    width: 100%;
  }
}

/* Add these styles at the end of your CSS */
.room-detail-info, .room-guest-info {
  margin-bottom: 0.75rem;
}

.guest-info-title {
  font-size: 1rem;
  font-weight: 600;
  margin: 0.5rem 0;
  color: #1e293b;
}

.room-detail-row {
  display: flex;
  margin-bottom: 0.5rem;
  font-size: 0.875rem;
  align-items: center;
}

.detail-label {
  width: 80px;
  color: #64748b;
  font-weight: 500;
}

.detail-value {
  color: #1e293b;
}

.no-guest-text {
  text-align: center;
  color: #64748b;
  font-style: italic;
  margin: 1rem 0;
}
</style>