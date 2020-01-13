# shiro-springboot
shiro相关学习项目
鉴于数据源切换的部分问题，当前多数据源只支持mybatis方法，jpa方式无效，可以改用注解的方式，也可进一步调整，推荐dao层mybatis与jpa只使用一种方式.后续可以开发一下项目jpa模式的分支，当前主用mybatis的方式\
使用Mybatis时，使用PageHelper进行分页，\
使用springboot jpa时，使用PageRequest进行分页，已开发对应的工具类PageUtil\
PageHelper分页插件相关问题已解决\
Mybatis一对多多对多配置已完成\
已集成事务，支持多数据源事务，异常回滚\
下阶段，dubbo的使用
