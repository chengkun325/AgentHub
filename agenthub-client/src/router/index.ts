import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import PcChatMain from '../views/pc/PcChatMain.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/pc',
    name: 'pc-chat',
    component: PcChatMain
  },
  {
    path: '/mobile',
    name: 'mobile-chat',
    component: () => import('@/views/mobile/MobileMain.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
