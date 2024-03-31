import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SideActionPerformed implements ActionListener {

    private final ArrayList<JButton[]> sideButtons;

    public  SideActionPerformed(ArrayList<JButton[]> sideButtons){
        this.sideButtons = sideButtons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int row = 0; row < sideButtons.size(); row++){

            for(int col = 0; col < 2; col++){

                if(e.getSource() == sideButtons.get(row)[col]){
                    ChessBoard.setInstance(row, col);
                    ChessUI.getUI().updateInstance();
                    return;
                }
            }
        }
    }
}
