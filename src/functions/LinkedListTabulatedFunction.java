package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction {

    class FunctionNode { //Класс вершины связного листа
        private FunctionPoint point; //поле значения вершины (точка)
        private FunctionNode prev; //поле предыдущей вершины
        private FunctionNode next; //поле следующей вершины
        //конструкторы
        public FunctionNode(){
            this.point = new FunctionPoint();
            this.prev = null;
            this.next = null;
        }
        public FunctionNode(FunctionNode prev, FunctionNode next){
            this.point = new FunctionPoint();
            this.prev = prev;
            this.next = next;
        }
        public FunctionNode(FunctionNode prev, FunctionNode next, FunctionPoint point){
            this.point = point;
            this.prev = prev;
            this.next = next;
        }
        public void setPoint(FunctionPoint point){
            this.point = point;
        }
        public FunctionPoint getPoint(){
            return this.point;
        }
        public void setNext(FunctionNode next){
            this.next = next;
        }
        public FunctionNode getNext(){
            return this.next;
        }
        public void setPrev(FunctionNode prev){
            this.prev = prev;
        }
        public FunctionNode getPrev(){
            return this.prev;
        }
    }
private FunctionNode root; //корень
private int size; // размер связного списка

    private FunctionNode getNodeByIndex(int index){ //получение вершины по индексу связного списка
        int steps; //шаги
        boolean clockwise; //по часвой стрелке?
        FunctionNode node;
        if(index <= this.size - 1 - index){ // по часовой
            steps = index;
            node = root.getNext();
            clockwise = true;
        }
        else { //против часовой
            steps = this.size - index - 1;
            node = root.getPrev();
            clockwise = false;
        }
        for(int i = 0; i < steps; ++i){
            node = clockwise ? node.getNext() : node.getPrev(); //по час стрелку ? ДА : НЕТ
        }
        return node;
    }

    private FunctionNode addNodeToTail(){ //добавление в конец списка
        root.getPrev().setNext(new FunctionNode(root.getPrev(), root)); //предыдущему головы присваиваем новую следующую вершину
        root.setPrev(root.getPrev().getNext()); //присваиваем новую предыдущую вершину голове
        ++size; //увеличиваем размер
        return root.getPrev();
    }

    private FunctionNode addNodeByIndex(int index) { //добавление по индексу
        FunctionNode node = getNodeByIndex(index);
        node.getPrev().setNext(new FunctionNode(node.getPrev(), node)); //предыдущему вершины присваиваем новую следующую вершину
        node.setPrev(node.getPrev().getNext()); //присваиваем новую предыдущую вершину вершине
        ++size; //увеличиваем размер
        return node.getPrev();
    }

    private FunctionNode deleteNodeByIndex(int index) { //удаление по индексу
        FunctionNode node = getNodeByIndex(index); //Удаляемая вершина (УВ)
        node.getPrev().setNext(node.getNext()); //Предыдущей УВ присваимваем следующую следующую УВ
        node.getNext().setPrev(node.getPrev());//следующей УВ присваиваем предыдущую предыдущую УВ
        node.setNext(null); //удаляем информацию о вершине
        node.setPrev(null);
        --size; //уменьшаем размер
        return node;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if(leftX >= rightX || pointsCount < 2) throw new IllegalArgumentException("Некорректные аргументы");
        double h = (rightX - leftX)/pointsCount; //шаг
        root = new FunctionNode(root, root, null);
        root.setNext(root);
        root.setPrev(root);
        for(int i = 0; i < pointsCount; i++){ //заполняем массив
            addNodeToTail().setPoint(new FunctionPoint(leftX + i*h, 0.0));
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if(leftX >= rightX || values.length < 2) throw new IllegalArgumentException("Некорректные аргументы");
        double h = (rightX - leftX)/values.length; //шаг
        root = new
        FunctionNode(root, root, null);
        root.setNext(root);
        root.setPrev(root);
        for(int i = 0; i < values.length; i++){ //заполняем массив
            addNodeToTail().setPoint(new FunctionPoint(leftX + i*h, values[i]));
        }
    }
    //методы
    @Override
    public double getLeftDomainBorder(){
        return root.getNext().getPoint().getX(); //возвращает левую границу X
    }
    @Override
    public double getRightDomainBorder(){
        return root.getPrev().getPoint().getX(); //возвращает правую границу X
    }
    @Override
    public double getFuntionValue(double x){ //значение функции в точке X
        if(x >= getLeftDomainBorder() && x <= getRightDomainBorder()){
            for(int i = 0; i < this.size; ++i){
                if(x == getNodeByIndex(i).getPoint().getX()) return getNodeByIndex(i).getPoint().getY();
            }
        }
        else{
            return (int)getNodeByIndex((int)(x-getLeftDomainBorder())).getPoint().getY()+((int)getNodeByIndex((int)(x-getLeftDomainBorder())).getPoint().getY()+1-(int)getNodeByIndex((int)(x-getLeftDomainBorder())).getPoint().getY())/((int)getNodeByIndex((int)(x-getLeftDomainBorder())).getPoint().getX()+1-(int)getNodeByIndex((int)(x-getLeftDomainBorder())).getPoint().getX())*(x*(int)getNodeByIndex((int)(x-getLeftDomainBorder())).getPoint().getX());
        }
        return Double.NaN;
    }
    @Override
    public int getPointCount(){
        return this.size; //возвращает количесвто точек
    }
    @Override
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        return getNodeByIndex(index).getPoint();//получение точки по индексу
    }
    @Override
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {//помещение точки
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        if(point.getX() > getNodeByIndex(index-1).getPoint().getX() && point.getX() < getNodeByIndex(index+1).getPoint().getX()){ //проверка: больше пред X, меньше след X
            getNodeByIndex(index).setPoint(point); //присваивание новой точки
        }
        else throw new InappropriateFunctionPointException(index);
    }
    @Override
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException { //получение X по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        return getNodeByIndex(index).getPoint().getX();
    }
    @Override
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException { //изменение Х по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        if(x > getNodeByIndex(index-1).getPoint().getX() && x < getNodeByIndex(index+1).getPoint().getX()) getNodeByIndex(index).getPoint().setX(x);//проверка: больше пред X, меньше след X
        else throw new InappropriateFunctionPointException(index);
    }
    @Override
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException { //получение У по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        return getNodeByIndex(index).getPoint().getY();
    }
    @Override
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{ ////изменение У по индексу
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        getNodeByIndex(index).getPoint().setY(y);
    }
    @Override
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException { //удаление точки
        if(index < 0 && index >= this.size) throw new FunctionPointIndexOutOfBoundsException(index);
        if(this.size < 3) throw new IllegalStateException("Удаление невозможно, точек менее трех");
        FunctionNode node = deleteNodeByIndex(index);
        --size;
    }
    @Override
    public void addPoint(FunctionPoint point)throws InappropriateFunctionPointException {//добавление точки
        if(root.getPrev().getPoint().getX() < point.getX()) addNodeToTail().setPoint(point);
        else {
            int i = 0;
            FunctionNode node = root.getNext();
            while(point.getX() >= node.getPoint().getX() && node != root){
                node = node.getNext();
                ++i;
            }
            if(node.getPrev().getPoint().getX() == point.getX()) throw new InappropriateFunctionPointException((int) point.getX());
            else addNodeByIndex(i).setPoint(point);
        }
    }
}
