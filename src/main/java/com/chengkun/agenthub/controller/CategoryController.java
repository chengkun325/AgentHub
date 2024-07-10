package com.chengkun.agenthub.controller;

import com.chengkun.agenthub.annotation.OptLog;
import com.chengkun.agenthub.model.dto.CategoryAdminDTO;
import com.chengkun.agenthub.model.dto.CategoryDTO;
import com.chengkun.agenthub.model.dto.CategoryOptionDTO;
import com.chengkun.agenthub.service.CategoryService;
import com.chengkun.agenthub.model.vo.CategoryVO;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.chengkun.agenthub.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.chengkun.agenthub.constant.OptTypeConstant.*;

@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取所有分类")
    @GetMapping("/categories/all")
    public ResultVO<List<CategoryDTO>> listCategories() {
        return ResultVO.ok(categoryService.listCategories());
    }

    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public ResultVO<PageResultDTO<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO conditionVO) {
        return ResultVO.ok(categoryService.listCategoriesAdmin(conditionVO));
    }

    @ApiOperation(value = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public ResultVO<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        return ResultVO.ok(categoryService.listCategoriesBySearch(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除分类")
    @DeleteMapping("/admin/categories")
    public ResultVO<?> deleteCategories(@RequestBody List<Integer> categoryIds) {
        categoryService.deleteCategories(categoryIds);
        return ResultVO.ok();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/admin/categories")
    public ResultVO<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return ResultVO.ok();
    }


}
