package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.CategoryAdminDTO;
import com.chengkun.agenthub.model.dto.CategoryDTO;
import com.chengkun.agenthub.model.dto.CategoryOptionDTO;
import com.chengkun.agenthub.entity.Category;
import com.chengkun.agenthub.model.vo.CategoryVO;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();

    PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO);

    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

    void deleteCategories(List<Integer> categoryIds);

    void saveOrUpdateCategory(CategoryVO categoryVO);

}
