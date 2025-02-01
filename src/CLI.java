import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class CLI extends JFrame {
    private final JPanel panel;
    private final JLabel commandLabel, initialFilePathLabel, keyValueLabel, textOfInitialFileLabel, textOfFinalFileLabel;
    private final JTextField initialFilePathTextField, keyValueTextField;
    private final JTextArea textOfInitialFileTextArea, textOfFinalFileTextArea;
    private final JButton chooseInitialFilePathButton, executeCommandButton, clearAllTextAreasButton;
    private final JScrollPane textOfInitialFileScrollPane, textOfFinalFileScrollPane;
    private final JComboBox<String> commandsComboBox;
    private final String[] arrayOfCommands = {"ENCRYPT","DECRYPT","BRUTE FORCE"};
    CLI(String[] args){
        this();
        if(checkingIsCommandCorrect(args[0])) commandsComboBox.setSelectedIndex(getCommandIndex(args[0]));
        if(args.length>1) initialFilePathTextField.setText(args[1]);
        if(args.length>2) keyValueTextField.setText(args[2]);
    }

    CLI() {
        super.setTitle("Cryptographer");
        super.setBounds(200,100,800,900);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        super.add(panel);

        commandLabel = new JLabel("Оберіть команду зі списку:", SwingConstants.RIGHT);
        commandLabel.setBounds(30,20,265,20);
        panel.add(commandLabel);
        commandsComboBox = new JComboBox<>(arrayOfCommands);
        commandsComboBox.setBounds(300, 20, 470, 20);
        panel.add(commandsComboBox);

        initialFilePathLabel = new JLabel("Введіть абсолютний шлях до файлу:", SwingConstants.RIGHT);
        initialFilePathLabel.setBounds(30,45,265,20);
        panel.add(initialFilePathLabel);
        initialFilePathTextField = new JTextField();
        initialFilePathTextField.setBounds(300, 45, 430, 20);
        panel.add(initialFilePathTextField);
        chooseInitialFilePathButton = new JButton("...");
        chooseInitialFilePathButton.setBounds(730, 45, 40, 20);
        panel.add(chooseInitialFilePathButton);

        keyValueLabel = new JLabel("Введіть ключ (ціле число):", SwingConstants.RIGHT);
        keyValueLabel.setBounds(30,70,265,20);
        panel.add(keyValueLabel);
        keyValueTextField = new JTextField();
        keyValueTextField.setBounds(300, 70, 470, 20);
        panel.add(keyValueTextField);

        textOfInitialFileLabel = new JLabel("Завантажений з файлу текст", SwingConstants.CENTER);
        textOfInitialFileLabel.setBounds(30,95,365,20);
        panel.add(textOfInitialFileLabel);
        textOfFinalFileLabel = new JLabel("Змінений за допомогою команди текст", SwingConstants.CENTER);
        textOfFinalFileLabel.setBounds(405,95,365,20);
        panel.add(textOfFinalFileLabel);

        textOfInitialFileTextArea = new JTextArea();
        textOfInitialFileScrollPane = new JScrollPane(textOfInitialFileTextArea);
        textOfInitialFileScrollPane.setBounds(30, 120, 365, 700);
        panel.add(textOfInitialFileScrollPane);
        textOfFinalFileTextArea = new JTextArea();
        textOfFinalFileScrollPane = new JScrollPane(textOfFinalFileTextArea);
        textOfFinalFileScrollPane.setBounds(405, 120, 365, 700);
        panel.add(textOfFinalFileScrollPane);

        executeCommandButton = new JButton("ENCRYPT");
        executeCommandButton.setBounds(470, 835, 140, 20);
        panel.add(executeCommandButton);

        clearAllTextAreasButton = new JButton("CLEAR");
        clearAllTextAreasButton.setBounds(630, 835, 140, 20);
        panel.add(clearAllTextAreasButton);

        commandsComboBox.addActionListener(_ -> executeCommandButton.setText(Objects.requireNonNull(commandsComboBox.getSelectedItem()).toString()));

        chooseInitialFilePathButton.addActionListener(_ -> inputFilePathSelection());

        executeCommandButton.addActionListener(_ -> executeCommand());

        clearAllTextAreasButton.addActionListener(_-> clearAllTextAreas());
    }
    private void inputFilePathSelection(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(panel);
        File selectedFile = fileChooser.getSelectedFile();
        initialFilePathTextField.setText(selectedFile.getAbsolutePath());
    }
    void showMassage(String massage){
        JOptionPane.showMessageDialog(panel, massage);
    }

    private String[] createArrayOfInitialArgs(){
        String command = Objects.requireNonNull(commandsComboBox.getSelectedItem()).toString();
        command = command.equals("BRUTE FORCE")? "BRUTE_FORCE" : command;
        String pathOfInitialFile = initialFilePathTextField.getText();
        String key = keyValueTextField.getText();
        return new String[]{command, pathOfInitialFile, key};
    }

    private void clearAllTextAreas(){
        textOfInitialFileTextArea.setText("");
        textOfFinalFileTextArea.setText("");
    }

    private boolean checkingIsCommandCorrect(String commandReceived){
        boolean commandIsCorrect = false;
        if(commandReceived.equals("BRUTE_FORCE")) commandReceived = "BRUTE FORCE";
        for (String command : arrayOfCommands) {
            if(command.equals(commandReceived)) {
                commandIsCorrect = true;
                break;
            }
        }
        return commandIsCorrect;
    }

    private int getCommandIndex(String commandReceived){
        return switch (commandReceived){
            case "DECRYPT" -> 1;
            case "BRUTE_FORCE" -> 2;
            default -> 0;
        };
    }

    private void executeCommand(){
        clearAllTextAreas();
        String[] args = createArrayOfInitialArgs();
        Runner runner = new Runner();
        runner.run(args, this);
        try {
            textOfInitialFileTextArea.append(runner.getFileWork().getInitialText());
            textOfFinalFileTextArea.append(runner.getFileWork().getFinalText());
            if(Objects.requireNonNull(commandsComboBox.getSelectedItem()).toString().equals("BRUTE FORCE")) {
                keyValueTextField.setText(""+runner.getFileWork().getBruteForceKey());
            }
        }catch (NullPointerException _){}
    }

}
