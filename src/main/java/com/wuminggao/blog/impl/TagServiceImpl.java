package com.wuminggao.blog.impl;

import com.wuminggao.blog.entity.Tag;
import com.wuminggao.blog.mapper.TagMapper;
import com.wuminggao.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 根据以逗号'，'分割的id字符串，获取对应id的tag集合
     * @param tagIds
     * @return
     */
    @Override
    public List<Tag> getTagListByString(String tagIds) {
        List<Long> idList = new ArrayList<>(); // 保存id的List
        if (tagIds!=null && !"".equals(tagIds)){
            String[] split = tagIds.split(",");
            for (String idStr:split
                 ) {
                long tagId = Long.parseLong(idStr);
                idList.add(tagId);
            }
        }
        //没有tag属性
        if (idList.size() == 0){
            return null;
        }
        return baseMapper.selectBatchIds(idList); // 批量根据id查询
    }

    @Override
    public List<Tag> getTopList(int size) {
        return baseMapper.selectTopList(size);
    }
}
