<template>
  <div class="message-send">
    <div class="btn-box btn-box-left">
      <el-popover
        :hide-after="0"
        placement="top-start"
        trigger="click"
      >
        <template #reference>
          <img class="icon-svg" :src="require('@/assets/msg-ctrl-open.svg')" alt="展开"/>
        </template>
        连续对话
        <el-switch
          v-model="isContinuous"
          class="is-continuous-switch"
          inline-prompt
          :active-icon="Check"
          :inactive-icon="Close"
        />
      </el-popover>
    </div>
    <div class="input-content">
      <el-input maxlength="1000" @keyup.enter="sendMsgHandle()" type="textarea" resize="none" :autosize="{ minRows: 1, maxRows: 5 }"
        v-model="inputContent"></el-input>
    </div>
    <div class="btn-box btn-box-right">
      <div class="loading-box" v-if="messageItemManager.loading.value">
        <LoaderLine></LoaderLine>
      </div>
      <div v-else>
        <img class="icon-svg" @click="sendMsgHandle()" :src="require('@/assets/发送.svg')" alt="发送"/>
        <el-popconfirm
          :width="200"
          confirm-button-text="确认"
          cancel-button-text="取消"
          title="对话清空后将无法恢复，确认清空？"
          @confirm="props.messageItemManager.cleanHistory()"
        >
          <template #reference>
            <img class="icon-svg" style="height: 25px; margin-bottom: 3px;" :src="require('@/assets/清除.svg')" alt="清除"/>
          </template>
        </el-popconfirm>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import "highlight.js/styles/atom-one-light.css";
import 'element-plus/es/components/message/style/css';
import { Check, Close } from '@element-plus/icons-vue';
import { MessageItemManager } from '@/types/chat-type';
import { ElMessage } from 'element-plus';
import { ref, defineProps } from 'vue';
import { userStore } from '@/pinia';
import { fetchEventSource } from '@microsoft/fetch-event-source';
import LoaderLine from "../ui/LoaderLine.vue";

const useUserStore = userStore();

// --------------props--------------
interface Props {
  messageItemManager: MessageItemManager
}
const props = defineProps<Props>();
// ----------响应式数据----------------
// 用户输入的消息
const inputContent = ref('');
// 是否连续对话
const isContinuous = ref(false);

// ------------函数----------------------
// 发送按钮处理
const sendMsgHandle = () => {
  if (inputContent.value.trim() === '') {
    ElMessage.warning("你要发送什么呢？")
    return;
  }
  if (props.messageItemManager.msgRecords.length >= 15) {
    ElMessage.warning("对话内容太多了，重新开始吧~");
    return;
  }
  if (!isContinuous.value) {
    props.messageItemManager.cleanRecords();
  }
  props.messageItemManager.pushUserMesage(inputContent.value);
  inputContent.value = '';
  send();
}

// 发送消息并处理
let controller: AbortController;
const send = async () => {
  controller = new AbortController();
  props.messageItemManager.waittingStart();
  props.messageItemManager.do();
  // 用来标志是否出错
  let error = false;
  // await fetchEventSource(window.globalConfig.apiBaseURL + "/chat/converse", {
  await fetchEventSource("http://localhost:9801/api/chat/converse", {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userKey: useUserStore.userKey, messageList: props.messageItemManager.msgRecords }),
    signal: controller.signal,
    async onopen() {
      props.messageItemManager.waittingEnd();
    },
    onmessage(event) {
      const data = event.data.substring(1, event.data.length - 1);
      switch (event.event) {
        case 'd': {
          props.messageItemManager.appendNowMessageContent(data);
          break;
        }
        case 'e': {
          ElMessage.error(data);
          props.messageItemManager.appendNowMessageContent(`<span style="color:red">${data}</span>`, false);
          error = true;
          break
        }
        default: {
          props.messageItemManager.appendNowMessageContent(data);
        }
      }
    },
    onclose() {
      if (error) {
        const lastMsg = props.messageItemManager.deleteLastMsgRecord();
        inputContent.value = lastMsg ? lastMsg.content : '';
        props.messageItemManager.messageReceiveDone(false);
      } else {
        props.messageItemManager.messageReceiveDone();
      }
    },
    onerror(error) {
      // TODO 出错时不要重试
      controller.abort();
      console.log(error);
    }
  });
}

</script>

<style lang="scss" scoped>
@use '@/styles/color.scss' as color;

$btn-box-right-width: 100px;
$btn-box-left-width: 45px;
.is-continuous-switch {
  --el-switch-on-color: #13ce66; 
  --el-switch-off-color: #ff4949;
}

.message-send {
  display: flex;
  align-items: flex-end;
  justify-content: center;
  padding: 10px;

  .input-content {
    width: calc(100% - $btn-box-right-width - $btn-box-left-width);
  }

  .loading-box {
    height: 100%;
    margin-left: 10px;
  }

  .btn-box {
    position: relative;
    height: 31px;
    .icon-svg {
      height: 30px;
      margin: 0 8px;
      cursor: pointer
    }
  }

  .btn-box-right {
    width: $btn-box-right-width;
    text-align: right;
  }

  .btn-box-left {
    width: $btn-box-left-width;
    text-align: left;
  }

}
</style>