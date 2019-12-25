package com.Geroid.ComProcesses;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Phaser;

public class ImageStore {

    public BufferedImage image;
    public Phaser phaser;

    public ImageStore(String url) {
        phaser = new Phaser();
        try {
            File imageFile = new File(url);
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveResult()
    {
        try{
            File imageFile = new File("GreyImage.jpg");
            ImageIO.write(image, "jpg", imageFile);
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
