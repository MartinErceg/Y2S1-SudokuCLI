/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 *
 * @author marti
 */
public class FileIO
{
    private int scoreNum;
    private int sizeOfSudoku;
    private int[][] playerSudoku;
    private int[][] answerSudoku;
    private boolean[][] lockedNumbers;

    private FileReader fr;
    private BufferedReader inStream;

    private PrintWriter writer;
    private FileOutputStream fOut;
    private PrintWriter outputStream;

    public FileIO()
    {
        scoreNum = 0;
        sizeOfSudoku = 0;
    }

    public void readFile()
    {
        try
        {
            fr = new FileReader("score.txt");
        } catch (FileNotFoundException e)
        {
            try
            {
                outputStream = new PrintWriter(new FileOutputStream("score.txt"));
            } catch (FileNotFoundException ex)
            {
                System.out.println("File error");
            }
            outputStream.close();
        } finally
        {
            try
            {
                fr = new FileReader("score.txt");
            } catch (FileNotFoundException ex)
            {
                System.out.println("File error");
            }
            inStream = new BufferedReader(fr);
        }

        String findTag = null;

        do
        {

            try
            {
                findTag = inStream.readLine();
            } catch (IOException ex)
            {
                System.out.println("File error");
            }

            if (findTag == null)
            {
                System.out.println("-------------------");
                System.out.println("Welcome to the Sudoku game.");
                writeTagAndNumber("@score", "0");
                writeTagAndNumber("@sizeOfSudoku", "0");
                writeTag("@answer");
                writeTag("@player");
                writeTag("@lockedNumbers");
                System.out.println("-------------------");
                break;
            } else if (findTag.contains("@score"))
            {
                try
                {
                    scoreNum = Integer.valueOf(inStream.readLine());
                } catch (IOException ex)
                {
                    System.out.println("File error");
                }
                System.out.println("-------------------");
                System.out.println("Welcome back to Sudoku.");
                System.out.println("Score: " + scoreNum);
                System.out.println("-------------------");
            } else if (findTag.contains("@sizeOfSudoku"))
            {
                try
                {
                    // Scans in size of sudoku number
                    sizeOfSudoku = Integer.valueOf(inStream.readLine());
                } catch (IOException ex)
                {
                    System.out.println("File error");
                }
            } else if (findTag.contains("@answer"))
            {
                // has to have answer to get players sudoku
                if (sizeOfSudoku != 0)
                {
                    String playerTag = null;
                    String lockedTag = null;
                    answerSudoku = readSudoku(answerSudoku);
                    try
                    {
                        playerTag = inStream.readLine();
                    } catch (IOException ex)
                    {
                        System.out.println("File error");
                    }

                    if (playerTag.equals("@player"))
                    {
                        playerSudoku = readSudoku(playerSudoku);
                    } else
                    {
                        break;
                    }

                    try
                    {
                        lockedTag = inStream.readLine();
                    } catch (IOException ex)
                    {
                        System.out.println("File error");
                    }

                    if (lockedTag.equals("@lockedNumbers"))
                    {
                        lockedNumbers = lockSudokuNumbers();
                    } else
                    {
                        break;
                    }

                    break;
                }
                break;
            }
        } while (true);

        try
        {
            //        fr.close();
            inStream.close();
        } catch (IOException ex)
        {
            System.out.println("File error");
        }
    }

    public int getSizeOfSudoku()
    {
        return sizeOfSudoku;
    }

    public int[][] getPlayerSudoku()
    {
        return playerSudoku;
    }

    public int[][] getAnswerSudoku()
    {
        return answerSudoku;
    }
    
    public boolean[][] getLockedNumbers()
    {
       return lockedNumbers; 
    }

    private boolean[][] lockSudokuNumbers()
    {
        lockedNumbers = new boolean[sizeOfSudoku][sizeOfSudoku];
        if (sizeOfSudoku != 0)
        {
            for (int r = 0; r < sizeOfSudoku; r++)
            {
                // read a single line of number's
                String readLineOfBooleans = "";
                try
                {
                    readLineOfBooleans = inStream.readLine();
                } catch (IOException ex)
                {
                    System.out.println("File error");
                }
                // get every number from line
                StringTokenizer st = new StringTokenizer(readLineOfBooleans, " \n", false);

                int c = 0;
                // iterate the line until there are no longer any numbers in the row
                while (st.hasMoreTokens())
                {
                    lockedNumbers[r][c] = Boolean.valueOf(st.nextToken());
                    c++;
                }
            }
        }
        return lockedNumbers;
    }

