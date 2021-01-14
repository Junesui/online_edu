package com.zjm.educms.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjm.commonutils.result.R;
import com.zjm.educms.entity.CmsAd;
import com.zjm.educms.entity.vo.AdVo;
import com.zjm.educms.service.CmsAdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页banner表 前端控制器
 * @author zjm
 * @since 2020-06-18
 */

@Api(description = "广告推荐管理")
@RestController
@RequestMapping("/educms/admin-cms/ad")
@Slf4j
public class AdController {
    @Autowired
    private CmsAdService adService;

    @ApiOperation("新增推荐")
    @PostMapping("save")
    public R save(@ApiParam(value = "推荐对象", required = true) @RequestBody CmsAd ad) {

        boolean result = adService.save(ad);
        if (result) {
            return R.success().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("更新推荐")
    @PutMapping("update")
    public R updateById(@ApiParam(value = "讲师推荐", required = true) @RequestBody CmsAd ad) {
        boolean result = adService.updateById(ad);
        if (result) {
            return R.success().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id获取推荐信息")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "推荐ID", required = true) @PathVariable String id) {
        CmsAd ad = adService.getById(id);
        if (ad != null) {
            return R.success().data("item", ad);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("推荐分页列表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit) {

        IPage<AdVo> pageModel = adService.selectPage(page, limit);
        List<AdVo> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.success().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除推荐")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "推荐ID", required = true) @PathVariable String id) {

        //删除图片
        adService.removeAdImageById(id);

        //删除推荐
        boolean result = adService.removeById(id);
        if (result) {
            return R.success().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }
}

