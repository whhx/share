/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.model;

public class Poi
{

   /**
    * 商户Id
    */
   private Long shopId;

   /**
    * 建筑物ID
    */
   private String buildId;

   /**
    * 商铺楼层
    */
   private String floor;

   /**
    * point点
    */
   private String x;

   private String y;

   public Long getShopId()
   {
      return shopId;
   }

   public void setShopId(Long shopId)
   {
      this.shopId = shopId;
   }

   public String getBuildId()
   {
      return buildId;
   }

   public void setBuildId(String buildId)
   {
      this.buildId = buildId;
   }

   public String getFloor()
   {
      return floor;
   }

   public void setFloor(String floor)
   {
      this.floor = floor;
   }

   public String getX()
   {
      return x;
   }

   public void setX(String x)
   {
      this.x = x;
   }

   public String getY()
   {
      return y;
   }

   public void setY(String y)
   {
      this.y = y;
   }

}
