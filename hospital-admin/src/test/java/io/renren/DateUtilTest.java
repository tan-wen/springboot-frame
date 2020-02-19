package io.renren;

import io.renren.common.utils.DateUtil;
import io.renren.common.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilTest.class);

    @Test
    public void getPatientListTest() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parseData = sdf.parse("26/01/1974");
            String str = sdf2.format(parseData);
            System.out.println("转换后的日期为" + str);
        } catch (Exception e) {
            System.out.println("格式转换异常");
        }


    }

    @Test
    public void getDatesTest() {

       /* SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");*/
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf2.parse("2019-11-19");
            DateUtils.getDates(date,7);
        }catch (Exception e){
            LOGGER.error("查询最近7天出错e:{}",e);
        }


    }

    @Test
    public void transfer() throws ParseException {
        Date date=new Date();
        LocalDate a1= DateUtil.date2LocalDate(date);
        Date a2=DateUtil.localDate2Date(a1);
        System.out.println(a1);
        System.out.println(a2);
    }


}
