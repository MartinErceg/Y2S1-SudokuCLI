/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author marti
 */
class ErrorHandling
{

    // Doesn't need static cause it's just 1 class.
    private static int stopTryingToBreakGame;
    private static int reported;
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static Scanner scan;

    public ErrorHandling()
    {
        stopTryingToBreakGame = 0;
        reported = 0;
    }

    public int withInt(int lowestNum, int highestNum)
    {
        int userPrompt;

        scan = new Scanner(System.in);

        do
        {
            try
            {
                userPrompt = scan.nextInt();
                if (userPrompt < lowestNum || userPrompt > highestNum)
                {
                    System.out.println("Please enter number within range");
                    System.out.print("Re-enter input: ");
                }
            } catch (InputMismatchException e)
            {
                tryingToBreakGame();
                System.out.println("Invalid input");
                System.out.print("Re-enter input: ");
                userPrompt = 0;
                scan.next();
            }
        } while (userPrompt < lowestNum || userPrompt > highestNum);

        return userPrompt;
    }

    public String withYesOrNo()
    {
        String userPrompt = "";
        boolean validation = true;
        scan = new Scanner(System.in);

        do
        {
            try
            {
                do
                {
                    userPrompt = scan.nextLine();
                    if (userPrompt.isEmpty())
                    {
                        tryingToBreakGame();
                        System.out.println("Nothing was entered");
                        System.out.print("Re-enter input: ");
                    }
                } while (userPrompt.isEmpty());

                validation = (!(userPrompt.equals("yes"))) && (!(userPrompt.equals("no")))
                        && (!(userPrompt.charAt(0) == 'y')) && (!(userPrompt.charAt(0) == 'n'));
                if (validation)
                {
                    tryingToBreakGame();
                    System.out.println("Try again");
                    System.out.print("Re-enter input: ");
                }
            } catch (InputMismatchException e)
            {
                tryingToBreakGame();
                System.out.println("Invalid input");
                System.out.print("Re-enter input: ");
                scan.next();
            }
        } while (validation);

        return userPrompt;
    }

    public String withRowColumn(int gridSize)
    {
        scan = new Scanner(System.in);
        String userInput;
        String rowAndColumn;
        String userHasPicked;
        boolean letter = false;
        boolean number = false;

        do
        {
            try
            {
                userHasPicked = "";
                System.out.print("Enter row and column: ");
                userInput = scan.nextLine();
                rowAndColumn = userInput.toLowerCase();

                if (rowAndColumn.length() == 2)
                {
                    for (int i = 0; i < 2; i++)
                    {
                        // letter or number at character 1 or 2
                        if (rowAndColumn.charAt(i) >= 'a' && rowAndColumn.charAt(i) < ('a' + gridSize))
                        {
                            letter = !letter;
                            if (!letter)
                            {
                                tryingToBreakGame();
                                System.out.println("Invalid input");
                            }
                            userHasPicked += rowAndColumn.charAt(i);
                        } else if (rowAndColumn.charAt(i) > '0' && rowAndColumn.charAt(i) <= gridSize + '0')
                        {
                            number = !number; // double up so resolve it
                            if (!number)
                            {
                                tryingToBreakGame();
                                System.out.println("Invalid input");
                            }
                            userHasPicked += rowAndColumn.charAt(i);
                        } else
                        {
                            tryingToBreakGame();
                            letter = false;
                            number = false;
                            System.out.println("Invalid input");
                            break;
                        }
                    }
                } else
                {
                    tryingToBreakGame();
                    if (userInput.isEmpty())
                    {
                        System.out.println("Nothing was entered");
                    } else
                    {
                        System.out.println("Invalid input");
                    }
                }
            } catch (Exception e)
            {
                tryingToBreakGame();
                System.out.println("Try again");
                userHasPicked = "";
            }
        } while (!(letter || number));

        return userHasPicked;
    }

    private void tryingToBreakGame()
    {
        stopTryingToBreakGame++;
        if (stopTryingToBreakGame % 5 == 0 && stopTryingToBreakGame < 31)
        {
            System.out.println("-------------------");
            switch (reported)
            {
                case 0:
                    System.out.println(ANSI_GREEN + "Stop trying to break my game please. Thanks" + ANSI_RESET);
                    reported++;
                    break;
                case 1:
                    System.out.println(ANSI_GREEN + "Stahp it, get some help" + ANSI_RESET);
                    reported++;
                    break;
                case 2:
                    System.out.println(ANSI_YELLOW + "You violiating the rules of Sudoku" + ANSI_RESET);
                    reported++;
                    break;
                case 3:
                    System.out.println(ANSI_RED + "Keep doing it I dare you to" + ANSI_RESET);
                    reported++;
                    break;
                case 4:
                    System.out.println(ANSI_RED + "1 more time come on" + ANSI_RESET);
                    reported++;
                    break;
                case 5:
                    System.out.println(ANSI_RED + "That's it you're getting reported to the Sudoku police station" + ANSI_RESET);
                    reported++;
                default:
                    break;
            }
            System.out.println("-------------------");
        }
    }
}
