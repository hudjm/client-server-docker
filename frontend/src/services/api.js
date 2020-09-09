import axios from "axios";
import { getToken } from "./auth";

const api = axios.create({
  baseURL: "http://127.0.0.1:8080"
});

api.interceptors.request.use(async config => {
  console.log('interceptor')
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Basic ${token}`;
  }
  return config;
});

axios.interceptors.request.use(config => {

  console.log('interceptor')
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Basic ${token}`;
  }
  return config;
}, error => {
  console.log('erro intercpet2')
  console.log(error)
  console.log(error.response.status)
  if (error.response.status === 401) {
    this.props.history.push("/");
  }

  return Promise.reject(error);
});

export default api;