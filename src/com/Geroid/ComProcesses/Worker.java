package com.Geroid.ComProcesses;

import java.awt.*;

public class Worker extends  Thread {
    private ImageStore store;
    private int startLine;
    private int endLine;

    public Worker(ImageStore store, int startLine, int endLine)
    {
        this.store = store;
        this.startLine = startLine;
        this.endLine = endLine;
        store.phaser.register();
    }

    @Override
    public void run(){
        //System.out.println(Thread.currentThread().getName() + " is ready.");
        store.phaser.arriveAndAwaitAdvance();
        //System.out.println(Thread.currentThread().getName() + " is started.");
        for(int y = startLine; y < endLine; y++){
            for(int x = 0; x < store.image.getWidth(); x++){
                Color c = new Color(store.image.getRGB(x, y));
                int grayScale = (int) ((c.getRed() * 0.299) + (c.getGreen() * 0.587) + (c.getBlue() * 0.114));
                Color newColor = new Color(grayScale, grayScale, grayScale);
                store.image.setRGB(x, y, newColor.getRGB());
            }
        }
        store.phaser.arriveAndAwaitAdvance();
    }
}
