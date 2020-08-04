package com.wuminggao.blog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_blog")
@ApiModel(value="Blog对象", description="")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1123131233332L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String firstPicture;

    private String flag = "原创"; // 原创/转载/翻译等标记

    private Integer views;

    private boolean appreciation;

    private boolean shareStatement;

    private boolean commentabled;

    private boolean published;

    private boolean recommend;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Long typeId;

    private Long userId;

    private String description;

    private String tagIds;

    @TableField(exist = false)
    private Type type;

    @TableField(exist = false)
    private User user;

    @TableLogic
    private Integer deleted;

//    @TableField(exist = false)
//    private String tagNames;

    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();

    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();

    // 用查询出来的tags集合来初始化tagIds字段，将其转化为字符串形式 eg: "1,2,5"，用于编辑博客时显示博客的所有tag属性
    public void initTagIdsFromTagList(){
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag: tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }



}
