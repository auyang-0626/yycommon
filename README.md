# yycommon
    limit 模块实现了限流，支持两种限流方式：
            a。基于固定时间窗口的限流
                优点： 实现简单？ 
                缺点： 流量控制力度比较粗，无法应对流量特别集中的突发情况
            b. 令牌桶算法
                优点： 能够应对流量集中的情况，整形效果比较好
                缺点： 流量特别集中的时候容易误杀，应该配合等待时间（阻塞）使用
                
           使用姿势：
               boolean acquire = limitFacade.acquireIgnoreException("test-resource",10000);
               
               配置信息：
                     @Bean
                       public LimitFacade getLimitFacade(){
                           LimitFacade limitFacade = new LimitFacade();
                           //   参数含义  QPS限流，令牌桶算法，资源，限流窗口，限流窗口内最多允许通过的流量
                           limitFacade.limitConfigMap.put("test-resource",new LimitConfig(LimitType.QPS,LimitAlgorithm.TOKEN_BUCKET,"test-resource",1000L,100L));
                           limitFacade.limitConfigMap.put("user-resource",new LimitConfig(LimitType.QPS,LimitAlgorithm.FIXED_TIME,"user-resource",1000L,20L));
                           return limitFacade;
                       }
               扩展：
                  a. 可以把配置做成注解
                  b. 也可以坐在filter里面，自动拦截url,配合配置中心动态调整限流
                  c. 可以使用redis作为计数中心，实现集群限流
                  d. 上面我三个都没做，因为我懒~~
    
    jdk demo 模块是个人阅读jdk源码的时候的一些练习
                  
       
                
