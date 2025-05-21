<template>
  <div class="forgot-password-container">
    <div class="forgot-password-box">
      <div class="forgot-password-header">
        <div class="logo">
          <!-- 简单的房子图标 -->
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 9L12 2L21 9V20C21 20.5304 20.7893 21.0391 20.4142 21.4142C20.0391 21.7893 19.5304 22 19 22H5C4.46957 22 3.96086 21.7893 3.58579 21.4142C3.21071 21.0391 3 20.5304 3 20V9Z" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M9 22V12H15V22" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h1>重置密码</h1>
        <p>请输入您的电子邮箱，我们将向您发送重置密码的链接</p>
      </div>
      
      <div class="forgot-password-form">
        <form v-if="!resetLinkSent" @submit.prevent="handleResetPassword">
          <div class="form-group">
            <label for="email">电子邮箱</label>
            <input 
              id="email" 
              v-model="email" 
              type="email" 
              required
            />
          </div>
          
          <div class="form-group">
            <button 
              type="submit" 
              class="reset-button"
              :disabled="isLoading"
            >
              {{ isLoading ? '发送中...' : '发送重置链接' }}
            </button>
          </div>
        </form>

        <div v-if="resetLinkSent" class="reset-success">
          <div class="success-icon">
            <!-- 邮件图标 -->
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M22 6C22 4.9 21.1 4 20 4H4C2.9 4 2 4.9 2 6V18C2 19.1 2.9 20 4 20H20C21.1 20 22 19.1 22 18V6ZM20 6L12 11L4 6H20ZM20 18H4V8L12 13L20 8V18Z" fill="#166534"/>
            </svg>
          </div>
          <h3>重置链接已发送</h3>
          <p>我们已向 {{ email }} 发送了一封包含重置密码链接的电子邮件。请检查您的收件箱。</p>
          <p class="resend-link">
            没有收到邮件? 
            <a href="#" @click.prevent="resetLinkSent = false">重新发送</a>
          </p>
        </div>

        <div class="divider">
          <span>或者</span>
        </div>

        <div class="back-to-login">
          <button @click="$emit('switch-to-login')" class="back-button">
            返回登录
          </button>
        </div>
      </div>
    </div>
    
    <!-- 错误提示 -->
    <div v-if="resetError" class="error-toast">
      <span>{{ resetError }}</span>
      <button @click="resetError = ''" class="close-button">×</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 表单数据
const email = ref('')
const isLoading = ref(false)
const resetError = ref('')
const resetLinkSent = ref(false)

// 定义事件
const emit = defineEmits(['switch-to-login'])

// 重置密码处理
const handleResetPassword = async () => {
  if (!email.value.trim()) {
    resetError.value = '请输入电子邮箱'
    return
  }

  isLoading.value = true
  resetError.value = ''

  try {
    // 模拟发送重置密码链接请求
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    // 这里应该是实际的发送重置密码链接逻辑
    // 例如: const response = await api.sendPasswordResetLink(email.value)
    
    // 模拟发送成功
    console.log('重置密码链接已发送')
    resetLinkSent.value = true
  } catch (error) {
    console.error('发送重置密码链接失败:', error)
    resetError.value = error.message || '发送失败，请稍后再试'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
/* ...样式同用户提供... */
</style> 