package com.github.Luythen.GreenHouseServer.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Luythen.GreenHouseServer.Dto.PlantVegetable;
import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Model.PlantModel;
import com.github.Luythen.GreenHouseServer.Service.PlantService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/plant")
public class PlantController {

    private final PlantService plantService;

    public PlantController (PlantService plantService) {
        this.plantService = plantService;
    }


    @PostMapping("/vegetable")
    public ResponseEntity<?> PlantNewVegetable (@RequestBody PlantVegetable plantVegetable) {
        PlantModel plantModel = plantService.plantVegetable(plantVegetable.crateId(), plantVegetable.plantType());

        return ResponseEntity.ok(plantModel);
    }

    @PatchMapping("/unplant/{id}")
    public ResponseEntity<ResponseDto> removePlant (@PathVariable String id) {
        plantService.unPlant(id);

        return ResponseEntity.ok(new ResponseDto(200, "Växt har blivit rensad"));
    }
    
    
}
