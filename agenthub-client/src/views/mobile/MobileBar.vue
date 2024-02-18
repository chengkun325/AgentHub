<template>
  <div class="mobile-bar">
    <div>
      <!-- 头像 -->
      <div class="face-show-box">
        <div>
          <img src="@/assets/unlogin-face.svg" alt="face">
        </div>
      </div>
      <!-- 选项 -->
      <div class="option-show-box">
        <div>
          <div 
            :class="item.key === mStore.key ? 'option-item-select' : 'option-item'" 
            v-for="(item, index) in mStore.optionList" 
            @click="clickOptionHandler(item.key)"
            :key="index">
            <div>
              <img :src="item.facePath" alt="face">
              <span>{{ item.label }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {menuStore} from '@/pinia'

const mStore = menuStore();


const clickOptionHandler = (key: number) => {
  mStore.setKey(key);
}


</script>

<style lang="scss" scoped>
@use '@/styles/measurement.scss' as measurement;
@use '@/styles/color.scss' as color;

.mobile-bar {
  padding-top: 15px;
  display: flex;
  justify-content: center;
  height: 100%;
  width: 100%;
  background-color: color.$primary;
}
.face-show-box {
  width: measurement.$mainLeftBarWidth - 15px;
  height: measurement.$mainLeftBarWidth - 15px;
  display: flex;
  justify-content: center;
  float: top;
  div {
    width: measurement.$mainLeftBarWidth - 20px;
    height: measurement.$mainLeftBarWidth - 20px;
    background-color: rgba(240, 248, 255, 0.315);
    border-radius: 5px;
    cursor: pointer;

    img {
      height: 100%;
    }
  }
}

.option-show-box {
  padding-top: 20px;
  width: 100%;
  display: flex;
  justify-content: center;
  float: top;
  .option-item-basic {
    margin:10px 0;
    border-radius: 5px;
    width: measurement.$mainLeftBarWidth - 15px;;
    height: measurement.$mainLeftBarWidth - 15px;;
    text-align: center;
    cursor: pointer;
    
    div {
      position: relative;
      top: 50%;
      transform: translateY(-50%);
    }
    img {
      height: 20px;
    }
    span {
      display: block;
      text-align: center;
      font-size: xx-small;
      font-weight: 900;
      color: #fff;
    }
  }

  .option-item {
    @extend .option-item-basic;
    &:hover {
      background-color: rgba(255, 255, 255, 0.1);
    }
  }

  .option-item-select {
    @extend .option-item-basic;
    background-color: rgba(255, 255, 255, 0.2);
  }

}

</style>