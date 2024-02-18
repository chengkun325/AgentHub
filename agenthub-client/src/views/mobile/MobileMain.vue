<template>
  <div class="mobile-main">
    <div class="header-box">
      <MobileHeader></MobileHeader>
    </div>
    <div class="content-box">
        <MChatIndex v-show="isChatPage"></MChatIndex>
        <SettingPage v-show="isSettingPage"></SettingPage>
    </div>
  </div>
</template>

<script setup lang="ts">
import MobileHeader from './MobileHeader.vue';
import MChatIndex from './chat/MChatIndex.vue';
import SettingPage from '@/views/common/setting/SettingPage.vue';
import { computed } from 'vue';
import {menuStore} from '@/pinia'

const mStore = menuStore();


const isChatPage = computed(() => mStore.key === 1);
const isSettingPage = computed(() => mStore.key === 2);
const isTestPage = computed(() => mStore.key === 3);



</script>

<style lang="scss" scoped>

.mobile-main {
  width: 100%;
  height: 100vh;
}

.header-box {
    width: 100%;
    height: 50px;
  }
.content-box {
  width: 100%;
  height: calc(100% - 50px);
}

</style>