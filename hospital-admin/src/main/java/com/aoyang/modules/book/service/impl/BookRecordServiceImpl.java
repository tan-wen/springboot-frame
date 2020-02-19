package com.aoyang.modules.book.service.impl;


import com.aoyang.modules.book.dao.BookRecordDao;
import com.aoyang.modules.book.dto.BookApp;
import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.dto.BookRecordItem;
import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.entity.BookRecordEntity;
import com.aoyang.modules.book.service.BookRecordService;
import com.aoyang.modules.book.service.TreatmentCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("bookRecordService")
public class BookRecordServiceImpl extends ServiceImpl<BookRecordDao, BookRecordEntity> implements BookRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRecordServiceImpl.class);

    /**
     * 空格
     */
    private static final String SPACE =  " ";

    private static final String CONNECTOR =  "-";


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookRecordEntity> page = this.selectPage(
                new Query<BookRecordEntity>(params).getPage(),
                new EntityWrapper<BookRecordEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public BookItemEntity getBookItemById(Long recordId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("recordId", recordId);
        return baseMapper.getBookItemById(param);
    }

    @Override
    public BookRecordEntity getRecordTimeById(Long recordId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("recordId", recordId);
        return baseMapper.getRecordTimeById(param);
    }

    @Override
    public List<BookRecordItem> getBookRecordItem(BookRecordDetail bookRecordDetail) {
        List<BookRecordItem> bookRecordItemList = Lists.newArrayList();
        if (null!= bookRecordDetail){
            String desc = TreatmentCategory.getDescByName(bookRecordDetail.getTreatmentCategory());
            List<BookApp> bookApps =bookRecordDetail.getBookApps();
            LOGGER.info("预约根据bookRecordDetail查询组装预约记录bookApplicationEntities:{}", new Gson().toJson(bookApps));

            if(null!=bookApps&& !bookApps.isEmpty()){
                for(BookApp bookApp:bookApps){
                    try{
                        BookRecordItem bookRecordItem = new BookRecordItem();
                        bookRecordItem.setTreatmentCategory(desc);
                        bookRecordItem.setBookItemName(bookApp.getBookItemName());
                        bookRecordItem.setBookRecordId(bookApp.getBookRecord().getId());
                        String date = DateUtils.format(bookApp.getAppDate(), DateUtils.DATE_PATTERN);
                        bookRecordItem.setAppDate(date);
                        String bookDate = bookApp.getBookRecord().getBookTime();
                        String startTime = bookApp.getStartTime().substring(0,5);
                        String endTime = bookApp.getEndTime().substring(0,5);
                        String bookTime =bookDate+SPACE+startTime+CONNECTOR+endTime;
                        bookRecordItem.setBookTime(bookTime);
                        bookRecordItemList.add(bookRecordItem);
                    }catch(Exception e){
                        LOGGER.error("未查询到对应的某条预约记录",e);
                    }
                }
            }
        }
        LOGGER.info("预约根据bookRecordDetail查询组装预约记录bookRecordItemList:{}", new Gson().toJson(bookRecordItemList));
        return bookRecordItemList;
    }




}
