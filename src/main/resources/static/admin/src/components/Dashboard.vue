<template>
  <div class="dashboard-content">
    <!-- 加载状态 -->
    <div class="loading-overlay" v-if="isLoading">
      <div class="spinner"></div>
      <p>加载中...</p>
    </div>
    
    <!-- 错误提示 -->
    <div class="error-message" v-if="error">
      <p>{{ error }}</p>
    </div>

    <!-- 统计卡片 -->
    <div class="stats-grid" v-if="!isLoading && !error">
      <div class="stat-card">
        <div class="stat-icon room-icon">
          <!-- 房间图标 -->
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 21H21M3 18H21M6 18V13M18 18V13M3 13H21M12 13V8M6 13V8M18 13V8M3 8H21M9 8V3H15V8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">总房间数</h3>
          <p class="stat-value">{{ roomStats.total }}</p>
          <p class="stat-desc">可用: {{ roomStats.available }}</p>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon booking-icon">
          <!-- 预订图标 -->
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M8 7V3M16 7V3M7 11H17M5 21H19C20.1046 21 21 20.1046 21 19V7C21 5.89543 20.1046 5 19 5H5C3.89543 5 3 5.89543 3 7V19C3 20.1046 3.89543 21 5 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">今日预订</h3>
          <p class="stat-value">{{ bookingStats.today }}</p>
          <p class="stat-desc">入住: {{ bookingStats.checkIn }} | 退房: {{ bookingStats.checkOut }}</p>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon customer-icon">
          <!-- 客户图标 -->
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M17 21V19C17 17.9391 16.5786 16.9217 15.8284 16.1716C15.0783 15.4214 14.0609 15 13 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21M23 21V19C22.9993 18.1137 22.7044 17.2528 22.1614 16.5523C21.6184 15.8519 20.8581 15.3516 20 15.13M16 3.13C16.8604 3.3503 17.623 3.8507 18.1676 4.55231C18.7122 5.25392 19.0078 6.11683 19.0078 7.005C19.0078 7.89317 18.7122 8.75608 18.1676 9.45769C17.623 10.1593 16.8604 10.6597 16 10.88M13 7C13 9.20914 11.2091 11 9 11C6.79086 11 5 9.20914 5 7C5 4.79086 6.79086 3 9 3C11.2091 3 13 4.79086 13 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">客户总数</h3>
          <p class="stat-value">{{ customerStats.total }}</p>
          <p class="stat-desc">本月新增: {{ customerStats.newThisMonth }}</p>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon revenue-icon">
          <!-- 收入图标 -->
          <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 1V23M17 5H9.5C8.57174 5 7.6815 5.36875 7.02513 6.02513C6.36875 6.6815 6 7.57174 6 8.5C6 9.42826 6.36875 10.3185 7.02513 10.9749C7.6815 11.6313 8.57174 12 9.5 12H14.5C15.4283 12 16.3185 12.3687 16.9749 13.0251C17.6313 13.6815 18 14.5717 18 15.5C18 16.4283 17.6313 17.3185 16.9749 17.9749C16.3185 18.6313 15.4283 19 14.5 19H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <div class="stat-info">
          <h3 class="stat-title">本月收入</h3>
          <p class="stat-value">¥{{ (revenueStats.monthlyAmount || 0).toLocaleString() }}</p>
          <p class="stat-desc">同比增长: {{ revenueStats.yearOverYearGrowth }}%</p>
        </div>
      </div>
    </div>
    
    <!-- 图表区域 -->
    <div class="charts-container" v-if="!isLoading && !error">
      <div class="chart-card">
        <h3 class="chart-title">入住率趋势</h3>
        <div class="chart-placeholder">
          <div class="chart-bars">
            <div v-for="(rate, index) in occupancyTrend" :key="index" class="chart-bar" :style="{height: rate + '%'}"></div>
          </div>
          <div class="chart-labels">
            <span v-for="(day, index) in weekdays" :key="index">{{ day }}</span>
          </div>
        </div>
      </div>
      
      <div class="chart-card">
        <h3 class="chart-title">收入来源</h3>
        <div class="pie-chart-container">
          <div class="pie-chart">
            <div class="pie-conic" :style="{
              background: getConicGradient(),
              transform: 'rotate(-90deg)'
            }"></div>
          </div>
          <div class="pie-legend">
            <div v-for="source in revenueSources" 
                 :key="source.name" 
                 class="legend-item">
              <span class="legend-color" :style="{ backgroundColor: source.color }"></span>
              <span class="legend-text">{{ source.name }} ({{ source.percentage }}%)</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 最近活动 -->
    <div class="recent-activity" v-if="!isLoading && !error">
      <h3 class="section-title">最近活动</h3>
      <div class="activity-list">
        <div v-for="(activity, index) in recentActivities" :key="index" class="activity-item">
          <div :class="['activity-icon', activity.type]">
            <!-- 活动图标 -->
            <svg class="activity-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path v-if="activity.type === 'check-in'" d="M9 11L12 14L22 4M21 12V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H16" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path v-if="activity.type === 'check-out'" d="M9 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H9M16 17L21 12M21 12L16 7M21 12H9" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path v-if="activity.type === 'booking'" d="M8 7V3M16 7V3M7 11H17M5 21H19C20.1046 21 21 20.1046 21 19V7C21 5.89543 20.1046 5 19 5H5C3.89543 5 3 5.89543 3 7V19C3 20.1046 3.89543 21 5 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path v-if="activity.type === 'payment'" d="M17 9V7C17 5.89543 16.1046 5 15 5H5C3.89543 5 3 5.89543 3 7V13C3 14.1046 3.89543 15 5 15H7M9 19H19C20.1046 19 21 18.1046 21 17V11C21 9.89543 20.1046 9 19 9H9C7.89543 9 7 9.89543 7 11V17C7 18.1046 7.89543 19 9 19ZM16 14C16 14.5523 15.5523 15 15 15C14.4477 15 14 14.5523 14 14C14 13.4477 14.4477 13 15 13C15.5523 13 16 13.4477 16 14Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="activity-content">
            <h4 class="activity-title">{{ activity.title }}</h4>
            <p class="activity-desc">{{ activity.description }}</p>
            <p class="activity-time">{{ activity.time }}</p>
          </div>
        </div>

        <div v-if="recentActivities.length === 0" class="no-data-message">
          暂无最近活动
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// 加载状态和错误信息
const isLoading = ref(true)
const error = ref('')

