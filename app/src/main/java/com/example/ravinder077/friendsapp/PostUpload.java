package com.example.ravinder077.friendsapp;

/**
 * Created by ravinder077 on 07-08-2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chugh on 7/3/2017.
 */

public class PostUpload extends AsyncTask<Bitmap,String,String> {

    private Context mContext;
    private SQLiteDatabase msqLiteDatabase;
    private String userid;
    private String status;

    public PostUpload (Context context) {
        mContext = context;
    }
    public PostUpload (Context context,SQLiteDatabase sqLiteDatabase){
        mContext = context;
        msqLiteDatabase=sqLiteDatabase;
    }
    public PostUpload (String context,String sqLiteDatabase,Context context1){
        userid=context;
        status=sqLiteDatabase;
        mContext=context1;
    }


    @Override
    protected String doInBackground(Bitmap... params) {

        // URL url=null;
        //String sourceFileUri = params[0];
        String st=null;
        int serverResponseCode=0;
        String saveimg = saveToInternalStorage( params[0]);

        System.out.println("saveimg"+saveimg);


        String sourceFileUri=saveimg;

        String upLoadServerUri = "http://omtii.com/mile/saveimage.php";
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            System.out.println("cannot not read source ");

        }
        try { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploadedimage", fileName);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedimage\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            //System.out.println();


            String json_response = "";
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String text = "";
            while ((text = br.readLine()) != null) {
                json_response += text;
                st=json_response;
            }

            System.out.println("uploadFile HTTP Response is :"+json_response);


            System.out.println("uploadFile HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            //st=serverResponseMessage;
            if(serverResponseCode == 200){

                System.out.println("serverResponseCode "+serverResponseCode);

            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {

            ex.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

     /*   int serverResponseCode = 0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        String fileName = sourceFile.getName();
        String st = null;
        System.err.println("filename=" + fileName);
        try {
            System.err.println("filename in try =" + fileName);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            // open a URL connection to the Servlet
            url = new URL("http://omtii.com/mile/saveimage.php");

            // Open a HTTP  connection to  the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploadedimage", fileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            //dos.writeBytes("Content-Disposition: form-data; name=uploadedimage;" + "filename=" + fileName + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedimage\";filename=\""+ fileName + "\"" + lineEnd);
            // create a buffer of  maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                System.err.println("bytesRead in while =" + bytesRead);
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);

            if (serverResponseCode == 200) {

                System.err.println("Response code 200");
            } else

            {
                System.err.println("Response code " + serverResponseCode);
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {


            ex.printStackTrace();


            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {


            e.printStackTrace();

        }      */


        //  code to upload data to server start


       /* FileUpload fup=new FileUpload(getApplicationContext());
        fup.execute(b);
        System.err.println("imageName1"+b);*/


    /*    String fupget=fup.get();
        System.err.println("fupget" + fupget);*/










        // OtpGen otpg=new OtpGen();





        /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datetime=dateFormat.format(date);*/



        //  System.err.println("query" + "http://omtii.com/mile/postwalldata.php?profilename="+username.getText()+"&status="+status.getText()+"&datetime="+datetime);
        String nurl="http://omtii.com/mile/postwalldata.php?profilename="+userid+"&status="+status+"&imgpath="+st;
        String numurl= null;
        try {
            numurl = parseUrl(nurl);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //
       // System.err.println("save img"+saveimg);

        //  otpg.execute(numurl);

        System.err.println("numurl" + numurl);


        //code to upload data to server ends

        URL url;

        try {


            url=new URL(numurl);
            System.err.println("url"+url);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent","");
            con.setRequestMethod("GET");

            con.connect();

            InputStream in= con.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder result=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null)
            {
                result.append(line);
            }

            st=result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }





        return st;
    }

    public static String parseUrl(String surl) throws Exception
    {
        URL u = new URL(surl);
        return new URI(u.getProtocol(), u.getAuthority(), u.getPath(), u.getQuery(), u.getRef()).toString();
    }


    private String saveToInternalStorage(Bitmap bitmapImage){
        System.err.println("I m in save Internal storage function");
        ContextWrapper cw = new ContextWrapper(mContext);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", MODE_PRIVATE);
        // Create imageDir
        String imgname="upic.jpg";
        File mypath=new File(directory,imgname);

        FileOutputStream fos = null;
        try {
            System.err.println("I m in save Internal storage function try block");
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            System.err.println("I m in save Internal storage function catch block");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()+"/"+imgname;
    }


}
