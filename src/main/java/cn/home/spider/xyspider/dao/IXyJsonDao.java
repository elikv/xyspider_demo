package cn.home.spider.xyspider.dao;

import cn.home.spider.xyspider.pojo.XyJson;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zzy
 * @Date 16:48 2018/9/28
 **/
@Component
@Mapper
public interface IXyJsonDao {
    public void saveBatch(List<XyJson> list);

    public void save(XyJson t);
}
