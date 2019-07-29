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
abstract public class TypeOfSudoku
{
    int sudoku[][];
    int gridSize;
    
    abstract public int[][] getSudoku();
}
