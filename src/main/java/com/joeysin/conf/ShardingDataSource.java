package com.joeysin.conf;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joeysin on  2018/6/15  下午5:12.
 * Describe：分库分表规则设置
 */
@Component
public class ShardingDataSource {

    @Autowired
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource;

    private DataSource shardingDataSource;

    @PostConstruct
    public void init() {
        Map<String, DataSource> map = Maps.newHashMapWithExpectedSize(2);
        map.put("primaryDb", primaryDataSource);
        map.put("secondaryDb", secondaryDataSource);
        DataSourceRule dataSourceRule = new DataSourceRule(map);
        List<TableRule> tableRuleList = Lists.newArrayListWithCapacity(1);
        List<String> pList = new ArrayList<String>();
        //table分表介质 i ,i 取决于分表规则
        for (int i = 0; i < 2; i++) {
            pList.add("t_order_" + i);
        }
        // List<TableRule>
        tableRuleList.add(new TableRule.TableRuleBuilder("t_order").actualTables(pList).dataSourceRule(dataSourceRule)

                //分表规则详情 ProgramShardingAlgorithm.java
                .tableShardingStrategy(new TableShardingStrategy("order_id", new ProgramShardingAlgorithm())).build());


        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule)

                //DB路由规则配置;DB路由规则详情 SingleKeyModuloDatabaseShardingAlgorithm.java
                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new SingleKeyModuloDatabaseShardingAlgorithm()))

                //table路由规则配置
                .tableRules(tableRuleList).build();

        shardingDataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    public DataSource getShardingDataSource() {
        return shardingDataSource;
    }
}
