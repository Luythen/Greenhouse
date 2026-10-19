package com.github.Luythen.GreenHouseServer.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.Luythen.GreenHouseServer.Dto.GreenHouseCrateDto;
import com.github.Luythen.GreenHouseServer.Dto.GreenHouseDto;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Model.PlantModel;
import com.github.Luythen.GreenHouseServer.Repository.GreenHouseRepository;
import com.github.Luythen.GreenHouseServer.Repository.PlantCrateRepository;
import com.github.Luythen.GreenHouseServer.Repository.PlantRepository;

@Service 
public class GreenHouseSerivce {

    private final PlantCrateRepository plantCrateRepository;
    private final GreenHouseRepository greenHouseRepository;
    private final PlantRepository plantRepository;

    public GreenHouseSerivce (PlantCrateRepository plantCrateRepository, GreenHouseRepository greenHouseRepository, PlantRepository plantRepository) {
        this.plantCrateRepository = plantCrateRepository;
        this.greenHouseRepository = greenHouseRepository;
        this.plantRepository = plantRepository;
    }

    public void createNewGreenHouse (String userId, String firstName) throws Exception {
        try {
            GreenHouseModel greenHouseModel = new GreenHouseModel();

            greenHouseModel.setName(firstName + " GreenHouse");
            greenHouseModel.setOwnerId(userId);

            greenHouseRepository.insert(greenHouseModel);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean doesGreenHouseExits (String Id) {
        return greenHouseRepository.findById(Id).isPresent();
    }

    public GreenHouseModel findGreenHouseByOwnerId (String id) throws Exception {
        return greenHouseRepository.findByOwnerId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public GreenHouseDto getGreenHouseDashboard (String userId) throws Exception {
        GreenHouseModel greenHouseModel = greenHouseRepository.findByOwnerId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<PlantCrateModel> plantCrateModels = plantCrateRepository.findByGreenHouseId(greenHouseModel.getId());
        /* Make a list of all plantCrate ids */
        List<String> ids = plantCrateModels.stream().map(PlantCrateModel::getId).toList();

        Map<String, List<PlantModel>> plants = new HashMap<>();

        /* Go through all ids and find any plant related to the id */
        for (String id : ids) {
            plants.put(id, plantRepository.findByPlantCrateId(id));
        }

        /* map through all of the plantCrates and in order to create the crateDto with relevant information and also any plants that is connected to the crate */
        List<GreenHouseCrateDto> crates = plantCrateModels.stream().map(pcm -> 
            new GreenHouseCrateDto(pcm.getId(), 
                        pcm.getLabel(), 
                        pcm.getMoistureSensorConnectionId(), 
                        plants.getOrDefault(pcm.getId(), List.of())))
                    .toList();
        
        return new GreenHouseDto(greenHouseModel.getId(), greenHouseModel.getName(), crates);
    }

}
