import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Home } from './components/Home'
import { Login } from './components/Login'
import { Register } from './components/Register'
import { LoginRequired } from './components/LoginRequired'
import { GreenHouse } from './components/GreenHouse'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login />} />
        <Route path='/register' element={<Register />} />
        <Route element={<LoginRequired />}>
          <Route path='/greenhouse' element={<GreenHouse />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
