package com.yocan.push.yuque.Constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConstantData {

    public static final List<String> URL = Arrays.asList(
        //注释隐藏掉key
    );


    public static final HashMap<String,Integer> HASHMAP =new HashMap<>();
    public static final HashMap<Integer,String> COMMENT =new HashMap<>();
    static {
        HASHMAP.put("WORDING_COMMENT",2);
        HASHMAP.put("comment_create",3);
        HASHMAP.put("publish",1);
        COMMENT.put(1,"新文章发布-");
        COMMENT.put(2,"新评论发布from-");
        COMMENT.put(3,"新评论发布from-");
        COMMENT.put(0,"null-");
    }


}
