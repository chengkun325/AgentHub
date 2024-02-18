<template>
  <div class="main">
    <div>
      <div>
        <span class="configuration-item-text">UserKey</span>
        <el-input
          v-model="userKeyInput"
          placeholder="请输入User Key"
          style="display: inline-block; width: 200px;">
        </el-input>
        <el-button :type="showUserInfo?'warning':'success'" plain @click="checkUserKey()">{{showUserInfo?'刷新':'确定'}}</el-button>
        <el-popover
          placement="top"
          :width="150"
          trigger="hover"
        >
          <template #reference>
            <span class="tip-text">联系我</span>
          </template>
          <el-image :src="require('@/assets/chengkun.png')" fit="fill"/>
        </el-popover>
      </div>
      <div v-if="showUserInfo">
        <el-tag type="warning" round>用户 : {{ userInfo.username }}</el-tag>
        <el-tag type="danger" round>余量 : {{ userInfo.usage }}</el-tag>
        <el-tag type="info" round>用户ID : {{ userInfo.userId }}</el-tag>
        <el-tag type="success" round>KeyID : {{ userInfo.keyId }}</el-tag>
      </div>
    </div>
        
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue';
import { userStore } from '@/pinia';
import { ElMessage } from "element-plus";
import Http from '@/api/request';

const useUserStore = userStore();

const userKeyInput = ref('');

const showUserInfo = ref(false);

const userInfo = reactive<UserInfo>({
  key: '',
  usage: 0,
  username: '',
  keyId: 0,
  userId: 0,
});

interface UserInfo {
  key: string;
  usage: number;
  username: string;
  keyId: number;
  userId: number;
}

watch(userKeyInput, () => {
  showUserInfo.value = false;
})

const checkUserKey = () => {
    const promise = Http.post<UserInfo>("/user/info", {userKey: userKeyInput.value});
    promise.then(res => {
        if (res.data) {
            userInfo.key = res.data.key;
            userInfo.usage = res.data.usage;
            userInfo.username = res.data.username;
            userInfo.keyId = res.data.keyId;
            userInfo.userId = res.data.userId;
            showUserInfo.value = true;
            useUserStore.setUserKey(userKeyInput.value);
        } else {
            ElMessage.error("UserKey不存在");
        }
    })

}

</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
}

.configuration-item-text {
  color: darkgrey;
  font-weight: 700;
  &::after {
    content: " : ";
  }
}

.tip-text {
  font-size: smaller;
  color: cadetblue;
  cursor: pointer;
  margin-left: 10px;
}


</style>