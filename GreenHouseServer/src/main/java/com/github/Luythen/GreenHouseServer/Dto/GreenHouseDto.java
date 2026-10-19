package com.github.Luythen.GreenHouseServer.Dto;

import java.util.List;

public record GreenHouseDto(String id, String name, List<CrateDto> crates) {}
