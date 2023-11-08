import { axios } from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logoutUser, setAccessToken } from '../slices/UserInfoSlice';

export const SeverBaseUrl = "http://localhost:8080/";

export const CustomAxios = () => {
    axios.create({
        baseURL: `${SeverBaseUrl}`,
        headers: {
            "Content-Type": "application/json",
        },
    });
};

CustomAxios.interceptors.request.use(
    (config) => {
        const access_token = useSelector((state) => {
            return state.userInfo.access_token;
        });

        if (access_token !== "") {
            config.body.access_token = `Bearer ${access_token}`;
        }
        return config;
    },
    (error) => Promise.reject(error),
);

CustomAxios.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;
        const dispatch = useDispatch();
        const userInfo = useSelector((state) => {
            return state.userInfo.value;
        });
        const navigate = useNavigate();

        // ERROR 401 => Refresh Token 보내기
        if (error.response.status === 401) {
        try {
            // access_token 재발급 요청
            await axios
            .post(
                `${SeverBaseUrl}/refresh`,
                { 
                    refresh_token: userInfo.refresh_token,
                },
                {
                    headers: { 
                        Authorization: userInfo.access_token,
                    },
                },
            )
            .then((res) => {
                dispatch(setAccessToken(res.data.token));
            });

            //access token 을 다시 setting 하고 origin request 를 재요청
            originalRequest.headers.Authorization = `Bearer ${useSelector((state) => {return state.userInfo.value;})}`;

            return axios(originalRequest);
        } catch (error) {
            // refresh_token 만료되는 경우는 에러나고 재로그인 해야 함.
            dispatch(logoutUser());
            navigate("/");
        }
        }

        return Promise.reject(error);
    },
);