<template>
  <div class="pc-main-right-content-show">
    <ChatPage v-show="isChatPage"></ChatPage>
    <SettingPage v-show="isSettingPage"></SettingPage>
    <TestPage v-show="isTestPage"></TestPage>
  </div>
</template>

<script setup lang="ts">
import { menuStore } from '@/pinia';
import { computed } from 'vue';
import TestPage from './test/TestPage.vue';
import ChatPage from './chat/ChatIndex.vue';
import SettingPage from '@/views/common/setting/SettingPage.vue';
const mlbStore = menuStore();

const isChatPage = computed(() => mlbStore.key === 1);
const isSettingPage = computed(() => mlbStore.key === 2);
const isTestPage = computed(() => mlbStore.key === 3);

</script>

<style lang="scss" scoped>
@use '@/styles/measurement.scss' as measurement;

.pc-main-right-content-show {
  border-radius: 0 5px 5px 0;
  float: left;
  height: 100%;
  width: calc(100% - measurement.$mainLeftBarWidth);
}

</style>
