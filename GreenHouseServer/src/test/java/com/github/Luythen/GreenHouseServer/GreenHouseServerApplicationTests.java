package com.github.Luythen.GreenHouseServer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.Luythen.GreenHouseServer.Dto.MeasurementDto;
import com.github.Luythen.GreenHouseServer.Dto.PlantCrateDto;
import com.github.Luythen.GreenHouseServer.Dto.RegisterDto;
import com.github.Luythen.GreenHouseServer.Exceptions.PlantCrateHasActivePlantException;
import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;
import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import com.github.Luythen.GreenHouseServer.Model.SoilMoistureModel;
import com.github.Luythen.GreenHouseServer.Model.UserModel;
import com.github.Luythen.GreenHouseServer.Repository.GreenHouseRepository;
import com.github.Luythen.GreenHouseServer.Repository.PlantRepository;
import com.github.Luythen.GreenHouseServer.Repository.UserRepository;
import com.github.Luythen.GreenHouseServer.Service.AuthService;
import com.github.Luythen.GreenHouseServer.Service.GreenHouseSerivce;
import com.github.Luythen.GreenHouseServer.Service.PlantCrateService;
import com.github.Luythen.GreenHouseServer.Service.PlantService;
import com.github.Luythen.GreenHouseServer.Service.SoilMoistureService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GreenHouseServerApplicationTests {

	@InjectMocks
	private AuthService authService;

	@InjectMocks
	GreenHouseSerivce greenHouseSerivce;

	@InjectMocks
	PlantCrateService plantCrateService;

	@InjectMocks
	PlantService plantService;

	@Mock
	private UserRepository userRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	private GreenHouseRepository greenHouseRepository;

	@Mock
	private SoilMoistureService soilMoistureService;

	@Mock
	private PlantRepository plantRepository;

	@BeforeEach
	public void setup () {
		when(passwordEncoder.encode(anyString())).thenReturn("hashed");
		when(userRepository.insert(any(UserModel.class))).thenAnswer(ans -> ans.getArgument(0)).thenThrow(new DuplicateKeyException("duplicate email"));
		lenient().when(greenHouseRepository.insert(any(GreenHouseModel.class))).thenAnswer(ans -> ans.getArgument(0));
	}

	@Test
	public void registerEmailDuplicationTest () {
		assertDoesNotThrow(() -> {
			authService.register(new RegisterDto("Test1", "Rest1", "test1@gmail.com", "test1"));
		});

		assertThrows(Exception.class, () -> {
			authService.register(new RegisterDto("Test2", "Rest2", "test1@gmail.com", "test2"));
		});
		
		verify(userRepository, times(2)).insert(any(UserModel.class));
	}

	@Test
	public void createANewGreenHouse () {
		try {
			UserModel userModel = authService.register(new RegisterDto("Test1", "Rest1", "test1@gmail.com", "test1"));
			assertDoesNotThrow(() -> {
				greenHouseSerivce.createNewGreenHouse(userModel.getId(), userModel.getFirstName());
			});

			assertTrue(greenHouseSerivce.doesGreenHouseExits(greenHouseRepository.findByOwnerId(userModel.getId()).get().getId()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void editPlantCrate () {
		try {
			UserModel userModel = authService.register(new RegisterDto("Test1", "Rest1", "test1@gmail.com", "test1"));
			greenHouseSerivce.createNewGreenHouse(userModel.getId(), userModel.getFirstName());
			
			PlantCrateModel plantCrateModel = plantCrateService.addNewPlantCrate(userModel.getId(), "Test label", null);
			assertDoesNotThrow(() -> {
				plantCrateService.updatePlantCrate(new PlantCrateDto("New label", "12"), userModel.getId(), plantCrateModel.getId());
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test 
	public void testAddAndGetSoilMeasurement () {
		try {
			UserModel userModel = authService.register(new RegisterDto("Test1", "Rest1", "test1@gmail.com", "test1"));
			greenHouseSerivce.createNewGreenHouse(userModel.getId(), userModel.getFirstName());

			GreenHouseModel greenHouseModel = greenHouseSerivce.findGreenHouseByOwnerId(userModel.getId());

			List<MeasurementDto> measurementDtos = new ArrayList<>();

			measurementDtos.add(new MeasurementDto("12",30));
			measurementDtos.add(new MeasurementDto("13",10));
			measurementDtos.add(new MeasurementDto("14",20));
			measurementDtos.add(new MeasurementDto("15",40));
			measurementDtos.add(new MeasurementDto("16",35));

			assertDoesNotThrow(() -> {
				soilMoistureService.addNewSoilMoistureMeasurement(greenHouseModel.getId(), measurementDtos);
			});

			List<SoilMoistureModel> sList = soilMoistureService.getLastestMoistMeasurements(greenHouseModel.getId());

			assertEquals(5, sList.size());

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@Test
	public void plantInCrateThatAlreadyHaveAPlant () {
		try {
			UserModel userModel = authService.register(new RegisterDto("Test1", "Rest1", "test1@gmail.com", "test1"));
			greenHouseSerivce.createNewGreenHouse(userModel.getId(), userModel.getFirstName());

			greenHouseSerivce.findGreenHouseByOwnerId(userModel.getId());

			PlantCrateModel plantCrateModel = plantCrateService.addNewPlantCrate(userModel.getId(),"Låda 1", null);

			plantService.plantVegetable(plantCrateModel.getId(),"Cucumber");

			assertThrows(PlantCrateHasActivePlantException.class, () -> {
				plantService.plantVegetable(plantCrateModel.getId(),"Cucumber");
			});


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
