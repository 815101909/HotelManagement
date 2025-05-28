<template>
    <div class="page-container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1 class="page-title">财务报表</h1>
        <div class="header-actions">
          <button class="btn btn-secondary">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 10V16M12 16L9 13M12 16L15 13M17 21H7C5.89543 21 5 20.1046 5 19V5C5 3.89543 5.89543 3 7 3H12.5858C12.851 3 13.1054 3.10536 13.2929 3.29289L18.7071 8.70711C18.8946 8.89464 19 9.149 19 9.41421V19C19 20.1046 18.1046 21 17 21Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            导出报表
          </button>
          <button class="btn btn-primary">
            <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M17 17H17.01M17.4 14H18C18.9319 14 19.3978 14 19.7654 14.1522C20.2554 14.3552 20.6448 14.7446 20.8478 15.2346C21 15.6022 21 16.0681 21 17C21 17.9319 21 18.3978 20.8478 18.7654C20.6448 19.2554 20.2554 19.6448 19.7654 19.8478C19.3978 20 18.9319 20 18 20H6C5.06812 20 4.60218 20 4.23463 19.8478C3.74458 19.6448 3.35523 19.2554 3.15224 18.7654C3 18.3978 3 17.9319 3 17C3 16.0681 3 15.6022 3.15224 15.2346C3.35523 14.7446 3.74458 14.3552 4.23463 14.1522C4.60218 14 5.06812 14 6 14H6.6M12 14H9C7.11438 14 6.17157 14 5.58579 13.4142C5 12.8284 5 11.8856 5 10V8C5 6.11438 5 5.17157 5.58579 4.58579C6.17157 4 7.11438 4 9 4H15C16.8856 4 17.8284 4 18.4142 4.58579C19 5.17157 19 6.11438 19 8V10C19 11.8856 19 12.8284 18.4142 13.4142C17.8284 14 16.8856 14 15 14H12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            打印报表
          </button>
        </div>
      </div>
      
      <!-- 日期筛选 -->
      <div class="date-filter">
        <div class="date-range-selector">
          <button class="date-btn" :class="{ active: dateRange === 'all' }" @click="setDateRange('all')">全部</button>
          <button class="date-btn" :class="{ active: dateRange === 'today' }" @click="setDateRange('today')">今日</button>
          <button class="date-btn" :class="{ active: dateRange === 'week' }" @click="setDateRange('week')">本周</button>
          <button class="date-btn" :class="{ active: dateRange === 'month' }" @click="setDateRange('month')">本月</button>
          <button class="date-btn" :class="{ active: dateRange === 'quarter' }" @click="setDateRange('quarter')">本季度</button>
          <button class="date-btn" :class="{ active: dateRange === 'year' }" @click="setDateRange('year')">本年</button>
          <button class="date-btn" :class="{ active: dateRange === 'custom' }" @click="setDateRange('custom')">自选日期</button>
        </div>
        
        <!-- 指定日期选择器 -->
        <div class="specific-date" v-if="dateRange === 'specific'">
          <div class="date-input-group">
            <label for="specific-date" class="date-label">选择日期</label>
            <input type="date" id="specific-date" class="date-input" v-model="specificDate">
          </div>
          <button class="btn btn-sm btn-primary" @click="applySpecificDateFilter">应用</button>
        </div>
        
        <div class="custom-date-range" v-if="dateRange === 'custom'">
          <div class="date-input-group">
            <label for="start-date" class="date-label">开始日期</label>
            <input type="date" id="start-date" class="date-input" v-model="startDate">
          </div>
          <div class="date-input-group">
            <label for="end-date" class="date-label">结束日期</label>
            <input type="date" id="end-date" class="date-input" v-model="endDate">
          </div>
          <button class="btn btn-sm btn-primary" @click="applyDateFilter">应用</button>
        </div>
      </div>
      
      <!-- 加载状态 -->
      <div class="loading-overlay" v-if="isLoading">
        <div class="spinner"></div>
        <p>加载中...</p>
      </div>
      
      <!-- 错误提示 -->
      <div class="error-message" v-if="error">
        <p>{{ error }}</p>
      </div>
      
      <!-- 财务概览 -->
      <div class="stats-row">
        <div class="stat-card">
          <div class="stat-icon revenue-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 1V23M17 5H9.5C8.57174 5 7.6815 5.36875 7.02513 6.02513C6.36875 6.6815 6 7.57174 6 8.5C6 9.42826 6.36875 10.3185 7.02513 10.9749C7.6815 11.6313 8.57174 12 9.5 12H14.5C15.4283 12 16.3185 12.3687 16.9749 13.0251C17.6313 13.6815 18 14.5717 18 15.5C18 16.4283 17.6313 17.3185 16.9749 17.9749C16.3185 18.6313 15.4283 19 14.5 19H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">总收入</h3>
            <p class="stat-value">¥{{ totalIncome.toLocaleString() }}</p>
            <p class="stat-trend positive">
              <svg class="trend-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M7 17L17 7M17 7H7M17 7V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ incomeTrend }}
            </p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon expense-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M3 9H21M9 9L9 5M5 3H19C20.1046 3 21 3.89543 21 5V19C21 20.1046 20.1046 21 19 21H5C3.89543 21 3 20.1046 3 19V5C3 3.89543 3.89543 3 5 3Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">总支出</h3>
            <p class="stat-value">¥{{ totalExpense.toLocaleString() }}</p>
            <p class="stat-trend negative">
              <svg class="trend-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M7 7L17 17M17 17V7M17 17H7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ expenseTrend }}
            </p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon profit-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 8C12 8 12 8 12.0001 8M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12ZM16 12C16 14.2091 14.2091 16 12 16C9.79086 16 8 14.2091 8 12C8 9.79086 9.79086 8 12 8C14.2091 8 16 9.79086 16 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">净利润</h3>
            <p class="stat-value">¥{{ totalProfit.toLocaleString() }}</p>
            <p class="stat-trend positive">
              <svg class="trend-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M7 17L17 7M17 7H7M17 7V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ profitTrend }}
            </p>
          </div>
        </div>
        
        <div class="stat-card">
          <div class="stat-icon occupancy-icon">
            <svg class="stat-svg" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M3 12L5 10M5 10L12 3L19 10M5 10V20C5 20.5523 5.44772 21 6 21H9M19 10L21 12M19 10V20C19 20.5523 18.5523 21 18 21H15M9 21C9.55228 21 10 20.5523 10 20V16C10 15.4477 10.4477 15 11 15H13C13.5523 15 14 15.4477 14 16V20C14 20.5523 14.4477 21 15 21M9 21H15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="stat-info">
            <h3 class="stat-title">入住率</h3>
            <p class="stat-value">{{ occupancyRate }}%</p>
            <p class="stat-trend positive">
              <svg class="trend-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M7 17L17 7M17 7H7M17 7V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ occupancyTrend }}
            </p>
          </div>
        </div>
      </div>
      
      <!-- 图表区域 -->
      <div class="charts-container">
        <!-- 收入趋势图 -->
        <div class="chart-card">
          <div class="chart-header">
            <h3 class="chart-title">收入趋势</h3>
            <div class="chart-actions">
              <select class="chart-select">
                <option value="daily">日视图</option>
                <option value="weekly">周视图</option>
                <option value="monthly" selected>月视图</option>
              </select>
            </div>
          </div>
          <div class="chart-body">
            <div class="line-chart">
              <div class="chart-y-axis">
                <div class="y-axis-label">¥150k</div>
                <div class="y-axis-label">¥100k</div>
                <div class="y-axis-label">¥50k</div>
                <div class="y-axis-label">¥0</div>
              </div>
              <div class="chart-content">
                <div class="chart-grid">
                  <div class="grid-line"></div>
                  <div class="grid-line"></div>
                  <div class="grid-line"></div>
                  <div class="grid-line"></div>
                </div>
                <div class="line-chart-bars">
                  <div v-for="(item, index) in monthlyIncomeData" :key="index" class="chart-bar-group">
                    <div class="chart-bar" :style="{ height: maxIncome > 0 ? (item.income / maxIncome * 100) + '%' : '0%' }"></div>
                    <div class="x-axis-label">{{ item.month }}</div>
                  </div>
                  </div>
                  </div>
                  </div>
          </div>
        </div>
        
        <!-- 收入来源分布 -->
        <div class="chart-card">
          <div class="chart-header">
            <h3 class="chart-title">收入来源分布</h3>
            <div class="chart-actions">
              <button class="chart-action-btn" @click="updatePieChart">
                <svg class="chart-action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M4 4V20H20" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M4 16L8 12L12 16L20 8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
              </button>
            </div>
          </div>
          <div class="chart-body">
            <!-- 饼图容器和图表部分 -->
            <div class="pie-chart-container">
              <div class="pie-chart">
                <!-- 使用conic-gradient实现饼图 -->
                <div class="pie-conic" style="background: conic-gradient(#4a89dc 0% 40%, #8e9fc4 40% 55%, #59bb6e 55% 80%, #e74c3c 80% 100%); transform: rotate(-90deg);"></div>
              </div>
              <div class="pie-legend">
                <div class="legend-item">
                  <span class="legend-color" style="background-color: #8e9fc4;"></span>
                  <span class="legend-text">单人间 (15%)</span>
                </div>
                <div class="legend-item">
                  <span class="legend-color" style="background-color: #4a89dc;"></span>
                  <span class="legend-text">双人间 (40%)</span>
                </div>
                <div class="legend-item">
                  <span class="legend-color" style="background-color: #59bb6e;"></span>
                  <span class="legend-text">豪华套房 (25%)</span>
                </div>
                <div class="legend-item">
                  <span class="legend-color" style="background-color: #e74c3c;"></span>
                  <span class="legend-text">标准间 (20%)</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 财务报表表格 -->
      <div class="report-section">
        <!-- 财务明细标题部分 -->
        <div class="section-header">
          <h3 class="section-title">财务明细</h3>
          <div class="section-actions">
            <button class="btn btn-sm btn-primary" @click="showAddExpenseModal = true">
              <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 4V20M4 12H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
              </svg>
              新增支出
            </button>
            <button class="btn btn-sm btn-success" @click="showAddIncomeModal = true">
              <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 4V20M4 12H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
              </svg>
              记录收入
            </button>
            <button class="btn btn-sm btn-outline">
              <svg class="btn-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M3 16.5L12 21.75L21 16.5M3 12L12 17.25L21 12M3 7.5L12 12.75L21 7.5L12 2.25L3 7.5Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              分类查看
            </button>
          </div>
        </div>
        
        <div class="table-container">
          <table class="financial-table">
            <thead>
              <tr>
                <th>日期</th>
                <th>类别</th>
                <th>描述</th>
                <th>收入</th>
                <th>支出</th>
                <th>净额</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in financialData" :key="index">
                <td>{{ formatDate(item.date) }}</td>
                <td>
                  <span class="category-badge" :class="item.category">{{ item.category }}</span>
                </td>
                <td>{{ item.description }}</td>
                <td class="amount income">{{ item.income > 0 ? '¥' + formatNumber(item.income) : '-' }}</td>
                <td class="amount expense">{{ item.expense > 0 ? '¥' + formatNumber(item.expense) : '-' }}</td>
                <td class="amount" :class="item.income - item.expense >= 0 ? 'positive' : 'negative'">
                  {{ (item.income - item.expense) >= 0 ? '+¥' + formatNumber(item.income - item.expense) : '-¥' + formatNumber(Math.abs(item.income - item.expense)) }}
                </td>
                <td>
                  <div class="action-buttons">
                    <button v-if="item.id && item.booking_id" class="icon-button view" title="查看详情" @click="viewBookingDetails(item.booking_id)">
                      <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                      </svg>
                    </button>
                    <!-- 删除按钮 - 只对支出数据显示 -->
                    <button v-if="item.expense > 0" class="icon-button delete" title="删除支出" @click="deleteFinancialRecord(item.id)">
                      <svg class="action-icon" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M3 6H5H21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6H19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M10 11V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M14 11V17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                      </svg>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
            <tfoot>
              <tr>
                <td colspan="3" class="total-label">总计</td>
                <td class="amount income total-income">¥{{ formatNumber(totalIncome) }}</td>
                <td class="amount expense total-expense">¥{{ formatNumber(totalExpense) }}</td>
                <td class="amount total-net" :class="totalIncome - totalExpense >= 0 ? 'positive' : 'negative'">
                  {{ (totalIncome - totalExpense) >= 0 ? '+¥' + formatNumber(totalIncome - totalExpense) : '-¥' + formatNumber(Math.abs(totalIncome - totalExpense)) }}
                </td>
                <td></td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
      
      <!-- 支出分析 -->
      <div class="report-section">
        <div class="section-header">
          <h3 class="section-title">支出分析</h3>
        </div>
        
        <div class="expense-analysis">
          <div class="expense-chart">
            <div class="horizontal-bar-chart">
              <div v-for="category in topExpenseCategories" :key="category" class="chart-item">
                <div class="chart-label">{{ category }}</div>
                <div class="chart-bar-container">
                  <div class="chart-bar" :style="{ width: getExpensePercentage(category) + '%' }">
                    {{ getExpensePercentage(category) }}%
                </div>
              </div>
                <div class="chart-value">¥{{ (expenseByCategory[category] || 0).toLocaleString() }}</div>
                </div>
            </div>
          </div>
          
          <div class="expense-summary">
            <div class="summary-card">
              <h4 class="summary-title">支出趋势</h4>
              <p class="summary-desc">本月支出较上月<span class="text-negative">增加 {{ expenseTrendPercentage }}%</span>，主要由于设施维护费用增加。</p>
            </div>
            <div class="summary-card">
              <h4 class="summary-title">优化建议</h4>
              <ul class="summary-list">
                <li>考虑优化人力资源配置，可节省约 {{ humanResourceSavings }}% 的人力成本</li>
                <li>实施节能措施，预计可减少 {{ energySavings }}% 的水电费用</li>
                <li>与供应商重新谈判，降低餐饮原材料成本</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加支出模态框 -->
    <div class="modal-overlay" v-if="showAddExpenseModal" @click.self="showAddExpenseModal = false">
      <div class="modal-container">
        <div class="modal-header">
          <h3 class="modal-title">新增支出</h3>
          <button class="modal-close" @click="showAddExpenseModal = false">×</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="addNewExpense">
            <div class="form-group">
              <label for="expense-date">日期时间</label>
              <input type="datetime-local" id="expense-date" class="form-input" v-model="newExpense.date" step="1" required>
            </div>

            <div class="form-group">
              <label for="expense-category">类别</label>
              <select id="expense-category" class="form-input" v-model="newExpense.category" required>
                <option value="">-- 请选择类别 --</option>
                <option value="人力">人力</option>
                <option value="水电">水电</option>
                <option value="设施">设施</option>
                <option value="餐饮">餐饮</option>
                <option value="其他">其他</option>
              </select>
            </div>
            <div class="form-group">
              <label for="expense-desc">描述</label>
              <input type="text" id="expense-desc" class="form-input" v-model="newExpense.description" placeholder="请输入支出描述" required>
            </div>
            <div class="form-group">
              <label for="expense-amount">金额</label>
              <input type="number" id="expense-amount" class="form-input" v-model="newExpense.amount" min="0" step="0.01" placeholder="请输入支出金额" required>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-secondary" @click="showAddExpenseModal = false">取消</button>
              <button type="submit" class="btn btn-primary">保存</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <!-- 添加收入模态框 -->
    <div class="modal-overlay" v-if="showAddIncomeModal" @click.self="showAddIncomeModal = false">
      <div class="modal-container">
        <div class="modal-header">
          <h3 class="modal-title">记录房间收入</h3>
          <button class="modal-close" @click="showAddIncomeModal = false">×</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="addNewIncome">
            <div class="form-group">
              <label for="income-date">收入日期时间</label>
              <input type="datetime-local" id="income-date" class="form-input" v-model="newIncome.date" step="1" required>
            </div>
            <div class="form-group">
              <label for="income-category">房间类型</label>
              <select id="income-category" class="form-input" v-model="newIncome.category" required>
                <option value="">-- 请选择房间类型 --</option>
                <option value="单人间">单人间</option>
                <option value="双人间">双人间</option>
                <option value="豪华套房">豪华套房</option>
                <option value="家庭套房">家庭套房</option>
                <option value="其他">其他收入</option>
              </select>
            </div>
            <div class="form-group">
              <label for="income-desc">描述</label>
              <input type="text" id="income-desc" class="form-input" v-model="newIncome.description" placeholder="请输入收入描述" required>
            </div>
            <div class="form-group">
              <label for="income-amount">金额</label>
              <input type="number" id="income-amount" class="form-input" v-model="newIncome.amount" min="0" step="0.01" placeholder="请输入收入金额" required>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-secondary" @click="showAddIncomeModal = false">取消</button>
              <button type="submit" class="btn btn-primary">保存</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 查看订单详情模态框 -->
    <div class="modal-overlay" v-if="showBookingModal" @click.self="showBookingModal = false">
      <div class="modal-container">
        <div class="modal-header">
          <h3 class="modal-title">订单详情</h3>
          <button class="modal-close" @click="showBookingModal = false">×</button>
        </div>
        <div class="modal-body">
          <!-- 订单详情内容 -->
          <div v-if="selectedBooking">
            <p><strong>房间类型:</strong> {{ selectedBooking.roomType }}</p>
            <p><strong>入住日期:</strong> {{ formatDate(selectedBooking.checkin_date) }}</p>
            <p><strong>退房日期:</strong> {{ formatDate(selectedBooking.checkout_date) }}</p>
            <p><strong>总金额:</strong> ¥{{ selectedBooking.totalAmount }}</p>
            <p><strong>状态:</strong> {{ selectedBooking.status }}</p>
            <p><strong>支付状态:</strong> {{ selectedBooking.paymentStatus }}</p>
            <p><strong>描述:</strong> {{ selectedBooking.description }}</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import axios from 'axios'
  
  // 日期筛选
  const dateRange = ref('all')  // 默认为全部
  const startDate = ref('')
  const endDate = ref('')
  const specificDate = ref('')
  
  // 获取当天日期作为默认值
  const today = new Date()
  const formatDefaultDate = (date) => {
    // return date.toISOString().split('T')[0] // 格式: YYYY-MM-DD
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
  
  // 财务数据
  const financialData = ref([])
  const incomeRecords = ref([])
  const expenseRecords = ref([])
  const isLoading = ref(false)
  const error = ref('')
  
  // 查看订单详情
  const selectedBooking = ref(null)
  const showBookingModal = ref(false)
  
  // 查看订单详情
  const viewBookingDetails = async (bookingId) => {
    if (!bookingId) return;
    
    isLoading.value = true;
    try {
      const response = await axios.get(`/api/admin/bookings/${bookingId}`);
      console.log('获取到的订单详情:', response.data);
      selectedBooking.value = response.data;
      showBookingModal.value = true;
    } catch (error) {
      console.error('获取订单详情失败:', error);
      alert('获取订单详情失败: ' + error.message);
    } finally {
      isLoading.value = false;
    }
  }
  
  // 收入来源数据
  const sourcesData = ref([])
  
  // 获取财务数据
  const fetchFinancialData = async () => {
    isLoading.value = true
    error.value = ''
    
    try {
      console.log('开始获取财务数据，当前日期范围:', dateRange.value)
        if (dateRange.value === 'custom') {
        console.log('自定义日期范围:', startDate.value, '至', endDate.value)
      } else if (dateRange.value === 'today') {
        const currentDate = formatDefaultDate(new Date())
        console.log('当日数据:', currentDate)
      } else if (dateRange.value === 'specific') {
        console.log('指定日期数据:', specificDate.value)
      }
        
        // 构建请求参数，包含日期筛选
      const params = {}
        
      // 添加日期过滤参数 (除了全部范围外都需要)
      if (dateRange.value !== 'all') {
        if (dateRange.value === 'today') {
          // 确保"今日"筛选使用系统当前日期
          const currentDate = formatDefaultDate(new Date())
          params.startDate = currentDate
          params.endDate = currentDate
          // 同时更新组件的日期值
          startDate.value = currentDate
          endDate.value = currentDate
        } else if (dateRange.value === 'specific') {
          // 使用指定的日期
          if (specificDate.value) {
            params.startDate = specificDate.value
            params.endDate = specificDate.value
          }
        } else {
          if (startDate.value) {
            params.startDate = startDate.value
          }
          if (endDate.value) {
            params.endDate = endDate.value
          }
        }
      }
      
      // 初始化空数据数组
      financialData.value = []
      incomeRecords.value = []
      expenseRecords.value = []
      
      // 第0步：先尝试获取已付款的预订数据
      try {
        const paidBookingsUrl = '/api/admin/bookings/paid';
        const paidBookingsRes = await axios.get(paidBookingsUrl, { params });
        console.log('已付款预订数据:', paidBookingsRes.data);
        
        if (paidBookingsRes.data && Array.isArray(paidBookingsRes.data) && paidBookingsRes.data.length > 0) {
          // 处理已付款的预订数据
          processBookingsData(paidBookingsRes.data);
          console.log('已处理付款预订数据, 共处理了:', paidBookingsRes.data.length, '条');
          
          // 获取支出数据以完成财务报表
          await fetchExpenseData(params);
          
          // 更新净利润
          totalProfit.value = totalIncome.value - totalExpense.value;
          
          // 财务数据按日期排序（降序，最新的在前面）
          sortFinancialData();
          
          // 更新图表
          updateCharts();
          
          return; // 结束处理
        } else {
          console.warn('未获取到已付款的预订数据，将尝试获取收入数据');
        }
      } catch (err) {
        console.warn('获取付款预订数据失败，将尝试获取收入数据', err);
      }
      
      // 第1步：获取收入数据
      try {
        const incomeUrl = '/api/admin/finance/income'
        // 使用日期参数获取收入数据
        const incomeRes = await axios.get(incomeUrl, { params })
        console.log('收入数据:', incomeRes.data)
        
        if (incomeRes.data && Array.isArray(incomeRes.data)) {
          // 设置收入记录
          incomeRecords.value = incomeRes.data.map(item => {
            // 确保日期格式一致
            let dateStr = item.date;
            if (typeof dateStr === 'string' && dateStr.includes(' ')) {
              // 如果日期包含空格，可能是 "yyyy-MM-dd HH:mm:ss" 格式
              dateStr = dateStr.split(' ')[0]; // 只取日期部分
            }
            
            return {
              id: item.id,
              date: dateStr,
              category: item.category || '其他',
              description: item.description || '收入记录',
              amount: Number(item.amount || 0),
              status: item.status || 'PAID',
              booking_id: item.booking_id, // 添加booking_id字段
              synced: item.synced,
              create_time: item.create_time || item.createTime || new Date().toISOString()
            };
          })
          
          // 计算总收入
          totalIncome.value = incomeRecords.value.reduce((sum, item) => sum + item.amount, 0)
          
          // 按类别统计收入
          incomeByCategory.value = {}
          incomeRecords.value.forEach(item => {
            const category = item.category || '其他';
            if (!incomeByCategory.value[category]) {
              incomeByCategory.value[category] = 0
            }
            incomeByCategory.value[category] += item.amount
          })
          
          // 添加到财务明细
          incomeRecords.value.forEach(item => {
            financialData.value.push({
              date: item.date,
              category: item.category,
              description: item.description,
              income: item.amount,
              expense: 0,
              booking_id: item.booking_id, // 添加booking_id方便跟踪
              id: item.id
            })
          })
          
          console.log('处理了收入记录:', incomeRecords.value.length, '条')
        } else {
          console.warn('未获取到有效的收入数据')
        }
      } catch (err) {
        console.warn('获取收入数据失败', err)
        error.value = '获取收入数据失败，请检查API连接'
      }
      
      // 第2步：获取支出数据
      await fetchExpenseData(params);
      
      // 第3步：处理月度趋势和其他统计数据
      try {
        // 处理月度趋势数据
        const trendUrl = '/api/admin/finance/trend/monthly';
        // 使用日期参数获取趋势数据
        const trendRes = await axios.get(trendUrl, { params });
        console.log('月度收入趋势数据:', trendRes.data);
        
        if (trendRes.data && Array.isArray(trendRes.data)) {
          // 使用API返回的月度趋势数据
          processMonthlyTrendData(trendRes.data);
        } else {
          // 如果没有数据，使用空数据
        const monthNames = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
        monthlyIncomeData.value = monthNames.map(month => ({ month, income: 0 }));
      }
      
        // 获取收入来源分布数据
        const sourcesUrl = '/api/admin/finance/sources';
        const sourcesRes = await axios.get(sourcesUrl, { params });
        console.log('收入来源数据:', sourcesRes.data);
        
        if (sourcesRes.data && Array.isArray(sourcesRes.data)) {
          sourcesData.value = sourcesRes.data;
          processIncomeSources();
      }
      
        // 获取房间统计数据
        const roomsUrl = '/api/admin/rooms/stats';
        const roomsRes = await axios.get(roomsUrl, { params });
        console.log('房间统计数据:', roomsRes.data);
        
        if (roomsRes.data && typeof roomsRes.data === 'object') {
          const total = roomsRes.data.total || 0;
          const booked = roomsRes.data.booked || 0;
          
          if (total > 0) {
            occupancyRate.value = Math.round((booked / total) * 100);
          } else {
            occupancyRate.value = 0;
          }
        } else {
          occupancyRate.value = 0;
        }
      } catch (err) {
        console.warn('获取统计数据失败:', err);
        // 设置默认的月度数据
        setupDefaultMonthlyData();
      }
      
      // 计算净利润
      totalProfit.value = totalIncome.value - totalExpense.value
      
      // 财务数据按日期排序（降序，最新的在前面）
      sortFinancialData();
      
      // 设置趋势数据（这里可以补充同比增长等计算逻辑）
      if (totalIncome.value > 0) {
        incomeTrend.value = '5.2% 同比'
      } else {
        incomeTrend.value = '0% 同比'
      }
      
      if (totalExpense.value > 0) {
        expenseTrend.value = '2.8% 同比'
      } else {
        expenseTrend.value = '0% 同比'
      }
      
      if (totalProfit.value > 0) {
        profitTrend.value = '6.5% 同比'
      } else {
        profitTrend.value = '0% 同比'
      }
      
      // 更新图表
      updateCharts()
      
    } catch (err) {
      console.error('获取财务数据失败:', err);
      error.value = '获取财务数据失败，请稍后再试';
      
      // 设置空数据
      totalIncome.value = 0;
      totalExpense.value = 0;
      totalProfit.value = 0;
      occupancyRate.value = 0;
      incomeByCategory.value = {};
      expenseByCategory.value = {};
      financialData.value = [];
      
    } finally {
      setTimeout(() => {
        isLoading.value = false;
      }, 300);
    }
  }
  
  // 排序财务数据，确保最新的在前面
  const sortFinancialData = () => {
    // 财务数据按日期排序（降序，最新的在前面）
    financialData.value.sort((a, b) => {
      // 确保使用完整日期+时间进行比较
      let dateA = a.date;
      let dateB = b.date;
      
      // 如果只有日期部分，添加时间部分进行比较
      if (typeof dateA === 'string' && dateA.length === 10) {
        dateA = dateA + ' 00:00:00';
      }
      if (typeof dateB === 'string' && dateB.length === 10) {
        dateB = dateB + ' 00:00:00';
      }
      
      // 如果有createTime，优先使用它进行排序
      if (a.createTime && b.createTime) {
        return new Date(b.createTime) - new Date(a.createTime);
      }
      
      // 否则使用date字段
      return new Date(dateB) - new Date(dateA);
    });
    
    console.log('财务数据已按日期降序排序，最新的在前面');
  }
  
  // 更新所有图表
  const updateCharts = () => {
    setTimeout(() => {
      updatePieChart()
      updateIncomeChart()
      updateExpenseChart()
    }, 100)
  }
  
  // 获取支出数据
  const fetchExpenseData = async (params) => {
    try {
      // 构建支出数据URL
      const expensesUrl = '/api/admin/finance/expenses';
      
      // 尝试获取支出数据
      const expensesRes = await axios.get(expensesUrl, { params });
      console.log('支出数据:', expensesRes.data);
      
      if (expensesRes.data && Array.isArray(expensesRes.data)) {
        // 处理每条支出记录
        expenseRecords.value = expensesRes.data.map(expense => {
          const amount = Number(expense.amount || 0);
          const category = expense.category || '其他';
          const description = expense.description || `${category}支出`;
          
          // 优先使用createTime作为日期，精确到秒
          let dateStr = new Date().toISOString().split('T')[0]; // 默认今天
          
          // 优先使用createTime，因为它精确到秒
          if (expense.createTime) {
            if (typeof expense.createTime === 'string') {
              // 如果是字符串格式，直接使用
              dateStr = expense.createTime;
            } else if (expense.createTime instanceof Date) {
              // 如果是Date对象，转换为字符串
              dateStr = expense.createTime.toISOString();
            } else if (typeof expense.createTime === 'number') {
              // 如果是时间戳，转换为字符串
              dateStr = new Date(expense.createTime).toISOString();
            }
          } 
          // 如果没有createTime，才使用expenseDate
          else if (expense.expenseDate) {
            if (typeof expense.expenseDate === 'string') {
              dateStr = expense.expenseDate;
            } else if (expense.expenseDate instanceof Date) {
              dateStr = expense.expenseDate.toISOString();
            } else if (typeof expense.expenseDate === 'number') {
              dateStr = new Date(expense.expenseDate).toISOString();
            }
          }
          
          return {
            id: expense.id, // 确保ID被正确设置
            date: dateStr,
            category: category,
            description: description,
            amount: amount,
            createTime: expense.createTime // 保存原始createTime
          };
        });
        
        // 计算总支出
        totalExpense.value = expenseRecords.value.reduce((sum, item) => sum + item.amount, 0);
        
        // 按类别统计支出
        expenseByCategory.value = {};
        expenseRecords.value.forEach(item => {
          if (!expenseByCategory.value[item.category]) {
            expenseByCategory.value[item.category] = 0;
          }
          expenseByCategory.value[item.category] += item.amount;
        });
        
        // 添加到财务明细
        expenseRecords.value.forEach(item => {
          financialData.value.push({
            id: item.id, // 确保ID被正确添加到财务数据
            date: item.date,
            category: item.category,
            description: item.description,
            income: 0,
            expense: item.amount
          });
        });
        
        console.log('处理了支出记录:', expenseRecords.value.length, '条');
      } else if (expensesRes.data && expensesRes.data.total !== undefined) {
        // 如果是汇总形式
        totalExpense.value = Number(expensesRes.data.total || 0);
        
        // 如果有按类别分的支出
        if (expensesRes.data.categories && typeof expensesRes.data.categories === 'object') {
          expenseByCategory.value = Object.keys(expensesRes.data.categories).reduce((acc, key) => {
            acc[key] = Number(expensesRes.data.categories[key] || 0);
            return acc;
          }, {});
        }
      } else {
        console.warn('未获取到有效的支出数据');
        totalExpense.value = 0;
        expenseByCategory.value = {};
      }
    } catch (err) {
      console.warn('获取支出数据失败:', err);
      // 如果无法获取支出数据，支出为0
      totalExpense.value = 0;
      expenseByCategory.value = {};
    }
  }
  
  // 处理已付款的预订数据
  const processBookingsData = (paidBookings) => {
    // 重置数据
    totalIncome.value = 0
    incomeByCategory.value = {}
    financialData.value = []
    
    console.log('Processing paid bookings:', paidBookings)
    
    // 检查是否有有效的预订
    if (!paidBookings || paidBookings.length === 0) {
      console.warn('No valid paid bookings found')
      // 直接显示错误消息，不生成测试数据
      error.value = '没有找到已付款的预订数据'
      return
    }
    
    // 按房间类型分类收入
    paidBookings.forEach(booking => {
      console.log('Processing booking:', booking)
      
      // 确保只处理已付款状态的预订 (二次确认)
      // 同时支持"confirmed"状态
      if (booking.paymentStatus !== 'PAID' && 
          booking.paymentStatus !== 'paid' && 
          booking.status !== 'confirmed' && 
          booking.status !== 'CONFIRMED') {
        console.log('Skipping non-paid/non-confirmed booking:', booking)
        return
      }
      
      // 提取房间类型信息
      const roomType = 
        booking.roomType || 
        booking.room_id || // 直接使用room_id作为房间类型
        (booking.room && booking.room.type) || 
        (booking.roomTypeId ? '房间-' + booking.roomTypeId : '其他')
      
      // 检查是否有金额
      let amount = 0
      if (booking.totalAmount !== undefined) amount = Number(booking.totalAmount)
      else if (booking.amount !== undefined) amount = Number(booking.amount)
      else if (booking.total !== undefined) amount = Number(booking.total)
      else if (booking.price !== undefined) amount = Number(booking.price)
      else if (booking.total_price !== undefined) amount = Number(booking.total_price) // 支持数据库中的total_price字段
      
      // 使用createTime作为日期(确保格式一致)
      let date = new Date().toISOString().split('T')[0] // 默认为今天
      
      // 按优先级处理各种可能的日期字段
      if (booking.createTime) {
        // 处理不同格式的createTime
        if (typeof booking.createTime === 'string') {
          // 如果是字符串，尝试转换为日期格式
          if (booking.createTime.includes('T')) {
            // ISO格式，例如: "2023-05-01T12:00:00"
            date = booking.createTime.split('T')[0]
          } else if (booking.createTime.includes('-')) {
            // 可能是普通日期格式，例如: "2023-05-01"
            date = booking.createTime
          } else if (!isNaN(Number(booking.createTime))) {
            // 可能是时间戳
            date = new Date(Number(booking.createTime)).toISOString().split('T')[0]
          }
        } else if (booking.createTime instanceof Date) {
          // 如果是Date对象
          date = booking.createTime.toISOString().split('T')[0]
        } else if (typeof booking.createTime === 'number') {
          // 如果是时间戳
          date = new Date(booking.createTime).toISOString().split('T')[0]
        }
      } else if (booking.create_time) {
        // 支持数据库中的create_time字段
        date = booking.create_time.split(' ')[0] 
      } else if (booking.checkout_date) {
        // 支持数据库中的checkout_date字段
        date = booking.checkout_date
      } else if (booking.checkin_date) {
        // 支持数据库中的checkin_date字段 
        date = booking.checkin_date
      } else if (booking.createDate) {
        // 备用字段
        if (typeof booking.createDate === 'string') {
          date = booking.createDate.includes('T') ? booking.createDate.split('T')[0] : booking.createDate
        } else if (booking.createDate instanceof Date) {
          date = booking.createDate.toISOString().split('T')[0]
        }
      }
      
      // 从数据库获取房间描述
      let description = '房间收入'
      if (booking.room && booking.room.description) {
        description = booking.room.description
      } else if (booking.description) {
        description = booking.description
      } else if (booking.status) {
        // 使用状态作为描述的一部分
        description = `${roomType}收入 (${booking.status})`
      } else {
        description = `${roomType}收入`
      }
      
      console.log(`Booking details - Type: ${roomType}, Amount: ${amount}, Date: ${date}, Desc: ${description}`)
      
      // 只处理有金额的预订
      if (amount > 0) {
        // 累计总收入
        totalIncome.value += amount
        
        // 按房间类型累计收入
        if (!incomeByCategory.value[roomType]) {
          incomeByCategory.value[roomType] = 0
        }
        incomeByCategory.value[roomType] += amount
        
        // 添加到财务明细数据
        financialData.value.push({
          date: date,
          category: roomType,
          description: description,
          income: amount,
      expense: 0 
        })
      } else {
        console.warn(`Skipping booking with zero amount:`, booking)
      }
    })
  }
  
  // 处理月度趋势数据
  const processMonthlyTrendData = (data) => {
    const monthNames = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    
    if (data.length <= 12) {
      // 如果数据少于12个月，按顺序填充
      monthlyIncomeData.value = data.map((item, index) => {
        // 处理不同的数据格式
        const monthName = item.month || item.name || monthNames[index]
        const income = item.income || item.amount || item.value || 0
        
        return {
          month: monthName,
          income: income
        }
      })
      
      // 如果数据不足12个月，用空数据填充
      if (monthlyIncomeData.value.length < 12) {
        for (let i = monthlyIncomeData.value.length; i < 12; i++) {
          monthlyIncomeData.value.push({
            month: monthNames[i],
            income: 0
          })
        }
      }
    } else {
      // 如果有超过12个月的数据，只取最近12个月
      monthlyIncomeData.value = data.slice(-12).map((item, index) => {
        const monthName = item.month || item.name || monthNames[index]
        const income = item.income || item.amount || item.value || 0
        
        return {
          month: monthName,
          income: income
        }
      })
    }
  }
  
  // 处理收入来源数据
  const processIncomeSources = () => {
    // 如果有源数据并且包含房间类型
    if (sourcesData.value && Array.isArray(sourcesData.value) && sourcesData.value.length > 0) {
      const categories = {};
      
      // 从API数据中提取各种收入来源
      sourcesData.value.forEach(source => {
        if (source.name && (source.percentage || source.value || source.amount)) {
          const percentage = source.percentage || source.value || 0;
          const amount = Math.round((percentage / 100) * totalIncome.value);
          categories[source.name] = amount;
        }
      });
      
      incomeByCategory.value = categories;
    } else if (Object.keys(incomeByCategory.value).length === 0) {
      // 如果没有API数据也没有从预订中提取到收入分类
      // 保持空对象，不添加任何默认值
      incomeByCategory.value = {};
    }
  }
  
  // 设置默认月度数据
  const setupDefaultMonthlyData = () => {
    const monthNames = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
    
    // 不生成模拟数据，使用空数据
    monthlyIncomeData.value = monthNames.map(month => {
      return {
        month,
        income: 0
      };
    });
  }
  
  // 应用日期过滤
  const applyDateFilter = () => {
    console.log(`应用日期过滤: ${dateRange.value}, 从 ${startDate.value} 到 ${endDate.value}`)
    
    // 保存到localStorage
    try {
      localStorage.setItem('financialDateRange', dateRange.value);
      localStorage.setItem('financialStartDate', startDate.value);
      localStorage.setItem('financialEndDate', endDate.value);
    } catch (e) {
      console.error('保存日期范围到localStorage失败:', e);
    }
    
    // 设置loading状态并清除之前的错误
    isLoading.value = true;
    error.value = '';
    
    // 应用过滤，始终获取最新的服务器数据
    fetchFinancialData();
  }
  
  // 统计数据
  const totalIncome = ref(0)
  const totalExpense = ref(0)
  const totalProfit = ref(0)
  const incomeByCategory = ref({})
  const expenseByCategory = ref({})
  const occupancyRate = ref(75) // 默认值
  
  // 趋势数据（通过API计算或设置一个合理值）
  const incomeTrend = ref('0% 同比')
  const expenseTrend = ref('0% 同比')
  const profitTrend = ref('0% 同比')
  const occupancyTrend = ref('0% 同比')
  const expenseTrendPercentage = ref(0)
  const humanResourceSavings = ref(0)
  const energySavings = ref(0)
  
  // 计算统计数据
  const calculateStats = () => {
  // 计算总收入和总支出
    totalIncome.value = financialData.value.reduce((sum, item) => {
      const income = item.income || 0;
      return sum + income;
    }, 0);
    
    totalExpense.value = financialData.value.reduce((sum, item) => sum + (item.expense || 0), 0);
    totalProfit.value = totalIncome.value - totalExpense.value;
    
    // 计算各类别收入占比
    const incomeCategories = {};
    const expenseCategories = {};
    
    financialData.value.forEach(item => {
      // 收入统计
      if (item.income > 0) {
        if (!incomeCategories[item.category]) {
          incomeCategories[item.category] = 0;
        }
        incomeCategories[item.category] += item.income;
      }
      
      // 支出统计
      if (item.expense > 0) {
        if (!expenseCategories[item.category]) {
          expenseCategories[item.category] = 0;
        }
        expenseCategories[item.category] += item.expense;
      }
    });
    
    incomeByCategory.value = incomeCategories;
    expenseByCategory.value = expenseCategories;
    
    // 确保总收入准确计算
    console.log('Total income calculated:', totalIncome.value);
    console.log('Income by category:', incomeByCategory.value);
    
    // 更新图表
    setTimeout(() => {
      updatePieChart();
      updateExpenseChart();
    }, 100);
  }
  
  // 获取支出占比
  const getExpensePercentage = (category) => {
    if (!expenseByCategory.value[category] || totalExpense.value === 0) return 0
    return Math.round((expenseByCategory.value[category] / totalExpense.value) * 100)
  }
  
  // 获取收入占比
  const getIncomePercentage = (category) => {
    if (!incomeByCategory.value[category] || totalIncome.value === 0) return 0
    return Math.round((incomeByCategory.value[category] / totalIncome.value) * 100)
  }
  
  // 更新饼图显示
  const updatePieChart = () => {
    // 从财务数据中提取不同房型的收入
    const roomTypeIncome = { '单人间': 0, '双人间': 0, '套房': 0, '标准间': 0 };
    let totalIncomeAmount = 0;
    
    financialData.value.forEach(item => {
      if (item.income > 0) {
        const category = 
          item.category === '单人间' ? '单人间' : 
          item.category === '双人间' ? '双人间' : 
          item.category === '套房' ? '套房' :
          item.category === '标准间' ? '标准间' : null;
        
        if (category) {
          roomTypeIncome[category] += item.income;
          totalIncomeAmount += item.income;
        }
      }
    });
    
    // 计算各类型占比
    let singleRoomPercentage = 0;
    let doubleRoomPercentage = 0;
    let suitePercentage = 0;
    let standardRoomPercentage = 0;
    
    if (totalIncomeAmount > 0) {
      singleRoomPercentage = Math.round((roomTypeIncome['单人间'] / totalIncomeAmount) * 100);
      doubleRoomPercentage = Math.round((roomTypeIncome['双人间'] / totalIncomeAmount) * 100);
      suitePercentage = Math.round((roomTypeIncome['套房'] / totalIncomeAmount) * 100);
      standardRoomPercentage = Math.round((roomTypeIncome['标准间'] / totalIncomeAmount) * 100);
      
      // 确保总和为100%
      const sum = singleRoomPercentage + doubleRoomPercentage + suitePercentage + standardRoomPercentage;
      if (sum !== 100 && sum > 0) {
        // 调整最大的那个值以确保总和为100
        if (singleRoomPercentage >= doubleRoomPercentage && singleRoomPercentage >= suitePercentage && singleRoomPercentage >= standardRoomPercentage) {
          singleRoomPercentage = 100 - doubleRoomPercentage - suitePercentage - standardRoomPercentage;
        } else if (doubleRoomPercentage >= singleRoomPercentage && doubleRoomPercentage >= suitePercentage && doubleRoomPercentage >= standardRoomPercentage) {
          doubleRoomPercentage = 100 - singleRoomPercentage - suitePercentage - standardRoomPercentage;
        } else if (suitePercentage >= singleRoomPercentage && suitePercentage >= doubleRoomPercentage && suitePercentage >= standardRoomPercentage) {
          suitePercentage = 100 - singleRoomPercentage - doubleRoomPercentage - standardRoomPercentage;
        } else {
          standardRoomPercentage = 100 - singleRoomPercentage - doubleRoomPercentage - suitePercentage;
        }
      }
    }
    
    // 更新conic-gradient的背景样式
    const pieEl = document.querySelector('.pie-conic');
    if (pieEl) {
      pieEl.style.background = `conic-gradient(
        #4a89dc 0% ${doubleRoomPercentage}%, 
        #8e9fc4 ${doubleRoomPercentage}% ${doubleRoomPercentage + singleRoomPercentage}%, 
        #59bb6e ${doubleRoomPercentage + singleRoomPercentage}% ${doubleRoomPercentage + singleRoomPercentage + suitePercentage}%,
        #e74c3c ${doubleRoomPercentage + singleRoomPercentage + suitePercentage}% 100%)`;
      pieEl.style.transform = 'rotate(-90deg)';
    }
    
    // 更新图例
    const legendItems = document.querySelectorAll('.pie-legend .legend-item');
    if (legendItems.length >= 4) {
      // 单人间图例
      const singleRoomText = legendItems[0].querySelector('.legend-text');
      if (singleRoomText) {
        singleRoomText.textContent = `单人间 (${singleRoomPercentage}%)`;
      }
      
      // 双人间图例
      const doubleRoomText = legendItems[1].querySelector('.legend-text');
      if (doubleRoomText) {
        doubleRoomText.textContent = `双人间 (${doubleRoomPercentage}%)`;
      }
      
      // 套房图例
      const suiteRoomText = legendItems[2].querySelector('.legend-text');
      if (suiteRoomText) {
        suiteRoomText.textContent = `豪华套房 (${suitePercentage}%)`;
      }
      
      // 标准间图例
      const standardRoomText = legendItems[3].querySelector('.legend-text');
      if (standardRoomText) {
        standardRoomText.textContent = `标准间 (${standardRoomPercentage}%)`;
      }
    }
  }
  
  // 更新水平柱状图
  const updateExpenseChart = () => {
    // 获取支出类别排序
    const sortedExpenses = Object.keys(expenseByCategory.value)
      .sort((a, b) => expenseByCategory.value[b] - expenseByCategory.value[a])
    
    // 更新柱状图百分比
    sortedExpenses.forEach((category, index) => {
      const chartItem = document.querySelector(`.chart-item:nth-child(${index + 1}) .chart-bar`)
      if (chartItem) {
        const percentage = getExpensePercentage(category)
        chartItem.style.width = `${percentage}%`
        chartItem.textContent = `${percentage}%`
      }
    })
  }
  
  // 获取类别颜色
  const getCategoryColor = (category) => {
    const colors = {
      '单人间': '#4f87c7', // 浅蓝色
      '双人间': '#8e9fc4', // 灰蓝色
      '餐饮': '#10b981',
      '其他': '#f59e0b',
      '设施': '#ef4444',
      '人力': '#8b5cf6',
      '水电': '#0ea5e9'
    }
    return colors[category] || '#94a3b8'
  }
  
  // 获取类别在饼图中的偏移量
  const getCategoryOffset = (index) => {
    const offsets = []
    for (let i = 0; i < index; i++) {
      offsets.push(getIncomePercentage(topIncomeCategories.value[i]))
    }
    return offsets.reduce((sum, val) => sum + val, 0)
  }
  
  // 排序后的收入类别（前4个）
  const topIncomeCategories = computed(() => {
    return Object.keys(incomeByCategory.value)
      .sort((a, b) => incomeByCategory.value[b] - incomeByCategory.value[a])
      .slice(0, 4)
  })
  
  // 排序后的支出类别（前5个）
  const topExpenseCategories = computed(() => {
    return Object.keys(expenseByCategory.value)
      .sort((a, b) => expenseByCategory.value[b] - expenseByCategory.value[a])
      .slice(0, 5)
  })
  
  // 设置日期范围
  const setDateRange = (range) => {
    dateRange.value = range;
    const systemDate = new Date();
    if (range === 'today') {
      const todayStr = formatDefaultDate(systemDate);
      startDate.value = todayStr;
      endDate.value = todayStr;
      applyDateFilter();
    } else if (range === 'week') {
      const day = systemDate.getDay() || 7;
      const weekStart = new Date(systemDate);
      weekStart.setDate(systemDate.getDate() - day + 1);
      startDate.value = formatDefaultDate(weekStart);
      endDate.value = formatDefaultDate(systemDate);
      applyDateFilter();
    } else if (range === 'month') {
      const monthStart = new Date(systemDate.getFullYear(), systemDate.getMonth(), 1);
      startDate.value = formatDefaultDate(monthStart);
      endDate.value = formatDefaultDate(systemDate);
      applyDateFilter();
    } else if (range === 'quarter') {
      const month = systemDate.getMonth();
      const quarterStartMonth = Math.floor(month / 3) * 3;
      const quarterStart = new Date(systemDate.getFullYear(), quarterStartMonth, 1);
      startDate.value = formatDefaultDate(quarterStart);
      endDate.value = formatDefaultDate(systemDate);
      applyDateFilter();
    } else if (range === 'year') {
      const yearStart = new Date(systemDate.getFullYear(), 0, 1);
      startDate.value = formatDefaultDate(yearStart);
      endDate.value = formatDefaultDate(systemDate);
      applyDateFilter();
    } else if (range === 'all') {
      startDate.value = '';
      endDate.value = '';
      applyDateFilter();
    }
    // 自选日期和其它情况保持原逻辑
  };
  
  // 页面加载时获取数据
  onMounted(() => {
    // 尝试从localStorage恢复之前的日期过滤设置
    try {
      const savedDateRange = localStorage.getItem('financialDateRange');
      const savedStartDate = localStorage.getItem('financialStartDate');
      const savedEndDate = localStorage.getItem('financialEndDate');
      const savedSpecificDate = localStorage.getItem('financialSpecificDate');
      
      // 如果有保存的日期范围，则使用它
      if (savedDateRange) {
        dateRange.value = savedDateRange;
        
        if (savedStartDate) startDate.value = savedStartDate;
        if (savedEndDate) endDate.value = savedEndDate;
        if (savedSpecificDate) specificDate.value = savedSpecificDate;
        
        console.log('已从本地存储恢复日期范围设置:', dateRange.value, startDate.value, endDate.value, specificDate.value);
      }
    } catch (e) {
      console.error('从localStorage恢复日期设置失败:', e);
    }
    
    // 获取财务数据
    fetchFinancialData()
  })
  
  // 格式化数字
  const formatNumber = (num) => {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }
  
  // 格式化日期
  const formatDate = (dateVal) => {
    if (!dateVal) return '-';
    
    try {
      // 处理日期对象
      if (dateVal instanceof Date) {
        return dateVal.toLocaleDateString('zh-CN') + ' ' + 
               dateVal.toLocaleTimeString('zh-CN', {hour: '2-digit', minute:'2-digit', second:'2-digit'});
      }
      
      // 处理字符串格式的日期
      if (typeof dateVal === 'string') {
        // 如果是ISO格式日期 或 yyyy-MM-dd HH:mm:ss 格式
        if (dateVal.includes('T') || dateVal.includes(':')) {
          // 保留完整的日期时间格式，包括秒
          const parts = dateVal.split(/[T ]/); // 用T或空格分割
          if (parts.length >= 2) {
            const datePart = parts[0].replace(/-/g, '/');
            let timePart = parts[1];
            
            // 确保时间部分包含秒
            if (timePart.split(':').length === 2) {
              timePart = timePart + ':00';  // 如果只有时分，添加秒
            } else if (timePart.length > 8) {
              timePart = timePart.substring(0, 8); // 取HH:mm:ss部分
            }
            
            return `${datePart} ${timePart}`;
          }
          // 如果无法分割，直接返回原始值
          return dateVal;
        }
        
        // 如果是普通的YYYY-MM-DD格式
        if (dateVal.includes('-')) {
          const parts = dateVal.split('-');
          if (parts.length === 3) {
            return `${parts[0]}/${parts[1]}/${parts[2]} 00:00:00`;  // 添加时分秒
          }
        }
        
        // 其他情况直接返回原始字符串
        return dateVal;
      }
      
      // 处理时间戳
      if (typeof dateVal === 'number') {
        const date = new Date(dateVal);
        return date.toLocaleDateString('zh-CN') + ' ' + 
               date.toLocaleTimeString('zh-CN', {hour: '2-digit', minute:'2-digit', second:'2-digit'});
      }
      
      // 默认情况，返回原始值的字符串
      return String(dateVal);
    } catch (e) {
      console.error('日期格式化错误:', e);
      return String(dateVal);
    }
  }
  
  // 格式化日期时间为datetime-local输入框所需的格式 (YYYY-MM-DDTHH:MM)
  const formatDateTime = (date) => {
    const d = new Date(date);
    // 格式化为YYYY-MM-DDTHH:MM:SS（完整时间格式）
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    const hours = String(d.getHours()).padStart(2, '0');
    const minutes = String(d.getMinutes()).padStart(2, '0');
    const seconds = String(d.getSeconds()).padStart(2, '0');
    
    // 返回完整格式，包含秒
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
  }
  
  // 新增支出相关数据
  const showAddExpenseModal = ref(false)
  const newExpense = ref({
    date: formatDateTime(new Date()),
    category: '',
    description: '',
    amount: ''
  })
  
  // 新增收入相关数据
  const showAddIncomeModal = ref(false)
  const newIncome = ref({
    date: formatDateTime(new Date()),
    category: '',
    description: '',
    amount: ''
  })
  
  // 添加新支出
  const addNewExpense = async () => {
    try {
      // 验证表单
      if (!newExpense.value.category || !newExpense.value.description || !newExpense.value.amount) {
        return
      }
      
      // 准备要提交到后端的数据
      const expenseData = {
        category: newExpense.value.category,
        description: newExpense.value.description,
        amount: Number(newExpense.value.amount),
        // 不设置expenseDate，让后端使用数据库的createTime
        remark: ''
      }
      
      console.log('提交支出数据:', expenseData);
      
      // 调用后端API保存到数据库
      const response = await axios.post('/api/admin/finance/expenses', expenseData)
      console.log('支出保存成功:', response.data)
      
      // 成功提交后，关闭模态框
      showAddExpenseModal.value = false;
      
      // 确保当前日期范围能捕捉到新添加的记录
      ensureDateRangeIncludesDate(new Date());
      
      // 立即重新加载数据，确保显示最新的数据库记录（包含精确的createTime）
      await refreshFinancialData();
      
      // 重置表单，使用当前时间（精确到秒）
      newExpense.value = {
        date: formatDateTime(new Date()),
        category: '',
        description: '',
        amount: ''
      }
    } catch (error) {
      console.error('添加支出失败:', error)
      alert('添加支出失败: ' + (error.response?.data?.message || error.message || '未知错误'))
    }
  }
  
  // 确保日期范围包含指定日期
  const ensureDateRangeIncludesDate = (date) => {
    // 将日期转换为YYYY-MM-DD格式
    const formattedDate = formatDefaultDate(date);
    
    // 如果当前是自定义日期范围，检查是否需要扩展范围
    if (dateRange.value === 'custom') {
      let startDateObj = startDate.value ? new Date(startDate.value) : null;
      let endDateObj = endDate.value ? new Date(endDate.value) : null;
      let needsUpdate = false;
      
      // 如果目标日期早于开始日期或没有开始日期，更新开始日期
      if (!startDateObj || date < startDateObj) {
        startDate.value = formattedDate;
        needsUpdate = true;
      }
      
      // 如果目标日期晚于结束日期或没有结束日期，更新结束日期
      if (!endDateObj || date > endDateObj) {
        endDate.value = formattedDate;
        needsUpdate = true;
      }
      
      if (needsUpdate) {
        // 保存更新后的日期范围
        try {
          localStorage.setItem('financialStartDate', startDate.value);
          localStorage.setItem('financialEndDate', endDate.value);
        } catch (e) {
          console.error('保存日期范围到localStorage失败:', e);
        }
      }
    } 
    // 如果当前不是自定义日期范围，并且新记录的日期不在当前范围内，改为"全部"范围
    else {
      const systemDate = new Date();
      
      // 判断日期是否在当前选定的范围内
      let isInCurrentRange = false;
      
      if (dateRange.value === 'today') {
        // 检查是否是当天
        isInCurrentRange = 
          date.getFullYear() === systemDate.getFullYear() &&
          date.getMonth() === systemDate.getMonth() &&
          date.getDate() === systemDate.getDate();
      } else if (dateRange.value === 'all') {
        // "全部"范围自然包含所有日期
        isInCurrentRange = true;
      } else {
        // 其他范围需要精确计算，为简单起见这里直接设为可能不在范围内
        isInCurrentRange = false;
      }
      
      // 如果不在当前范围内，切换到"全部"范围
      if (!isInCurrentRange) {
        dateRange.value = 'all';
        startDate.value = '';
        endDate.value = '';
        
        // 保存到localStorage
        try {
          localStorage.setItem('financialDateRange', 'all');
          localStorage.setItem('financialStartDate', '');
          localStorage.setItem('financialEndDate', '');
        } catch (e) {
          console.error('保存日期范围到localStorage失败:', e);
        }
      }
    }
  }
  
  // 添加新收入
  const addNewIncome = async () => {
    try {
      // 验证表单
      if (!newIncome.value.date || !newIncome.value.category || !newIncome.value.description || !newIncome.value.amount) {
        return
      }
      
      // 准备要提交到后端的数据
      const incomeData = {
        category: newIncome.value.category,
        description: newIncome.value.description,
        amount: Number(newIncome.value.amount),
        date: newIncome.value.date, // 直接使用完整时间格式，已包含秒
        status: 'PAID'
      }
      
      console.log('提交收入数据:', incomeData)
      
      // 调用后端API保存到收入表
      const response = await axios.post('/api/admin/finance/income/add', incomeData)
      console.log('收入保存成功:', response.data)
      
      if (response.data && response.data.success) {
        // 成功提交后，关闭模态框
        showAddIncomeModal.value = false;
        
        // 确保当前日期范围能捕捉到新添加的记录
        ensureDateRangeIncludesDate(new Date());
        
        // 重新加载数据
        refreshFinancialData();
        
        // 重置表单
        newIncome.value = {
          date: formatDateTime(new Date()),
          category: '',
          description: '',
          amount: ''
        }
      } else {
        alert('添加收入失败: ' + (response.data ? response.data.message : '未知错误'))
      }
    } catch (error) {
      console.error('添加收入失败:', error)
      alert('添加收入失败: ' + (error.response?.data?.message || error.message || '未知错误'))
    }
  }
  
  // 同步预订数据到财务系统
  const syncBookingData = async () => {
    try {
      isLoading.value = true
      
      // 调用后端API同步预订数据
      const response = await axios.post('/api/admin/finance/sync-bookings')
      console.log('同步预订数据结果:', response.data)
      
      if (response.data && response.data.success) {
        // 如果有同步的项目，直接添加到财务明细
        if (response.data.syncedItems && Array.isArray(response.data.syncedItems) && response.data.syncedItems.length > 0) {
          console.log('收到同步的记录:', response.data.syncedItems.length, '条')
          
          // 将同步的项目添加到财务数据中
          response.data.syncedItems.forEach(item => {
            // 确保数据类型正确
            const financialItem = {
              date: item.date,
              category: item.category || '标准房',
              description: item.description || '房间收入',
              income: Number(item.income || 0),
              expense: Number(item.expense || 0)
            }
            
            // 添加到财务数据
            financialData.value.push(financialItem)
            
            // 更新总计
            totalIncome.value += financialItem.income
            
            // 更新分类统计
            if (!incomeByCategory.value[financialItem.category]) {
              incomeByCategory.value[financialItem.category] = 0
            }
            incomeByCategory.value[financialItem.category] += financialItem.income
          })
          
          // 更新净利润
          totalProfit.value = totalIncome.value - totalExpense.value
          
          // 重新排序财务数据（降序）
          financialData.value.sort((a, b) => {
            // 确保使用完整日期+时间进行比较
            const dateA = a.date;
            const dateB = b.date;
            return dateB.localeCompare(dateA); // 降序排列，最新的在前面
          })
          // 更新图表
          setTimeout(() => {
            updatePieChart()
            updateIncomeChart()
          }, 100)
          
          alert(`同步成功: 已将${response.data.syncedItems.length}条预订收入添加到财务明细中`)
        } else {
          // 如果没有新的同步项，则刷新全部数据
          await fetchFinancialData()
          alert('同步完成，但没有新的预订收入需要添加')
        }
      } else {
        alert('同步失败: ' + (response.data ? response.data.message : '未知错误'))
      }
    } catch (error) {
      console.error('同步预订数据失败:', error)
      alert('同步预订数据失败: ' + (error.response?.data?.message || error.message || '未知错误'))
    } finally {
      isLoading.value = false
    }
  }
  
  // 刷新财务数据
  const refreshFinancialData = async () => {
    try {
      isLoading.value = true;
      
      // 保存当前的日期过滤值
      const currentDateRange = dateRange.value;
      const currentStartDate = startDate.value;
      const currentEndDate = endDate.value;
      
      // 临时设为全部，确保获取所有数据
      dateRange.value = 'all';
      startDate.value = '';
      endDate.value = '';
      
      // 请求最新数据
      await fetchFinancialData();
      
      // 恢复之前的过滤状态
      dateRange.value = currentDateRange;
      startDate.value = currentStartDate;
      endDate.value = currentEndDate;
      
      // 如果恢复后不是"全部"，则重新应用过滤
      if (currentDateRange !== 'all') {
        // 构建要过滤的日期范围
        const params = {};
        if (currentStartDate) {
          params.startDate = currentStartDate;
        }
        if (currentEndDate) {
          params.endDate = currentEndDate;
        }
        
        // 过滤现有数据而非重新获取
        filterLocalData(params);
      }
      
      console.log('财务数据刷新成功');
    } catch (error) {
      console.error('刷新财务数据失败:', error);
      error.value = '刷新财务数据失败: ' + error.message;
    } finally {
      isLoading.value = false;
    }
  }
  
  // 过滤本地数据
  const filterLocalData = (params) => {
    if (!params.startDate && !params.endDate) return; // 没有过滤条件
    
    // 解析日期参数
    const startDateObj = params.startDate ? new Date(params.startDate) : null;
    const endDateObj = params.endDate ? new Date(params.endDate) : null;
    
    // 创建过滤后的财务数据
    const filteredData = financialData.value.filter(item => {
      const itemDate = new Date(item.date);
      
      // 检查日期是否在范围内
      let isInRange = true;
      if (startDateObj) {
        isInRange = isInRange && itemDate >= startDateObj;
      }
      if (endDateObj) {
        isInRange = isInRange && itemDate <= endDateObj;
      }
      
      return isInRange;
    });
    
    // 重新计算汇总指标
    const filteredIncome = filteredData.reduce((sum, item) => sum + (item.income || 0), 0);
    const filteredExpense = filteredData.reduce((sum, item) => sum + (item.expense || 0), 0);
    
    // 更新财务数据和统计指标
    financialData.value = filteredData;
    totalIncome.value = filteredIncome;
    totalExpense.value = filteredExpense;
    totalProfit.value = filteredIncome - filteredExpense;
    
    // 重新统计分类数据
    recalculateCategoryData();
    
    // 重新排序财务数据
    sortFinancialData();
    
    // 更新图表
    updateCharts();
    
    console.log(`已根据日期过滤数据, 共${filteredData.length}条记录`);
  }
  
  // 重新计算分类统计数据
  const recalculateCategoryData = () => {
    // 清空现有分类数据
    incomeByCategory.value = {};
    expenseByCategory.value = {};
    
    // 重新统计
    financialData.value.forEach(item => {
      // 收入统计
      if (item.income > 0) {
        const category = item.category || '其他';
        if (!incomeByCategory.value[category]) {
          incomeByCategory.value[category] = 0;
        }
        incomeByCategory.value[category] += item.income;
      }
      
      // 支出统计
      if (item.expense > 0) {
        const category = item.category || '其他';
        if (!expenseByCategory.value[category]) {
          expenseByCategory.value[category] = 0;
        }
        expenseByCategory.value[category] += item.expense;
      }
    });
  }
  
  // 添加月度收入数据
  const monthlyIncomeData = ref([])
  
  // 更新收入趋势图
  const updateIncomeChart = () => {
    // 找到最大收入值，用于计算高度比例
    const maxIncome = Math.max(...monthlyIncomeData.value.map(item => item.income || 0));
    
    // 更新Y轴标签
    const yLabels = document.querySelectorAll('.chart-y-axis .y-axis-label');
    if (yLabels && yLabels.length >= 4) {
      if (maxIncome > 0) {
        const maxLabel = Math.ceil(maxIncome / 50000) * 50000;
        yLabels[0].textContent = '¥' + (maxLabel).toLocaleString();
        yLabels[1].textContent = '¥' + (maxLabel * 2/3).toLocaleString();
        yLabels[2].textContent = '¥' + (maxLabel * 1/3).toLocaleString();
        yLabels[3].textContent = '¥0';
      } else {
        // 如果没有数据，显示空Y轴
        yLabels[0].textContent = '¥0';
        yLabels[1].textContent = '';
        yLabels[2].textContent = '';
        yLabels[3].textContent = '';
      }
    }
    
    // 更新每个月份的柱状图高度
    const chartBars = document.querySelectorAll('.chart-bar-group .chart-bar');
    if (chartBars) {
      monthlyIncomeData.value.forEach((item, index) => {
        const percentage = maxIncome > 0 ? Math.round((item.income / maxIncome) * 100) : 0;
        if (index < chartBars.length) {
          chartBars[index].style.height = `${percentage}%`;
        }
      });
    }
  }
  
  // 计算最大收入值
  const maxIncome = computed(() => {
    return Math.max(...monthlyIncomeData.value.map(item => item.income || 0))
  })
  
  // 设置默认数据
  const setupDefaultData = () => {
    // 设置收入分类 - 使用固定的默认值，确保有单人间和双人间数据
    incomeByCategory.value = { 
      '单人间': 902, 
      '双人间': 1298,
      '餐饮服务': 350,
      '会议室租赁': 280,
      '水疗服务': 180
    }
    
    // 设置支出分类
    expenseByCategory.value = {
      '人力': 1056,
      '设施': 264,
      '水电': 440
    }
    
    // 设置收入来源数据
    sourcesData.value = [
      { name: '单人间', percentage: 45 },
      { name: '双人间', percentage: 55 }
    ]
    
    // 设置月度收入趋势数据
    setupDefaultMonthlyData();
    
    // 生成财务明细表数据
    generateTransactions();
  }
  
  // 应用特定日期过滤
  const applySpecificDateFilter = () => {
    if (!specificDate.value) {
      alert('请选择日期');
      return;
    }
    
    console.log(`应用指定日期过滤: ${specificDate.value}`);
    
    // 保存到localStorage
    try {
      localStorage.setItem('financialDateRange', 'specific');
      localStorage.setItem('financialSpecificDate', specificDate.value);
    } catch (e) {
      console.error('保存日期范围到localStorage失败:', e);
    }
    
    // 设置loading状态并清除之前的错误
    isLoading.value = true;
    error.value = '';
    
    // 应用过滤，始终获取最新的服务器数据
    fetchFinancialData();
  }
  
  // 删除财务记录
  const deleteFinancialRecord = async (id) => {
    console.log('尝试删除记录ID:', id);
    
    if (!id) {
      alert('无法删除此记录，ID不存在');
      return;
    }
    
    if (!confirm('确定要删除这条支出记录吗？')) {
      return;
    }
    
    try {
      isLoading.value = true;
      // 调用API删除记录
      const response = await axios.delete(`/api/admin/finance/expenses/${id}`);
      console.log('删除支出记录成功:', response.data);
      
      // 从财务数据中移除该记录
      const index = financialData.value.findIndex(item => item.id === id && item.expense > 0);
      if (index !== -1) {
        // 更新总支出
        const expense = financialData.value[index].expense || 0;
        totalExpense.value -= expense;
        totalProfit.value = totalIncome.value - totalExpense.value;
        
        // 更新支出分类统计
        const category = financialData.value[index].category;
        if (category && expenseByCategory.value[category]) {
          expenseByCategory.value[category] -= expense;
          if (expenseByCategory.value[category] <= 0) {
            delete expenseByCategory.value[category];
          }
        }
        
        // 从数组中移除该记录
        financialData.value.splice(index, 1);
        
        // 更新图表
        updateCharts();
      }
    } catch (error) {
      console.error('删除支出记录失败:', error);
      alert('删除支出记录失败: ' + (error.response?.data?.message || error.message || '未知错误'));
    } finally {
      isLoading.value = false;
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
  
  /* 日期筛选 */
  .date-filter {
    background-color: white;
    border-radius: 0.5rem;
    padding: 1rem;
    margin-bottom: 1.5rem;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .date-range-selector {
    display: flex;
    flex-wrap: wrap;
    gap: 0.5rem;
    margin-bottom: 1rem;
  }
  
  .date-btn {
    padding: 0.5rem 1rem;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    background-color: #f1f5f9;
    color: #64748b;
    border: 1px solid #e2e8f0;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .date-btn:hover {
    background-color: #e2e8f0;
  }
  
  .date-btn.active {
    background-color: #e11d48;
    color: white;
    border-color: #e11d48;
  }
  
  .refresh-btn {
    padding: 0.5rem;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    background-color: #f1f5f9;
    color: #64748b;
    border: 1px solid #e2e8f0;
    cursor: pointer;
    transition: all 0.2s ease;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .refresh-btn:hover {
    background-color: #e2e8f0;
    color: #1e293b;
  }
  
  .refresh-icon {
    width: 1.25rem;
    height: 1.25rem;
  }
  
  .specific-date {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    align-items: flex-end;
  }
  
  .custom-date-range {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    align-items: flex-end;
  }
  
  .date-input-group {
    display: flex;
    flex-direction: column;
  }
  
  .date-label {
    font-size: 0.75rem;
    color: #64748b;
    margin-bottom: 0.25rem;
  }
  
  .date-input {
    padding: 0.5rem;
    border: 1px solid #cbd5e1;
    border-radius: 0.375rem;
    font-size: 0.875rem;
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
  
  .revenue-icon {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .expense-icon {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .profit-icon {
    background-color: #ede9fe;
    color: #6d28d9;
  }
  
  .occupancy-icon {
    background-color: #dbeafe;
    color: #2563eb;
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
    margin: 0 0 0.25rem 0;
  }
  
  .stat-trend {
    display: flex;
    align-items: center;
    font-size: 0.75rem;
    margin: 0;
  }
  
  .trend-icon {
    width: 0.875rem;
    height: 0.875rem;
    margin-right: 0.25rem;
  }
  
  .positive {
    color: #16a34a;
  }
  
  .negative {
    color: #dc2626;
  }
  
  /* 图表区域 */
  .charts-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1.5rem;
    margin-bottom: 1.5rem;
  }
  
  .chart-card {
    background-color: white;
    border-radius: 0.5rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .chart-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .chart-title {
    font-size: 1rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
  }
  
  .chart-actions {
    display: flex;
    gap: 0.5rem;
  }
  
  .chart-select {
    padding: 0.25rem 0.5rem;
    border: 1px solid #cbd5e1;
    border-radius: 0.375rem;
    font-size: 0.75rem;
    color: #64748b;
  }
  
  .chart-action-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 1.5rem;
    height: 1.5rem;
    border-radius: 0.375rem;
    background-color: #f1f5f9;
    border: 1px solid #e2e8f0;
    color: #64748b;
    cursor: pointer;
  }
  
  .chart-action-icon {
    width: 1rem;
    height: 1rem;
  }
  
  .chart-body {
    padding: 1rem;
  }
  
  /* 折线图 */
  .line-chart {
    display: flex;
    height: 300px;
  }
  
  .chart-y-axis {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding-right: 0.5rem;
    width: 50px;
  }
  
  .y-axis-label {
    font-size: 0.75rem;
    color: #94a3b8;
    text-align: right;
  }
  
  .chart-content {
    flex: 1;
    position: relative;
  }
  
  .chart-grid {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 20px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  
  .grid-line {
    width: 100%;
    height: 1px;
    background-color: #e2e8f0;
  }
  
  .line-chart-bars {
    position: relative;
    height: 100%;
    display: flex;
    align-items: flex-end;
    padding-bottom: 20px;
  }
  
  .chart-bar-group {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;
  }
  
  .chart-bar {
    width: 60%;
    background-color: #e11d48;
    border-radius: 4px 4px 0 0;
  }
  
  .x-axis-label {
    font-size: 0.75rem;
    color: #94a3b8;
    margin-top: 0.5rem;
  }
  
  /* 饼图 */
  .pie-chart-container {
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-wrap: wrap;
    height: 300px;
  }
  
  .pie-chart {
    position: relative;
    width: 150px;
    height: 150px;
    border-radius: 50%;
    overflow: hidden;
    /* 添加背景和内边距，使饼图更加突出 */
    background: #fff;
    padding: 8px;
    margin: 0 auto;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  }
  
  .pie-conic {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    /* 添加边框和阴影，使饼图更加突出 */
    border: 2px solid white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    /* 添加过渡效果 */
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
  
  /* 报表部分 */
  .report-section {
    background-color: white;
    border-radius: 0.5rem;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    margin-bottom: 1.5rem;
  }
  
  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .section-title {
    font-size: 1rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
  }
  
  .section-actions {
    display: flex;
    gap: 0.5rem;
  }
  
  /* 财务表格 */
  .table-container {
    overflow-x: auto;
  }
  
  .financial-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .financial-table th {
    background-color: #f8fafc;
    padding: 0.75rem 1rem;
    text-align: left;
    font-size: 0.875rem;
    font-weight: 600;
    color: #64748b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .financial-table th:nth-child(4),
  .financial-table th:nth-child(5),
  .financial-table th:nth-child(6) {
    text-align: right;
  }
  
  .financial-table td {
    padding: 0.75rem 1rem;
    font-size: 0.875rem;
    color: #1e293b;
    border-bottom: 1px solid #e2e8f0;
  }
  
  .financial-table tfoot td {
    font-weight: 600;
    background-color: #f8fafc;
  }
  
  .category-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }
  
  /* 类别的颜色框框 */
  .category-badge.单人间,
  .category-badge.双人间,
  .category-badge.标准间,
  .category-badge.套房 {
    background-color: #dbeafe;
    color: #2563eb;
  }
  
  .category-badge.餐饮 {
    background-color: #fef3c7;
    color: #d97706;
  }
  
  .category-badge.人力 {
    background-color: #fee2e2;
    color: #dc2626;
  }
  
  .category-badge.设施 {
    background-color: #dcfce7;
    color: #16a34a;
  }
  
  .category-badge.水电 {
    background-color: #e0e7ff;
    color: #4f46e5;
  }
  
  .category-badge.其他 {
    background-color: #f1f5f9;
    color: #64748b;
  }
  
  /* 订单号标签样式 */
  .booking-badge {
    display: inline-block;
    padding: 0.25rem 0.5rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
    background-color: #e9d5ff;
    color: #7e22ce;
  }
  
  /* 操作按钮样式 */
  .action-buttons {
    display: flex;
    gap: 0.5rem;
    justify-content: center;
  }
  
  .icon-button {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 2rem;
    height: 2rem;
    border-radius: 0.375rem;
    background-color: transparent;
    border: 1px solid #e2e8f0;
    color: #64748b;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .icon-button:hover {
    background-color: #f8fafc;
  }
  
  .icon-button.view:hover {
    color: #2563eb;
    border-color: #2563eb;
  }
  
  .action-icon {
    width: 1.125rem;
    height: 1.125rem;
  }
  
  .amount {
    text-align: right;
    font-family: monospace;
    white-space: nowrap;
  }
  
  .income {
    color: #16a34a;
  }
  
  .expense {
    color: #dc2626;
  }
  
  .total-label {
    text-align: right;
  }
  
  /* 支出分析 */
  .expense-analysis {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 1.5rem;
    padding: 1rem;
  }
  
  .expense-chart {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .horizontal-bar-chart {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .chart-item {
    display: flex;
    align-items: center;
    gap: 1rem;
  }
  
  .chart-label {
    width: 100px;
    font-size: 0.875rem;
    color: #1e293b;
    text-align: right;
  }
  
  .chart-bar-container {
    flex: 1;
    height: 1.5rem;
    background-color: #f1f5f9;
    border-radius: 0.25rem;
    overflow: hidden;
  }
  
  .chart-bar {
    height: 100%;
    background-color: #e11d48;
    color: white;
    font-size: 0.75rem;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    padding: 0 0.5rem;
  }
  
  .chart-value {
    width: 80px;
    font-size: 0.875rem;
    color: #1e293b;
    text-align: right;
    font-family: monospace;
  }
  
  .expense-summary {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .summary-card {
    background-color: #f8fafc;
    border-radius: 0.5rem;
    padding: 1rem;
    border: 1px solid #e2e8f0;
  }
  
  .summary-title {
    font-size: 0.875rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0 0 0.5rem 0;
  }
  
  .summary-desc {
    font-size: 0.875rem;
    color: #64748b;
    margin: 0;
  }
  
  .summary-list {
    margin: 0;
    padding-left: 1.5rem;
    font-size: 0.875rem;
    color: #64748b;
  }
  
  .summary-list li {
    margin-bottom: 0.5rem;
  }
  
  .summary-list li:last-child {
    margin-bottom: 0;
  }
  
  .text-negative {
    color: #dc2626;
    font-weight: 500;
  }
  
  /* 响应式设计 */
  @media (max-width: 1024px) {
    .stats-row {
      grid-template-columns: repeat(2, 1fr);
    }
    
    .charts-container {
      grid-template-columns: 1fr;
    }
    
    .expense-analysis {
      grid-template-columns: 1fr;
    }
  }
  
  @media (max-width: 640px) {
    .stats-row {
      grid-template-columns: 1fr;
    }
    
    .date-range-selector {
      flex-direction: column;
    }
    
    .custom-date-range {
      flex-direction: column;
    }
    
    .chart-item {
      flex-direction: column;
      align-items: flex-start;
      gap: 0.5rem;
    }
    
    .chart-label {
      width: 100%;
      text-align: left;
    }
    
    .chart-value {
      width: 100%;
      text-align: left;
    }
  }
  
  /* 加载和错误状态样式 */
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
  
  /* 模态框样式 */
  .modal-overlay {
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
  
  .modal-container {
    background-color: white;
    border-radius: 0.5rem;
    width: 90%;
    max-width: 500px;
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
    font-size: 1.25rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
  }
  
  .modal-close {
    background: none;
    border: none;
    font-size: 1.5rem;
    color: #64748b;
    cursor: pointer;
  }
  
  .modal-body {
    padding: 1rem;
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  .form-group label {
    display: block;
    font-size: 0.875rem;
    font-weight: 500;
    color: #1e293b;
    margin-bottom: 0.5rem;
  }
  
  .form-input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #cbd5e1;
    border-radius: 0.375rem;
    font-size: 0.875rem;
  }
  
  .form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 0.75rem;
    margin-top: 1.5rem;
  }
  
  /* 添加套房专用颜色 */
  .category-badge.套房 {
    background-color: #d1fae5;
    color: #059669;
  }
  
  .legend-color.套房 {
    background-color: #59bb6e;
  }
  
  /* 添加标准间专用颜色 */
  .category-badge.标准间 {
    background-color: #fee2e2;
    color: #b91c1c;
  }
  
  .legend-text {
    font-size: 0.75rem;
    color: #64748b;
  }
  
  /* 在style部分添加新的样式 */
  .total-net {
    font-weight: 700;
    font-size: 1.1em;
  }
  
  .total-net.positive {
    color: #16a34a;
    background-color: #dcfce7;
  }
  
  .total-net.negative {
    color: #dc2626;
    background-color: #fee2e2;
  }

  /* 在style部分添加或修改样式 */
  .total-income {
    color: #16a34a !important;
    background-color: #dcfce7 !important;
    font-weight: 600;
  }

  .total-expense {
    color: #dc2626 !important;
    background-color: #fee2e2 !important;
    font-weight: 600;
  }

  .total-net {
    font-weight: 700;
    font-size: 1.1em;
  }

  .total-net.positive {
    color: #1e40af !important;
    background-color: #dbeafe !important;
  }

  .total-net.negative {
    color: #dc2626 !important;
    background-color: #fee2e2 !important;
  }
  </style>