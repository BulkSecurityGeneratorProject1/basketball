package com.mycompany.myapp.domain.DTO;

import java.time.LocalDate;

/**
 * Created by 25369405Z on 20/01/2017.
 */
public class EvolutionpDTO {

    private LocalDate time;
    private Long count;


    public EvolutionpDTO(){

    }

    public EvolutionpDTO(LocalDate time, Long count) {
        this.time = time;
        this.count = count;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
