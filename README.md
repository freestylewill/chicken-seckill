# chicken-secKill
一个使用Spring Boot与前后端缓存、队列、分布式来秒杀小鸡的系统

* [x] 框架搭建
* [x] 用户登录、分布式session
* [x] 秒杀功能
* [x] JMeter压力测试
* [x] 页面级秒杀优化
* [x] 服务级秒杀优化(Redis + RabbitMQ)

--------------------------
以上是慕课网[《Java秒杀系统方案优化 高性能高并发实战》](https://coding.imooc.com/class/168.html)提供的秒杀系统解决方案  
为进一步提升系统性能、稳定性，同时提升自己分布式系统的构建能力，考虑使用以下中间件或分布式调度机制：

* [ ] 分布式锁，正在另外的[repo](https://github.com/xbox1994/distributed-lock-redis)开发
* [ ] 分布式事务
* [ ] 分布式限流