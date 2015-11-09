/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateUtil
{

   /**
    * 得到日期时间字符串 精确到秒
    */
   public static String format(Long time)
   {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return df.format(new Date(time));
   }

   public static String timeLong(Long time)
   {
      Period period = new Period(time.longValue());
      PeriodFormatter formatter = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2).appendHours()
            .appendLiteral(":").appendMinutes().appendLiteral(":").appendSeconds().toFormatter();
      return period.toString(formatter);
   }

}
