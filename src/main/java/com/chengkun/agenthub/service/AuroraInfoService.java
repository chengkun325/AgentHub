package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.AboutDTO;
import com.chengkun.agenthub.model.dto.AuroraAdminInfoDTO;
import com.chengkun.agenthub.model.dto.AuroraHomeInfoDTO;
import com.chengkun.agenthub.model.dto.WebsiteConfigDTO;
import com.chengkun.agenthub.model.vo.AboutVO;
import com.chengkun.agenthub.model.vo.WebsiteConfigVO;

public interface AuroraInfoService {

    void report();

    AuroraHomeInfoDTO getAuroraHomeInfo();

    AuroraAdminInfoDTO getAuroraAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    WebsiteConfigDTO getWebsiteConfig();

    void updateAbout(AboutVO aboutVO);

    AboutDTO getAbout();

}
