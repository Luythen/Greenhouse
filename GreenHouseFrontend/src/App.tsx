import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { Home } from './components/Home'
import { Login } from './components/Login'
import { Register } from './components/Register'
import { LoginRequired } from './components/LoginRequired'
import { GreenHouse } from './components/GreenHouse'
import { Navbar } from './components/Navbar'

function App() {

  return (
    <BrowserRouter>
      <Navbar>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route element={<LoginRequired />}>
              <Route path='/greenhouse' element={<GreenHouse />} />
            </Route>
        </Routes>
      </Navbar>
    </BrowserRouter>
  )
}

export default App
