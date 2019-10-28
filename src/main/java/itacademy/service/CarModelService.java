package itacademy.service;

import itacademy.model.CarModel;
import itacademy.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CarModelService {

    private CarModelRepository carModelRepository;

    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public List<CarModel> getCarModels(){
        return carModelRepository.findAll();
    }

    public CarModel getById(Long id){
        Optional<CarModel> carModel = carModelRepository.findById(id);
        if (!carModel.isPresent()){
            return null;
        }
        return carModel.get();
    }

}
