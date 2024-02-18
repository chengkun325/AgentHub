import axios, {AxiosInstance, AxiosRequestConfig} from 'axios'
import router from '@/router'


// 数据返回的接口
// 定义请求响应参数，不含data
export interface Result {
  code: number;
  msg: string
}

// 请求响应参数，包含data
export interface ResultData<T = unknown> extends Result {
  data: T;
}


const URL = window.globalConfig.apiBaseURL;

enum ErrorCode {
  TIMEOUT = 20000,
  GO_LOGIN_DOWN = 2000,
  OG_LOGIN_UP = 2009,
  SUCCESS = 200, // 请求成功
}

const config = {
  // 默认地址
  baseURL: URL,
  // 设置超时时间
  timeout: ErrorCode.TIMEOUT as number,
  // 跨域时候允许携带凭证
  withCredentials: true
}

class RequestHttp {
  // 定义成员变量并指定类型
  service: AxiosInstance;

  public constructor(config: AxiosRequestConfig) {
    // 实例化axios
    this.service = axios.create(config);

    /**
     * 请求拦截器
     * 客户端发送请求 -> [请求拦截器] -> 服务器
     * token校验(JWT) : 接受服务器返回的token,存储到vuex/pinia/本地储存当中
     */
    this.service.interceptors.request.use((config) => {
        // 请求前的处理
        return config;
      },
      error => {
        // 请求报错
        Promise.reject(error)
      }
    );

    /**
     * 响应拦截器
     * 服务器换返回信息 -> [拦截统一处理] -> 客户端JS获取到信息
     */
    this.service.interceptors.response.use(
      response => {
        const result = response.data as ResultData;
        // 登录失效，跳转到登录页面
        if (result.code <= ErrorCode.OG_LOGIN_UP && result.code >= ErrorCode.GO_LOGIN_DOWN) {
          router.replace('/login');
        }
        return response;
      }
    )
  }

  // 常用方法封装
  get<T>(url: string, params?: object): Promise<ResultData<T>> {
    return new Promise((resolve, reject) => {
      this.service.get(url, {params}).then(response => {
        resolve(response.data);
      }).catch(reason => {

        reject(reason);
      })
    })
  }

  post<T>(url: string, data?: object, params?: object): Promise<ResultData<T>> {
    return new Promise((resolve, reject) => {
      this.service.post(url, data, {params}).then(response => {
        resolve(response.data);
      }).catch(reason => {

        reject(reason);
      })
    })
  }
  put<T>(url: string, params?: object): Promise<ResultData<T>> {
    return this.service.put(url, params);
  }
  delete<T>(url: string, params?: object): Promise<ResultData<T>> {
    return this.service.delete(url, {params});
  }
}

// 导出一个实例对象
export default new RequestHttp(config);