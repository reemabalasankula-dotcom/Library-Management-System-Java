import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryManagementSystem extends JFrame {

    JTextArea output;
    String[] books = {"Book A", "Book B", "Book C", "Book D"};
    Date borrowDate = null;

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        output = new JTextArea();
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");

        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());

        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void borrowBook() {
        String book = (String) JOptionPane.showInputDialog(
                this,
                "Select a book to borrow:",
                "Borrow Book",
                JOptionPane.PLAIN_MESSAGE,
                null,
                books,
                books[0]
        );

        if (book != null) {
            borrowDate = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            String borrowDateStr = sdf.format(borrowDate);

            Date returnDateObj = new Date(borrowDate.getTime() + 7L * 24 * 60 * 60 * 1000);
            String returnDateStr = sdf.format(returnDateObj);

            output.setText(
                    "Book Borrowed: " + book +
                    "\nBorrow Date: " + borrowDateStr +
                    "\nReturn By: " + returnDateStr +
                    "\n\nNote: Rs.2 fine per day if returned after 7 days."
            );
        }
    }

    private void returnBook() {
        if (borrowDate == null) {
            output.setText("No book has been borrowed yet.");
            return;
        }

        try {
            String bookName = JOptionPane.showInputDialog(this, "Enter Book Name:");
            String returnDateStr = JOptionPane.showInputDialog(this, "Enter Return Date (dd/MM/yy):");

            if (returnDateStr == null || returnDateStr.trim().isEmpty()) {
                output.setText("Return date is required.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            Date returnDate = sdf.parse(returnDateStr);

            long diffInMillies = returnDate.getTime() - borrowDate.getTime();
            long totalDays = diffInMillies / (1000 * 60 * 60 * 24);
            long lateDays = totalDays - 7;

            int fine = 0;

            if (lateDays > 0) {
                fine = (int) lateDays * 2;
            }

            output.setText(
                    "Book Returned: " + bookName +
                    "\nBorrow Date: " + sdf.format(borrowDate) +
                    "\nReturned Date: " + returnDateStr +
                    "\nTotal Days: " + totalDays +
                    "\nLate by: " + (lateDays > 0 ? lateDays : 0) + " days" +
                    "\nFine: Rs." + fine
            );

            borrowDate = null;

        } catch (Exception e) {
            output.setText("Error: Invalid date format. Use dd/MM/yy.");
        }
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
}