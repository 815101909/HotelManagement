<template>
    <div class="page-container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1 class="page-title">预订管理</h1>
        <div class="header-actions">
          <button class="btn btn-primary" @click="showAddBookingModal = true">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 5V19M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            新增预订
          </button>
          <button class="btn btn-secondary" @click="showCalendarModal = true">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M8 7V3M16 7V3M7 11H17M5 21H19C20.1046 21 21 20.1046 21 19V7C21 5.89543 20.1046 5 19 5H5C3.89543 5 3 5.89543 3 7V19C3 20.1046 3.89543 21 5 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            查看日历
          </button>
        </div>
      </div>
      
      <!-- 统计卡片 -->
      <div class="stats-row">
        <div class="stat-card">
          <div class="stat-icon total-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M8 7V3M16 7V3M7 11H17M5 21H19C20.1046 21 21 20.1046 21 19V7C21 5.89543 20.1046 5 19 5H5C3.89543 5 3 5.89543 3 7V19C3 20.1046 3.89543 21 5 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">总预订数</h3>
            <p class="stat-value">{{ totalBookingCount }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon today-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M9 11L12 14L22 4M21 12V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H16" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">今日入住</h3>
            <p class="stat-value">{{ todayCheckinCount }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon checkout-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M9 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H9M16 17L21 12M21 12L16 7M21 12H9" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">今日退房</h3>
            <p class="stat-value">{{ todayCheckoutCount }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon pending-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 8V12L15 15M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">待确认</h3>
            <p class="stat-value">{{ pendingCount }}</p>
          </div>
        </div>
      </div>
      
      <!-- 筛选和搜索 -->
      <div class="filter-section">
        <div class="filter-group">
          <label for="booking-status" class="filter-label">预订状态</label>
          <select id="booking-status" class="filter-select" v-model="filters.status">
            <option value="">全部状态</option>
            <option value="confirmed">已确认</option>
            <option value="pending">待确认</option>
            <option value="checked-in">已入住</option>
            <option value="checked-out">已退房</option>
            <option value="cancelled">已取消</option>
          </select>
        </div>
        
        <div class="filter-group">
          <label for="date-range" class="filter-label">日期范围</label>
          <select id="date-range" class="filter-select" v-model="filters.dateRange">
            <option value="all">全部</option>
            <option value="today">今天</option>
            <option value="this-week">本周</option>
            <option value="this-month">本月</option>
            <option value="this-year">本年</option>
          </select>
        </div>
        
        <div class="date-picker-group" v-if="filters.dateRange === 'custom'">
          <div class="date-input-group">
            <label for="start-date" class="filter-label">开始日期</label>
            <input type="date" id="start-date" class="date-input" v-model="filters.startDate">
          </div>
          <div class="date-input-group">
            <label for="end-date" class="filter-label">结束日期</label>
            <input type="date" id="end-date" class="date-input" v-model="filters.endDate">
          </div>
        </div>
        
        <div class="search-group">
          <input type="text" class="search-input" placeholder="搜索预订号、客人姓名..." v-model="searchQuery">
          <button class="search-button">
            <svg class="search-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </button>
        </div>
      </div>
      
      <!-- 预订列表 -->
      <div class="booking-table-container">
        <table class="booking-table">
          <thead>
            <tr>
              <th>预订号</th>
              <th>客人姓名</th>
              <th>房间</th>
              <th>入住日期</th>
              <th>退房日期</th>
              <th>状态</th>
              <th>支付状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="booking in filteredBookings" :key="booking.id" :class="booking.status">
              <td>{{ booking.id }}</td>
              <td>{{ booking.customerName }}</td>
              <td>{{ booking.roomNumber }}</td>
              <td>{{ booking.checkinDate }}</td>
              <td>{{ booking.checkoutDate }}</td>
              <td>
                <span class="status-badge" :class="booking.status">
                  {{ getStatusText(booking.status) }}
                </span>
              </td>
              <td>
                <span class="payment-badge" :class="normalizePaymentStatus(booking.paymentStatus)">
                  {{ getPaymentStatusText(booking.paymentStatus) }}
                </span>
              </td>
              <td>
                <div class="table-actions">
                  <button class="action-btn view-btn" @click="viewBookingDetails(booking)" title="查看详情">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                      <path d="M2.45825 12C3.73253 7.94288 7.52281 5 12.0004 5C16.4781 5 20.2684 7.94291 21.5426 12C20.2684 16.0571 16.4781 19 12.0005 19C7.52281 19 3.73251 16.0571 2.45825 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                  <button class="action-btn edit-btn" @click="editBooking(booking)" title="编辑">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13M18.5 2.5C18.8978 2.10217 19.4374 1.87868 20 1.87868C20.5626 1.87868 21.1022 2.10217 21.5 2.5C21.8978 2.89782 22.1213 3.43739 22.1213 4C22.1213 4.56261 21.8978 5.10217 21.5 5.5L12 15L8 16L9 12L18.5 2.5Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                  <button class="action-btn" :class="getActionButtonClass(booking)" @click="handleBookingAction(booking)" :title="getActionButtonTitle(booking)">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path :d="getActionButtonIcon(booking)" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                  <button class="action-btn delete-btn" @click="deleteBooking(booking.id)" title="删除">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M3 6H5H21M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <!-- 分页 -->
      <div class="pagination">
        <button class="pagination-btn" :disabled="currentPage === 1" @click="currentPage--">
          <svg class="pagination-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
        <span class="pagination-info">第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
        <button class="pagination-btn" :disabled="currentPage === totalPages" @click="currentPage++">
          <svg class="pagination-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
      </div>
      
      <!-- 添加预订模态框 -->
      <div class="modal" v-if="showAddBookingModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">新增预订</h2>
            <button class="modal-close" @click="showAddBookingModal = false">
              <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="form-label">客户姓名</label>
              <input class="form-input" v-model="newBooking.customerName" placeholder="请输入客户姓名" />
            </div>
            <div class="form-group">
              <label class="form-label">房间</label>
              <select class="form-select" v-model="newBooking.roomId">
                <option value="">请选择房间</option>
                <option v-for="r in rooms" :key="r.id" :value="r.id">{{ r.roomNumber }}</option>
              </select>
            </div>
            <div class="form-row">
              <div class="form-group form-group-half">
                <label class="form-label">入住日期</label>
                <input type="date" class="form-input" v-model="newBooking.checkinDate" />
              </div>
              <div class="form-group form-group-half">
                <label class="form-label">退房日期</label>
                <input type="date" class="form-input" v-model="newBooking.checkoutDate" />
              </div>
            </div>
            <div class="form-group">
              <label class="form-label">总价 (¥)</label>
              <input type="number" class="form-input" v-model="newBooking.totalPrice" min="0" step="0.01" />
            </div>
            <div class="form-row">
              <div class="form-group form-group-half">
                <label class="form-label">订单状态</label>
                <select class="form-select" v-model="newBooking.status">
                  <option value="confirmed">已确认</option>
                  <option value="pending">待确认</option>
                  <option value="checked-in">已入住</option>
                  <option value="checked-out">已退房</option>
                  <option value="cancelled">已取消</option>
                </select>
              </div>
              <div class="form-group form-group-half">
                <label class="form-label">支付状态</label>
                <select class="form-select" v-model="newBooking.paymentStatus">
                  <option value="unpaid">未付款</option>
                  <option value="paid">已付款</option>
                  <option value="partial">部分付款</option>
                  <option value="refunded">已退款</option>
                </select>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showAddBookingModal = false">取消</button>
            <button class="btn btn-primary" @click="addNewBooking">保存</button>
          </div>
        </div>
      </div>
      
      <!-- 编辑预订模态框 -->
      <div class="modal" v-if="showEditBookingModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">编辑预订</h2>
            <button class="modal-close" @click="showEditBookingModal = false">
              <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="form-label">客人姓名</label>
              <input type="text" class="form-input" v-model="editBookingData.customerName" disabled />
            </div>
            <div class="form-row">
              <div class="form-group form-group-half">
                <label class="form-label">入住日期</label>
                <input type="date" class="form-input" v-model="editBookingData.checkinDate" />
              </div>
              <div class="form-group form-group-half">
                <label class="form-label">退房日期</label>
                <input type="date" class="form-input" v-model="editBookingData.checkoutDate" />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group form-group-half">
                <label class="form-label">房间号</label>
                <input type="text" class="form-input" v-model="editBookingData.roomNumber" disabled />
              </div>
              <div class="form-group form-group-half">
                <label class="form-label">支付状态</label>
                <select class="form-select" v-model="editBookingData.paymentStatus">
                  <option value="unpaid">未付款</option>
                  <option value="paid">已付款</option>
                  <option value="partial">部分付款</option>
                  <option value="refunded">已退款</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label">状态</label>
              <select class="form-select" v-model="editBookingData.status">
                <option value="confirmed">已确认</option>
                <option value="pending">待确认</option>
                <option value="checked-in">已入住</option>
                <option value="checked-out">已退房</option>
                <option value="cancelled">已取消</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">总价 (¥)</label>
              <input type="number" class="form-input" v-model="editBookingData.totalPrice" min="0" step="0.01" />
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showEditBookingModal = false">取消</button>
            <button class="btn btn-primary" @click="updateBooking">保存</button>
          </div>
        </div>
      </div>
      
      <!-- 日历视图模态框 -->
      <div class="modal" v-if="showCalendarModal">
        <div class="modal-content calendar-modal">
          <div class="modal-header">
            <h2 class="modal-title">预订日历</h2>
            <button class="modal-close" @click="showCalendarModal = false">
              <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="calendar-controls">
              <button class="btn btn-secondary" @click="previousMonth">
                <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                上个月
              </button>
              <h3 class="calendar-title">{{ calendarYear }}年{{ calendarMonth + 1 }}月</h3>
              <button class="btn btn-secondary" @click="nextMonth">
                下个月
                <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </button>
            </div>
            <div class="calendar">
              <div class="calendar-header">
                <span>星期日</span>
                <span>星期一</span>
                <span>星期二</span>
                <span>星期三</span>
                <span>星期四</span>
                <span>星期五</span>
                <span>星期六</span>
              </div>
              <div class="calendar-body">
                <div v-for="(week, weekIndex) in calendarDays" :key="'week-' + weekIndex" class="calendar-week">
                  <div v-for="(day, dayIndex) in week" :key="'day-' + weekIndex + '-' + dayIndex" 
                       class="calendar-day" 
                       :class="{ 
                         'other-month': day.otherMonth, 
                         'today': day.isToday,
                         'has-bookings': day.bookings.length > 0 
                       }">
                    <div class="day-number">{{ day.day }}</div>
                    <div v-if="day.bookings.length > 0" class="day-bookings">
                      <div v-for="booking in day.bookings" :key="booking.id" 
                           class="day-booking" 
                           :class="booking.status"
                           @click="viewBookingDetails(booking)">
                        {{ booking.roomNumber }}: {{ booking.customerName }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <div class="calendar-legend">
              <div class="legend-item">
                <span class="legend-color confirmed"></span>
                <span class="legend-text">已确认</span>
              </div>
              <div class="legend-item">
                <span class="legend-color checked-in"></span>
                <span class="legend-text">已入住</span>
              </div>
              <div class="legend-item">
                <span class="legend-color checked-out"></span>
                <span class="legend-text">已退房</span>
              </div>
              <div class="legend-item">
                <span class="legend-color pending"></span>
                <span class="legend-text">待确认</span>
              </div>
              <div class="legend-item">
                <span class="legend-color cancelled"></span>
                <span class="legend-text">已取消</span>
              </div>
            </div>
            <button class="btn btn-secondary" @click="showCalendarModal = false">关闭</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import axios from 'axios'
  
  // 搜索查询
  const searchQuery = ref('')
  
  // 当前页码
  const currentPage = ref(1)
  const pageSize = 10
  const totalPages = ref(1)
  
  // 筛选条件
  const filters = ref({
    status: '',
    dateRange: 'all',
    startDate: '',
    endDate: ''
  })
  
  // 添加预订模态框
  const showAddBookingModal = ref(false)
  const newBooking = ref({
    customerName: '',
    customerId: '',
    roomId: '',
    checkinDate: '',
    checkoutDate: '',
    status: 'pending',
    paymentStatus: 'unpaid',
    totalPrice: 0,
    createdAt: new Date().toISOString() // 使用正确的字段名 createdAt
  })
  
  // 日历视图
  const showCalendarModal = ref(false)
  const calendarYear = ref(new Date().getFullYear())
  const calendarMonth = ref(new Date().getMonth())
  
  // 日历导航
  const previousMonth = () => {
    if (calendarMonth.value === 0) {
      calendarMonth.value = 11
      calendarYear.value--
    } else {
      calendarMonth.value--
    }
  }
  
  const nextMonth = () => {
    if (calendarMonth.value === 11) {
      calendarMonth.value = 0
      calendarYear.value++
    } else {
      calendarMonth.value++
    }
  }
  
  // 生成日历数据
  const calendarDays = computed(() => {
    const year = calendarYear.value
    const month = calendarMonth.value
    
    // 当月第一天
    const firstDay = new Date(year, month, 1)
    // 当月最后一天
    const lastDay = new Date(year, month + 1, 0)
    
    // 当月第一天是星期几 (0-6，0表示星期日)
    const firstDayOfWeek = firstDay.getDay()
    
    // 当月的天数
    const daysInMonth = lastDay.getDate()
    
    // 上个月最后几天
    const daysFromPrevMonth = firstDayOfWeek
    const prevMonthLastDay = new Date(year, month, 0).getDate()
    
    const days = []
    let week = []
    
    // 填充上个月的日期
    for (let i = 0; i < daysFromPrevMonth; i++) {
      const day = prevMonthLastDay - daysFromPrevMonth + i + 1
      const date = new Date(year, month - 1, day)
      week.push({
        day,
        date,
        otherMonth: true,
        isToday: isSameDay(date, new Date()),
        bookings: getBookingsForDay(date)
      })
    }
    
    // 填充当月的日期
    for (let i = 1; i <= daysInMonth; i++) {
      const date = new Date(year, month, i)
      week.push({
        day: i,
        date,
        otherMonth: false,
        isToday: isSameDay(date, new Date()),
        bookings: getBookingsForDay(date)
      })
      
      // 如果是星期六或者最后一天，创建新的一周
      if (week.length === 7 || i === daysInMonth) {
        days.push(week)
        week = []
      }
    }
    
    // 如果最后一周不满，填充下个月的日期
    if (week.length > 0) {
      const daysToAdd = 7 - week.length
      for (let i = 1; i <= daysToAdd; i++) {
        const date = new Date(year, month + 1, i)
        week.push({
          day: i,
          date,
          otherMonth: true,
          isToday: isSameDay(date, new Date()),
          bookings: getBookingsForDay(date)
        })
      }
      days.push(week)
    }
    
    return days
  })
  
  // 检查两个日期是否是同一天
  const isSameDay = (date1, date2) => {
    return date1.getFullYear() === date2.getFullYear() &&
           date1.getMonth() === date2.getMonth() &&
           date1.getDate() === date2.getDate()
  }
  
  // 获取指定日期的预订
  const getBookingsForDay = (date) => {
    const dateStr = date.toISOString().slice(0, 10)
    return bookings.value.filter(booking => {
      const checkin = booking.checkinDate
      const checkout = booking.checkoutDate
      
      // 处理入住日期等于当天的情况
      if (checkin === dateStr) {
        return true
      }
      
      // 处理退房日期等于当天的情况
      if (checkout === dateStr) {
        return true
      }
      
      // 处理住宿期间的情况（在入住日期之后，退房日期之前）
      if (checkin < dateStr && checkout > dateStr) {
        return true
      }
      
      return false
    })
  }
  
  // 新增预订表单下拉数据（假设已通过接口获取）
  const customers = ref([])
  const rooms = ref([])
  
  // 页面加载时获取客户和房间列表
  const fetchCustomersAndRooms = async () => {
    try {
      const [customerRes, roomRes] = await Promise.all([
        axios.get('/api/customers'),
        axios.get('/api/rooms')
      ])
      customers.value = customerRes.data || []
      rooms.value = roomRes.data || []
    } catch (e) {
      // 可选：处理错误
    }
  }
  
  // 预订数据
  const bookings = ref([])
  const loading = ref(false)
  const error = ref('')
  
  // 获取所有预订
  const fetchBookings = async () => {
    loading.value = true
    error.value = ''
    try {
      const res = await axios.get('/api/bookings')
      bookings.value = res.data || []
      // 计算总页数
      totalPages.value = Math.max(1, Math.ceil(bookings.value.length / pageSize))
      if (currentPage.value > totalPages.value) currentPage.value = totalPages.value
    } catch (e) {
      error.value = e.response?.data?.message || '获取预订数据失败'
    } finally {
      loading.value = false
    }
  }
  
  onMounted(() => {
    fetchBookings()
    fetchCustomersAndRooms()
    
    // 检查是否从房间页面跳转过来
    const route = window.location.search
    const params = new URLSearchParams(route)
    if (params.get('action') === 'create' && params.get('roomId')) {
      // 从房间列表点击预订跳转过来
      showAddBookingModal.value = true
      const roomId = params.get('roomId')
      if (roomId) {
        newBooking.value.roomId = roomId
        // 设置入住日期为今天
        const today = new Date().toISOString().slice(0, 10)
        newBooking.value.checkinDate = today
        // 设置退房日期为明天
        const tomorrow = new Date()
        tomorrow.setDate(tomorrow.getDate() + 1)
        newBooking.value.checkoutDate = tomorrow.toISOString().slice(0, 10)
      }
    } else if (params.get('id')) {
      // 查看指定预订
      const bookingId = params.get('id')
      const booking = bookings.value.find(b => b.id == bookingId)
      if (booking) {
        editBooking(booking)
      }
    }
  })
  
  // 删除预订
  const deleteBooking = async (id) => {
    if (!confirm('确定要删除该预订吗？')) return
    try {
      await axios.delete(`/api/bookings/${id}`)
      await fetchBookings()
    } catch (e) {
      error.value = e.response?.data?.message || '删除失败'
    }
  }
  
  // 新增预订
  const addNewBooking = async () => {
    // 校验必填项
    if (!newBooking.value.customerName) {
      error.value = '请输入客户姓名'
      alert(error.value)
      error.value = ''
      return
    }
    if (!newBooking.value.roomId) {
      error.value = '请选择房间'
      alert(error.value)
      error.value = ''
      return
    }
    if (!newBooking.value.checkinDate) {
      error.value = '请选择入住日期'
      alert(error.value)
      error.value = ''
      return
    }
    if (!newBooking.value.checkoutDate) {
      error.value = '请选择退房日期'
      alert(error.value)
      error.value = ''
      return
    }
    if (!newBooking.value.totalPrice || Number(newBooking.value.totalPrice) <= 0) {
      error.value = '请输入有效的总价'
      alert(error.value)
      error.value = ''
      return
    }
    try {
      let customerId = ''
      // 1. 检查客户名是否已存在
      const exist = customers.value.find(c => c.name === newBooking.value.customerName)
      if (exist) {
        customerId = exist.id
      } else {
        // 2. 不存在则先新增客户
        const customerData = { 
          name: newBooking.value.customerName,
          type: 'regular' // 确保新客户默认为普通客户类型
        }
        console.log('创建新客户:', customerData)
        const res = await axios.post('/api/customers', customerData)
        customerId = res.data.id || res.data // 兼容返回结构
        await fetchCustomersAndRooms()
      }
      // 3. 新增预订，roomId 强制转数字
      const bookingData = {
        customerId,
        roomId: Number(newBooking.value.roomId),
        checkinDate: formatDateForBackend(newBooking.value.checkinDate),
        checkoutDate: formatDateForBackend(newBooking.value.checkoutDate),
        status: newBooking.value.status,
        paymentStatus: newBooking.value.paymentStatus,
        totalPrice: newBooking.value.totalPrice,
        createdAt: newBooking.value.createdAt // 使用正确的字段名 createdAt
      }
      
      // 打印格式化后的数据，用于调试
      console.log('新增预订数据:', JSON.stringify(bookingData));
      console.log('入住日期格式:', bookingData.checkinDate);
      console.log('退房日期格式:', bookingData.checkoutDate);
      
      await axios.post('/api/bookings', bookingData)
      showAddBookingModal.value = false
      // 重置表单
      newBooking.value = {
        customerName: '',
        customerId: '',
        roomId: '',
        checkinDate: '',
        checkoutDate: '',
        status: 'pending',
        paymentStatus: 'unpaid',
        totalPrice: 0,
        createdAt: new Date().toISOString() // 使用正确的字段名 createdAt
      }
      await fetchBookings()
    } catch (e) {
      error.value = e.response?.data?.message || '添加失败'
      alert(error.value)
      error.value = ''
    }
  }
  
  // 分页、筛选、搜索
  const filteredBookings = computed(() => {
    let result = bookings.value

    // 状态筛选
    if (filters.value.status) {
      result = result.filter(booking => booking.status === filters.value.status)
    }

    // 日期范围筛选
    const today = new Date()
    if (filters.value.dateRange === 'today') {
      const todayStr = today.toISOString().slice(0, 10)
      result = result.filter(b => b.checkinDate === todayStr)
    } else if (filters.value.dateRange === 'this-week') {
      const day = today.getDay() || 7
      const weekStart = new Date(today)
      weekStart.setDate(today.getDate() - day + 1)
      const weekEnd = new Date(weekStart)
      weekEnd.setDate(weekStart.getDate() + 6)
      const weekStartStr = weekStart.toISOString().slice(0, 10)
      const weekEndStr = weekEnd.toISOString().slice(0, 10)
      result = result.filter(b => b.checkinDate >= weekStartStr && b.checkinDate <= weekEndStr)
    } else if (filters.value.dateRange === 'this-month') {
      const monthStart = new Date(today.getFullYear(), today.getMonth(), 1)
      const monthEnd = new Date(today.getFullYear(), today.getMonth() + 1, 0)
      const monthStartStr = monthStart.toISOString().slice(0, 10)
      const monthEndStr = monthEnd.toISOString().slice(0, 10)
      result = result.filter(b => b.checkinDate >= monthStartStr && b.checkinDate <= monthEndStr)
    } else if (filters.value.dateRange === 'this-year') {
      const yearStart = new Date(today.getFullYear(), 0, 1)
      const yearEnd = new Date(today.getFullYear(), 11, 31)
      const yearStartStr = yearStart.toISOString().slice(0, 10)
      const yearEndStr = yearEnd.toISOString().slice(0, 10)
      result = result.filter(b => b.checkinDate >= yearStartStr && b.checkinDate <= yearEndStr)
    }
    // "全部"不做任何筛选

    // 搜索
    if (searchQuery.value) {
      const query = searchQuery.value.toLowerCase()
      result = result.filter(booking =>
        (booking.id + '').toLowerCase().includes(query) ||
        (booking.customerName || '').toLowerCase().includes(query) ||
        (booking.roomNumber + '').toLowerCase().includes(query)
      )
    }
    // 分页
    const start = (currentPage.value - 1) * pageSize
    const end = start + pageSize
    return result.slice(start, end)
  })
  
  // 统计卡片数据
  const totalBookingCount = computed(() => bookings.value.length)
  const todayCheckinCount = computed(() => {
    const today = new Date().toISOString().slice(0, 10)
    return bookings.value.filter(b => b.status === 'checked-in' && b.checkinDate === today).length
  })
  const todayCheckoutCount = computed(() => {
    const today = new Date().toISOString().slice(0, 10)
    return bookings.value.filter(b => b.checkoutDate === today).length
  })
  const pendingCount = computed(() => bookings.value.filter(b => b.status === 'pending').length)
  
  // 获取状态文本
  const getStatusText = (status) => {
    const statusMap = {
      'confirmed': '已确认',
      'pending': '待确认',
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
  
  // 归一化支付状态，确保CSS类名一致
  const normalizePaymentStatus = (status) => {
    if (status === '已付款' || status === 'paid') {
      return 'paid'
    } else if (status === '未付款' || status === 'unpaid') {
      return 'unpaid'
    } else if (status === '部分付款' || status === 'partial') {
      return 'partial'
    } else if (status === '已退款' || status === 'refunded') {
      return 'refunded'
    }
    return status
  }
  
  // 获取操作按钮类
  const getActionButtonClass = (booking) => {
    const classMap = {
      'confirmed': 'check-in-btn',
      'pending': 'confirm-btn',
      'checked-in': 'check-out-btn',
      'checked-out': 'invoice-btn',
      'cancelled': 'restore-btn'
    }
    return classMap[booking.status] || ''
  }
  
  // 获取操作按钮标题
  const getActionButtonTitle = (booking) => {
    const titleMap = {
      'confirmed': '办理入住',
      'pending': '确认预订',
      'checked-in': '办理退房',
      'checked-out': '查看账单',
      'cancelled': '恢复预订'
    }
    return titleMap[booking.status] || '操作'
  }
  
  // 获取操作按钮图标
  const getActionButtonIcon = (booking) => {
    const iconMap = {
      'confirmed': 'M9 11L12 14L22 4M21 12V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H16',
      'pending': 'M9 12L11 14L15 10M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12Z',
      'checked-in': 'M9 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H9M16 17L21 12M21 12L16 7M21 12H9',
      'checked-out': 'M17 17H17.01M17.4 14H18C18.9319 14 19.3978 14 19.7654 14.1522C20.2554 14.3552 20.6448 14.7446 20.8478 15.2346C21 15.6022 21 16.0681 21 17C21 17.9319 21 18.3978 20.8478 18.7654C20.6448 19.2554 20.2554 19.6448 19.7654 19.8478C19.3978 20 18.9319 20 18 20H6C5.06812 20 4.60218 20 4.23463 19.8478C3.74458 19.6448 3.35523 19.2554 3.15224 18.7654C3 18.3978 3 17.9319 3 17C3 16.0681 3 15.6022 3.15224 15.2346C3.35523 14.7446 3.74458 14.3552 4.23463 14.1522C4.60218 14 5.06812 14 6 14H6.6',
      'cancelled': 'M3 12L5 10M5 10L12 3L19 10M5 10V20C5 20.5523 5.44772 21 6 21H9M19 10L21 12M19 10V20C19 20.5523 18.5523 21 18 21H15M9 21C9.55228 21 10 20.5523 10 20V16C10 15.4477 10.4477 15 11 15H13C13.5523 15 14 15.4477 14 16V20C14 20.5523 14.4477 21 15 21M9 21H15'
    }
    return iconMap[booking.status] || ''
  }
  
  // 查看预订详情
  const viewBookingDetails = (booking) => {
    // 这里可以实现查看详情的逻辑
    alert('查看预订详情: ' + JSON.stringify(booking, null, 2))
  }
  
  // 编辑预订
  const showEditBookingModal = ref(false)
  const editBookingData = ref({})
  const editBooking = (booking) => {
    // 深拷贝，带上 id、customerId、roomId
    editBookingData.value = {
      id: booking.id,
      customerId: booking.customerId, // 需要后端VO返回该字段
      roomId: booking.roomId,         // 需要后端VO返回该字段
      checkinDate: booking.checkinDate,
      checkoutDate: booking.checkoutDate,
      status: booking.status,
      paymentStatus: booking.paymentStatus,
      totalPrice: booking.totalPrice  // 添加总价字段
    }
    // 只读显示
    editBookingData.value.customerName = booking.customerName
    editBookingData.value.roomNumber = booking.roomNumber
    showEditBookingModal.value = true
  }
  
  // 格式化日期，确保包含时间部分，用于发送到后端
  const formatDateForBackend = (dateStr) => {
    if (!dateStr) return '';
    
    // 如果是日期字符串 (YYYY-MM-DD)
    if (typeof dateStr === 'string' && dateStr.length === 10) {
      // 使用application.yml中配置的格式：yyyy-MM-dd HH:mm:ss
      return `${dateStr} 00:00:00`;
    }
    
    // 如果是Date对象
    if (dateStr instanceof Date) {
      // 格式化为yyyy-MM-dd HH:mm:ss
      const year = dateStr.getFullYear();
      const month = String(dateStr.getMonth() + 1).padStart(2, '0');
      const day = String(dateStr.getDate()).padStart(2, '0');
      return `${year}-${month}-${day} 00:00:00`;
    }
    
    // 如果已经是带时间的字符串，但格式不匹配
    if (typeof dateStr === 'string' && dateStr.includes('T')) {
      try {
        const date = new Date(dateStr);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day} 00:00:00`;
      } catch (e) {
        console.error('日期格式转换错误:', e);
      }
    }
    
    // 其他情况返回原值
    return dateStr;
  }
  
  const updateBooking = async () => {
    try {
      console.log('Updating booking with data:', editBookingData.value)
      
      // 确保支付状态格式正确
      let paymentStatus = editBookingData.value.paymentStatus
      // 检查是否需要标准化支付状态 (确保后端能识别的值)
      if (paymentStatus === '已付款') {
        paymentStatus = 'paid'
      } else if (paymentStatus === '未付款') {
        paymentStatus = 'unpaid'
      }
      
      // 只提交后端需要的字段
      const data = {
        id: editBookingData.value.id,
        customerId: editBookingData.value.customerId,
        roomId: editBookingData.value.roomId,
        checkinDate: formatDateForBackend(editBookingData.value.checkinDate),
        checkoutDate: formatDateForBackend(editBookingData.value.checkoutDate),
        status: editBookingData.value.status,
        paymentStatus: paymentStatus,
        totalPrice: editBookingData.value.totalPrice
      }
      
      // 打印格式化后的数据，用于调试
      console.log('发送到后端的数据:', JSON.stringify(data));
      console.log('入住日期格式:', data.checkinDate);
      console.log('退房日期格式:', data.checkoutDate);
      
      await axios.put('/api/bookings', data)
      showEditBookingModal.value = false
      await fetchBookings()
    } catch (e) {
      error.value = e.response?.data?.message || '更新失败'
      console.error('更新失败:', e)
      // 显示详细错误信息
      if (e.response?.data) {
        console.error('后端返回的错误:', e.response.data);
      }
    }
  }
  
  // 处理预订操作
  const handleBookingAction = async (booking) => {
    try {
      let newStatus = booking.status;
      
      // 根据当前状态决定下一个状态
      if (booking.status === 'confirmed') {
        // 办理入住
        newStatus = 'checked-in';
        if (!confirm('确定要为该客人办理入住吗？')) return;
      } else if (booking.status === 'checked-in') {
        // 办理退房
        newStatus = 'checked-out';
        if (!confirm('确定要为该客人办理退房吗？')) return;
      } else if (booking.status === 'pending') {
        // 确认预订
        newStatus = 'confirmed';
        if (!confirm('确定要确认该预订吗？')) return;
      } else if (booking.status === 'cancelled') {
        // 恢复预订
        newStatus = 'pending';
        if (!confirm('确定要恢复该预订吗？')) return;
      } else {
        // 其他情况，显示详情
        alert('预订操作: ' + JSON.stringify(booking, null, 2));
        return;
      }
      
      // 如果状态有变化，则更新
      if (newStatus !== booking.status) {
        const data = {
          id: booking.id,
          customerId: booking.customerId,
          roomId: booking.roomId,
          checkinDate: formatDateForBackend(booking.checkinDate),
          checkoutDate: formatDateForBackend(booking.checkoutDate),
          status: newStatus,
          paymentStatus: booking.paymentStatus,
          totalPrice: booking.totalPrice
        };
        
        // 打印格式化后的数据，用于调试
        console.log('状态变更数据:', JSON.stringify(data));
        console.log('入住日期格式:', data.checkinDate);
        console.log('退房日期格式:', data.checkoutDate);
        
        await axios.put('/api/bookings', data);
        await fetchBookings();
      }
    } catch (e) {
      error.value = e.response?.data?.message || '操作失败';
      console.error('操作失败:', e);
    }
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
  
  .today-icon {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .checkout-icon {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .pending-icon {
    background-color: #fef3c7;
    color: #d97706;
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
    align-items: center;
    justify-content: flex-end;
  }
  
  .filter-group {
    display: flex;
    flex-direction: column;
    min-width: 150px;
    margin-bottom: 0;
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
  
  .date-picker-group {
    display: flex;
    gap: 1rem;
  }
  
  .date-input-group {
    display: flex;
    flex-direction: column;
  }
  
  .date-input {
    padding: 0.5rem;
    border: 1px solid #cbd5e1;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    color: #1e293b;
  }
  
  .search-group {
    display: flex;
    align-items: center;
    min-width: 200px;
    margin-left: auto;
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
  
  /* 预订表格 */
  .booking-table-container {
    background-color: white;
    border-radius: 0.5rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    margin-bottom: 1.5rem;
    overflow-x: auto;
  }
  
  .booking-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .booking-table th {
    background-color: #f8fafc;
    padding: 0.75rem 1rem;
    text-align: left;
    font-size: 0.875rem;
    font-weight: 600;
    color: #64748b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .booking-table td {
    padding: 0.75rem 1rem;
    font-size: 0.875rem;
    color: #1e293b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .booking-table tr:last-child td {
    border-bottom: none;
  }
  
  .booking-table tr.checked-in {
    background-color: #f0fdf4;
  }
  
  .booking-table tr.checked-out {
    background-color: #f8fafc;
  }
  
  .booking-table tr.cancelled {
    background-color: #fef2f2;
  }
  
  .status-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  .status-badge.confirmed {
    background-color: #e0e7ff;
    color: #4f46e5;
  }
  
  .status-badge.pending {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  .status-badge.checked-in {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .status-badge.checked-out {
    background-color: #f1f5f9;
    color: #64748b;
  }
  
  .status-badge.cancelled {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .payment-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
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
  
  .payment-badge.partial {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  .payment-badge.refunded {
    background-color: #f1f5f9;
    color: #64748b;
  }
  
  .table-actions {
    display: flex;
    gap: 0.5rem;
  }
  
  .action-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 1.75rem;
    height: 1.75rem;
    border-radius: 0.375rem;
    background-color: #f8fafc;
    border: 1px solid #e2e8f0;
    color: #64748b;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .action-btn:hover {
    background-color: #f1f5f9;
  }
  
  .action-icon {
    width: 1rem;
    height: 1rem;
  }
  
  .view-btn {
    color: #6d28d9;
  }
  
  .edit-btn {
    color: #2563eb;
  }
  
  .check-in-btn {
    color: #16a34a;
  }
  
  .check-out-btn {
    color: #dc2626;
  }
  
  .confirm-btn {
    color: #d97706;
  }
  
  .invoice-btn {
    color: #6d28d9;
  }
  
  .restore-btn {
    color: #64748b;
  }
  
  /* 添加删除按钮样式 */
  .delete-btn {
    color: #ef4444;
  }
  
  .delete-btn:hover {
    background-color: #fee2e2;
  }
  
  /* 分页 */
  .pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 1.5rem;
  }
  
  .pagination-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 2rem;
    height: 2rem;
    border-radius: 0.375rem;
    background-color: white;
    border: 1px solid #cbd5e1;
    color: #1e293b;
    cursor: pointer;
  }
  
  .pagination-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  
  .pagination-icon {
    width: 1rem;
    height: 1rem;
  }
  
  .pagination-info {
    margin: 0 1rem;
    font-size: 0.875rem;
    color: #64748b;
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
  }
  
  .modal-content {
    background-color: white;
    border-radius: 0.5rem;
    width: 100%;
    max-width: 600px;
    max-height: 90vh;
    overflow-y: auto;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  
  .modal-header {
    padding: 1rem;
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
    padding: 1rem;
  }
  
  .modal-footer {
    padding: 1rem;
    display: flex;
    justify-content: flex-end;
    gap: 0.75rem;
    border-top: 1px solid #e2e8f0;
  }
  
  /* 表单样式 */
  .form-group {
    margin-bottom: 1rem;
  }
  
  .form-row {
    display: flex;
    gap: 1rem;
    margin-bottom: 1rem;
  }
  
  .form-group-half {
    flex: 1;
    margin-bottom: 0;
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
    min-height: 100px;
    resize: vertical;
  }
  
  /* 响应式设计 */
  @media (max-width: 1024px) {
    .stats-row {
      grid-template-columns: repeat(2, 1fr);
    }
    
    .form-row {
      flex-direction: column;
      gap: 1rem;
    }
    
    .form-group-half {
      width: 100%;
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
    .search-group,
    .date-picker-group {
      width: 100%;
    }
    
    .date-picker-group {
      flex-direction: column;
      gap: 1rem;
    }
  }
  
  /* 日历样式 */
  .calendar-modal {
    max-width: 900px;
    width: 90%;
  }
  
  .calendar-controls {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
  }
  
  .calendar-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin: 0;
  }
  
  .calendar {
    border: 1px solid #e2e8f0;
    border-radius: 0.5rem;
    overflow: hidden;
  }
  
  .calendar-header {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    background-color: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .calendar-header span {
    padding: 0.75rem;
    text-align: center;
    font-size: 0.875rem;
    font-weight: 600;
    color: #64748b;
  }
  
  .calendar-body {
    background-color: white;
  }
  
  .calendar-week {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    border-bottom: 1px solid #e2e8f0;
  }
  
  .calendar-week:last-child {
    border-bottom: none;
  }
  
  .calendar-day {
    min-height: 100px;
    border-right: 1px solid #e2e8f0;
    padding: 0.5rem;
    position: relative;
  }
  
  .calendar-day:last-child {
    border-right: none;
  }
  
  .calendar-day.other-month {
    background-color: #f8fafc;
    color: #94a3b8;
  }
  
  .calendar-day.today {
    background-color: #eff6ff;
  }
  
  .day-number {
    font-size: 0.875rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
  }
  
  .day-bookings {
    display: flex;
    flex-direction: column;
    gap: 0.25rem;
  }
  
  .day-booking {
    font-size: 0.75rem;
    padding: 0.25rem;
    border-radius: 0.25rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    cursor: pointer;
  }
  
  .day-booking.confirmed {
    background-color: #e0e7ff;
    color: #4f46e5;
  }
  
  .day-booking.checked-in {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .day-booking.checked-out {
    background-color: #f1f5f9;
    color: #64748b;
  }
  
  .day-booking.pending {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  .day-booking.cancelled {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .calendar-legend {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    margin-right: auto;
  }
  
  .legend-item {
    display: flex;
    align-items: center;
    gap: 0.25rem;
  }
  
  .legend-color {
    width: 1rem;
    height: 1rem;
    border-radius: 0.25rem;
  }
  
  .legend-color.confirmed {
    background-color: #e0e7ff;
  }
  
  .legend-color.checked-in {
    background-color: #dcfce7;
  }
  
  .legend-color.checked-out {
    background-color: #f1f5f9;
  }
  
  .legend-color.pending {
    background-color: #fef3c7;
  }
  
  .legend-color.cancelled {
    background-color: #fee2e2;
  }
  
  .legend-text {
    font-size: 0.75rem;
    color: #64748b;
  }
  </style>