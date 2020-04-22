package com.yocan.push.yuque.app;

import com.yocan.push.yuque.Constant.ConstantData;
import com.yocan.push.yuque.dto.ParamDto;
import com.yocan.push.yuque.dto.RequestDto;
import com.yocan.push.yuque.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.channels.SocketChannel;

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
     * 2020-03-30  22:00 无法一对多推送，可将需要推送的地址维护一个list  done
     * 2020-04-18 想增加评论相关信息 done
     * 2020-04-19 增加根据发布类型，进行评论和文章的区分
     * 2020-04-22 添加自定义更新url接口
     */
    @RequestMapping("/weChat")
    public String pushText(@RequestBody ParamDto paramDto) {
        System.out.println(JSONUtil.objectToJsonString(paramDto));
        RequestDto requestParam = new RequestDto();
        int type = ConstantData.HASHMAP.get(paramDto.getData().getAction_type()) == null ? 0 : ConstantData.HASHMAP.get(paramDto.getData().getAction_type());
        if (type > 1) {
            requestParam.setText("<语雀通知>" + ConstantData.COMMENT.get(type) + paramDto.getData().getCommentable().getTitle());
            requestParam.setDesp(paramDto.getData().getBody_html());

        } else {
            requestParam.setText("<语雀通知>" + paramDto.getData().getTitle());
            requestParam.setDesp(paramDto.getData().getBody());

        }
        ResponseEntity<String> stringResponseEntity = null;
        //构造get方法发送消息
        for (String url : ConstantData.URL) {
            stringResponseEntity = restTemplate.getForEntity(url + "?text=" + requestParam.getText() + "&desp=-" + requestParam.getDesp(), String.class);
        }
        return stringResponseEntity.getBody();
    }

    @GetMapping("/urlRegistered")
    public String urlRegistered(@RequestParam String url) throws IOException {
        if (url.contains("\"")){
            return "URL错误，请检查重试";
        }
        System.out.println("URL-注册" + url);
        File file = new File("~/data/url.txt");
        try (Writer writer = new FileWriter(file, true)) {
            // 把内容转换成字符数组
            char[] data = url.toCharArray();
            for (char chars : data) {
                writer.append(chars);
            }
            writer.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }


}
