# shiro-springboot
shiro相关学习项目

当前认为，PageHelper插件失效，可能与SqlSessionFactory有关，怀疑未成功初始化
在项目启动时，并未成功初始化PageHelper相关插件，当前有两个方案
1、首先跑一下别人能成功的spring-boot项目，将部分环境调为跟当前项目一致
2、将项目先调为单数据源
3、判断会不会是mybatis集成包的问题