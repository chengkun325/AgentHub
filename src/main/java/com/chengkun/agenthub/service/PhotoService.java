package com.chengkun.agenthub.service;

import com.chengkun.agenthub.model.dto.PageResultDTO;
import com.chengkun.agenthub.model.dto.PhotoAdminDTO;
import com.chengkun.agenthub.model.dto.PhotoDTO;
import com.chengkun.agenthub.entity.Photo;
import com.chengkun.agenthub.model.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    PageResultDTO<PhotoAdminDTO> listPhotos(ConditionVO conditionVO);

    void updatePhoto(PhotoInfoVO photoInfoVO);

    void savePhotos(PhotoVO photoVO);

    void updatePhotosAlbum(PhotoVO photoVO);

    void updatePhotoDelete(DeleteVO deleteVO);

    void deletePhotos(List<Integer> photoIds);

    PhotoDTO listPhotosByAlbumId(Integer albumId);

}
