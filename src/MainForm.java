import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainForm {
    public JPanel mainPanel;
    private JPanel sudokuPanel;
    private JButton solveButton;

    private JTextField[][] numbers;

    public MainForm(){
        sudokuPanel.setLayout(new GridLayout(9,9));
        numbers = new JTextField[9][9];
        Font font = new Font("Sans Serif", Font.PLAIN, 28);

        DocumentFilter filter = new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                    return;
                }
                int currentLength = fb.getDocument().getLength();
                if(currentLength + text.length()>1){
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        };

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                numbers[i][j] = new JTextField();
                numbers[i][j].setHorizontalAlignment(JTextField.CENTER);
                numbers[i][j].setFont(font);
                numbers[i][j].setColumns(1);
                ((AbstractDocument)numbers[i][j].getDocument()).setDocumentFilter(filter);
                sudokuPanel.add(numbers[i][j]);
                int top = 1, left = 1, bottom = 1, right = 1;
                if(j==0 || j == 3 || j == 6){
                    left = 3;
                }
                if(j==8 || j == 2 || j == 5){
                    right = 3;
                }
                if(i==0 || i == 3 || i == 6){
                    top = 3;
                }
                if(i==8 || i == 2 || i == 5){
                    bottom = 3;
                }
                numbers[i][j].setBorder(BorderFactory.createMatteBorder(top,left,bottom,right, Color.BLACK));
            }

        }
    }
}
