import { reactive, ref } from "vue";
import md from "./markdown";

export interface MessageItem {
  isUser: boolean;
  content: string;
}

interface MsgRecord {
  role: string,
  content: string,
}


export class MessageItemManager {
  refMessageItemList = reactive<Array<MessageItem>>([]);
  nowMessageText = '';
  nowMessageHtml = ref("");
  nowMessageLine = ref("");
  loading = ref(false);
  waitting = ref(false);
  msgRecords = new Array<MsgRecord>();

  public pushUserMesage(content: string) {
    this.refMessageItemList.push({isUser: true, content: content})
    this.msgRecords.push({role: 'user', content: content})
  }

  /**
   * 消息接收结束
   * 
   * @param record 是否记录本次接收的消息
   */
  public messageReceiveDone(record = true): void {
    if (this.nowMessageLine.value !== '') {
      this.nowMessageText += this.nowMessageLine.value;
      this.nowMessageHtml.value = md.render(this.nowMessageText);
      this.nowMessageLine.value = '';
    }
    if (record) {
      this.msgRecords.push({role: 'assistant', content: this.nowMessageText})
    }
    this.nowMessageText = '';
    this.refMessageItemList.push({isUser: false, content: this.nowMessageHtml.value});
    this.loading.value = false;
    this.nowMessageHtml.value = '';
  }

  public do(): void {
    this.loading.value = true;
  }

  public waittingStart() {
    this.waitting.value = true;
  }

  public waittingEnd() {
    this.waitting.value = false;
  }

  /**
   * 清空消息历史，包含界面显示的和记录的
   */
  public cleanHistory(): void {
    this.cleanRecords();
    if (this.refMessageItemList.length > 0) {
      this.refMessageItemList.splice(0, this.refMessageItemList.length);
    }
  }

  /**
   * 清空消息记录
   */
  public cleanRecords(): void {
    if (this.msgRecords.length > 0) {
      this.msgRecords = [];
    }
  }
 
  public deleteLastMsgRecord(): MsgRecord | undefined {
    return this.msgRecords.pop();
  }

  /**
   * 向现在的消息中添加新内容
   * TODO 优化md的转换，减少转化次数
   * @param newContent 新的内容
   */
  public appendNowMessageContent(newContent: string, parse = true): void {
    if (parse) {
      newContent = JSON.parse(`"${newContent}"`);
    }
    if (newContent.includes("\n")) {
      this.nowMessageText +=
        this.nowMessageLine.value + newContent;
      this.nowMessageLine.value = "";
      this.nowMessageHtml.value = md.render(this.nowMessageText);
    } else {
      this.nowMessageLine.value += newContent;
    }
  }

}
