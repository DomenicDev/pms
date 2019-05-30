package de.hfu.pms.utils;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static Image parseToImage(byte[] data) {
        if (data == null) {
            return null;
        }
        return new Image(new ByteArrayInputStream(data));
    }

    public static BufferedImage getImage(byte[] buffer) throws IOException {
        InputStream in = new ByteArrayInputStream(buffer);
        return ImageIO.read(in);
    }

    public static byte[] getImageData(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", out);
        return out.toByteArray();
    }
}
