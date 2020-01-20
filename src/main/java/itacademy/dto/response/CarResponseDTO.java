package itacademy.dto.response;

import itacademy.dto.CarModelDTO;
import itacademy.dto.EngineDTO;
import itacademy.model.enums.Body;
import itacademy.model.enums.Drive;
import itacademy.model.enums.Gearbox;

public class CarResponseDTO {

    private Long id;

    private Gearbox gearbox;

    private Drive drive;

    private Body body;

    private Integer seats;

    private CarModelDTO carModel;

    private EngineDTO engine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public CarModelDTO getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDTO carModel) {
        this.carModel = carModel;
    }

    public EngineDTO getEngine() {
        return engine;
    }

    public void setEngine(EngineDTO engine) {
        this.engine = engine;
    }
}
