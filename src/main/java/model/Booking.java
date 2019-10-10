

package model;

import adapter.LocalTimeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalTime;
import java.util.Date;


@XmlRootElement(name = "booking")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter @NoArgsConstructor
public class Booking {

    @XmlElement(name = "room")
    private String room;

    @XmlElement(name = "weekday")
    private String weekDay;

    @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class)
    @XmlElement(name = "startTime")
    private LocalTime startTime;

    @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class)
    @XmlElement(name = "endTime")
    private LocalTime endTime;

    private Lecture lecture;

}
