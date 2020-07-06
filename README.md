Spring Cloud
--
##
Default framework for service of web app 
Used 
#####Java 11,
#####Spring Cloud Hoxton.SR3
#####Spring Cloud Alibaba 2.3.0
#####Spring Boot 2.3.0
#####Undertow 2.0.30.Final(Netty 4.1.47.Final)
 Netty with Java 11 Required to set these JVM_OPTs for ignoring debug messages :
```
--add-opens java.base/jdk.internal.misc=ALL-UNNAMED
-Dio.netty.tryReflectionSetAccessible=true
```
#####MyBatis-Spring-Boot 2.1.2
#####Fastjson 1.2.62
