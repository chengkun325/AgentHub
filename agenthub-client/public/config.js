// 后端服务器
const serverHost = "localhost:8080";
// 接口共有的URL前段
const apiBaseURLPre = "/api";
// 是否是https
const ssl = false;
// api前缀
const apiBaseURL = "http" + (ssl ? "s" : "") + "://" + serverHost + apiBaseURLPre;


window.globalConfig = {
  apiBaseURL: apiBaseURL,
}