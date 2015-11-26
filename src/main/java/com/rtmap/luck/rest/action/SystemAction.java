/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.rest.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@ControllerAdvice
@RestController
@RequestMapping(value = "/sys")
public class SystemAction extends AbstractJsonpResponseBodyAdvice//jsonp支持
{
   @Autowired
   private JedisPool jedisPool;

   //jsonp支持
   public SystemAction()
   {
      super("callback");
   }


   @RequestMapping(value = "/get/{key}")
   public String get(@PathVariable("key") String key)
   {
      Jedis jedis = jedisPool.getResource();
      String value = jedis.get(key);
      jedis.close();
      return value;
   }

   @RequestMapping(value = "/hget/{key}")
   public Map<String, String> hget(@PathVariable("key") String key)
   {
      Jedis jedis = jedisPool.getResource();
      Map<String, String> value = jedis.hgetAll(key);
      jedis.close();
      return value;
   }

}
