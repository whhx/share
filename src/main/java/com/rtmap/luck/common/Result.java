/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.common;

import java.util.List;

/**
 * 
 * Result. 返回结果
 * 
 * @author Jeffrey Jia
 * @since 0.1
 */
public class Result
{

   /**
    * 店铺名称
    */
   private String shopName;
   /**
    * 商店名称
    */
   private String marketName;
   /**
    * 店铺LOGO
    */
   private String shopLogoUrl;
   
   
   /**
    * 商场LOGO
    */
   private String marketLogoUrl;

   //奖品信息
   /**
    * 1 商场，  2 商户
    */
   private String level;

   /**
    * 主要信息
    */
   private String main;

   /**
    * 辅助信息
    */
   private String extend;

   /**
    * 有效期
    */
   private String startTime;

   /**
    * 有效期
    */
   private String endTime;

   /**
    * 核销地点
    */
   private String position;
   /**
    * 奖品图片
    */
   private String imgUrl;

   /**
    * 模版url
    */
   private String template;

   /**
    * 奖品数量
    */
   private Integer num;

   /**
    * 奖品已经发放数量
    */
   private Integer issue;

   /**
    * 券类型
    */
   private Integer coupon;

   /**
    * 说明条款(奖券说明)
    */
   private String desc;
   
   /**
    * 点赞数
    */
   private Long click;
   
   /**
    * 是否可以分享  0：不可以     1：可以。默认0
    */
   private Integer shareAble;
   
   
   /**
    * 活动类型：0 抽奖，1 发卷
    */
   private Integer channel;
   
   /**
    * 宣传页判断   0 宣传页，1 可抽取页面
    */
   private Integer brochur;
   
   
   /**
    * 活动说明（抽奖活动中的说明）
    */
   private String description;
   
   /**
    * 活动名称
    */
   private String activityName;
   
   /**
    * 活动ID
    */
   private Integer activityId;
   
   /**
    * 奖券名称
    */
   private String prizeName;
   
   /**
    * 商场地址
    */
   private String address;
   
   
   /**
    * 最近更新的十个奖券名称列表
    */
   private List<String> last;
   
   /**
    * 是否是APP和PC端（ 0、不是默认app发券活动；
                     1、是系统默认（APP专用,全场）；
                     2、是系统默认（APP专用店内发券）。
                                                   默认0）
    */
   private Integer isDefault;
   
   /**
    * 返回的券数
    */
   private Integer haveCount;
   
   public String getShopName()
   {
      return shopName;
   }

   public void setShopName(String shopName)
   {
      this.shopName = shopName;
   }

   public String getLevel()
   {
      return level;
   }

   public void setLevel(String level)
   {
      this.level = level;
   }

   public String getMain()
   {
      return main;
   }

   public void setMain(String main)
   {
      this.main = main;
   }

   public String getExtend()
   {
      return extend;
   }

   public void setExtend(String extend)
   {
      this.extend = extend;
   }

   public String getStartTime()
   {
      return startTime;
   }

   public void setStartTime(String startTime)
   {
      this.startTime = startTime;
   }

   public String getEndTime()
   {
      return endTime;
   }

   public void setEndTime(String endTime)
   {
      this.endTime = endTime;
   }

   public String getPosition()
   {
      return position;
   }

   public void setPosition(String position)
   {
      this.position = position;
   }

   public String getImgUrl()
   {
      return imgUrl;
   }

   public void setImgUrl(String imgUrl)
   {
      this.imgUrl = imgUrl;
   }

   public String getTemplate()
   {
      return template;
   }

   public void setTemplate(String template)
   {
      this.template = template;
   }

   public Integer getNum()
   {
      return num;
   }

   public void setNum(Integer num)
   {
      this.num = num;
   }

   public Integer getIssue()
   {
      return issue;
   }

   public void setIssue(Integer issue)
   {
      this.issue = issue;
   }

   public Integer getCoupon()
   {
      return coupon;
   }

   public void setCoupon(Integer coupon)
   {
      this.coupon = coupon;
   }

   public String getDesc()
   {
      return desc;
   }

   public void setDesc(String desc)
   {
      this.desc = desc;
   }

   public Long getClick()
   {
      return click;
   }

   public void setClick(Long click)
   {
      this.click = click;
   }

   public Integer getShareAble()
   {
      return shareAble;
   }

   public void setShareAble(Integer shareAble)
   {
      this.shareAble = shareAble;
   }

   public Integer getChannel()
   {
      return channel;
   }

   public void setChannel(Integer channel)
   {
      this.channel = channel;
   }

   public Integer getBrochur()
   {
      return brochur;
   }

   public void setBrochur(Integer brochur)
   {
      this.brochur = brochur;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getActivityName()
   {
      return activityName;
   }

   public void setActivityName(String activityName)
   {
      this.activityName = activityName;
   }

   public String getPrizeName()
   {
      return prizeName;
   }

   public void setPrizeName(String prizeName)
   {
      this.prizeName = prizeName;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress(String address)
   {
      this.address = address;
   }

   public List<String> getLast()
   {
      return last;
   }

   public void setLast(List<String> last)
   {
      this.last = last;
   }

   public Integer getIsDefault()
   {
      return isDefault;
   }

   public void setIsDefault(Integer isDefault)
   {
      this.isDefault = isDefault;
   }

   public String getShopLogoUrl()
   {
      return shopLogoUrl;
   }

   public void setShopLogoUrl(String shopLogoUrl)
   {
      this.shopLogoUrl = shopLogoUrl;
   }

   public String getMarketLogoUrl()
   {
      return marketLogoUrl;
   }

   public void setMarketLogoUrl(String marketLogoUrl)
   {
      this.marketLogoUrl = marketLogoUrl;
   }

   public Integer getActivityId()
   {
      return activityId;
   }

   public void setActivityId(Integer activityId)
   {
      this.activityId = activityId;
   }

   public Integer getHaveCount()
   {
      return haveCount;
   }

   public void setHaveCount(Integer haveCount)
   {
      this.haveCount = haveCount;
   }

   public String getMarketName()
   {
      return marketName;
   }

   public void setMarketName(String marketName)
   {
      this.marketName = marketName;
   }

}
