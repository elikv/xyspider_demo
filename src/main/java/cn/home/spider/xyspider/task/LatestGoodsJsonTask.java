package cn.home.spider.xyspider.task;

import cn.home.spider.xyspider.dao.IXyJsonDao;
import cn.home.spider.xyspider.pojo.XyJson;
import cn.home.spider.xyspider.utils.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zzy
 * @Date 16:11 2018/9/28
 **/
@Component
@Slf4j
public class LatestGoodsJsonTask {
@Autowired
private IXyJsonDao xyJsonDao;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void xyJsonTask(){
        String url = "https://s.2.taobao.com/list/waterfall/waterfall.htm?wp=1&_ksTS=1538120321930_156&callback=jsonp157&stype=1&st_trust=1&q=switch&ist=1";
        String httpPostRequest = HttpClientUtils.httpGetRequest(url);
        //去掉json157(
        String jsonp157 = httpPostRequest.replaceAll("jsonp157\\(", "");
        jsonp157 = replaceBlank(jsonp157);
        //去掉最后的）
        jsonp157 = jsonp157.substring(0, jsonp157.length() - 1);
        JSONObject jsonp157Object = JSON.parseObject(jsonp157);
        Integer num = (Integer)jsonp157Object.get("numFound");
        log.info("---------------------当前接口总商品数量："+num+"-----------------------");
        JSONArray idle = jsonp157Object.getJSONArray("idle");
        System.out.print(idle.toJSONString());
        List<XyJson> XyJson = new ArrayList<>();
        for (int i=0;i<idle.size();i=i+1){
            JSONObject o = idle.getJSONObject(i);
            JSONObject user = o.getJSONObject("user");
            String userNick = user.getString("userNick");
            XyJson item = o.getJSONObject("item").toJavaObject(XyJson.class);
            item.setSatime(new Date());
            item.setUserNick(userNick);
            item.setId(UUID.randomUUID().toString().replaceAll("-",""));
            log.info("---------------------添加商品："+item.getTitle()+"-----------------------价格："+item.getPrice());
            XyJson.add(item);
        }
        xyJsonDao.saveBatch(XyJson);
        log.info("--------------------"+ LocalTime.now()+"商品更新结束----------------------");

    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

    }


    /**
     * jsonp157({
     "numFound": 22937,
     "currPage": 1,
     "totalPage": 1147,
     "ershouCount": 0,
     "idleCount": 20,
     "ershou": [],
     "idle": [{
     "user": {
     "userIcon": "//wwc.alicdn.com/avatar/getAvatar.do?userNick=流逝匆匆&width=30&height=30&type=sns",
     "userNick": "流逝匆匆",
     "vipLevel": 3,
     "userTypeId": [],
     "isTaobaoWomen": false,
     "taobaoWomenUrl": "//mm.taobao.com/847508347.htm",
     "userCreditUrl": "//s.2.taobao.com/list/list.htm?usernick=Á÷ÊÅ´Ò´Ò",
     "userItemsUrl": "//s.2.taobao.com/list/list.htm?usernick=Á÷ÊÅ´Ò´Ò",
     "isSinaV": false,
     "yellowSeller": false
     },
     "item": {
     "imageUrl": "//img.alicdn.com/bao/uploaded/i3/6000000006101/TB2EVmgj1uSBuNjSsziXXbq8pXa_!!0-fleamarket.jpg_220x10000.jpg",
     "imageHeight": 720,
     "imageWidth": 1280,
     "itemUrl": "//2.taobao.com/item.htm?id=567133724896&from=list&similarUrl=",
     "isBrandNew": false,
     "price": "1700.00",
     "orgPrice": "",
     "provcity": "山东菏泽",
     "describe": "购于2017年11月30号，因为没有带过壳所以表面成色一般，但是没有任何磕碰，左边手柄拆过，换过摇杆，便携底座。一直实体卡，有一个数字版的星露谷，所有配件齐全。账号我也不要了，退坑了。",
     "publishTime": "1分钟前",
     "itemFrom": "发布自iPhone客户端",
     "itemFromDesc": "iPhone客户端",
     "itemFromTarget": "//2.taobao.com/mobile/iphone.htm",
     "commentCount": 0,
     "commentUrl": "//2.taobao.com/item.htm?id=567133724896&from=list&similarUrl=#item-comments",
     "collectCount": 0,
     "title": "任天堂switch，箱说盒，发票全在，港版。"
     }
     }]
     })
     */
}


