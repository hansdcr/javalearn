package com.hans.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hans.demo.exception.http.ParameterException;
import com.hans.demo.model.User;
import com.hans.demo.repository.UserRepository;
import com.hans.demo.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticationService {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${wx.code2session}")
    private String code2sessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;

    public String code2session(String code){
        // 构建向微信小程序服务器发送的url
        String url = MessageFormat.format(code2sessionUrl, this.appid, this.appsecret, code);
        RestTemplate restTemplate = new RestTemplate();
        // 向微信服务器发送请求，并获取返回结果
        String sessionText = restTemplate.getForObject(url, String.class);
        //  反序列化，将response的返回结果序列化为类

        Map<String, Object> session = new HashMap<>();
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // TODO openid判空
        }

        return this.registerUser(session);
    }

    public String registerUser(Map<String, Object> session){
        String openid = (String) session.get("openid");
        if(openid == null){
            throw  new ParameterException(20004);
        }

        Optional<User> userOptional = userRepository.findByOpenid(openid);
        if(userOptional.isPresent()){
            // TODO: 返回JTW令牌
            return JwtToken.makeToken(userOptional.get().getId());
        }

        User user = User.builder().openid(openid).build();
        // 将user数据保存的数据库
        userRepository.save(user);
        // 返回jWT令牌
        Long uid = user.getId();
        return JwtToken.makeToken(uid);
    }

}
