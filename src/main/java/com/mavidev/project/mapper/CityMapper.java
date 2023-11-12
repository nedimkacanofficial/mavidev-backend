package com.mavidev.project.mapper;

import com.mavidev.project.dto.request.CityRequestDTO;
import com.mavidev.project.dto.response.CityResponseDTO;
import com.mavidev.project.entity.City;

import java.util.List;
import java.util.stream.Collectors;

public class CityMapper {
    public static City toEntity(CityRequestDTO cityRequestDTO) {
        City city = new City();
        city.setName(cityRequestDTO.getName());
        return city;
    }

    public static CityResponseDTO toDTO(City city) {
        CityResponseDTO cityResponseDTO = new CityResponseDTO();
        cityResponseDTO.setId(city.getId());
        cityResponseDTO.setName(city.getName());
        return cityResponseDTO;
    }

    public static List<City> toEntityList(List<CityRequestDTO> cityRequestDTOList) {
        return cityRequestDTOList.stream().map(CityMapper::toEntity).collect(Collectors.toList());
    }

    public static List<CityResponseDTO> toDTOList(List<City> cityList) {
        return cityList.stream().map(CityMapper::toDTO).collect(Collectors.toList());
    }
}