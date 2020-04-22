# PushFromYq

```text 

listening to all YUQUE update information from PushBear push 

YUEQUE => WECHAT

```

- PushFromYq是什么
    - 对接语雀api进行各种更新的push
    - push 目标当然是wechat
    - 使用的push工具为[【server酱】](http://sc.ftqq.com/3.version)


- 20-03-30更新
    - 一对多需要提供认证过的服务号
    - 由于没有合适的服务号，所以调整策略
        -  维护一个地址list进行通知push
        -  已经部署生效了。
        
- 20-04-19更新
    - 评论发布时看不到具体内容
    - 新增根据发布类型，从不同途径获取内容
        -  支持通知新增评论以及新增行内评论
        -  已经部署生效了。
        
- 20-04-22更新
    - 新增可以自动配置，新人加入再也不用手动维护
    - 提供http接口，可直接操作更新推送地址库
    -  已经部署生效了。

- 后续待完善