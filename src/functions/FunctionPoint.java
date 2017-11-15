package functions;

public class FunctionPoint {
    private double x, y;
    
    double getX(){
        return x;
    }
    double getY(){
        return y;
    }
    void setX(double oX){
        x = oX;
    }
    void setY(double oY){
        y = oY;
    }
    //конструктор, который создает точку с координатами (0, 0)
    public FunctionPoint(){ 
        x = 0.0;
        y = 0.0;
    }
    //конструктор, который создаёт объект точки с заданными координатами
    public FunctionPoint(double x, double y){ 
        this.x = x;
        this.y = y;
    }
    //конструктор, который создаёт объект точки с теми же координатами, что у указанной точки;
    public FunctionPoint(FunctionPoint point){ 
        this.x = point.x;
        this.y = point.y;
    }
}
