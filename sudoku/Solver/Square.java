package sudoku.Solver;

import sudoku.model.Board;

import java.util.LinkedList;

public class Square {
    int val;
    LinkedList<Integer> moves = new LinkedList<>();
    int movesLeft;
    int index;

    public Square(int val, int size){
        int movesLeft = size;
        for(int i =0; i<size; i++){
            moves.add(i+1);
        }
        val = 0;
        index = 0;
    }

    public void insert(Integer v){
        if(val == 0){
            val = v;
            movesLeft--;
            if (v!=0){moves.remove(v);}
        }else{
            moves.add(val);
            val = v;
            if (v!=0){moves.remove(v);}
        }
    }
    public int getVal(){return val; }

    public int movePtr(){
        if(moves.size()> index+1){
           return index ++;
        }
        return index =0;
    }









}
