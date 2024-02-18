<template>
  <div class="pc-chat-main">
    <div class="pc-chat-window">
      <MainLeftBar class="left-bar"></MainLeftBar>
      <MainRightContentShow></MainRightContentShow>
    </div>
  </div>
</template>

<script setup lang="ts">
import MainLeftBar from './MainLeftBar.vue';
import MainRightContentShow from './MainRightContentShow.vue';

</script>

<style lang="scss" scoped>
@use '@/styles/measurement.scss' as measurement;
.pc-chat-main {
  display: flex;
  justify-content: center; /* 在主轴上居中 */
  height: 100vh; /* 设置容器的高度 */


  .pc-chat-window {
    margin-top: 5vh;
    padding-bottom: 3px;
    border-radius: 5px;
    width: measurement.$chatWindowWidth;
    height: measurement.$chatWindowHeight;
    min-width: measurement.$chatWindowWidthMin;
    min-height: measurement.$chatWindowHeightMin;
    background: rgb(236, 236, 236);
    box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
  }

  .left-bar {
    float: left;
  }



}

</style>