package com.javarush;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

class CLI extends JFrame {
    private final JPanel panel;
    private JLabel commandLabel, initialFilePathLabel, keyValueLabel, textOfInitialFileLabel, textOfFinalFileLabel;
    private JTextField initialFilePathTextField, keyValueTextField;
    private JTextArea textOfInitialFileTextArea, textOfFinalFileTextArea;
    private JButton chooseInitialFilePathButton, executeCommandButton, clearAllTextAreasButton;
    private JScrollPane textOfInitialFileScrollPane, textOfFinalFileScrollPane;
    private JComboBox<String> commandsComboBox;
    private final String[] arrayOfCommands = {"ENCRYPT", "DECRYPT", "BRUTE FORCE"};

    CLI(String[] args) {
        this();
        fillGivenFields(args);
    }

    CLI() {
        super.setTitle("Cryptographer");
        super.setBounds(200, 100, 800, 900);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        super.add(panel);
        initializeFormElements();
        setBoundsFormElements();
        addFormElementsToPanel(panel);
        setActionToElements();
    }

    private void inputFilePathSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(panel);
        File selectedFile = fileChooser.getSelectedFile();
        initialFilePathTextField.setText(selectedFile.getAbsolutePath());
    }

    void showMassage(String massage) {
        JOptionPane.showMessageDialog(panel, massage);
    }

    private String[] createArrayOfInitialArgs() {
        String command = commandsComboBox.getSelectedItem().toString();
        command = command.equals("BRUTE FORCE") ? "BRUTE_FORCE" : command;
        String pathOfInitialFile = initialFilePathTextField.getText();
        String key = keyValueTextField.getText();
        return new String[]{command, pathOfInitialFile, key};
    }

    private void clearAllTextAreas() {
        textOfInitialFileTextArea.setText("");
        textOfFinalFileTextArea.setText("");
    }

    private int getCommandIndex(String commandReceived) {
        return switch (commandReceived) {
            case "DECRYPT" -> 1;
            case "BRUTE_FORCE" -> 2;
            default -> 0;
        };
    }

    private void executeCommand() {
        clearAllTextAreas();
        String[] args = createArrayOfInitialArgs();
        Runner runner = new Runner();
        runner.run(args, this);
        textOfInitialFileTextArea.append(runner.getFileWork().getInitialText());
        textOfFinalFileTextArea.append(runner.getFileWork().getFinalText());
        if (commandsComboBox.getSelectedItem().toString().equals("BRUTE FORCE")) {
                keyValueTextField.setText(runner.getFileWork().getKey());
        }
    }

    private void fillGivenFields(String[] args) {
        commandsComboBox.setSelectedIndex(getCommandIndex(args[0]));
        if (args.length > 1) initialFilePathTextField.setText(args[1]);
        if (args.length > 2) keyValueTextField.setText(args[2]);
    }

    private void initializeFormElements() {
        commandLabel = new JLabel("Оберіть команду зі списку:", SwingConstants.RIGHT);
        commandsComboBox = new JComboBox<>(arrayOfCommands);
        initialFilePathLabel = new JLabel("Введіть абсолютний шлях до файлу:", SwingConstants.RIGHT);
        initialFilePathTextField = new JTextField();
        chooseInitialFilePathButton = new JButton("...");
        keyValueLabel = new JLabel("Введіть ключ (ціле число):", SwingConstants.RIGHT);
        keyValueTextField = new JTextField();
        textOfInitialFileLabel = new JLabel("Завантажений з файлу текст", SwingConstants.CENTER);
        textOfFinalFileLabel = new JLabel("Змінений за допомогою команди текст", SwingConstants.CENTER);
        textOfInitialFileTextArea = new JTextArea();
        textOfInitialFileScrollPane = new JScrollPane(textOfInitialFileTextArea);
        textOfFinalFileTextArea = new JTextArea();
        textOfFinalFileScrollPane = new JScrollPane(textOfFinalFileTextArea);
        executeCommandButton = new JButton("ENCRYPT");
        clearAllTextAreasButton = new JButton("CLEAR");
    }

    private void setBoundsFormElements() {
        commandLabel.setBounds(30, 20, 265, 20);
        commandsComboBox.setBounds(300, 20, 470, 20);
        initialFilePathLabel.setBounds(30, 45, 265, 20);
        initialFilePathTextField.setBounds(300, 45, 430, 20);
        chooseInitialFilePathButton.setBounds(730, 45, 40, 20);
        keyValueLabel.setBounds(30, 70, 265, 20);
        keyValueTextField.setBounds(300, 70, 470, 20);
        textOfInitialFileLabel.setBounds(30, 95, 365, 20);
        textOfFinalFileLabel.setBounds(405, 95, 365, 20);
        textOfInitialFileScrollPane.setBounds(30, 120, 365, 700);
        textOfFinalFileScrollPane.setBounds(405, 120, 365, 700);
        executeCommandButton.setBounds(470, 835, 140, 20);
        clearAllTextAreasButton.setBounds(630, 835, 140, 20);
    }

    private void addFormElementsToPanel(JPanel panel) {
        panel.add(commandLabel);
        panel.add(commandsComboBox);
        panel.add(initialFilePathLabel);
        panel.add(initialFilePathTextField);
        panel.add(chooseInitialFilePathButton);
        panel.add(keyValueLabel);
        panel.add(keyValueTextField);
        panel.add(textOfInitialFileLabel);
        panel.add(textOfFinalFileLabel);
        panel.add(textOfInitialFileScrollPane);
        panel.add(textOfFinalFileScrollPane);
        panel.add(executeCommandButton);
        panel.add(clearAllTextAreasButton);
    }

    private void setActionToElements() {
        commandsComboBox.addActionListener(_ -> executeCommandButton.setText(Objects.requireNonNull(commandsComboBox.getSelectedItem()).toString()));
        chooseInitialFilePathButton.addActionListener(_ -> inputFilePathSelection());
        executeCommandButton.addActionListener(_ -> executeCommand());
        clearAllTextAreasButton.addActionListener(_ -> clearAllTextAreas());
    }
}