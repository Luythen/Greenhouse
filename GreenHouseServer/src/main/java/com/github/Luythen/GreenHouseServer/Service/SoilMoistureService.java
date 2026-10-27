package com.github.Luythen.GreenHouseServer.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.Luythen.GreenHouseServer.Dto.MeasurementDto;
import com.github.Luythen.GreenHouseServer.Exceptions.FindByException;
import com.github.Luythen.GreenHouseServer.Exceptions.InvalidFieldException;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.SoilMoistureModel;
import com.github.Luythen.GreenHouseServer.Repository.GreenHouseRepository;
import com.github.Luythen.GreenHouseServer.Repository.SoilMoistureRepository;

@Service 
public class SoilMoistureService {

    private final SoilMoistureRepository soilMoistureRepository;
    private final GreenHouseRepository greenHouseRepository;

    public SoilMoistureService (SoilMoistureRepository soilMoistureRepository, GreenHouseRepository greenHouseRepository) {
        this.soilMoistureRepository = soilMoistureRepository;
        this.greenHouseRepository = greenHouseRepository;
    }

    public List<SoilMoistureModel> addNewSoilMoistureMeasurement (String greenHouseId, List<MeasurementDto> measurements) {
        GreenHouseModel greenHouseModel = greenHouseRepository.findById(greenHouseId).orElseThrow(() -> new FindByException("Kunde inte hitta ett växthus med den idn"));
        if (measurements.size() == 0) throw new InvalidFieldException("Listan är tom");
        Instant currentTime = Instant.now();
        
        List<SoilMoistureModel> sList = new ArrayList<>();
        for (MeasurementDto meas : measurements) {
            SoilMoistureModel soilMoistureModel = new SoilMoistureModel();

            soilMoistureModel.setGreenHouseId(greenHouseModel.getId());
            soilMoistureModel.setMeasurement(meas.measurement());
            soilMoistureModel.setTime(currentTime);
            soilMoistureModel.setMoistureSensorId(meas.sensorId());

            sList.add(soilMoistureModel);
        }

        return soilMoistureRepository.insert(sList);
    }

    public List<SoilMoistureModel> getLastestMoistMeasurements (String greenHouseId) {
        return soilMoistureRepository.findAllTopByGreenHouseIdOrderByTimeDesc(greenHouseId);
    }

}
