import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm } from 'react-hook-form'
import { api } from '../lib/api'
import { hasAnyRole } from '../lib/roles'

type Warehouse = { id: number; code: string; name: string; location?: string }
type Form = { code: string; name: string; location?: string }

export default function WarehouseList() {
  const qc = useQueryClient()
  const { data, isLoading, error } = useQuery<Warehouse[]>({
    queryKey: ['warehouses'],
    queryFn: async () => (await api.get('/warehouses')).data
  })

  const { register, handleSubmit, reset } = useForm<Form>()
  const createMutation = useMutation({
    mutationFn: async (values: Form) => (await api.post('/warehouses', values)).data,
    onSuccess: () => { qc.invalidateQueries({ queryKey: ['warehouses'] }); reset() }
  })

  if (isLoading) return <div>Loading...</div>
  if (error) return <div>Error loading warehouses</div>

  return (
    <div>
      <h2>Warehouses</h2>
      {hasAnyRole('ADMIN') && (
        <form onSubmit={handleSubmit(async (v) => createMutation.mutate(v))} style={{ display: 'grid', gap: 8, maxWidth: 520, marginBottom: 16 }}>
          <h3>Add Warehouse (ADMIN)</h3>
          <input placeholder='Code' {...register('code', { required: true })} />
          <input placeholder='Name' {...register('name', { required: true })} />
          <input placeholder='Location' {...register('location')} />
          <button type='submit' disabled={createMutation.isPending}>Create</button>
        </form>
      )}
      <table border={1} cellPadding={6} cellSpacing={0}>
        <thead>
          <tr><th>ID</th><th>Code</th><th>Name</th><th>Location</th></tr>
        </thead>
        <tbody>
          {data?.map(w => (
            <tr key={w.id}><td>{w.id}</td><td>{w.code}</td><td>{w.name}</td><td>{w.location}</td></tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}


