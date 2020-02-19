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

package io.renren.common.utils;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);



    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return  返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }


    /**
     *
     * @param dateStr : 日期格式为  26/01/1974
     * @return ： str : 返回的日期格式为  yyyy-MM-dd
     */

    public static Date formatDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parseData = sdf.parse(dateStr);
            String date = sdf2.format(parseData);
            return sdf2.parse(date);

        } catch (Exception e) {
            LOGGER.error("{}日期格式由 dd/MM/yy 转为 yyyy-MM-dd失败：{}", dateStr,e);
        }
        return null;
    }


    /**
     * 获取传入日期所在周的第一天
     * @param date
     * @return
     * public final static String DATE_PATTERN = "yyyy-MM-dd";
     */
    public static Date getTimesWeekmorning(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (1 == cal.get(Calendar.DAY_OF_WEEK)){//判断当前日期是否为周末，因为周末是本周第一天，如果不向后推迟一天的到的将是下周一的零点，而不是本周周一零点
            cal.add(Calendar.DATE, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date firstDayOfWeek = cal.getTime();
        return firstDayOfWeek;
    }





    public static ArrayList<Map<Object,Object>> getDates(Date date, int intervals) throws Exception{
        ArrayList<Map<Object,Object>> nextDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            nextDaysList.add(getNextDate(date,i));
        }
        LOGGER.info("nextDaysList：{}", new Gson().toJson(nextDaysList));

        return nextDaysList;
    }



    /**
     * 获取将来第几天的日期
     *
     * @param past
     * @return
     */
    public static  Map<Object,Object> getNextDate(Date  date,int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);*/
        int weekOrderNum = getWeekday(today);
        Map<Object,Object> dateWeekMap = new HashMap<>();
        dateWeekMap.put("date",today);
        dateWeekMap.put("weekOrderNum",weekOrderNum);
        LOGGER.info("dateWeekMap：{}", new Gson().toJson(dateWeekMap));
        return dateWeekMap;
    }


    /**
     *
     * @param date 传入的日期
     * @return  返回对应的sys_dic中性别的顺序值
     */
    public static int getWeekday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return Calendar.MONDAY-1;
            case Calendar.TUESDAY:
                return Calendar.TUESDAY-1;
            case Calendar.WEDNESDAY:
                return Calendar.WEDNESDAY-1;
            case Calendar.THURSDAY:
                return Calendar.THURSDAY-1;
            case Calendar.FRIDAY:
                return Calendar.FRIDAY-1;
            case Calendar.SATURDAY:
                return Calendar.SATURDAY-1;
            case Calendar.SUNDAY:
                return Calendar.SUNDAY+7-1;
            default:
                return -1;
        }
    }










}
