package functions;

public class ArrayTabulatedFunction implements TabulatedFunction{
    private FunctionPoint[] points; //массив точек
    private int size; //количесвто точек в массиве

//конструктор (лев X, прав X, кол-во точек)
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if(leftX >= rightX || pointsCount < 2) throw new IllegalArgumentException("Некорректные аргументы");
        double h = (rightX - leftX)/pointsCount; //шаг
        this.size = pointsCount;//запоминаем количесвто точек
        this.points = new FunctionPoint[pointsCount+5]; //инициализируем массив с количесвом элементов на 5 больше
        for(int i = 0; i < this.size; i++){ //заполняем массив
            this.points[i] = new FunctionPoint(leftX + i*h, 0); //инициализируем точки
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if(leftX >= rightX || values.length < 2) throw new IllegalArgumentException("Некорректные аргументы");
        double h = (rightX - leftX)/values.length; //шаг
        this.size = values.length; //запоминаем количство точек
        this.points = new FunctionPoint[values.length+5];//инициализируем массив с количесвом элементов на 5 больше
        for(int i = 0; i < size; i++){ //заполняем массив
            this.points[i] = new FunctionPoint(leftX + i*h, values[i]); //инициализируем точки
        }
    }
//методы
    @Override
    public double getLeftDomainBorder(){
        return this.points[0].getX(); //возвращает левую границу X
    }
    @Override
    public double getRightDomainBorder(){
        return this.points[this.size-1].getX(); //возвращает правую границу X
    }
    @Override
    public double getFuntionValue(double x){ //значение функции в точке X
        if(x >= getLeftDomainBorder() && x <= getRightDomainBorder()) return (int)points[(int)(x-getLeftDomainBorder())].getY()+((int)points[(int)(x-getLeftDomainBorder())].getY()+1-(int)points[(int)(x-getLeftDomainBorder())].getY())/((int)points[(int)(x-getLeftDomainBorder())].getX()+1-(int)points[(int)(x-getLeftDomainBorder())].getX())*(x*(int)points[(int)(x-getLeftDomainBorder())].getX());
        else return Double.NaN;
    }
    @Override
    public int getPointCount(){
        return this.size; //возвращает количесвто точек
    }
    @Override
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        return this.points[index];//получение точки по индексу
    }
    @Override
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {//помещение точки
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        if(point.getX() > points[index-1].getX() && point.getX() < points[index+1].getX()){ //проверка: больше пред X, меньше след X
            points[index] = point; //присваивание новой точки
        }
        else throw new InappropriateFunctionPointException(index);
    }
    @Override
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException { //получение X по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        return this.points[index].getX();
    }
    @Override
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException { //изменение Х по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        if(x > points[index-1].getX() && x < points[index+1].getX()) points[index].setX(x);//проверка: больше пред X, меньше след X
        else throw new InappropriateFunctionPointException(index);
    }
    @Override
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException { //получение У по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        return this.points[index].getY();
    }
    @Override
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{ ////изменение У по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        points[index].setY(y);
    }
    @Override
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException { //удаление точки
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        if(this.size < 3) throw new IllegalStateException("Удаление невозможно, точек менее трех");
        points[index] = null; //присваиваем точке null
        for(int i = index; i < this.size-1; i++){
            points[i] = points[i+1]; //сдвигаем точки на одну влево
        }
        points[this.size-1] = null; //концу присваиваем nuLL
        this.size -= 1; //уменьшаем количесвто точек на 1
    }
    @Override
    public void addPoint(FunctionPoint point)throws InappropriateFunctionPointException {//добавление точки
        boolean check = true;
        for(int i = 0; i < this.size; ++i) {
            if(this.points[i].getX() == point.getX()) check = false;
        }
        if(check){
            if(this.size == points.length){ //если размер массива совпал с количесвом точек
                FunctionPoint[] temp = new FunctionPoint[size+1]; //создаем временный массив
                System.arraycopy(this.points, 0, temp, 0, size);//копируем в него наши элементы. arraycope(нач масс, нач копр, кон масс, с какого вставлять, кон копр)
                this.points = new FunctionPoint[size+5];//разширяем наш массив
                addPoint(point);//добавляем точку
            }
            else {
                this.points[size] = point; // добавляем точку
                this.size += 1; //увеличиваем размер на 1
                for(int i = 1; i < this.size; i++) { //сортировка
                    for(int j = i; j > 0 && this.points[j-1].getX() > this.points[j].getX(); j--){
                        FunctionPoint temp = this.points[j-1];
                        this.points[j-1] = this.points[j];
                        this.points[j] = temp;
                        }
                    }
            }
        }
        else throw new InappropriateFunctionPointException((int) point.getX());
    }
}