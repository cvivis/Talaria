import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ChakraProvider } from '@chakra-ui/react';
import theme from "./theme/theme.js";
import { Provider } from 'react-redux';
import store from './components/store/store';
import { PersistGate } from 'redux-persist/integration/react';
import { persistStore } from 'redux-persist';

export const persistor = persistStore(store);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <ChakraProvider 
        theme={theme}
        position="relative"
        // resetCSS={false}
      >
        <App />
      </ChakraProvider>
    </PersistGate>
  </Provider>
);

reportWebVitals();
