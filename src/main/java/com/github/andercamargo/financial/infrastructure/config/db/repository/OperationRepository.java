package com.github.andercamargo.financial.infrastructure.config.db.repository;

import com.github.andercamargo.financial.infrastructure.common.dto.TotalBalanceDTO;
import com.github.andercamargo.financial.infrastructure.config.db.schema.OperationSchema;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OperationRepository  extends MongoRepository<OperationSchema, UUID> {
    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}}")
    List<OperationSchema> findByDate(LocalDate start, LocalDate end);
    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}, 'type': ?2}")
    List<OperationSchema> findByDateAndType(LocalDate start, LocalDate end, String type);
    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}, 'walletName': ?2, 'customerName': ?3}")
    List<OperationSchema> findByDateAndIds(LocalDate start, LocalDate end, String walletName, String customerName);

    //https://www.mongodb.com/community/forums/t/mongo-aggregation-results-not-getting-mapped-after-spring-boot-upgrade/209179
    @Aggregation(pipeline = {
            "{$match: { 'date':{ $gte: ?0, $lte: ?1}, 'walletName': ?2, 'customerName': ?3}}",
            "{$group: { _id: '$date', totalBalance: { $sum: '$amount' } } }"
    })
    List<TotalBalanceDTO> getBalance(LocalDate start, LocalDate end, String walletName, String customerName);
}
