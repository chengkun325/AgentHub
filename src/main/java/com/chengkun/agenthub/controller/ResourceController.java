package com.chengkun.agenthub.controller;

import com.chengkun.agenthub.annotation.OptLog;
import com.chengkun.agenthub.model.dto.LabelOptionDTO;
import com.chengkun.agenthub.model.dto.ResourceDTO;
import com.chengkun.agenthub.model.vo.ResultVO;
import com.chengkun.agenthub.service.ResourceService;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.vo.ResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.chengkun.agenthub.constant.OptTypeConstant.*;

@Api(tags = "资源模块")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public ResultVO<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return ResultVO.ok(resourceService.listResources(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public ResultVO<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return ResultVO.ok();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public ResultVO<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return ResultVO.ok();
    }

    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public ResultVO<List<LabelOptionDTO>> listResourceOption() {
        return ResultVO.ok(resourceService.listResourceOption());
    }
}
