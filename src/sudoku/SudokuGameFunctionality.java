/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.Scanner;

/**
 *
 * @author marti
 */
public class SudokuGameFunctionality
{

    private static Scanner scan;

    public void startGame()
    {
        scan = new Scanner(System.in);
        int userRow = 0;
        int userColumn = 0;
        int userNumber = 0;
        String userString;

        FileIO fileInOut = new FileIO();

        fileInOut.readFile();

        // Make error handling class
        ErrorHandling errorHandle = new ErrorHandling();

        // initialise grid size and use it for new sudoku
        int gridSize = 0;
        GenerateSudoku newSudoku;

        // new two classes, store answer and players sudoku
        // new SudokuApplication can be changed to change to a new sudoku filler
        TypeOfSudoku answer;
        PlayerSudoku player;

        // sudokuChecker just checks numbers inputed
        // doesnt need to create another instant of it
        SudokuChecker checker = new SudokuChecker();

        boolean endGame;
        boolean newGame = true;
        boolean matchingSudoku;

        do
        {
            if (fileInOut.getSizeOfSudoku() == 0)
            {
                System.out.print("Enter grid size, 3 - 9: ");
                // get users grid size and generate new sudoku
                gridSize = errorHandle.withInt(3, 9);
            } else
            {
                System.out.print("Would you like to continue with previous Sudoku game? ");
                userString = errorHandle.withYesOrNo();

                if (userString.equals("yes") || userString.charAt(0) == 'y')
                {
                    // application will continue
                    endGame = true;
                    newGame = true;
                    gridSize = fileInOut.getSizeOfSudoku();
                } else if (userString.equals("no") || userString.charAt(0) == 'n')
                {
                    // make new game
                    fileInOut.refreshFile();
                    System.out.print("Enter grid size, 3 - 9: ");
                    // get users grid size and generate new sudoku
                    gridSize = errorHandle.withInt(3, 9);
                }
            }

            if (fileInOut.getAnswerSudoku() == null)
            {
                newSudoku = new GenerateSudoku(gridSize);
                answer = new AnswerSudoku(newSudoku.getGeneratedAnswer(), gridSize);
                player = new PlayerSudoku(newSudoku.getGeneratedPlayer(), newSudoku.getLockedNumbers(), gridSize);
            } else
            {
                answer = new AnswerSudoku(fileInOut.getAnswerSudoku(), gridSize);
                player = new PlayerSudoku(fileInOut.getPlayerSudoku(), fileInOut.getLockedNumbers(), gridSize);
            }
            fileInOut.savePlayerProgress(answer.getSudoku(), player.getSudoku(), player.getLockedNumbers());

            do
            {
                player.printSudoku();

                boolean checkIfBoxHasNum;
                boolean checkLockedNumber;
                do
                {
                    String rowAndColumnChoice = "";
//                    System.out.print("Enter row and column: ");

                    rowAndColumnChoice = errorHandle.withRowColumn(gridSize);

                    if (rowAndColumnChoice.charAt(0) >= 'a' && rowAndColumnChoice.charAt(0) <= gridSize + 'a')
                    {
                        userColumn = Character.getNumericValue(rowAndColumnChoice.charAt(0));
                        userRow = Character.getNumericValue(rowAndColumnChoice.charAt(1));
                    } else if (rowAndColumnChoice.charAt(0) >= '0' && rowAndColumnChoice.charAt(0) <= gridSize + '0')
                    {
                        userRow = Character.getNumericValue(rowAndColumnChoice.charAt(0));
                        userColumn = Character.getNumericValue(rowAndColumnChoice.charAt(1));
                    }
                    userRow--;
                    userColumn -= 10;

                    checkLockedNumber = player.isLocked(userRow, userColumn);
                    if (checkLockedNumber)
                    {
                        System.out.println("Number is part of sudoku, cannot touch locked number.");
                    }
                } while (checkLockedNumber);

                do
                {
                    System.out.print("Enter number, (1 to " + gridSize + "): ");
                    userNumber = errorHandle.withInt(1, gridSize);
                    System.out.println();
                } while (userNumber < 0 || userNumber > gridSize);

                player.enterNumber(userRow, userColumn, userNumber);
                fileInOut.savePlayerProgress(answer.getSudoku(), player.getSudoku(), player.getLockedNumbers());

                // Check game is finished
                endGame = checker.checkForGameFinish(player.getSudoku());

                // Check player has got it all right
                if (endGame)
                {
                    matchingSudoku = checker.playerToCheckAnswers(player.getSudoku(), answer.getSudoku());
                    System.out.println("-------------------");
                    if (matchingSudoku)
                    {
                        System.out.println("Well done, you've completed the sudoku!");
                        fileInOut.addScore();
                        System.out.println("Score: " + fileInOut.getScore());
                    } else
                    {
                        System.out.println("Sorry you didn't complete the sudoku.");
                        System.out.println("Score: " + fileInOut.getScore());
                    }
                    System.out.println("-------------------");

                    // because game has finished
                    fileInOut.refreshFile();
                    System.out.print("Would you like to try a new sudoku? ");

                    userString = errorHandle.withYesOrNo();
                    if (userString.equals("yes") || userString.charAt(0) == 'y')
                    {
                        // application will continue;
                        endGame = true;
                        newGame = true;
                    } else if (userString.equals("no") || userString.charAt(0) == 'n')
                    {
                        // application will finish;
                        endGame = true;
                        newGame = false;
                    }
                    fileInOut.refreshFile();
                }
            } while (!endGame);
        } while (newGame);
        // print out score
        System.out.println("Thank you for playing.");
    }
}
