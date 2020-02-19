# springboot-lwohvye

对于连不上github的情况，编辑hosts文件，添加\
192.30.253.113  github.com
51.101.25.194 github.global.ssl.fastly.net
192.30.253.121 codeload.github.com
即可解决

springboot相关学习项目，用到mybatis、jpa、pageHelper、shiro、异步编程、数据源切换、druid、dubbo等技术\
鉴于数据源切换的部分问题，当前多数据源只支持mybatis方法，jpa方式无效，可以改用注解的方式，也可进一步调整，推荐dao层mybatis与jpa只使用一种方式.后续可以开发一下项目jpa模式的分支，当前主用mybatis的方式\
使用Mybatis时，使用PageHelper进行分页，\
使用springboot jpa时，使用PageRequest进行分页，已开发对应的工具类PageUtil\
PageHelper分页插件相关问题已解决\
Mybatis一对多多对多配置已完成\
已集成事务，支持多数据源事务，异常回滚\
重构代码，调整返回格式\
逐渐弱化fastjson的使用，改用jackson\
行政区划信息查询，支持单次多地区查询，使用异步编程，配合数据库索引，提高查询效率。使用索引时，需注意规避会导致索引失效的情况\
经测试对比，使用异步编程，即便只是查数据库，不包含其他的处理逻辑，较单线程，也能大幅提高效率。\
项目后端添加用户信息中手机号校验\
项目使用postman进行压力测试，测试异步方法，未出现线程安全问题\
项目已构建dubbo分支，当前shiro及redis功能正常\
swagger由于配置扫描包错误，导致异常，已解决\
druid管理模块在provider层，需要通过8085接口访问\
分模块后，依赖分散，需寻找统一解决的方法\
使用第三方工具包Hutool，包含常用工具类中的功能，后续调整项目代码，尽量使用该工具包\
使用redis的布隆过滤器，过滤掉无关的条件项，用于避免缓存穿透问题\
使用SpringTask实现定时任务，定时更新布隆过滤器中内容\
当把消费层异步调用生产层时，当存在多个生产层，也会类似负载均衡（比如行政区划的多线程接口，当存在多个provider时，会将任务分给多个provider，从而进一步提高效率）
Controller层向页面跳转时，需注意，地址前不要加/ ，比如跳转登陆页面 return "/login" ;在开发时能正常，但以jar部署时就会报错，所以需使用 return "login"; \
quartz定时任务框架的使用，下阶段\
使用springSecurity和JWT用于认证和授权，下阶段\
当前knife4j框架的参数忽略依旧存在问题，待解决\
集群与session共享:由于当前已将session放入redis中，所以已实现session的共享，在jar方式部署的集群项目中，验证无问题;
项目采用4个consumer层，2个provider层的方式，使用nginx进行反向代理，各consumer层能够正常获取session。已完成\
需排查jpa的删除问题（角色删除及权限删除是否连带删除关联表中内容），若不删除最多会导致关联表有无用数据，由于采用内关联，不会出现程序问题\

使用CentOS 7搭载Docker，使用mysql主从容器和redis容器,集成dubbo后使用了zookeeper容器\
CentOS 8暂由于未知原因，无法正常使用docker相关，容器内部连不上网。
