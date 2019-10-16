package service;

import model.*;

import java.util.*;

public class LectureService {

    private LectureService() {}

    private static LectureService INSTANCE = null;

    public static synchronized LectureService getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new LectureService();
        }

        return INSTANCE;
    }

    public static final String ROOM_CONFLICTS_KEY = "roomConflicts";
    public static final String CURRICULAR_CONFLICTS_KEY = "curricularConflicts";


    /**
     * return room of conflicts and curricular conflicts
     * @param university
     * @return a map that contains room conflicts and curricular conflicts
     */
    public Map<String, List<Conflict>> getConflicts(University university) {
        List<Lecture> lectureList = university.getLectures();
        List<Conflict> roomConflicts = new ArrayList<>();
        List<Conflict> curricularConflicts = new ArrayList<>();

        Map<String, List<Conflict>> conflicts = new HashMap<>();
        ListIterator<Lecture> lectureIterator = lectureList.listIterator();

        lectureIterator.forEachRemaining(lecture -> {
            List<Lecture> lecturesToCompareWith = lectureList.subList(lectureIterator.nextIndex(), lectureList.size()-1);
            ListIterator<Lecture> lectureToCompareIterator = lecturesToCompareWith.listIterator();
            lectureToCompareIterator.forEachRemaining(lectureToCheckWith -> {
                if (this.hasRoomConflict(lecture, lectureToCheckWith)) {
                    Conflict conflict = new Conflict();
                    conflict.setFirstLecture(lecture);
                    conflict.setSecondLecture(lectureToCheckWith);
                    roomConflicts.add(conflict);
                }

                if (this.hasCurricularConflict(university.getCurriculumList(), lecture, lectureToCheckWith)) {
                    Conflict conflict = new Conflict();
                    conflict.setFirstLecture(lecture);
                    conflict.setSecondLecture(lectureToCheckWith);
                    curricularConflicts.add(conflict);
                }
            });
        });

        conflicts.put(ROOM_CONFLICTS_KEY, roomConflicts);
        conflicts.put(CURRICULAR_CONFLICTS_KEY, curricularConflicts);

        return conflicts;
    }

    /**
     * check if there is a room conflict between the two lecture
     * @param firstLecture
     * @param secondLecture
     * @return true if a room  conflict exists
     */
    public boolean hasRoomConflict(Lecture firstLecture, Lecture secondLecture) {

        boolean hasConflict = firstLecture.getBookings().stream()
            .anyMatch(firstBooking ->
                secondLecture.getBookings().stream()
                    .anyMatch(secondBooking -> firstBooking.getWeekDay().equals(secondBooking.getWeekDay())
                                    && firstBooking.getRoom().equals(secondBooking.getRoom())
                                    && hasTimeConflict(firstBooking, secondBooking)
                    )
            );

        return hasConflict;
    }

    /**
     * check if there is a time conflict between the two lecture
     * @param firstBooking
     * @param secondBooking
     * @return true if a time  conflict exists
     */
    public boolean hasTimeConflict(Booking firstBooking, Booking secondBooking) {
        if (firstBooking.getStartTime().isBefore(firstBooking.getStartTime()) && firstBooking.getEndTime().isAfter(secondBooking.getStartTime())
                || firstBooking.getStartTime().isAfter(secondBooking.getStartTime()) && secondBooking.getEndTime().isAfter(firstBooking.getStartTime())
        ) {
            return true;
        }

        return false;
    }

    /**
     * check if there is a curricular conflict between the two lecture
     * @param curriculumList
     * @param firstLecture
     * @param secondLecture
     * @return true if a curricular conflict exists
     */
    public boolean hasCurricularConflict(List<Curriculum> curriculumList, Lecture firstLecture, Lecture secondLecture) {
        if (firstLecture.getCurriculum(curriculumList).equals(secondLecture.getCurriculum(curriculumList))) {
            boolean hasConflict =  firstLecture.getBookings().stream()
                    .anyMatch(firstBooking -> secondLecture.getBookings().stream()
                        .anyMatch(secondBooking -> firstBooking.getWeekDay().equals(secondBooking.getWeekDay()) && this.hasTimeConflict(firstBooking, secondBooking)));

            return hasConflict;
        }

        return false;
    }
}
