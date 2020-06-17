#############
提供快速集成发送邮件的功能，目前只支持stmp
在pom中添加以来
<dependency>
   <groupId>com.star</groupId>
   <artifactId>mail-starter</artifactId>
   <version>1.0.3-RELEASE</version>
</dependency>
要使用邮件发送需要在配置文件中配置:
mail.host 邮件服务器
mail.port 端口号
mail.sender 发送人邮箱地址
mail.ticket 发送人邮箱密钥
mail.content-type 文本类型，1:html,2:文本
mail.file-path 文件缓存路径
mail.to-mail-address 收件人邮件地址，多个用英文逗号隔开
mail.cc-mail-address 抄送邮件地址，多个地址用英文逗号隔开
mail.bcc-mail-address 密送邮件地址，多个用英文逗号隔开