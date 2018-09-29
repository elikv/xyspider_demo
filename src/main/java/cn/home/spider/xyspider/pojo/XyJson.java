package cn.home.spider.xyspider.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: zzy
 * @Date 16:25 2018/9/28
 **/
@Data
public class XyJson {

    private String id;

    private  String title;

    private String price;
    //商品描述
    private String describe;
    //来源省市
    private String provcity;
    //发布者
    private String userNick;
    private String itemUrl;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date satime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date smtime;

}
