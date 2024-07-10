package com.chengkun.agenthub.mapper;

import com.chengkun.agenthub.model.dto.TagAdminDTO;
import com.chengkun.agenthub.model.dto.TagDTO;
import com.chengkun.agenthub.entity.Tag;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<TagDTO> listTags();

    List<TagDTO> listTopTenTags();

    List<String> listTagNamesByArticleId(Integer articleId);

    List<TagAdminDTO> listTagsAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}
