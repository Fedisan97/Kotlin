import java.awt.*
import javax.swing.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.swing.BorderFactory
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants
import javax.swing.UIManager
import javax.swing.plaf.nimbus.NimbusLookAndFeel

class DiaryApp : JFrame() {
    private lateinit var textArea: JTextArea
    private lateinit var historyList: JList<String>
    private lateinit var historyModel: DefaultListModel<String>
    private lateinit var dayRatingComboBox: JComboBox<String>
    private lateinit var productivenessCheckBox: JCheckBox
    private lateinit var dayTypeComboBox: JComboBox<String>
    private lateinit var calendar: JCalendarPanel

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
        scrollPane.border = BorderFactory.createTitledBorder("Ваши мысли")
        newEntryPanel.add(scrollPane, BorderLayout.CENTER)

        val inputPanel = JPanel(GridLayout(4, 1))
        inputPanel.background = Color.decode("#FAFAFA")

        dayRatingComboBox = JComboBox(arrayOf("😊 Отлично", "🤷‍♂️ Так себе", "🥶 Устал"))
        dayRatingComboBox.preferredSize = Dimension(150, 30)
        dayRatingComboBox.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        inputPanel.add(dayRatingComboBox)

        productivenessCheckBox = JCheckBox("🏃‍♂️ Был продуктивным?")
        productivenessCheckBox.background = Color.decode("#FAFAFA")
        productivenessCheckBox.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        inputPanel.add(productivenessCheckBox)

        dayTypeComboBox = JComboBox(arrayOf("🌞 Хороший", "🌧 Плохой", "📚 Образовательный", "🏃‍♂️ Спортивный"))
        dayTypeComboBox.preferredSize = Dimension(150, 30)
        dayTypeComboBox.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        inputPanel.add(dayTypeComboBox)

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

        calendar = JCalendarPanel(Calendar.getInstance())
        calendar.background = Color.decode("#FAFAFA")

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

        mainPanel.add(newEntryPanel, BorderLayout.NORTH)
        mainPanel.add(historyPanel, BorderLayout.CENTER)
        mainPanel.add(bottomPanel, BorderLayout.SOUTH)
        bottomPanel.add(calendar, BorderLayout.WEST)
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
        val dayType = dayTypeComboBox.selectedItem as String

        val recordText = "$formattedDateTime | 🗓️ День: $rating | 👉 Продуктивность: $wasProductive | 📅 Тип дня: $dayType | 💬 Мысли: $comment"
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

class JCalendarPanel(private val cal: Calendar) : JPanel() {
    init {
        layout = GridLayout(6, 7)
        background = Color.decode("#FAFAFA")
        generateDays()
    }

    private fun generateDays() {
        removeAll()
        val startWeekDay = cal.get(Calendar.DAY_OF_WEEK)
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfMonth = cal.clone() as Calendar
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        val offset = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1

        repeat(offset) {
            add(JLabel("", SwingConstants.CENTER))
        }

    }}
