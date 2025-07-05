package org.binhntt.exception;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipFile;

public class CatchingAndHandlingException {
    private List<Integer> list;
    private static final int SIZE = 10;

    public CatchingAndHandlingException() {
        list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    public void writeList() throws IOException {
        // The FileWrite constructor throws IOException, which must be caught.
        // -> Checked Exception
        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));

        for (int i = 0; i < SIZE; i++) {
            // The get(int) method throws IndexOutOfBoundsException, which must be caught.
            // -> Unchecked Exception
            out.println("Value at: " + i + " = " + list.get(i));
        }
        out.close();
    }

    public static void writeToFileZipFileContents(String zipFileName, String outputFileName) throws IOException {
        Charset charset = StandardCharsets.US_ASCII;
        Path outputFilePath = Paths.get(outputFileName);

        // Open zip file and create output file with try-with-resources statement
        try (ZipFile zf = new ZipFile(zipFileName);
             BufferedWriter writer = Files.newBufferedWriter(outputFilePath, charset)) {
            // Enumerate each entry
            for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                // Get the entry name and write it to the output file
                String newLine = System.getProperty("line.separator");
                String zipEntryName = ((java.util.zip.ZipEntry) entries.nextElement()).getName() +
                        newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        }
    }

    /**
     * // Chained Exceptions (throw ra lỗi chi tiết hơn)
     * try {
     *
     * } catch (IOException e) {
     *     throw new SampleException("Other IOException", e);
     * }
     *
     * // Accessing Stack Track Information
     * catch (Exception cause) {
     *     StackTraceElement elements[] = cause.getStackTrace();
     *     for (int i = 0, n = elements.length; i < n; i++) {
     *         System.err.println(elements[i].getFileName()
     *             + ":" + elements[i].getLineNumber()
     *             + ">> "
     *             + elements[i].getMethodName() + "()");
     *     }
     * }
     *
     * // Logging API
     *
     * try {
     *     Handler handler = new FileHandler("OutFile.log");
     *     Logger.getLogger("").addHandler(handler);
     *
     * } catch (IOException e) {
     *     Logger logger = Logger.getLogger("package.name");
     *     StackTraceElement elements[] = e.getStackTrace();
     *     for (int i = 0, n = elements.length; i < n; i++) {
     *         logger.log(Level.WARNING, elements[i].getMethodName());
     *     }
     * }
     *
     * **/
}
