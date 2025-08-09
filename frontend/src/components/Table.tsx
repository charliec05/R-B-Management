import { ReactNode } from 'react'

export function Table({ children }: { children: ReactNode }) {
  return (
    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
      {children}
    </table>
  )
}


