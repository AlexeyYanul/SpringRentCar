package itacademy.service;

import itacademy.model.Engine;
import itacademy.repository.EngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Engine service.
 */
@Service
@Transactional
public class EngineService {

    private EngineRepository engineRepository;

    /**
     * Instantiates a new Engine service.
     *
     * @param engineRepository the engine repository
     */
    @Autowired
    public EngineService(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    /**
     * Gets engines list.
     *
     * @return the engines list
     */
    public List<Engine> getEnginesList() {
        return engineRepository.findAll();
    }

    /**
     * Gets engine by id.
     *
     * @param id the id
     * @return the by id
     */
    public Engine getById(Long id) {
        Optional<Engine> engine = engineRepository.findById(id);
        if (!engine.isPresent()) {
            return null;
        }
        return engine.get();
    }

    /**
     * Save transient or update detached/persistent engine.
     *
     * @param engine the engine
     */
    public void saveEngine(Engine engine) {
        engineRepository.save(engine);
    }
}
