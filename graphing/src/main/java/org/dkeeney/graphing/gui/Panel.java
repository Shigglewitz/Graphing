package org.dkeeney.graphing.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.RandomStringUtils;
import org.dkeeney.graphing.ColorGrapher;
import org.dkeeney.graphing.ImageMaker;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;

public class Panel implements ActionListener {
    private static final long serialVersionUID = 5143557122459035395L;

    private JPanel equationPane;
    private JPanel drawPane;
    private JPanel savePane;
    private JButton draw;
    private JButton save;
    private final JTextField redEquation = new JTextField();
    private final JTextField greenEquation = new JTextField();
    private final JTextField blueEquation = new JTextField();
    private JLabel imageDisplay;
    private final JLabel redEquationLabel = new JLabel("asdf");
    private final JLabel greenEquationLabel = new JLabel();
    private final JLabel blueEquationLabel = new JLabel();
    private BufferedImage image;

    private ColorGrapher cg;

    // dimensions
    private static final int GLOBAL_PADDING = 5;
    private static final int INTERNAL_PADDING = 10;

    private static final int EQUATION_TEXT_WIDTH = 100;
    private static final int EQUATION_TEXT_HEIGHT = 25;
    private static final int BUTTON_HEIGHT = EQUATION_TEXT_HEIGHT;
    private static final int BUTTON_WIDTH = 80;
    private static final int EQUATION_LABEL_WIDTH = 50;
    private static final int EQUATION_LABEL_HEIGHT = EQUATION_TEXT_HEIGHT;

    public JPanel createContentPane() {

        JTextField[] equationFields = { this.redEquation, this.greenEquation,
                this.blueEquation };

        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);

        // Creation of a Panel to contain the equation inputs
        this.equationPane = new JPanel();
        this.equationPane.setLayout(null);
        this.equationPane.setLocation(GLOBAL_PADDING, GLOBAL_PADDING);
        this.equationPane.setSize(EQUATION_LABEL_WIDTH + GLOBAL_PADDING
                + EQUATION_TEXT_WIDTH,
                (INTERNAL_PADDING + EQUATION_TEXT_HEIGHT)
                        * equationFields.length + BUTTON_HEIGHT);
        totalGUI.add(this.equationPane);

        this.redEquationLabel.setText("R:");
        this.redEquationLabel.setLocation(0, 0);
        this.redEquationLabel.setSize(EQUATION_LABEL_WIDTH,
                EQUATION_LABEL_HEIGHT);
        this.redEquationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.redEquationLabel.setBackground(Color.RED);
        this.redEquationLabel.setVisible(true);
        this.equationPane.add(this.redEquationLabel);

        this.greenEquationLabel.setText("G:");
        this.greenEquationLabel.setLocation(0, EQUATION_TEXT_HEIGHT
                + INTERNAL_PADDING);
        this.greenEquationLabel.setSize(EQUATION_LABEL_WIDTH,
                EQUATION_LABEL_HEIGHT);
        this.greenEquationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.greenEquationLabel.add(this.redEquationLabel);

        this.blueEquationLabel.setText("B:");
        this.blueEquationLabel.setLocation(0,
                2 * (EQUATION_TEXT_HEIGHT + INTERNAL_PADDING));
        this.blueEquationLabel.setSize(EQUATION_LABEL_WIDTH,
                EQUATION_LABEL_HEIGHT);
        this.blueEquationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.blueEquationLabel.add(this.redEquationLabel);

        for (int i = 0; i < equationFields.length; i++) {
            JTextField temp = equationFields[i];
            temp.setLocation(EQUATION_LABEL_WIDTH + GLOBAL_PADDING, i
                    * (EQUATION_TEXT_HEIGHT + INTERNAL_PADDING));
            temp.setSize(EQUATION_TEXT_WIDTH, EQUATION_TEXT_HEIGHT);
            temp.setHorizontalAlignment(SwingConstants.LEFT);
            this.equationPane.add(temp);
        }

        this.draw = new JButton("Draw");
        this.draw.setMnemonic((int) 'D');
        this.draw.setLocation(
                (this.equationPane.getWidth() - BUTTON_WIDTH) / 2,
                equationFields.length
                        * (EQUATION_TEXT_HEIGHT + INTERNAL_PADDING));
        this.draw.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        this.draw.addActionListener(this);
        this.equationPane.add(this.draw);

        // Creation of a Panel to contain the graph.
        this.drawPane = new JPanel();
        this.drawPane.setLayout(null);
        this.drawPane.setLocation(this.equationPane.getWidth()
                + this.equationPane.getX() + GLOBAL_PADDING, GLOBAL_PADDING);
        this.drawPane.setSize(ColorGrapher.DEFAULT_WIDTH,
                ColorGrapher.DEFAULT_HEIGHT);
        totalGUI.add(this.drawPane);

        this.imageDisplay = new JLabel();
        this.imageDisplay.setLocation(0, 0);
        this.imageDisplay.setSize(ColorGrapher.DEFAULT_WIDTH,
                ColorGrapher.DEFAULT_HEIGHT);
        this.displayImage(this.generateRandomImage());
        this.drawPane.add(this.imageDisplay);

        // Creation of a Panel to contain save options
        this.savePane = new JPanel();
        this.savePane.setLayout(null);
        this.savePane.setLocation(
                this.drawPane.getWidth() + this.drawPane.getX()
                        + GLOBAL_PADDING, GLOBAL_PADDING);
        this.savePane.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        totalGUI.add(this.savePane);

        this.save = new JButton("Save");
        this.save.setMnemonic((int) 'S');
        this.save.setLocation((this.savePane.getWidth() - BUTTON_WIDTH) / 2, 0);
        this.save.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        this.save.addActionListener(this);
        this.savePane.add(this.save);

        totalGUI.setOpaque(true);
        return totalGUI;
    }

    private BufferedImage generateRandomImage() {
        return ImageMaker.randomImage(ColorGrapher.DEFAULT_WIDTH,
                ColorGrapher.DEFAULT_HEIGHT);
    }

    private void displayImage(BufferedImage image) {
        this.image = image;
        this.imageDisplay.setIcon(new ImageIcon(image));
    }

    private void saveImage() throws IOException {
        ImageMaker
                .saveImage(this.image, RandomStringUtils.randomAlphabetic(15));
    }

    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("[=] Color Graphing [=]");

        // Create and set up the content pane.
        Panel demo = new Panel();
        frame.setContentPane(demo.createContentPane());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == this.draw) {
            try {
                this.cg = new ColorGrapher(this.redEquation.getText(),
                        this.greenEquation.getText(),
                        this.blueEquation.getText());
                this.displayImage(this.cg.getGraph());
            } catch (InvalidEquationException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Equation error!", JOptionPane.PLAIN_MESSAGE);
                this.displayImage(this.generateRandomImage());
            }
        } else if (source == this.save) {
            try {
                this.saveImage();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Saving Error", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
