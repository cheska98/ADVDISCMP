import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private int dimension;
    private List<Vector> vectors;
    private int row;

    public Matrix(){}

    public Matrix(int dimension) {

        this.dimension = dimension;
        this.row = dimension;
        this.vectors = new ArrayList<>();

        for(int i = 0; i < dimension; i++) {
            Vector v = new Vector(dimension);
            v.getArray()[i] = 1.0;
            vectors.add(v);
        }
    }

    public Matrix(List<Vector> list, int dimension) {
        this.vectors = transform(list, dimension);
        this.dimension = vectors.get(0).getArray().length;
        this.row = vectors.size();
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public void setVectors(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Matrix times(Matrix b) {

        Matrix result = new Matrix();
        List<Vector> n = new ArrayList<>();

        if(this.getDimension() != b.getRow()){
            return null;
        } else {
            double[][] product = new double[this.getRow()][b.getDimension()];

            for(int i = 0; i < this.getRow(); i++)
                for(int j = 0; j < b.getDimension(); j++)
                    for(int k = 0; k < this.getDimension(); k++)
                        product[i][j] += this.getVectors().get(i).getArray()[k] * b.getVectors().get(k).getArray()[j];

            for (int i = 0; i < product.length; i++) {
                n.add(new Vector(b.getDimension(), product[i]));
            }

            result.setVectors(n);
            result.setRow(this.getRow());
            result.setDimension(b.getDimension());
        }

        return result;
    }

    public static List<Vector> transform(List<Vector> vectors, int dimension) {

        double[][] temp = new double[dimension][vectors.size()];

        for (int i = 0; i < vectors.size(); i++)
            for (int j = 0; j < vectors.get(i).getArray().length; j++)
                temp[j][i] = vectors.get(i).getArray()[j];

        vectors.clear();
        //System.out.println("T; Temp size: " +temp.length);
        for (int i = 0; i < temp.length; i++) {
            vectors.add(new Vector(dimension, temp[i]));
        }

        return vectors;

    }
    
    public static void main(String[] args) {

        Matrix h = new Matrix(3);

        List<Vector> a = h.getVectors();
        for(int i = 0; i < h.getDimension(); i++) {
            for(int j = 0; j < h.getDimension(); j++) {
                System.out.print(a.get(i).getArray()[j] + " ");
            }

            System.out.println();
        }

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
        List<Vector> y = n.getVectors();

        for(int i = 0; i < n.getRow();i++) {
            for(int j = 0; j < n.getDimension(); j++) {
                System.out.print(y.get(i).getArray()[j] + " ");
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
        List<Vector> z = m.getVectors();

        for(int i = 0; i < m.getRow();i++) {
            for(int j = 0; j < m.getDimension(); j++) {
                System.out.print(z.get(i).getArray()[j] + " ");
            }

            System.out.println();
        }

        Matrix x = n.times(m);

        for(int i = 0; i < x.getRow(); i++) {
            for(int j = 0; j < x.getDimension();j++) {
                System.out.print(x.getVectors().get(i).getArray()[j] + " ");
            }

            System.out.println();
        }

    }
}
