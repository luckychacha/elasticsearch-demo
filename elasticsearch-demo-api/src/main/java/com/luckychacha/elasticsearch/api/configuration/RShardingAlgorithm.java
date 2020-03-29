package com.luckychacha.elasticsearch.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class RShardingAlgorithm implements RangeShardingAlgorithm<Timestamp> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Timestamp> rangeShardingValue) {
        Collection<String> tables = new ArrayList<>();

//        Range<LocalDateTime> dates = rangeShardingValue.getValueRange();
        LocalDateTime start = rangeShardingValue.getValueRange().lowerEndpoint().toLocalDateTime();
        LocalDateTime end = rangeShardingValue.getValueRange().upperEndpoint().toLocalDateTime();
        while(start.isBefore(end)) {
            tables.add(
                    String.format(
                            "%s_%s",
                            rangeShardingValue.getLogicTableName(),
                            start.format(DateTimeFormatter.ofPattern("yyyyMM"))
                    )
            );
            start = start.plusMonths(1);
        }

        return tables;
    }

}
