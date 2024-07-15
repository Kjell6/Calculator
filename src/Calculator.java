import javax.swing.*;

public class Calculator {
    public static void main(String[] args) {
        Logic logic = new Logic();
        View view = new View(logic);
    }
}
