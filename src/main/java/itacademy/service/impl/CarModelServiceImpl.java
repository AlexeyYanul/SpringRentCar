package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.CarModel;
import itacademy.repository.CarModelRepository;
import itacademy.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service(value = "carModelService")
@Transactional
public class CarModelServiceImpl implements CarModelService {

    private CarModelRepository carModelRepository;

    private LocalizedMessageSource localizedMessageSource;

    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository, LocalizedMessageSource localizedMessageSource) {
        this.carModelRepository = carModelRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<CarModel> getCarModels() {
        return carModelRepository.findAll();
    }

    @Override
    public CarModel getById(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.idIsNull", new Object[]{}));
        Optional<CarModel> carModel = carModelRepository.findById(id);
        if (!carModel.isPresent()) {
            throw  new EntityNotFoundException(localizedMessageSource.getMessage("error.carModel.notFound", new Object[]{}));
        }
        return carModel.get();
    }

    @Override
    public CarModel saveCarModel(CarModel carModel) {
        if (carModel.getId() != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carModel.notHaveId", new Object[]{}));
        checkDublicate(carModel);
        return carModelRepository.save(carModel);
    }

    @Override
    public CarModel updateCarModel(CarModel carModel) {
        if (carModel.getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carModel.haveId", new Object[]{}));
        checkDublicate(carModel);
        return carModelRepository.save(carModel);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.haveId", new Object[]{}));
        carModelRepository.deleteById(id);
    }

    public List<CarModel> getByExample(CarModel carModel){
        Example<CarModel> modelExample = Example.of(carModel);
        return carModelRepository.findAll(modelExample);
    }

    private void checkDublicate(CarModel carModel){
        List<CarModel> dublicateCarModel = getByExample(carModel);
        if (!dublicateCarModel.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.carModel.notUnique", new Object[]{}));
    }
}
