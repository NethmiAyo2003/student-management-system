// src/TimetablePanel.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TimeabalePanel extends JPanel {
    
    private int lecturerId;
    private JTable timetableTable;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private JLabel lblInfo;
    
    private timetableDAO timetableDAO;
    
    public TimeabalePanel(int lecturerId) {
        this.lecturerId = lecturerId;
        this.timetableDAO = new timetableDAO();
        
        initComponents();
        loadTimetable();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("📅 Weekly Timetable");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        lblInfo = new JLabel("Your weekly schedule");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setForeground(new Color(127, 140, 141));
        
        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(titleLabel);
        leftPanel.add(lblInfo);
        
        btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(120, 40));
        btnRefresh.addActionListener(e -> loadTimetable());
        
        titlePanel.add(leftPanel, BorderLayout.WEST);
        titlePanel.add(btnRefresh, BorderLayout.EAST);
        
        // Table
        String[] columns = {"Day", "Time", "Course Code", "Course Name", "Room"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        timetableTable = new JTable(tableModel);
        timetableTable.setRowHeight(40);
        timetableTable.setFont(new Font("Arial", Font.PLAIN, 13));
        timetableTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        timetableTable.getTableHeader().setBackground(new Color(52, 152, 219));
        timetableTable.getTableHeader().setForeground(Color.WHITE);
        timetableTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        // Set column widths
        timetableTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        timetableTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        timetableTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        timetableTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        timetableTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        // Center align columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        timetableTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        timetableTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        timetableTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        timetableTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        // Alternate row colors
        timetableTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(236, 240, 241));
                }
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(timetableTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        // Legend Panel
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        legendPanel.setBackground(new Color(236, 240, 241));
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel legendLabel = new JLabel("💡 Tip: Double-click a row to view course details");
        legendLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        legendLabel.setForeground(new Color(52, 73, 94));
        legendPanel.add(legendLabel);
        
        // Add components
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(legendPanel, BorderLayout.SOUTH);
        
        // Add double-click listener
        timetableTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showCourseDetails();
                }
            }
        });
    }
    
    private void loadTimetable() {
        tableModel.setRowCount(0);
        
        List<timetable> timetableList = timetableDAO.getTimetableByLecturer(lecturerId);
        
        if (timetableList.isEmpty()) {
            lblInfo.setText("No classes scheduled");
            return;
        }
        
        lblInfo.setText(timetableList.size() + " classes scheduled this week");
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String currentDay = "";
        
        for (timetable entry : timetableList) {
            String day = entry.getDayOfWeek();
            String startTime = timeFormat.format(entry.getStartTime());
            String endTime = timeFormat.format(entry.getEndTime());
            String timeSlot = startTime + " - " + endTime;
            
            // Show day only for first occurrence
            String displayDay = day.equals(currentDay) ? "" : day;
            currentDay = day;
            
            Object[] row = {
                displayDay,
                timeSlot,
                entry.getCourseCode(),
                entry.getCourseName(),
                entry.getRoomNumber()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showCourseDetails() {
        int selectedRow = timetableTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        
        String courseCode = (String) tableModel.getValueAt(selectedRow, 2);
        String courseName = (String) tableModel.getValueAt(selectedRow, 3);
        String time = (String) tableModel.getValueAt(selectedRow, 1);
        String room = (String) tableModel.getValueAt(selectedRow, 4);
        
        // Find the day (look upward for non-empty day cell)
        String day = "";
        for (int i = selectedRow; i >= 0; i--) {
            String dayValue = (String) tableModel.getValueAt(i, 0);
            if (!dayValue.isEmpty()) {
                day = dayValue;
                break;
            }
        }
        
        String message = String.format(
            "Course: %s - %s\n" +
            "Day: %s\n" +
            "Time: %s\n" +
            "Room: %s",
            courseCode, courseName, day, time, room
        );
        
        JOptionPane.showMessageDialog(
            this,
            message,
            "Course Details",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}