import { useForm } from 'react-hook-form'
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { api } from '../lib/api'

type Item = { id: number; sku: string; name: string }
type Warehouse = { id: number; code: string; name: string }
type Form = { sku: string; warehouseCode: string; lotNo?: string; qty: number; unitCost: number }

export default function ReceiveStock() {
  const qc = useQueryClient()
  const items = useQuery<{ content: Item[] }>({ queryKey: ['items'], queryFn: async () => (await api.get('/items')).data })
  const warehouses = useQuery<Warehouse[]>({ queryKey: ['warehouses'], queryFn: async () => (await api.get('/warehouses')).data })
  const { register, handleSubmit, formState: { isSubmitting }, reset } = useForm<Form>()
  const mutation = useMutation({
    mutationFn: async (v: Form) => (await api.post('/inventory/receive', v)).data,
    onSuccess: () => { qc.invalidateQueries({ queryKey: ['inventory'] }); reset() }
  })

  return (
    <form onSubmit={handleSubmit(async (v) => mutation.mutate(v))} style={{ display: 'grid', gap: 8, maxWidth: 480 }}>
      <h2>Receive Stock</h2>
      <label>Item</label>
      <select {...register('sku', { required: true })}>
        <option value=''>Select item</option>
        {items.data?.content?.map(i => <option key={i.id} value={i.sku}>{i.sku} - {i.name}</option>)}
      </select>
      <label>Warehouse</label>
      <select {...register('warehouseCode', { required: true })}>
        <option value=''>Select warehouse</option>
        {warehouses.data?.map(w => <option key={w.id} value={w.code}>{w.code} - {w.name}</option>)}
      </select>
      <input placeholder='Lot No' {...register('lotNo')} />
      <input placeholder='Quantity' type='number' step='0.001' {...register('qty', { required: true, valueAsNumber: true })} />
      <input placeholder='Unit Cost' type='number' step='0.0001' {...register('unitCost', { required: true, valueAsNumber: true })} />
      <button type='submit' disabled={isSubmitting || mutation.isPending}>Receive</button>
      {mutation.isError && <span>Failed to receive</span>}
    </form>
  )
}


