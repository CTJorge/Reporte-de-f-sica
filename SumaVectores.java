import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SumaVectores extends JPanel {
    private JTextField vector1LengthField, vector2LengthField;
    private JTextField vector1AngleField, vector2AngleField;
    private JButton drawButton;
    private int[] vector1, vector2, sumaVector;

    public SumaVectores () {
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new GridLayout(3, 2));
        vector1LengthField = new JTextField();
        vector2LengthField = new JTextField();
        vector1AngleField = new JTextField();
        vector2AngleField = new JTextField();
        drawButton = new JButton("Dibujar");
        drawButton.addActionListener(e -> dibujar());

        controlPanel.add(new JLabel("Magnitud Vector 1:"));
        controlPanel.add(vector1LengthField);
        controlPanel.add(new JLabel("Magnitud Vector 2:"));
        controlPanel.add(vector2LengthField);
        controlPanel.add(new JLabel("Dirección Vector 1 (grados):"));
        controlPanel.add(new JPanel().add(vector1AngleField));
        controlPanel.add(new JLabel("Dirección Vector 2 (grados):"));
        controlPanel.add(new JPanel().add(vector2AngleField));

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.add(drawButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void dibujar() {
        try {
            double magnitude1 = Double.parseDouble(vector1LengthField.getText());
            double magnitude2 = Double.parseDouble(vector2LengthField.getText());
            double angle1 = Math.toRadians(Double.parseDouble(vector1AngleField.getText()));
            double angle2 = Math.toRadians(Double.parseDouble(vector2AngleField.getText()));

            double x1 = magnitude1 * Math.cos(angle1);
            double y1 = magnitude1 * Math.sin(angle1);
            double x2 = magnitude2 * Math.cos(angle2);
            double y2 = magnitude2 * Math.sin(angle2);

            vector1 = new int[]{(int) x1, (int) y1};
            vector2 = new int[]{(int) x2, (int) y2};
            sumaVector = new int[]{(int) (x1 + x2), (int) (y1 + y2)};

            repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vector1 != null && vector2 != null && sumaVector != null) {
            Graphics2D g2d = (Graphics2D) g;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            g2d.drawLine(0, centerY, getWidth(), centerY); // Eje X
            g2d.drawLine(centerX, 0, centerX, getHeight()); // Eje Y

            dibujarVector(g2d, Color.RED, vector1, centerX, centerY, "Vector 1: Magnitud=" + vectorMagnitude(vector1) + ", Dirección=" + vectorDirection(vector1));
            dibujarVector(g2d, Color.BLUE, vector2, centerX, centerY, "Vector 2: Magnitud=" + vectorMagnitude(vector2) + ", Dirección=" + vectorDirection(vector2));
            dibujarVector(g2d, Color.GREEN, sumaVector, centerX, centerY, "Vector Suma: Magnitud=" + vectorMagnitude(sumaVector) + ", Dirección=" + vectorDirection(sumaVector));
        }
    }

    private void dibujarVector(Graphics2D g2d, Color color, int[] vector, int centerX, int centerY, String label) {
        g2d.setColor(color);
        g2d.drawLine(centerX, centerY, centerX + vector[0], centerY - vector[1]);
        g2d.drawString(label, centerX + vector[0], centerY - vector[1]);
    }

    private double vectorMagnitude(int[] vector) {
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
    }

    private double vectorDirection(int[] vector) {
        return Math.toDegrees(Math.atan2(vector[1], vector[0]));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MetodoTriangulo::new);
    }
}