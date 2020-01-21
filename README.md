# springboot-lwohvye

对于连不上github的情况，编辑hosts文件，添加
192.30.253.113  github.com
51.101.25.194 github.global.ssl.fastly.net
192.30.253.121 codeload.github.com
即可解决

shiro相关学习项目
鉴于数据源切换的部分问题，当前多数据源只支持mybatis方法，jpa方式无效，可以改用注解的方式，也可进一步调整，推荐dao层mybatis与jpa只使用一种方式.后续可以开发一下项目jpa模式的分支，当前主用mybatis的方式\
使用Mybatis时，使用PageHelper进行分页，\
使用springboot jpa时，使用PageRequest进行分页，已开发对应的工具类PageUtil\
PageHelper分页插件相关问题已解决\
Mybatis一对多多对多配置已完成\
已集成事务，支持多数据源事务，异常回滚\
重构代码，调整返回格式\
逐渐弱化fastjson的使用，改用jackson\
下阶段，dubbo的使用\
需排查jpa的删除问题（角色删除及权限删除是否连带删除关联表中内容），若不删除最多会导致关联表有无用数据，由于才能内关联，不出出现程序问题

使用CentOS 7搭载Docker，使用mysql主从容器和redis容器\
CentOS 8暂由于未知原因，无法正常使用docker相关，容器内部连不上网。