package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.LabelOptionDTO;
import com.chengkun.agenthub.model.dto.MenuDTO;
import com.chengkun.agenthub.model.dto.UserMenuDTO;
import com.chengkun.agenthub.entity.Menu;
import com.chengkun.agenthub.model.vo.ConditionVO;
import com.chengkun.agenthub.model.vo.IsHiddenVO;
import com.chengkun.agenthub.model.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuDTO> listMenus(ConditionVO conditionVO);

    void saveOrUpdateMenu(MenuVO menuVO);

    void updateMenuIsHidden(IsHiddenVO isHiddenVO);

    void deleteMenu(Integer menuId);

    List<LabelOptionDTO> listMenuOptions();

    List<UserMenuDTO> listUserMenus();

}
