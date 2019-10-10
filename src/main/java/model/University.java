package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "university")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
public class University implements Serializable {
    @XmlElementWrapper(name = "lectures")
    @XmlElement(name = "lecture")
    private List<Lecture> lectures;
    @XmlElementWrapper(name = "curricula")
    @XmlElement(name = "curriculum")
    private List<Curriculum> curriculumList;

}
