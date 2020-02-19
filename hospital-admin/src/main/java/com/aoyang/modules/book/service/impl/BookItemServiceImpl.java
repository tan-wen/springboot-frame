package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.dao.BookItemDao;
import com.aoyang.modules.book.dao.BookQuotaDetailDao;
import com.aoyang.modules.book.dao.BookQuotaTempDao;
import com.aoyang.modules.book.dto.BookItem;
import com.aoyang.modules.book.dto.BookRecord;
import com.aoyang.modules.book.dto.DetectionQuota;
import com.aoyang.modules.book.dto.DetectionQuotaOccupied;
import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.aoyang.modules.book.entity.BookQuotaTempEntity;
import com.aoyang.modules.book.service.BookItemService;
import com.aoyang.modules.book.service.TreatmentCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("baseBookItemService")
public class BookItemServiceImpl extends ServiceImpl<BookItemDao, BookItemEntity> implements BookItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookItemServiceImpl.class);

    private static final String CONNECTOR = "-";

    @Autowired
    private BookQuotaDetailDao bookQuotaDetailDao;

    @Autowired
    private BookQuotaTempDao bookQuotaTempDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<BookItemEntity> page = this.selectPage(
                new Query<BookItemEntity>(params).getPage(),
                new EntityWrapper<BookItemEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)

        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<BaseMemberEntity> page = new Query<BaseMemberEntity>(params).getPage();
        List<BaseMemberEntity> baseMemberEntities = baseMapper.listPage(page, name);
        return new PageUtils(page.setRecords(baseMemberEntities));
    }

    @Override
    public DetectionQuota getQuota(Long detectionId, Long weekId, String[] ordIdList, String category) {
        Map<String, Object> paramIn = Maps.newHashMap();
        paramIn.put("detectionId", detectionId);
        paramIn.put("weekId", weekId);
        paramIn.put("category", category);
        paramIn.put("ordIdList", ordIdList);
        LOGGER.info("查询号源入参param:{}", paramIn);
        DetectionQuota quota = baseMapper.getQuota(paramIn);
        //获取预约项目的该工作日的所有配额，建立映射集
        Map<String, Object> param = Maps.newHashMap();
        param.put("week_id", weekId);

        if (quota != null) {
            for (BookItem item : quota.getBookItems()) {
                Map<String, Integer> map = Maps.newHashMap();
                Map<String, Long> idMap = Maps.newHashMap();
                param.put("book_item_id", item.getBookItemId());
                List<BookQuotaDetailEntity> details = bookQuotaDetailDao.selectByMap(param);
                for (BookQuotaDetailEntity detail : details) {
                    String key = detail.getWeekId() + CONNECTOR + detail.getTimeSliceId() + CONNECTOR + detail.getQuotaCategoryId();
                    map.put(key, detail.getMax());
                    idMap.put(key, detail.getId());
                }
                item.setMap(map);
                item.setIdMap(idMap);
            }
            LOGGER.info("医技科室号源配置，quota={}", new Gson().toJson(quota));
            return quota;
        } else {
            return null;
        }
    }

    @Override
    public DetectionQuota getQuotaTemp(Long detectionId, Long weekId, String[] ordIdList, String category, String checkedDate,String depId) {
        //查询传入的具体的类别

        Map<String, Object> localParamIn = Maps.newHashMap();
        localParamIn.put("detectionId", detectionId);
        localParamIn.put("weekId", weekId);
        localParamIn.put("category", category);
        localParamIn.put("ordIdList", ordIdList);
        localParamIn.put("depId", depId);
        LOGGER.info("查询局部分类的号源入参param:{}", new Gson().toJson(localParamIn));
        DetectionQuota quota = baseMapper.getLocalQuota(localParamIn);
        if(null == quota){
            LOGGER.info("未配置局部分类的号源");
            Map<String, Object> paramIn = Maps.newHashMap();
            paramIn.put("detectionId", detectionId);
            paramIn.put("weekId", weekId);
            paramIn.put("category", category);
            paramIn.put("ordIdList", ordIdList);
            LOGGER.info("查询号源入参param:{}", new Gson().toJson(paramIn));
            quota = baseMapper.getQuota(paramIn);

        }
        LOGGER.info("查询quota:{}", new Gson().toJson(quota));
        if (quota != null) {
            //获取预约项目的该工作日的所有配额，建立映射集
            Map<String, Object> param = Maps.newHashMap();
            Map<String, Object> paramTemp = Maps.newHashMap();
            param.put("week_id", weekId);
            paramTemp.put("week_id", weekId);
            paramTemp.put("full_time", checkedDate);
            for (BookItem item : quota.getBookItems()) {
                Map<String, Integer> map = Maps.newHashMap();
                Map<String, Long> idMap = Maps.newHashMap();
                param.put("book_item_id", item.getBookItemId());
                paramTemp.put("book_item_id", item.getBookItemId());
                List<BookQuotaDetailEntity> details = bookQuotaDetailDao.selectByMap(param);
                for (BookQuotaDetailEntity detail : details) {
                    String key = detail.getWeekId() + CONNECTOR + detail.getTimeSliceId() + CONNECTOR + detail.getQuotaCategoryId();
                    map.put(key, detail.getMax());
                    idMap.put(key, detail.getId());
                }
                List<BookQuotaTempEntity> detailTemps = bookQuotaTempDao.selectByMap(paramTemp);
                for (BookQuotaTempEntity detail : detailTemps) {
                    String key = detail.getWeekId() + CONNECTOR + detail.getTimeSliceId() + CONNECTOR + detail.getQuotaCategoryId();
                    map.put(key, detail.getMax());
                    idMap.put(key, detail.getId());
                }
                item.setMap(map);
                item.setIdMap(idMap);

                Map<String, Integer> occupiedMap = Maps.newHashMap();
                //根据  weekId 、日期book_time、book_item_id   category ：'OUTPATIENT'查询已预约的号源
                List<DetectionQuotaOccupied> detectionQuotaOccupied = getOccupiedQuota(weekId, checkedDate, item.getBookItemId(), category);
                for (DetectionQuotaOccupied detectionQuotaOcupiedItem : detectionQuotaOccupied) {
                    BookRecord bookRecord = detectionQuotaOcupiedItem.getBookRecord();
                    String key = bookRecord.getWeekId() + CONNECTOR + bookRecord.getTimeSlice().getTimeSliceId()+ CONNECTOR + detectionQuotaOcupiedItem.getQuotaCategoryId();
                    occupiedMap.put(key, detectionQuotaOcupiedItem.getOccupiedNum());
                }
                item.setOccupiedMap(occupiedMap);
            }
            LOGGER.info("医技科室号源详情临时配置组装数据，quota={}", new Gson().toJson(quota));
            return quota;
        } else {
            return null;
        }
    }


    @Override
    public List<DetectionQuotaOccupied> getOccupiedQuota(Long weekId, String checkedDate, Long bookItemId, String category) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("weekId", weekId);
        param.put("bookTime", checkedDate);
        param.put("bookItemId", bookItemId);
        param.put("category", category);
        return baseMapper.getOccupiedQuota(param);
    }


    @Override
    public Integer getOccupiedQuotaNum(Long weekId, String bookTime, Long bookItemId, String category, Long timeSliceId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("weekId", weekId);
        param.put("bookTime", bookTime);
        param.put("bookItemId", bookItemId);
        param.put("category", TreatmentCategory.INPATIENT.name());
        param.put("timeSliceId", timeSliceId);
        Integer occupiedQuotaNum = 0;
        List<DetectionQuotaOccupied> detectionQuotaOccupieds = baseMapper.getOccupiedQuotaNum(param);
        if (detectionQuotaOccupieds != null&& !detectionQuotaOccupieds.isEmpty()) {
            occupiedQuotaNum = detectionQuotaOccupieds.get(0).getOccupiedNum();
        }
        return occupiedQuotaNum;

    }

    @Override
    public List<BookItemEntity> getBookItemListByDetectionDeptId(Map<String, Object> params) {
        return baseMapper.getBookItemListByDetectionDeptId(params);
    }

}
