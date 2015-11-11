/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommUtil
{

   private static ObjectMapper mapper = new ObjectMapper();

   public static String json(Object object)
   {
      try
      {

         return mapper.writeValueAsString(object);
      } catch (Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }

   public static <T> T read(String json, Class<T> classz)
   {
      try
      {
         return mapper.readValue(json, classz);
      } catch (Exception e)
      {
         e.printStackTrace();
         return null;
      }

   }
   
  
   
   
}
