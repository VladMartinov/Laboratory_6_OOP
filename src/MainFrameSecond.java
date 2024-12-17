import javax.swing.*;
import java.awt.*;

public class MainFrameSecond extends JFrame {
    private JDesktopPane desktopPane;
    private JTextArea mainTextArea; // Текстовая область главного окна

    public MainFrameSecond() {
        setTitle("Main Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem createMenuItem = new JMenuItem("Создать");
        fileMenu.add(createMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        createMenuItem.addActionListener(e -> showCreateWindowDialog());
    }

    private void showCreateWindowDialog() {
        String[] options = {"Внутреннее окно", "Отдельное окно"};
        int choice = JOptionPane.showOptionDialog(this,
                "Выберите тип окна для создания:",
                "Тип окна",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            openNewInternalFrame();
        } else if (choice == 1) {
            openNewFrame();
        }
    }

    private void openNewInternalFrame() {
        NewInternalFrame newFrame = new NewInternalFrame(this);
        desktopPane.add(newFrame);
        newFrame.setVisible(true);
    }

    private void openNewFrame() {
        NewOuterFrame newFrame = new NewOuterFrame(this);
        newFrame.setVisible(true);
    }

    // Метод для отображения таблицы в JTextArea внутреннего окна
    public void displayTableInInternalFrame(String tableType, JTextArea textArea) {
        StringBuilder table = new StringBuilder();
        if (tableType.equals("умножение")) {
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 10; j++) {
                    table.append(String.format("%4d", i * j));
                }
                table.append("\n");
            }
        } else if (tableType.equals("сложение")) {
            for (int i = 0; i <= 10; i++) {
                for (int j = 0; j <= 10; j++) {
                    table.append(String.format("%4d", i + j));
                }
                table.append("\n");
            }
        }

        textArea.setText(table.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrameSecond frame = new MainFrameSecond();
            frame.setVisible(true);
        });
    }

    class NewInternalFrame extends JInternalFrame {
        private ButtonGroup radioGroup;
        private JButton okButton;
        private JTextArea resultArea;

        private MainFrameSecond mainFrame;

        public NewInternalFrame(MainFrameSecond mainFrame) {
            super("New Internal Frame", true, true, true, true);
            this.mainFrame = mainFrame;
            setSize(350, 250);
            setLocation(20, 20);

            resultArea = new JTextArea();
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);
            setLayout(new BorderLayout());
            add(scrollPane,BorderLayout.CENTER);

            JPanel radioPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JRadioButton multiplicationButton = new JRadioButton("отображать таблицу умножения");
            radioPanel.add(multiplicationButton,gbc);
            gbc.gridy=1;
            JRadioButton additionButton = new JRadioButton("отображать таблицу сложения");
            radioPanel.add(additionButton,gbc);

            radioGroup = new ButtonGroup();
            radioGroup.add(multiplicationButton);
            radioGroup.add(additionButton);

            okButton = new JButton("OK");
            add(radioPanel, BorderLayout.NORTH);
            add(okButton,BorderLayout.SOUTH);

            okButton.addActionListener(e -> {
                String selectedOption = null;
                if (multiplicationButton.isSelected()) {
                    selectedOption = "умножение";
                } else if (additionButton.isSelected()) {
                    selectedOption = "сложение";
                }
                if (selectedOption != null) {
                    mainFrame.displayTableInInternalFrame(selectedOption,resultArea);
                } else {
                    JOptionPane.showMessageDialog(this, "Необходимо выбрать тип таблицы");
                }
            });
        }
    }

    class NewOuterFrame extends JFrame {
        private ButtonGroup radioGroup;
        private JButton okButton;
        private JTextArea resultArea;
        private MainFrameSecond mainFrame;
        public NewOuterFrame(MainFrameSecond mainFrame) {
            setTitle("New Frame");
            this.mainFrame=mainFrame;
            setSize(350, 250);
            setLocationRelativeTo(null);
            resultArea = new JTextArea();
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea);
            setLayout(new BorderLayout());
            add(scrollPane,BorderLayout.CENTER);

            JPanel radioPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JRadioButton multiplicationButton = new JRadioButton("отображать таблицу умножения");
            radioPanel.add(multiplicationButton,gbc);
            gbc.gridy=1;
            JRadioButton additionButton = new JRadioButton("отображать таблицу сложения");
            radioPanel.add(additionButton,gbc);

            radioGroup = new ButtonGroup();
            radioGroup.add(multiplicationButton);
            radioGroup.add(additionButton);

            okButton = new JButton("OK");
            add(radioPanel, BorderLayout.NORTH);
            add(okButton, BorderLayout.SOUTH);

            okButton.addActionListener(e -> {
                String selectedOption = null;
                if (multiplicationButton.isSelected()) {
                    selectedOption = "умножение";
                } else if (additionButton.isSelected()) {
                    selectedOption = "сложение";
                }
                if (selectedOption != null) {
                    mainFrame.displayTableInInternalFrame(selectedOption,resultArea);
                } else {
                    JOptionPane.showMessageDialog(this, "Необходимо выбрать тип таблицы");
                }
            });
        }
    }
}