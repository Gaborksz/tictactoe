import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {

        ticTacToe();

        // HUMAN-AI     AI-HUMAN    HUMAN-HUMAN
    }

    public static void ticTacToe() {

        String[][] board = initBoard();
        String playerX = "X",
                playerO = "O",
                currentPlayer,
                gameOutcome = "";


        currentPlayer = playerX;
        showWelcomeMessage();
        printBoard(board);

        do {
            int[] gridLocation = getHumanMove(currentPlayer, board);
            mark(gridLocation, board, currentPlayer);

            if ( userExit(gridLocation) ) {
                gameOutcome = "Player " + currentPlayer + " decided to quit, thanks for playing.";
                break;
            }

            if ( hasWon(board, currentPlayer) ) {
                gameOutcome = currentPlayer + "  has won!";
                break;
            }

            if ( isFull(board) ) {
                gameOutcome =  "It's a tie!";
                break;
            }

            printBoard(board);
            currentPlayer = (currentPlayer.equals(playerX)) ? playerO : playerX;

        } while (true);

        System.out.println("\n\n" + gameOutcome + "\nThanks for playing");
        printBoard(board);
    }

    public static boolean userExit(int[] gridLocation) {
        int exitValue = 9999,
            rowID = 0,
            columnId = 1;

        return gridLocation[rowID] == exitValue && gridLocation[columnId] == exitValue;
    }

    private static void showWelcomeMessage() {

        System.out.println("\nWelcome to" + "\n" + "" +
                "\t\t\tTic" + "\n" +
                "\t\t\t\tTac" + "\n" +
                "\t\t\t\t\tToe" + "\n\n");
    }

    public static boolean isFull(String[][] board) {

        for (String[] row : board) {
            for (String location : row) {
                if (location.equals(".")) return false;
            }
        }
        return true;
    }

    public static void printBoard(String[][] board) {

        String boardPrint = "";
        char rowId = 'A';

        boardPrint += getHeader(board) + "\n";

        for (int i = 0; i < board.length; i++) {
            boardPrint += rowId + getRowPrint(board, i) + "\n";
            boardPrint += getRowDivider(board, i) + "\n";
            rowId++;
        }
        System.out.println(boardPrint);
    }

    private static String getHeader(String[][] board) {

        String header = "  ";
        int columnID = 1;

        for (int i = 0; i < board.length; i++) {
            header += columnID + "   ";
            columnID++;
        }
        return header;
    }

    private static String getRowPrint(String[][] board, int i) {

        String rowPrint = "";

        for (int j = 0; j < board.length; j++) {
            rowPrint += " " + board[i][j] + " " + getColumnDivider(board, j);
        }
        return rowPrint;
    }

    private static String getRowDivider(String[][] board, int i) {

        String rowDivider = " ",
                columnWidth = "---",
                columnDivider;

        for (int j = 0; j < board.length; j++) {
            columnDivider = (j == board.length - 1) ? " " : "+";
            rowDivider += columnWidth + columnDivider;
        }
        return (i == board.length - 1) ? "" : rowDivider;
    }

    private static char getColumnDivider(String[][] board, int j) {

        return (j == board.length - 1) ? ' ' : '|';
    }

    public static boolean hasWon(String[][] board, String player) {

        if (hasFullRow(board, player)) return true;
        if (hasFullColumn(board, player)) return true;
        if (hasFullLeftToRight(board, player)) return true;
        if (hasFullRightToLeft(board, player)) return true;

        return false;
    }

    public static boolean hasFullRow(String[][] board, String player) {

        for (String[] row : board) {
            if (isFullRow(row, player)) return true;
        }
        return false;
    }

    public static boolean isFullRow(String[] row, String player) {

        for (String location : row) {
            if (!location.equals(player)) return false;
        }
        return true;
    }

    public static boolean hasFullLeftToRight(String[][] board, String player) {

        return board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player);
    }

    public static boolean hasFullRightToLeft(String[][] board, String player) {

        return board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player);
    }

    public static boolean hasFullColumn(String[][] board, String player) {

        int rows = board.length,
                maxRowIndex = rows - 1,
                columnIndex = 0;

        String[] columnContent;

        do {
            columnContent = getColumnContent(board, columnIndex);
            if (isFullColumn(columnContent, player)) return true;
            columnIndex++;
        } while (notEqual(maxRowIndex, columnIndex));
        return false;
    }

    private static boolean notEqual(int a, int b) {
        return a != b;
    }

    public static String[] getColumnContent(String[][] board, int columnIndex) {

        int rows = board.length;
        String[] columnContent = new String[rows];

        for (int i = 0; i < rows; i++) {
            columnContent[i] = board[i][columnIndex];
        }

        return columnContent;
    }

    public static boolean isFullColumn(String[] column, String player) {

        for (String location : column) {
            if (!location.equals(player)) return false;
        }
        return true;
    }

    public static String[][] initBoard() {

        String[][] board = new String[][]{
                new String[]{".", ".", "."},
                new String[]{".", ".", "."},
                new String[]{".", ".", "."},
        };
        return board;
    }

    public static int[] getHumanMove(String player, String[][] board) {

        int[] locationArray = new int[2];
        int row, col,
            exitValue = 9999;


        String userInput;
        char firstChar, secondChar;

        do {
            userInput = getUserInput(player);

            if (userInput.equalsIgnoreCase("quit")) {
                addToLocationArray(locationArray,exitValue,exitValue);
                break;
            }

            if (!isTwoCharLong(userInput)) continue;

            firstChar = userInput.toLowerCase().charAt(0);
            secondChar = userInput.charAt(1);

            if (isValidRowCharacter(firstChar)) {
                row = getRowNumberBasedOn(firstChar);
            } else continue;

            if (isValidColumnNumber(secondChar)) {
                col = getColNumberBasedOn(secondChar);
            } else continue;

            if (isLocationFree(row, col, board)) {
                addToLocationArray(locationArray, row, col);
                break;
            } else
                System.out.println("Location " + userInput.toUpperCase() + " is already taken");

        } while (true);

        return locationArray;
    }


    public static void mark(int[] location, String[][] board, String player) {
        int row, col;
        row = location[0];
        col = location[1];
        board[row][col] = player;
    }


    private static void addToLocationArray(int[] locationArray, int row, int col) {
        int rowId = 0, columnId = 1;
        locationArray[rowId] = row;
        locationArray[columnId] = col;
    }

    private static String getUserInput(String player) {

        String userInput;

        Scanner strScanner = new Scanner(System.in);

        System.out.print("Please choose a location on the grid, for example A3\n" +
                "Player " + player + " : ");
        userInput = strScanner.next();

        return userInput;
    }

    public static boolean isLocationFree(int row, int col, String[][] board) {

        return board[row][col].equals(".");
    }

    public static int getRowNumberBasedOn(char firstChar) {

        int rowNumber = 0;

        switch (firstChar) {
            case 'a': {
                rowNumber = 0;
            }
            break;

            case 'b': {
                rowNumber = 1;
            }
            break;

            case 'c': {
                rowNumber = 2;
            }
        }

        return rowNumber;
    }

    public static int getColNumberBasedOn(char secondChar) {

        int columnNumber = 0;

        switch (secondChar) {
            case '1': {
                columnNumber = 0;
            }
            break;

            case '2': {
                columnNumber = 1;
            }
            break;

            case '3': {
                columnNumber = 2;
            }
        }

        return columnNumber;
    }

    public static boolean isValidRowCharacter(char character) {

        char gridFirstRow = 'a',
                gridLastRow = 'c';

        return character >= gridFirstRow && character <= gridLastRow;
    }

    public static boolean isValidColumnNumber(char character) {

        int firstColumn = 1,
                lastColumn = 3,
                chosenColum = Character.getNumericValue(character);

        return chosenColum >= firstColumn && chosenColum <= lastColumn;
    }

    private static boolean isTwoCharLong(String userInput) {

        int requiredLength = 2;
        return userInput.length() == requiredLength;
    }
}