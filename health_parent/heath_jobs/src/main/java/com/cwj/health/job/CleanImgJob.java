package com.cwj.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cwj.health.Utils.QiNiuUtils;
import com.cwj.health.service.SetmealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("/cleanImgJob")
public class CleanImgJob {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference
    private SetmealService setmealService;

    @Scheduled(fixedDelay = 1800000,initialDelay = 3000)
    public void cleanImg(){
        log.info("清理7牛上垃圾图片的任务开始执行。。。。。。。");
        //获取七牛云上的图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        log.debug("imgIn7Niu 有{}张图片要清理", imgIn7Niu.size());
        //获取数据库上的图片
        List<String> imgInDb = setmealService.findImgs();
        log.debug("imgInDb 有{}张图片", imgInDb.size());
        //需要删除的图片
        imgIn7Niu.removeAll(imgInDb);
        log.debug("有{}张图片 需要删除", imgIn7Niu.size());
        //删除图片
        try {
            log.info("开始删除七牛上的图片 {}", imgIn7Niu.size());
            QiNiuUtils.removeFiles(imgIn7Niu.toArray(new String[]{}));
            log.info("删除七牛上的图片 {}  成功===============", imgIn7Niu.size());
        } catch (Exception e) {
            log.error("清理垃圾图片出错了",e);
        }
    }
}
