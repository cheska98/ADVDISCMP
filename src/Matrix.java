/*
    S17 ADVDISC
    ALEJANDRINO, CHESKA
    ORTIZ, PAMELA
    RESPICIO, MICHAEL
*/
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

    public Matrix(List<Vector> list, int dimension, int row) {
        this.vectors = list;
        this.dimension = list.get(0).getArray().length;
        this.row = list.size();
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
       for(int i = 0; i < vectors.size(); i++)
            for(int j = 0; j < vectors.get(i).getArray().length; j++)
                this.vectors.get(i).getArray()[j] = vectors.get(i).getArray()[j];
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

            result = new Matrix(n, this.getDimension(), this.row);
        }

        return result;
    }

    public static List<Vector> transform(List<Vector> vectors, int dimension) {

        double[][] temp = new double[vectors.get(vectors.size()-1).getArray().length][vectors.size()];
        for (int i = 0; i < vectors.size(); i++) {
            for (int j = 0; j < vectors.get(i).getArray().length; j++)
                temp[j][i] = vectors.get(i).getArray()[j];
        }

        vectors.clear();
        //System.out.println("T; Temp size: " +temp.length);
        for (int i = 0; i < temp.length; i++) {
            vectors.add(new Vector(dimension, temp[i]));
        }

        return vectors;
    }

    public static List<Vector> combine(Matrix matrix) {
        List<Vector> combined = new ArrayList<Vector>();
        Matrix identity = new Matrix(matrix.dimension);
        int nDim = matrix.dimension * 2;

        for (int i = 0; i < matrix.row; i++) {
            double[] array = new double[nDim];
            for (int j = 0; j < nDim; j++) {
                if (j >= matrix.dimension) {
                    array[j] = identity.getVectors().get(i).getArray()[j - matrix.dimension];
                }
                if(j < matrix.dimension) {
                    array[j] = matrix.getVectors().get(i).getArray()[j];
                }
            }
            combined.add(new Vector(nDim, array));
        }
        return combined;
    }

    public List<Vector> extract(List<Vector> vectors, int dimension) {
        List<Vector> list = new ArrayList<>();

        for(int i = 0; i < vectors.size(); i++) {
            double[] array = new double[dimension];
            for (int j = dimension; j < vectors.get(i).getArray().length; j++) {
                array[j-dimension] = vectors.get(i).getArray()[j];
            }
            list.add(new Vector(dimension, array));
        }
        return list;
    }
	
	public double det(){
		double z = 1;
        double scale = 1;
        Matrix matrix = new Matrix(this.getDimension());
        matrix.setVectors(this.getVectors());
		 if(this.getDimension() == this.getRow()) {
             List<Vector> vecList = matrix.getVectors();
            double scalar = 0;
			for(int i = 0; i < vecList.size(); i++){
                for (int k = i; k < vecList.size() - 1; k++) {
                   if( vecList.get(k).getArray()[i] < vecList.get(k+1).getArray()[i]  && vecList.get(k).getArray()[i] == 0) {
                        double[] temp = vecList.get(k+1).getArray();
                        vecList.get(k+1).setArray(vecList.get(k).getArray());
                        vecList.get(k).setArray(temp);
                        z = -1*z;
                    }
                }
				for(int j = i; j<vecList.size(); j++){
					if(i != j){
						double x = vecList.get(i).getArray()[i];
						double y = vecList.get(j).getArray()[i];
						
						if(x!=0  && y !=0){
							scalar = -1 * (y / x);
							vecList.get(j).add(vecList.get(i).scale(scalar));
							scale = scale*scalar;
						}
					//System.out.println("x " + x + " y " + y);

					}
				}
			}

			for(int i = 0; i<vecList.size(); i++){
				for(int j = 0; j<vecList.size(); j++)
					if(i == j){
						z = z * vecList.get(i).getArray()[j];
						//System.out.println(z);
					}

			}
            z = z/scale;
			return Math.round(z);
		 }

		 return 0;
	}
	
    public Matrix inverse() {
        Matrix determinant = new Matrix(this.getDimension());
        determinant.setVectors(this.getVectors());
        if(this.getDimension() == this.getRow()&& determinant.det() != 0) {
            Matrix matrix = new Matrix(this.getDimension());
            List<Vector> vecList = combine(this);
            double scalar = 0;
            for (int i = 0; i < vecList.size(); i++) {
                Vector.Arrange(vecList, i);
                for (int j = 0; j < this.getDimension(); j++) {
                    if (i != j) {
                        double x = vecList.get(j).getArray()[i];
                        double y = vecList.get(i).getArray()[i];

                        if (x != 0 && y != 0) {
                            scalar = -1 * (x / y);
                            vecList.get(j).add(vecList.get(i).scale(scalar));
                            vecList.get(i).unscale(scalar);
                        }
                    }
                }

                for(int j = 0; j < matrix.getDimension(); j++)
                    if(vecList.get(j).getArray()[j] != 1)
                        vecList.get(j).divide(vecList.get(j).getArray()[j]);

            }
            Vector.print_matrix(vecList);
            List<Vector> vec = extract(vecList, matrix.getDimension());
            matrix.setVectors(vec);
            matrix.setDimension(vec.get(0).getArray().length);
            matrix.setRow(vec.size());
            return matrix;
        }
       else
           return null;
    }
    public static void main(String[] args) {

        List<Vector> vecList = new ArrayList<>();
        int dimension = 3;

        double[] arr1 = {-3,0,1};
        double[] arr2 = {0,1,0};
        double[] arr3 = {1,2,3};

//        double[] arr1 = {4,2};
//        double[] arr2 = {7,6};

       /* double[] arr1 = {6,4,2};
        double[] arr2 = {1,-2,8};
        double[] arr3 = {1,5,7};*/

        Vector vector1 = new Vector(dimension, arr1);
        Vector vector2 = new Vector(dimension, arr2);
        Vector vector3 = new Vector(dimension, arr3);


        vecList.add(vector1);
        vecList.add(vector2);
        vecList.add(vector3);

        Matrix n = new Matrix(vecList, dimension);
		System.out.println(n.det() + "");
        Matrix temp = n.inverse();
        if(temp == null)
          System.out.println("NULL");
        else Vector.print_matrix(temp.getVectors());


    }
}
