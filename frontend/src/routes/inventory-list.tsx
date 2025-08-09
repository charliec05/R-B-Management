import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm } from 'react-hook-form'
import { api } from '../lib/api'
import { hasAnyRole } from '../lib/roles'

type Item = { id: number; sku: string; name: string; unit: string; costMethod: string }
type Page<T> = { content: T[] }
type ItemForm = { sku: string; name: string; unit: string; costMethod: 'FIFO' | 'WMA' }

export default function InventoryList() {
  const qc = useQueryClient()
  const { data, isLoading, error } = useQuery<Page<Item>>({
    queryKey: ['items'],
    queryFn: async () => (await api.get('/items')).data
  })

  const { register, handleSubmit, reset } = useForm<ItemForm>({
    defaultValues: { costMethod: 'FIFO' }
  })
  const createMutation = useMutation({
    mutationFn: async (values: ItemForm) => (await api.post('/items', values)).data,
    onSuccess: () => { qc.invalidateQueries({ queryKey: ['items'] }); reset() }
  })

  if (isLoading) return <div>Loading...</div>
  if (error) return <div>Error loading items</div>

  return (
    <div>
      <h2>Inventory</h2>
      {hasAnyRole('ADMIN') && (
        <form onSubmit={handleSubmit(async (v) => createMutation.mutate(v))} style={{ display: 'grid', gap: 8, maxWidth: 520, marginBottom: 16 }}>
          <h3>Add Item (ADMIN)</h3>
          <input placeholder='SKU' {...register('sku', { required: true })} />
          <input placeholder='Name' {...register('name', { required: true })} />
          <input placeholder='Unit' {...register('unit', { required: true })} />
          <select {...register('costMethod', { required: true })}>
            <option value='FIFO'>FIFO</option>
            <option value='WMA'>WMA</option>
          </select>
          <button type='submit' disabled={createMutation.isPending}>Create</button>
          {createMutation.isError && <span>Creation failed</span>}
        </form>
      )}
      <table border={1} cellPadding={6} cellSpacing={0}>
        <thead>
          <tr>
            <th>ID</th>
            <th>SKU</th>
            <th>Name</th>
            <th>Unit</th>
            <th>Cost Method</th>
          </tr>
        </thead>
        <tbody>
          {data?.content?.map(i => (
            <tr key={i.id}>
              <td>{i.id}</td>
              <td>{i.sku}</td>
              <td>{i.name}</td>
              <td>{i.unit}</td>
              <td>{i.costMethod}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}


