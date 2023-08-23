package com.gallery.manage.common.util;

import com.gallery.manage.common.constants.PictureDetailConstant;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.*;
import com.gallery.manage.common.service.PictureDetailService;
import com.light.config.util.ApplicationContextUtil;
import com.light.core.model.TempFileInfo;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PictureDetailUtil {

    public static Map<Long, PictureDetail> getBankLogoMapByBankIdList(List<Long> list) {
        return getMap(list, aLong -> aLong, PictureDetailConstant.BANK_LOGO.getKey());
    }


    public static Long generateId(byte type, Long relatedId, int sort) {
        return getId(type, relatedId, sort);
    }


    private static Long getId(Object... values) {
        String str = Arrays.stream(values).map(o -> o.toString()).collect(Collectors.joining());
        return Long.valueOf(str);
    }


    public static Map<Long, PictureDetail> getByIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new HashMap<>();
        }
        idList = idList.stream().distinct().collect(Collectors.toList());
        PictureDetailService pictureDetailService = ApplicationContextUtil.getBean(PictureDetailService.class);
        Collection<PictureDetail> pictureDetailList = pictureDetailService.listByIds(idList);
        if (CollectionUtils.isEmpty(pictureDetailList)) {
            return new HashMap<>();
        }
        return pictureDetailList.stream().collect(Collectors.toMap(PictureDetail::getRelatedId, pictureDetail -> pictureDetail));
    }


    public static PictureDetail getById(Long pictureDetailId) {
        PictureDetailService pictureDetailService = ApplicationContextUtil.getBean(PictureDetailService.class);
        if (pictureDetailService == null) {
            return null;
        }
        return pictureDetailService.getById(pictureDetailId);
    }

    public static PictureDetail generatePictureDetail(Long pictureDetailId, Long relatedId, TempFileInfo tempFileInfo, Byte type, Integer sort) {
        return new PictureDetail().setId(pictureDetailId).setAll(tempFileInfo.getPath(), tempFileInfo.getWidth(), tempFileInfo.getHeight())
                .setRelatedId(relatedId).setType(type).setStatus(ProjectConstant.COMMON_STATUS_AVAILABLE).setSort(sort);
    }

    public static PictureDetail generatePictureDetail(Long pictureDetailId, Long relatedId, TempFileInfo tempFileInfo, Byte type) {
        return generatePictureDetail(pictureDetailId, relatedId, tempFileInfo, type, 1);
    }

    public static List<PictureDetail> generatePictureDetailList(Long relatedId, List<TempFileInfo> tmpFileInfoList, Byte type) {
        if (CollectionUtils.isEmpty(tmpFileInfoList)) {
            return Collections.emptyList();
        }
        List<PictureDetail> pictureDetailList = new ArrayList<>();
        for (int i = 0; i < tmpFileInfoList.size(); i++) {
            TempFileInfo tmpFileInfo = tmpFileInfoList.get(i);
            int sort = i + 1;
            Long pictureDetailId = PictureDetailUtil.generateId(type, relatedId, sort);
            PictureDetail pictureDetail = PictureDetailUtil.generatePictureDetail(pictureDetailId, relatedId, tmpFileInfo, type, sort);
            pictureDetailList.add(pictureDetail);
        }
        return pictureDetailList;
    }

    public static <T> Map<Long, PictureDetail> getMap(List<T> list, Function<T, Long> function, Byte type) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        List<Long> idList = list.stream().map(t -> generateId(type, function.apply(t), 1)).distinct().collect(Collectors.toList());
        return getByIds(idList);
    }

}

