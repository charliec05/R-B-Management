import { createBrowserRouter } from 'react-router-dom'
import App from '../App'
import Login from './login'
import Dashboard from './dashboard'
import InventoryList from './inventory-list'
import WarehouseList from './warehouses'
import ReceiveStock from './receive-stock'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Dashboard /> },
      { path: 'inventory', element: <InventoryList /> },
      { path: 'warehouses', element: <WarehouseList /> },
      { path: 'receive', element: <ReceiveStock /> },
      { path: 'login', element: <Login /> }
    ]
  }
])


