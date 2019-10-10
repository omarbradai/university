import model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final String TIMETABLE_XML = "src/main/resources/timetable.xml";

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        // CREATE CONTEXT
        JAXBContext context = JAXBContext.newInstance(University.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        System.out.println("Output from our XML File: ");
        Unmarshaller um = context.createUnmarshaller();
        File file = new File(TIMETABLE_XML);
        University university = (University) um.unmarshal(file);
        List<Lecture> lectureList = university.getLectures();
        List<Conflict> roomConflicts = new ArrayList<>();
        List<Conflict> curricularConflicts = new ArrayList<>();








        for(int i=0; i<lectureList.size()-1; ++i) {
            Lecture currentLecture = lectureList.get(i);
            List<Lecture> lecturesToCompareWith = lectureList.subList(i+1, lectureList.size()-1);
            for(Lecture lectureToCheckWith : lecturesToCompareWith) {
                if (hasRoomConflict(currentLecture, lectureToCheckWith)) {
                    Conflict conflict = new Conflict();
                    conflict.setFirstLecture(currentLecture);
                    conflict.setSecondLecture(lectureToCheckWith);
                    roomConflicts.add(conflict);
                }

                if (hasCurricularConflict(university.getCurriculumList(), currentLecture, lectureToCheckWith)) {
                    Conflict conflict = new Conflict();
                    conflict.setFirstLecture(currentLecture);
                    conflict.setSecondLecture(lectureToCheckWith);
                    curricularConflicts.add(conflict);
                }
            }
        }

        System.out.println(roomConflicts.size());
        System.out.println(curricularConflicts.size());


    }

    public static boolean hasRoomConflict(Lecture firstLecture, Lecture secondLecture) {
        boolean hasConflict = false;
        for(Booking firstBooking : firstLecture.getBookings()) {
            for (Booking secondBooking: secondLecture.getBookings()) {
                if (firstBooking.getWeekDay().equals(secondBooking.getWeekDay()) && firstBooking.getRoom().equals(secondBooking.getRoom()) && hasTimeConflict(firstBooking, secondBooking)) {
                    hasConflict = true;
                    break;
                }
            }
            if (hasConflict) {
                break;
            }
        }

        return hasConflict;
    }

    public static boolean hasTimeConflict(Booking firstBooking, Booking secondBooking) {
        if (firstBooking.getStartTime().isBefore(firstBooking.getStartTime()) && firstBooking.getEndTime().isAfter(secondBooking.getStartTime())
            || firstBooking.getStartTime().isAfter(secondBooking.getStartTime()) && secondBooking.getEndTime().isAfter(firstBooking.getStartTime())
        ) {
            return true;
        }

        return false;
    }

    public static boolean hasCurricularConflict(List<Curriculum> curriculumList, Lecture firstLecture, Lecture secondLecture) {
        if (firstLecture.getCurriculum(curriculumList).equals(secondLecture.getCurriculum(curriculumList))) {
            boolean hasConflict = false;

            for(Booking firstBooking : firstLecture.getBookings()) {
                for (Booking secondBooking: secondLecture.getBookings()) {
                    if (firstBooking.getWeekDay().equals(secondBooking.getWeekDay()) && hasTimeConflict(firstBooking, secondBooking)) {
                        hasConflict = true;
                        break;
                    }
                }
                if (hasConflict) {
                    break;
                }
            }

            return hasConflict;
        }

        return false;
    }

}
