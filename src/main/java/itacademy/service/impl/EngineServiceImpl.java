package itacademy.service.impl;

import itacademy.model.Engine;
import itacademy.repository.EngineRepository;
import itacademy.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "engineService")
@Transactional
public class EngineServiceImpl implements EngineService {

    private EngineRepository engineRepository;

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    public List<Engine> getEnginesList() {
        return engineRepository.findAll();
    }

    public Engine getById(Long id) {
        Optional<Engine> engine = engineRepository.findById(id);
        if (!engine.isPresent()) {
            return null;
        }
        return engine.get();
    }

    public Engine saveEngine(Engine engine) {
        return engineRepository.save(engine);
    }
}
