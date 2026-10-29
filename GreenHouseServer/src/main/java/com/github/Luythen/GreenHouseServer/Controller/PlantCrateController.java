package com.github.Luythen.GreenHouseServer.Controller;

import com.github.Luythen.GreenHouseServer.Service.PlantCrateService;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.GreenHouseCrateDto;
import com.github.Luythen.GreenHouseServer.Dto.PlantCrateDto;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Model.PlantModel;
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
        PlantCrateModel plantCrateModel = plantCrateService.addNewPlantCrate(userModel.getId(), plantCrateDto.label(), plantCrateDto.sensorId());
            return ResponseEntity.ok().body(new GreenHouseCrateDto(plantCrateModel.getId(), plantCrateModel.getLabel(),plantCrateModel.getMoistureSensorConnectionId(), new ArrayList<PlantModel>()));
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<ResponseDto> removePlantCrate (@AuthenticationPrincipal UserModel userModel, @PathVariable String id) {
        plantCrateService.removePlantCrateById(id, userModel.getId());

        return ResponseEntity.ok(new ResponseDto(200, "Lådan har blivit bort tagen"));
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePlantCrate (@AuthenticationPrincipal UserModel userModel, @PathVariable String id, @RequestBody PlantCrateDto pCrateDto) {
        return ResponseEntity.ok(plantCrateService.updatePlantCrate(pCrateDto, userModel.getId(), id));
    }

}
