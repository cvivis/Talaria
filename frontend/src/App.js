import SwaggerUI from 'swagger-ui-react';
import "swagger-ui-react/swagger-ui.css";
import SwaggerTest from '../src/SwaggerTest.js';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Main from './Main';

function App() {

  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
					<Route path="/" element={<Main />} />
          <Route path="/editor" element={<SwaggerTest />} />
          <Route path="/ui" element={<SwaggerUI url='https://petstore3.swagger.io/api/v3/openapi.json' />} />
					{/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는경우 처리 */}
					{/* <Route path="*" element={<NotFound />}></Route> */}
				</Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
