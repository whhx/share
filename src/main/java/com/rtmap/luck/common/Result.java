/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.common;

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
    * 店铺LOGO
    */
   private String logoUrl;

   //奖品信息
   /**
    * 奖品等级
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
    * 说明条款
    */
   private String desc;
   
   /**
    * 点赞数
    */
   private Long click;

   
   public String getShopName()
   {
      return shopName;
   }

   public void setShopName(String shopName)
   {
      this.shopName = shopName;
   }

   public String getLogoUrl()
   {
      return logoUrl;
   }

   public void setLogoUrl(String logoUrl)
   {
      this.logoUrl = logoUrl;
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

}
