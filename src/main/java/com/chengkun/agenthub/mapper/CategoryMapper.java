package com.chengkun.agenthub.mapper;

import com.chengkun.agenthub.model.dto.CategoryAdminDTO;
import com.chengkun.agenthub.model.dto.CategoryDTO;
import com.chengkun.agenthub.entity.Category;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryDTO> listCategories();

    List<CategoryAdminDTO> listCategoriesAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}
