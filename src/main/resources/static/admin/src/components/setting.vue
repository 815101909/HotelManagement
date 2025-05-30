<template>
    <div class="page-container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1 class="page-title">系统设置</h1>
        <div class="header-actions">
          <button class="btn btn-primary" @click="saveAllSettings">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M5 13L9 17L19 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            保存所有设置
          </button>
        </div>
      </div>
      
      <!-- 设置导航和内容 -->
      <div class="settings-container">
        <!-- 设置导航 -->
        <div class="settings-nav">
          <button 
            v-for="tab in settingTabs" 
            :key="tab.id" 
            class="nav-item" 
            :class="{ active: activeTab === tab.id }"
            @click="activeTab = tab.id"
          >
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path :d="tab.icon" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>{{ tab.name }}</span>
          </button>
        </div>
        
        <!-- 设置内容 -->
        <div class="settings-content">
          <!-- 基本设置 -->
          <div v-if="activeTab === 'general'" class="settings-section">
            <h2 class="section-title">基本设置</h2>
            
            <div class="form-group">
              <label for="hotel-name" class="form-label">酒店名称</label>
              <input type="text" id="hotel-name" class="form-input" v-model="generalSettings.hotelName">
            </div>
            
            <div class="form-group">
              <label for="hotel-address" class="form-label">酒店地址</label>
              <input type="text" id="hotel-address" class="form-input" v-model="generalSettings.hotelAddress">
            </div>
            
            <div class="form-group">
              <label for="hotel-phone" class="form-label">联系电话</label>
              <input type="tel" id="hotel-phone" class="form-input" v-model="generalSettings.hotelPhone">
            </div>
            
            <div class="form-group">
              <label for="hotel-email" class="form-label">电子邮箱</label>
              <input type="email" id="hotel-email" class="form-input" v-model="generalSettings.hotelEmail">
            </div>
            
            <div class="form-group">
              <label for="hotel-website" class="form-label">网站地址</label>
              <input type="url" id="hotel-website" class="form-input" v-model="generalSettings.hotelWebsite">
            </div>
            
            <div class="form-group">
              <label for="hotel-currency" class="form-label">货币单位</label>
              <select id="hotel-currency" class="form-select" v-model="generalSettings.currency">
                <option value="CNY">人民币 (CNY)</option>
                <option value="USD">美元 (USD)</option>
                <option value="EUR">欧元 (EUR)</option>
                <option value="JPY">日元 (JPY)</option>
                <option value="GBP">英镑 (GBP)</option>
              </select>
            </div>
            
            <div class="form-group">
              <label for="hotel-timezone" class="form-label">时区</label>
              <select id="hotel-timezone" class="form-select" v-model="generalSettings.timezone">
                <option value="Asia/Shanghai">中国标准时间 (GMT+8)</option>
                <option value="America/New_York">美国东部时间 (GMT-5)</option>
                <option value="Europe/London">英国标准时间 (GMT+0)</option>
                <option value="Europe/Paris">中欧时间 (GMT+1)</option>
                <option value="Asia/Tokyo">日本标准时间 (GMT+9)</option>
              </select>
            </div>
            
            <div class="form-group">
              <label for="hotel-language" class="form-label">系统语言</label>
              <select id="hotel-language" class="form-select" v-model="generalSettings.language">
                <option value="zh-CN">简体中文</option>
                <option value="en-US">English (US)</option>
                <option value="ja-JP">日本語</option>
                <option value="ko-KR">한국어</option>
                <option value="fr-FR">Français</option>
              </select>
            </div>
            
            <div class="form-group">
              <label class="form-label">系统主题</label>
              <div class="theme-selector">
                <div 
                  class="theme-option" 
                  :class="{ active: generalSettings.theme === 'light' }"
                  @click="generalSettings.theme = 'light'"
                >
                  <div class="theme-preview light-theme"></div>
                  <span>浅色主题</span>
                </div>
                <div 
                  class="theme-option" 
                  :class="{ active: generalSettings.theme === 'dark' }"
                  @click="generalSettings.theme = 'dark'"
                >
                  <div class="theme-preview dark-theme"></div>
                  <span>深色主题</span>
                </div>
                <div 
                  class="theme-option" 
                  :class="{ active: generalSettings.theme === 'auto' }"
                  @click="generalSettings.theme = 'auto'"
                >
                  <div class="theme-preview auto-theme"></div>
                  <span>跟随系统</span>
                </div>
              </div>
            </div>
            
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveGeneralSettings">保存设置</button>
              <button class="btn btn-secondary" @click="resetGeneralSettings">重置</button>
            </div>
          </div>
          
          <!-- 用户管理 -->
          <div v-if="activeTab === 'users'" class="settings-section">
            <h2 class="section-title">员工管理</h2>
            
            <div class="section-actions">
              <button class="btn btn-primary" @click="showAddEmployeeModal = true">
                <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M12 5V19M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                添加员工
              </button>
            </div>
            
            <div class="table-container">
              <table class="users-table">
                <thead>
                  <tr>
                    <th>工号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>电话</th>
                    <th>职位</th>
                    <th style="min-width: 80px;">入职日期</th>
                    <th>薪资</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="user in users" :key="user.id">
                    <td>{{ user.id }}</td>
                    <td>{{ user.name }}</td>
                    <td>{{ user.gender }}</td>
                    <td>{{ user.phone }}</td>
                    <td>{{ user.position }}</td>
                    <td>{{ user.hireDate ? (user.hireDate.length ? user.hireDate.substring(0, 10) : user.hireDate) : '' }}</td>
                    <td>{{ user.salary }}</td>
                    <td>{{ user.status == 1 ? '在职' : '离职' }}</td>
                    <td>{{ user.createTime ? user.createTime.replace('T', ' ').substring(0, 19) : '' }}</td>
                    <td class="action-btns">
                      <button class="btn btn-secondary" @click="openEditEmployeeModal(user)">编辑</button>
                      <button class="btn btn-danger" style="background-color: #e11d48; color: #fff; border: none;" @click="deleteEmployee(user)">删除</button>
                    </td>
                  </tr>
                </tbody>
              </table>
              <div v-if="isUserLoading">加载中...</div>
              <div v-if="userError" style="color:red">{{ userError }}</div>
            </div>
          </div>
          
          <!-- 权限设置 -->
          <div v-if="activeTab === 'permissions'" class="settings-section">
            <h2 class="section-title">权限设置</h2>
            
            <div class="role-tabs">
              <button 
                v-for="role in roles" 
                :key="role.id" 
                class="role-tab" 
                :class="{ active: activeRole === role.id }"
                @click="activeRole = role.id"
              >
                {{ role.name }}
              </button>
            </div>
            
            <div class="permissions-container">
              <div v-for="(permGroup, groupName) in permissionGroups" :key="groupName" class="permission-group">
                <h3 class="group-title">{{ groupName }}</h3>
                <div class="permission-list">
                  <div v-for="perm in permGroup" :key="perm.id" class="permission-item">
                    <div class="permission-checkbox">
                      <input 
                        type="checkbox" 
                        :id="perm.id" 
                        :checked="hasPermission(activeRole, perm.id)"
                        @change="togglePermission(activeRole, perm.id)"
                      >
                      <label :for="perm.id">{{ perm.name }}</label>
                    </div>
                    <span class="permission-desc">{{ perm.description }}</span>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="form-actions">
              <button class="btn btn-primary" @click="savePermissions">保存权限</button>
              <button class="btn btn-secondary" @click="resetPermissions">重置</button>
            </div>
          </div>
          
          <!-- 通知设置 -->
          <div v-if="activeTab === 'notifications'" class="settings-section">
            <h2 class="section-title">通知设置</h2>
            
            <div class="notification-settings">
              <div class="notification-group">
                <h3 class="group-title">电子邮件通知</h3>
                
                <div class="form-group">
                  <div class="toggle-switch">
                    <input type="checkbox" id="email-enabled" class="toggle-input" v-model="notificationSettings.email.enabled">
                    <label for="email-enabled" class="toggle-label"></label>
                  </div>
                  <div class="toggle-content">
                    <label for="email-enabled" class="toggle-title">启用电子邮件通知</label>
                    <p class="toggle-desc">向客户和员工发送电子邮件通知</p>
                  </div>
                </div>
                
                <div class="form-group" v-if="notificationSettings.email.enabled">
                  <label for="email-server" class="form-label">SMTP服务器</label>
                  <input type="text" id="email-server" class="form-input" v-model="notificationSettings.email.server">
                </div>
                
                <div class="form-group" v-if="notificationSettings.email.enabled">
                  <label for="email-port" class="form-label">SMTP端口</label>
                  <input type="number" id="email-port" class="form-input" v-model="notificationSettings.email.port">
                </div>
                
                <div class="form-group" v-if="notificationSettings.email.enabled">
                  <label for="email-username" class="form-label">SMTP用户名</label>
                  <input type="text" id="email-username" class="form-input" v-model="notificationSettings.email.username">
                </div>
                
                <div class="form-group" v-if="notificationSettings.email.enabled">
                  <label for="email-password" class="form-label">SMTP密码</label>
                  <input type="password" id="email-password" class="form-input" v-model="notificationSettings.email.password">
                </div>
                
                <div class="form-group" v-if="notificationSettings.email.enabled">
                  <label for="email-from" class="form-label">发件人地址</label>
                  <input type="email" id="email-from" class="form-input" v-model="notificationSettings.email.from">
                </div>
              </div>
              
              <div class="notification-group">
                <h3 class="group-title">短信通知</h3>
                
                <div class="form-group">
                  <div class="toggle-switch">
                    <input type="checkbox" id="sms-enabled" class="toggle-input" v-model="notificationSettings.sms.enabled">
                    <label for="sms-enabled" class="toggle-label"></label>
                  </div>
                  <div class="toggle-content">
                    <label for="sms-enabled" class="toggle-title">启用短信通知</label>
                    <p class="toggle-desc">向客户和员工发送短信通知</p>
                  </div>
                </div>
                
                <div class="form-group" v-if="notificationSettings.sms.enabled">
                  <label for="sms-provider" class="form-label">短信服务提供商</label>
                  <select id="sms-provider" class="form-select" v-model="notificationSettings.sms.provider">
                    <option value="aliyun">阿里云短信</option>
                    <option value="tencent">腾讯云短信</option>
                    <option value="twilio">Twilio</option>
                  </select>
                </div>
                
                <div class="form-group" v-if="notificationSettings.sms.enabled">
                  <label for="sms-apikey" class="form-label">API密钥</label>
                  <input type="text" id="sms-apikey" class="form-input" v-model="notificationSettings.sms.apiKey">
                </div>
                
                <div class="form-group" v-if="notificationSettings.sms.enabled">
                  <label for="sms-apisecret" class="form-label">API密钥</label>
                  <input type="password" id="sms-apisecret" class="form-input" v-model="notificationSettings.sms.apiSecret">
                </div>
              </div>
              
              <div class="notification-group">
                <h3 class="group-title">通知事件</h3>
                
                <div class="form-group">
                  <div class="toggle-switch">
                    <input type="checkbox" id="event-booking" class="toggle-input" v-model="notificationSettings.events.booking">
                    <label for="event-booking" class="toggle-label"></label>
                  </div>
                  <div class="toggle-content">
                    <label for="event-booking" class="toggle-title">新预订通知</label>
                    <p class="toggle-desc">当有新预订时发送通知</p>
                  </div>
                </div>
                
                <div class="form-group">
                  <div class="toggle-switch">
                    <input type="checkbox" id="event-checkin" class="toggle-input" v-model="notificationSettings.events.checkin">
                    <label for="event-checkin" class="toggle-label"></label>
                  </div>
                  <div class="toggle-content">
                    <label for="event-checkin" class="toggle-title">入住通知</label>
                    <p class="toggle-desc">当客人入住时发送通知</p>
                  </div>
                </div>
                
                <div class="form-group">
                  <div class="toggle-switch">
                    <input type="checkbox" id="event-checkout" class="toggle-input" v-model="notificationSettings.events.checkout">
                    <label for="event-checkout" class="toggle-label"></label>
                  </div>
                  <div class="toggle-content">
                    <label for="event-checkout" class="toggle-title">退房通知</label>
                    <p class="toggle-desc">当客人退房时发送通知</p>
                  </div>
                </div>
                
                <div class="form-group">
                  <div class="toggle-switch">
                    <input type="checkbox" id="event-payment" class="toggle-input" v-model="notificationSettings.events.payment">
                    <label for="event-payment" class="toggle-label"></label>
                  </div>
                  <div class="toggle-content">
                    <label for="event-payment" class="toggle-title">支付通知</label>
                    <p class="toggle-desc">当收到支付时发送通知</p>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="form-actions">
              <button class="btn btn-primary" @click="saveNotificationSettings">保存设置</button>
              <button class="btn btn-secondary" @click="resetNotificationSettings">重置</button>
            </div>
          </div>
          
          <!-- 备份与恢复 -->
          <div v-if="activeTab === 'backup'" class="settings-section">
            <h2 class="section-title">备份与恢复</h2>
            
            <div class="backup-section">
              <h3 class="group-title">数据备份</h3>
              
              <div class="backup-info">
                <div class="info-item">
                  <span class="info-label">上次备份时间：</span>
                  <span class="info-value">{{ backupSettings.lastBackupTime || '从未备份' }}</span>
                </div>
                <div class="info-item">
                  <span class="info-label">备份状态：</span>
                  <span class="info-value" :class="backupSettings.status">{{ getBackupStatusText(backupSettings.status) }}</span>
                </div>
              </div>
              
              <div class="backup-actions">
                <button class="btn btn-primary" @click="createBackup">
                  <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M21 15V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V15M17 8L12 3M12 3L7 8M12 3V15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                  创建备份
                </button>
              </div>
              
              <div class="form-group">
                <div class="toggle-switch">
                  <input type="checkbox" id="auto-backup" class="toggle-input" v-model="backupSettings.autoBackup">
                  <label for="auto-backup" class="toggle-label"></label>
                </div>
                <div class="toggle-content">
                  <label for="auto-backup" class="toggle-title">自动备份</label>
                  <p class="toggle-desc">定期自动备份系统数据</p>
                </div>
              </div>
              
              <div class="form-group" v-if="backupSettings.autoBackup">
                <label for="backup-frequency" class="form-label">备份频率</label>
                <select id="backup-frequency" class="form-select" v-model="backupSettings.frequency">
                  <option value="daily">每天</option>
                  <option value="weekly">每周</option>
                  <option value="monthly">每月</option>
                </select>
              </div>
              
              <div class="form-group" v-if="backupSettings.autoBackup">
                <label for="backup-retention" class="form-label">保留备份数量</label>
                <input type="number" id="backup-retention" class="form-input" v-model="backupSettings.retention" min="1" max="30">
              </div>
            </div>
            
            <div class="backup-section">
              <h3 class="group-title">备份历史</h3>
              
              <div class="table-container">
                <table class="backup-table">
                  <thead>
                    <tr>
                      <th>备份时间</th>
                      <th>备份大小</th>
                      <th>备份类型</th>
                      <th>状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(backup, index) in backupHistory" :key="index">
                      <td>{{ backup.time }}</td>
                      <td>{{ backup.size }}</td>
                      <td>{{ backup.type }}</td>
                      <td>
                        <span class="status-badge" :class="backup.status">{{ getBackupStatusText(backup.status) }}</span>
                      </td>
                      <td>
                        <div class="table-actions">
                          <button class="action-btn restore-btn" @click="restoreBackup(backup)" title="恢复">
                            <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                              <path d="M3 9L12 2L21 9V20C21 20.5304 20.7893 21.0391 20.4142 21.4142C20.0391 21.7893 19.5304 22 19 22H5C4.46957 22 3.96086 21.7893 3.58579 21.4142C3.21071 21.0391 3 20.5304 3 20V9Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                              <path d="M9 22V12H15V22" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                          </button>
                          <button class="action-btn download-btn" @click="downloadBackup(backup)" title="下载">
                            <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                              <path d="M21 15V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V15M7 10L12 15M12 15L17 10M12 15V3" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                          </button>
                          <button class="action-btn delete-btn" @click="deleteBackup(backup)" title="删除">
                            <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                              <path d="M3 6H5H21M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6M10 11V17M14 11V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                          </button>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            
            <div class="backup-section">
              <h3 class="group-title">数据恢复</h3>
              
              <div class="upload-backup">
                <label for="backup-file" class="upload-label">
                  <svg class="upload-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M21 15V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V15M17 8L12 3M12 3L7 8M12 3V15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                  <span>上传备份文件</span>
                  <input type="file" id="backup-file" class="upload-input" @change="handleBackupFileUpload">
                </label>
              </div>
              
              <div class="warning-message">
                <svg class="warning-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M12 9V13M12 17H12.01M12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <p>警告：恢复备份将会覆盖当前系统中的所有数据，请确保您已经备份了当前数据。</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 添加员工模态框 -->
      <div class="modal" v-if="showAddEmployeeModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">添加员工</h2>
            <button class="modal-close" @click="showAddEmployeeModal = false">×</button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="form-label">姓名</label>
              <input class="form-input" v-model="newEmployee.name" />
            </div>
            <div class="form-group">
              <label class="form-label">性别</label>
              <select class="form-input" v-model="newEmployee.gender">
                <option value="男">男</option>
                <option value="女">女</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">电话</label>
              <input class="form-input" v-model="newEmployee.phone" />
            </div>
            <div class="form-group">
              <label class="form-label">职位</label>
              <input class="form-input" v-model="newEmployee.position" />
            </div>
            <div class="form-group">
              <label class="form-label">入职日期</label>
              <input class="form-input" type="date" v-model="newEmployee.hireDate" />
            </div>
            <div class="form-group">
              <label class="form-label">薪资</label>
              <input class="form-input" type="number" v-model="newEmployee.salary" />
            </div>
            <div class="form-group">
              <label class="form-label">状态</label>
              <select class="form-input" v-model="newEmployee.status">
                <option value="1">在职</option>
                <option value="0">离职</option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showAddEmployeeModal = false">取消</button>
            <button class="btn btn-primary" @click="addEmployee">保存</button>
          </div>
        </div>
      </div>
      
      <!-- 编辑员工模态框 -->
      <div class="modal" v-if="showEditEmployeeModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">编辑员工</h2>
            <button class="modal-close" @click="showEditEmployeeModal = false">×</button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="form-label">姓名</label>
              <input class="form-input" v-model="editEmployee.name" />
            </div>
            <div class="form-group">
              <label class="form-label">性别</label>
              <select class="form-input" v-model="editEmployee.gender">
                <option value="男">男</option>
                <option value="女">女</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">电话</label>
              <input class="form-input" v-model="editEmployee.phone" />
            </div>
            <div class="form-group">
              <label class="form-label">职位</label>
              <input class="form-input" v-model="editEmployee.position" />
            </div>
            <div class="form-group">
              <label class="form-label">入职日期</label>
              <input class="form-input" type="date" v-model="editEmployee.hireDate" />
            </div>
            <div class="form-group">
              <label class="form-label">薪资</label>
              <input class="form-input" type="number" v-model="editEmployee.salary" />
            </div>
            <div class="form-group">
              <label class="form-label">状态</label>
              <select class="form-input" v-model="editEmployee.status">
                <option value="1">在职</option>
                <option value="0">离职</option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showEditEmployeeModal = false">取消</button>
            <button class="btn btn-primary" @click="saveEditEmployee">保存</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive, onMounted, watch } from 'vue'
  import axios from 'axios'
  
  // 当前活动标签
  const activeTab = ref('general')
  
  // 设置标签
  const settingTabs = [
    { 
      id: 'general', 
      name: '基本设置', 
      icon: 'M12 15C15.866 15 19 11.866 19 8C19 4.13401 15.866 1 12 1C8.13401 1 5 4.13401 5 8C5 11.866 8.13401 15 12 15Z' 
    },
    { 
      id: 'users', 
      name: '员工管理', 
      icon: 'M17 21V19C17 17.9391 16.5786 16.9217 15.8284 16.1716C15.0783 15.4214 14.0609 15 13 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21M23 21V19C22.9993 18.1137 22.7044 17.2528 22.1614 16.5523C21.6184 15.8519 20.8581 15.3516 20 15.13M16 3.13C16.8604 3.3503 17.623 3.8507 18.1676 4.55231C18.7122 5.25392 19.0078 6.11683 19.0078 7.005C19.0078 7.89317 18.7122 8.75608 18.1676 9.45769C17.623 10.1593 16.8604 10.6597 16 10.88M13 7C13 9.20914 11.2091 11 9 11C6.79086 11 5 9.20914 5 7C5 4.79086 6.79086 3 9 3C11.2091 3 13 4.79086 13 7Z' 
    },
    { 
      id: 'permissions', 
      name: '权限设置', 
      icon: 'M12 15V3M12 15L8 11M12 15L16 11M3 17V19C3 19.5304 3.21071 20.0391 3.58579 20.4142C3.96086 20.7893 4.46957 21 5 21H19C19.5304 21 20.0391 20.7893 20.4142 20.4142C20.7893 20.0391 21 19.5304 21 19V17' 
    },
    { 
      id: 'notifications', 
      name: '通知设置', 
      icon: 'M18 8C18 6.4087 17.3679 4.88258 16.2426 3.75736C15.1174 2.63214 13.5913 2 12 2C10.4087 2 8.88258 2.63214 7.75736 3.75736C6.63214 4.88258 6 6.4087 6 8C6 15 3 17 3 17H21C21 17 18 15 18 8Z' 
    },
    { 
      id: 'backup', 
      name: '备份与恢复', 
      icon: 'M4 22H20C20.5304 22 21.0391 21.7893 21.4142 21.4142C21.7893 21.0391 22 20.5304 22 20V8L16 2H4C3.46957 2 2.96086 2.21071 2.58579 2.58579C2.21071 2.96086 2 3.46957 2 4V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22Z' 
    }
  ]
  
  // 基本设置
  const generalSettings = reactive({
    hotelName: '',
    hotelAddress: '',
    hotelPhone: '',
    hotelEmail: '',
    hotelWebsite: '',
    currency: 'CNY',
    timezone: 'Asia/Shanghai',
    language: 'zh-CN',
    theme: 'light'
  })
  
  // 用户管理
  const showAddEmployeeModal = ref(false)
  const newEmployee = ref({
    name: '',
    gender: '男',
    phone: '',
    position: '',
    hireDate: '',
    salary: '',
    status: '1'
  })
  
  // 用户管理
  const users = ref([])
  const isUserLoading = ref(false)
  const userError = ref('')
  
  const fetchUsers = async () => {
    isUserLoading.value = true
    userError.value = ''
    try {
      const res = await axios.get('/employee/all')
      console.log('员工数据', res.data)
      users.value = res.data
    } catch (e) {
      userError.value = '获取员工数据失败'
    } finally {
      isUserLoading.value = false
    }
  }
  
  onMounted(() => {
    fetchAllSettings()
    fetchUsers()
    applyTheme(generalSettings.theme)
  })
  
  // 获取角色名称
  const getRoleName = (role) => {
    const roleMap = {
      'admin': '系统管理员',
      'manager': '酒店经理',
      'staff': '普通员工'
    }
    return roleMap[role] || role
  }
  
  // 编辑用户
  const editUser = (user) => {
    console.log('编辑用户:', user)
    // 这里可以实现编辑用户的逻辑
  }
  
  // 切换用户状态
  const toggleUserStatus = (user) => {
    console.log('切换用户状态:', user)
    // 这里可以实现切换用户状态的逻辑
    const index = users.value.findIndex(u => u.id === user.id)
    if (index !== -1) {
      users.value[index].status = user.status === 'active' ? 'inactive' : 'active'
    }
  }
  
  // 删除用户
  const deleteUser = (user) => {
    console.log('删除用户:', user)
    // 这里可以实现删除用户的逻辑
    users.value = users.value.filter(u => u.id !== user.id)
  }
  
  // 添加员工
  const addEmployee = async () => {
    try {
      await axios.post('/employee/add', newEmployee.value)
      showAddEmployeeModal.value = false
      // 清空表单
      newEmployee.value = {
        name: '',
        gender: '男',
        phone: '',
        position: '',
        hireDate: '',
        salary: '',
        status: '1'
      }
      // 刷新员工列表
      fetchUsers()
    } catch (e) {
      alert('添加失败')
    }
  }
  
  // 权限设置
  const activeRole = ref('admin')
  
  // 角色列表
  const roles = [
    { id: 'admin', name: '系统管理员' },
    { id: 'manager', name: '酒店经理' },
    { id: 'staff', name: '普通员工' }
  ]
  
  // 权限组
  const permissionGroups = {
    '仪表盘': [
      { id: 'dashboard.view', name: '查看仪表盘', description: '允许查看系统仪表盘' },
      { id: 'dashboard.analytics', name: '查看分析数据', description: '允许查看详细的分析数据' }
    ],
    '房间管理': [
      { id: 'rooms.view', name: '查看房间', description: '允许查看房间信息' },
      { id: 'rooms.add', name: '添加房间', description: '允许添加新房间' },
      { id: 'rooms.edit', name: '编辑房间', description: '允许编辑房间信息' },
      { id: 'rooms.delete', name: '删除房间', description: '允许删除房间' }
    ],
    '预订管理': [
      { id: 'bookings.view', name: '查看预订', description: '允许查看预订信息' },
      { id: 'bookings.add', name: '添加预订', description: '允许添加新预订' },
      { id: 'bookings.edit', name: '编辑预订', description: '允许编辑预订信息' },
      { id: 'bookings.delete', name: '删除预订', description: '允许删除预订' },
      { id: 'bookings.checkin', name: '办理入住', description: '允许办理入住' },
      { id: 'bookings.checkout', name: '办理退房', description: '允许办理退房' }
    ],
    '客户管理': [
      { id: 'customers.view', name: '查看客户', description: '允许查看客户信息' },
      { id: 'customers.add', name: '添加客户', description: '允许添加新客户' },
      { id: 'customers.edit', name: '编辑客户', description: '允许编辑客户信息' },
      { id: 'customers.delete', name: '删除客户', description: '允许删除客户' }
    ],
    '财务管理': [
      { id: 'finance.view', name: '查看财务', description: '允许查看财务信息' },
      { id: 'finance.transactions', name: '处理交易', description: '允许处理财务交易' },
      { id: 'finance.reports', name: '生成报表', description: '允许生成财务报表' }
    ],
    '系统设置': [
      { id: 'settings.view', name: '查看设置', description: '允许查看系统设置' },
      { id: 'settings.edit', name: '修改设置', description: '允许修改系统设置' },
      { id: 'users.manage', name: '管理用户', description: '允许管理系统用户' },
      { id: 'permissions.manage', name: '管理权限', description: '允许管理系统权限' },
      { id: 'backup.manage', name: '管理备份', description: '允许管理系统备份' }
    ]
  }
  
  // 角色权限
  const rolePermissions = reactive({
    'admin': [
      'dashboard.view', 'dashboard.analytics',
      'rooms.view', 'rooms.add', 'rooms.edit', 'rooms.delete',
      'bookings.view', 'bookings.add', 'bookings.edit', 'bookings.delete', 'bookings.checkin', 'bookings.checkout',
      'customers.view', 'customers.add', 'customers.edit', 'customers.delete',
      'finance.view', 'finance.transactions', 'finance.reports',
      'settings.view', 'settings.edit', 'users.manage', 'permissions.manage', 'backup.manage'
    ],
    'manager': [
      'dashboard.view', 'dashboard.analytics',
      'rooms.view', 'rooms.add', 'rooms.edit',
      'bookings.view', 'bookings.add', 'bookings.edit', 'bookings.checkin', 'bookings.checkout',
      'customers.view', 'customers.add', 'customers.edit',
      'finance.view', 'finance.reports',
      'settings.view'
    ],
    'staff': [
      'dashboard.view',
      'rooms.view',
      'bookings.view', 'bookings.add', 'bookings.checkin', 'bookings.checkout',
      'customers.view', 'customers.add'
    ]
  })
  
  // 检查角色是否有权限
  const hasPermission = (role, permissionId) => {
    return rolePermissions[role].includes(permissionId)
  }
  
  // 切换权限
  const togglePermission = (role, permissionId) => {
    const index = rolePermissions[role].indexOf(permissionId)
    if (index === -1) {
      rolePermissions[role].push(permissionId)
    } else {
      rolePermissions[role].splice(index, 1)
    }
  }
  
  // 保存权限
  const savePermissions = () => {
    console.log('保存权限:', rolePermissions)
    // 这里可以实现保存权限的逻辑
  }
  
  // 重置权限
  const resetPermissions = () => {
    console.log('重置权限')
    // 这里可以实现重置权限的逻辑
  }
  
  // 通知设置
  const notificationSettings = reactive({
    email: {
      enabled: false,
      server: '',
      port: 587,
      username: '',
      password: '',
      from: ''
    },
    sms: {
      enabled: false,
      provider: '',
      apiKey: '',
      apiSecret: ''
    },
    events: {
      booking: false,
      checkin: false,
      checkout: false,
      payment: false
    }
  })
  
  // 保存通知设置
  const saveNotificationSettings = async () => {
    try {
      const data = {
        emailEnabled: notificationSettings.email.enabled,
        emailServer: notificationSettings.email.server,
        emailPort: notificationSettings.email.port,
        emailUsername: notificationSettings.email.username,
        emailPassword: notificationSettings.email.password,
        emailFrom: notificationSettings.email.from,
        smsEnabled: notificationSettings.sms.enabled,
        smsProvider: notificationSettings.sms.provider,
        smsApiKey: notificationSettings.sms.apiKey,
        smsApiSecret: notificationSettings.sms.apiSecret,
        eventBooking: notificationSettings.events.booking,
        eventCheckin: notificationSettings.events.checkin,
        eventCheckout: notificationSettings.events.checkout,
        eventPayment: notificationSettings.events.payment
      }
      await axios.post('/api/settings/notification', data)
      alert('保存成功')
    } catch (e) {
      alert('保存失败')
    }
  }
  
  // 重置通知设置
  const resetNotificationSettings = () => {
    console.log('重置通知设置')
    // 这里可以实现重置通知设置的逻辑
  }
  
  // 备份设置
  const backupSettings = reactive({
    autoBackup: false,
    frequency: 'daily',
    retention: 7,
    lastBackupTime: '',
    status: ''
  })
  
  // 备份历史
  const backupHistory = [
    { time: '2023-05-10 08:30:15', size: '25.4 MB', type: '自动', status: 'success' },
    { time: '2023-05-09 08:30:12', size: '25.2 MB', type: '自动', status: 'success' },
    { time: '2023-05-08 08:30:10', size: '25.0 MB', type: '自动', status: 'success' },
    { time: '2023-05-07 08:30:08', size: '24.8 MB', type: '自动', status: 'success' },
    { time: '2023-05-06 08:30:05', size: '24.5 MB', type: '自动', status: 'success' },
    { time: '2023-05-05 14:15:30', size: '24.3 MB', type: '手动', status: 'success' },
    { time: '2023-05-05 08:30:00', size: '24.2 MB', type: '自动', status: 'failed' }
  ]
  
  // 获取备份状态文本
  const getBackupStatusText = (status) => {
    const statusMap = {
      'success': '成功',
      'failed': '失败',
      'in-progress': '进行中'
    }
    return statusMap[status] || status
  }
  
  // 创建备份
  const createBackup = () => {
    console.log('创建备份')
    // 这里可以实现创建备份的逻辑
  }
  
  // 恢复备份
  const restoreBackup = (backup) => {
    console.log('恢复备份:', backup)
    // 这里可以实现恢复备份的逻辑
  }
  
  // 下载备份
  const downloadBackup = (backup) => {
    console.log('下载备份:', backup)
    // 这里可以实现下载备份的逻辑
  }
  
  // 删除备份
  const deleteBackup = (backup) => {
    console.log('删除备份:', backup)
    // 这里可以实现删除备份的逻辑
    backupHistory.splice(backupHistory.indexOf(backup), 1)
  }
  
  // 处理备份文件上传
  const handleBackupFileUpload = (event) => {
    const file = event.target.files[0]
    console.log('上传备份文件:', file)
    // 这里可以实现处理备份文件上传的逻辑
  }
  
  // 保存基本设置
  const saveGeneralSettings = async () => {
    try {
      await axios.post('/api/settings/general', generalSettings)
      alert('保存成功')
    } catch (e) {
      alert('保存失败')
    }
  }
  
  // 重置基本设置
  const resetGeneralSettings = () => {
    console.log('重置基本设置')
    // 这里可以实现重置基本设置的逻辑
  }
  
  // 保存备份设置
  const saveBackupSettings = async () => {
    try {
      await axios.post('/api/settings/backup', backupSettings)
      alert('保存成功')
    } catch (e) {
      alert('保存失败')
    }
  }
  
  // 保存所有设置
  const saveAllSettings = async () => {
    await saveGeneralSettings()
    await saveNotificationSettings()
    await saveBackupSettings()
    alert('全部保存完成')
  }
  
  // 获取设置
  const fetchAllSettings = async () => {
    try {
      // 基本设置
      const generalRes = await axios.get('/api/settings/general')
      Object.assign(generalSettings, generalRes.data || {})
      // 通知设置
      const notifyRes = await axios.get('/api/settings/notification')
      if (notifyRes.data) {
        notificationSettings.email.enabled = notifyRes.data.emailEnabled
        notificationSettings.email.server = notifyRes.data.emailServer
        notificationSettings.email.port = notifyRes.data.emailPort
        notificationSettings.email.username = notifyRes.data.emailUsername
        notificationSettings.email.password = notifyRes.data.emailPassword
        notificationSettings.email.from = notifyRes.data.emailFrom
        notificationSettings.sms.enabled = notifyRes.data.smsEnabled
        notificationSettings.sms.provider = notifyRes.data.smsProvider
        notificationSettings.sms.apiKey = notifyRes.data.smsApiKey
        notificationSettings.sms.apiSecret = notifyRes.data.smsApiSecret
        notificationSettings.events.booking = notifyRes.data.eventBooking
        notificationSettings.events.checkin = notifyRes.data.eventCheckin
        notificationSettings.events.checkout = notifyRes.data.eventCheckout
        notificationSettings.events.payment = notifyRes.data.eventPayment
      }
      // 备份设置
      const backupRes = await axios.get('/api/settings/backup')
      Object.assign(backupSettings, backupRes.data || {})
    } catch (e) {
      console.error('获取设置失败', e)
    }
  }
  
  // 编辑员工模态框
  const showEditEmployeeModal = ref(false)
  const editEmployee = ref({})
  
  function openEditEmployeeModal(user) {
    editEmployee.value = { ...user }
    showEditEmployeeModal.value = true
  }
  
  async function saveEditEmployee() {
    try {
      await axios.put('/employee/update', editEmployee.value)
      showEditEmployeeModal.value = false
      fetchUsers()
    } catch (e) {
      alert('编辑失败')
    }
  }
  
  async function deleteEmployee(user) {
    if (!confirm('确定要删除该员工吗？')) return
    try {
      await axios.delete(`/employee/delete/${user.id}`)
      fetchUsers()
    } catch (e) {
      alert('删除失败')
    }
  }
  
  // 主题切换函数
  function applyTheme(theme) {
    const root = document.documentElement
    root.classList.remove('theme-light', 'theme-dark')
    if (theme === 'light') {
      root.classList.add('theme-light')
    } else if (theme === 'dark') {
      root.classList.add('theme-dark')
    } else if (theme === 'auto') {
      const isDark = window.matchMedia('(prefers-color-scheme: dark)').matches
      root.classList.add(isDark ? 'theme-dark' : 'theme-light')
    }
  }
  
  // 监听主题变化
  watch(
    () => generalSettings.theme,
    (val) => {
      applyTheme(val)
    },
    { immediate: true }
  )
  
  // 跟随系统变化自动切换
  if (window.matchMedia) {
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
      if (generalSettings.theme === 'auto') {
        applyTheme('auto')
      }
    })
  }
  </script>
  
  <style scoped>
  /* 页面容器 */
  .page-container {
    padding: 1.5rem;
    max-width: 1600px;
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
  
  /* 设置容器 */
  .settings-container {
    display: flex;
    gap: 1.5rem;
    background-color: white;
    border-radius: 0.5rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  /* 设置导航 */
  .settings-nav {
    width: 200px;
    background-color: #f8fafc;
    padding: 1rem 0;
    border-right: 1px solid #e2e8f0;
  }
  
  .nav-item {
    display: flex;
    align-items: center;
    padding: 0.75rem 1rem;
    font-size: 0.875rem;
    color: #64748b;
    width: 100%;
    text-align: left;
    background: none;
    border: none;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .nav-item:hover {
    background-color: #f1f5f9;
    color: #1e293b;
  }
  
  .nav-item.active {
    background-color: #e11d48;
    color: white;
  }
  
  .nav-icon {
    width: 1.25rem;
    height: 1.25rem;
    margin-right: 0.75rem;
  }
  
  /* 设置内容 */
  .settings-content {
    flex: 1;
    padding: 1.5rem;
    overflow: auto;
  }
  
  .settings-section {
    max-width: 1400px;
  }
  
  .section-title {
    font-size: 1.25rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 1.5rem 0;
  }
  
  .section-actions {
    margin-bottom: 1.5rem;
  }
  
  /* 表单样式 */
  .form-group {
    margin-bottom: 1.5rem;
  }
  
  .form-label {
    display: block;
    font-size: 0.875rem;
    font-weight: 500;
    color: #1e293b;
    margin-bottom: 0.5rem;
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
  
  .form-actions {
    display: flex;
    gap: 0.75rem;
    margin-top: 2rem;
  }
  
  /* 主题选择器 */
  .theme-selector {
    display: flex;
    gap: 1rem;
  }
  
  .theme-option {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
  }
  
  .theme-preview {
    width: 80px;
    height: 50px;
    border-radius: 0.375rem;
    margin-bottom: 0.5rem;
    border: 2px solid transparent;
    transition: all 0.2s ease;
  }
  
  .theme-option.active .theme-preview {
    border-color: #e11d48;
  }
  
  .light-theme {
    background-color: #ffffff;
    border: 1px solid #e2e8f0;
  }
  
  .dark-theme {
    background-color: #1e293b;
  }
  
  .auto-theme {
    background: linear-gradient(to right, #ffffff 50%, #1e293b 50%);
    border: 1px solid #e2e8f0;
  }
  
  /* 用户表格 */
  .table-container {
    width: 100%;
    min-width: unset;
    overflow-x: unset;
    margin-bottom: 1.5rem;
  }
  
  .users-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .users-table th {
    background-color: #f8fafc;
    padding: 0.75rem 1rem;
    min-width: 30px;
    text-align: left;
    font-size: 0.875rem;
    font-weight: 600;
    color: #64748b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .users-table td {
    padding: 0.75rem 1rem;
    font-size: 0.875rem;
    color: #1e293b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .role-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  .role-badge.admin {
    background-color: #ede9fe;
    color: #6d28d9;
  }
  
  .role-badge.manager {
    background-color: #dbeafe;
    color: #2563eb;
  }
  
  .role-badge.staff {
    background-color: #f1f5f9;
    color: #64748b;
  }
  
  .status-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  .status-badge.active {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .status-badge.inactive {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .status-badge.success {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .status-badge.failed {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .status-badge.in-progress {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  /* 操作列宽度固定，内容居中 */
  .users-table th:last-child,
  .users-table td.action-btns {
    width: 120px;
    min-width: 100px;
    max-width: 140px;
    text-align: center;
    padding-left: 0 !important;
    padding-right: 0 !important;
  }
  .action-btns {
    display: flex;
    gap: 8px;
    align-items: center;
    justify-content: center;
    text-align: center;
    width: 100%;
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
  
  .edit-btn {
    color: #2563eb;
  }
  
  .enable-btn {
    color: #16a34a;
  }
  
  .disable-btn {
    color: #dc2626;
  }
  
  .delete-btn {
    color: #dc2626;
  }
  
  .restore-btn {
    color: #6d28d9;
  }
  
  .download-btn {
    color: #2563eb;
  }
  
  /* 权限设置 */
  .role-tabs {
    display: flex;
    gap: 0.5rem;
    margin-bottom: 1.5rem;
    border-bottom: 1px solid #e2e8f0;
    padding-bottom: 1rem;
  }
  
  .role-tab {
    padding: 0.5rem 1rem;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    background-color: #f1f5f9;
    color: #64748b;
    border: 1px solid #e2e8f0;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .role-tab:hover {
    background-color: #e2e8f0;
  }
  
  .role-tab.active {
    background-color: #e11d48;
    color: white;
    border-color: #e11d48;
  }
  
  .permissions-container {
    margin-bottom: 1.5rem;
  }
  
  .permission-group {
    margin-bottom: 1.5rem;
  }
  
  .group-title {
    font-size: 1rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 1rem 0;
    padding-bottom: 0.5rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .permission-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 1rem;
  }
  
  .permission-item {
    display: flex;
    flex-direction: column;
  }
  
  .permission-checkbox {
    display: flex;
    align-items: center;
    margin-bottom: 0.25rem;
  }
  
  .permission-checkbox input[type="checkbox"] {
    margin-right: 0.5rem;
  }
  
  .permission-checkbox label {
    font-size: 0.875rem;
    font-weight: 500;
    color: #1e293b;
  }
  
  .permission-desc {
    font-size: 0.75rem;
    color: #64748b;
  }
  
  /* 通知设置 */
  .notification-settings {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
    margin-bottom: 1.5rem;
  }
  
  .notification-group {
    background-color: #f8fafc;
    border-radius: 0.5rem;
    padding: 1rem;
    border: 1px solid #e2e8f0;
  }
  
  .toggle-switch {
    position: relative;
    display: inline-block;
    width: 40px;
    height: 20px;
    margin-right: 0.75rem;
  }
  
  .toggle-input {
    opacity: 0;
    width: 0;
    height: 0;
  }
  
  .toggle-label {
    position: absolute;
    cursor: pointer;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #cbd5e1;
    transition: .4s;
    border-radius: 34px;
  }
  
  .toggle-label:before {
    position: absolute;
    content: "";
    height: 16px;
    width: 16px;
    left: 2px;
    bottom: 2px;
    background-color: white;
    transition: .4s;
    border-radius: 50%;
  }
  
  .toggle-input:checked + .toggle-label {
    background-color: #e11d48;
  }
  
  .toggle-input:checked + .toggle-label:before {
    transform: translateX(20px);
  }
  
  .toggle-content {
    display: flex;
    flex-direction: column;
  }
  
  .toggle-title {
    font-size: 0.875rem;
    font-weight: 500;
    color: #1e293b;
  }
  
  .toggle-desc {
    font-size: 0.75rem;
    color: #64748b;
    margin: 0;
  }
  
  /* 备份与恢复 */
  .backup-section {
    margin-bottom: 1.5rem;
    padding-bottom: 1.5rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .backup-section:last-child {
    border-bottom: none;
    padding-bottom: 0;
  }
  
  .backup-info {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    margin-bottom: 1rem;
  }
  
  .info-item {
    display: flex;
    align-items: center;
  }
  
  .info-label {
    font-size: 0.875rem;
    font-weight: 500;
    color: #1e293b;
    margin-right: 0.5rem;
  }
  
  .info-value {
    font-size: 0.875rem;
    color: #64748b;
  }
  
  .backup-actions {
    margin-bottom: 1rem;
  }
  
  .backup-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .backup-table th {
    background-color: #f8fafc;
    padding: 0.75rem 1rem;
    text-align: left;
    font-size: 0.875rem;
    font-weight: 600;
    color: #64748b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .backup-table td {
    padding: 0.75rem 1rem;
    font-size: 0.875rem;
    color: #1e293b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .upload-backup {
    margin-bottom: 1rem;
  }
  
  .upload-label {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 1.5rem;
    border: 2px dashed #cbd5e1;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .upload-label:hover {
    border-color: #e11d48;
    color: #e11d48;
  }
  
  .upload-icon {
    width: 1.5rem;
    height: 1.5rem;
    margin-right: 0.5rem;
  }
  
  .upload-input {
    display: none;
  }
  
  .warning-message {
    display: flex;
    align-items: flex-start;
    padding: 1rem;
    background-color: #fef2f2;
    border-radius: 0.5rem;
    border: 1px solid #fee2e2;
    color: #dc2626;
  }
  
  .warning-icon {
    width: 1.25rem;
    height: 1.25rem;
    margin-right: 0.75rem;
    flex-shrink: 0;
    margin-top: 0.125rem;
  }
  
  .warning-message p {
    margin: 0;
    font-size: 0.875rem;
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
    max-width: 500px;
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
  
  /* 响应式设计 */
  @media (max-width: 768px) {
    .settings-container {
      flex-direction: column;
    }
    
    .settings-nav {
      width: 100%;
      border-right: none;
      border-bottom: 1px solid #e2e8f0;
      display: flex;
      flex-wrap: wrap;
      padding: 0.5rem;
    }
    
    .nav-item {
      width: auto;
    }
    
    .permission-list {
      grid-template-columns: 1fr;
    }
    
    .theme-selector {
      flex-wrap: wrap;
    }
  }

  .page-container {
    background: var(--bg-color);
    color: var(--text-color);
  }
  .settings-content {
    background: var(--bg-color);
    color: var(--text-color);
  }
  .btn-primary {
    background: var(--primary-color);
  }
  .settings-nav {
    background: var(--secondary-bg);
    border-right: 1px solid var(--border-color);
  }
  .action-btns {
    display: flex;
    gap: 8px;
    align-items: center;
    justify-content: center;
  }
  .action-btns .btn {
    padding: 2px 10px;
    min-width: 40px;
    font-size: 12px;
    white-space: nowrap;
    height: 28px;
    line-height: 24px;
    border-radius: 4px;
  }

  </style>