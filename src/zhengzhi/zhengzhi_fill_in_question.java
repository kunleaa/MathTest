package zhengzhi;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class zhengzhi_fill_in_question {

    static class Question {
        final String rawText;
        final String displayText;
        final List<String> answers;
        int totalAttempts;
        int correctCount;

        public Question(String rawText, String displayText, List<String> answers) {
            this.rawText = rawText;
            this.displayText = displayText;
            this.answers = answers;
        }

        public double getAccuracy() {
            return totalAttempts > 0 ? (correctCount * 100.0 / totalAttempts) : 0;
        }
    }

    private static final String STATS_FILE = "zhengzhi_fill_in_question_stats.md";
    private static final Pattern ANSWER_PATTERN = Pattern.compile("<([^>]+)>");
    private static final String CSV_SPLIT = "‖";

    public static void main(String[] args) {
        List<Question> questions = loadQuestions("E:\\document\\zq\\考公\\z 检测本——政治.md");
        if (questions == null) return;

        loadStatistics(questions);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveStatistics(questions)));

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            Question q = selectQuestion(questions, random);
            runQuizSession(q, scanner);
        }
    }

    private static List<Question> loadQuestions(String path) {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> answers = new ArrayList<>();
                Matcher matcher = ANSWER_PATTERN.matcher(line);
                StringBuffer displayText = new StringBuffer();

                while (matcher.find()) {
                    answers.add(matcher.group(1));
                    matcher.appendReplacement(displayText, "______");
                }
                matcher.appendTail(displayText);

                if (!answers.isEmpty()) {
                    questions.add(new Question(line, displayText.toString(), answers));
                }
            }
        } catch (IOException e) {
            System.err.println("题目加载失败: " + e.getMessage());
            return null;
        }
        return !questions.isEmpty() ? questions : null;
    }

    private static void loadStatistics(List<Question> questions) {
        Map<String, Question> questionMap = new HashMap<>();
        questions.forEach(q -> questionMap.put(q.rawText, q));

        try (BufferedReader br = new BufferedReader(new FileReader(STATS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(CSV_SPLIT);
                if (parts.length != 3) continue;

                Question q = questionMap.get(parts[0]);
                if (q != null) {
                    q.totalAttempts = Integer.parseInt(parts[1]);
                    q.correctCount = Integer.parseInt(parts[2]);
                }
            }
        } catch (Exception e) {
            System.err.println("统计加载失败: " + e.getMessage());
        }
    }

    private static void saveStatistics(List<Question> questions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STATS_FILE))) {
            for (Question q : questions) {
                String line = String.join(CSV_SPLIT,
                        q.rawText,
                        String.valueOf(q.totalAttempts),
                        String.valueOf(q.correctCount));
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("统计保存失败: " + e.getMessage());
        }
    }

    private static Question selectQuestion(List<Question> questions, Random random) {
        return questions.get(random.nextInt(questions.size()));
    }

    private static void runQuizSession(Question q, Scanner scanner) {
        System.out.println("\n题目：" + q.displayText);
        System.out.print("按回车查看答案...");
        scanner.nextLine();

        System.out.println("正确答案：" + String.join(" | ", q.answers));

        boolean isCorrect = getFeedback(scanner);
        updateStats(q, isCorrect);
        showQuestionStats(q);
    }

    private static boolean getFeedback(Scanner scanner) {
        while (true) {
            System.out.print("是否全部正确？(y/n) ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("无效输入，请重新输入！");
        }
    }

    private static void updateStats(Question q, boolean isCorrect) {
        q.totalAttempts++;
        if (isCorrect) q.correctCount++;
    }

    private static void showQuestionStats(Question q) {
        System.out.printf("本题统计：作答%d次，正确率%.1f%%\n\n",
                q.totalAttempts, q.getAccuracy());
    }
}