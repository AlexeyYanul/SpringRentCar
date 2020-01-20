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

/**
 * The type Engine service.
 */
@Service(value = "engineService")
@Transactional
public class EngineServiceImpl implements EngineService {

    private EngineRepository engineRepository;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Engine service.
     *
     * @param engineRepository       the engine repository
     * @param localizedMessageSource the localized message source
     */
    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository, LocalizedMessageSource localizedMessageSource) {
        this.engineRepository = engineRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all engines.
     *
     * @return the engines list
     */
    @Override
    public List<Engine> getEnginesList() {
        return engineRepository.findAll();
    }

    /**
     * Gets by id.
     *
     * @param id the engine id
     * @return the engine
     */
    @Override
    public Engine getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<Engine> engine = engineRepository.findById(id);
        if (!engine.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.engine.notFound", new Object[]{}));
        }
        return engine.get();
    }

    /**
     * Save engine.
     *
     * @param engine the engine
     * @return engine
     */
    @Override
    public Engine saveEngine(Engine engine) {
        validate(engine.getId() != null, "error.engine.notHaveId");
        checkDuplicate(engine);
        return engineRepository.save(engine);
    }

    /**
     * Update engine.
     *
     * @param engine the engine
     * @return the response entity
     */
    @Override
    public Engine updateEngine(Engine engine) {
        validate(engine.getId() == null, "error.engine.haveId");
        checkDuplicate(engine);
        return engineRepository.save(engine);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void deleteEngien(Long id) {
        getById(id);
        engineRepository.deleteById(id);
    }

    /**
     * Gets by example.
     *
     * @param engine the engine
     * @return the by example
     */
    public List<Engine> getByExample(Engine engine) {
        Example<Engine> engineExample = Example.of(engine);
        return engineRepository.findAll(engineExample);
    }

    /**
     * Check duplicate in the database.
     *
     * @param engine the engine
     */
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
