/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.rest.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import redis.clients.jedis.Jedis;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rtmap.luck.common.Result;
import com.rtmap.luck.common.Trace;
import com.rtmap.luck.mapper.LevelMapper;
import com.rtmap.luck.utils.CommUtil;

/**
 * 
 * ShareAction. 分享
 * 
 * @author Jeffrey Jia
 * @since 0.1
 */
@ControllerAdvice
@RestController
public class ShareAction extends AbstractJsonpResponseBodyAdvice//jsonp支持
{

   private static Logger log = LoggerFactory.getLogger(ShareAction.class);

   private LevelMapper levelMapper;

   private MongoClient mongoClient;

   private Jedis jedis;

   private Map<String, String> commonMap;

   //jsonp支持
   public ShareAction()
   {
      super("callback");
   }

   /**
    * 查看点赞数
    * 
    * @param id券id
    */
   @RequestMapping(value = "/view/{id}")
   public Long view(@PathVariable("id") String id)
   {
      String key = "PRIZE_CLICK";
      String click = jedis.hget(key, id);
      log.debug("{}:{}={}", key, id, click);
      if (click == null)
         click = "0";
      return Long.valueOf(click);
   }

   /**
    * 点赞数+1
    * 
    * @param id
    */
   @RequestMapping(value = "/click/{id}")
   public Long click(@PathVariable("id") String id)
   {
      String key = "PRIZE_CLICK";
      Long click = jedis.hincrBy(key, id, 1);
      return click;
   }

   /**
    * 分享追踪
    * 
    * @param trace
    */
   @RequestMapping(value = "/trace")
   public Trace trace(@ModelAttribute Trace trace)
   {
      MongoDatabase database = mongoClient.getDatabase("promo");
      MongoCollection<Document> collection = database.getCollection("trace");
      Document doc = new Document();

      doc.put("activity_id", trace.getAid());
      doc.put("prize_id", trace.getId());
      doc.put("parent", trace.getParent());
      doc.put("open_id", trace.getOpenId());
      Date date = new Date();
      doc.put("create_time", date);
      doc.put("time", date.getTime());
      collection.insertOne(doc);
      return trace;
   }

   /**
    * 卡券信息接口
    */
   @RequestMapping(value = "/prize/{id}")
   public Result prize(@PathVariable("id") Long id)
   {
      Result result = null;

      String key = "PRIZE_" + id;
      String json = jedis.get(key);
      if (json == null)
      {
         result = fit(id);
         if (result == null)
         {
            result = new Result();
         }
         json = CommUtil.json(result);
         jedis.set(key, json);
         jedis.expire(key, 60 * 60);//缓存小时
         log.info("{}={}", key, json);

      } else
      {
         result = CommUtil.read(json, Result.class);
      }
      Long click = view(id.toString());
      result.setClick(click);
      return result;

   }

   private Result fit(Long id)
   {
      String cdnBase = commonMap.get("cdn.base.url");

      // 组装等级信息
      Result result = levelMapper.findById(id);
      if (result == null)
         return null;
      String imgUrl = result.getImgUrl();
      result.setImgUrl(cdnBase +"/"+imgUrl);
      String template = result.getTemplate();// 模版
      result.setTemplate(cdnBase + template);

      // 组装店铺信息(名称,logo)
      Result shop = levelMapper.findShop(id);
      if (shop == null)// 店铺查不到就用商场的logo和名称
      {
         shop = levelMapper.findMarket(id);
      }
      if (shop != null)// 奖品指定了提供方(防止提供方shopId=0)
      {
         result.setShopName(shop.getShopName());
         String logoUrl = shop.getLogoUrl();
         if (StringUtils.isNotBlank(logoUrl))
         {
            result.setLogoUrl(cdnBase + logoUrl);
         }

      }
      return result;
   }

   public MongoClient getMongoClient()
   {
      return mongoClient;
   }

   public void setMongoClient(MongoClient mongoClient)
   {
      this.mongoClient = mongoClient;
   }

   public Jedis getJedis()
   {
      return jedis;
   }

   public void setJedis(Jedis jedis)
   {
      this.jedis = jedis;
   }

   public LevelMapper getLevelMapper()
   {
      return levelMapper;
   }

   public void setLevelMapper(LevelMapper levelMapper)
   {
      this.levelMapper = levelMapper;
   }

   public Map<String, String> getCommonMap()
   {
      return commonMap;
   }

   public void setCommonMap(Map<String, String> commonMap)
   {
      this.commonMap = commonMap;
   }

}
