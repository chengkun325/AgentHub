<template>
  <div class="chat-records-main" ref="recordsBox">
    <div  
      v-for="(item, index) in props.messageItemManager.refMessageItemList"
      :class="item.isUser?'user-record-box':'robot-record-box'"
      :key="index">
      <div :class="item.isUser?userBoxClassName:robotBoxClassName">
        <div  v-if="item.isUser">
          <p>{{ item.content }}</p>
        </div>
        <div v-html="item.content" v-else>
        </div>
      </div>
    </div>
    <div class="robot-record-box" v-if="props.messageItemManager.loading.value">
      <div class="robot-record-item">
        <template v-if="props.messageItemManager.waitting.value">
          <p>wait...</p>
        </template>
        <template v-else>
          <div v-html="props.messageItemManager.nowMessageHtml.value">
          </div>
          <p>{{ props.messageItemManager.nowMessageLine.value }}</p>
        </template>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import "highlight.js/styles/atom-one-light.css";
import { MessageItemManager } from '@/types/chat-type';
import {defineProps, watch, ref, nextTick} from 'vue';

const props = defineProps<{
  messageItemManager: MessageItemManager;
}>();

const recordsBox = ref();

const userBoxClassName = "user-record-item";

const robotBoxClassName = "robot-record-item";

const moveScroll = () => {
  nextTick(()=> {
    recordsBox.value.scrollTop = recordsBox.value.scrollHeight;
  })
}

watch(props.messageItemManager.nowMessageLine, () => {
  moveScroll();
});

watch(props.messageItemManager.refMessageItemList, () => {
  moveScroll();
});
</script>

<style lang="scss" >
.chat-records-main {
  height: 100%;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}

.record-item {
  display: inline-block;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
  padding: 10px 15px;
  max-width: calc(100% - 60px);
  margin-bottom: 10px;
  font-size: small;
}

.user-record-item {
  @extend .record-item;
  background-color: #DCF8C6;
  text-align: left;
}

.robot-record-item {
  @extend .record-item;
  background-color: #fff;
}

.record-box {
  width: calc(100% - 20px);
  margin-left: 10px;
}
.user-record-box {
  @extend .record-box;
  text-align: right;
}

.robot-record-box {
  @extend .record-box;
  text-align: left;
}

.code-pre {
  overflow-x: auto;
}

p {
  margin: 5px 0;
  min-height: 1em;
}

</style>