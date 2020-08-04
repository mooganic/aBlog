package com.wuminggao.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_blog_tags")
@ApiModel(value="BlogTags对象", description="")
public class BlogTags implements Serializable {

    private static final long serialVersionUID = 523511412132L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long tagId;

    private Long blogId;


}
