package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.book.dao.BookQuotaCategoryDao;
import com.aoyang.modules.book.entity.BookQuotaCategoryEntity;
import com.aoyang.modules.book.service.BookQuotaCategoryService;
import com.aoyang.modules.book.service.TreatmentCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("bookQuotaCategoryService")
public class BookQuotaCategoryServiceImpl extends ServiceImpl<BookQuotaCategoryDao, BookQuotaCategoryEntity> implements BookQuotaCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookQuotaCategoryEntity> page = this.selectPage(
                new Query<BookQuotaCategoryEntity>(params).getPage(),
                new EntityWrapper<BookQuotaCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPage(Map<String, Object> params) {
        String name=(String) params.get("name");
        Page<BookQuotaCategoryEntity> page = new Query<BookQuotaCategoryEntity>(params).getPage();
        List<BookQuotaCategoryEntity> bookQuotaCategories = baseMapper.listPage(page, name);

        //列表中显示枚举类的描述信息
        bookQuotaCategories.forEach(quotaCategroy ->
                quotaCategroy.setCategory(TreatmentCategory.valueOf(quotaCategroy.getCategory()).getDesc()));
        return new PageUtils(page.setRecords(bookQuotaCategories));
    }

    @Override
    public void generateAutoDepGroup(BaseDetectionDepEntity baseDetectionDep) {
        if (baseDetectionDep == null) {
            return;
        }
        List<BookQuotaCategoryEntity> quotaCategories = Lists.newArrayList();
        // 1、生成门诊
        BookQuotaCategoryEntity out = generateQuotaCategory(baseDetectionDep, TreatmentCategory.OUTPATIENT);
        if (null != out) {
            quotaCategories.add(out);
        }
        //2、生成住院
        BookQuotaCategoryEntity in = generateQuotaCategory(baseDetectionDep, TreatmentCategory.INPATIENT);
        if (null != in) {
            quotaCategories.add(in);
        }
        //3、生成外院
        BookQuotaCategoryEntity outside = generateQuotaCategory(baseDetectionDep, TreatmentCategory.OUTSIDE);
        if (null != outside) {
            quotaCategories.add(outside);
        }
        //4、生成总名额
        BookQuotaCategoryEntity sum = generateQuotaCategory(baseDetectionDep, TreatmentCategory.SUM);
        if (null != sum) {
            quotaCategories.add(sum);
        }
        if (quotaCategories.size() > 0 ) {
            this.insertBatch(quotaCategories);
        }
    }

    private BookQuotaCategoryEntity generateQuotaCategory(BaseDetectionDepEntity baseDetectionDep,
                                                          TreatmentCategory category) {
        if (baseDetectionDep == null) {
            return null;
        }
        Long detectionDepId = baseDetectionDep.getId();
        BookQuotaCategoryEntity entity = new BookQuotaCategoryEntity();
        //查看该医技科室是否已经存在改配额类别
        Object o = this.selectObj(new Wrapper<BookQuotaCategoryEntity>() {
            @Override
            public String getSqlSegment() {
                return     " and detection_dep_id = " + detectionDepId
                        +  " and category = '" + category.name()
                        +  "' and auto = " + true
                        +  " limit 1";
            }
        });
        if ( null != o) {
            return null;
        }
        switch (category) {
            case OUTPATIENT:
                entity.setName(baseDetectionDep.getName() + "-门诊");
                entity.setPosition(1);
                break;
            case INPATIENT:
                entity.setName(baseDetectionDep.getName() + "-住院");
                entity.setPosition(200);
                break;
            case OUTSIDE:
                entity.setName(baseDetectionDep.getName() + "-外院");
                entity.setPosition(300);
                break;
            case SUM:
                entity.setName(baseDetectionDep.getName() + "-总名额");
                entity.setPosition(400);
                break;
        }
        Long curUserId = baseDetectionDep.getLastModifyBy();
        entity.setCategory(category.name());
        entity.setCreateBy(curUserId);
        entity.setLastModifyBy(curUserId);
        entity.setAuto(true);
        entity.setDetectionDepId(detectionDepId);
        return entity;
    }
}
