<template>
  <div class="register-container">
    <div class="register-box">
      <div class="register-header">
        <div class="logo">
          <!-- 简单的房子图标 -->
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 9L12 2L21 9V20C21 20.5304 20.7893 21.0391 20.4142 21.4142C20.0391 21.7893 19.5304 22 19 22H5C4.46957 22 3.96086 21.7893 3.58579 21.4142C3.21071 21.0391 3 20.5304 3 20V9Z" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M9 22V12H15V22" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h1>酒店管理系统</h1>
        <p>创建新账号</p>
      </div>
      
      <div class="register-form">
        <form @submit.prevent="handleRegister">
          <!-- 姓名 -->
          <div class="form-group">
            <label for="name">姓名</label>
            <input 
              id="name" 
              v-model="name" 
              type="text" 
              required
            />
          </div>
          
          <!-- 邮箱 -->
          <div class="form-group">
            <label for="email">电子邮箱</label>
            <input 
              id="email" 
              v-model="email" 
              type="email" 
              required
            />
          </div>
          
          <!-- 密码 -->
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
            </div>
          </div>
          
          <!-- 确认密码 -->
          <div class="form-group">
            <label for="confirmPassword">确认密码</label>
            <input 
              id="confirmPassword" 
              v-model="confirmPassword" 
              :type="showPassword ? 'text' : 'password'" 
              required
            />
          </div>
          
          <!-- 服务条款 -->
          <div class="form-group terms">
            <input 
              id="terms" 
              type="checkbox" 
              v-model="agreeTerms"
              required
            />
            <label for="terms">我已阅读并同意服务条款和隐私政策</label>
          </div>
          
          <!-- 注册按钮 -->
          <div class="form-group">
            <button 
              type="submit" 
              class="register-button"
              :disabled="isLoading || !isFormValid"
            >
              {{ isLoading ? '注册中...' : '注册' }}
            </button>
          </div>
          
          <!-- 错误信息 -->
          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>
          
          <!-- 成功信息 -->
          <div v-if="successMessage" class="success-message">
            {{ successMessage }}
          </div>
          
          <!-- 登录链接 -->
          <div class="login-link">
            <p>
              已有账号? 
              <a href="#" @click.prevent="$emit('switch-to-login')">
                返回登录
              </a>
            </p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 表单数据
const name = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const agreeTerms = ref(false)
const showPassword = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

// 定义事件
const emit = defineEmits(['register-success', 'switch-to-login'])

// 表单验证
const isFormValid = computed(() => {
  return name.value && 
         email.value && 
         password.value && 
         password.value === confirmPassword.value && 
         agreeTerms.value
})

// 注册处理
const handleRegister = async () => {
  if (!isFormValid.value) {
    if (password.value !== confirmPassword.value) {
      errorMessage.value = '两次输入的密码不一致'
    } else {
      errorMessage.value = '请填写所有必填字段并同意服务条款'
    }
    return
  }
  
  isLoading.value = true
  errorMessage.value = ''
  successMessage.value = ''
  
  try {
    // 模拟注册请求
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // 简单的注册逻辑，实际应用中应该调用API
    console.log('注册信息:', {
      name: name.value,
      email: email.value,
      password: password.value
    })
    
    // 注册成功
    successMessage.value = '注册成功！请使用新账号登录'
    
    // 清空表单
    name.value = ''
    email.value = ''
    password.value = ''
    confirmPassword.value = ''
    agreeTerms.value = false
    
    // 通知父组件注册成功
    emit('register-success')
    
    // 3秒后自动切换到登录页面
    setTimeout(() => {
      emit('switch-to-login')
    }, 3000)
  } catch (error) {
    errorMessage.value = error.message || '注册失败，请稍后再试'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  background-color: #f9fafb;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 1rem;
}

.register-box {
  max-width: 400px;
  width: 100%;
  margin: 0 auto;
}

.register-header {
  text-align: center;
  margin-bottom: 2rem;
}

.logo {
  display: flex;
  justify-content: center;
  margin-bottom: 1rem;
}

.register-header h1 {
  font-size: 1.875rem;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.register-header p {
  color: #4b5563;
  font-size: 0.875rem;
}

.register-form {
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
.form-group input[type="email"],
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

.terms {
  display: flex;
  align-items: flex-start;
}

.terms input {
  margin-right: 0.5rem;
  margin-top: 0.25rem;
}

.terms label {
  font-size: 0.875rem;
  color: #4b5563;
}

.register-button {
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

.register-button:hover {
  background-color: #be123c;
}

.register-button:disabled {
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

.success-message {
  background-color: #dcfce7;
  color: #166534;
  padding: 0.5rem;
  border-radius: 0.25rem;
  font-size: 0.875rem;
  margin-bottom: 1rem;
}

.login-link {
  text-align: center;
  font-size: 0.875rem;
  color: #4b5563;
}

.login-link a {
  color: #e11d48;
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style> 