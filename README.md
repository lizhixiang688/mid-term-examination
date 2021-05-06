# 期中考核

### 一、大致功能

* 实现封面，自动跳转到下一个界面

* 实现了注册和登录功能，可实现自动登录

![image](https://github.com/lizhixiang688/mid-term-examination/blob/master/images/Screenshot_20210506_142513_com.example.timecube.jpg)
![image](https://github.com/lizhixiang688/mid-term-examination/blob/master/images/Screenshot_20210506_142519_com.example.timecube.jpg)
* 实现保存用户名，下次点开app自动加载用户名

* 点击按钮可添加备忘录，备忘录可设置截止时间，提醒时间
* 时间到了提醒时间后，app会发送一条通知，有提示声音和震动

* 使用了LitePal数据库对备忘录进行存储

* 长按一个备忘录可进行删除

------

### 二、实现思路

* 使用Handler来实现封面的定时跳转

* 用Shareprefence来存储用户的账号和密码，以及用户名

* 主页面是一个Tablayout+Viewpager+两个Fragment

* 第一个Fragment里面有一个RecycleView用来显示我的备忘录，在左下角有一个按钮，点击后可以跳转到新建备忘录的活动

* 新建备忘录的活动左上面有一个返回按钮，点击可返回第一个Fragment。中间是一个Edittext可输入想做的事，在Edittext的右下角有两个按钮，点击后会展示PickerView，来设置提醒时间和到期时间，右上角是一个确定按钮，点击后会将输入的数据存入LitePal数据库，并把输入的提醒时间用来设置Alarm和Calendar，实现定时发送通知的功能，并返回第一个Fragment

* 在第一个Fragment中的RecycleView会显示所有的备忘录，包括刚才添加的那个，当你长按其中一个item的时候会把这个item删除

* 发送的通知中会显示你在Edittext中输入的想做的事，并有提示声音和震动，来提醒你完成任务

* 在第二个Fragment中是设置界面，用户可点击头像进行头像设置，~~时间有限还没开发~~，用户可点击名字，对用户名进行设置，在Fragment的最下面有一个退出登录按钮，点击后会弹出一个AlertDialog，点击确定后，会退出登录，并跳转到登录界面

   ------

   

### 三、难点和疑惑

* 首先在数据库的选择上我先想了Shareprefence但是发现用它来存储的时候，删除和查询不太方便，然后想的是SQLite数据库，但是SQL语句过于繁琐与复杂，又想了Room，但是刚学不太会用，最后，我发现LitePal才是~~yyds~~ ，只需要配置一点点代码，就可以完美使用LitePal，不仅创建数据库十分简便

  ```java
  if(LitePal.getDatabase()==null){
      LitePal.getDatabase();
  }
  ```

​     而且在和RecycleView配合使用的时候，简直是量身定做而成， 

```
  adapter=new RecycleAdapter(DataSupport.findAll(Note.class),this);
```

   因为查询LitePal的时候，它会返回你一个`List<Node>`,而这个就是我们在构造Adapater时所需要的。

* 然后是在实现到提醒时间发送消息的功能上，在AlarmManager真的是卡了我好久，每次都收不到消息，最后看了几篇博客，才稍微好一点。但是我又发现当我的时间设置稍微久一点，它的消息发送就不准时，一般都会延后几十秒，我想了半天，去网上搜了一下，差点把我气死，**官方文档说从API19（android4.4）开始, 为了节能省电（减少系统唤醒和电池使用）使用Alarm.set()和Alarm.setRepeating()已经不保证精确性**，解决办法就是

  ```java
  am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);        //不精准
  
  am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);   //精准
  ```

* 最后就是在长按删除item那里，发现怎么长按都没有效果，没办法，最后只好使用了一下接口回调，回调到Fragment中来进行删除item，同时也删除在数据库中的数据

  ------

  

### 四、优点和不足

####      优点

​      说实话，真没啥优点，但是还是要厚着脸皮说几个。。。

  > 俗话说功能不够，UI来凑，我的UI呢，只好厚着脸皮说还能将就看下去，废话不多说，看图
  >
  > ![image](https://github.com/lizhixiang688/mid-term-examination/blob/master/images/VIDEO_050615345271549.gif)

#### 不足

​     说到不足之处，那就太多了。。。

> 我觉得可以吧设置头像的功能完善一下
>
> 然后可以实现添加列表，完成任务的功能等等

