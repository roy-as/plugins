package com.cmt.common;


import com.cmt.common.exception.MessageErrorException;
import com.cmt.config.MailConfigProperties;

/**
 * @className: MailSenders
 * @description: TODO
 * @author: Aby@iv66.net
 * @create: 2019-10-24 17:11
 * @version: v1.0.0
 **/
public class MailSenders {

    private MailConfigProperties properties;

    public MailSenders(){}

    public MailSenders properties (MailConfigProperties properties) {
        this.properties = properties;
        return this;
    }

    public MailSender build() {
        if(null == properties) {
            throw new MessageErrorException("there is no mail config properties");
        }
        return new MailSender(this.properties);
    }
}
