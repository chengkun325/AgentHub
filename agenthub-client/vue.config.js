const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')

module.exports = defineConfig({
  publicPath: './',
  transpileDependencies: true,
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()]
      }),
      Components({
        resolvers: [ElementPlusResolver()]
      })
    ]
  },

  devServer: {
    //请求服务器的地址
    proxy: {
        //  '/yu'为请求前缀，用于控制是不是走代理，想走代理时就在请求前缀前边加上这个请求前缀
        '/api': {
            target: 'http://localhost:9801/',
            changeOrigin: true //用于控制请求头中的host值:
        }
    }
  }
})
