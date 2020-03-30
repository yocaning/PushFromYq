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
     * 2020-03-03 修改为单点推送，由于没有服务号
     *  TODO 无法一对多推送，可将需要推送的地址维护一个list todo
     */
    @RequestMapping("/weChat")
    public String  pushText(@RequestBody ParamDto paramDto){
        System.out.println(JSONUtil.objectToJsonString(paramDto));
        RequestDto requestParam =new RequestDto();
        requestParam.setText("语雀通知"+paramDto.getData().getTitle());
        requestParam.setDesp(paramDto.getData().getBody());
        //构造get方法发送消息
        ResponseEntity<String> stringResponseEntity =restTemplate.getForEntity(ConstantData.URL+"?text="+requestParam.getText()+"&desp=-"+requestParam.getDesp(),String.class);
        return stringResponseEntity.getBody();
    }

}
