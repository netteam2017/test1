import functions.*;


public class Main {
    public static void main(String[] args)throws InappropriateFunctionPointException {
        TabulatedFunction f = new LinkedListTabulatedFunction(0, 10, 10);//создание экземпляра функции с 10-ю точками
        //TabulatedFunction f2 = new LinkedListTabulatedFunction(9, 2, 10);
        double[] y = {1, 2, 4, 5, 24, 14};
        TabulatedFunction f1 = new ArrayTabulatedFunction(2, 12, y);
        System.out.println("Точки функции F");
        for(int i = -1; i < f.getPointCount(); i++){
            System.out.println("(" + f.getPointX(i) + "," + f.getPointY(i) + ")");
        }
        System.out.println("Точки функции F1");
        for(int i = 0; i < f1.getPointCount(); i++){
            System.out.println("(" + f1.getPointX(i) + "," + f1.getPointY(i) + ")");
        }
        System.out.println("Значение ф-ции F в точке 9");
        System.out.println(f.getFuntionValue(9));
        System.out.println("Значение ф-ции F в точке 11");
        System.out.println(f.getFuntionValue(11));
        FunctionPoint a = new FunctionPoint(5.5, 205);
        f.addPoint(a);
        System.out.println("ф-ция F с добавленной точкой (5.5,205)");
        for(int i = 0; i < f.getPointCount(); i++){
            System.out.println("(" + f.getPointX(i) + "," + f.getPointY(i) + ")");
        }
    }
}
