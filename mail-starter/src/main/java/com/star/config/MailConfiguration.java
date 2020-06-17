package com.star.config;

import com.star.common.MailSenders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import java.io.File;

/**
 * @className: MailConfiguration
 * @description: TODO
 * @create: 2019-10-24 13:43
 * @version: v1.0.0
 **/
@Configuration
@EnableConfigurationProperties(MailConfigProperties.class)
public class MailConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailConfiguration.class);

    @Autowired
    private MailConfigProperties mailProperties;

    @Bean
    public MailSenders mailSender() {
        String filePath = mailProperties.getFilePath();
        // 创建文件夹
        if(!StringUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if(!file.exists()) {
                if(!file.mkdirs()) {
                    LOGGER.error("{}创建失败", filePath);
                }
            }else {
                if(!file.isDirectory()) {
                    if(!file.mkdirs()) {
                        LOGGER.error("{}创建失败", filePath);
                    }
                }
            }
        }
        return new MailSenders().properties(mailProperties);
    }

}
