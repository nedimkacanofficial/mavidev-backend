package com.mavidev.project.controller;

import com.mavidev.project.dto.request.CityRequestDTO;
import com.mavidev.project.dto.response.CityResponseDTO;
import com.mavidev.project.service.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "City Controller")
public class CityController {
    private final CityService cityService;

    /**
     * Method that handles the REST request to fetch all cities.
     *
     * @return ResponseEntity containing a list of all cities.
     */
    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> getAll() {
        log.info("REST request to get all City");
        List<CityResponseDTO> cityResponseDTOList = this.cityService.getAll();
        if (cityResponseDTOList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityResponseDTOList, HttpStatus.OK);
    }

    /**
     * Method that handles the REST request to save a new city.
     *
     * @param cityRequestDTO CityRequestDTO object containing the information of the city to be saved.
     * @return ResponseEntity containing the saved city.
     * @throws Exception An exception may be thrown during the save operation.
     */
    @PostMapping
    public ResponseEntity<CityResponseDTO> insert(@Valid @RequestBody CityRequestDTO cityRequestDTO) throws Exception {
        log.info("REST request to save City : {}", cityRequestDTO.getClass().getSimpleName());
        CityResponseDTO cityResponseDTO = this.cityService.insert(cityRequestDTO);
        if (cityResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cityResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Method that handles the REST request to update a city.
     *
     * @param cityRequestDTO CityRequestDTO object containing the information of the city to be updated.
     * @param id             The ID of the city to be updated.
     * @return ResponseEntity containing the updated city.
     * @throws Exception An exception may be thrown during the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDTO> update(@Valid @RequestBody CityRequestDTO cityRequestDTO, @PathVariable(name = "id") Long id) throws Exception {
        log.info("REST request to update City Id : {}, {}", id, cityRequestDTO.getClass().getSimpleName());
        CityResponseDTO cityResponseDTO = this.cityService.update(cityRequestDTO, id);
        if (cityResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cityResponseDTO, HttpStatus.OK);
    }

    /**
     * Method that handles the REST request to delete a specific city by its ID.
     *
     * @param id The ID of the city to be deleted.
     * @return ResponseEntity containing the deleted city.
     * @throws Exception An exception may be thrown during the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CityResponseDTO> deleteById(@PathVariable(name = "id") Long id) throws Exception {
        log.debug("REST request to delete City Id : {}", id);
        CityResponseDTO cityResponseDTO = this.cityService.deleteById(id);
        if (cityResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cityResponseDTO, HttpStatus.OK);
    }
}