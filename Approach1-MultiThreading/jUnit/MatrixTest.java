package matrixMult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void testGetNumsOfColumsAndRows() {
        Matrix mat1 = new Matrix(4, 5);
        assertEquals( 5, mat1.getNumOfColumns());
        assertEquals( 4, mat1.getNumOfRows());

        Matrix mat2 = new Matrix(510, 340);
        assertEquals( 340, mat2.getNumOfColumns());
        assertEquals( 510, mat2.getNumOfRows());

        Matrix mat = new Matrix(5, 25);
        MatrixMultiplication mult = new MatrixMultiplication(mat1, mat);
        assertEquals("4x25", mult.dimensions());


        int[][] arr1 = {{1,2,4},{4,5,6}};
        int[][] arr2 = {{7,8}, {9,10}, {11,12} };
        Matrix mat3 = new Matrix (arr1);
        Matrix mat4 = new Matrix (arr2);
        assertEquals(mat3.getNumOfRows(), 2);
        assertEquals(mat3.getNumOfColumns(),3);

        MatrixMultiplication m2 = new MatrixMultiplication(mat3, mat4);
    }


    @Test
    public void testEquals () {
        int[][] arr1 = {{1,2,4},{4,5,6}};
        int[][] arr11 = {{1,2,4},{4,5,6}};
        Matrix mat3 = new Matrix (arr1);
        Matrix mat4 = new Matrix (arr11);

        assertTrue(mat3.equals(mat4));
        assertEquals(mat3, mat4);

        //***********************************

        int[][] arr2 = {{1,2},{4,5,6}};
        int[][] arr22 = {{1,2,4},{4,5,6}};
        Matrix mat5 = new Matrix (arr2);
        Matrix mat6 = new Matrix (arr22);

        assertTrue(!mat5.equals(mat6));
        assertNotEquals(mat5, mat6);
    }

    @Test
    public void testMultiplicationConstructor() {
        int[][] arr1 = {{1,2,4},{4,5,6}};
        int[][] arr11 = {{1,2,4},{4,5,6}, {1,2,3}};

        Matrix mat3 = new Matrix (arr1);
        Matrix mat4 = new Matrix (arr11);

        //exception is NOT expected here as matrix1 Comums == matrix2 Rows
        try {
            MatrixMultiplication m = new MatrixMultiplication(mat3, mat4);
        }
        catch (IllegalArgumentException e) {
            fail("");
        }

        //exception is expected as matrix1 Comums != matrix2 Rows
        Matrix mat5 = new Matrix (4, 2);
        Matrix mat6 = new Matrix (5, 4);
        try {
            MatrixMultiplication m = new MatrixMultiplication(mat5, mat6);
            fail("");
        }
        catch (IllegalArgumentException e) {
        }


    }
    @Test
    public void testMultiply() {
        int[][] arr1 = {{1,2,3},{4,5,6}};
        int[][] arr2 = {{7,8}, {9,10}, {11,12} };
        Matrix mat1 = new Matrix (arr1);
        Matrix mat2 = new Matrix (arr2);
        MatrixMultiplication m1 = new MatrixMultiplication(mat1, mat2);

        //create expected result
        int [][] arr = {{58,64},{139, 154}} ;
        Matrix expected = new Matrix (arr);
        try {
            assertEquals(expected, m1.evaluate());
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }

        //*********************************************//

        int[][] arr3 = {{1,1,1},{2,2,2}, {3,3,3}};
        int[][] arr4 = {{4,4}, {5,5}, {6,6} };
        Matrix mat3 = new Matrix (arr3);
        Matrix mat4 = new Matrix (arr4);
        MatrixMultiplication m2 = new MatrixMultiplication(mat3, mat4);

        //create expected result
        int [][] arr5 = {{15,15},{30,30},{45,45}} ;
        Matrix expected1 = new Matrix (arr5);
        try {
            assertEquals(expected1, m2.evaluate());
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }

        //*********************************************//

        int[][] arr6 = {{2}};
        int[][] arr7 = {{2,4,6}};
        Matrix mat6 = new Matrix (arr6);
        Matrix mat7 = new Matrix (arr7);
        MatrixMultiplication m3 = new MatrixMultiplication(mat6, mat7);

        //create expected result
        int [][] arr8 = {{4,8,12}} ;
        Matrix expected2 = new Matrix (arr8);
        try {
            assertEquals(expected2, m3.evaluate());
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testNumberOfCreatedThreads () throws ExecutionException, InterruptedException {
        Matrix m1 = new Matrix(4,5);
        Matrix m2 = new Matrix(5,6);
        m1.populateAutomatically();
        m2.populateAutomatically();

        MatrixMultiplication mult = new MatrixMultiplication(m1, m2);
        assertEquals(0, mult.numOfThreadsCreated);
        mult.evaluate();
        assertEquals(mult.numOfThreadsCreated, 4 * 6);

        //**********************************************

        Matrix m3 = new Matrix(1,99);
        Matrix m4 = new Matrix(99,8);
        m1.populateAutomatically();
        m2.populateAutomatically();

        MatrixMultiplication mult1 = new MatrixMultiplication(m3, m4);
        assertEquals(0, mult1.numOfThreadsCreated);
        mult1.evaluate();
        assertEquals(mult1.numOfThreadsCreated, 1 * 8);

    }
}