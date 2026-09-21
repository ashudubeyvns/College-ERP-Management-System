package attendancemgt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A modern table component with search, sorting, filtering, sticky headers,
 * alternate row colors, and pagination.
 */
public class ModernTable extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> filterCombo;
    private JLabel pageLabel;
    private JPanel tableContainer;
    private JScrollPane scrollPane;

    private int currentPage = 0;
    private int rowsPerPage = 15;
    private List<Object[]> allData = new ArrayList<>();
    private List<Object[]> filteredData = new ArrayList<>();
    private String[] columnNames;

    private static final Color HEADER_BG = UIStyles.TABLE_HEADER_BG;
    private static final Color HEADER_FG = UIStyles.TABLE_HEADER_TEXT;
    private static final Color ROW_ALT = UIStyles.TABLE_ROW_ALT;
    private static final Color ROW_NORMAL = UIStyles.TABLE_ROW;
    private static final Color BORDER_COLOR = UIStyles.TABLE_BORDER;

    public ModernTable(String[] columnNames) {
        this(columnNames, new Object[0][]);
    }

    public ModernTable(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        setLayout(new BorderLayout(0, 10));
        setOpaque(false);

        // Build table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Add data
        for (Object[] row : data) {
            allData.add(row);
        }

        // Build components
        buildSearchBar();
        buildTable();
        buildPagination();

        // Initial filter
        applyFilter("");
    }

    private void buildSearchBar() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(0, 0, 5, 0));

        // Search field
        JPanel searchInputPanel = new JPanel(new BorderLayout());
        searchInputPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.setFont(UIStyles.FONT_PLAIN_LG);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "\uD83D\uDD0D Search...");

        // Quick filter combo
        filterCombo = new JComboBox<>(new String[]{"All", "Present", "Absent", "Late"});
        filterCombo.setFont(UIStyles.FONT_PLAIN_MD);
        filterCombo.setBackground(Color.WHITE);
        filterCombo.addActionListener(e -> applyFilter(searchField.getText()));

        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        searchInputPanel.add(filterCombo, BorderLayout.EAST);

        searchPanel.add(searchInputPanel, BorderLayout.CENTER);

        // Search listener
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilter(searchField.getText());
            }
        });

        add(searchPanel, BorderLayout.NORTH);
    }

    @SuppressWarnings("unchecked")
    private void buildTable() {
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    comp.setBackground(row % 2 == 0 ? ROW_NORMAL : ROW_ALT);
                } else {
                    comp.setBackground(UIStyles.withAlpha(UIStyles.PRIMARY, 40));
                }
                return comp;
            }
        };

        // Table styling
        table.setFont(UIStyles.FONT_PLAIN_BASE);
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setGridColor(BORDER_COLOR);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(UIStyles.withAlpha(UIStyles.PRIMARY, 60));
        table.setSelectionForeground(Color.WHITE);
        table.setAutoCreateRowSorter(true);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(UIStyles.FONT_BOLD_MD);
        header.setBackground(HEADER_BG);
        header.setForeground(HEADER_FG);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(header.getWidth(), 44));
        header.setReorderingAllowed(false);

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Header renderer
        TableCellRenderer headerRenderer = header.getDefaultRenderer();
        if (headerRenderer instanceof JLabel) {
            ((JLabel) headerRenderer).setHorizontalAlignment(SwingConstants.CENTER);
            ((JLabel) headerRenderer).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 255, 255, 30)),
                new EmptyBorder(0, 5, 0, 5)
            ));
        }

        // Container with rounded corners
        tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }

    private void buildPagination() {
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        paginationPanel.setOpaque(false);

        JButton prevBtn = new JButton("\u25C0 Previous");
        prevBtn.setFont(UIStyles.FONT_PLAIN_SM);
        prevBtn.setFocusPainted(false);
        prevBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevBtn.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                updateTableData();
            }
        });

        pageLabel = new JLabel("Page 1 of 1");
        pageLabel.setFont(UIStyles.FONT_BOLD_BASE);
        pageLabel.setForeground(UIStyles.TEXT_MUTED);

        JButton nextBtn = new JButton("Next \u25B6");
        nextBtn.setFont(UIStyles.FONT_PLAIN_SM);
        nextBtn.setFocusPainted(false);
        nextBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextBtn.addActionListener(e -> {
            int maxPage = (int) Math.ceil((double) filteredData.size() / rowsPerPage) - 1;
            if (currentPage < maxPage) {
                currentPage++;
                updateTableData();
            }
        });

        // Rows per page selector
        JLabel rowsLabel = new JLabel("Rows/page:");
        rowsLabel.setFont(UIStyles.FONT_PLAIN_SM);
        rowsLabel.setForeground(UIStyles.TEXT_MUTED);

        JComboBox<Integer> rowsCombo = new JComboBox<>(new Integer[]{10, 15, 20, 25, 50, 100});
        rowsCombo.setFont(UIStyles.FONT_PLAIN_SM);
        rowsCombo.setSelectedItem(rowsPerPage);
        rowsCombo.addActionListener(e -> {
            rowsPerPage = (Integer) rowsCombo.getSelectedItem();
            currentPage = 0;
            updateTableData();
        });

        paginationPanel.add(prevBtn);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextBtn);
        paginationPanel.add(Box.createHorizontalStrut(20));
        paginationPanel.add(rowsLabel);
        paginationPanel.add(rowsCombo);

        add(paginationPanel, BorderLayout.SOUTH);
    }

    /**
     * Apply search filter across all columns.
     */
    private void applyFilter(String query) {
        String filterStatus = (String) filterCombo.getSelectedItem();
        filteredData.clear();

        for (Object[] row : allData) {
            boolean matchesSearch = true;
            if (!query.isEmpty()) {
                matchesSearch = false;
                for (Object cell : row) {
                    if (cell != null && cell.toString().toLowerCase().contains(query.toLowerCase())) {
                        matchesSearch = true;
                        break;
                    }
                }
            }

            boolean matchesFilter = true;
            if (!"All".equals(filterStatus)) {
                matchesFilter = false;
                for (Object cell : row) {
                    if (cell != null && cell.toString().toLowerCase().contains(filterStatus.toLowerCase())) {
                        matchesFilter = true;
                        break;
                    }
                }
            }

            if (matchesSearch && matchesFilter) {
                filteredData.add(row);
            }
        }

        currentPage = 0;
        updateTableData();
    }

    private void updateTableData() {
        tableModel.setRowCount(0);

        int start = currentPage * rowsPerPage;
        int end = Math.min(start + rowsPerPage, filteredData.size());

        for (int i = start; i < end; i++) {
            tableModel.addRow(filteredData.get(i));
        }

        int maxPage = (int) Math.ceil((double) filteredData.size() / rowsPerPage);
        pageLabel.setText("Page " + (currentPage + 1) + " of " + Math.max(maxPage, 1));
    }

    /**
     * Set new data for the table.
     */
    public void setData(Object[][] data) {
        allData.clear();
        for (Object[] row : data) {
            allData.add(row);
        }
        applyFilter(searchField.getText());
    }

    /**
     * Add a row to the table.
     */
    public void addRow(Object[] row) {
        allData.add(row);
        applyFilter(searchField.getText());
    }

    /**
     * Remove a row at the specified index.
     */
    public void removeRow(int index) {
        if (index >= 0 && index < allData.size()) {
            allData.remove(index);
            applyFilter(searchField.getText());
        }
    }

    /**
     * Get the selected row's data.
     */
    public Object[] getSelectedRow() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) return null;
        int modelRow = table.convertRowIndexToModel(viewRow);
        return filteredData.get(modelRow);
    }

    /**
     * Get the underlying JTable.
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Get total filtered row count.
     */
    public int getFilteredRowCount() {
        return filteredData.size();
    }

    /**
     * Get total row count (unfiltered).
     */
    public int getTotalRowCount() {
        return allData.size();
    }

    /**
     * Clear all data.
     */
    public void clearData() {
        allData.clear();
        filteredData.clear();
        tableModel.setRowCount(0);
    }
}
