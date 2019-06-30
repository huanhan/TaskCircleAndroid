# TaskCircleAndroid
##任务圈安卓端

#### 简介
- “任务圈”APP，它提供任务信息整理、分类、规范、监控执行、消息推送、及时聊天等功能。满足用户查看、发布、接取、监控等需求，对于任务执行者提供身份审核认证，保障用户权益。本文详细叙述了可行性分析、需求分析、系统结构设计以及完成代码的编写和测试。项目主要使用AndroidStudio进行开发，Kotlin作为开发语言，结合现实需求，设计各个功能模块，最终实现了任务圈App。
- 项目主要分成app和baselibrary模块。其中ui借鉴了饿了么（分类选择联动），图片选取使用了知乎开源的matisse以及阿里云Oss提供图片存储服务。及时通信以及订单推送使用了极光推送。使用JSON Web Token（JWT）来对应用请求进行授权认证。提供本地令牌持久化，过期重连等。避免用户频繁登录以及账号密码泄漏等安全性问题。
- 本人的毕业设计，app端从设计、实现、图片制作等都是本人独立完成的大概用时3个月左右，路过的大佬给个star蟹蟹！！
#### 主要框架
- Kotlin+Anko+MVP+Rxjava2+okhttp3+Retrofit2+Dagger2+rxlifecycle+rxpermissions+Glide

#### 效果预览
<p>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/1.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/2.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/3.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/4.png" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/5.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/6.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/7.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/8.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/9.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/10.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/11.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/12.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/13.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/14.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/15.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/16.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/17.jpg" width="225" height="465" alt=""/>
<img src="https://github.com/huanhan/TaskCircleAndroid/blob/master/18.jpg" width="225" height="465" alt=""/>
</p>