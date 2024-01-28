import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainForm {
    public JPanel mainPanel;
    private JPanel sudokuPanel;
    private JButton solveButton;
    private JButton resetBoardButton;

    private JTextField[][] numbers;

    private boolean solved = false;

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
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solved = false;
                if(!isSolvable()){
                    JOptionPane.showMessageDialog(Frame.getFrames()[0], "This sudoku has no solution!");
                    return;

                }
                findSolution(0,0);
            }
        });
        resetBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
    }

    public int retrieveValue(int x, int y){
        String text = numbers[x][y].getText();
        int value = 0;
        if(!text.isEmpty() && !text.isBlank()){
            value = Integer.parseInt(text);
        }
        return value;
    }

    public boolean verifySingleNumber(int x, int y, int value){

        // verify the numbers inside the same column
        for(int i = 0; i < 9; i++){
            if(i == x){
                continue;
            }
            int currentValue = retrieveValue(i,y);
            if(currentValue != 0 && (currentValue == value)){
                return false;
            }
        }

        // verify the numbers inside the same row
        for(int j = 0; j < 9; j++){
            if(j == y){
                continue;
            }
            int currentValue = retrieveValue(x,j);
            if(currentValue != 0 && (currentValue == value)){
                return false;
            }
        }

        // verify the numbers inside the square
        int minX = (x/3) * 3;
        int minY = (y/3) * 3;
        for (int i = minX; i < minX +3; i++) {
            for (int j = minY; j < minY + 3; j++) {
                if (i == x && j == y) {
                    continue;
                }
                int currentValue = retrieveValue(i, j);
                if (currentValue != 0 && (currentValue == value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void findSolution(int x, int y){
        if(x == 9){
            solved = true;
            return;
        }
        int nextX = x +((y+1) / 9);
        int nextY = (y+1) % 9;
        if(retrieveValue(x,y)!=0){
            findSolution(nextX,nextY);
        }else {
            for (int value = 1; value < 10; value++) {
                if (verifySingleNumber(x, y, value)) {
                    numbers[x][y].setText(value + "");
                    findSolution(nextX, nextY);
                    if (solved) {
                        return;
                    }
                    numbers[x][y].setText("");
                }
            }
        }
    }

    public void resetBoard(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                numbers[i][j].setText("");
            }
        }
    }

    public boolean isSolvable(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int value = retrieveValue(i,j);
                if(value!=0){
                    boolean result = verifySingleNumber(i,j,value);
                    if(!result){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
