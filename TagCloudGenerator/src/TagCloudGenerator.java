import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;

/**
 * @author Charan Nanduri and Nathan Damian
 */

public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    // Constant separators to find when a tag has ended
    private static final String SEPARATORS = " .,:;'{][}|><?!~1234567890"
            + "@#$%^&*()-_=+";

    // Sorts the value of integers in certain map Pairs in decreasing order
    private static class CompareInteger implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> int1,
                Map.Pair<String, Integer> int2) {
            return int2.value().compareTo(int1.value());
        }
    }

    // Sorts string keys in alphabetical order
    private static class CompareString implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> s1, Map.Pair<String, Integer> s2) {
            return s1.key().compareTo(s2.key());
        }
    }

    /**
     * Given a set of common separators for text files, this will generate a set
     * of breaks for when the program should find the end of a word.
     *
     * @param str
     *            the string passed to the function
     * @return set of breaks
     * @ensures breaks = " + / + ` + characters in the str
     *
     **/
    private static Set<Character> generateBreaks(String str) {

        // Create a new instance of a Set
        Set<Character> breaks = new Set1L<Character>();

        // Add other breaks
        breaks.add('"');
        breaks.add('`');
        breaks.add('/');

        // Loop through the separators constant and create a set of breaks from it
        for (int i = 0; i < str.length(); i++) {
            if (!breaks.contains(str.charAt(i))) {
                breaks.add(str.charAt(i));
            }
        }

        // Finally return the set
        return breaks;
    }

    /**
     * Returns the first string of Characters that does not contain a break
     *
     * @param text
     *            the String passed into the function containing either a break
     *            or character collection
     * @param pos
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires <pre>
     *          {@code 0 <= position < |text|}
     * </pre>
     */
    private static String nextCollection(String text, int pos,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null.";
        assert separators != null : "Violation of: separators is not null.";
        assert pos < text.length() : "Violation of: position < |text|";

        // Check if we have a breaking character
        boolean breakChar = separators.contains(text.charAt(pos));

        // increment the line position
        int finalPos = pos + 1;

        while (finalPos < text.length()
                && (separators.contains(text.charAt(finalPos)) == breakChar)) {
            finalPos++;
        }

        //Return the word or separator.

        return text.substring(pos, finalPos);
    }

    /**
     * Reads through the file and finds all collections until it hits a break.
     * Then, it adds the collection to the Map if it doesn't exist and
     * increments its value if it does
     *
     * @param input
     *            input file to be counted
     * @param breaks
     *            set of characters that signify breaks in text
     * @replaces countMap
     * @requires input is open
     *
     */
    private static Map<String, Integer> readInput(Set<Character> breaks,
            SimpleReader input) {

        // Create a new map to hold the words and counts
        Map<String, Integer> countMap = new Map1L<String, Integer>();

        // While we have not reached the end of the files lines, go through
        // the file
        while (!input.atEOS()) {

            // Reset the position and get the next line
            int pos = 0;
            String line = input.nextLine().toLowerCase();

            while (pos < line.length()) {

                // Get the next collection of letters before a break or a break
                // char
                String col = nextCollection(line, pos, breaks);

                // Check what came back
                if (!breaks.contains(col.charAt(0))) {
                    boolean exists = countMap.hasKey(col);

                    if (exists) {
                        int newWordCount = countMap.value(col) + 1;
                        countMap.remove(col);
                        countMap.add(col, newWordCount);
                    } else {
                        countMap.add(col, 1);
                    }
                }

                // Update our position
                pos = pos + col.length();
            }

        }

        return countMap;
    }

    /**
     * This takes in the list of words and associated counts and first sorts the
     * words by their counts, finds the max and min, sorts the words
     * alphabetically, and creates a tag for the words that it written to the
     * output HTML file
     *
     * @param output
     *            HTML file to print the tags to
     * @param n
     *            number of high count words
     * @param countMap
     *            map of words and counts
     * @clears countMap
     * @ensures output content = output content * n words from countMap
     */
    private static void sortByCountAndLetter(SimpleWriter output, int n,
            Map<String, Integer> countMap) {

        // Create an integer comparator and sorting machine
        Comparator<Pair<String, Integer>> countOrder = new CompareInteger();
        SortingMachine<Map.Pair<String, Integer>> sortByCount = new SortingMachine2<Map.Pair<String, Integer>>(
                countOrder);

        // Add all map values to the sorting machine
        while (countMap.size() > 0) {
            sortByCount.add(countMap.removeAny());
        }

        // Now sort it by changing to extraction mode
        sortByCount.changeToExtractionMode();

        // Create a String comparator and sorting machine
        Comparator<Pair<String, Integer>> alphaOrder = new CompareString();
        SortingMachine<Map.Pair<String, Integer>> sortByLetter = new SortingMachine2<Map.Pair<String, Integer>>(
                alphaOrder);

        // Create a max value and minimum value
        int countMax = 0;
        int countMin = Integer.MAX_VALUE;

        //sortByCount.changeToInsertionMode();

        if (sortByCount.size() > 0) {
            Map.Pair<String, Integer> countPair = sortByCount.removeFirst();
            countMax = countPair.value();
            sortByCount.add(countPair);
        }

        for (int topCount = 0; topCount < n && sortByCount.size() > 0; topCount++) {
            Map.Pair<String, Integer> countPair = sortByCount.removeFirst();

            // Find the high and low values
            if (sortByCount.size() == 1 || topCount + 1 == n) {
                countMin = countPair.value();
            }

            // Add to the alphabetical sorting machine
            sortByLetter.add(countPair);
        }

        // Sort alphabetically
        sortByLetter.changeToExtractionMode();

        // Loop through the sorted list and find the font size for each of the words
        while (sortByLetter.size() > 0) {
            Map.Pair<String, Integer> countPair = sortByLetter.removeFirst();

            // Get the count
            int count = countPair.value();
            final int maxFontSize = 48;
            final int minFontSize = 11;
            int difference = maxFontSize - minFontSize;
            int countDiff = countMax - countMin;

            // Set a min font size in case when the variable is initialized
            // Make sure that there won't be a divide by zero error.
            int fontSize = 11;
            if (countDiff != 0) {
                fontSize = (((difference * (count - countMin)) / countDiff)
                        + minFontSize);
            }

            // Convert to an integer so we can access the CSS class provided in the
            // project assignment page
            String size = Integer.toString(fontSize);

            // Create the tag
            String tag = "<span style=\"cursor:default\" class=\"f" + size
                    + "\" title=\"count: " + countPair.value() + "\">" + countPair.key()
                    + "</span>";
            output.println(tag);
        }
    }

    /**
     * Outputs opening for the HTML file and create the header for the HTML
     * file.
     *
     * @param output
     *            output stream
     * @param inputFile
     *            name of the input file to read from
     * @param n
     *            number of words with highest counts
     * @updates output
     * @requires <pre>
     *            output stream is open and inputFile is not null and n >= 0
     * </pre>
     *
     */
    private static void createOpeningHTML(SimpleWriter output, String inputFile, int n) {

        // Output the name of the file and with n and the inputFile
        output.println(
                "<html><head><title>Top " + n + " words in " + inputFile + "</title>");
        output.println(
                "<link href=\"doc/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("</head><body><h3>Top " + n + " words in " + inputFile + "</h3>");
        output.println("<hr><div class = \"cdiv\"><p class = \"cbox\">");
    }

    /**
     * Method for a number input to print the amount of numbers requested.
     *
     * @param out
     * @param in
     * @param max
     *            max number allowed
     * @requires out.is_open and in.is_open
     *
     *
     * @return a positive number
     */
    private static int getNum(SimpleReader in, SimpleWriter out, int max) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";

        int num = -1;
        out.print("How many words should be included in the generated tag cloud?: ");
        String inputNum = in.nextLine();
        boolean check = true;
        /*
         * Checks to make sure the number is less than the max length of file,
         * and that it is positive
         */
        while (check) {
            num = Integer.parseInt(inputNum);
            if (num < 0 || num > max) {
                out.print("Enter a positive number less than " + max + ": ");
                inputNum = in.nextLine();
            } else {
                check = false;
            }
        }
        return num;

    }

    /**
     * Closes out the tags created in the HTML file
     *
     * @param output
     *            the simple writer stream for the output html file
     */
    private static void createClosingHTML(SimpleWriter output) {

        // Outputs the closing tags for the HTML file
        output.println("</p></div></body></html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {

        // Open the input stream and output stream
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Gets name of the input file
        out.print("Enter the name of the input file (.txt): ");
        String inputFile = in.nextLine();

        // Create the input stream from the inputFile name
        SimpleReader input = new SimpleReader1L(inputFile);

        // Get the breaks
        Set<Character> breaks = new Set1L<Character>();
        breaks = generateBreaks(SEPARATORS);

        // Call readInput to get the count of each word
        Map<String, Integer> wordsWithCounts = readInput(breaks, input);

        // get name of  output file and create SimpleWriter
        out.print("Enter the name of an output file (.html): ");
        String outputFile = in.nextLine();

        // Open up an output stream for the HTML file
        SimpleWriter writeHTML = new SimpleWriter1L(outputFile);

        // Asking for the number of words
        int numWords = getNum(in, out, wordsWithCounts.size());

        // makes header with number of words
        createOpeningHTML(writeHTML, inputFile, numWords);

        // Create the body of the HTML file
        sortByCountAndLetter(writeHTML, numWords, wordsWithCounts);

        // Creates the closing to finish out the HTML file
        createClosingHTML(writeHTML);

        //close all I/O streams
        in.close();
        out.close();
        input.close();
        writeHTML.close();
    }

}
