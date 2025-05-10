package completion_question_exams;

import common.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class gongwen {

    public static final String WORK_DIR = "E:\\document\\zq\\考公";
    private static final String STATS_FILE = "gongwen_question_stats.md";
    public static final List<String> KEY_WORDS = List.of("公文");

    public static void main(String[] args) throws IOException {
        List<Utils.Question> questions = Utils.getQuestions(WORK_DIR, KEY_WORDS, STATS_FILE);
        if (questions == null) {
            System.out.println("无题，退出");
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> Utils.saveStatistics(questions, STATS_FILE)));

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            Utils.Question q = questions.get(random.nextInt(questions.size()));
            Utils.runQuizSession(q, scanner);
        }
    }

}