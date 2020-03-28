package com.yocan.push.yuque.app;

import com.yocan.push.yuque.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yocan
 */
@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    RestTemplate restTemplate;

    private  String url ="https://sc.ftqq.com/SCU40692Tccb6b97f9f07146f0b5ac909ca1bfcf35c3c6e3cd17d2.send";

    /**
     * 接收语雀推送，并推送微信
     */
    @RequestMapping("/weChat")
    public void crawlText(@RequestBody Object paramDto){
        System.out.println(JSONUtil.objectToJsonString(paramDto));
        restTemplate.getForEntity(url+"?text="+ JSONUtil.objectToJsonString(paramDto),String.class);
    }

}
