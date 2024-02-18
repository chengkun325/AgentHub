<template>
  <div class="loader">
    <div class="loader__circle"></div>
    <div class="loader__circle"></div>
    <div class="loader__circle"></div>
    <div class="loader__circle"></div>
  </div>
</template>

<script setup lang="ts">

</script>

<style lang="scss" scoped>
@use '@/styles/color.scss' as color;
.loader {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.loader__circle {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 0 4px;
  animation: loader_901 1s ease-in-out infinite;

  &:nth-of-type(1) {
    background-color: color.$primary2;
    animation-delay: 0;
  }

  &:nth-of-type(2) {
    background-color: color.$primary3;
    animation-delay: 0.25s;
  }

  &:nth-of-type(3) {
    background-color: color.$primary;
    animation-delay: 0.5s;
  }
}

@keyframes loader_901 {
  0% {
    transform: scale(1);
  }

  20% {
    transform: scale(1.2);
  }

  40% {
    transform: scale(1);
  }
}




</style>