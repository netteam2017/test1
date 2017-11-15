package functions;

public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {
    private int num;
    public FunctionPointIndexOutOfBoundsException(int index) {
        this.num = index;
    }
    public void myMessage(){
        System.out.println("FunctionPointIndexOutOfBoundsException[" + num + "] ");
    }
}
