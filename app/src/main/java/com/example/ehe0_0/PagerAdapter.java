package com.example.ehe0_0;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
//import com.chootdev.blurimg.BlurImage;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    WallpaerPagerActivity wallpaerPagerActivity;
    ArrayList<String> allImageList;

    public PagerAdapter(WallpaerPagerActivity wallpaerPagerActivity, ArrayList<String> allImageList) {
        this.allImageList = allImageList;
        this.wallpaerPagerActivity = wallpaerPagerActivity;
    }

    @Override
    public int getCount() {
        return allImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(wallpaerPagerActivity).inflate(R.layout.pager_adapter_item, container, false);
        ImageView imgBlur;
        ImageView simpleImg;
        ImageView share;
        ImageView download;
        ImageView setwallpaper;
//        imgBlur = view.findViewById(R.id.imgBlur);
        simpleImg = view.findViewById(R.id.simpleImage);
        share = view.findViewById(R.id.share);
        download = view.findViewById(R.id.download);
        setwallpaper = view.findViewById(R.id.set_wallpaper);

//        BlurImage.withContext(wallpaerPagerActivity).blurFromUri(allImageList.get(position)).into(imgBlur);
        Glide.with(wallpaerPagerActivity).load(allImageList.get(position)).into(simpleImg);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new shareImage().execute(allImageList.get(position));
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImage().execute(allImageList.get(position));
            }
        });

        setwallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new setWallPaper().execute(allImageList.get(position));
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            Toast.makeText(wallpaerPagerActivity, "Done Download", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);


                Bitmap icon = myBitmap;

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                int time = (int) (System.currentTimeMillis());
                File f = new File(Environment.getExternalStorageDirectory() + "/Wallpaper 4k/" + time + "myimage" + ".jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(wallpaerPagerActivity, "Download successfully", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }
        private void refreshGallery(WallpaerPagerActivity wallpaerPagerActivity, Uri imageUri) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(imageUri);
            wallpaerPagerActivity.sendBroadcast(mediaScanIntent);
        }
    }

    class shareImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Toast.makeText(wallpaerPagerActivity, "Share Successfully", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);


                Bitmap b = myBitmap;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.setType("text/html");
                share.putExtra(Intent.EXTRA_TEXT, "shre body");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(wallpaerPagerActivity.getContentResolver(),
                        b, "Title", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                wallpaerPagerActivity.startActivity(Intent.createChooser(share, "Select"));


            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }
    }

    class setWallPaper extends AsyncTask<String, Void, Bitmap> {

        Random random;
        int var;

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            Toast.makeText(wallpaerPagerActivity, "Wallpaper set Success", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Bitmap doInBackground(String... urls) {

            random = new Random();
            var = random.nextInt(10000);

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(wallpaerPagerActivity.getApplicationContext());
                try {
                    myWallpaperManager.setBitmap(myBitmap);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            } catch (Exception e) {

                e.printStackTrace();

            }
            return null;
        }
    }

}
