package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.base.dao.BaseDepDao;
import com.aoyang.modules.base.dao.BaseInpatientAreaDao;
import com.aoyang.modules.base.entity.BaseDepEntity;
import com.aoyang.modules.base.entity.BaseInpatientAreaEntity;
import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.base.service.BaseDepService;
import com.aoyang.modules.base.service.BaseMemberService;
import com.aoyang.modules.book.dao.BookQuotaDetailDao;
import com.aoyang.modules.book.dao.BookQuotaTempDao;
import com.aoyang.modules.book.dto.BookApp;
import com.aoyang.modules.book.dto.BookPatientDetail;
import com.aoyang.modules.book.dto.Treatment;
import com.aoyang.modules.book.entity.*;
import com.aoyang.modules.book.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("bookQuotaOccupiedService")
public class BookQuotaOccupiedServiceImpl extends ServiceImpl<BookQuotaDetailDao, BookQuotaDetailEntity> implements BookQuotaOccupiedService {


    private static final Logger LOGGER = LoggerFactory.getLogger(BookQuotaOccupiedServiceImpl.class);

    @Autowired
    private BookPatientDetailServiceImpl bookPatientDetailService;

    @Autowired
    private BaseMemberService baseMemberService;

    @Autowired
    private BookTreatmentService bookTreatmentService;

    @Autowired
    private BookRecordService bookRecordService;

    @Autowired
    private BookApplicationService bookApplicationService;

    @Autowired
    private BaseDepService baseDepService;

    @Autowired
    private BaseDepDao baseDepDao;


    @Autowired
    private BaseInpatientAreaDao baseInpatientAreaDao;


    @Autowired
    private BookQuotaDetailDao bookQuotaDetailDao;

