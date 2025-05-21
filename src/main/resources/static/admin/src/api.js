import axios from 'axios';

const api = axios.create({
  baseURL: '', // 如有代理可用 /api，否则用 ''
  timeout: 5000
});

// 员工API
export const getEmployees = () => api.get('/employee/all');
export const getEmployeeById = (id) => api.get(`/employee/${id}`);

// 房间API
export const getRooms = () => api.get('/room/all');
export const getRoomById = (id) => api.get(`/room/${id}`);

export default api; 