<template>
  <div>
    <h2>员工列表</h2>
    <table border="1" cellpadding="6">
      <thead>
        <tr>
          <th>ID</th>
          <th>姓名</th>
          <th>性别</th>
          <th>电话</th>
          <th>职位</th>
          <th>入职日期</th>
          <th>状态</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="emp in employees" :key="emp.employeeId">
          <td>{{ emp.employeeId }}</td>
          <td>{{ emp.name }}</td>
          <td>{{ emp.gender }}</td>
          <td>{{ emp.phone }}</td>
          <td>{{ emp.position }}</td>
          <td>{{ emp.hireDate }}</td>
          <td>{{ emp.status }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getEmployees } from '../api';

const employees = ref([]);

onMounted(async () => {
  const res = await getEmployees();
  employees.value = res.data;
});
</script> 