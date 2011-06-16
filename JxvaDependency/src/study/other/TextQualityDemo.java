package study.other;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class TextQualityDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame("LCD Text Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(630, 460));
    frame.setContentPane(new MyPanel1(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF));
    frame.pack();
    frame.setVisible(true);
  }
}

class MyPanel1 extends JPanel {
  private Object hintValue;

  public MyPanel1(Object hintValue) {
    this.hintValue = hintValue;
    this.setPreferredSize(new Dimension(300, 100));
    String title = hintValue.toString();
    Border border = new TitledBorder(title);
    this.setBorder(border);
  }

  public void paintComponent(Graphics g) {
    Dimension d = this.getSize();
    BufferedImage backBuffer = (BufferedImage) this.createImage(d.width, d.height);
    Graphics2D g2 = backBuffer.createGraphics();

    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, d.width, d.height);

    g2.setColor(Color.BLACK);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, hintValue);

    g2.drawRect(0, 0, d.width - 1, d.height - 1);

    g2.drawString("abcdefghijklmnopqrstuvwxyz", 20, 40);
    g2.drawString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 20, 60);
    g2.drawString("1234567890-=!@#$%^&*()_+,./<>?", 20, 80);

    g.drawImage(backBuffer, 0, 0, this);
  }
}
