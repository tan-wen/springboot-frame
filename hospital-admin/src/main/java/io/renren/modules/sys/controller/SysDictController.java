/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.SysDictService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    public final static String DATE_PATTERN = "yyyy-MM-dd";


    /**
     * 字典类型
     */
    public final static String DICT_WEEK_TYPE = "week";

    public final static int DAY_INTERVALS = 7;

    private static final Logger LOGGER = LoggerFactory.getLogger(SysDictController.class);







    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysDictService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id) {
        SysDictEntity dict = sysDictService.selectById(id);

        return R.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDictEntity dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.insert(dict);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDictEntity dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids) {
        sysDictService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 获取指定Type的数据字典
     * @return
     */
    @RequestMapping("/{type}")
    public R genders(@PathVariable String type) {
        List<SysDictEntity> entities = sysDictService.getByType(type);
        return R.ok().put(type, entities);
    }



    /**
     * 获取日期和时间关系
     * @param startDate
     * @return
     */

    @RequestMapping("/getRecentDateAndWeek")
    public R getRecentWeek(@RequestParam("startDate") String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date sDate = new Date();
        Calendar calendar = new GregorianCalendar();
        ArrayList<Map<Object,Object>> nextDaysList = Lists.newArrayList();
        if(StringUtils.isBlank(startDate)){
            try {
                startDate = sdf.format(calendar.getTime());
            }catch(Exception e){
                LOGGER.error("获取当前时间出错,"+e);
            }
        }
        try{
            sDate = sdf.parse(startDate);
            Date firstDayOfWeek = DateUtils.getTimesWeekmorning(sDate);
            nextDaysList =  DateUtils.getDates(firstDayOfWeek,DAY_INTERVALS);
            //获取weekId信息
            for (Map<Object,Object> nextDay : nextDaysList) {
                Map<String, Object> param = Maps.newHashMap();
                param.put("type",DICT_WEEK_TYPE);
                param.put("order_num",nextDay.get("weekOrderNum"));
                List<SysDictEntity> entities = sysDictService.selectByMap(param);
                if(entities!=null && entities.size()>0){
                    Long weekId =entities.get(0).getId();
                    String weekDay = entities.get(0).getValue();
                    nextDay.put("id",weekId);
                    nextDay.put("weekDay",weekDay);
                }
            }

        }catch(Exception e){
            LOGGER.error("日期string装为date或获取日期数组失败,"+e);
        }
        LOGGER.info("关联weekId查询后nextDaysList：{}", new Gson().toJson(nextDaysList));
        //根据传入的 startDate  查询对应的weekId

        Map<Object,Object> currentDay =  DateUtils.getNextDate(sDate,0);
        Map<String, Object> selectDayparam = Maps.newHashMap();
        selectDayparam.put("type",DICT_WEEK_TYPE);
        selectDayparam.put("order_num",currentDay.get("weekOrderNum"));
        List<SysDictEntity> currentDayEntities = sysDictService.selectByMap(selectDayparam);
        if(currentDayEntities!=null && currentDayEntities.size()>0){
            Long selectDayWeekId =currentDayEntities.get(0).getId();
            return R.ok().put("nextDaysList", nextDaysList).put("selectDayWeekId",selectDayWeekId);
        }else{
            return R.ok().put("nextDaysList", nextDaysList);
        }

    }

}
