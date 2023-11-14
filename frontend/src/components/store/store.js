import { combineReducers, configureStore } from "@reduxjs/toolkit";
// import storage from "redux-persist/lib/storage"; // local storage
import storage from "redux-persist/lib/storage/session"; // session storage
import UserInfoSlice from "../slices/UserInfoSlice";
import { persistReducer } from "redux-persist";

const reducers = combineReducers({
    userInfo: UserInfoSlice,
});

const persisConfig = {
    key: "root",
    storage,
    whitelist: ["userInfo"],
}

const persistedReducer = persistReducer(persisConfig,reducers);

const store = configureStore({
    reducer: persistedReducer,
    middleware: getDefaultMiddleware => getDefaultMiddleware({ serializableCheck: false }),
});

export default store;