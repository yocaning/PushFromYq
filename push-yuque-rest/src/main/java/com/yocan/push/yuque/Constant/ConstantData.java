package com.yocan.push.yuque.Constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConstantData {

    public static final List<String> URL = Arrays.asList(
            "https://sc.ftqq.com/SCU40692Tccb6b97f9f07146f0b5ac909ca1bfcf35c3c6e3cd17d2.send",
            "https://sc.ftqq.com/SCU91391T98d6e181aadd26097d34bd9c4817626a5e7daf64673f5.send",
            "https://sc.ftqq.com/SCU93338T1744c51eeeb7d5b8774cfd6ed039c0345e900faed9559.send",
            "https://sc.ftqq.com/SCU93340T07faef67160c9a92c5386c2fd8844b3d5e9012e795322.send",
            "https://sc.ftqq.com/SCU93341T73a7b6396c4f40fd2b1bc5f28935c7e25e9011a431bd3.send",
            "https://sc.ftqq.com/SCU93344T8e566b262a0fd07ae03a0220e55453e75e9017597f575.send",
            "https://sc.ftqq.com/SCU93348T8b85b4d49cf48ec6f6fcfb17b85df1d65e90182c2b6ee.send",
            "https://sc.ftqq.com/SCU93347Te1965e405e92f70bc10c970b4785100d5e9017d805056.send",

            "https://sc.ftqq.com/SCU93337T26cb3e9b1110090e1626b994239474ce5e902bae0d59b.send",
            "https://sc.ftqq.com/SCU93422Ta62b2530f664eb03d8c3d3d2a0086d0a5e908931127b9.send",

            "https://sc.ftqq.com/SCU91393Taa853aabb79459170e9becb742f8bffe5e908bd7c2816.send",
            "https://sc.ftqq.com/SCU93426Tcf50efbaa8f9cdedffc853d8dd1b31955e908fdeacc71.send",

            "https://sc.ftqq.com/SCU94652T3a976b6593132c54139806a4e41e13455e9ddf616fc90.send"
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
