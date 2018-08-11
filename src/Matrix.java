import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private int dimension;
    private double[][] array;


    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double[][] getArray() {
        return array;
    }

    public void setArray(double[][] array) {
        this.array = array;
    }

    public Matrix(int dimension) {

        this.dimension = dimension;
        this.array = new double[dimension][dimension];

        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                if(i == j) {
                    array[i][j] = 1;
                } else
                    array[i][j] = 0;
            }
        }

    }

    public Matrix(List<Vector> list, int dimension) {

    }


    public static void main(String[] args) {

        Matrix m = new Matrix(5);


        double[][] x = m.getArray();
        for(int i = 0; i < m.getDimension(); i++) {
            for(int j = 0; j < m.getDimension(); j++) {
                System.out.print(x[i][j] + " ");
            }

            System.out.println();
        }


    }
}
