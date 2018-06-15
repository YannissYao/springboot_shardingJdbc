package com.joeysin.conf;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Joeysin on  2018/6/15  下午4:58.
 * Describe：table分表算法
 */
public class ProgramShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

    /**
     * Created by Joeysin on  2018/6/15  下午6:05.
     * Describe：单个插入||查询规则
     */
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Created by Joeysin on  2018/6/15  下午6:06.
     * Describe：批量插入||查询 规则
     */
    public Collection<String> doInSharding(Collection<String> availableTargetNames,ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());
        Collection<Integer> values = shardingValue.getValues();
        for (Integer value : values) {
            for (String tableNames : availableTargetNames) {
                if (tableNames.endsWith(value % 2 + "")) {
                    result.add(tableNames);
                }
            }
        }
        return result;
    }

    /**
     * Created by Joeysin on  2018/6/15  下午6:06.
     * Describe：范围查询规则
     */
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(availableTargetNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

}
