package program;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;



public class Qr {

    public  static Image toImage(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        WritableImage image = new WritableImage(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = matrix.get(x,y) ? Color.BLACK : Color.WHITE;
                image.getPixelWriter().setColor(x, y, color);
            }
        }
        return image;
    }

    public static BitMatrix qrCodeMatrix(String uri) {
        Writer qrWriter = new QRCodeWriter();
        BitMatrix matrix;
        try {
            matrix = qrWriter.encode(uri, BarcodeFormat.QR_CODE, 320, 240);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }

    public static Image qrCodeImage(String uri) {
        return toImage(qrCodeMatrix(uri));
    }
}
