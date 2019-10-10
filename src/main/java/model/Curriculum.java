package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


@XmlRootElement(name = "curriculum")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter @Getter @NoArgsConstructor
public class Curriculum {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "lecture")
    private List<String> lecturesList;

}
