<template>
  <div class="m-header">
    <div class="menu-box" @click="drawerCtrl()">
      <img :src="require('@/assets/菜单.svg')" />
    </div>
    <el-drawer
      :with-header="false"
      v-model="drawer"
      style="padding: 0;"
      :show-close="false"
      :size="70"
      direction="ltr"
    >
    <MobileBarVue></MobileBarVue>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import MobileBarVue from './MobileBar.vue';
import { ref } from 'vue';

const drawer = ref(false);

const drawerCtrl = () => {
  drawer.value = !drawer.value;
}


</script>

<style lang="scss" scoped>
@use '@/styles/color.scss' as color;

:deep(.el-drawer__body) {
  padding: 0;
}

.m-header {
  width: 100%;
  height: 100%;
  background-color: color.$primary;
  .menu-box {
    height: 100%;
    margin-left: 10px;
    img {
      height: 100%;
    }
  }
  
}
</style>