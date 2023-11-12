package com.mavidev.project.service;

import com.mavidev.project.dto.request.CityRequestDTO;
import com.mavidev.project.dto.response.CityResponseDTO;
import com.mavidev.project.entity.City;
import com.mavidev.project.exception.AlreadyExistException;
import com.mavidev.project.exception.NotFoundException;
import com.mavidev.project.mapper.CityMapper;
import com.mavidev.project.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testGetAllCities() {
        // Mocking repository behavior
        List<City> mockCityList = new ArrayList<>();
        mockCityList.add(new City(1L, "City1"));
        mockCityList.add(new City(2L, "City2"));

        when(cityRepository.findAll()).thenReturn(mockCityList);

        // Testing service method
        List<CityResponseDTO> result = cityService.getAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("City1", result.get(0).getName());
        assertEquals("City2", result.get(1).getName());
    }

    @Test
    public void testInsertCity() {
        // Mocking repository behavior
        CityRequestDTO requestDTO = new CityRequestDTO();
        requestDTO.setName("NewCity");

        when(cityRepository.existsByNameIgnoreCase("NewCity")).thenReturn(false);

        City cityToSave = CityMapper.toEntity(requestDTO);
        when(cityRepository.save(any(City.class))).thenReturn(cityToSave);

        // Testing service method
        CityResponseDTO result = cityService.insert(requestDTO);

        // Assertions
        assertEquals("NewCity", result.getName());
    }

    @Test
    public void testInsertCityWithExistingName() {
        // Mocking repository behavior
        CityRequestDTO requestDTO = new CityRequestDTO();
        requestDTO.setName("ExistingCity");

        when(cityRepository.existsByNameIgnoreCase("ExistingCity")).thenReturn(true);

        // Testing service method and expecting an exception
        assertThrows(AlreadyExistException.class, () -> cityService.insert(requestDTO));
    }

    @Test
    public void testUpdateCity() {
        // Mocking repository behavior
        Long cityId = 1L;
        CityRequestDTO requestDTO = new CityRequestDTO();
        requestDTO.setName("UpdatedCity");

        City existingCity = new City(cityId, "OldCity");
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.existsByNameIgnoreCase("UpdatedCity")).thenReturn(false);
        when(cityRepository.save(any(City.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Testing service method
        CityResponseDTO result = cityService.update(requestDTO, cityId);

        // Assertions
        assertEquals("UpdatedCity", result.getName());
    }

    @Test
    public void testUpdateCityWithNonExistingId() {
        // Mocking repository behavior
        Long nonExistingCityId = 99L;
        CityRequestDTO requestDTO = new CityRequestDTO();
        requestDTO.setName("UpdatedCity");

        when(cityRepository.findById(nonExistingCityId)).thenReturn(Optional.empty());

        // Testing service method and expecting an exception
        assertThrows(NotFoundException.class, () -> cityService.update(requestDTO, nonExistingCityId));
    }

    @Test
    public void testUpdateCityWithExistingName() {
        // Mocking repository behavior
        Long cityId = 1L;
        CityRequestDTO requestDTO = new CityRequestDTO();
        requestDTO.setName("ExistingCity");

        City existingCity = new City(cityId, "OldCity");
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.existsByNameIgnoreCase("ExistingCity")).thenReturn(true);

        // Testing service method and expecting an exception
        assertThrows(AlreadyExistException.class, () -> cityService.update(requestDTO, cityId));
    }

    @Test
    public void testDeleteCityById() {
        // Mocking repository behavior
        Long cityId = 1L;
        City existingCity = new City(cityId, "CityToDelete");

        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.save(any(City.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Testing service method
        CityResponseDTO result = cityService.deleteById(cityId);

        // Assertions
        assertEquals("CityToDelete", result.getName());
    }

    @Test
    public void testDeleteCityByNonExistingId() {
        // Mocking repository behavior
        Long nonExistingCityId = 99L;

        when(cityRepository.findById(nonExistingCityId)).thenReturn(Optional.empty());

        // Testing service method and expecting an exception
        assertThrows(NotFoundException.class, () -> cityService.deleteById(nonExistingCityId));
    }
}
