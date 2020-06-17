package com.cmt.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

import com.cmt.authenticator.MailAuthenticator;
import com.cmt.common.exception.MessageErrorException;
import com.cmt.common.utils.CollectionUtils;
import com.cmt.common.enums.MailContentTypeEnum;
import com.cmt.config.MailConfigProperties;
import com.cmt.entity.MailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * 发送邮件配置及发送
 *
 * @author Aby
 */
public class MailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

    /**
     * 邮件实体类
     */
    private MailEntity mailEntity = new MailEntity();

    public MailSender() {

    }

    MailSender(MailConfigProperties properties) {
        BeanUtils.copyProperties(properties, mailEntity);
    }

    /**
     * 邮箱域名
     * @param host
     * @return
     */
    public MailSender host(String host) {
        mailEntity.setHost(host);
        return this;
    }

    /**
     * 端口号
     * @param port
     * @return
     */
    public MailSender port(String port) {
        mailEntity.setPort(port);
        return this;
    }


    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public MailSender title(String title) {
        mailEntity.setTitle(title);
        return this;
    }

    /**
     * 设置邮件内容
     *
     * @param content
     * @return
     */
    public MailSender content(String content) {
        mailEntity.setContent(content);
        return this;
    }

    /**
     * 邮件内容类型  html/text
     *
     * @param contentType
     * @return
     */
    public MailSender contentType(Integer contentType) {
        mailEntity.setContentType(contentType);
        return this;
    }

    /**
     * 发件人
     *
     * @param sender
     * @return
     */
    public MailSender from(String sender) {
        mailEntity.setSender(sender);
        return this;
    }

    /**
     * 密钥
     * @param ticket
     * @return
     */
    public MailSender ticket(String ticket){
        mailEntity.setTicket(ticket);
        return this;
    }

    /**
     * 收件人
     *
     * @param target
     * @return
     */
    public MailSender target(List<String> target) {
        mailEntity.setToMailAddresses(target);
        return this;
    }

    /**
     * 抄送人
     *
     * @param ccTarget
     * @return
     */
    public MailSender ccTarget(List<String> ccTarget) {
        mailEntity.setCcMailAddresses(ccTarget);
        return this;
    }

    /**
     * 密送人
     *
     * @param bccTarget
     * @return
     */
    public MailSender bccTarget(List<String> bccTarget) {
        mailEntity.setBccMailAddresses(bccTarget);
        return this;
    }

    /**
     * 设置图片文件名
     *
     * @param imgNames
     * @return
     */
    public MailSender imgNames(List<String> imgNames) {
        mailEntity.setImgNames(imgNames);
        return this;
    }

    /**
     * 附件文件名
     *
     * @param attachNames
     * @return
     */
    public MailSender attachNames(List<String> attachNames) {
        mailEntity.setAttachNames(attachNames);
        return this;
    }

    /**
     * 发送邮件
     *
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void send() throws MessagingException, UnsupportedEncodingException {
        //发件人为空时抛出异常
        if (StringUtils.isEmpty(mailEntity.getSender())) {
            throw new MessageErrorException("发件人为空");
        }
        //收件人为空时抛出异常
        if (mailEntity.getToMailAddresses().isEmpty()) {
            throw new MessageErrorException("收件人为空");
        }
        //配置发送邮件的相关参数
        final Properties props = new Properties();
        //smtp必须设置身份认证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailEntity.getHost());
        props.put("mail.smtp.port", mailEntity.getPort());
        props.put("mail.user", mailEntity.getSender());
        props.put("mail.password", mailEntity.getTicket());

        //身份认证器
        Authenticator authenticator = new MailAuthenticator(mailEntity.getSender(), mailEntity.getTicket());

        //创建邮件会话
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);

        //配置message
        Message message = this.createMessage(session);

        //发送邮件
        this.sendTo(message, mailEntity.getToMailAddresses(), RecipientType.TO);

        //当抄送人不为空是时，邮件进行抄送
        if (!CollectionUtils.isNullOrEmpty(mailEntity.getCcMailAddresses())) {
            sendTo(message, mailEntity.getCcMailAddresses(), RecipientType.CC);
        }

        //当密送人不为空，发送
        if (!CollectionUtils.isNullOrEmpty(mailEntity.getBccMailAddresses())) {
            sendTo(message, mailEntity.getBccMailAddresses(), RecipientType.BCC);
        }
        // 发送邮件
        Transport.send(message);
    }

    /**
     * 遍历收件人地址，进行发送
     *
     * @param message
     * @param addresses
     */
    private void sendTo(Message message, List<String> addresses, Message.RecipientType type) {
        try {
            InternetAddress[] internetAddresses = addresses.stream().map(address -> {
                try {
                    return new InternetAddress(address);
                } catch (AddressException e) {
                    return null;
                }
            }).filter(Objects::nonNull).toArray(InternetAddress[]::new);
            //发送
            message.setRecipients(type, internetAddresses);
            LOGGER.info("收件人:{}设置成功", addresses);
        } catch (MessagingException e) {
            LOGGER.error("收件人:{}发送失败", addresses);
        }
    }

    /**
     * 创建message
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private Message createMessage(Session session) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = new MimeMessage(session);
        //设置发件人地址
        message.setFrom(new InternetAddress(mailEntity.getSender()));
        //设置邮件头
        message.setSubject(mailEntity.getTitle());

        MimeBodyPart text = new MimeBodyPart();
        ;
        List<MimeBodyPart> imgs = new ArrayList<>();
        List<MimeBodyPart> attaches = new ArrayList<>();
        //设置邮件文本内容
        StringBuilder content = new StringBuilder(mailEntity.getContent());
        //文本内容含有图片文件时，添加到文本中去
        if (!CollectionUtils.isNullOrEmpty(mailEntity.getImgNames())) {
            if (StringUtils.isEmpty(mailEntity.getFilePath())) {
                throw new MessageErrorException("文件路径为空");
            }
            for (String imgName : mailEntity.getImgNames()) {
                content.append("<br>").append("<img src='cid:").append(imgName).append("'>");
                MimeBodyPart img = new MimeBodyPart();
                img.setDataHandler(new DataHandler(new FileDataSource(mailEntity.getFilePath() + imgName)));
                img.setContentID(imgName);
                imgs.add(img);
            }
        }
        text.setContent(content.toString(), MailContentTypeEnum.getValue(mailEntity.getContentType()));

        //当附件不为空时，添加附件
        if (!CollectionUtils.isNullOrEmpty(mailEntity.getAttachNames())) {
            if (StringUtils.isEmpty(mailEntity.getFilePath())) {
                throw new MessageErrorException("文件路径为空");
            }
            for (String attachName : mailEntity.getAttachNames()) {
                MimeBodyPart attach = new MimeBodyPart();
                attach.setDataHandler(new DataHandler(new FileDataSource(mailEntity.getFilePath() + attachName)));
                attach.setFileName(MimeUtility.encodeText(attachName));
                attaches.add(attach);
            }
        }

        //设置图片与文本的关系
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(text);
        if (!imgs.isEmpty()) {
            for (MimeBodyPart part : imgs) {
                multipart.addBodyPart(part);
            }
            multipart.setSubType("related");
        }

        //设置文本与附件的关系
        MimeMultipart mimeMultipart = null;
        if (!attaches.isEmpty()) {
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(multipart);
            mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mbp);
            for (MimeBodyPart part : attaches) {
                mimeMultipart.addBodyPart(part);
            }
            mimeMultipart.setSubType("mixed");
        }
        // 设置不同的内容
        if (null == mimeMultipart) {
            message.setContent(multipart);
            message.saveChanges();
        } else {
            message.setContent(mimeMultipart);
            message.saveChanges();
        }
        return message;
    }
}
