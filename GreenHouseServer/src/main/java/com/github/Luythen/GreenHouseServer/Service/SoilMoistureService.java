package com.github.Luythen.GreenHouseServer.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.Luythen.GreenHouseServer.Dto.MeasurementDto;
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

    public List<SoilMoistureModel> addNewSoilMoistureMeasurement (String greenHouseId, List<MeasurementDto> measurements) throws ResponseStatusException {
        GreenHouseModel greenHouseModel = greenHouseRepository.findById(greenHouseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Instant currentTime = Instant.now();
        
        try {
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
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<SoilMoistureModel> getLastestMoistMeasurements (String greenHouseId) throws ResponseStatusException {
        try {
            return soilMoistureRepository.findAllTopByGreenHouseIdOrderByTimeDesc(greenHouseId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
