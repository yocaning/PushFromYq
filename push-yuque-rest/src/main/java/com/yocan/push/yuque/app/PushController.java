package com.yocan.push.yuque.app;

import com.yocan.push.yuque.Constant.ConstantData;
import com.yocan.push.yuque.dto.ParamDto;
import com.yocan.push.yuque.dto.RequestDto;
import com.yocan.push.yuque.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    /**
     * 接收语雀推送，并推送微信
     */
    @RequestMapping("/weChat")
    public String  pushText(@RequestBody ParamDto paramDto){
        System.out.println(JSONUtil.objectToJsonString(paramDto));
        RequestDto requestParam =new RequestDto();
        requestParam.setSendkey("17915-529c8c66882169a27b63b26da3ee137e");
        requestParam.setText("语雀通知-测试");
        requestParam.setDesp(paramDto.getData().getBody());
        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonString = JSONUtil.objectToJsonString(requestParam);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        ResponseEntity responseEntity =restTemplate.getForEntity(ConstantData.URL+"",String.class);
        return responseEntity.getBody().toString();
    }

}
