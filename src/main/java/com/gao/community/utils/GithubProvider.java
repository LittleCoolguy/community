package com.gao.community.utils;

import com.alibaba.fastjson.JSON;
import com.gao.community.dto.AccessTokenDTO;
import com.gao.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @description:
 * @author: XiaoGao
 * @time: 2021/11/3 20:07
 */
@Component
@Slf4j
public class GithubProvider {
    private static final Logger loggger = LoggerFactory.getLogger(GithubProvider.class);
    /**
     * 使用okhttp发送post请求到github授权地址
     * @param accessTokenDTO
     * @return accessToken
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            return split[0].split("=")[1];
        } catch (IOException e) {
            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }return null;
    }

    /**
     * 通过github返回的accessToken获取github用户信息
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token=" + accessToken)
                .url("https://api.github.com/user")
                .header("Authorization", "token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            return JSON.parseObject(string,GithubUser.class);
        } catch (IOException e){
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }
}
