package com.cmt.entity;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class MailEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;

	//邮箱服务器地址
	private String host;
	
	//服务器地址端口号
	private String port;
	
	//发件人邮箱地址
	private String sender;
	
	//发件人邮箱授权码
	private String ticket;

	//邮件标题
	private String title;
	
	//邮件正文
	private String content;
	
	//正文的类型，html/txt，默认html
	private Integer contentType;
	 
	//收件人集合
	private List<String> toMailAddresses;
	
	//抄送人邮件地址
	private List<String> ccMailAddresses;
	
	//私信人邮件地址
	private List<String> bccMailAddresses;
	
	//图片名称
	private List<String> imgNames;
	
	//附件名称
	private List<String> attachNames;

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTitle() {
		return Optional.ofNullable(title).orElse("");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return Optional.ofNullable(content).orElse("");
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public List<String> getToMailAddresses() {
		return toMailAddresses;
	}

	public void setToMailAddresses(List<String> toMailAddresses) {
		this.toMailAddresses = toMailAddresses;
	}

	public List<String> getCcMailAddresses() {
		return ccMailAddresses;
	}

	public void setCcMailAddresses(List<String> ccMailAddresses) {
		this.ccMailAddresses = ccMailAddresses;
	}

	public List<String> getBccMailAddresses() {
		return bccMailAddresses;
	}

	public void setBccMailAddresses(List<String> bccMailAddresses) {
		this.bccMailAddresses = bccMailAddresses;
	}

	public List<String> getImgNames() {
		return imgNames;
	}

	public void setImgNames(List<String> imgNames) {
		this.imgNames = imgNames;
	}

	public List<String> getAttachNames() {
		return attachNames;
	}

	public void setAttachNames(List<String> attachNames) {
		this.attachNames = attachNames;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
