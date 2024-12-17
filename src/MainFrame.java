import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JTextArea resultArea; // Текстовая область для вывода таблицы

    public MainFrame() {
        setTitle("Main Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Центрируем окно

        // Инициализация текстовой области
        resultArea = new JTextArea();
        resultArea.setEditable(false); // Запрещаем редактирование
        JScrollPane scrollPane = new JScrollPane(resultArea); // добавляем скроллбар
        add(scrollPane, BorderLayout.CENTER); // размещаем в центре окна

        // Создание меню
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem createMenuItem = new JMenuItem("Создать");
        fileMenu.add(createMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Обработчик нажатия на пункт меню "Создать"
        createMenuItem.addActionListener(e -> openNewWindow());
    }

    private void openNewWindow() {
        NewWindow newWindow = new NewWindow(this); // Передаём ссылку на главное окно
        newWindow.setVisible(true);
    }

    public void displayTable(String tableType) {
        StringBuilder table = new StringBuilder();
        if(tableType.equals("Умножение")) {
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 10; j++) {
                    table.append(String.format("%4d", i * j));
                }
                table.append("\n");
            }
        } else if(tableType.equals("Сложение")){
            for (int i = 0; i <= 10; i++) {
                for (int j = 0; j <= 10; j++) {
                    table.append(String.format("%4d", i + j));
                }
                table.append("\n");
            }
        }

        resultArea.setText(table.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    class NewWindow extends JFrame {
        private ButtonGroup radioGroup;
        private JButton okButton;
        private MainFrame mainFrame;

        public NewWindow(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
            setTitle("New Window");
            setSize(300, 200);
            setLocationRelativeTo(null); // центрируем относительно родительского окна
            setLayout(new BorderLayout()); // используем BorderLayout для общего размещения

            // Создаем панель для радиокнопок с FlowLayout
            JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JRadioButton multiplicationButton = new JRadioButton("Отображать таблицу умножения");
            JRadioButton additionButton = new JRadioButton("Отображать таблицу сложения");

            radioGroup = new ButtonGroup();
            radioGroup.add(multiplicationButton);
            radioGroup.add(additionButton);

            radioPanel.add(multiplicationButton);
            radioPanel.add(additionButton);

            okButton = new JButton("OK");

            // Размещаем панель с радиокнопками в верхней части
            add(radioPanel, BorderLayout.CENTER);
            // размещаем кнопку по центру в нижней части
            add(okButton, BorderLayout.SOUTH);

            okButton.addActionListener(e -> {
                String selectedOption = null;
                if (multiplicationButton.isSelected()) {
                    selectedOption = "Умножение";
                } else if (additionButton.isSelected()) {
                    selectedOption = "Сложение";
                }

                if(selectedOption != null){
                    this.mainFrame.displayTable(selectedOption);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Необходимо выбрать тип таблицы");
                }
            });
        }
    }
}