package com.yocan.push.yuque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestfulConfig {
    /**
     * 给restTemplate添加自定义消息转换器
     * */


    @Bean(value = "rawRestTemplate")
    public RestTemplate rawRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SupportOtherMediaTypeMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    /**
     * 注册这个bean
     * feign会自动添加，从而支持对contenttype非application/json类型信息的转换
     * spring mvc模块也会自动添加，从而支持接收非application/json类型的请求
     * */
    @Bean
    public SupportOtherMediaTypeMappingJackson2HttpMessageConverter supportOtherMediaTypeMappingJackson2HttpMessageConverter(){
        return new SupportOtherMediaTypeMappingJackson2HttpMessageConverter();
    }

    /**
     * 默认情况下，restTemplate和feign在调用rest接口时，只会对content type类型为application/json和application/*+json
     * 且编码为utf-8的response进行反序列化，但是有些接口并不遵循这个规范，数据为json，但是content type不为上述值，这时
     * 进行对象映射会抛出找不到messageConvert异常，因此需要给restTemplate和feign添加定制的messageconvert
     * 这里定义了一个支持content type 为text/html和text/plain的json转换器
     * */
    class SupportOtherMediaTypeMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public SupportOtherMediaTypeMappingJackson2HttpMessageConverter(){
            List<MediaType> supportMediaTypes = new ArrayList<>(2);
            supportMediaTypes.add(MediaType.TEXT_HTML);
            supportMediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(supportMediaTypes);
        }
    }
}
