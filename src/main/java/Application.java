import dao.FileAccess;
import model.*;
import service.LectureService;

import javax.xml.bind.JAXBException;
import java.util.*;

public class Application {

    private static final String TIMETABLE_XML = "src/main/resources/timetable.xml";

    public static void main(String[] args) {
        University university = new University();

        try {
            university = FileAccess.mapDataFromFile(TIMETABLE_XML);
        } catch (JAXBException e) {
            System.out.println("access file error");
        }
        LectureService lectureService = new LectureService();


        List<Lecture> lectureList = university.getLectures();
        Map<String, List<Conflict>> conflicts = lectureService.getConflicts(university, lectureList);
        List<Conflict> roomConflicts = new ArrayList<>();
        List<Conflict> curricularConflicts = new ArrayList<>();
        if (conflicts.containsKey(LectureService.ROOM_CONFLICTS_KEY)) {
            roomConflicts = conflicts.get(LectureService.ROOM_CONFLICTS_KEY);
        }
        if (conflicts.containsKey(LectureService.CURRICULAR_CONFLICTS_KEY)) {
            curricularConflicts = conflicts.get(LectureService.CURRICULAR_CONFLICTS_KEY);
        }

        System.out.println("room conflicts:");
        roomConflicts.forEach(conflict -> System.out.println(conflict.getFirstLecture().getId() + " - " + conflict.getSecondLecture().getId()));

        System.out.println("curricular conflicts");
        curricularConflicts.forEach(conflict -> System.out.println(conflict.getFirstLecture().getId() + " - " + conflict.getSecondLecture().getId()));

    }

}
