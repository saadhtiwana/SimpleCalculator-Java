import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lab02Task2 extends JFrame {

    private JTextField displayField;
    private String currentInput = "";
    private double result = 0;
    private String lastOperator = "=";
    private boolean isOperatorPressed = false;

    public lab02Task2() {
        super("Calculator");

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.PLAIN, 30));
        displayField.setHorizontalAlignment(JTextField.RIGHT);

        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 24));
            numberButtons[i].addActionListener(new NumberButtonListener());
        }

        JButton clearButton = new JButton("C");
        JButton equalButton = new JButton("=");
        JButton addButton = new JButton("+");
        JButton subtractButton = new JButton("-");
        JButton multiplyButton = new JButton("*");
        JButton divideButton = new JButton("/");

        clearButton.setFont(new Font("Arial", Font.PLAIN, 24));
        equalButton.setFont(new Font("Arial", Font.PLAIN, 24));
        addButton.setFont(new Font("Arial", Font.PLAIN, 24));
        subtractButton.setFont(new Font("Arial", Font.PLAIN, 24));
        multiplyButton.setFont(new Font("Arial", Font.PLAIN, 24));
        divideButton.setFont(new Font("Arial", Font.PLAIN, 24));

        clearButton.addActionListener(new ClearButtonListener());
        equalButton.addActionListener(new EqualButtonListener());
        addButton.addActionListener(new OperatorButtonListener());
        subtractButton.addActionListener(new OperatorButtonListener());
        multiplyButton.addActionListener(new OperatorButtonListener());
        divideButton.addActionListener(new OperatorButtonListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBackground(Color.lightGray);

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(divideButton);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(multiplyButton);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(subtractButton);

        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(clearButton);
        buttonPanel.add(equalButton);
        buttonPanel.add(addButton);

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(displayField, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String number = e.getActionCommand();
            // Prevent multiple decimal points in the current number
            if (number.equals(".") && currentInput.contains(".")) {
                return; // Ignore the dot if already present
            }
            if (isOperatorPressed) {
                currentInput = number;
                isOperatorPressed = false;
            } else {
                currentInput += number;
            }
            displayField.setText(currentInput);
        }
    }

    private class OperatorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String operator = e.getActionCommand();
            if (!currentInput.isEmpty()) {
                calculateResult();
                lastOperator = operator;
                isOperatorPressed = true;
            }
        }
    }

    private class EqualButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculateResult();
            lastOperator = "=";
            isOperatorPressed = true;
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentInput = "";
            result = 0;
            lastOperator = "=";
            displayField.setText("");
        }
    }

    private void calculateResult() {
        try {
            double currentNumber = Double.parseDouble(currentInput);
            switch (lastOperator) {
                case "+":
                    result += currentNumber;
                    break;
                case "-":
                    result -= currentNumber;
                    break;
                case "*":
                    result *= currentNumber;
                    break;
                case "/":
                    if (currentNumber == 0) {
                        displayField.setText("Error");
                        currentInput = "";
                        result = 0;
                        return;
                    } else {
                        result /= currentNumber;
                    }
                    break;
                case "=":
                    result = currentNumber;
                    break;
            }
            currentInput = String.valueOf(result);
            displayField.setText(currentInput);
        } catch (NumberFormatException e) {
            displayField.setText("Error");
            currentInput = "";
            result = 0;
        }
    }

    public static void main(String[] args) {
        new lab02Task2();
    }
}
