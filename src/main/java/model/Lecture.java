package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "lecture")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter @NoArgsConstructor
public class Lecture {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "roombookings")
    @XmlElement(name="booking")
    private ArrayList<Booking> bookings;

    public String getCurriculum(List<Curriculum> curriculumList) {
        return curriculumList.stream()
                    .filter(curriculum -> curriculum.getLecturesList().contains(this.id))
                    .findFirst()
                    .map(curriculum -> curriculum.getName())
                    .orElse(null);


    }

}
