package com.lwohvye.springboot.otherpart.local.video2asc.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//视频图像显示窗口类
public class ImageGUI extends JComponent {
    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    public ImageGUI() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g;
        if (image == null) {
            g2d.setPaint(Color.BLACK);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        } else {
            g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
//            System.out.println("show frame...");
        }
    }

    public void createWin(String title) {
        var ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(new Dimension(330, 240));
        ui.setVisible(true);
    }

    public void createWin(String title, Dimension size) {
        var ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(new Dimension(size));
        ui.setVisible(true);
    }

    public void imshow(BufferedImage image) {
        this.image = image;
        this.repaint();
    }
}
