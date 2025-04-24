import java.awt.*
import javax.swing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DiaryApp : JFrame() {
    private lateinit var textArea: JTextArea
    private lateinit var historyList: JList<String>
    private lateinit var historyModel: DefaultListModel<String>
    private lateinit var tabbedPane: JTabbedPane
    private lateinit var dayRatingComboBox: JComboBox<String>
    private lateinit var productivenessCheckBox: JCheckBox

    init {
        title = "Дневник"
        defaultCloseOperation = EXIT_ON_CLOSE
        setExtendedState(MAXIMIZED_BOTH)
        background = Color.WHITE
        layout = BorderLayout()

        setupComponents()
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setupComponents() {
     
        val screenWidth = Toolkit.getDefaultToolkit().screenSize.width
        val screenHeight = Toolkit.getDefaultToolkit().screenSize.height
        setBounds((screenWidth - screenWidth / 2) / 2, (screenHeight - screenHeight / 2) / 2,
            screenWidth / 2, screenHeight / 2)

    
        tabbedPane = JTabbedPane(JTabbedPane.TOP)
        tabbedPane.tabPlacement = JTabbedPane.LEFT

    
        val newEntryPanel = JPanel(BorderLayout())
        newEntryPanel.background = Color.WHITE

     
        val inputPanel = JPanel(GridLayout(3, 1))
        inputPanel.background = Color.WHITE

        textArea = JTextArea()
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        val scrollPane = JScrollPane(textArea)
        scrollPane.border = BorderFactory.createTitledBorder("Комментарий")
        inputPanel.add(scrollPane)

 
        dayRatingComboBox = JComboBox(arrayOf("Хорошо", "Плохо"))
        dayRatingComboBox.preferredSize = Dimension(150, 30)
        inputPanel.add(dayRatingComboBox)

  
        productivenessCheckBox = JCheckBox("Был продуктивным?")
        productivenessCheckBox.background = Color.WHITE
        inputPanel.add(productivenessCheckBox)

  
        val addButton = JButton("Добавить запись")
        addButton.background = Color.decode("#007BFF") // Яркий синий цвет
        addButton.foreground = Color.WHITE
        addButton.font = Font(Font.SANS_SERIF, Font.BOLD, 16)
        addButton.addActionListener {
            addNewRecord()
        }
        newEntryPanel.add(inputPanel, BorderLayout.NORTH)
        newEntryPanel.add(addButton, BorderLayout.SOUTH)

        val historyPanel = JPanel(BorderLayout())
        historyPanel.background = Color.WHITE

        historyModel = DefaultListModel()
        historyList = JList(historyModel)
        historyList.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        val historyScrollPane = JScrollPane(historyList)
        historyScrollPane.border = BorderFactory.createTitledBorder("История записей")
        historyPanel.add(historyScrollPane, BorderLayout.CENTER)


        tabbedPane.addTab("Новая запись", newEntryPanel)
        tabbedPane.addTab("История", historyPanel)

    
        add(tabbedPane, BorderLayout.CENTER)
    }

    private fun addNewRecord() {
        val comment = textArea.text.trim()
        if (comment.isBlank()) {
            JOptionPane.showMessageDialog(this, "Напишите комментарий, пожалуйста.")
            return
        }

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val rating = dayRatingComboBox.selectedItem as String
        val wasProductive = if (productivenessCheckBox.isSelected) "продуктивный" else "непродуктивный"

        val recordText = "$formattedDateTime | День: $rating | Продуктивность: $wasProductive | Коммент.: $comment"
        historyModel.addElement(recordText)

        textArea.text = ""
        JOptionPane.showMessageDialog(this, "Запись добавлена!")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater { DiaryApp() }
        }
    }
}
