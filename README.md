博客开发记录
参考博客：
https://onestar.newstar.net.cn/
http://www.zhuyiming.top/
参考教程： B站小而美博客教程

项目开源在github仓库，地址：https://github.com/mooganic/aBlog

一个入门级的博客项目(不是我现在使用的博客)，后端基于SpringBoot+MyBatisPlus，使用了thymeleaf模板引擎，前端CSS使用SemanticUI框架

原教程的持久层是用JPA做的，我改用了MyBatisPlus，使用的3.0.5版本，和新版可能有些区别，也是第一次使用这个插件，还不是很熟。用到了里面的自动填充策略，代码生成器，条件构造器，逻辑删除等功能

后端管理系统做了博客管理，分类管理，标签管理

前端页面做了首页，分类查询页，标签查询页，归档页，关于我页，集成了简单的搜索功能

目前基本就是参照教程把基本功能都实现了，前端页面我没有做更多的改动，应该不会对本项目做更多的改进了，纯练手用。之后应该会学习Vue，做前后端分离的项目
第一次撸项目，坑点还蛮多，改bug一改就到深夜了，现在终于知道断点有啥用了23333，就是不停的打断点调试，改完茅厕顿开的感觉还是很爽

建表文件参考了朱一鸣开源仓库的sql文件，做了一些小的改动

本人博客地址：https://wuminggao.cn