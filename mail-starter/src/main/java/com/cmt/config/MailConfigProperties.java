package com.cmt.config;

import com.cmt.common.enums.MailContentTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @className: MailConfigProperties
 * @description: TODO
 * @author: Aby@iv66.net
 * @create: 2019-10-24 13:36
 * @version: v1.0.0
 **/
@ConfigurationProperties(prefix = "as.mail")
public class MailConfigProperties {

    /**
     * 邮件服务器
     */
    private String host;

    /**
     * 端口号
     */
    private String port;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 授权码
     */
    private String ticket;

    /**
     * 类型
     */
    private Integer contentType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 收件人
     */
    private String toMailAddresses;

    /**
     * 抄送人
     */
    private String ccMailAddresses;

    /**
     * 私信人
     */
    private String bccMailAddresses;

    public List<String> getCcMailAddresses() {
        return new ArrayList<>(Arrays.asList(Optional.ofNullable(ccMailAddresses).orElse("").trim().split(",")));
    }

    public void setCcMailAddresses(String ccMailAddresses) {
        this.ccMailAddresses = ccMailAddresses;
    }

    public List<String> getBccMailAddresses() {
        return new ArrayList<>(Arrays.asList(Optional.ofNullable(bccMailAddresses).orElse("").trim().split(",")));
    }

    public void setBccMailAddresses(String bccMailAddresses) {
        this.bccMailAddresses = bccMailAddresses;
    }

    public List<String> getToMailAddresses() {
        return new ArrayList<>(Arrays.asList(Optional.ofNullable(toMailAddresses).orElse("").trim().split(",")));
    }

    public void setToMailAddresses(String toMailAddress) {
        this.toMailAddresses = toMailAddress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        if(null != filePath && !filePath.endsWith("\\") && !filePath.endsWith("/") ) {
            filePath = filePath + "/";
        }
        this.filePath = filePath;
    }

    public Integer getContentType() {
        return Optional.ofNullable(contentType).orElse(MailContentTypeEnum.HTML.getType());
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
