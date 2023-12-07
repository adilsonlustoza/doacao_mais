package br.com.lustoza.doacaomais.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by Adilson on 13/02/2018.
 */

public class HandleFile {

    private final Context context;
    private final String fileName;
    private File file;

    public HandleFile(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public void WriteFile(String content) {

        try {

            file = context.getFileStreamPath(fileName);

            if (!file.exists())
                file.createNewFile();

            FileOutputStream _fileOutputStream = new FileOutputStream(file);
            _fileOutputStream.write(content.getBytes());
            _fileOutputStream.flush();
            _fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void WriteBitMapFile(Bitmap bitmap) {

        try {

            file = context.getFileStreamPath(fileName);

            if (file.exists())
                file.delete();
            file.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream _fileOutputStream = new FileOutputStream(file);
            _fileOutputStream.write(bitmapdata);
            _fileOutputStream.flush();
            _fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public long LastChangeFile() {

        try {
            file = context.getFileStreamPath(fileName);
            if (file.exists())
                return file.lastModified();

        } catch (Exception e) {
            TrackHelper.WriteError(this, "LastChangeFile", e.getMessage());
        }
        return 0;
    }

    public String ReadFile() {

        try {
            file = context.getFileStreamPath(fileName);
            if (file.exists()) {
                FileInputStream _fileInputStream = new FileInputStream(file);
                BufferedReader _bufferedReader = new BufferedReader(new InputStreamReader(_fileInputStream));
                StringBuilder _stringBuilder = new StringBuilder();
                String line;
                while ((line = _bufferedReader.readLine()) != null)
                    _stringBuilder.append(line);
                return _stringBuilder.toString();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Bitmap ReadBitMapFile() {
        Bitmap bitmap = null;
        BitmapFactory.Options options;
        try {

            file = context.getFileStreamPath(fileName);

            if (file.exists()) {
                options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                FileInputStream inputStream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void ApagarArquivo() {

        file = context.getFileStreamPath(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

}

