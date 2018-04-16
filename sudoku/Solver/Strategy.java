package sudoku.Solver;
import sudoku.model.Board;

import java.util.Random;

public class Strategy {
    private static final int UNASSIGNED = 0;

public Strategy(){}



/*    public Board solve(Board b){
        for(int i=0; i< b.size(); i++){
            for(int j=0; j<b.size(); j++){
                Square curr = b.getSquare(i,j);
                //int indx = curr.index;
                int val = b.getValueAtCoordinates(i,j);
                if(val == 0 && curr.moves.size()!= 0) {
                    b.insert(i,j,indx);
                    }else if(val == 0){
                        i--;
                        j--;
                        b.insert(i,j,indx);
                    }else
                        break;
                }
                }
        return b;
            }*/
    public boolean solveSudoku(Board b)
    {
        for(int row=0;row<b.size();row++)
        {
            for(int col=0;col<b.size();col++)
            {
                if(b.getValueAtCoordinates(row,col)==UNASSIGNED)
                {
                    for(int number=1;number<=b.size();number++)
                    {
                        if(b.insert(row, col, number))
                        {
                            if(solveSudoku(b))
                            {
                                return true;
                            }
                            else
                            {
                                b.insert(row, col, UNASSIGNED);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}
