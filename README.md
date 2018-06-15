# springboot-shardingjdbc



简介：
    1.框架
        spring boot
        sharding-jdbc
        mybatis 
     
    2.数据库：
        在本地使用两个DB，分别为 test0,test1, sql脚本在项目中
        
        
    3.分库：
        使用 userId % 2 作为分库规则
        
        
    4.分表：
        使用 orderId % 2 作为分表规则
