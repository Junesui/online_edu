package com.zjm.educms.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.commonutils.result.R;
import com.zjm.educms.entity.CmsAdType;
import com.zjm.educms.service.CmsAdTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  前端控制器
 * @author zjm
 * @since 2020-06-18
 */

@Api(description = "推荐位管理")
@RestController
@RequestMapping("/educms/admin-cms/ad-type")
@Slf4j
public class AdTypeController {

    @Autowired
    private CmsAdTypeService adTypeService;

    @ApiOperation("所有推荐类别列表")
    @GetMapping("list")
    public R listAll() {
        List<CmsAdType> list = adTypeService.list(null);
        return R.success().data("items", list);
    }

    @ApiOperation("推荐类别分页列表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit) {

        Page<CmsAdType> pageParam = new Page<>(page, limit);
        IPage<CmsAdType> pageModel = adTypeService.page(pageParam,null);
        List<CmsAdType> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.success().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除推荐类别")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "推荐类别ID", required = true) @PathVariable String id) {

        boolean result = adTypeService.removeById(id);
        if (result) {
            return R.success().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("新增推荐类别")
    @PostMapping("save")
    public R save(@ApiParam(value = "推荐类别对象", required = true) @RequestBody CmsAdType adType) {

        boolean result = adTypeService.save(adType);
        if (result) {
            return R.success().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("更新推荐类别")
    @PutMapping("update")
    public R updateById(@ApiParam(value = "讲师推荐类别", required = true) @RequestBody CmsAdType adType) {
        boolean result = adTypeService.updateById(adType);
        if (result) {
            return R.success().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id获取推荐类别信息")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "推荐类别ID", required = true) @PathVariable String id) {
        CmsAdType adType = adTypeService.getById(id);
        if (adType != null) {
            return R.success().data("item", adType);
        } else {
            return R.error().message("数据不存在");
        }
    }
}

