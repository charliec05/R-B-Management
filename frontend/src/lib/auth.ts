import { api } from './api'

export async function login(email: string, password: string) {
  const res = await api.post('/auth/login', { email, password })
  const token = res.data.token as string
  localStorage.setItem('token', token)
}


