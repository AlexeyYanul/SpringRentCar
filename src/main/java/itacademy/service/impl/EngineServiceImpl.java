package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.Engine;
import itacademy.repository.EngineRepository;
import itacademy.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service(value = "engineService")
@Transactional
public class EngineServiceImpl implements EngineService {

    private EngineRepository engineRepository;

    private LocalizedMessageSource localizedMessageSource;

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository, LocalizedMessageSource localizedMessageSource) {
        this.engineRepository = engineRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<Engine> getEnginesList() {
        return engineRepository.findAll();
    }

    @Override
    public Engine getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<Engine> engine = engineRepository.findById(id);
        if (!engine.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.engine.notFound", new Object[]{}));
        }
        return engine.get();
    }

    @Override
    public Engine saveEngine(Engine engine) {
        validate(engine.getId() != null, "error.engine.notHaveId");
        checkDuplicate(engine);
        return engineRepository.save(engine);
    }

    @Override
    public Engine updateEngine(Engine engine) {
        validate(engine.getId() == null, "error.engine.haveId");
        checkDuplicate(engine);
        return engineRepository.save(engine);
    }

    @Override
    public void deleteEngien(Long id) {
        getById(id);
        engineRepository.deleteById(id);
    }

    public List<Engine> getByExample(Engine engine) {
        Example<Engine> engineExample = Example.of(engine);
        return engineRepository.findAll(engineExample);
    }

    private void checkDuplicate(Engine engine) {
        List<Engine> duplicateCarModel = getByExample(engine);
        validate(!duplicateCarModel.isEmpty(), "error.engine.notUnique");
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new NullPointerException(errorMessage);
        }
    }
}
