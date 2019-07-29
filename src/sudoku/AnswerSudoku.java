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
public class AnswerSudoku extends TypeOfSudoku
{
    
    public AnswerSudoku(int answerSudoku[][], int gridSize)
    {
        this.gridSize = gridSize;
        this.sudoku = new int[gridSize][gridSize];
        this.sudoku = answerSudoku;      
    }

    @Override
    public int[][] getSudoku()
    {
        return sudoku;
    }
}
