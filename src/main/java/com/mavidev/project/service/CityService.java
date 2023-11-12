package com.mavidev.project.service;

import com.mavidev.project.dto.request.CityRequestDTO;
import com.mavidev.project.dto.response.CityResponseDTO;
import com.mavidev.project.entity.City;
import com.mavidev.project.exception.AlreadyExistException;
import com.mavidev.project.exception.NotFoundException;
import com.mavidev.project.mapper.CityMapper;
import com.mavidev.project.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    /**
     * Method to retrieve all cities.
     *
     * @return A list of CityResponseDTO containing all cities.
     */
    @Transactional(readOnly = true)
    public List<CityResponseDTO> getAll() {
        log.info("Request to get all City");
        List<City> cityList = this.cityRepository.findAll();
        return CityMapper.toDTOList(cityList);
    }

    /**
     * Method used to save a specified city.
     *
     * @param cityRequestDTO DTO (Data Transfer Object) containing city information.
     * @return DTO containing information of the saved city.
     * @throws AlreadyExistException Thrown if a city with the same name already exists.
     */
    public CityResponseDTO insert(CityRequestDTO cityRequestDTO) {
        log.info("Request to save City : {}", cityRequestDTO.getClass().getSimpleName());
        if (!this.cityRepository.existsByNameIgnoreCase(cityRequestDTO.getName())) {
            City city = this.cityRepository.save(CityMapper.toEntity(cityRequestDTO));
            return CityMapper.toDTO(city);
        }
        throw new AlreadyExistException("name: " + cityRequestDTO.getName());
    }

    /**
     * Method used to update a specified city.
     *
     * @param cityRequestDTO DTO (Data Transfer Object) containing updated city information.
     * @param id             Identifier of the city to be updated.
     * @return DTO containing information of the updated city.
     * @throws NotFoundException     Thrown if a city with the specified identifier is not found.
     * @throws AlreadyExistException Thrown if the updated city name conflicts with another city.
     */
    public CityResponseDTO update(CityRequestDTO cityRequestDTO, Long id) throws NotFoundException {
        log.info("Request to update City Id : {}, {}", id, cityRequestDTO.getClass().getSimpleName());
        if (!this.cityRepository.existsByNameIgnoreCase(cityRequestDTO.getName())) {
            City city = this.cityRepository.findById(id).orElseThrow(() -> new NotFoundException("cityId: " + id));
            city.setName(cityRequestDTO.getName());
            return CityMapper.toDTO(this.cityRepository.save(city));
        }
        throw new AlreadyExistException("name: " + cityRequestDTO.getName());
    }

    /**
     * Method used to delete a specific city by its ID.
     *
     * @param id The ID of the city to be deleted.
     * @return A CityResponseDTO object containing the deleted city.
     * @throws NotFoundException An exception of NotFoundException is thrown if the city with the specified ID is not found.
     */
    public CityResponseDTO deleteById(Long id) throws NotFoundException {
        log.info("Request to delete City Id : {}", id);
        City city = this.cityRepository.findById(id).orElseThrow(() -> new NotFoundException("cityId: {}" + id));
        this.cityRepository.deleteById(city.getId());
        return CityMapper.toDTO(city);
    }
}