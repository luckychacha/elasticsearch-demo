package com.luckychacha.elasticsearch.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Slf4j
public class EqualShardingAlgorithm implements PreciseShardingAlgorithm<BigInteger> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<BigInteger> preciseShardingValue) {
        return null;
    }

}
