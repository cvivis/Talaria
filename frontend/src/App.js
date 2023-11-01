import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NotFound from './layouts/NotFound.js';
import SignIn from './layouts/SignIn.js';
import PublicRoute from './components/route/PublicRoute.js';
import PrivateRoute from './components/route/PrivateRoute.js';
import User from './layouts/User.js';
import Admin from './layouts/Admin.js';
import Developer from './layouts/Developer.js';

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Public */}
          <Route exact path='/' element={<PublicRoute component={<SignIn />} />} />
          {/* Private */}
          <Route exact path='/user' element={<PrivateRoute selectRole="user" component={<User />} />} />
          <Route exact path='/admin' element={<PrivateRoute selectRole="admin" component={<Admin />} />} />
          <Route exact path='/developer' element={<PrivateRoute selectRole="developer" component={<Developer />} />} />
					{/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는경우 처리 */}
					<Route path='/*' element={<NotFound />} />
				</Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
