<template>
  <router-view/>
</template>

<script setup lang="ts">
import { _isMobile } from "@/types/utils";
import { onMounted } from "vue";
import router from "./router";

onMounted(() => {
  if (_isMobile()) {
    router.replace("/mobile");
  }else{
    router.replace("/pc");
  }
})

</script>



<style lang="scss">
body {
  padding: 0;
  margin: 0;
}

</style>