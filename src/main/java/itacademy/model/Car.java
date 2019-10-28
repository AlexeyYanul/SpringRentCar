package itacademy.model;

import itacademy.model.enums.Body;
import itacademy.model.enums.Drive;
import itacademy.model.enums.Gearbox;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    @Enumerated(EnumType.STRING)
    private Drive drive;

    @Enumerated(EnumType.STRING)
    private Body body;

    private Integer seats;

    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;

    @OneToOne(mappedBy = "car")
    private CarInfo carInfo;

    public Car() {
    }

    public Car(Gearbox gearbox, Drive drive, Body body, Integer seats, byte[] image, CarModel carModel, Engine engine) {
        this.gearbox = gearbox;
        this.drive = drive;
        this.body = body;
        this.seats = seats;
        this.image = image;
        this.carModel = carModel;
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", gearbox=" + gearbox +
                ", drive=" + drive +
                ", body=" + body +
                ", seats=" + seats +
                ", image=" + Arrays.toString(image) +
                ", carModel=" + carModel +
                ", engine=" + engine +
                '}';
    }

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

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }
}
