package com.zjm.educms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjm.educms.entity.CmsAd;
import com.zjm.educms.entity.vo.AdVo;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Helen
 * @since 2020-04-28
 */
public interface CmsAdService extends IService<CmsAd> {

    IPage<AdVo> selectPage(Long page, Long limit);

    boolean removeAdImageById(String id);

    List<CmsAd> selectByAdTypeId(String adTypeId);
}
