package np.edu.nast.demoapp.quizapp.contracts;

/**
 * Created on 13/11/2017.
 *
 * @author Sanjay Tamata
 */

public class AppContract {
    private AppContract() {
    }

    public class PreferencesKeys {
        public final static String SUBJECT_QUIZ = "quiz_subject";
        public static final String SUBJECT_GK = "gk_subject";
        public static final String SUBJECT_SCIENCE = "science_subject";
    }

    public class PreferencesValues {
        public final static String QUIZ = "quiz";
        public static final String GK = "gk";
        public static final String SCIENCE = "science";
    }

    public static class Extras {
        public final static String DATA = "data";
    }

}
