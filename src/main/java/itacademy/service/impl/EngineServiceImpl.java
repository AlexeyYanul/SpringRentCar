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
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.idIsNull", new Object[]{}));
        Optional<Engine> engine = engineRepository.findById(id);
        if (!engine.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.engine.notFound", new Object[]{}));
        }
        return engine.get();
    }

    @Override
    public Engine saveEngine(Engine engine) {
        if (engine.getId() != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.notHaveId", new Object[]{}));
        List<Engine> dublicateEngine = getByExample(engine);
        if (!dublicateEngine.isEmpty()) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.notUnique", new Object[]{}));
        }
        return engineRepository.save(engine);
    }

    @Override
    public Engine updateEngine(Engine engine) {
        if (engine.getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.haveId", new Object[]{}));
        List<Engine> dublicateEngine = getByExample(engine);
        if (!dublicateEngine.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.notUnique", new Object[]{}));
        return engineRepository.save(engine);
    }

    @Override
    public void deleteEngien(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.haveId", new Object[]{}));
        engineRepository.deleteById(id);
    }

    public List<Engine> getByExample(Engine engine) {
        Example<Engine> engineExample = Example.of(engine);
        return engineRepository.findAll(engineExample);
    }
}
