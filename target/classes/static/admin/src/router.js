import { createRouter, createWebHistory } from 'vue-router'
import Login from './components/Login.vue'
import Register from './components/Register.vue'
import ForgotPassword from './components/ForgotPassword.vue'
import ResetPassword from './components/ResetPassword.vue'
import Dashboard from './components/Dashboard.vue'
import RoomList from './components/RoomList.vue'
import Booking from './components/Booking.vue'
import Customs from './components/Customs.vue'
import FinancialReports from './components/FinancialReports.vue'
import Settings from './components/setting.vue'
import Layout from './components/Layout.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/forgot-password', component: ForgotPassword },
  { path: '/reset-password', component: ResetPassword },
  {
    path: '/',
    component: Layout,
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'rooms', component: RoomList },
      { path: 'rooms/:id', component: () => import('./components/RoomDetail.vue') },
      { path: 'bookings', component: Booking },
      { path: 'customers', component: Customs },
      { path: '/finance', component: FinancialReports },
      { path: '/settings', component: Settings },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 登录持久化：未登录自动跳转到登录页
router.beforeEach((to, from, next) => {
  const user = localStorage.getItem('user')
  if (!user && to.path !== '/login') {
    next('/login')
  } else {
    next()
  }
})

export default router 