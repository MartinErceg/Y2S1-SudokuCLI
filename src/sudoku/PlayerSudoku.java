/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author marti
 */
public class PlayerSudoku extends TypeOfSudoku
{

    private final boolean lockedNumbers[][];

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    public PlayerSudoku(int playerSudoku[][], boolean lockedNumbers[][], int gridSize)
    {
        this.gridSize = gridSize;
        this.lockedNumbers = lockedNumbers;
        this.sudoku = new int[gridSize][gridSize];
        this.sudoku = playerSudoku;
    }

    @Override
    public int[][] getSudoku()
    {
        return sudoku;
    }

    public void enterNumber(int row, int column, int playersNum)
    {
        sudoku[row][column] = playersNum;
    }

    public boolean isLocked(int row, int column)
    {
        return lockedNumbers[row][column];
    }

    public boolean[][] getLockedNumbers()
    {
        return lockedNumbers;
    }

    public void printSudoku()
    {
        // Print letter on top of sudoku
        System.out.print("\n     ");
        for (int i = 0; i < gridSize; i++)
        {
            System.out.print((char) ('A' + i) + "   ");
        }
        System.out.println("");

        // print out of numbers in sudoku
        for (int i = 0; i < gridSize; i++)
        {
            System.out.print("    ");
            for (int j = 0; j < gridSize; j++)
            {
                System.out.print("- - ");
            }
            System.out.print("\n ");

            System.out.print((i + 1) + " |");
            for (int j = 0; j < gridSize; j++)
            {
                if (sudoku[i][j] == 0)
                {
                    System.out.print("   |");
                } else
                {
                    if (isLocked(i, j))
                    {
                        System.out.print(" "  + ANSI_RED + sudoku[i][j] + ANSI_RESET + " |");
                    } else
                    {
                        System.out.print(" "  + ANSI_BLUE + sudoku[i][j] + ANSI_RESET + " |");
                    }
                }
            }
            System.out.println("");

            if (i == gridSize - 1)
            {
                System.out.print("    ");
                for (int j = 0; j < gridSize; j++)
                {
                    System.out.print("- - ");
                }
                System.out.println("");
            }
        }

        System.out.println(ANSI_RED + "Red"  + ANSI_RESET + " = Locked Number");
        System.out.println(ANSI_BLUE + "Blue" + ANSI_RESET + " = Player Number\n");
    }
}
