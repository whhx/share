/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.mapper;

import com.rtmap.luck.common.Result;

public interface LevelMapper
{

   //根据奖品等级id查提供店铺
   public Result findShop(Long id);

   //根据奖品id查提供商场
   public Result findMarket(Long id);

   //根据奖品id查等级
   public Result findById(Long id);

}
