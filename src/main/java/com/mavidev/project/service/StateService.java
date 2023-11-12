package com.mavidev.project.service;

import com.mavidev.project.dto.request.StateRequestDTO;
import com.mavidev.project.dto.response.StateResponseDTO;
import com.mavidev.project.entity.City;
import com.mavidev.project.entity.State;
import com.mavidev.project.exception.AlreadyExistException;
import com.mavidev.project.exception.NotFoundException;
import com.mavidev.project.mapper.StateMapper;
import com.mavidev.project.repository.CityRepository;
import com.mavidev.project.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    /**
     * Method used to retrieve all states.
     *
     * @return A list of StateResponseDTO containing all states.
     */
    @Transactional(readOnly = true)
    public List<StateResponseDTO> getAll() {
        log.info("Request to get all State");
        List<State> stateList = this.stateRepository.findAll();
        return StateMapper.toDTOList(stateList);
    }

    /**
     * Method used to save a specified state.
     *
     * @param stateRequestDTO DTO (Data Transfer Object) containing state information to be saved.
     * @return DTO containing information of the saved state.
     * @throws NotFoundException     Thrown if a city with the specified identifier is not found.
     * @throws AlreadyExistException Thrown if a state with the specified name and city identifier already exists.
     */
    public StateResponseDTO insert(StateRequestDTO stateRequestDTO) throws NotFoundException {
        log.info("Request to save State : {}", stateRequestDTO.getClass().getSimpleName());
        if (!this.stateRepository.existsByNameIgnoreCaseAndCity_Id(stateRequestDTO.getName(), stateRequestDTO.getCityId())) {
            City city = this.cityRepository.findById(stateRequestDTO.getCityId()).orElseThrow(() -> new NotFoundException("stateId: " + stateRequestDTO.getCityId()));
            State state = this.stateRepository.save(StateMapper.toEntity(stateRequestDTO, city));
            return StateMapper.toDTO(state);
        }
        throw new AlreadyExistException("name: " + stateRequestDTO.getName() + " cityId: " + stateRequestDTO.getCityId());
    }

    /**
     * Updates the information of a state associated with the specified identity (ID).
     *
     * @param stateRequestDTO Data transfer object containing the information of the state to be updated.
     * @param id              Identity (ID) of the state to be updated.
     * @return Data transfer object containing the information of the updated state.
     * @throws NotFoundException     Thrown if a state cannot be found with the specified identity (ID).
     * @throws AlreadyExistException Thrown during the update process if another state with the same name and city identity exists.
     */
    public StateResponseDTO update(StateRequestDTO stateRequestDTO, Long id) throws NotFoundException {
        log.info("Request to update State Id: {}, {}", id, stateRequestDTO.getClass().getSimpleName());
        if (!this.stateRepository.existsByNameIgnoreCaseAndCity_Id(stateRequestDTO.getName(), stateRequestDTO.getCityId())) {
            State state = this.stateRepository.findById(id).orElseThrow(() -> new NotFoundException("stateId: " + id));
            City cityToUpdate = cityRepository.findById(stateRequestDTO.getCityId()).orElseThrow(() -> new NotFoundException("City not found with id: " + stateRequestDTO.getCityId()));
            state.setName(stateRequestDTO.getName());
            state.setCity(cityToUpdate);
            return StateMapper.toDTO(this.stateRepository.save(state));
        }
        throw new AlreadyExistException("name: " + stateRequestDTO.getName() + " cityId: " + stateRequestDTO.getCityId());
    }

    /**
     * Method used to delete a specific state by its ID.
     *
     * @param id The ID of the state to be deleted.
     * @return A StateResponseDTO object containing the deleted state.
     * @throws NotFoundException An exception of NotFoundException is thrown if the state with the specified ID is not found.
     */
    public StateResponseDTO deleteById(Long id) throws NotFoundException {
        log.info("Request to delete State Id : {}", id);
        State state = this.stateRepository.findById(id).orElseThrow(() -> new NotFoundException("stateId: " + id));
        this.stateRepository.deleteById(state.getId());
        return StateMapper.toDTO(state);
    }
}