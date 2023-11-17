import { createSlice } from "@reduxjs/toolkit";

const UserInfoSlice = createSlice({
    name: "userInfo",
    initialState: { 
        "member_id": "",
        "email": "",
        "role": "",
        "key_id": "",
        "access_token": "",
        "refresh_token": ""
    },
    reducers: {
        setUser: (state, action) => {
            state.member_id = action.payload.member_id;
            state.email = action.payload.email;
            state.role = action.payload.role;
            state.key_id = action.payload.key_id;
            state.access_token = action.payload.access_token;
            state.refresh_token = action.payload.refresh_token;
        },
        setAccessToken: (state, action) => {
            state.access_token = action.payload;
        },
        setRefreshToken: (state, action) => {
            state.refresh_token = action.payload;
        },
        logoutUser: (state) => {
            state.member_id = "";
            state.email = "";
            state.role = "";
            state.key_id = "";
            state.access_token = "";
            state.refresh_token = "";
        },
    },
});

export const { setUser, setAccessToken, setRefreshToken, logoutUser } = UserInfoSlice.actions;
export default UserInfoSlice.reducer;