    @Autowired
    private BookQuotaTempDao bookQuotaTempDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookQuotaDetailEntity> page = this.selectPage(
                new Query<BookQuotaDetailEntity>(params).getPage(),
                new EntityWrapper<BookQuotaDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BookQuotaDetailEntity> selectByDectionAndWeek(Long detectionId, Long weekId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("detectionId", detectionId);
        param.put("weekId", weekId);
        return baseMapper.selectByDectionAndWeek(param);
    }

    @Override
    public void save(String startDate, String adminNo, Long bookItemId, String bookTime, Long weekId, Long detectionId, Long timeSliceId, String payCategory, String category, String wardId, String depId) {

        BookPatientDetail bookPatientDetail = bookPatientDetailService.getPatientDetail(adminNo);
        Treatment treatment = bookPatientDetail.getTreatment();
        Long memberId = treatment.getId();
        Long userId = ShiroUtils.getUserId();
        //当  base_member 中id不存在时，该用户在本系统中不存在
        if (null == memberId) {
            BaseMemberEntity baseMemberEntity = new BaseMemberEntity();
            BeanUtils.copyProperties(treatment, baseMemberEntity);
            baseMemberEntity.setCreateBy(userId);
            baseMemberEntity.setLastModifyBy(userId);
            baseMemberEntity.setDeleted(false);
            baseMemberService.insert(baseMemberEntity);
            memberId = baseMemberEntity.getId();
            LOGGER.info("base_member存表返回的memberId为:{}", memberId);
        }

        Map<String, Object> param = Maps.newHashMap();
        param.put("adm_no", adminNo);
        List<BookTreatmentEntity> bookTreatmentEntities = bookTreatmentService.selectByMap(param);
        Long treatmentId = 0L;
        if (null == bookTreatmentEntities || bookTreatmentEntities.isEmpty()) {
            // 存储就诊记录表 book_treatment
            BookTreatmentEntity bookTreatment = new BookTreatmentEntity();
            bookTreatment.setAdmNo(adminNo);
            bookTreatment.setMemberId(memberId);
            String payCategoryName = PayCategory.getNameByDesc(payCategory);
            bookTreatment.setPayCategory(payCategoryName);
            bookTreatment.setTreatmentCategory(category);
            bookTreatment.setCreateBy(userId);
            bookTreatment.setLastModifyBy(userId);
            if (null != depId && !StringUtils.isEmpty(depId)) {
                BaseDepEntity baseDepEntity=baseDepDao.getHisId(depId);
                LOGGER.info("获取base_dep主键,{}",baseDepEntity.getId());
                bookTreatment.setDepId(baseDepEntity.getId());
            }
            if (null != wardId && !StringUtils.isEmpty(wardId)) {
                BaseInpatientAreaEntity baseInpatientAreaEntity=baseInpatientAreaDao.getHisId(wardId);
                LOGGER.info("获取 base_inpatient_area 主键,{}",baseInpatientAreaEntity.getId());
                bookTreatment.setInpatientAreaId(baseInpatientAreaEntity.getId());
            }

            bookTreatmentService.insert(bookTreatment);
            treatmentId = bookTreatment.getId();
            LOGGER.info("book_treatment存表返回的treatmentId为:{}", treatmentId);

        } else {
            BookTreatmentEntity exitBookTreatment = bookTreatmentEntities.get(0);
            treatmentId = exitBookTreatment.getId();
            if (null != depId && !StringUtils.isEmpty(depId)) {
                BaseDepEntity baseDepEntity=baseDepDao.getHisId(depId);
                LOGGER.info("获取base_dep主键,{}",baseDepEntity.getId());
                exitBookTreatment.setDepId(baseDepEntity.getId());
            }
            if (null != wardId && !StringUtils.isEmpty(wardId)) {
                BaseInpatientAreaEntity baseInpatientAreaEntity=baseInpatientAreaDao.getHisId(wardId);
                LOGGER.info("获取 base_inpatient_area 主键,{}",baseInpatientAreaEntity.getId());
                exitBookTreatment.setInpatientAreaId(baseInpatientAreaEntity.getId());
            }



            bookTreatmentService.updateById(exitBookTreatment);
        }

        //存储 book_application 表 (若有多条appdata与 预约的项目匹配则插入多条 appdata记录)
        List<BookApp> bookApps = bookPatientDetail.getBookApps();


        for (BookApp bookApp : bookApps) {
            BookApplicationEntity bookApplicationEntity=new BookApplicationEntity();
            BeanUtils.copyProperties(bookApp, bookApplicationEntity);
            String appNo = bookApplicationEntity.getAppNo();
            //查找appNo对应的记录是否存在
            Map<String, Object> aparam = Maps.newHashMap();
            aparam.put("app_no", appNo);
            List<BookApplicationEntity> exitApplicationEntities = bookApplicationService.selectByMap(aparam);
            if (null == exitApplicationEntities || exitApplicationEntities.isEmpty()) {
                // 保存 book_application表
                bookApplicationEntity.setTreatmentId(treatmentId);
                bookApplicationEntity.setCreateBy(userId);
                bookApplicationEntity.setLastModifyBy(userId);
                bookApplicationService.insert(bookApplicationEntity);
            } else {
                BookApplicationEntity exitApplication = exitApplicationEntities.get(0);
                Long appId = exitApplication.getId();
                BeanUtils.copyProperties(exitApplication, bookApplicationEntity);
                exitApplication.setId(appId);
                exitApplication.setTreatmentId(treatmentId);
                exitApplication.setLastModifyBy(ShiroUtils.getUserId());
                bookApplicationService.updateById(exitApplication);
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBookRecord(String startDate, String adminNo, Long bookItemId, String bookTime, Long weekId, Long detectionId, Long timeSliceId) {
        Long userId = ShiroUtils.getUserId();
        //存储预约记录表 book_record
        BookRecordEntity bookRecord = new BookRecordEntity();
        bookRecord.setBookItemId(bookItemId);
        bookRecord.setBookTime(bookTime);
        bookRecord.setWeekId(weekId);
        bookRecord.setTimeSliceId(timeSliceId);
        bookRecord.setCreateBy(userId);
        bookRecord.setLastModifyBy(userId);
        bookRecordService.insert(bookRecord);
        Long bookRecordId = bookRecord.getId();
        LOGGER.info("book_record存表返回的bookRecordId为:{}", bookRecordId);

        // 根据 bookItemId 查找符合条件的 appdata 并更新 book_record_id 字段
        List<BookApplicationEntity> unbindBookApplicationEntities = bookApplicationService.getAppNotBindBookRecord(bookItemId, adminNo);
        if (null != unbindBookApplicationEntities && unbindBookApplicationEntities.size() > 0) {
            for (BookApplicationEntity unbindBookApplicationEntity : unbindBookApplicationEntities) {
                unbindBookApplicationEntity.setBookRecordId(bookRecordId);
                bookApplicationService.updateById(unbindBookApplicationEntity);
                LOGGER.info("unbindBookApplicationEntity" + new Gson().toJson(unbindBookApplicationEntity));
            }

        } else {
            LOGGER.info("book_application绑定book_record,无可绑定的book_application记录,bookItemId;{},adminNo:{}", bookItemId, adminNo);
            throw new RuntimeException();
        }
    }



    @Override
    public Integer getQuotaMax(Long quotaDetailId) {
        BookQuotaTempEntity bookQuotaTempEntity = bookQuotaTempDao.selectById(quotaDetailId);
        Integer quotaMax = 0;
        if(null!=bookQuotaTempEntity){
            quotaMax=bookQuotaTempEntity.getMax();
        }else{
            BookQuotaDetailEntity bookQuotaDetail = bookQuotaDetailDao.selectById(quotaDetailId);
            quotaMax = bookQuotaDetail.getMax();
        }
        return  quotaMax;
    }
}
