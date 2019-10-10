package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Conflict {
    private Lecture firstLecture;
    private Lecture secondLecture;
}
