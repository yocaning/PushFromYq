package com.yocan.push.yuque.app;

import com.yocan.push.yuque.Constant.ConstantData;
import com.yocan.push.yuque.dto.ParamDto;
import com.yocan.push.yuque.dto.RequestDto;
import com.yocan.push.yuque.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
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
    @Qualifier(value = "rawRestTemplate")
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
     * TODO 换成post方法，get方法有部分限制 done
     * 2020-04-27 修改get方法为post方法
     * restTemplate.getForEntity(url + "?text=" + requestParam.getText() + "&desp=-" + requestParam.getDesp(), String.class);
     *
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
            //将对象装入HttpEntity中
            MultiValueMap map = new LinkedMultiValueMap();
            map.add("text", requestParam.getText());
            map.add("desp", requestParam.getDesp());
            stringResponseEntity = restTemplate.postForEntity(url, map,String.class);

        }
        return stringResponseEntity.getBody();
    }

    @GetMapping("/urlRegistered")
    public String urlRegistered(@RequestParam String url)  {
        if (url.contains("\"") || !url.startsWith("http") || !url.endsWith("send")) {
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

    @GetMapping("/amountExe")
    public BigDecimal amountExe(@RequestParam Integer people,@RequestParam Long amount) {
        Integer historyAmount=0;
        Integer truePeople=people;
        if (people>50 ){
            historyAmount +=(people-50)*800;
            people -=people-50;
        }
        if (people>40){
            historyAmount +=(people-40)*750;
            people -=people-40;
        }
        if (people>30){
            historyAmount +=(people-30)*650;
            people -=people-30;
        }
        if (people>20){
            historyAmount +=(people-20)*500;
            people -=people-20;
        }
        if (people>10){
            historyAmount +=(people-10)*400;
            people -=people-10;
        }
        if (people <=10 ){
            historyAmount +=people*300;
        }
        BigDecimal i = BigDecimal.valueOf(truePeople);
        BigDecimal j = BigDecimal.valueOf(amount);
        BigDecimal result =BigDecimal.valueOf(historyAmount.longValue());
        BigDecimal data1 = j.divide(i, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal data2 = data1.divide(BigDecimal.valueOf(10500), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal data3 = data2.multiply(result);
        BigDecimal data4 = data3.add(BigDecimal.valueOf(3800));
        return data4;
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
            // 去除多余的空格
            string = string.trim();
            String[] strings = string.split("\n");

            return Arrays.asList(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/test")
    public void send(){
        //将对象装入HttpEntity中
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("text", "测试");
        map.add("desp", "难搞");
        String url =ConstantData.URL.get(0);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, map,String.class);
        System.out.println(stringResponseEntity);
    }

}
