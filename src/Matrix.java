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

    public static double[][] transform(List<Vector> list, int dimension) {

        double[][] temp = new double[dimension][dimension];

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j <dimension; j++)
                temp[j][i]= list.get(i).getArray()[j];

        return temp;

    }

    public Matrix(List<Vector> list, int dimension) {
        this.dimension = dimension;
        this.array = transform(list, dimension);
    }


    public static void main(String[] args) {

//        Matrix m = new Matrix(5);
//
//
//        double[][] x = m.getArray();
//        for(int i = 0; i < m.getDimension(); i++) {
//            for(int j = 0; j < m.getDimension(); j++) {
//                System.out.print(x[i][j] + " ");
//            }
//
//            System.out.println();
//        }

        List<Vector> vecList = new ArrayList<>();
        int dimension = 3;

        double[] arr1 = {1, 2, 4};
        double[] arr2 = {1, 2, 6};
        double[] arr3 = {0, 2, 3};

        Vector vector1 = new Vector(dimension, arr1);
        Vector vector2 = new Vector(dimension, arr2);
        Vector vector3 = new Vector(dimension, arr3);

        vecList.add(vector1);
        vecList.add(vector2);
        vecList.add(vector3);

        Matrix n = new Matrix(vecList, dimension);
        double[][] y = n.getArray();
        
        for(int i = 0; i < n.getDimension(); i++) {
            for(int j = 0; j < n.getDimension(); j++) {
                System.out.print(y[i][j] + " ");
            }

            System.out.println();
        }
    }
}
