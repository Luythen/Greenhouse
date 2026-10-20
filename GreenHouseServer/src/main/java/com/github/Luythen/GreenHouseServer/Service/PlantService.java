package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Repository.PlantCrateRepository;
import com.github.Luythen.GreenHouseServer.Repository.PlantRepository;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Model.PlantModel;

@Service 
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantCrateRepository plantCrateRepository;

    PlantService(PlantRepository plantRepository, PlantCrateRepository plantCrateRepository) {
        this.plantRepository = plantRepository;
        this.plantCrateRepository = plantCrateRepository;
    }

    public PlantModel plantVegetable (String crateId, String plantType) throws Exception {
        PlantCrateModel plantCrateModel = plantCrateRepository.findById(crateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (crateHasActivePlant(crateId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            PlantModel plantModel = new PlantModel();

            plantModel.setActive(true);
            plantModel.setPlantCrateId(plantCrateModel.getId());
            plantModel.setPlantedAt(Instant.now());
            plantModel.setPlantType(plantType);

            plantRepository.insert(plantModel);
            
            return plantModel;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void unPlant (String plantId) throws Exception {
        PlantModel plantModel = plantRepository.findById(plantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (!plantModel.getActive()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        try {
            plantModel.setActive(false);
            plantModel.setRemovedAt(Instant.now());

            plantRepository.save(plantModel);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean crateHasActivePlant (String crateId) {
        List<PlantModel> plist = plantRepository.findByPlantCrateId(crateId);
        List<PlantModel> activePlant = plist.stream().filter((p) -> p.getActive()).toList();

        return activePlant.size() > 0;
    }

}
