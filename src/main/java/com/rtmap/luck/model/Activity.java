/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.model;

/**
 * 
 * Activity. 活动表 shake_activity
 * 
 * @author Jeffrey Jia
 * @since 0.1
 */
public class Activity
{

   private Long id;

   /**
    * 活动名称
    */
   private String name;

   /**
    * 活动类型 1：摇一摇周边 2：吃豆豆
    */
   private Integer type;

   /**
    * 活动状态：活动状态(默认0准备中、1.已开始、2.暂停、3.已结束、)默认为0、5.审核中
    */
   private Integer status;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Integer getType()
   {
      return type;
   }

   public void setType(Integer type)
   {
      this.type = type;
   }

   public Integer getStatus()
   {
      return status;
   }

   public void setStatus(Integer status)
   {
      this.status = status;
   }

}
