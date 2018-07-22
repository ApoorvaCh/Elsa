package com.example.apoorvach.chatui;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.alicebot.ab.MagicStrings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileWriter extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "FileWriter";
        Context mContext;
        String root_dir_application;
        String botName = "MY_BOT";
        final static String AIML = "/Vaccines";
        IWritinngCompletionCallback mCallback;

        public interface IWritinngCompletionCallback {
            void onWritingComplete();
        }

        public FileWriter(Context context, IWritinngCompletionCallback callback) {
            mContext = context;
            root_dir_application = context.getFilesDir() + AIML + File.separator + "bots" + File.separator + botName;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AssetManager assets = mContext.getResources().getAssets();
            File appDir = new File(root_dir_application);
            boolean b = appDir.mkdirs();
            if (appDir.exists()) {
                try {
                    for (String dir : assets.list("Vaccines")) {
                        File subdir = new File(appDir.getPath() + "/" + dir);
                        subdir.mkdirs();
                        for (String file : assets.list("Vaccines/" + dir)) {
                            File f = new File(appDir.getPath() + "/" + dir + "/" + file);
//                        if (f.exists()) {
//                            continue;
//                        }
                            InputStream in = null;
                            OutputStream out = null;
                            in = assets.open("Vaccines/" + dir + "/" + file);
                            out = new FileOutputStream(appDir.getPath() + "/" + dir + "/" + file);
                            copyFile(in, out);
                            in.close();
                            in = null;
                            out.flush();
                            out.close();
                            out = null;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e(TAG, "does not exist");
            }
            return null;

        }

        private void copyFile(InputStream in, OutputStream out) throws IOException {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            MagicStrings.root_path = mContext.getFilesDir() + AIML;
            mCallback.onWritingComplete();
        }
    }



