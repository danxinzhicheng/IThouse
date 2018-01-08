
# ITHouse

一款高仿IT之家的Android APP，力求原汁原味，还原那个熟悉的之家。

## APP下载

当前Release版本：[点击下载](https://github.com/danxinzhicheng/IThouse/blob/master/app/release/app-release.apk?raw=true)

> 由于用到了5.0以上SDK独有的API，比如动画相关，所以尽量在5.0+以上设备上调试，否则会Crash哦。

## 编译环境

1. Android Studio：3.0正式版
2. 编译SDK： 26
3. Gradle版本：gradle-4.1-all.zip
   classpath 'com.android.tools.build:gradle:3.0.0'

   注：可根据自己的环境修改Gradle版本，只要编译通过就可

4. Android Support库：26.0.1

- com.android.support:appcompat-v7:26.0.1
- com.android.support:design:26.0.1
- com.android.support:cardview-v7:26.0.1
- com.android.support:support-v4:26.0.1


## 第三方库

1. Banner幻灯片：YouthBanner(com.youth.banner:banner:1.4.10)
2. 通信总线 : EventBus
3. Http请求 : Retrofit
4. 图片加载 ：Glide
5. 数据持久化 ：Realm
6. 日志Log：Bugly

## 项目架构参考

- DiyCode
- OSChina
> 感谢github，感谢开源.

## 抓包工具

  > 使用Fiddler抓包工具抓取IT之家的xml和json，作为样本数据，项目中部分以URL完整地址请求数据。

## 版本
### v1.0.1
- 添加资讯栏目中自定义TAB导航页
- 支持辣品栏目跳转到详情页
- 支持资讯栏目搜索功能
- 添加日历界面
- 添加圈子二级页面(分类二级页面，评论楼层页面)
- 支持登陆功能（登陆和个人信息使用diyCode接口）

### v1.0.0
- 整个项目的架构，从无到有
- 资讯，辣品，圈子，我，四大导航栏主界面的布局及数据展示
- 文章详情页的布局及数据展示

## 项目截图
资讯：

![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_1.png)

辣品：

![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_2.png)

圈子：

![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_3.png)

个人：

![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_4.png)

## Bug改进与开发思考
1. 资讯，辣品 Banner展示偶现不显示
2. 辣品 排行榜布局偶现不显示，其他BUG等
3. 欢迎反馈Bug或建议

## 作者联系方式
- 微信：danxinzhicheng
- QQ：742671635
> 两年多Android开发经验，商业APP开发，系统应用开发，Application Framework定制等，欢迎添加骚扰，技术交流等。

****
> 声明：本项目属仿IT之家Android版本，仅供学习交流，勿用于商业用途。
