export type Role = 'ADMIN' | 'WAREHOUSE_OPS' | 'READONLY'

function parseJwt<T = any>(token: string): T | null {
  try {
    const base64Url = token.split('.')[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)).join(''))
    return JSON.parse(jsonPayload)
  } catch {
    return null
  }
}

export function getUserRoles(): Role[] {
  const token = localStorage.getItem('token')
  if (!token) return []
  const payload = parseJwt<any>(token)
  // roles claim may be array of authorities objects: [{authority: 'ROLE_ADMIN'}, ...]
  const authorities: string[] = Array.isArray(payload?.roles)
    ? payload.roles.map((r: any) => (typeof r === 'string' ? r : r?.authority)).filter(Boolean)
    : []
  return authorities.map(a => String(a).replace(/^ROLE_/, '')) as Role[]
}

export function hasAnyRole(...roles: Role[]): boolean {
  const userRoles = getUserRoles()
  return roles.some(r => userRoles.includes(r))
}


