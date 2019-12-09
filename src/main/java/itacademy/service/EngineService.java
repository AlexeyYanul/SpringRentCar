package itacademy.service;

import itacademy.model.Engine;

import java.util.List;

public interface EngineService {
    List<Engine> getEnginesList();

    Engine getById(Long id);

    Engine saveEngine(Engine engine);
}
