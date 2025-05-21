<template>
  <div class="reset-password-container">
    <div class="reset-password-box">
      <div class="reset-password-header">
        <div class="logo">
          <!-- 简单的房子图标 -->
          <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M3 9L12 2L21 9V20C21 20.5304 20.7893 21.0391 20.4142 21.4142C20.0391 21.7893 19.5304 22 19 22H5C4.46957 22 3.96086 21.7893 3.58579 21.4142C3.21071 21.0391 3 20.5304 3 20V9Z" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M9 22V12H15V22" stroke="#1E293B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h1>设置新密码</h1>
        <p>请输入您的新密码</p>
      </div>
      
      <div class="reset-password-form">
        <form v-if="!resetSuccess" @submit.prevent="handleResetPassword">
          <div class="form-group">
            <label for="password">新密码</label>
            <div class="password-input">
              <input 
                id="password" 
                v-model="password" 
                :type="showPassword ? 'text' : 'password'" 
                required
              />
              <button 
                type="button" 
                @click="showPassword = !showPassword"
                class="toggle-password"
              >
                <!-- 眼睛图标 -->
                <svg v-if="!showPassword" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M2.45825 12C3.73253 7.94288 7.52281 5 12.0004 5C16.4781 5 20.2684 7.94291 21.5426 12C20.2684 16.0571 16.4781 19 12.0005 19C7.52281 19 3.73251 16.0571 2.45825 12Z" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <svg v-else width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M9.9 4.24002C10.5883 4.0789 11.2931 3.99836 12 4.00003C16.4781 4.00003 20.2684 6.94294 21.5426 11C21.2311 12.0377 20.7704 13.0234 20.18 13.92M14.12 14.12C13.8454 14.4148 13.5141 14.6512 13.1462 14.8151C12.7782 14.9791 12.3809 15.0673 11.9781 15.0744C11.5753 15.0815 11.1752 15.0074 10.8016 14.8565C10.4281 14.7056 10.0887 14.4811 9.80385 14.1962C9.51897 13.9113 9.29439 13.572 9.14351 13.1984C8.99262 12.8249 8.91853 12.4247 8.92563 12.0219C8.93274 11.6191 9.02091 11.2219 9.18488 10.8539C9.34884 10.4859 9.58525 10.1547 9.88 9.88003M17.94 17.94C16.2306 19.243 14.1491 19.9649 12 20C7.52195 20 3.73195 17.0571 2.45795 13C3.02495 11.1714 4.05895 9.5236 5.46995 8.26003L17.94 17.94Z" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M1 1L23 23" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </button>
            </div>
            <p class="password-hint">密码至少包含8个字符，包括字母和数字</p>
          </div>
          
          <div class="form-group">
            <label for="confirmPassword">确认新密码</label>
            <div class="password-input">
              <input 
                id="confirmPassword" 
                v-model="confirmPassword" 
                :type="showConfirmPassword ? 'text' : 'password'" 
                required
              />
              <button 
                type="button" 
                @click="showConfirmPassword = !showConfirmPassword"
                class="toggle-password"
              >
                <!-- 眼睛图标 -->
                <svg v-if="!showConfirmPassword" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M2.45825 12C3.73253 7.94288 7.52281 5 12.0004 5C16.4781 5 20.2684 7.94291 21.5426 12C20.2684 16.0571 16.4781 19 12.0005 19C7.52281 19 3.73251 16.0571 2.45825 12Z" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <svg v-else width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M9.9 4.24002C10.5883 4.0789 11.2931 3.99836 12 4.00003C16.4781 4.00003 20.2684 6.94294 21.5426 11C21.2311 12.0377 20.7704 13.0234 20.18 13.92M14.12 14.12C13.8454 14.4148 13.5141 14.6512 13.1462 14.8151C12.7782 14.9791 12.3809 15.0673 11.9781 15.0744C11.5753 15.0815 11.1752 15.0074 10.8016 14.8565C10.4281 14.7056 10.0887 14.4811 9.80385 14.1962C9.51897 13.9113 9.29439 13.572 9.14351 13.1984C8.99262 12.8249 8.91853 12.4247 8.92563 12.0219C8.93274 11.6191 9.02091 11.2219 9.18488 10.8539C9.34884 10.4859 9.58525 10.1547 9.88 9.88003M17.94 17.94C16.2306 19.243 14.1491 19.9649 12 20C7.52195 20 3.73195 17.0571 2.45795 13C3.02495 11.1714 4.05895 9.5236 5.46995 8.26003L17.94 17.94Z" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M1 1L23 23" stroke="#6B7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </button>
            </div>
          </div>
          
          <div class="form-group">
            <button 
              type="submit" 
              class="reset-button"
              :disabled="isLoading || !isFormValid"
            >
              {{ isLoading ? '重置中...' : '重置密码' }}
            </button>
          </div>
        </form>

        <div v-if="resetSuccess" class="reset-success">
          <div class="success-icon">
            <!-- 对勾图标 -->
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M20 6L9 17L4 12" stroke="#166534" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <h3>密码重置成功</h3>
          <p>您的密码已成功重置，现在可以使用新密码登录系统了。</p>
          <div class="login-button-container">
            <button @click="$emit('switch-to-login')" class="login-button">
              前往登录
            </button>
          </div>
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
import { ref, computed } from 'vue'

// 表单数据
const password = ref('')
const confirmPassword = ref('')
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)
const resetError = ref('')
const resetSuccess = ref(false)

// 定义事件
const emit = defineEmits(['switch-to-login'])

// 从URL获取重置令牌
const token = new URLSearchParams(window.location.search).get('token')

// 表单验证
const isFormValid = computed(() => {
  return password.value.length >= 8 && password.value === confirmPassword.value
})

// 重置密码处理
const handleResetPassword = async () => {
  if (!isFormValid.value) {
    if (password.value.length < 8) {
      resetError.value = '密码至少需要8个字符'
    } else if (password.value !== confirmPassword.value) {
      resetError.value = '两次输入的密码不一致'
    }
    return
  }

  isLoading.value = true
  resetError.value = ''

  try {
    // 模拟重置密码请求
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    // 这里应该是实际的重置密码逻辑
    // 例如: const response = await api.resetPassword(token, password.value)
    
    // 模拟重置成功
    console.log('密码重置成功')
    resetSuccess.value = true
  } catch (error) {
    console.error('密码重置失败:', error)
    resetError.value = error.message || '重置失败，请稍后再试'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
/* ...样式同用户提供... */
</style> 