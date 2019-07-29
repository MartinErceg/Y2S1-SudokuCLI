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
public class SudokuChecker
{
    public boolean checkForGameFinish(int playerSudoku[][])
    {   
        for (int r = 0; r < playerSudoku.length; r++)
        {
            for (int c = 0; c < playerSudoku[r].length; c++)
            {
                if (playerSudoku[r][c] == 0)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public boolean playerToCheckAnswers(int playerSudoku[][], int answerSudoku[][])
    {
        for (int r = 0; r < playerSudoku.length; r++)
        {
            for (int c = 0; c < playerSudoku[r].length; c++)
            {
                if (playerSudoku[r][c] != answerSudoku[r][c])
                {
                    return false;
                }
            }
        }
        
        return true;
    }
}
