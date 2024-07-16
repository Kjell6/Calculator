import model.Logic;
import view.View;

public class Calculator {
    public static void main(String[] args) {
        Logic logic = new Logic();
        View view = new View(logic);
    }
}
