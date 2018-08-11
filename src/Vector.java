/*
    ADVDISC S18
    ALEJANDRINO, Cheska
    RESPICIO, Michael
    ORTIZ, Pamela
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class Vector {

    private int dimension;
    private double[] array;

    public Vector(int dimension) {
        this.dimension = dimension;
        this.array = new double[dimension];
        for (int i = 0; i < dimension; i++)
            array[i] = 0;
    }

    public Vector(int dimension, double[] array) {
        this.dimension = dimension;
        this.array = new double[dimension];
        this.array = array;

    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double[] getArray() {
        return array;
    }

    public void setArray(double[] array) {
        this.array = array;
    }

    public Vector scale(double scalar) {
        for (int i = 0; i < this.getArray().length; i++)
            array[i] = array[i] * scalar;

        return this;
    }

    public Vector unscale(double scalar) {
        for (int i = 0; i < this.getArray().length; i++)
            array[i] = array[i] / scalar;

        return this;

    }

    public Vector add(Vector addend) {
        if (dimension == addend.getDimension()) {
            for (int i = 0; i < this.getArray().length; i++)
                this.array[i] += addend.getArray()[i];

            return this;
        } else
            return null;

    }

    public static void transform(List<Vector> vectors, int dimension) {

        double[][] temp = new double[dimension][vectors.size()];

        for (int i = 0; i < vectors.size(); i++)
            for (int j = 0; j < vectors.get(i).getArray().length; j++)
                temp[j][i] = vectors.get(i).getArray()[j];

        vectors.clear();
        //System.out.println("T; Temp size: " +temp.length);
        for (int i = 0; i < temp.length; i++) {
            vectors.add(new Vector(dimension, temp[i]));
        }

    }

    public static List<Vector> combine(List<Vector> vector, Vector constants, int dimension) {
        List<Vector> vectors = new ArrayList<>();
        transform(vector, dimension);
        for (int i = 0; i < vector.size(); i++) {
            double[] array = new double[vector.get(i).getArray().length + 1];
            for (int j = 0; j <= vector.get(i).getArray().length; j++) {
                if (j == vector.get(i).getArray().length) {
                    array[j] = constants.getArray()[i];
                } else {
                    array[j] = vector.get(i).getArray()[j];
                }
            }
            vectors.add(new Vector(dimension + 1, array));
        }
        return vectors;
    }

    static void print_matrix(List<Vector> vecist) {
        System.out.println("Current Matrix: ");
        for (int i = 0; i < vecist.size(); i++) {
            for (int j = 0; j < vecist.get(i).getArray().length; j++)
                System.out.print(vecist.get(i).getArray()[j] + " ");
            System.out.println();
        }
    }

    static int IsSolution(List<Vector> vectors, int flag) {

        flag = 3;

        for (int i = 0; i < vectors.size(); i++) {
            double sum = 0;
            for (int j = 0; j < vectors.get(i).getArray().length; j++) {
                sum += vectors.get(i).getArray()[j];
                if (sum == vectors.get(i).getArray()[j])
                    flag = 2;
            }
        }

        return flag;

    }

    public static void Arrange(List<Vector> vectors, int j) {
        int size = vectors.size();
            for (int k = j; k < size - 1; k++)
                if( vectors.get(k).getArray()[j] < vectors.get(k+1).getArray()[j]) {
                    double[] temp = vectors.get(k+1).getArray();
                    vectors.get(k+1).setArray(vectors.get(k).getArray());
                    vectors.get(k).setArray(temp);
                }
    }



    public static Vector Gauss_Jordan(List<Vector> vectors, int dimension, Vector constants) {

      if(vectors.size() == constants.getDimension()) {
            List<Vector> vecList = combine(vectors, constants, dimension);
            int ctr, flag = 0;
            double scalar = 0;
            for (int i = 0; i < vecList.size(); i++) {
              Arrange(vecList, i);
              print_matrix(vecList);
              if (vecList.get(i).getArray()[i] == 0) {
                  ctr = 1;
                  while ((((i + ctr) < vecList.size()) && vecList.get(i + ctr).getArray()[i] == 0))
                      ctr++;
                  if ((i + ctr) == vecList.size()) {
                      flag = 1;
                      break;
                  }
              }


                for (int j = 0; j < vecList.size(); j++) {
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
                print_matrix(vecList);
            }

            print_matrix(vecList);
            Vector v = new Vector(dimension);

            if (flag == 1)
                flag = IsSolution(vecList, flag);

            if (flag == 2) {
                v.getArray()[0] = Integer.MAX_VALUE;
                return null;
            } else if (flag == 3) {
                return null;
            } else {

                for (int m = 0; m < vecList.size(); m++) {
                     if(vecList.get(m).getArray()[vecList.size()] != 0  && vecList.get(m).getArray()[m] != 0)
                             v.getArray()[m] = vecList.get(m).getArray()[vecList.size()] / vecList.get(m).getArray()[m];
                    else
                        return null;
                }
                if (v.getArray().length != constants.getArray().length) {
                    return null;
                }

            }
            return v;
        }
        else
           return null;
    }


    public static int span(List<Vector> list, int dimension) {
        int count = 0, zeroes = 0;
       // print_matrix(list);
        Vector.Gauss_Jordan(list, dimension, new Vector(dimension));
        //print_matrix(list);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < dimension; j++) {
                if (list.get(i).getArray()[j] == 0)
                    zeroes++;
            }
            if (zeroes == dimension)
                count++;
        }

        return (list.size() - count);

    }

    public static void main(String[] args) {

        List<Vector> vecList = new ArrayList<>();
        /*
        test 1 :([[2, 3], [3, 5]], 2, [5, 8]), expected answer is [1, 1] - correct
        test 2 :([[0, 2], [2, 7]], 2, [2, 9]), expected answer is [1, 1] - correct
        test 3 :[[4, 2], [0, 0]], 2, [1, 2]), expected answer is null - correct
        test 4 :([[0, 0], [0, 0]], 2, [1, 1]), expected answer is null - correct
        test 5 :([[1, 0, 0], [0, 1, 0], [0, 0, 1]], 3, [1, 1, 1]), expected answer is [1, 1, 1] - correct
        test 6 :[[1, 2, 3], [3, 4, 6], [1, 0, 1]], 3, [5, 6, 10]), expected answer is [1, 1, 1] - correct
        test 7 :([[1, 2, 4], [1, 2, 6], [0, 2, 3]], 3, [2, 6, 13]), expected answer is [1, 1, 1] - correct
        test 8 :([[1, 2, 2, 3], [2, 4, 4, 2], [5, 8, 10, 7], [7, 16, 14, 2]], 4, [0, 0, 0, 0]), expected answer is null - correct
        test 9 :([[1, 0, 0, 0, 0], [0, 1, 0, 0, 0], [0, 0, 1, 0, 0], [0, 0, 0, 1, 0], [0, 0, 0, 0, 1]], 5, [1, 1, 1, 1, 1]), expected answer is [1, 1, 1, 1, 1] - correct
        test 10:([[1,2,3,4,5,6],[10,0,0,0,0,10],[10,0,0,0,0,10],[10,0,0,0,0,10],[10,0,0,0,0,10],[1,2,3,4,5,6]], 6, [0,0,0,0,0,0]), expected answer is null - correct
        test 11: 8x8 identity
        test 12:([[1, 2, 2, 3], [2, 4, 5, 2], [5, 8, 10, 7], [7, 16, 14, 2]], 4, [14, 30, 31, 14]), expected answer is [1, 1, 1, 1] - 2.3333333333333335 3.0 1.0256410256410255 0.41025641025641024
        test 13:([[0, 2], [0, 4], [0, 9], [0, 2]], 2, [2, 4]), expected answer is null - correct
        test 14:([[1,2,2,2,2,2],[2,1,2,2,2,2],[2,2,1,2,2,2]], 6, [0,0,0,0,0,0]), expected answer is null - correct
        test 15:([[2,1], 2, [2,3]), expected answer is null - correct
     */
        int dimension = 4;

        double[] arr1 = {1, 2, 2, 3};
        double[] arr2 = {2, 4, 5, 2};
        double[] arr3 = {5, 8, 10, 7};
        double[] arr4 = {7, 16, 14, 2};
        /*double[] arr5 = {10,0,0,0,0,10};
        double[] arr6 = {1,2,3,4,5,6};*/


        Vector vector1 = new Vector(dimension, arr1);
        Vector vector2 = new Vector(dimension, arr2);
        Vector vector3 = new Vector(dimension, arr3);

        Vector vector4 = new Vector(dimension, arr4);
        /*Vector vector5 = new Vector(dimension, arr5);
        Vector vector6 = new Vector(dimension, arr6);*/

        vecList.add(vector1);
        vecList.add(vector2);
        vecList.add(vector3);

        vecList.add(vector4);
        /*vecList.add(vector5);
        vecList.add(vector6);
        */
        double[] cons = {14, 30, 31, 14};
        Vector constants = new Vector(dimension, cons);
        Vector v = Gauss_Jordan(vecList, dimension, constants);

        if(v != null) {

            if(v.getArray()[0] == Integer.MAX_VALUE) {
                System.out.println(v.getArray()[0] );
            } else if(v.getArray().length == constants.getArray().length) {
                for (int c = 0; c < v.getArray().length; c++)
                    System.out.print(v.getArray()[c] + " ");
            }
        }  else
            System.out.println("Null");


    }
}