package matrixMult;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Matrix {

    private final int numOfRows;
    private final int numOfColumns;
    public int[][] elements;


    public Matrix(int rows, int columns) {
        this.numOfColumns = columns;
        this.numOfRows = rows;
        this.elements = new int[rows][columns];
    }

    //this constructor created to ease testing
    public Matrix(int[][] matrix) {
        this.numOfRows = matrix.length;
        this.numOfColumns = matrix[0].length;
        this.elements = new int[this.numOfRows][this.numOfColumns];
        for (int[] row : matrix) {
            elements = matrix;
        }
    }

    public int getNumOfRows() {
        return this.numOfRows;
    }

    int getNumOfColumns() {
        return this.numOfColumns;
    }

    public int getElementAtPosition(int row, int column) {
        return elements[row][column];
    }

    public void setElementAtPosition(int row, int column, int value) {
        this.elements[row][column] = value;
    }

    //to print the matrix on the user screen
    public String printMatrix() {
        String result = "";

        for (int i = 0; i < this.getNumOfRows(); i++) {
            for ( int j = 0; j < this.getNumOfColumns(); j++ ) {
                result +=  elements[i][j] + " " ;
            }
                result += "\n";
        }
//        for (int[] row : elements) {
//            result += "|" + Arrays.toString(row) + "|\n";
//        }
        return result;
    }

    // this method is used to populate the Matrix object with
    // random positive (int) values;
    public void populateAutomatically() {
        for (int i = 0; i < this.numOfRows; i++) {
            for (int j = 0; j < this.numOfColumns; j++) {
                int randomNum = new Random().nextInt(10);
                elements[i][j] = randomNum;
            }
        }
    }

    //this method is used to populate the Matrix with
    //elemements entered by the user
    public void populateManually() {
        Scanner scanner = new Scanner(System.in);
        boolean possible = false;
        for (int i = 0; i < this.getNumOfRows(); i++) {
            System.out.print("Row " + (i+1) + ": Enter " + this.getNumOfColumns() + " numbers separated by a comma : ");
            String[] rowElements = scanner.nextLine().split(",");
            for (int j = 0; j < this.getNumOfColumns(); j++) {
                this.elements[i][j] = Integer.parseInt(rowElements[j]);
            }
        }
    }

    //method is overriden for testing puproses
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Matrix matrix = (Matrix) obj;
        if (this.getNumOfRows() != matrix.getNumOfRows()) return false;
        if (this.getNumOfColumns() != matrix.getNumOfColumns()) return false;

        for (int i = 0; i < elements.length; i++) {
            if (!Arrays.equals(elements[i], matrix.elements[i])) return false;
        }
        return true;
    }

}
