package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Room {
    private String name;
    private List<Booking> bookings;
}
