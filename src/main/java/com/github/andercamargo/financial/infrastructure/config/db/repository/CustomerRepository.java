package com.github.andercamargo.financial.infrastructure.config.db.repository;

import com.github.andercamargo.financial.infrastructure.config.db.schema.CustomerSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.UUID;

public interface CustomerRepository extends MongoRepository<CustomerSchema, UUID> {
    @Query(value = "{ 'name' : ?0, 'wallets.name' : ?1 }")
    CustomerSchema findByUserNameAndWallet(String name, String walletName);

    @Query(value = "{ 'name' : ?0}")
    CustomerSchema findByUserName(String name);
}
