package common;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static final Pattern ANSWER_PATTERN = Pattern.compile("<([^>]+)>");
    public static final String CSV_SPLIT = "‖";

    // 最大公约数
    public static int gcd(int a, int b) {
        int t=1;
        while (b!=0) {
            t = a%b;
            a=b;
            b=t;
        }
        return a;
    }

    // 找因数
    public static int[] findFactors(int a) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i < a; i++) {
            if (a%i==0) {
                factors.add(i);
            }
        }
        return factors.stream().mapToInt(i->i).toArray();
    }


    // 转为最接近自己的偶数，3-》2，1-》0,5-》4
    public static int toEven(int a) {
        return (a/2)*2;
    }

    public static void speak(String content) {
        // Windows: 使用PowerShell的语音合成功能
        String command = String.format(
                "Add-Type -AssemblyName System.Speech; " +
                        "$speech = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                        "$speech.Rate = %d; " +  // 添加语速控制
                        "$speech.Speak('%s');",
                -3, content
        );

        try {
            Process process = Runtime.getRuntime().exec(new String[]{"PowerShell", "-Command", command});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Question> loadQuestions(String path) {
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
            return new ArrayList<>();
        }
        return !questions.isEmpty() ? questions : new ArrayList<>();
    }

    public static void loadStatistics(List<Question> questions, String statsFile) {
        Map<String, Question> questionMap = new HashMap<>();
        questions.forEach(q -> questionMap.put(q.rawText, q));

        try (BufferedReader br = new BufferedReader(new FileReader(statsFile))) {
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

    public static void saveStatistics(List<Question> questions, String statsFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(statsFile))) {
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

    public static void updateStats(Question q, boolean isCorrect) {
        q.totalAttempts++;
        if (isCorrect) q.correctCount++;
    }

    public static void showQuestionStats(Question q) {
        System.out.printf("本题统计：作答%d次，正确率%.1f%%\n\n",
                q.totalAttempts, q.getAccuracy());
    }

    public static void runQuizSession(Question q, Scanner scanner) {
        System.out.println("\n题目：" + q.getDisplayText());
        System.out.print("按回车查看答案...");
        scanner.nextLine();

        System.out.println("正确答案：" + String.join(" | ", q.getAnswers()));

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

    public static List<Question> getQuestions(String workDir, List<String> keyWords, String statsFile) throws IOException {
        List<String> pathList = findFilesByKeywords(workDir, keyWords);

        List<Question> questions = new ArrayList<>();
        for (String v : pathList) {
            questions.addAll(loadQuestions(v));
        }

        if (questions.isEmpty()) return null;

        loadStatistics(questions, statsFile);
        return questions;
    }

    public static class Question {
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

        public String getRawText() {
            return rawText;
        }

        public String getDisplayText() {
            return displayText;
        }

        public List<String> getAnswers() {
            return answers;
        }

        public int getTotalAttempts() {
            return totalAttempts;
        }

        public void setTotalAttempts(int totalAttempts) {
            this.totalAttempts = totalAttempts;
        }

        public int getCorrectCount() {
            return correctCount;
        }

        public void setCorrectCount(int correctCount) {
            this.correctCount = correctCount;
        }
    }



    /**
            * 查找包含任一关键字的文件
     * @param dirPath 搜索目录路径
     * @param keywords 关键字列表（OR逻辑）
            * @return 匹配文件的绝对路径列表
     * @throws IOException 当目录访问失败时抛出
     */
    public static List<String> findFilesByKeywords(String dirPath, List<String> keywords) throws IOException {
        // 参数校验
        Objects.requireNonNull(keywords, "关键字列表不能为null");
        if (keywords.isEmpty()) {
            throw new IllegalArgumentException("关键字列表不能为空");
        }
        if (keywords.stream().anyMatch(String::isEmpty)) {
            throw new IllegalArgumentException("关键字列表包含空字符串");
        }

        List<String> result = new ArrayList<>();
        Path startDir = Paths.get(dirPath);

        if (!Files.isDirectory(startDir)) {
            throw new IllegalArgumentException("无效的搜索目录: " + dirPath);
        }

        Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                String fileName = file.getFileName().toString();

                // 多关键字OR匹配
                if (keywords.stream().anyMatch(fileName::contains)) {
                    result.add(file.toAbsolutePath().toString());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        return result;
    }

}

