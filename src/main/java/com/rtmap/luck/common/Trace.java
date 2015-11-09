/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.common;

public class Trace
{

   /**
    * 分享人openId
    */
   private String openId;

   /**
    * 父分享人openId
    */
   private String parent;

   /**
    * 活动id
    */
   private Long aid;

   /**
    * 券id
    */
   private Long id;

   public String getOpenId()
   {
      return openId;
   }

   public void setOpenId(String openId)
   {
      this.openId = openId;
   }

   public String getParent()
   {
      return parent;
   }

   public void setParent(String parent)
   {
      this.parent = parent;
   }

   public Long getAid()
   {
      return aid;
   }

   public void setAid(Long aid)
   {
      this.aid = aid;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

}