    private int[][] readSudoku(int[][] typeOfSudoku)
    {
        typeOfSudoku = new int[sizeOfSudoku][sizeOfSudoku];
        if (sizeOfSudoku != 0)
        {
            for (int r = 0; r < sizeOfSudoku; r++)
            {
                // read a single line of number's
                String readLineOfNumbers = "";
                try
                {
                    readLineOfNumbers = inStream.readLine();
                } catch (IOException ex)
                {
                    System.out.println("File error");
                }
                // get every number from line
                StringTokenizer st = new StringTokenizer(readLineOfNumbers, " \n", false);

                int c = 0;
                // iterate the line until there are no longer any numbers in the row
                while (st.hasMoreTokens())
                {
                    typeOfSudoku[r][c] = Integer.parseInt(st.nextToken());
                    c++;
                }

            }
        }

        return typeOfSudoku;
    }

    public void refreshFile()
    {
        try
        {
            outputStream = new PrintWriter(new FileOutputStream("score.txt"));
        } catch (FileNotFoundException ex)
        {
            System.out.println("File error");
        }
        outputStream.close();

        writeTagAndNumber("@score", String.valueOf(scoreNum));
        writeTagAndNumber("@sizeOfSudoku", "0");
        writeTag("@answer");
        writeTag("@player");
        writeTag("@lockedNumbers");

        playerSudoku = null;
        answerSudoku = null;
        lockedNumbers = null;
        sizeOfSudoku = 0;
    }

    public int addScore()
    {
        return scoreNum++;
    }

    public int getScore()
    {
        return scoreNum;
    }

    public void savePlayerProgress(int[][] answerSudoku, int[][] playerSudoku, boolean[][] lockedNumbers)
    {
        this.playerSudoku = playerSudoku;
        this.answerSudoku = answerSudoku;
        this.sizeOfSudoku = playerSudoku.length;
        this.lockedNumbers = lockedNumbers;
        overWritePlayerSudoku();
    }

    private void overWritePlayerSudoku()
    {
        try
        {
            outputStream = new PrintWriter(new FileOutputStream("score.txt"));
        } catch (FileNotFoundException ex)
        {
            System.out.println("File error");
        }
        outputStream.close();

        writeTagAndNumber("@score", String.valueOf(scoreNum));
        writeTagAndNumber("@sizeOfSudoku", String.valueOf(sizeOfSudoku));

        writeTag("@answer");
        writeSudoku(answerSudoku);

        writeTag("@player");
        writeSudoku(playerSudoku);
        
        writeTag("@lockedNumbers");
        writeLockedNumbers();
    }
    
    private void writeLockedNumbers()
    {
       try
        {
            fOut = new FileOutputStream("score.txt", true);
        } catch (FileNotFoundException ex)
        {
            System.out.println("File error");
        }
        writer = new PrintWriter(fOut);
        
        String lineContainingLockedNumbers;
        if (sizeOfSudoku != 0)
        {
            lineContainingLockedNumbers = "";

            for (int r = 0; r < sizeOfSudoku; r++)
            {
                lineContainingLockedNumbers = "";
                for (int c = 0; c < sizeOfSudoku; c++)
                {
                    if (c < sizeOfSudoku - 1)
                    {
                        lineContainingLockedNumbers += (lockedNumbers[r][c] + " ");
                    } else
                    {
                        lineContainingLockedNumbers += lockedNumbers[r][c];
                    }
                }
                writer.println(lineContainingLockedNumbers);
            }
        }

        writer.close();
    }
    
    private void writeSudoku(int[][] typeOfSudoku)
    {
        try
        {
            fOut = new FileOutputStream("score.txt", true);
        } catch (FileNotFoundException ex)
        {
            System.out.println("File error");
        }
        writer = new PrintWriter(fOut);

        String lineContainingNumbers;
        if (sizeOfSudoku != 0)
        {
            lineContainingNumbers = "";

            for (int r = 0; r < sizeOfSudoku; r++)
            {
                lineContainingNumbers = "";
                for (int c = 0; c < sizeOfSudoku; c++)
                {
                    if (c < sizeOfSudoku - 1)
                    {
                        lineContainingNumbers += (typeOfSudoku[r][c] + " ");
                    } else
                    {
                        lineContainingNumbers += typeOfSudoku[r][c];
                    }
                }
                writer.println(lineContainingNumbers);
            }
        }

        writer.close();
    }

    private void writeTag(String tag)
    {
        fOut = null;

        try
        {
            fOut = new FileOutputStream("score.txt", true);
        } catch (FileNotFoundException ex)
        {
            System.out.println("Error with file");
        }

        writer = new PrintWriter(fOut);

        writer.println(tag);

        writer.close();
    }

    private void writeTagAndNumber(String tag, String numbers)
    {
        try
        {
            fOut = new FileOutputStream("score.txt", true);
        } catch (FileNotFoundException ex)
        {
            System.out.println("File error");
        }

        writer = new PrintWriter(fOut);

        writer.println(tag);
        writer.println(numbers);

        writer.close();
    }
}
