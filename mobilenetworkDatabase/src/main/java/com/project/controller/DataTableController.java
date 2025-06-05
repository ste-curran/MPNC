package com.project.controller;

import com.project.dto.DataTable;
import com.project.repo.DataTableRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/call-data")

public class DataTableController {
    private DataTableRepo dataTableRepo;
    public DataTableController(DataTableRepo dataTableRepo) {
        this.dataTableRepo = dataTableRepo;
    }
    @GetMapping
    public List<DataTable> getAllCallData() {
        return dataTableRepo.findAll();
    }
    @GetMapping("/total-calls")
    public long getTotalCalls() {
        return dataTableRepo.count();
    }
}
