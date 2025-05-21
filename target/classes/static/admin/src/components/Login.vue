<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <div class="logo">
          <!-- 简单的房子图标 -->
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 9L12 2L21 9V20C21 20.5304 20.7893 21.0391 20.4142 21.4142C20.0391 21.7893 19.5304 22 19 22H5C4.46957 22 3.96086 21.7893 3.58579 21.4142C3.21071 21.0391 3 20.5304 3 20V9Z" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M9 22V12H15V22" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h1>酒店管理系统</h1>
        <p>请登录您的账号</p>
      </div>
      
      <div class="login-form">
        <form @submit.prevent="handleLogin">
          <div class="form-group">
            <label for="username">用户名 / 邮箱</label>
            <input 
              id="username" 
              v-model="username" 
              type="text" 
              required
            />
          </div>
          
          <div class="form-group">
            <label for="password">密码</label>
            <input 
              id="password" 
              v-model="password" 
              :type="showPassword ? 'text' : 'password'" 
              required
            />
            <div class="password-options">
              <div class="show-password">
                <input 
                  id="show-password" 
                  type="checkbox" 
                  v-model="showPassword"
                />
                <label for="show-password">显示密码</label>
              </div>
              <a href="#" class="forgot-password">忘记密码?</a>
            </div>
          </div>
          
          <div class="form-group remember-me">
            <input 
              id="remember-me" 
              type="checkbox" 
              v-model="rememberMe"
            />
            <label for="remember-me">记住我</label>
          </div>
          
          <div class="form-group">
            <button 
              type="submit" 
              :disabled="isLoading"
              class="login-button"
            >
              {{ isLoading ? '登录中...' : '登录' }}
            </button>
          </div>
          
          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>
          
          <div class="register-link">
            <p>
              还没有账号? 
              <a href="#" @click.prevent="$emit('switch-to-register')">
                注册新账号
              </a>
            </p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
// 表单数据
const username = ref('')
const password = ref('')
const rememberMe = ref(false)
const showPassword = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')

// 定义事件
const emit = defineEmits(['login-success', 'switch-to-register'])

// 登录处理
const handleLogin = async () => {
  if (!username.value || !password.value) {
    errorMessage.value = '请输入用户名和密码'
    return
  }
  
  isLoading.value = true
  errorMessage.value = ''
  
  try {
    const res = await axios.post('/api/login', {
      username: username.value,
      password: password.value
    })
    if (res.data) {
      localStorage.setItem('user', JSON.stringify(res.data))
      // 跳转到仪表盘或首页
      router.push('/dashboard')
    }
  } catch (error) {
    errorMessage.value = error.response?.data?.message || '登录失败'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background-color: #f9fafb;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 1rem;
}

.login-box {
  max-width: 400px;
  width: 100%;
  margin: 0 auto;
}

.login-header {
  text-align: center;
  margin-bottom: 2rem;
}

.logo {
  display: flex;
  justify-content: center;
  margin-bottom: 1rem;
}

.login-header h1 {
  font-size: 1.875rem;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.login-header p {
  color: #4b5563;
  font-size: 0.875rem;
}

.login-form {
  background-color: white;
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
  margin-bottom: 0.5rem;
}

.form-group input[type="text"],
.form-group input[type="password"] {
  width: 100%;
  padding: 0.625rem;
  border: 1px solid #d1d5db;
  border-radius: 0.375rem;
  font-size: 0.875rem;
}

.password-options {
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
  font-size: 0.875rem;
}

.show-password {
  display: flex;
  align-items: center;
}

.show-password input {
  margin-right: 0.5rem;
}

.forgot-password {
  color: #e11d48;
  text-decoration: none;
}

.remember-me {
  display: flex;
  align-items: center;
}

.remember-me input {
  margin-right: 0.5rem;
}

.login-button {
  width: 100%;
  padding: 0.625rem;
  background-color: #e11d48;
  color: white;
  border: none;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
}

.login-button:hover {
  background-color: #be123c;
}

.login-button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error-message {
  background-color: #fee2e2;
  color: #b91c1c;
  padding: 0.5rem;
  border-radius: 0.25rem;
  font-size: 0.875rem;
  margin-bottom: 1rem;
}

.register-link {
  text-align: center;
  font-size: 0.875rem;
  color: #4b5563;
}

.register-link a {
  color: #e11d48;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>