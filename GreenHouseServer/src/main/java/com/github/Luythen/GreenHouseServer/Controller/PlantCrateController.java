package com.github.Luythen.GreenHouseServer.Controller;

import com.github.Luythen.GreenHouseServer.Service.PlantCrateService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.PlantCrateDto;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.UserModel;
import org.springframework.web.bind.annotation.PutMapping;


@PreAuthorize("GH_User")
@RestController
@RequestMapping("/plantcrate")
public class PlantCrateController {

    private final PlantCrateService plantCrateService;

    PlantCrateController(PlantCrateService plantCrateService) {
        this.plantCrateService = plantCrateService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlantCrate (@AuthenticationPrincipal UserModel userModel, @RequestBody PlantCrateDto plantCrateDto) {
        try {
            return ResponseEntity.ok(plantCrateService.addNewPlantCrate(userModel.getId(), plantCrateDto.label(), plantCrateDto.sensorId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<ResponseDto> removePlantCrate (@AuthenticationPrincipal UserModel userModel, @PathVariable String id) {
        try {
            plantCrateService.removePlantCrateById(id, userModel.getId());

            return ResponseEntity.ok(new ResponseDto(200, "Plantcrate have successfully been removed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePlantCrate (@AuthenticationPrincipal UserModel userModel, @PathVariable String plantCrateId, @RequestBody PlantCrateDto pCrateDto) {
        try {
            return ResponseEntity.ok(plantCrateService.updatePlantCrate(pCrateDto, userModel.getId(), plantCrateId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
        }
    }

}
