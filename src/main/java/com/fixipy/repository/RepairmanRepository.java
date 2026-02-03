package com.fixipy.repository;

import com.fixipy.entity.Repairman;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairmanRepository extends MongoRepository<Repairman, String> {
}
