import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logoutUser, setAccessToken } from '../slices/UserInfoSlice';
import store from '../store/store';

export const SeverBaseUrl = "http://localhost:8080/";

const CustomAxios = () => {
    return axios.create({
        baseURL: `${SeverBaseUrl}`,
        headers: {
            "Content-Type": "application/json",
        },
        body: { 
            "access_token": "",
        },
    });
};
const instance = CustomAxios();

instance.interceptors.request.use(
    (config) => {
        const access_token = store.getState().userInfo.access_token;

        if (access_token !== "") {
            config.body.access_token = `Bearer ${access_token}`;
        }
        return config;
    },
    (error) => Promise.reject(error),
);

instance.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // ERROR 401 => Refresh Token 보내기
        if (error.response.status === 401) {
        try {
            // access_token 재발급 요청
            await axios
            .post(
                `${SeverBaseUrl}/refresh`,
                { 
                    refresh_token: store.getState().userInfo.refresh_token
                },
                {
                    headers: { 
                        Authorization: store.getState().userInfo.access_token
                    },
                },
            )
            .then((res) => {
                useDispatch(setAccessToken(res.data.token)); // 여기 문제 될 수도 있음
            });

            //access token 을 다시 setting 하고 origin request 를 재요청
            originalRequest.headers.Authorization = `Bearer ${store.getState().userInfo.access_token})}`;

            return axios(originalRequest);
        } catch (error) {
            // refresh_token 만료되는 경우는 에러나고 재로그인 해야 함.
            useDispatch(logoutUser());
            useNavigate("/");
        }
        } else if(error.response.status === 400) { // ERROR 400 => 존재하지 않는 계정
            alert(error.response.data.errorMessage);
        }

        return Promise.reject(error);
    },
);

export default instance;