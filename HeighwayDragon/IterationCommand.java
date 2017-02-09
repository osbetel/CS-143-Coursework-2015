package HeighwayDragon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ADKN.
 * @version 07 Feb 2016.
 */


/**
 * Class to receive input via the two JButtons, as well as contain a JLabel to view the iteration count.
 * It could have been made more efficient seeing as it also holds it's own "private int iterations," but
 * there's almost no danger of this iteration count updating and the model iteration count not updating.
 * Seeing as the command to update must go through this class before hitting the model.
 */
public class IterationCommand implements ActionListener {
    private DragonMain data;
    protected JPanel buttonPane;
    protected JLabel label;
    private int iterations;

    /**
     * Constructs the IterationCommand object.
     * @param dc Must be passed in the Model aspect, in this case DragonMain
     */
    public IterationCommand(DragonMain dc) {
        data = dc;
        iterations = 0;

        //Creates the JPanel
        buttonPane = new JPanel();
        buttonPane.setBackground(Color.LIGHT_GRAY);
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
//        buttonPane.setPreferredSize(new Dimension(300, DragonViewing.FRAME_SIZE));
        buttonPane.setVisible(true);
        buttonPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        //Decrement button
        JButton decrement = new JButton("<< Decrement");
//        decrement.setPreferredSize(new Dimension(200,25));
        decrement.addActionListener(this);
        decrement.setActionCommand("--");           //Important. Required for the switch functionality in actionPerformed().
        buttonPane.add(decrement);

        //Increment button
        JButton increment = new JButton("Increment >>");
//        increment.setPreferredSize(new Dimension(200,25));
        increment.addActionListener(this);
        increment.setActionCommand("++");           //Important. Required for the switch functionality in actionPerformed().
        buttonPane.add(increment);

        //Label for the number of iterations.
        label = new JLabel("Current Iteration: " + iterations);
        buttonPane.add(label);
    }

    /**
     * Responds to input from the user via the two increment and decrement buttons.
     * @param event The event cause by the user clicking one of the buttons.
     */
    public void actionPerformed(ActionEvent event) {
        //Will increase or decrease number of iterations and redraw the dragons.
        switch (event.getActionCommand()) {
            case "--":              //If decremented

                if (iterations > 0) {
                    iterations--;
                    buttonPane.remove(label);
                    label = new JLabel("Current Iteration: " + iterations);
                    buttonPane.add(label);
                    /*
                    I couldn't figure out any other way to update the label with the iterations. repaint(), validate(),
                    and so on didn't seem to have any effect.
                     */
                }
                data.decrement();

                this.buttonPane.repaint();
                this.buttonPane.validate();
                break;

            case "++":              //If incremented
                iterations++;
                data.increment();

                buttonPane.remove(label);
                label = new JLabel("Current Iteration: " + iterations);
                buttonPane.add(label);

                this.buttonPane.repaint();
                this.buttonPane.validate();
                break;
        }
    }
}
