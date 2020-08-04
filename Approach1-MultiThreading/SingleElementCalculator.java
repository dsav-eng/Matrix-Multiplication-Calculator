package matrixMult;

import java.util.concurrent.Callable;

public class SingleElementCalculator extends MatrixMultiplication implements Callable<Integer> {
    int row;
    int col;

    public SingleElementCalculator(Matrix matrix1, Matrix matrix2, int row, int col) {
        super (matrix1, matrix2);
        this.row = row;
        this.col = col;
    }


    //this method is called by an individual thread created from the parent class:
    @Override
    public Integer call() {

        Integer element = 0;
        for (int k = 0; k < matrixOne.getNumOfColumns(); k++) {
            element += matrixOne.getElementAtPosition(row, k) * matrixTwo.getElementAtPosition(k, col);
        }
        return element;
    }
}

