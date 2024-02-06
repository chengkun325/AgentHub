package com.chengkun.agenthub;


import baidumodel.constant.ChatRoleConstant;
import baidumodel.entity.chat.*;
import baidumodel.listener.BaiduEventSourceListener;
import baidumodel.service.BaiduService;
import baidumodel.util.TokenUtil;
import cn.hutool.json.JSONUtil;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度千帆SDK测试
 */
public class BaiduServiceTest {

    private final String apiKey = "W16RirSjGTpVFD57dUKlCZCF";
    private final String secretKey = "MKRdo1qnWHxuoyKto3hHv0CIuAWegVxw";

    @Test
    public void testErnieBotTurbo() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ErnieBotTurboParam param = ErnieBotTurboParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        ErnieBotTurboResponse ernieBotTurboResponse = baiduService.ernieBotTurbo(param, token);
        System.out.println(JSONUtil.toJsonStr(ernieBotTurboResponse));
    }

    @Test
    public void testErnieBotTurboStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ErnieBotTurboParam param = ErnieBotTurboParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.ernieBotTurboStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testBloomz7b() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Bloomz7bParam param = Bloomz7bParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        Bloomz7bResponse bloomz7bResponse = baiduService.bloomz7b(param, token);
        System.out.println(JSONUtil.toJsonStr(bloomz7bResponse));
    }

    @Test
    public void testBloomz7bStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Bloomz7bParam param = Bloomz7bParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.bloomz7bStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testErnieBot() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ErnieBotParam param = ErnieBotParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        ErnieBotResponse ernieBotResponse = baiduService.ernieBot(param, token);
        System.out.println(JSONUtil.toJsonStr(ernieBotResponse));
    }

    @Test
    public void testErnieBotStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ErnieBotParam param = ErnieBotParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.ernieBotStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        BaiduService baiduService = new BaiduService(apiKey, secretKey, client.build());
        ErnieBotParam param = ErnieBotParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.ernieBotStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testErnieBot4() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ErnieBot4Param param = ErnieBot4Param.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        ErnieBot4Response ernieBotResponse = baiduService.ernieBot4(param, token);
        System.out.println(JSONUtil.toJsonStr(ernieBotResponse));
    }

    @Test
    public void testErnieBot4Stream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ErnieBot4Param param = ErnieBot4Param.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .temperature(0.95F)
                .penalty_score(1.0F)
                .top_p(0.8F)
                .system("你是一个贴心小助手")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.ernieBot4Stream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testPromptTemplate() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Map<String, Object> map = new HashMap<>();
        map.put("number", 2);
        map.put("content", "如何写一个好提纲");
        PromptTemplateParam param = PromptTemplateParam.builder()
                .id(1966)
                .values(map)
                .build();
        PromptTemplateResponse promptTemplateResponse = baiduService.promptTemplate(param, baiduService.getToken());
        System.out.println(JSONUtil.toJsonStr(promptTemplateResponse));
    }

    @Test
    public void testEmbeddingV1() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        EmbeddingV1Param param = EmbeddingV1Param.builder()
                .input(Collections.singletonList("文本向量"))
                .user_id("1")
                .build();
        EmbeddingV1Response embeddingV1Response = baiduService.embeddingV1(param, baiduService.getToken());
        System.out.println(JSONUtil.toJsonStr(embeddingV1Response));
    }

    @Test
    public void testVisualGLM6B() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        VisualGLM6BParam param = VisualGLM6BParam.builder()
                .apiName("apiName")
                .prompt("生成一张小猫的图片")
                .height(500)
                .width(500)
                .build();
        VisualGLM6BResponse visualGLM6BResponse = baiduService.visualGLM6B(param, baiduService.getToken());
        System.out.println(JSONUtil.toJsonStr(visualGLM6BResponse));
    }

    @Test
    public void testLinlyChineseLLaMA() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        LinlyChineseLLaMAParam param = LinlyChineseLLaMAParam.builder()
                .apiName("apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        LinlyChineseLLaMAResponse linlyChineseLLaMAResponse = baiduService.linlyChineseLLaMA(param, token);
        System.out.println(JSONUtil.toJsonStr(linlyChineseLLaMAResponse));
    }

    @Test
    public void testLinlyChineseLLaMAStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        LinlyChineseLLaMAParam param = LinlyChineseLLaMAParam.builder()
                .apiName("your apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.linlyChineseLLaMAStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testChatGLM26B() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ChatGLM26BParam param = ChatGLM26BParam.builder()
                .apiName("apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        ChatGLM26BResponse chatGLM26BResponse = baiduService.chatGLM26B(param, token);
        System.out.println(JSONUtil.toJsonStr(chatGLM26BResponse));
    }

    @Test
    public void testChatGLM26BStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        ChatGLM26BParam param = ChatGLM26BParam.builder()
                .apiName("your apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.chatGLM26BStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testOpenLLaMA7B() {BaiduService baiduService = new BaiduService(apiKey, secretKey);
        OpenLLaMA7BParam param = OpenLLaMA7BParam.builder()
                .apiName("apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        OpenLLaMA7BResponse response = baiduService.openLLaMA7B(param, token);
        System.out.println(JSONUtil.toJsonStr(response));
    }

    @Test
    public void testOpenLLaMA7BStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        OpenLLaMA7BParam param = OpenLLaMA7BParam.builder()
                .apiName("your apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.openLLaMA7BStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testFalcon7B() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Falcon7BParam param = Falcon7BParam.builder()
                .apiName("apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        Falcon7BResponse response = baiduService.falcon7B(param, token);
        System.out.println(JSONUtil.toJsonStr(response));
    }

    @Test
    public void testFalcon7BStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Falcon7BParam param = Falcon7BParam.builder()
                .apiName("your apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.falcon7BStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testDolly12B() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Dolly12BParam param = Dolly12BParam.builder()
                .apiName("apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        Dolly12BResponse response = baiduService.dolly12B(param, token);
        System.out.println(JSONUtil.toJsonStr(response));
    }

    @Test
    public void testDolly12BStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Dolly12BParam param = Dolly12BParam.builder()
                .apiName("your apiName")
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.dolly12BStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testLlama2_70bChat() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Llama2ChatParam param = Llama2ChatParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        LlamaChatResponse response = baiduService.Llama2_70bChat(param, token);
        System.out.println(JSONUtil.toJsonStr(response));
    }

    @Test
    public void testLlama2_7bChat() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Llama2ChatParam param = Llama2ChatParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        LlamaChatResponse response = baiduService.Llama2_7bChat(param, token);
        System.out.println(JSONUtil.toJsonStr(response));
    }

    @Test
    public void testLlama2_13bChat() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Llama2ChatParam param = Llama2ChatParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        LlamaChatResponse response = baiduService.Llama2_13bChat(param, token);
        System.out.println(JSONUtil.toJsonStr(response));
    }

    @Test
    public void testLlama2_70bChatStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Llama2ChatParam param = Llama2ChatParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.Llama2_70bChatStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testLlama2_7bChatStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Llama2ChatParam param = Llama2ChatParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.Llama2_7bChatStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testLlama2_13bChatStream() {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        Llama2ChatParam param = Llama2ChatParam.builder()
                .messages(Collections.singletonList(BaiduChatMessage.builder()
                        .role(ChatRoleConstant.USER)
                        .content("你是谁呀？")
                        .build()))
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        // 通过 BaiduEventSourceListener#onEvent 方法接收数据
        baiduService.Llama2_13bChatStream(param, token, new BaiduEventSourceListener());
        while (true) {
        }
    }

    @Test
    public void testStableDiffusionXL() throws IOException {
        BaiduService baiduService = new BaiduService(apiKey, secretKey);
        StableDiffusionXLParam param = StableDiffusionXLParam.builder()
                .prompt("画一只可爱的小猫咪")
                .negative_prompt("背景不要白色")
                .steps(null)
                .size("512x512")
                .n(1)
                .sampler_index("Euler a")
                .user_id("1")
                .build();
        String token = TokenUtil.getToken(apiKey, secretKey);
        StableDiffusionXLResponse response = baiduService.stableDiffusionXL(param, token);
        byte[] imageBytes = Base64.getDecoder().decode(response.getData().get(0).getB64_image());
        Files.write(Paths.get("output.png"), imageBytes, StandardOpenOption.CREATE_NEW);
        System.out.println(JSONUtil.toJsonStr(response));
    }
}
