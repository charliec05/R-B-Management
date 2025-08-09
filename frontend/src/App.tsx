import { Link, Outlet } from 'react-router-dom'

export default function App() {
  return (
    <div style={{ fontFamily: 'system-ui, sans-serif', padding: 16 }}>
      <nav style={{ display: 'flex', gap: 12, marginBottom: 16 }}>
        <Link to="/">Dashboard</Link>
        <Link to="/inventory">Inventory</Link>
        <Link to="/warehouses">Warehouses</Link>
        <Link to="/receive">Receive</Link>
        <Link to="/login">Login</Link>
      </nav>
      <Outlet />
    </div>
  )
}


