package com.serwis.repository;

import com.serwis.entity.Repairs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jakub on 29.05.2018.
 */
@Repository
public interface RepairsRepository extends JpaRepository<Repairs, Integer>{
}