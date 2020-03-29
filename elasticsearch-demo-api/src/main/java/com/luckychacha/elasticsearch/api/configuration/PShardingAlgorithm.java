package com.luckychacha.elasticsearch.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class PShardingAlgorithm implements PreciseShardingAlgorithm<Timestamp> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Timestamp> preciseShardingValue) {
        log.info("p" + String.format(
                "%s_%s",
                preciseShardingValue.getLogicTableName(),
                preciseShardingValue.getValue().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMM"))
        ));
        return String.format(
                "%s_%s",
                preciseShardingValue.getLogicTableName(),
                preciseShardingValue.getValue().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMM"))
        );
    }

}
