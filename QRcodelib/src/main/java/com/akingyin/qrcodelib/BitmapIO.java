package com.akingyin.qrcodelib;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.akingyin.qrcodelib.image.ImageType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitmapIO {

    public static boolean write(Bitmap image, String type, OutputStream stream) throws IOException {
        if (type.equals(ImageType.PNG)) {
            return image.compress(CompressFormat.PNG, 80, stream);
        } else if (type.equals(ImageType.JPG)) {
            return image.compress(CompressFormat.JPEG, 80, stream);
        }

        // Default should be Bitmap for android
        return BmpUtil.save(image, stream);

    }

    public static boolean write(Bitmap image, String type, File file) throws IOException {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            return write(image, type, stream);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
}
