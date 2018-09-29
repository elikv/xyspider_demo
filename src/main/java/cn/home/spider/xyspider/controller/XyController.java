package cn.home.spider.xyspider.controller;

import cn.home.spider.xyspider.dao.IXyJsonDao;
import cn.home.spider.xyspider.pojo.XyJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @author: zzy
 * @Date 17:54 2018/9/28
 **/
@Controller
@RequestMapping("/task")
public class XyController
{
    @Autowired
    private IXyJsonDao xyJsonDao;
    @RequestMapping("/task")
    public ResponseEntity task(){

        XyJson t = new XyJson();
        t.setId(UUID.randomUUID().toString());
        xyJsonDao.save(t);
        return  ResponseEntity.ok(t);
    }

}
