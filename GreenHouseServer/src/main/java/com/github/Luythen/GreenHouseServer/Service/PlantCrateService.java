package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Dto.PlantCrateDto;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Repository.PlantCrateRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlantCrateService {
    
    private final PlantCrateRepository plantCrateRepository;
    private final GreenHouseSerivce greenHouseSerivce;

    PlantCrateService(PlantCrateRepository plantCrateRepository, GreenHouseSerivce greenHouseSerivce) {
        this.plantCrateRepository = plantCrateRepository;
        this.greenHouseSerivce = greenHouseSerivce;
    }

    public PlantCrateModel addNewPlantCrate (String userId, String crateLabel, String SensorId) throws Exception {
        try {
            PlantCrateModel plantCrateModel = new PlantCrateModel();
            GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userId);

            plantCrateModel.setGreenHouseId(greenHouseModel.getId());
            plantCrateModel.setLabel(crateLabel);
            plantCrateModel.setMoistureSensorConnectionId(SensorId);

            return plantCrateRepository.insert(plantCrateModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void removePlantCrateById (String id, String userId) throws Exception {
        try {
            PlantCrateModel plantCrateModel = plantCrateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            String greenHouseId = greenHouseSerivce.findGreenHouseByOwnerId(userId).getId();
            if (!plantCrateModel.getGreenHouseId().equals(greenHouseId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            plantCrateRepository.delete(plantCrateModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public PlantCrateModel updatePlantCrate (PlantCrateDto plantCrateDto, String userId, String id) throws Exception {
        try {
            PlantCrateModel plantCrateModel = plantCrateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userId);
            
            if (!plantCrateModel.getGreenHouseId().equals(greenHouseModel.getId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            plantCrateModel.setLabel(plantCrateDto.label());
            plantCrateModel.setMoistureSensorConnectionId(plantCrateDto.sensorId());

            return plantCrateRepository.save(plantCrateModel);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

}
