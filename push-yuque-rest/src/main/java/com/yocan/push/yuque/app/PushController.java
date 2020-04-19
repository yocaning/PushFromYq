package com.yocan.push.yuque.app;

import com.yocan.push.yuque.Constant.ConstantData;
import com.yocan.push.yuque.dto.ParamDto;
import com.yocan.push.yuque.dto.RequestDto;
import com.yocan.push.yuque.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 2020-03-30   21:00 修改为单点推送，由于没有服务号
     *  2020-03-30  22:00 无法一对多推送，可将需要推送的地址维护一个list  done
     *TODO  2020-04-18 想增加评论相关信息
     * 2020-04-19 增加根据发布类型，进行评论和文章的区分
     */
    @RequestMapping("/weChat")
    public String  pushText(@RequestBody ParamDto paramDto){
        System.out.println(JSONUtil.objectToJsonString(paramDto));
        RequestDto requestParam =new RequestDto();
        int type =ConstantData.HASHMAP.get(paramDto.getData().getAction_type())==null?0:ConstantData.HASHMAP.get(paramDto.getData().getAction_type());
        if (type >1){
            requestParam.setText("<语雀通知>"+ConstantData.COMMENT.get(type)+paramDto.getData().getCommentable().getTitle());
            requestParam.setDesp(paramDto.getData().getBody_html());

        }else {
            requestParam.setText("<语雀通知>"+paramDto.getData().getTitle());
            requestParam.setDesp(paramDto.getData().getBody());

        }
        ResponseEntity<String> stringResponseEntity =null;
        //构造get方法发送消息
        for (String url: ConstantData.URL){
            stringResponseEntity=restTemplate.getForEntity(url+"?text="+requestParam.getText()+"&desp=-"+requestParam.getDesp(),String.class);
        }
        return stringResponseEntity.getBody();
    }

}
