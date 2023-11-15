import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NotFound from './layouts/NotFound.js';
import SignIn from './layouts/SignIn.js';
import PublicRoute from './components/route/PublicRoute.js';
import PrivateRoute from './components/route/PrivateRoute.js';
import User from './layouts/User.js';
import Admin from './layouts/Admin.js';
import Developer from './layouts/Developer.js';
import ApiProducts from './components/user/ApiProducts.js';
import MySubscription from './components/user/MySubscription.js';
import Product from './components/user/Product.js';
import ApiProductsDev from './components/developer/ApiProducts.js';
import ProductDev from './components/developer/Product.js';
import Chart from "./components/admin/MainChart.js";
import Aggregate from "./components/admin/APIAggregate.js"
import APIApproval from "./components/admin/APIApproval.js"
import APIManagement from "./components/admin/APIManagement.js"
import APIRegistration from "./components/admin/APIRegistration.js"
import APISubscription from "./components/admin/APISubscription.js"
import MemberManagement from "./components/admin/MemberManagement.js"
import API from "./components/admin/API.js"

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* Public */}
          <Route exact path='/' element={<PublicRoute component={<SignIn />} />} />
          {/* Private */}
          <Route exact path='/user' element={<PrivateRoute selectRole="USER" component={<User />} />}>
            <Route exact index element={<ApiProducts />} />
            <Route exact path='/user/API Products/:productName' element={<Product />} />
            <Route exact path='/user/My Subscription' element={<MySubscription />} />
          </Route>
          <Route exact path='/admin' element={<PrivateRoute selectRole="ADMIN" component={<Admin />} />} >
            <Route exact index element={<Chart/>}></Route>
            <Route exact  path='/admin/aggregate' element={<Aggregate/>}></Route>
            <Route exact path='/admin/apiApproval' element={<APIApproval/>}></Route>
            <Route exact path='/admin/apiRegistration' element={<APIRegistration/>}></Route>
            <Route exact path='/admin/apiManagement' element={<APIManagement/>}></Route>
            <Route exact path='/admin/apiSubscription' element={<APISubscription/>}></Route>
            <Route exact path='/admin/memberManagement' element={<MemberManagement/>}></Route>
            <Route path='/admin/api/:apiId' element={<API />} />
          </Route>
          <Route exact path='/developer' element={<PrivateRoute selectRole="DEVELOPER" component={<Developer />} />} >
            <Route exact index element={<ApiProductsDev />} />
            <Route exact path='/developer/API Products/:apiId' element={<ProductDev />} />
          </Route>
					{/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는경우 처리 */}
					<Route path='/*' element={<NotFound />} />
				</Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
