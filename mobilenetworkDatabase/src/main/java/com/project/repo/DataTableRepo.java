package com.project.repo;

import com.project.dto.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTableRepo extends JpaRepository<DataTable, Long> {
    @Query("SELECT count(d) from DataTable d")
    long countTotalCalls();
}