import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'

interface Option {
  facePath: string,
  label: string,
  key: number
}



export const menuStore = defineStore('mainLeftBarStore', () => {
  const key = ref(2);

  const optionList = reactive<Array<Option>>([
    {
      facePath: require("@/assets/robot.svg"),
      label: "对 话",
      key: 1
    },
    {
      facePath: require("@/assets/settings.svg"),
      label: "设 置",
      key: 2
    },
    {
      facePath: require("@/assets/question.svg"),
      label: "问 题",
      key: 3
    },
  ])


  function setKey(newKey: number) {
    key.value = newKey;
  }

  return { key, setKey, optionList }
})


export const userStore = defineStore('userStore', () => {
  const userKey = ref('');

  function setUserKey(newUserKey: string) {
    userKey.value = newUserKey;
  }


  return { userKey, setUserKey }
})
