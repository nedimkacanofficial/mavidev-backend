package com.mavidev.project.service;

import com.mavidev.project.dto.request.StateRequestDTO;
import com.mavidev.project.dto.response.StateResponseDTO;
import com.mavidev.project.entity.City;
import com.mavidev.project.entity.State;
import com.mavidev.project.exception.AlreadyExistException;
import com.mavidev.project.exception.NotFoundException;
import com.mavidev.project.repository.CityRepository;
import com.mavidev.project.repository.StateRepository;
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
public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private StateService stateService;

    @Test
    public void testGetAllStates() {
        // Mocking repository behavior
        List<State> mockStateList = new ArrayList<>();
        mockStateList.add(new State(1L, "State1", new City(1L, "City1")));
        mockStateList.add(new State(2L, "State2", new City(2L, "City2")));

        when(stateRepository.findAll()).thenReturn(mockStateList);

        // Testing service method
        List<StateResponseDTO> result = stateService.getAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("State1", result.get(0).getName());
        assertEquals("City1", result.get(0).getCityName());
        assertEquals("State2", result.get(1).getName());
        assertEquals("City2", result.get(1).getCityName());
    }

    @Test
    public void testInsertState() throws NotFoundException {
        // Mocking repository behavior
        StateRequestDTO requestDTO = new StateRequestDTO();
        requestDTO.setName("NewState");
        requestDTO.setCityId(1L);

        when(stateRepository.existsByNameIgnoreCaseAndCity_Id("NewState", 1L)).thenReturn(false);
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City(1L, "City1")));
        when(stateRepository.save(any(State.class))).thenReturn(new State(1L, "NewState", new City(1L, "City1")));

        // Testing service method
        StateResponseDTO result = stateService.insert(requestDTO);

        // Assertions
        assertEquals("NewState", result.getName());
        assertEquals("City1", result.getCityName());
    }

    @Test
    public void testInsertStateWithExistingNameAndCity() {
        // Mocking repository behavior
        StateRequestDTO requestDTO = new StateRequestDTO();
        requestDTO.setName("ExistingState");
        requestDTO.setCityId(1L);

        when(stateRepository.existsByNameIgnoreCaseAndCity_Id("ExistingState", 1L)).thenReturn(true);

        // Testing service method and expecting an exception
        assertThrows(AlreadyExistException.class, () -> stateService.insert(requestDTO));
    }

    @Test
    public void testUpdateState() throws NotFoundException {
        // Mocking repository behavior
        Long stateId = 1L;
        StateRequestDTO requestDTO = new StateRequestDTO();
        requestDTO.setName("UpdatedState");
        requestDTO.setCityId(1L);

        State existingState = new State(stateId, "OldState", new City(1L, "City1"));
        when(stateRepository.findById(stateId)).thenReturn(Optional.of(existingState));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City(1L, "City1")));
        when(stateRepository.existsByNameIgnoreCaseAndCity_Id("UpdatedState", 1L)).thenReturn(false);
        when(stateRepository.save(any(State.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Testing service method
        StateResponseDTO result = stateService.update(requestDTO, stateId);

        // Assertions
        assertEquals("UpdatedState", result.getName());
        assertEquals("City1", result.getCityName());
    }

    @Test
    public void testUpdateStateWithNonExistingId() {
        // Mocking repository behavior
        Long nonExistingStateId = 99L;
        StateRequestDTO requestDTO = new StateRequestDTO();
        requestDTO.setName("UpdatedState");
        requestDTO.setCityId(1L);

        when(stateRepository.findById(nonExistingStateId)).thenReturn(Optional.empty());

        // Testing service method and expecting an exception
        assertThrows(NotFoundException.class, () -> stateService.update(requestDTO, nonExistingStateId));
    }

    @Test
    public void testUpdateStateWithExistingNameAndCity() {
        // Mocking repository behavior
        Long stateId = 1L;
        StateRequestDTO requestDTO = new StateRequestDTO();
        requestDTO.setName("ExistingState");
        requestDTO.setCityId(1L);

        State existingState = new State(stateId, "OldState", new City(1L, "City1"));
        when(stateRepository.findById(stateId)).thenReturn(Optional.of(existingState));
        when(stateRepository.existsByNameIgnoreCaseAndCity_Id("ExistingState", 1L)).thenReturn(true);

        // Testing service method and expecting an exception
        assertThrows(AlreadyExistException.class, () -> stateService.update(requestDTO, stateId));
    }

    @Test
    public void testDeleteStateById() throws NotFoundException {
        // Mocking repository behavior
        Long stateId = 1L;
        State existingState = new State(stateId, "StateToDelete", new City(1L, "City1"));

        when(stateRepository.findById(stateId)).thenReturn(Optional.of(existingState));
        when(stateRepository.save(any(State.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Testing service method
        StateResponseDTO result = stateService.deleteById(stateId);

        // Assertions
        assertEquals("StateToDelete", result.getName());
        assertEquals("City1", result.getCityName());
    }

    @Test
    public void testDeleteStateByNonExistingId() {
        // Mocking repository behavior
        Long nonExistingStateId = 99L;

        when(stateRepository.findById(nonExistingStateId)).thenReturn(Optional.empty());

        // Testing service method and expecting an exception
        assertThrows(NotFoundException.class, () -> stateService.deleteById(nonExistingStateId));
    }
}
