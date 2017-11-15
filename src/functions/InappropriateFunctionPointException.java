package functions;

public class InappropriateFunctionPointException extends  Exception {
    private int num;
    public InappropriateFunctionPointException(int number) {
        this.num = number;
    }
    public void myMessage(){
        System.out.println(num + " not only one .");
    }
}
