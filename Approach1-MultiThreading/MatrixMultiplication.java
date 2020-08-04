package matrixMult;

import java.util.Scanner;
import java.util.concurrent.*;

public class MatrixMultiplication {
    public Matrix matrixOne;
    public Matrix matrixTwo;

    //for testing purposes ONLY:
    public int numOfThreadsCreated;

    /* To multiply an m×n matrix by an n×p matrix, the n's must be the same,
    and the result is an m×p matrix. 	*/
    public MatrixMultiplication(Matrix matrix1, Matrix matrix2) {
        this.matrixOne = matrix1;
        if (matrix1.getNumOfColumns() != matrix2.getNumOfRows())
            throw new IllegalArgumentException("Num of Columns in Matrix 1 must be equal to the num of Rows in Matrix 2");
        this.matrixTwo = matrix2;
    }

    public String dimensions() {
        return +this.matrixOne.getNumOfRows() + "x" + this.matrixTwo.getNumOfColumns();
    }

    public Matrix evaluate() throws ExecutionException, InterruptedException {

        Matrix matrixResult = new Matrix(matrixOne.getNumOfRows(), matrixTwo.getNumOfColumns());

        //According to requirements, we need to have a new thread for each element in a result Matrix
        //so here we are calculating the number of threads required
        //and creating a pool of threads:
        int numOfThreads = matrixResult.getNumOfColumns() * matrixResult.getNumOfRows();
        ExecutorService pool = Executors.newFixedThreadPool(numOfThreads);

        //create a single thread to calculate a single element, then
        //get the result from each thread and add it to the Matrix Result:
        for (int i = 0; i < matrixResult.getNumOfRows(); i++) {
            for (int j = 0; j < matrixResult.getNumOfColumns(); j++) {
                Future<Integer> futureValue = pool.submit(new SingleElementCalculator(this.matrixOne, this.matrixTwo, i, j));
                matrixResult.setElementAtPosition(i, j, futureValue.get());

                //for testing puproses only:
                this.numOfThreadsCreated++;
            }
        }
        //ensure all threads are prior returning the result
        pool.shutdown();
        while (!pool.isTerminated()) {
        }

        System.out.println(matrixResult.printMatrix());
        return matrixResult;
    }

    public static void main(String[] args) {
        boolean created = false;
        int matrixOneColumns, matrixOneRows;
        int matrixTwoColumns, matrixTwoRows;
        Scanner scanner = new Scanner(System.in);

        System.out.println("***** Hi There! This is a Matrix Multiplication Calculator *****");
        System.out.println("____________________________________________________________");
        System.out.println("Enter dimensions for Matrix 1");
        System.out.print("Number of Rows: ");
        matrixOneRows = scanner.nextInt();
        System.out.print("Number of Columns: ");
        matrixOneColumns = scanner.nextInt();

        do {
            System.out.println("Enter dimensions for Matrix 2:");
            System.out.print("Number of Rows: ");
            matrixTwoRows = scanner.nextInt();
            if (matrixOneColumns != matrixTwoRows)  {
                System.out.println("Number of Rows in Matrix 2 MUST be equal to Number of Columns in Matrix 1");
            }
        }
        while (matrixOneColumns != matrixTwoRows);
        System.out.print("Number of Columns: ");
        matrixTwoColumns = scanner.nextInt();

        Matrix matrix1 = new Matrix(matrixOneRows, matrixOneColumns);
        Matrix matrix2 = new Matrix(matrixTwoRows, matrixTwoColumns);
        MatrixMultiplication mult = new MatrixMultiplication(matrix1, matrix2);
        System.out.println("____________________________________________________________");
        System.out.print("Enter \"1\" to populate Matrices manually OR Enter \"2\" to populate Matrices automatically: ");
        int choice = scanner.nextInt();
        System.out.println("____________________________________________________________");
        if (choice == 1) {
            System.out.println("Matrix 1: ");
            matrix1.populateManually();
            System.out.println("Matrix 2: ");
            matrix2.populateManually();
        } else if (choice == 2) {
            matrix1.populateAutomatically();
            matrix2.populateAutomatically();
        }

        //print Matrixces
        System.out.println("Matrix 1: ");
        System.out.println(matrix1.printMatrix());
        System.out.println("Matrix 2: ");
        System.out.print(matrix2.printMatrix());
        System.out.println("____________________________________________________________");

        System.out.println("Matrix Result: ");

        try {
            mult.evaluate();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

