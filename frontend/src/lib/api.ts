import axios from 'axios'

const baseURL = (import.meta as any).env?.VITE_API_URL || (window as any).__VITE_API_URL__ || 'http://localhost:8080/api'

export const api = axios.create({ baseURL })

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers = config.headers || {}
    config.headers['Authorization'] = `Bearer ${token}`
  }
  return config
})


