import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private int dimension;
    private double[][] array;
    private int row;

    public Matrix(){}

    public Matrix(int dimension) {

        this.dimension = dimension;
        this.row = dimension;
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
        this.array = transform(list, dimension);
        this.dimension = array[0].length;
        this.row = array.length;
    }


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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Matrix times(Matrix b) {

        Matrix result = new Matrix();

        if(this.getDimension() != b.getRow()){
            return null;
        } else {
            double[][] product = new double[this.getRow()][b.getDimension()];

            for(int i = 0; i < this.getRow(); i++)
                for(int j = 0; j < b.getDimension(); j++)
                    for(int k = 0; k < this.getDimension(); k++)
                        product[i][j] += this.getArray()[i][k] * b.getArray()[k][j];

            result.setArray(product);
            result.setDimension(b.getDimension());
            result.setRow(this.getRow());
        }

        return result;
    }

    public static double[][] transform(List<Vector> list, int dimension) {

        double[][] temp = new double[dimension][list.size()];
        for (int i = 0; i < list.size(); i++)
            for (int j = 0; j < list.get(i).getArray().length; j++)
                temp[j][i]= list.get(i).getArray()[j];

        return temp;
    }

    public static void main(String[] args) {

//        Matrix m = new Matrix(3);
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
        int dimension = 2;

        double[] arr1 = {8, 5};
        double[] arr2 = {9, -1};
        //double[] arr3 = {2, 2};
        //double[] arr4 = {7, 8, 9};

        Vector vector1 = new Vector(dimension, arr1);
        Vector vector2 = new Vector(dimension, arr2);
        //Vector vector3 = new Vector(dimension, arr3);
        //Vector vector4 = new Vector(dimension, arr4);

        vecList.add(vector1);
        vecList.add(vector2);
        //vecList.add(vector3);
        //vecList.add(vector4);

        Matrix n = new Matrix(vecList, dimension);
        double[][] y = n.getArray();

        for(int i = 0; i < n.getRow();i++) {
            for(int j = 0; j < n.getDimension(); j++) {
                System.out.print(y[i][j] + " ");
            }

            System.out.println();
        }

        List<Vector> list = new ArrayList<>();
        int dim = 2;

        double[] ar1 = {-2, 4};
        double[] ar2 = {3, 0};
       // double[] ar3 = {0, 2, 3};
        //double[] arr4 = {7, 8, 9};

        Vector vec1 = new Vector(dim, ar1);
        Vector vec2 = new Vector(dim, ar2);
        //Vector vector3 = new Vector(dimension, arr3);
        //Vector vector4 = new Vector(dimension, arr4);

        list.add(vec1);
        list.add(vec2);
        // vecList.add(vector3);
        //vecList.add(vector4);

        Matrix m = new Matrix(list, dim);
        double[][] z = m.getArray();

        for(int i = 0; i < m.getRow();i++) {
            for(int j = 0; j < m.getDimension(); j++) {
                System.out.print(z[i][j] + " ");
            }

            System.out.println();
        }

        Matrix x = n.times(m);

        for(int i = 0; i < x.getRow(); i++) {
            for(int j = 0; j < x.getDimension();j++) {
                System.out.print(x.getArray()[i][j] + " ");
            }

            System.out.println();
        }

    }
}
