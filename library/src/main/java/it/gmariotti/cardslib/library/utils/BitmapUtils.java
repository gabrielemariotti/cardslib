/*
 * ******************************************************************************
 *   Copyright (c) 2013 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package it.gmariotti.cardslib.library.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class BitmapUtils {

    /**
     * Creates a File from a Bitmap
     *
     * @param bitmap to convert in a file
     *
     * @return File
     */
    public static File createFileFromBitmap(Bitmap bitmap) {

        if (bitmap == null) return null;

        File photoFile;

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File photostorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        photoFile = new File(photostorage, (System.currentTimeMillis()) + ".jpg");
        try {
            //f.createNewFile();
            FileOutputStream fo = new FileOutputStream(photoFile);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            Log.e("BirthDayCard", "error", e);
        }

        return photoFile;
    }

    /**
     * Creates a intent with an image
     *
     * @param image
     * @return
     */
    public static Intent createIntentFromImage(File image) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        return share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
    }


}
