package com.github.Luythen.GreenHouseServer.Service;

import com.github.Luythen.GreenHouseServer.Repository.PlantCrateRepository;
import com.github.Luythen.GreenHouseServer.Repository.PlantRepository;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.Luythen.GreenHouseServer.Exceptions.FindByException;
import com.github.Luythen.GreenHouseServer.Exceptions.InvalidAccessException;
import com.github.Luythen.GreenHouseServer.Exceptions.PlantCrateHasActivePlantException;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Model.PlantModel;

@Service 
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantCrateRepository plantCrateRepository;
    private final GreenHouseSerivce greenHouseSerivce;

    PlantService(PlantRepository plantRepository, PlantCrateRepository plantCrateRepository, GreenHouseSerivce greenHouseSerivce) {
        this.plantRepository = plantRepository;
        this.plantCrateRepository = plantCrateRepository;
        this.greenHouseSerivce = greenHouseSerivce;
    }

    public PlantModel plantVegetable (String crateId, String plantType, String userId) {
        PlantCrateModel plantCrateModel = plantCrateRepository.findById(crateId).orElseThrow(() -> new FindByException("Kunde inte hitta en växthus låda med den idn"));
        if (crateHasActivePlant(crateId)) throw new PlantCrateHasActivePlantException("Lådan innehåller redan en växt");
        GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userId);
        if (!greenHouseModel.getId().equals(plantCrateModel.getGreenHouseId())) throw new InvalidAccessException("Du har inte tillgång till denna låda");

        PlantModel plantModel = new PlantModel();

        plantModel.setActive(true);
        plantModel.setPlantCrateId(plantCrateModel.getId());
        plantModel.setPlantedAt(Instant.now());
        plantModel.setPlantType(plantType);

        return plantRepository.insert(plantModel);
    }

    public void unPlant (String plantId, String userId) {
        PlantModel plantModel = plantRepository.findById(plantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (!plantModel.getActive()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        PlantCrateModel plantCrateModel = plantCrateRepository.findById(plantModel.getPlantCrateId()).orElseThrow(() -> new FindByException("Kunde inte hitta en växthus låda med den idn"));
        GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userId);
        if (!greenHouseModel.getId().equals(plantCrateModel.getGreenHouseId())) throw new InvalidAccessException("Du har inte tillgång till denna låda");

        plantModel.setActive(false);
        plantModel.setRemovedAt(Instant.now());

        plantRepository.save(plantModel);
    }

    private boolean crateHasActivePlant (String crateId) {
        List<PlantModel> plist = plantRepository.findByPlantCrateId(crateId);
        List<PlantModel> activePlant = plist.stream().filter((p) -> p.getActive()).toList();

        return activePlant.size() > 0;
    }

}
