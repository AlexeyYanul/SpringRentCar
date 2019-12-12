package itacademy.dto.request;

import itacademy.model.enums.Body;
import itacademy.model.enums.Drive;
import itacademy.model.enums.Gearbox;

import javax.validation.constraints.NotNull;

public class CarRequestDTO {

    private Long id;

    private Gearbox gearbox;

    private Drive drive;

    private Body body;

    private Integer seats;

    private byte[] image;

    @NotNull
    private Long CarModelId;

    @NotNull
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
