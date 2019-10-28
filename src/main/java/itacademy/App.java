package itacademy;

import itacademy.configuration.AppConfig;
import itacademy.model.*;
import itacademy.model.enums.Body;
import itacademy.service.CarInfoService;
import itacademy.service.CarModelService;
import itacademy.service.CarService;
import itacademy.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CarModelService carModelService = context.getBean("carModelService", CarModelService.class);

        List<CarModel> carModels = carModelService.getCarModels();
        for (CarModel carModel : carModels){
            System.out.println(carModel);
        }

        CarModel model = carModelService.getById(1L);
        System.out.println(model);


    }
}
