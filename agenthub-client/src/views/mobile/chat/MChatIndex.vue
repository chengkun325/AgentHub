<template>
  <div class="main">
    <div class="records-box">
      <ChatRecords :message-item-manager="messageItemManager"></ChatRecords>
    </div>
    <div class="control-box">
      <MessageCtrl :message-item-manager="messageItemManager"></MessageCtrl>
    </div>
  </div>
</template>

<script setup lang="ts">
import { MessageItemManager } from '@/types/chat-type';
import ChatRecords from '@/views/common/chat/ChatRecords.vue';
import MessageCtrl from '@/views/common/chat/MessageCtrl.vue';

// 消息管理器
const messageItemManager = new MessageItemManager();

</script>

<style lang="scss" scoped>

.main {
  width: 100%;
  height: 100%;
}
.records-box {
    width: 100%;
    height: calc(100% - 80px);
    padding-bottom: 70px;
    padding-top: 10px;
    background-color: rgb(236, 236, 236);
  }

.control-box {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  background-color: #f5f5f5;
  padding-bottom: 20px;
}

</style>