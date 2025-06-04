package com.project.controller;

import com.project.dto.DataTable;
import com.project.repo.DataTableRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DataTableControllerTest {

    private DataTableRepo dataTableRepo;
    private DataTableController dataTableController;

    @BeforeEach
    void setUp() {
        dataTableRepo = mock(DataTableRepo.class);
        dataTableController = new DataTableController(dataTableRepo);
    }

    @Test
    void testGetAllCallData_ReturnsListOfDataTable() {
        // Arrange
        DataTable record1 = new DataTable(); // You can set fields if needed
        DataTable record2 = new DataTable();
        List<DataTable> mockList = Arrays.asList(record1, record2);

        when(dataTableRepo.findAll()).thenReturn(mockList);

        // Act
        List<DataTable> result = dataTableController.getAllCallData();

        // Assert
        assertEquals(2, result.size());
        verify(dataTableRepo, times(1)).findAll();
    }
}
