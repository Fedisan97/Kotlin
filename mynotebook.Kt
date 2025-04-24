import java.awt.*
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.Collections
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants
import javax.swing.UIManager
import javax.swing.plaf.nimbus.NimbusLookAndFeel

class DiaryApp : JFrame() {
    private lateinit var textArea: JTextArea
    private lateinit var historyList: JList<String>
    private lateinit var historyModel: DefaultListModel<String>
    private lateinit var tabbedPane: JTabbedPane
    private lateinit var dayRatingComboBox: JComboBox<String>
    private lateinit var productivenessCheckBox: JCheckBox

    init {
        title = "Современный дневник"
        defaultCloseOperation = EXIT_ON_CLOSE
        setExtendedState(MAXIMIZED_BOTH)
        background = Color.decode("#FAFAFA")
        layout = BorderLayout()

        setupComponents()
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun setupComponents() {
     
        val mainPanel = JPanel(BorderLayout())
        mainPanel.background = Color.decode("#FAFAFA")

   
        val newEntryPanel = JPanel(BorderLayout())
        newEntryPanel.background = Color.decode("#FAFAFA")

       
        textArea = JTextArea()
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        textArea.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        textArea.foreground = Color.decode("#333")
        textArea.background = Color.decode("#FFFFFF")
        val scrollPane = JScrollPane(textArea)
        scrollPane.border = BorderFactory.createTitledBorder("Новые мысли")
        newEntryPanel.add(scrollPane, BorderLayout.CENTER)

     
        val inputPanel = JPanel(GridLayout(3, 1))
        inputPanel.background = Color.decode("#FAFAFA")

     
        dayRatingComboBox = JComboBox(arrayOf("😊 Отлично", "🤷‍♂️ Так себе", "🥶 Устал"))
        dayRatingComboBox.preferredSize = Dimension(150, 30)
        inputPanel.add(dayRatingComboBox)

     
        productivenessCheckBox = JCheckBox("🏃‍♂️ Был продуктивным?")
        productivenessCheckBox.background = Color.decode("#FAFAFA")
        inputPanel.add(productivenessCheckBox)

      
        val addButton = JButton("🎯 Добавить запись")
        addButton.background = Color.decode("#4CAF50")
        addButton.foreground = Color.decode("#FFF")
        addButton.font = Font(Font.SANS_SERIF, Font.BOLD, 16)
        addButton.addActionListener {
            addNewRecord()
        }
        inputPanel.add(addButton)

        newEntryPanel.add(inputPanel, BorderLayout.SOUTH)

   
        val historyPanel = JPanel(BorderLayout())
        historyPanel.background = Color.decode("#FAFAFA")

        historyModel = DefaultListModel()
        historyList = JList(historyModel)
        historyList.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        val historyScrollPane = JScrollPane(historyList)
        historyScrollPane.border = BorderFactory.createTitledBorder("📝 История записей")
        historyPanel.add(historyScrollPane, BorderLayout.CENTER)

 
        val bottomPanel = JPanel(BorderLayout())
        bottomPanel.background = Color.decode("#FAFAFA")

    
        val calendarPanel = JPanel(BorderLayout())
        calendarPanel.background = Color.decode("#FAFAFA")
        val today = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val calendarTitle = JLabel(today.format(dateFormat))
        calendarTitle.foreground = Color.decode("#333")
        calendarTitle.horizontalAlignment = SwingConstants.CENTER
        calendarPanel.add(calendarTitle, BorderLayout.NORTH)

      
        val notesPanel = JPanel(BorderLayout())
        notesPanel.background = Color.decode("#FAFAFA")
        val notesTextArea = JTextArea()
        notesTextArea.lineWrap = true
        notesTextArea.wrapStyleWord = true
        notesTextArea.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        notesTextArea.foreground = Color.decode("#333")
        notesTextArea.background = Color.decode("#FFFFFF")
        val notesScrollPane = JScrollPane(notesTextArea)
        notesScrollPane.border = BorderFactory.createTitledBorder("📋 Заметки")
        notesPanel.add(notesScrollPane, BorderLayout.CENTER)

        // Общая сборка
        mainPanel.add(newEntryPanel, BorderLayout.NORTH)
        mainPanel.add(historyPanel, BorderLayout.CENTER)
        mainPanel.add(bottomPanel, BorderLayout.SOUTH)
        bottomPanel.add(calendarPanel, BorderLayout.WEST)
        bottomPanel.add(notesPanel, BorderLayout.EAST)

        add(mainPanel, BorderLayout.CENTER)
    }

    private fun addNewRecord() {
        val comment = textArea.text.trim()
        if (comment.isBlank()) {
            JOptionPane.showMessageDialog(this, "Введите вашу мысль.")
            return
        }

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val rating = dayRatingComboBox.selectedItem as String
        val wasProductive = if (productivenessCheckBox.isSelected) "✨ да" else "☹️ нет"

        val recordText = "$formattedDateTime | 🗓️ День: $rating | 👉 Продуктивность: $wasProductive | 💬 Мысли: $comment"
        historyModel.addElement(recordText)

        textArea.text = ""
        JOptionPane.showMessageDialog(this, "Запись успешно добавлена!")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater { DiaryApp() }
        }
    }
}
