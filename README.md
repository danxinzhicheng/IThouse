
# ITHouse

一款高仿IT之家的Android APP。

## APP下载

当前Release版本：[点击下载](http://note.youdao.com/)

## 编译环境

1. Android Studio：3.0正式版
2. 编译SDK： 26
3. Gradle版本：gradle-4.1-all.zip

   classpath 'com.android.tools.build:gradle:3.0.0'
   注：可根据自己环境修改版本，只要编译通过就可

4. Android Support库：26.0.1

    compile 'com.android.support:appcompat-v7:26.0.1'

    compile 'com.android.support:design:26.0.1'

    compile 'com.android.support:cardview-v7:26.0.1'

    compile 'com.android.support:support-v4:26.0.1'


## 第三方库

1. Banner幻灯片：YouthBanner  (com.youth.banner:banner:1.4.10)
2. 通信总线 : EventBus
3. Http请求 : Retrofit
4. 图片加载 ：Glide

## 项目架构参考
- DiyCode

  整个项目结构，包括组件化，http请求，列表刷新等等

  Github：https://github.com/GcsSloop/diycode


- OSChina

  底部导航栏参考

  Github：https://gitee.com/oschina/android-app

## 版本

### v1.0.0
- 整个项目的架构，从无到有
- 资讯，辣品，圈子，我，四大导航栏主界面的布局及数据展示
- 文章详情页的布局及数据展示

## 项目截图
![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_1.png)


![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_2.png)


![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_3.png)


![image](https://raw.githubusercontent.com/danxinzhicheng/IThouse/master/Screenshots/Screenshot_4.png)

## Bug改进与开发思考
1. 资讯，辣品 Banner展示偶现不显示
2. 辣品 排行榜布局偶现不显示

****
> 本项目属仿IT之家Android版本，仅供学习交流，勿用于商业用途。