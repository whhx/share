/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.mapper;

import com.rtmap.luck.model.Poi;

public interface PoiMapper
{

   //根据券id查找相应信息并返回
   public Poi findPrizeInfo(long id);
   
   //根据shopId查询相应信息
   public Poi findByShopId(long shopId);
   
}
