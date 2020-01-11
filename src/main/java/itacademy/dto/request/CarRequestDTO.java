package itacademy.dto.request;

import itacademy.model.enums.Body;
import itacademy.model.enums.Drive;
import itacademy.model.enums.Gearbox;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CarRequestDTO {

    private Long id;

    @NotNull(message = "{car.gearbox.notNull}")
    @NotEmpty(message = "{car.gearbox.notEmpty}")
    private Gearbox gearbox;

    @NotNull(message = "{car.drive.notNull}")
    @NotEmpty(message = "{car.drive.notEmpty}")
    private Drive drive;

    @NotNull(message = "{car.body.notNull}")
    @NotEmpty(message = "{car.body.notEmpty}")
    private Body body;

    @NotNull(message = "{car.seats.notNull}")
    @NotEmpty(message = "{car.seats.notEmpty}")
    private Integer seats;

    @NotNull(message = "{car.model.notNull}")
    @NotEmpty(message = "{car.model.notEmpty}")
    private Long CarModelId;

    @NotNull(message = "{car.engine.notNull}")
    @NotEmpty(message = "{car.engine.notEmpty}")
    private Long engineId;

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

    public Long getCarModelId() {
        return CarModelId;
    }

    public void setCarModelId(Long carModelId) {
        CarModelId = carModelId;
    }

    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }
}
