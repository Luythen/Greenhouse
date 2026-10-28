package com.github.Luythen.GreenHouseServer.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.PlantVegetable;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.PlantModel;
import com.github.Luythen.GreenHouseServer.Model.UserModel;
import com.github.Luythen.GreenHouseServer.Service.PlantService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PreAuthorize("GH_User")
@RestController 
@RequestMapping("/plant")
public class PlantController {

    private final PlantService plantService;

    public PlantController (PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping("/vegetable")
    public ResponseEntity<?> PlantNewVegetable (@AuthenticationPrincipal UserModel userModel, @RequestBody PlantVegetable plantVegetable) {
        PlantModel plantModel = plantService.plantVegetable(plantVegetable.crateId(), plantVegetable.plantType(), userModel.getId());

        return ResponseEntity.ok(plantModel);
    }

    @PatchMapping("/unplant/{id}")
    public ResponseEntity<ResponseDto> removePlant (@AuthenticationPrincipal UserModel userModel, @PathVariable String id) {
        plantService.unPlant(id, userModel.getId());

        return ResponseEntity.ok(new ResponseDto(200, "Växt har blivit rensad"));
    }
    
    
}