// 初始化数据
const roomStats = ref({
  total: 0,
  available: 0,
  booked: 0
})

const bookingStats = ref({
  today: 0,
  checkIn: 0,
  checkOut: 0
})

const customerStats = ref({
  total: 0,
  newThisMonth: 0
})

const revenueStats = ref({
  monthlyAmount: 0,
  yearOverYearGrowth: 0
})

const occupancyTrend = ref([])
const weekdays = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']

const revenueSources = ref([])

const recentActivities = ref([])

// 获取本月第一天和最后一天
const getMonthRange = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = now.getMonth() + 1
  const firstDay = `${year}-${String(month).padStart(2, '0')}-01`
  const lastDay = `${year}-${String(month).padStart(2, '0')}-${String(new Date(year, month, 0).getDate()).padStart(2, '0')}`
  return { firstDay, lastDay }
}

// 格式化日期为后端需要的格式
const formatDateForBackend = (date) => {
  const d = new Date(date)
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const hours = String(d.getHours()).padStart(2, '0')
  const minutes = String(d.getMinutes()).padStart(2, '0')
  const seconds = String(d.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 获取仪表盘数据
const fetchDashboardData = async () => {
  isLoading.value = true
  error.value = ''
  
  try {
    // 房间统计数据
    const roomResponse = await axios.get('/api/rooms/stats')
    roomStats.value = roomResponse.data
    
    // 预订统计数据 - 直接获取所有预订
    const bookingResponse = await axios.get('/api/bookings')
    const allBookings = bookingResponse.data || []
    
    // 计算今日预订统计
    const today = new Date().toISOString().slice(0, 10)
    console.log('今日日期:', today)
    console.log('所有预订:', allBookings)
    
    const checkedInBookings = allBookings.filter(b => b.status === 'checked-in')
    console.log('已入住预订:', checkedInBookings)
    
    const todayCheckedIn = checkedInBookings.filter(b => b.checkinDate === today)
    console.log('今日已入住:', todayCheckedIn)
    
    bookingStats.value = {
      today: allBookings.filter(b => b.checkinDate === today).length,  // 今日预计入住
      checkIn: todayCheckedIn.length,  // 今日已入住数量
      checkOut: allBookings.filter(b => b.status === 'checked-out' && b.checkoutDate === today).length  // 今日实际已退房
    }
    
    console.log('统计结果:', bookingStats.value)
    
    // 客户统计数据
    const customerResponse = await axios.get('/api/admin/customers/stats')
    customerStats.value = customerResponse.data
    
    // 收入统计数据（统一用月度趋势接口）
    const trendRes = await axios.get('/api/admin/finance/trend/monthly')
    const monthNames = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
    const now = new Date()
    const thisMonth = now.getMonth()
    let monthlyAmount = 0
    if (trendRes.data && Array.isArray(trendRes.data)) {
      const thisMonthData = trendRes.data.find(item => item.month === monthNames[thisMonth])
      monthlyAmount = thisMonthData ? Number(thisMonthData.income || 0) : 0
    }
    revenueStats.value = {
      monthlyAmount,
      yearOverYearGrowth: 0
    }
    
    // 入住率趋势
    const occupancyResponse = await axios.get('/api/admin/rooms/occupancy/weekly')
    occupancyTrend.value = occupancyResponse.data.occupancyRates || []
    
    // 收入来源数据
    const sourceResponse = await axios.get('/api/admin/finance/income-sources')
    console.log('收入来源数据:', sourceResponse.data)
    
    const colors = ['#4f46e5', '#10b981', '#f59e0b', '#ef4444']
    
    if (sourceResponse.data && Array.isArray(sourceResponse.data)) {
      // 创建收入记录数据
      const now = new Date()
      const incomeData = {
        category: '标准间',
        description: '房间收入',
        amount: 299.99,
        date: now.toISOString().split('T')[0], // 只使用日期部分：YYYY-MM-DD
        status: 'PAID'
      }
      
      console.log('发送的收入数据:', incomeData)
      
      try {
        // 使用同步预订接口
        const syncResponse = await axios.post('/api/admin/finance/sync-bookings')
        console.log('同步预订结果:', syncResponse.data)
        
        // 刷新数据
        const refreshResponse = await axios.get('/api/admin/finance/income-sources')
        if (refreshResponse.data && Array.isArray(refreshResponse.data)) {
          revenueSources.value = refreshResponse.data.map((source, index) => ({
            name: source.name,
            percentage: source.percentage || 0,
            color: colors[index % colors.length]
          }))
        }
      } catch (err) {
        console.error('操作失败:', err.response?.data || err.message)
      }
    }
    
    // 最近活动
    const activitiesResponse = await axios.get('/api/admin/activities/recent')
    recentActivities.value = activitiesResponse.data.map(activity => ({
      type: activity.type.toLowerCase(),
      title: activity.title,
      description: activity.description,
      time: formatActivityTime(activity.timestamp)
    }))
    
  } catch (err) {
    console.error('获取仪表盘数据失败:', err)
    error.value = '获取仪表盘数据失败，请稍后再试'
  } finally {
    isLoading.value = false
  }
}

// 格式化活动时间
const formatActivityTime = (timestamp) => {
  const now = new Date()
  const activityTime = new Date(timestamp)
  const diff = Math.floor((now - activityTime) / 1000 / 60) // 分钟差
  
  if (diff < 1) return '刚刚'
  if (diff < 60) return `${diff}分钟前`
  
  const hourDiff = Math.floor(diff / 60)
  if (hourDiff < 24) return `${hourDiff}小时前`
  
  const dayDiff = Math.floor(hourDiff / 24)
  if (dayDiff < 30) return `${dayDiff}天前`
  
  return activityTime.toLocaleDateString()
}

// 计算总百分比
const getPreviousSegmentsTotal = (index) => {
  return revenueSources.value
    .slice(0, index)
    .reduce((total, source) => total + source.percentage, 0);
}

const getConicGradient = () => {
  let gradient = 'conic-gradient(';
  let currentPercentage = 0;
  
  revenueSources.value.forEach((source, index) => {
    if (index > 0) gradient += ', ';
    gradient += `${source.color} ${currentPercentage}% ${currentPercentage + source.percentage}%`;
    currentPercentage += source.percentage;
  });
  
  gradient += ')';
  return gradient;
}

// 页面加载时获取数据
onMounted(() => {
  fetchDashboardData()
})

async function addTestBooking() {
  try {
    const now = new Date();
    const tomorrow = new Date(now);
    tomorrow.setDate(tomorrow.getDate() + 1);

    // 格式化日期为 "yyyy-MM-dd" 格式
    const formatDate = (date) => {
      const pad = (num) => String(num).padStart(2, '0');
      const year = date.getFullYear();
      const month = pad(date.getMonth() + 1);
      const day = pad(date.getDate());
      return `${year}-${month}-${day}`;
    };

    const bookingData = {
      customerId: 1,  // 测试用客户ID
      roomId: 1,      // 测试用房间ID
      checkinDate: formatDate(now),
      checkoutDate: formatDate(tomorrow),
      totalPrice: 400.00,
      status: "booked",
      paymentStatus: "unpaid"
      // 移除 createdAt，让后端自动处理
    };

    console.log('发送预订数据:', bookingData);
    
    const response = await fetch('/api/bookings', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(bookingData)
    });

    const responseText = await response.text();
    console.log('服务器响应:', response.status, responseText);

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}, body: ${responseText}`);
    }

    let result;
    try {
      result = JSON.parse(responseText);
    } catch (e) {
      console.warn('响应不是JSON格式:', responseText);
      result = responseText;
    }

    console.log('预订创建成功:', result);
    
    // 刷新数据
    await fetchDashboardData();
  } catch (error) {
    console.error('创建预订失败:', error);
    console.error('错误详情:', error.message);
    if (error.response) {
      console.error('响应状态:', error.response.status);
      const text = await error.response.text();
      console.error('响应内容:', text);
    }
  }
}
</script>

<style scoped>
@import './dashboard-styles.css';

/* 加载状态和错误提示样式 */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.8);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #e2e8f0;
  border-top-color: #e11d48;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error-message {
  padding: 1rem;
  margin: 1rem 0;
  background-color: #fee2e2;
  color: #dc2626;
  border-radius: 0.5rem;
  border-left: 4px solid #dc2626;
}

.no-data-message {
  text-align: center;
  padding: 2rem;
  color: #64748b;
  font-style: italic;
}

/* 饼图样式 */
.pie-chart-container {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  padding: 1rem;
  gap: 2rem;
}

.pie-chart {
  position: relative;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  overflow: hidden;
  background: #fff;
  padding: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.pie-conic {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 2px solid white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: background 0.3s ease;
}

.pie-legend {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.legend-item {
  display: flex;
  align-items: center;
  font-size: 0.875rem;
  color: #1e293b;
}

.legend-color {
  width: 12px;
  height: 12px;
  border-radius: 2px;
  margin-right: 0.5rem;
}

.legend-text {
  color: #1e293b;
}

.chart-card {
  background: white;
  border-radius: 0.5rem;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.chart-title {
  font-size: 1.125rem;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 1rem;
  text-align: center;
}
</style>