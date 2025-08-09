import { useForm } from 'react-hook-form'
import { login } from '../lib/auth'

type Form = { email: string; password: string }

export default function Login() {
  const { register, handleSubmit, formState: { isSubmitting, errors } } = useForm<Form>()

  return (
    <form onSubmit={handleSubmit(async (values) => {
      await login(values.email, values.password)
      alert('Logged in!')
    })} style={{ maxWidth: 360, display: 'grid', gap: 12 }}>
      <h2>Login</h2>
      <input placeholder='Email' type='email' {...register('email', { required: true })} />
      {errors.email && <span>Email is required</span>}
      <input placeholder='Password' type='password' {...register('password', { required: true })} />
      {errors.password && <span>Password is required</span>}
      <button disabled={isSubmitting} type='submit'>Login</button>
    </form>
  )
}


