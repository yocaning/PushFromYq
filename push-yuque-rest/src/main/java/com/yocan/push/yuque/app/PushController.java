package com.yocan.push.yuque.app;

import com.yocan.push.yuque.Constant.ConstantData;
import com.yocan.push.yuque.dto.ParamDto;
import com.yocan.push.yuque.dto.RequestDto;
import com.yocan.push.yuque.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yocan
 */
@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    RestTemplate restTemplate;

    private static Set<String> FILE_URL =new LinkedHashSet<>();

    static {
        for (String data:ConstantData.URL){
            FILE_URL.add(data);
        }
    }


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
        List<String> listUrl =getUrlList();
        if ( listUrl!=null){
            for (String url:listUrl){
                FILE_URL.add(url);
            }
        }
        for (String url : FILE_URL) {
            stringResponseEntity = restTemplate.getForEntity(url + "?text=" + requestParam.getText() + "&desp=-" + requestParam.getDesp(), String.class);
        }
        return stringResponseEntity.getBody();
    }

    @GetMapping("/urlRegistered")
    public String urlRegistered(@RequestParam String url) throws IOException {
        if (url.contains("\"")) {
            return "URL错误，请检查重试";
        }
        System.out.println("URL-注册" + url);
        File file = new File("/root/data/url");
        try (Writer writer = new FileWriter(file, true)) {
            // 把内容转换成字符数组
            char[] data = url.toCharArray();
            for (char chars : data) {
                writer.append(chars);
            }
            writer.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
            return "异常，请联系 Yocan";
        }
        return "OK";
    }


    public static List<String> getUrlList() {
        // 构建指定文件
        // 根据文件创建文件的输出流
        File file = new File("/root/data/url");
        try (FileReader reader = new FileReader(file)) {
            // 创建字符数组
            char[] data = new char[10240];
            // 读取内容，放到字符数组里面
            reader.read(data);
            String string = new String(data);
            string = string.trim();
            String[] strings = string.split("\n");

            return Arrays.asList(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(1);
    }

}
