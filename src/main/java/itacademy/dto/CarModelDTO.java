package itacademy.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CarModelDTO {

    private Long id;

    @NotNull(message = "{carModel.name.notNull}")
    @NotEmpty(message = "{carModel.name.notEmpty}")
    private String name;

    @NotNull(message = "{carModel.year.notNull}")
    @NotEmpty(message = "{carModel.year.notEmpty}")
    private Integer year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
