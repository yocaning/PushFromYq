package com.yocan.push.yuque.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ParamDto {

    private DocDetailSerializer data;
    private String action_type;
    /**
     *  Attributes
     * • id - 文档编号
     * • slug - 文档路径
     * • title - 标题
     * • book_id - 仓库编号，就是 repo_id
     * • book - 仓库信息 <BookSerializer>，就是 repo 信息
     * • user_id - 用户/团队编号
     * • user - 用户/团队信息 <UserSerializer>
     * • format - 描述了正文的格式 [lake , markdown]
     * • body - 正文 Markdown 源代码
     * • body_draft - 草稿 Markdown 源代码
     * • body_html - 转换过后的正文 HTML
     * • body_lake - 语雀 lake 格式的文档内容
     * • creator_id - 文档创建人 User Id
     * • public - 公开级别 [0 - 私密, 1 - 公开]
     * • status - 状态 [0 - 草稿, 1 - 发布]
     * • likes_count - 赞数量
     * • comments_count - 评论数量
     * • content_updated_at - 文档内容更新时间
     * • deleted_at - 删除时间，未删除为 null
     * • created_at - 创建时间
     * • updated_at - 更新时间
     */
            @Data
            public static class DocDetailSerializer {
                private Integer id;
                private String slug;
                private String title;
                private UserSerializer user;
                private Date updated_at;
                private String body;
            }
            /**
             * Attributes
             * • id - 用户编号
             * • type - 类型 [`User`  - 用户, Group - 团队]
             * • login - 用户个人路径
             * • name - 昵称
             * • avatar_url - 头像 URL
             * • created_at - 创建时间
             * • updated_at - 更新时间
             */
            @Data
            public static class UserSerializer {
                private Integer id;
                private String name;
    }



}
