package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Dto.GreenHouseCrateDto;
import com.github.Luythen.GreenHouseServer.Dto.PlantCrateDto;
import com.github.Luythen.GreenHouseServer.Exceptions.FindByException;
import com.github.Luythen.GreenHouseServer.Exceptions.NotFoundException;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Repository.PlantCrateRepository;
import com.github.Luythen.GreenHouseServer.Repository.PlantRepository;

import org.springframework.stereotype.Service;

@Service
public class PlantCrateService {
    
    private final PlantCrateRepository plantCrateRepository;
    private final GreenHouseSerivce greenHouseSerivce;
    private final PlantRepository plantRepository;

    PlantCrateService(PlantCrateRepository plantCrateRepository, GreenHouseSerivce greenHouseSerivce, PlantRepository plantRepository) {
        this.plantCrateRepository = plantCrateRepository;
        this.greenHouseSerivce = greenHouseSerivce;
        this.plantRepository = plantRepository;
    }

    public PlantCrateModel addNewPlantCrate (String userId, String crateLabel, String SensorId) {
        PlantCrateModel plantCrateModel = new PlantCrateModel();
        GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userId);

        plantCrateModel.setGreenHouseId(greenHouseModel.getId());
        plantCrateModel.setLabel(crateLabel);
        plantCrateModel.setMoistureSensorConnectionId(SensorId);

        return plantCrateRepository.insert(plantCrateModel);
    }

    public void removePlantCrateById (String id, String userId) {
        PlantCrateModel plantCrateModel = plantCrateRepository.findById(id).orElseThrow(() -> new FindByException("Kunde inte hitta någon låda med den idn"));
        String greenHouseId = greenHouseSerivce.findGreenHouseByOwnerId(userId).getId();
        if (!plantCrateModel.getGreenHouseId().equals(greenHouseId)) throw new NotFoundException("Lådan tillhör ej växthuset\"");

        plantCrateRepository.delete(plantCrateModel);
    }

    public GreenHouseCrateDto updatePlantCrate (PlantCrateDto plantCrateDto, String userId, String id) {
        PlantCrateModel plantCrateModel = plantCrateRepository.findById(id).orElseThrow(() -> new FindByException("Kunde inte hitta någon låda med den idn"));
        GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userId);
            
        if (!plantCrateModel.getGreenHouseId().equals(greenHouseModel.getId())) throw new NotFoundException("Lådan tillhör ej växthuset");

        plantCrateModel.setLabel(plantCrateDto.label());
        plantCrateModel.setMoistureSensorConnectionId(plantCrateDto.sensorId());
        plantCrateRepository.save(plantCrateModel);

        return new GreenHouseCrateDto(plantCrateModel.getId(), plantCrateModel.getLabel(), plantCrateModel.getMoistureSensorConnectionId(), plantRepository.findByPlantCrateId(id));

    }

}
