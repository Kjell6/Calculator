import model.Logic;
import view.View;

public class Calculator {
    public static void main(String[] args) {
        Logic logic = new Logic();
        new View(logic);

    }
}
