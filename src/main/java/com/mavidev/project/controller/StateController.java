package com.mavidev.project.controller;

import com.mavidev.project.dto.request.StateRequestDTO;
import com.mavidev.project.dto.response.StateResponseDTO;
import com.mavidev.project.service.StateService;
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
@RequestMapping("/states")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "State Controller")
public class StateController {
    private final StateService stateService;

    /**
     * Method that handles the REST request to fetch all states.
     *
     * @return ResponseEntity containing a list of all states.
     */
    @GetMapping
    public ResponseEntity<List<StateResponseDTO>> getAll() {
        log.info("REST request to get all State");
        List<StateResponseDTO> stateResponseDTOList = this.stateService.getAll();
        if (stateResponseDTOList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stateResponseDTOList, HttpStatus.OK);
    }

    /**
     * Method that handles the REST request to save a new state.
     *
     * @param stateRequestDTO StateRequestDTO object containing the information of the state to be saved.
     * @return ResponseEntity containing the saved state.
     * @throws Exception An exception may be thrown during the save operation.
     */
    @PostMapping
    public ResponseEntity<StateResponseDTO> insert(@Valid @RequestBody StateRequestDTO stateRequestDTO) throws Exception {
        log.info("REST request to save State : {}", stateRequestDTO.getClass().getSimpleName());
        StateResponseDTO stateResponseDTO = this.stateService.insert(stateRequestDTO);
        if (stateResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stateResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Method that handles the REST request to update a state.
     *
     * @param stateRequestDTO StateRequestDTO object containing the information of the state to be updated.
     * @param id              The ID of the state to be updated.
     * @return ResponseEntity containing the updated state.
     * @throws Exception An exception may be thrown during the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StateResponseDTO> update(@Valid @RequestBody StateRequestDTO stateRequestDTO, @PathVariable(name = "id") Long id) throws Exception {
        log.info("REST request to update State Id : {}, {}", id, stateRequestDTO.getClass().getSimpleName());
        StateResponseDTO stateResponseDTO = this.stateService.update(stateRequestDTO, id);
        if (stateResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stateResponseDTO, HttpStatus.OK);
    }

    /**
     * Method that handles the REST request to delete a specific state by its ID.
     *
     * @param id The ID of the state to be deleted.
     * @return ResponseEntity containing the deleted state.
     * @throws Exception An exception may be thrown during the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<StateResponseDTO> deleteById(@PathVariable(name = "id") Long id) throws Exception {
        log.debug("REST request to delete State Id : {}", id);
        StateResponseDTO stateResponseDTO = this.stateService.deleteById(id);
        if (stateResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stateResponseDTO, HttpStatus.OK);
    }
}