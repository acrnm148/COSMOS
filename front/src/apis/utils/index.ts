import axios from "axios";
import getUserAcToken from "../../layouts/MainLayout"

const BASE_URL = process.env.REACT_APP_API_URL;

const axiosApi = (baseURL: any) => {
  const instance = axios.create({
    baseURL,
    withCredentials: true,
  });
  return instance;
};
export function AxiosAuthApi(baseURL: any, ac:string|null){
  // console.log(getUserAcToken())
  // ac = user.acToken
  console.log("여기있니?")
  const instance = axios.create({
    baseURL,
    withCredentials: true,
  });
  instance.interceptors.request.use(
    (config) => {
      // const access_token = localStorage.getItem("accessToken");
      // console.log('ac',ac)
      if (ac) {
        console.log('ac토큰있음', ac)
        config.headers.Authorization = "Bearer " + ac;
      }
      console.log('config=============', config)
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
    // api헤더에 요청보내는 ac가 만료되었는지 확인해서 새로 발급받는 과정을 interceptors로 삽입
              // instance.interceptors.response.use(
              //   (response) => {
              //     return response;
              //   },

              //   async (error) => {
              //     const {
              //       config,
              //       response: { status },
              //     } = error;
              //     if (status === 401) {
              //       if (error.response.data.code === "003") {
              //         const originalRequest = config;
              //         const refreshToken = localStorage.getItem("refreshToken");
              //         const accessToken = localStorage.getItem("accessToken");
              //         // token refresh 요청
              //         const data = await axios
              //           .get(`${process.env.REACT_APP_API_URL}/user/refresh`, {
              //             headers: {
              //               RefreshToken: `Bearer ${refreshToken}`,
              //               AccessToken: `Bearer ${accessToken}`,
              //             },
              //           })
              //           .then((data) => {
              //             const {
              //               data: {
              //                 accessToken: newAccessToken,
              //                 refreshToken: newRefreshToken,
              //               },
              //             } = data;
              //             localStorage.setItem("accessToken", newAccessToken);
              //             localStorage.setItem("refreshToken", newRefreshToken);
              //             originalRequest.headers.AccessToken = `Bearer ${newAccessToken}`;
              //           })
              //           .catch((error) => {
              //             if (error.response.data.code === "008") {
              //               localStorage.removeItem("isLogin");
              //               localStorage.removeItem("user");
              //               localStorage.removeItem("refreshToken");
              //               localStorage.removeItem("accessToken");
              //               window.location.href = "/login";
              //             }
              //           });
              //         // 요청 후 새롭게 받은 access token과 refresh token 을 다시 저장
              //         // localStorage에도 변경 해야하고 현재 request의 header도 변경

              //         return axios(originalRequest);
              //       }
              //     }
              //     return Promise.reject(error);
              //   }
              // );
  return instance;
};

export const defaultInstance = axiosApi(BASE_URL);

// export const authInstance = AxiosAuthApi(BASE_URL);
