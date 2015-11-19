/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.rtmap.luck.rest.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rtmap.luck.common.Result;
import com.rtmap.luck.common.Touch;
import com.rtmap.luck.common.Trace;
import com.rtmap.luck.mapper.LevelMapper;
import com.rtmap.luck.mapper.PoiMapper;
import com.rtmap.luck.model.Poi;
import com.rtmap.luck.utils.CommUtil;
import com.rtmap.luck.utils.DateUtil;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.IntegerTranscoder;

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

   private static final Logger log = LoggerFactory.getLogger(ShareAction.class);

   private LevelMapper levelMapper;

   private PoiMapper poiMapper;

   private MongoClient mongoClient;

   private MemcachedClient memcachedClient;

   private JedisPool jedisPool;

   private Map<String, String> commonMap;

   private Document doc = null;

   //jsonp支持
   public ShareAction()
   {
      super("callback");
   }

   /**
    * 
    * 返回阅读数
    * 
    * @param id
    *           （券ID）
    * @return
    */
   @RequestMapping(value = "/read/{id}")
   public long read(@PathVariable("id") String id)
   {
      String key = "PRIZE_EXPOSELOG";
      Jedis jedis = jedisPool.getResource();
      String read = jedis.hget(key, id);
      log.debug("{}:{}={}", key, id, read);
      log.info("（调用阅读接口）券（ID：" + id + "）被阅读的数量为！"+read);
      if (read == null)
         read = "0";
      jedis.close();
      return Long.valueOf(read);
   }

   /**
    * 查看点赞数
    * 
    * @param id券id
    */
   @RequestMapping(value = "/view/{id}")
   public Long view(@PathVariable("id") String id)
   {
      String key = "PRIZE_TOUCH";
      Jedis jedis = jedisPool.getResource();
      String click = jedis.hget(key, id);
      log.debug("{}:{}={}", key, id, click);
      if (click == null)
         click = "0";
      jedis.close();
      return Long.valueOf(click);
   }

   /**
    * 查看点赞数
    * 
    * @param id券id
    */
   @RequestMapping(value = "/view/{id}/{openId}")
   public Touch view2(@PathVariable("id") String id, @PathVariable("openId") String openId)
   {
      Touch touch = new Touch();
      String key = "PRIZE_TOUCH";
      Jedis jedis = jedisPool.getResource();

      String click = jedis.hget(key, id);
      log.debug("{}:{}={}", key, id, click);
      if (click == null)
         click = "0";

      String openIdKey = "PRIZE_TOUCH_OPENID";
      Boolean exists = jedis.hexists(openIdKey, id + "_" + openId);
      touch.setNum(Long.valueOf(click));
      touch.setCan(!exists);
      jedis.close();
      return touch;
   }

   /**
    * 点赞数+1
    * 
    * @param id
    */
   @RequestMapping(value = "/click/{id}")
   public Long click(@PathVariable("id") String id)
   {
      String key = "PRIZE_TOUCH";
      Jedis jedis = jedisPool.getResource();
      Long click = jedis.hincrBy(key, id, 1);
      jedis.close();
      return click;
   }

   /**
    * 点赞数+1
    * 
    * @param id
    * @param openId
    * @return
    */
   @RequestMapping(value = "/click/{id}/{openId}")
   public Long click2(@PathVariable("id") String id, @PathVariable("openId") String openId)
   {
      MongoDatabase database = mongoClient.getDatabase("promo");
      MongoCollection<Document> collection = database.getCollection("share");

      Jedis jedis = jedisPool.getResource();
      String key = "PRIZE_TOUCH";
      String openIdKey = "PRIZE_TOUCH_OPENID";

      String entity = id + "_" + openId;
      Long num = jedis.hsetnx(openIdKey, entity, entity);
      jedis.expire(openIdKey, 60 * 60 * 24 * 10);//缓存10天
      Date date = new Date();
      doc = new Document("prize_id", id).append("open_id", openId).append("create_time", date)
            .append("time", date.getTime());

      Long click = 0L;
      if (num == 0)
      {
         doc.append("touch", 0);
         click = view(id);
      } else
      {
         click = jedis.hincrBy(key, id, 1);
         doc.append("touch", 1);
         collection.insertOne(doc);
      }
      jedis.close();
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
   public Result prize(@PathVariable("id") Long id, @RequestParam(value = "openId", required = false) String openId)
   {
      Result result = null;

      Jedis jedis = jedisPool.getResource();
      String key = "PRIZE_" + id;
      String json = jedis.get(key);
      if (json == null)
      {
         result = fit(id, openId);
         if (result == null)
         {
            result = new Result();
         }
         json = CommUtil.json(result);
         jedis.set(key, json);
         jedis.expire(key, 60 * 60);//缓存一个小时
         log.info("{}={}", key, json);

      } else
      {
         result = CommUtil.read(json, Result.class);
         result.setHaveCount(0);
      }
      Long click = view(id.toString());
      result.setClick(click);
      jedis.close();
      return result;

   }

   /**
    * 
    * 定位需要信息
    * 
    * @param id
    * @return
    */
   @RequestMapping(value = "/location/{id}")
   public Poi location(@PathVariable("id") long id)
   {
      Poi poi = null;

      Jedis jedis = jedisPool.getResource();
      String key = "PRIZE_POI_" + id;
      String json = jedis.get(key);
      if (json == null)
      {
         //从数据库中根据prize查找buildId，floor，shopId
         poi = poiMapper.findPrizeInfo(id);
         if (poi == null)
         {
            return null;
         }

         if (poi.getShopId() == null)
         {
            if ("".equals(poi.getFloor()) || poi.getFloor() == null)
            {
               poi.setFloor("F1");
            }

         } else
         {
            poi = poiMapper.findByShopId(poi.getShopId());
         }
         json = CommUtil.json(poi);
         jedis.set(key, json);
         jedis.expire(key, 60 * 60);//缓存1小时
         log.info("{}={}", key, json);

      } else
      {
         poi = CommUtil.read(json, Poi.class);
      }
      jedis.close();
      return poi;
   }

   private Result fit(Long id, String openId)
   {
      // 组装等级信息
      Result result = levelMapper.findById(id);
      if (result == null)
         return null;

      //判断活动类型（0 抽奖，1发卷）
      Integer channel = result.getChannel();

      //判断是否是APP(0 Pc端，1全场APP端，2店内APP，默认为0)
      Integer isDefault = result.getIsDefault();

      if ((0 == channel) && 0 == isDefault)
      {
         Result market = assemble(id);
         market.setBrochur(0);//待定是否保留(显示宣传页)

         return market;
      }

      //发券活动店内APP抽奖，显示宣传页
      if (1 == channel && 2 == isDefault)
      {
         //显示数据为：商场logo，商场名称，商场地址，有效时间，商户logo，活动名称，十个最近的奖券，活动说明（优惠券的desc）
         Result market = assemble(id);
         market.setBrochur(0);
         return market;

      }

      if (1 == isDefault || (1 == channel && 0 == isDefault))
      {
         //App商场或者PC发券,如果已经领取过，则显示宣传页
         Result market = assemble(id);

         if (levelOwn(id, market, openId) > 0)
         {
            market.setBrochur(0);
            return market;
         } else
         {
            //不管是否还有券都显示这个页面（如果有券则显示领取，无则显示已领完）
            //符合抽取条件，显示的数据有：商户名称，商户logo，优惠券上图片，优惠卷主题，优惠券副标题，兑奖地址，优惠券有效期，优惠劵的活动说明
            //market.setBrochur(1);
            return market;
         }
      }
      return null;
   }

   //整合数据market
   public Result assemble(Long id)
   {
      String cdnBase = commonMap.get("cdn.base.url");
      Result result = levelMapper.findById(id);

      Result market = levelMapper.findMarket(id);
      if (market == null)
      {
         market = result;
      }
      Result shop = levelMapper.findShop(id);
      if (shop == null)
      {
         shop = market;
      }
      market.setTemplate(cdnBase + "/" + result.getTemplate());// 模版
      market.setShopName(shop.getShopName());
      market.setMarketLogoUrl(cdnBase + "/" + market.getMarketLogoUrl());
      market.setHaveCount(levelMapper.havePrize(id));
      market.setShopLogoUrl(cdnBase + "/" + shop.getShopLogoUrl());
      market.setImgUrl(cdnBase + "/" + market.getImgUrl());//优惠券上传的图片
      market.setLevel(result.getLevel());
      List<String> lastList = levelMapper.last(market.getActivityId());
      market.setLast(lastList);

      return market;

   }

   /**
    * 根据等级id和openId查用户已经获得的该奖品数量
    */
   public Integer levelOwn(Long id, Result result, String openId)
   {
      String key = "A" + result.getActivityId() + "_LEVEL_OWN_" + id + "_" + openId;
      Integer own = memcachedClient.get(key, new IntegerTranscoder());
      log.debug("{}={}", key, own);
      if (own == null)
      {
         own = levelMapper.prizeCount(id, openId);
         memcachedClient.set(key, expire(id, result), own);
         log.debug("Cache KV:{}={}", key, own);
      }
      return own;
   }

   /**
    * 获取过期时间
    */
   public int expire(Long id, Result result)
   {
      return expire(id, false, result);
   }

   /**
    * 获取/重建过期时间 build=true重建
    */
   public int expire(Long id, boolean build, Result result)
   {
      String key = "A" + id + "_EXPIRE_TIME";
      Integer expireTime = memcachedClient.get(key, new IntegerTranscoder());
      if (expireTime == null || build)
      {
         String endDate = result.getEndTime();
         Long endTime = DateUtil.format(endDate);
         Long endSec = endTime / 1000;
         expireTime = endSec.intValue();
         memcachedClient.set(key, expireTime, expireTime);
         log.debug("Cache KV: " + key + "=" + expireTime + " ExpireTime:" + DateUtil.format(endDate));
         return expireTime;// 活动结束时间点
      } else
      {
         log.debug(key + "=" + expireTime);
         return expireTime;
      }

   }

   public MongoClient getMongoClient()
   {
      return mongoClient;
   }

   public void setMongoClient(MongoClient mongoClient)
   {
      this.mongoClient = mongoClient;
   }

   public LevelMapper getLevelMapper()
   {
      return levelMapper;
   }

   public JedisPool getJedisPool()
   {
      return jedisPool;
   }

   public void setJedisPool(JedisPool jedisPool)
   {
      this.jedisPool = jedisPool;
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

   public MemcachedClient getMemcachedClient()
   {
      return memcachedClient;
   }

   public void setMemcachedClient(MemcachedClient memcachedClient)
   {
      this.memcachedClient = memcachedClient;
   }

   public PoiMapper getPoiMapper()
   {
      return poiMapper;
   }

   public void setPoiMapper(PoiMapper poiMapper)
   {
      this.poiMapper = poiMapper;
   }

}
