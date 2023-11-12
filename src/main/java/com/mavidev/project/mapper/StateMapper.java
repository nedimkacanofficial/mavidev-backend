package com.mavidev.project.mapper;

import com.mavidev.project.dto.request.StateRequestDTO;
import com.mavidev.project.dto.response.StateResponseDTO;
import com.mavidev.project.entity.City;
import com.mavidev.project.entity.State;

import java.util.List;
import java.util.stream.Collectors;

public class StateMapper {
    public static State toEntity(StateRequestDTO stateDTO, City city) {
        State state = new State();
        state.setName(stateDTO.getName());
        state.setCity(city);
        return state;
    }

    public static StateResponseDTO toDTO(State state) {
        StateResponseDTO stateResponseDTO = new StateResponseDTO();
        stateResponseDTO.setId(state.getId());
        stateResponseDTO.setName(state.getName());
        stateResponseDTO.setCityName(state.getCity().getName());
        return stateResponseDTO;
    }

    public static List<State> toEntityList(List<StateRequestDTO> stateRequestDTOList, City city) {
        return stateRequestDTOList.stream().map(stateDTO -> toEntity(stateDTO, city)).collect(Collectors.toList());
    }

    public static List<StateResponseDTO> toDTOList(List<State> stateList) {
        return stateList.stream().map(StateMapper::toDTO).collect(Collectors.toList());
    }
}