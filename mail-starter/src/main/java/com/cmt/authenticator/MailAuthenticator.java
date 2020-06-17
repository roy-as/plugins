package com.cmt.authenticator;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @className: MailAuthenticator
 * @description: TODO
 * @author: Aby@iv66.net
 * @create: 2019-11-26 19:27
 * @version: v1.0.0
 **/
public class MailAuthenticator extends Authenticator {

    private String userName;

    private String password;

    public MailAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
