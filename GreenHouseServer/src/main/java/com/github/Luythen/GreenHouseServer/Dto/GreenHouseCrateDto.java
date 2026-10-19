package com.github.Luythen.GreenHouseServer.Dto;

import java.util.List;

import com.github.Luythen.GreenHouseServer.Model.PlantModel;

public record GreenHouseCrateDto(String id, String label, String moistureSensorConnectionId, List<PlantModel> plants) {}
