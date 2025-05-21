<template>
    <div class="page-container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1 class="page-title">客户管理</h1>
        <div class="header-actions">
          <button class="btn btn-primary" @click="showAddCustomerModal = true">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 5V19M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            添加客户
          </button>
          <button class="btn btn-secondary">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 10V16M12 16L9 13M12 16L15 13M17 21H7C5.89543 21 5 20.1046 5 19V5C5 3.89543 5.89543 3 7 3H12.5858C12.851 3 13.1054 3.10536 13.2929 3.29289L18.7071 8.70711C18.8946 8.89464 19 9.149 19 9.41421V19C19 20.1046 18.1046 21 17 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            导出数据
          </button>
        </div>
      </div>
      
      <!-- 统计卡片 -->
      <div class="stats-row">
        <div class="stat-card">
          <div class="stat-icon total-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M17 21V19C17 17.9391 16.5786 16.9217 15.8284 16.1716C15.0783 15.4214 14.0609 15 13 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21M23 21V19C22.9993 18.1137 22.7044 17.2528 22.1614 16.5523C21.6184 15.8519 20.8581 15.3516 20 15.13M16 3.13C16.8604 3.3503 17.623 3.8507 18.1676 4.55231C18.7122 5.25392 19.0078 6.11683 19.0078 7.005C19.0078 7.89317 18.7122 8.75608 18.1676 9.45769C17.623 10.1593 16.8604 10.6597 16 10.88M13 7C13 9.20914 11.2091 11 9 11C6.79086 11 5 9.20914 5 7C5 4.79086 6.79086 3 9 3C11.2091 3 13 4.79086 13 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">总客户数</h3>
            <p class="stat-value">{{ customerStats.totalCount || 0 }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon new-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M16 21V19C16 17.9391 15.5786 16.9217 14.8284 16.1716C14.0783 15.4214 13.0609 15 12 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21M12.5 7C12.5 9.20914 10.7091 11 8.5 11C6.29086 11 4.5 9.20914 4.5 7C4.5 4.79086 6.29086 3 8.5 3C10.7091 3 12.5 4.79086 12.5 7ZM20 8V14M23 11H17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">本月新增</h3>
            <p class="stat-value">{{ customerStats.newMonthlyCount || 0 }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon vip-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 15C15.866 15 19 11.866 19 8C19 4.13401 15.866 1 12 1C8.13401 1 5 4.13401 5 8C5 11.866 8.13401 15 12 15Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M8.21 13.89L7 23L12 20L17 23L15.79 13.88" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">VIP客户</h3>
            <p class="stat-value">{{ customerStats.vipCount || 0 }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon active-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M16 6V18M12 10V18M8 14V18M6 22H18C19.1046 22 20 21.1046 20 20V4C20 2.89543 19.1046 2 18 2H6C4.89543 2 4 2.89543 4 4V20C4 21.1046 4.89543 22 6 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">活跃客户</h3>
            <p class="stat-value">{{ customerStats.activeCount || 0 }}</p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon visits-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M19 4H5C3.89543 4 3 4.89543 3 6V20C3 21.1046 3.89543 22 5 22H19C20.1046 22 21 21.1046 21 20V6C21 4.89543 20.1046 4 19 4Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M16 2V6M8 2V6M3 10H21M8 14H8.01M12 14H12.01M16 14H16.01M8 18H8.01M12 18H12.01M16 18H16.01" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">平均入住次数</h3>
            <p class="stat-value">{{ avgVisits }}</p>
          </div>
        </div>
      </div>
      
      <!-- 筛选和搜索 -->
      <div class="filter-section">
        <div class="filters-container">
        <div class="filter-group">
          <label for="customer-type" class="filter-label">客户类型</label>
          <select id="customer-type" class="filter-select" v-model="filters.type">
            <option value="">全部类型</option>
            <option value="regular">普通客户</option>
            <option value="vip">VIP客户</option>
            <option value="corporate">企业客户</option>
          </select>
        </div>
        
        <div class="filter-group">
          <label for="sort-by" class="filter-label">排序方式</label>
          <select id="sort-by" class="filter-select" v-model="filters.sortBy">
            <option value="name">姓名</option>
            <option value="created">注册时间</option>
            <option value="visits">入住次数</option>
            <option value="spent">消费金额</option>
          </select>
          </div>
          
          <div class="filter-group">
            <label for="top-visits" class="filter-label">入住次数筛选</label>
            <select id="top-visits" class="filter-select" v-model="filters.topVisits">
              <option value="">全部客户</option>
              <option value="top10">入住TOP10</option>
              <option value="top20">入住TOP20</option>
              <option value="frequent">经常入住(5次以上)</option>
            </select>
          </div>
        </div>
        
        <div class="search-group">
          <input type="text" class="search-input" placeholder="搜索客户姓名、电话或邮箱..." v-model="searchQuery">
          <button class="search-button" @click="searchCustomers">
            <svg class="search-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </button>
        </div>
      </div>
      
      <!-- 客户列表 -->
      <div class="customer-table-container">
        <table class="customer-table">
          <thead>
            <tr>
              <th>
                <input type="checkbox" class="select-all-checkbox" v-model="selectAll" @change="toggleSelectAll">
              </th>
              <th>客户姓名</th>
              <th>联系电话</th>
              <th>电子邮箱</th>
              <th>客户类型</th>
              <th>注册时间</th>
              <th>入住次数</th>
              <th>总消费</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="customer in filteredCustomers" :key="customer.id" :class="customer.status">
              <td>
                <input type="checkbox" class="select-checkbox" v-model="selectedCustomers" :value="customer.id">
              </td>
              <td>
                <div class="customer-name">
                  <div class="customer-avatar" :class="customer.type">
                    {{ getInitials(customer.name) }}
                  </div>
                  <span>{{ customer.name }}</span>
                </div>
              </td>
              <td>{{ customer.phone }}</td>
              <td>{{ customer.email }}</td>
              <td>
                <span class="type-badge" :class="customer.type">
                  {{ getTypeText(customer.type) }}
                </span>
              </td>
              <td>{{ customer.createTime }}</td>
              <td>{{ customer.visits || 0 }}</td>
              <td>¥{{ (customer.spent || 0).toLocaleString() }}</td>
              <td>
                <div class="table-actions">
                  <button class="action-btn view-btn" @click="viewCustomerDetails(customer)" title="查看详情">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M15 12C15 13.6569 13.6569 15 12 15C10.3431 15 9 13.6569 9 12C9 10.3431 10.3431 9 12 9C13.6569 9 15 10.3431 15 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                      <path d="M2.45825 12C3.73253 7.94288 7.52281 5 12.0004 5C16.4781 5 20.2684 7.94291 21.5426 12C20.2684 16.0571 16.4781 19 12.0005 19C7.52281 19 3.73251 16.0571 2.45825 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                  <button class="action-btn edit-btn" @click="editCustomer(customer)" title="编辑">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13M18.5 2.5C18.8978 2.10217 19.4374 1.87868 20 1.87868C20.5626 1.87868 21.1022 2.10217 21.5 2.5C21.8978 2.89782 22.1213 3.43739 22.1213 4C22.1213 4.56261 21.8978 5.10217 21.5 5.5L12 15L8 16L9 12L18.5 2.5Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                  <button class="action-btn" :class="customer.type === 'vip' ? 'downgrade-btn' : 'upgrade-btn'" @click="toggleVipStatus(customer)" :title="customer.type === 'vip' ? '取消VIP' : '升级为VIP'">
                    <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path :d="customer.type === 'vip' ? 'M19 9L12 16L5 9' : 'M5 15L12 8L19 15'" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <!-- 批量操作 -->
      <div class="bulk-actions" v-if="selectedCustomers.length > 0">
        <span class="selected-count">已选择 {{ selectedCustomers.length }} 位客户</span>
        <div class="bulk-buttons">
          <button class="btn btn-sm btn-outline" @click="bulkExport">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 10V16M12 16L9 13M12 16L15 13M17 21H7C5.89543 21 5 20.1046 5 19V5C5 3.89543 5.89543 3 7 3H12.5858C12.851 3 13.1054 3.10536 13.2929 3.29289L18.7071 8.70711C18.8946 8.89464 19 9.149 19 9.41421V19C19 20.1046 18.1046 21 17 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            导出所选
          </button>
          <button class="btn btn-sm btn-outline" @click="bulkUpgradeToVip">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 15C15.866 15 19 11.866 19 8C19 4.13401 15.866 1 12 1C8.13401 1 5 4.13401 5 8C5 11.866 8.13401 15 12 15Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M8.21 13.89L7 23L12 20L17 23L15.79 13.88" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            升级为VIP
          </button>
          <button class="btn btn-sm btn-danger" @click="bulkDelete">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M3 6H5H21M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6M10 11V17M14 11V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            删除所选
          </button>
        </div>
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
      
      <!-- 添加客户模态框 -->
      <div class="modal" v-if="showAddCustomerModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">添加新客户</h2>
            <button class="modal-close" @click="showAddCustomerModal = false">
              <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="customer-name" class="form-label">客户姓名</label>
              <input type="text" id="customer-name" class="form-input" v-model="newCustomer.name">
            </div>
            <div class="form-group">
              <label for="customer-phone" class="form-label">联系电话</label>
              <input type="tel" id="customer-phone" class="form-input" v-model="newCustomer.phone">
            </div>
            <div class="form-group">
              <label for="customer-email" class="form-label">电子邮箱</label>
              <input type="email" id="customer-email" class="form-input" v-model="newCustomer.email">
            </div>
            <div class="form-group">
              <label for="customer-type" class="form-label">客户类型</label>
              <select id="customer-type" class="form-select" v-model="newCustomer.type">
                <option value="regular">普通客户</option>
                <option value="vip">VIP客户</option>
                <option value="corporate">企业客户</option>
              </select>
            </div>
            <div class="form-group">
              <label for="customer-id-type" class="form-label">证件类型</label>
              <select id="customer-id-type" class="form-select" v-model="newCustomer.idType">
                <option value="id-card">身份证</option>
                <option value="passport">护照</option>
                <option value="driver-license">驾驶证</option>
                <option value="other">其他</option>
              </select>
            </div>
            <div class="form-group">
              <label for="customer-id-number" class="form-label">证件号码</label>
              <input type="text" id="customer-id-number" class="form-input" v-model="newCustomer.idNumber">
            </div>
            <div class="form-group">
              <label for="customer-address" class="form-label">地址</label>
              <textarea id="customer-address" class="form-textarea" v-model="newCustomer.address"></textarea>
            </div>
            <div class="form-group">
              <label for="customer-notes" class="form-label">备注</label>
              <textarea id="customer-notes" class="form-textarea" v-model="newCustomer.notes"></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showAddCustomerModal = false">取消</button>
            <button class="btn btn-primary" @click="addNewCustomer">保存</button>
          </div>
        </div>
      </div>
      
      <!-- 编辑客户模态框 -->
      <div class="modal" v-if="showEditCustomerModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">编辑客户</h2>
            <button class="modal-close" @click="showEditCustomerModal = false">
              <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="edit-customer-name" class="form-label">客户姓名</label>
              <input type="text" id="edit-customer-name" class="form-input" v-model="editCustomerData.name">
            </div>
            <div class="form-group">
              <label for="edit-customer-phone" class="form-label">联系电话</label>
              <input type="tel" id="edit-customer-phone" class="form-input" v-model="editCustomerData.phone">
            </div>
            <div class="form-group">
              <label for="edit-customer-email" class="form-label">电子邮箱</label>
              <input type="email" id="edit-customer-email" class="form-input" v-model="editCustomerData.email">
            </div>
            <div class="form-group">
              <label for="edit-customer-type" class="form-label">客户类型</label>
              <select id="edit-customer-type" class="form-select" v-model="editCustomerData.type">
                <option value="regular">普通客户</option>
                <option value="vip">VIP客户</option>
                <option value="corporate">企业客户</option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showEditCustomerModal = false">取消</button>
            <button class="btn btn-primary" @click="updateCustomer">保存</button>
          </div>
        </div>
      </div>
      
      <!-- 客户详情模态框 -->
      <div class="modal" v-if="showCustomerDetailModal">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">客户详情</h2>
            <button class="modal-close" @click="showCustomerDetailModal = false">
              <svg class="modal-close-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group"><label class="form-label">客户姓名</label><input type="text" class="form-input" :value="customerDetailData.name" readonly></div>
            <div class="form-group"><label class="form-label">联系电话</label><input type="text" class="form-input" :value="customerDetailData.phone" readonly></div>
            <div class="form-group"><label class="form-label">电子邮箱</label><input type="text" class="form-input" :value="customerDetailData.email" readonly></div>
            <div class="form-group"><label class="form-label">客户类型</label><input type="text" class="form-input" :value="getTypeText(customerDetailData.type)" readonly></div>
            <div class="form-group"><label class="form-label">注册时间</label><input type="text" class="form-input" :value="customerDetailData.createdAt" readonly></div>
            <div class="form-group"><label class="form-label">入住次数</label><input type="text" class="form-input" :value="customerDetailData.visits || 0" readonly></div>
            <div class="form-group"><label class="form-label">总消费</label><input type="text" class="form-input" :value="'¥' + (customerDetailData.spent || 0).toLocaleString()" readonly></div>
            <div class="form-group"><label class="form-label">证件类型</label><input type="text" class="form-input" :value="getIdTypeText(customerDetailData.idType)" readonly></div>
            <div class="form-group"><label class="form-label">证件号码</label><input type="text" class="form-input" :value="customerDetailData.idNumber" readonly></div>
            <div class="form-group"><label class="form-label">地址</label><input type="text" class="form-input" :value="customerDetailData.address" readonly></div>
            <div class="form-group"><label class="form-label">备注</label><input type="text" class="form-input" :value="customerDetailData.notes" readonly></div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showCustomerDetailModal = false">关闭</button>
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
  const totalPages = ref(1)
  
  // 筛选条件
  const filters = ref({
    type: '',
    sortBy: 'name',
    topVisits: ''
  })
  
  // 选择客户
  const selectAll = ref(false)
  const selectedCustomers = ref([])
  
  // 添加客户模态框
  const showAddCustomerModal = ref(false)
  const newCustomer = ref({
    name: '',
    phone: '',
    email: '',
    type: 'regular',
    idType: 'id-card',
    idNumber: '',
    address: '',
    notes: '',
    status: 'active',
    gender: '',
  })
  
  // 客户数据
  const customers = ref([])
  const loading = ref(false)
  const error = ref('')
  
  // 客户统计数据
  const customerStats = ref({
    totalCount: 0,
    newMonthlyCount: 0,
    vipCount: 0,
    activeCount: 0
  })
  
  // 编辑客户模态框
  const showEditCustomerModal = ref(false)
  const editCustomerData = ref({})
  
  // 客户详情模态框
  const showCustomerDetailModal = ref(false)
  const customerDetailData = ref({})
  
  // 平均入住次数
  const avgVisits = ref('0')
  
  // 获取客户列表
  const fetchCustomers = async () => {
    loading.value = true
    error.value = ''
    try {
      console.log('正在获取客户数据...')
      
      // 简化API调用 - 先确保基本数据能正常显示
      const res = await axios.get('/api/customers')
      console.log('API响应:', res.data)
      
      // 确保数据结构正确处理
      if (Array.isArray(res.data)) {
        // 如果是数组，直接使用
        customers.value = res.data
      totalPages.value = Math.max(1, Math.ceil(customers.value.length / 10))
      } else if (res.data && res.data.content && Array.isArray(res.data.content)) {
        // 如果是分页对象，提取内容
        customers.value = res.data.content
        totalPages.value = res.data.totalPages || 1
      } else {
        // 如果数据格式异常，设置为空数组
        console.error('API返回数据格式不正确:', res.data)
        customers.value = []
        totalPages.value = 1
      }
      
      console.log('处理后的客户数据:', customers.value)
      
      if (currentPage.value > totalPages.value) currentPage.value = totalPages.value
      
      // 尝试刷新统计数据
      await fetchCustomerStats()
    } catch (e) {
      console.error('获取客户数据失败:', e)
      error.value = e.response?.data?.message || '获取客户数据失败'
      customers.value = []
      totalPages.value = 1
    } finally {
      loading.value = false
    }
  }
  
  // 获取客户统计数据
  const fetchCustomerStats = async () => {
    try {
      // 使用专门的统计API
      const res = await axios.get('/api/customers/stats');
      console.log('获取客户统计数据:', res.data);
      customerStats.value = res.data || {
        totalCount: 0,
        newMonthlyCount: 0,
        vipCount: 0,
        activeCount: 0
      };
      
      // 更新平均入住次数
      if (res.data && typeof res.data.averageVisits !== 'undefined') {
        // 如果API返回了平均入住次数，直接使用
        avgVisits.value = Number(res.data.averageVisits).toFixed(1);
      } else {
        // 否则计算平均入住次数
        const totalVisits = customers.value.reduce((sum, customer) => {
          return sum + (customer.visits || 0);
        }, 0);
        
        avgVisits.value = customers.value.length > 0 ? 
          (totalVisits / customers.value.length).toFixed(1) : '0';
      }
    } catch (err) {
      console.error('获取客户统计数据失败:', err);
      // 如果API请求失败，尝试从已有数据计算
      if (customers.value.length > 0) {
        const now = new Date();
        const firstDayOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
        
        customerStats.value = {
          totalCount: customers.value.length,
          newMonthlyCount: customers.value.filter(c => {
            if (!c.createTime && !c.createdAt) return false;
            const createTime = new Date(c.createTime || c.createdAt);
            return createTime >= firstDayOfMonth;
          }).length,
          vipCount: customers.value.filter(c => c.type === 'vip').length,
          activeCount: customers.value.filter(c => c.visits && c.visits > 0).length
        };
        
        // 重新计算平均入住次数
        const totalVisits = customers.value.reduce((sum, customer) => {
          return sum + (customer.visits || 0);
        }, 0);
        
        avgVisits.value = (totalVisits / customers.value.length).toFixed(1);
      }
    }
  }
  
  // 搜索客户
  const searchCustomers = () => {
    currentPage.value = 1 // 重置页码
    fetchCustomers()
  }
  
  onMounted(() => {
    fetchCustomers()
    fetchCustomerStats()
  })
  
  // 添加新客户
  const addNewCustomer = async () => {
    try {
      await axios.post('/api/customers', newCustomer.value)
      showAddCustomerModal.value = false
      // 重置表单
      newCustomer.value = {
        name: '',
        phone: '',
        email: '',
        type: 'regular',
        idType: 'id-card',
        idNumber: '',
        address: '',
        notes: '',
        status: 'active',
        gender: '',
      }
      await fetchCustomers()
    } catch (e) {
      error.value = e.response?.data?.message || '添加失败'
    }
  }
  
  // 编辑客户（弹窗）
  const editCustomer = (customer) => {
    // 保留所有字段，防止未编辑字段被置为 null
    editCustomerData.value = { ...customer }
    showEditCustomerModal.value = true
  }
  
  // 更新客户
  const updateCustomer = async () => {
    try {
      await axios.put('/api/customers', editCustomerData.value)
      showEditCustomerModal.value = false
      await fetchCustomers()
    } catch (e) {
      error.value = e.response?.data?.message || '更新失败'
    }
  }
  
  // 删除客户
  const deleteCustomer = async (id) => {
    if (!confirm('确定要删除该客户吗？')) return
    try {
      await axios.delete(`/api/customers/${id}`)
      await fetchCustomers()
    } catch (e) {
      error.value = e.response?.data?.message || '删除失败'
    }
  }
  
  // 根据搜索和筛选条件过滤客户
  const filteredCustomers = computed(() => {
    let result = customers.value
    
    // 不需要在前端再过滤，因为已经交给后端处理
    // 之前的过滤代码可以注释或删除
    
    return result
  })
  
  // 获取客户姓名首字母
  const getInitials = (name) => {
    return name.charAt(0)
  }
  
  // 获取客户类型文本
  const getTypeText = (type) => {
    const typeMap = {
      'regular': '普通客户',
      'vip': 'VIP客户',
      'corporate': '企业客户'
    }
    return typeMap[type] || type
  }
  
  // 切换全选
  const toggleSelectAll = () => {
    if (selectAll.value) {
      selectedCustomers.value = filteredCustomers.value.map(customer => customer.id)
    } else {
      selectedCustomers.value = []
    }
  }
  
  // 查看客户详情
  const viewCustomerDetails = (customer) => {
    customerDetailData.value = { ...customer }
    showCustomerDetailModal.value = true
  }
  
  // 切换VIP状态
  const toggleVipStatus = (customer) => {
    console.log('切换VIP状态:', customer)
    // 这里可以实现切换VIP状态的逻辑
    const index = customers.value.findIndex(c => c.id === customer.id)
    if (index !== -1) {
      customers.value[index].type = customer.type === 'vip' ? 'regular' : 'vip'
    }
  }
  
  // 批量导出
  const bulkExport = () => {
    console.log('批量导出:', selectedCustomers.value)
    // 这里可以实现批量导出的逻辑
  }
  
  // 批量升级为VIP
  const bulkUpgradeToVip = () => {
    console.log('批量升级为VIP:', selectedCustomers.value)
    // 这里可以实现批量升级为VIP的逻辑
    customers.value = customers.value.map(customer => {
      if (selectedCustomers.value.includes(customer.id)) {
        return { ...customer, type: 'vip' }
      }
      return customer
    })
  }
  
  // 批量删除
  const bulkDelete = () => {
    console.log('批量删除:', selectedCustomers.value)
    // 这里可以实现批量删除的逻辑
    customers.value = customers.value.filter(customer => !selectedCustomers.value.includes(customer.id))
    selectedCustomers.value = []
    selectAll.value = false
  }
  
  // 证件类型文本
  const getIdTypeText = (idType) => {
    const map = {
      'id-card': '身份证',
      'passport': '护照',
      'driver-license': '驾驶证',
      'other': '其他'
    }
    return map[idType] || idType || ''
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
  
  .btn-danger {
    background-color: #ef4444;
    color: white;
  }
  
  .btn-danger:hover {
    background-color: #dc2626;
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
    grid-template-columns: repeat(5, 1fr);
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
  
  .new-icon {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .vip-icon {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  .active-icon {
    background-color: #dbeafe;
    color: #2563eb;
  }
  
  .visits-icon {
    background-color: #fff1f2;
    color: #e11d48;
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
    justify-content: space-between;
    align-items: flex-end;
  }
  
  .filters-container {
    display: flex;
    gap: 1rem;
    flex-wrap: wrap;
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
    height: 38px;
  }
  
  .search-group {
    display: flex;
    align-items: center;
    min-width: 250px;
  }
  
  .search-input {
    flex: 1;
    padding: 0.5rem;
    border: 1px solid #cbd5e1;
    border-radius: 0.375rem 0 0 0.375rem;
    font-size: 0.875rem;
    height: 38px;
  }
  
  .search-button {
    background-color: #e11d48;
    color: white;
    border: none;
    padding: 0.5rem;
    border-radius: 0 0.375rem 0.375rem 0;
    cursor: pointer;
    height: 38px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .search-icon {
    width: 1rem;
    height: 1rem;
  }
  
  /* 客户表格 */
  .customer-table-container {
    background-color: white;
    border-radius: 0.5rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    margin-bottom: 1.5rem;
    overflow-x: auto;
  }
  
  .customer-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .customer-table th {
    background-color: #f8fafc;
    padding: 0.75rem 1rem;
    text-align: left;
    font-size: 0.875rem;
    font-weight: 600;
    color: #64748b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .customer-table td {
    padding: 0.75rem 1rem;
    font-size: 0.875rem;
    color: #1e293b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .customer-table tr:last-child td {
    border-bottom: none;
  }
  
  .customer-table tr.inactive {
    background-color: #f8fafc;
  }
  
  .customer-table tr.blacklisted {
    background-color: #fef2f2;
  }
  
  .select-all-checkbox,
  .select-checkbox {
    width: 1rem;
    height: 1rem;
    border-radius: 0.25rem;
    border: 1px solid #cbd5e1;
    cursor: pointer;
  }
  
  .customer-name {
    display: flex;
    align-items: center;
  }
  
  .customer-avatar {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    background-color: #64748b;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
    margin-right: 0.75rem;
  }
  
  .customer-avatar.vip {
    background-color: #d97706;
  }
  
  .customer-avatar.corporate {
    background-color: #2563eb;
  }
  
  .type-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  .type-badge.regular {
    background-color: #f1f5f9;
    color: #64748b;
  }
  
  .type-badge.vip {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  .type-badge.corporate {
    background-color: #dbeafe;
    color: #2563eb;
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
  
  .upgrade-btn {
    color: #16a34a;
  }
  
  .downgrade-btn {
    color: #dc2626;
  }
  
  /* 批量操作 */
  .bulk-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #f8fafc;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;
    margin-bottom: 1.5rem;
  }
  
  .selected-count {
    font-size: 0.875rem;
    color: #1e293b;
  }
  
  .bulk-buttons {
    display: flex;
    gap: 0.5rem;
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
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .modal-title {
    font-size: 1.5rem;
    font-weight: 600;
    color: #1e293b;
  }
  
  .modal-close {
    background: none;
    border: none;
    font: inherit;
    cursor: pointer;
    outline: inherit;
  }
  
  .modal-body {
    padding: 1rem;
  }
  
  .form-group {
    margin-bottom: 1.5rem;
  }
  
  .form-label {
    display: block;
    font-size: 0.75rem;
    color: #64748b;
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
  
  .modal-footer {
    padding: 1rem;
    border-top: 1px solid #e2e8f0;
    display: flex;
    justify-content: flex-end;
    gap: 0.5rem;
  }
  </style>