package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllureManager {

    private static final int MAX_ARCHIVED_RUNS = 10;
    private static final boolean ARCHIVE_FAILED_ONLY = false;

    /**
     * أرشفة النتائج السابقة قبل بدء Run جديد
     */
    public static void archivePreviousRun() {
        try {
            String projectDir = System.getProperty("user.dir");
            Path allureResultsPath = Paths.get(projectDir, "allure-results");
            Path archivePath = Paths.get(projectDir, "allure-results-archive");

            if (!Files.exists(archivePath)) {
                Files.createDirectories(archivePath);
            }

            if (Files.exists(allureResultsPath)) {
                try (Stream<Path> files = Files.list(allureResultsPath)) {
                    if (files.findAny().isPresent()) {
                        String oldRunFolder = "run_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                        Path oldRunPath = archivePath.resolve(oldRunFolder);
                        Files.createDirectories(oldRunPath);

                        moveFiles(allureResultsPath, oldRunPath);
                        System.out.println("📦 تم أرشفة النتائج السابقة في: " + oldRunFolder);
                    }
                }
            } else {
                Files.createDirectories(allureResultsPath);
            }
            cleanOldArchives(archivePath);
        } catch (IOException e) {
            System.err.println("❌ خطأ في أرشفة النتائج: " + e.getMessage());
        }
    }

    /**
     * أرشفة النتائج الحالية بعد انتهاء الـ Suite
     */
    public static void archiveCurrentRun(String timestamp) {
        try {
            String projectDir = System.getProperty("user.dir");
            Path allureResultsPath = Paths.get(projectDir, "allure-results");
            Path archivePath = Paths.get(projectDir, "allure-results-archive");

            if (Files.exists(allureResultsPath)) {
                try (Stream<Path> files = Files.list(allureResultsPath)) {
                    if (files.findAny().isPresent()) {
                        boolean hasFailedTests = checkForFailedTests();

                        if (ARCHIVE_FAILED_ONLY && !hasFailedTests) {
                            cleanDirectory(allureResultsPath);
                            return;
                        }

                        String runFolder = "run_" + timestamp + (hasFailedTests ? "_FAILED" : "_PASSED");
                        Path runPath = archivePath.resolve(runFolder);
                        Files.createDirectories(runPath);

                        moveFiles(allureResultsPath, runPath);
                        System.out.println("📦 تم حفظ Run الحالي في الأرشيف: " + runFolder);
                        cleanOldArchives(archivePath);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ خطأ في أرشفة Run الحالي: " + e.getMessage());
        }
    }

    private static boolean checkForFailedTests() {
        try {
            Path allureResultsPath = Paths.get(System.getProperty("user.dir"), "allure-results");
            if (!Files.exists(allureResultsPath)) return false;

            try (Stream<Path> files = Files.walk(allureResultsPath)) {
                return files.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".json"))
                        .anyMatch(path -> {
                            try {
                                String content = Files.readString(path, StandardCharsets.UTF_8).toLowerCase();
                                return content.contains("\"status\":\"failed\"") || content.contains("\"status\":\"broken\"");
                            } catch (IOException e) { return false; }
                        });
            }
        } catch (IOException e) { return false; }
    }

    private static void moveFiles(Path sourceDir, Path targetDir) throws IOException {
        try (Stream<Path> fileStream = Files.list(sourceDir)) {
            fileStream.forEach(source -> {
                try {
                    Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) { /* Ignore */ }
            });
        }
    }

    private static void cleanOldArchives(Path archivePath) throws IOException {
        try (Stream<Path> dirs = Files.list(archivePath)) {
            List<Path> sortedDirs = dirs.filter(Files::isDirectory)
                    .sorted(Comparator.comparing(p -> {
                        try { return Files.getLastModifiedTime(p); }
                        catch (IOException e) { return java.nio.file.attribute.FileTime.fromMillis(0); }
                    }))
                    .collect(Collectors.toList());

            if (sortedDirs.size() > MAX_ARCHIVED_RUNS) {
                for (int i = 0; i < sortedDirs.size() - MAX_ARCHIVED_RUNS; i++) {
                    deleteDirectory(sortedDirs.get(i));
                }
            }
        }
    }

    public static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            try (Stream<Path> paths = Files.walk(directory)) {
                paths.sorted(Comparator.reverseOrder()).forEach(path -> {
                    try { Files.delete(path); } catch (IOException e) { }
                });
            }
        }
    }

    public static void cleanDirectory(Path directory) throws IOException {
        try (Stream<Path> files = Files.list(directory)) {
            files.forEach(path -> {
                try {
                    if (Files.isDirectory(path)) deleteDirectory(path);
                    else Files.delete(path);
                } catch (IOException e) { }
            });
        }
    }
}