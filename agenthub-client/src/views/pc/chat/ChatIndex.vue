<template>
  <div style="height: 100%; position: relative;">
    <div class="chat-records-box">
      <ChatRecords :message-item-manager="messageItemManager"></ChatRecords>
    </div>
    <div class="input-send">
      <MessageCtrl :message-item-manager="messageItemManager" ></MessageCtrl>
    </div>
  </div>
</template>

<script setup lang="ts">
import ChatRecords from '@/views/common/chat/ChatRecords.vue';
import { MessageItemManager } from '@/types/chat-type';
import MessageCtrl from '@/views/common/chat/MessageCtrl.vue';

// 消息管理器
const messageItemManager = new MessageItemManager();

</script>

<style lang="scss" scoped>
.input-send {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  background-color: #f5f5f5;
}

.chat-records-box {
  width: 100%;
  height: calc(100% - 90px);
  // overflow-y: scroll;
  padding: 10px 0 80px 0;
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-thumb {
  border-radius: 3px;
  background-color: rgba(0, 0, 0, 0.2);
}

::-webkit-scrollbar-track {
  border-radius: 3px;
  background-color: rgba(0, 0, 0, 0.1);
}

</style>