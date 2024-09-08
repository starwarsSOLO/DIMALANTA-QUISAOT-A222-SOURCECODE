public class Tokenizer {
    public static void main(String[] args) {

        String userInput = "I/am/Benedict/Solo/P./Dimalanta!/my/phone/number/is/0929-393-8508./MJ/is/the/best/charmer./He/can/charm/all/the/girls/with/no/help/whatsoever./pythoncharmers123";


        String[] tokens = splitInput(userInput);


        String[] parsedTokens = processTokens(tokens);


        System.out.println("-------------- Phase 1: ------------------");
        phase1(parsedTokens);
        System.out.println("-------------- Phase 2: ------------------");
        phase2(parsedTokens);
    }


    private static String[] splitInput(String input) {
        return input.split("/");  // break the string at each '/'
    }


    private static String[] processTokens(String[] tokens) {

        int totalElements = 0;
        for (String token : tokens) {
            totalElements += countElements(token);
        }


        String[] parsedArray = new String[totalElements];
        int index = 0;


        for (String token : tokens) {
            index = separateElements(token, parsedArray, index);
        }

        return parsedArray;
    }


    private static int countElements(String token) {
        int count = 0;
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);


            if (isPartOfNumber(token, i, c) || Character.isLetterOrDigit(c)) {
                buffer.append(c);
            } else {

                if (buffer.length() > 0) {
                    count++;
                    buffer.setLength(0);
                }
                count++;
            }
        }


        if (buffer.length() > 0) {
            count++;
        }

        return count;
    }


    private static int separateElements(String token, String[] parsedArray, int index) {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);


            if (isPartOfNumber(token, i, c) || Character.isLetterOrDigit(c)) {
                buffer.append(c);
            } else {

                if (buffer.length() > 0) {
                    parsedArray[index++] = buffer.toString();
                    buffer.setLength(0);
                }
                parsedArray[index++] = String.valueOf(c);
            }
        }


        if (buffer.length() > 0) {
            parsedArray[index++] = buffer.toString();
        }

        return index;
    }


    private static boolean isPartOfNumber(String token, int i, char c) {

        return Character.isDigit(c) || (c == '.' && i > 0 && Character.isDigit(token.charAt(i - 1)) && (i + 1 < token.length() && Character.isDigit(token.charAt(i + 1))));
    }

    // Phase 1
    private static void phase1(String[] parsedArray) {
        for (String s : parsedArray) {
            boolean hasDigits = false;
            boolean hasLetters = false;
            boolean hasSpecial = false;


            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    hasDigits = true;
                } else if (Character.isLetter(c)) {
                    hasLetters = true;
                } else {
                    hasSpecial = true;
                }
            }


            if (hasLetters && hasDigits && !hasSpecial) {
                System.out.println("Token\t: \"" + s + "\" - Type: AlphaNumeric");
            } else if (hasLetters && !hasSpecial) {
                System.out.println("Token\t: \"" + s + "\" - Type: Word");
            } else if (!hasLetters && hasDigits) {
                System.out.println("Token\t: \"" + s + "\" - Type: Number");
            } else {
                System.out.println("Token\t: \"" + s + "\" - Type: Punctuation");
            }
        }

        System.out.println("Token\t: \"\\n\" - Type: End of Line");
    }

    // Phase 2
    private static void phase2(String[] parsedArray) {
        for (String token : parsedArray) {

            if (!token.matches("[a-zA-Z0-9]+") && !token.matches("[0-9]+\\.[0-9]+")) {
                continue;
            }

            StringBuilder result = new StringBuilder("Token\t: \"" + token + "\"\t->\t");
            for (int i = 0; i < token.length(); i++) {
                result.append("'").append(token.charAt(i)).append("'");
                if (i < token.length() - 1) {
                    result.append(", ");
                }
            }
            System.out.println(result);
        }
    }
}
