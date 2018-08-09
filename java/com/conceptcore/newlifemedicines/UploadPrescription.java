package com.conceptcore.newlifemedicines;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.media.ExifInterface;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.PhotoAdapter;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPrescription extends AppCompatActivity implements View.OnClickListener{

    private String LOG = "UploadPrescription";

    private static String TEMP_PRES_PHOTO_NAME = "Temp_Pres.jpg";

    private static int PICK_IMAGE = 101;
    private static int TAKE_PICTURE = 201;
    private Button btnFromGallery,btnTakePhoto,btnUploadPrescription;
    private RecyclerView rvPhotos;
    private EditText etxtTitle,etxtComments;

    PhotoAdapter photoAdapter;
    private List<String> list = null;

    private Call<AddressBean> call;

    HelperMethods helperMethods = null;

    private SharedPrefBean sharedPrefBean;

    private List<String> uploadFilesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_prescription);

        Toolbar toolbarUploadPres = findViewById(R.id.toolbarUploadPres);
        setSupportActionBar(toolbarUploadPres);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        helperMethods = new HelperMethods(UploadPrescription.this);
        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        list = new ArrayList<>();

        btnFromGallery = findViewById(R.id.btnFromGallery);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        rvPhotos = findViewById(R.id.rvPhotos);
        etxtTitle = findViewById(R.id.etxtTitle);
        etxtComments = findViewById(R.id.etxtComments);
        btnUploadPrescription = findViewById(R.id.btnUploadPrescription);


        btnFromGallery.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
        btnUploadPrescription.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFromGallery:
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
                break;
            case R.id.btnTakePhoto:
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, TAKE_PICTURE);
                break;
            case R.id.btnUploadPrescription:
                uploadPrescription();
                break;
        }
    }

    private void uploadPrescription(){
        helperMethods.showProgress();
        if (!validate()) {
            return;
        }
//        helperMethods.showProgress();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("title", etxtTitle.getText().toString());
        builder.addFormDataPart("description", etxtComments.getText().toString());
        builder.addFormDataPart("userId", sharedPrefBean.getUserId());

        /*for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            builder.addFormDataPart("file[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }*/
//        Log.e("upload file list size","" + uploadFilesList.size());
        for (int i = 0; i < uploadFilesList.size(); i++) {
//            File file = uploadFilesList.get(i);
            File file = new File(compressImage(uploadFilesList.get(i)));
//            Log.e("local file path ","compress image" + file.length());
            builder.addFormDataPart("image[]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

//            Toast.makeText(this, "file size" + String.valueOf(file.length()/1024) + "kb", Toast.LENGTH_SHORT).show();
        }

        MultipartBody requestBody = builder.build();

        call = new NewLifeApiService().getNewLifeApi().uploadPrescription(requestBody);
        call.enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200) {
                    AddressBean addressBean = response.body();
                    Toast.makeText(UploadPrescription.this, addressBean.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(UploadPrescription.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(UploadPrescription.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validate() {

        boolean valid = true;

        String title = etxtTitle.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
//        Log.e("returned uri","" + imageReturnedIntent.getData().toString());
//        Log.e("real path","" + getRealPathFromURI(imageReturnedIntent.getData()));
//        Log.e("back uri","" + Uri.fromFile(new File(getRealPathFromURI(imageReturnedIntent.getData()))).toString());

        switch(requestCode) {
            case 201:
                if(resultCode == RESULT_OK){

                    Uri selectedImage = getImageUri(UploadPrescription.this,(Bitmap) imageReturnedIntent.getExtras().get("data"));
//                    Log.e("camera uri","" + imageReturnedIntent.getExtras().get("data"));
                    list.add(getRealPathFromURI(selectedImage));

                    myCompressImage(selectedImage);

                    if(list.size() > 0){
                        rvPhotos.setVisibility(View.VISIBLE);
                    }
                    rvPhotos.setHasFixedSize(true);
                    LinearLayoutManager llm = new LinearLayoutManager(UploadPrescription.this);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvPhotos.setLayoutManager(llm);

                    photoAdapter = new PhotoAdapter(UploadPrescription.this,list);
                    rvPhotos.setAdapter(photoAdapter);
                }
                break;
            case 101:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    list.add(getRealPathFromURI(selectedImage));

                    myCompressImage(selectedImage);
//                    Toast.makeText(this, "list size" + list.size(), Toast.LENGTH_SHORT).show();

                    if(list.size() > 0){
                        rvPhotos.setVisibility(View.VISIBLE);
                    }

                    rvPhotos.setHasFixedSize(true);
                    LinearLayoutManager llm = new LinearLayoutManager(UploadPrescription.this);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvPhotos.setLayoutManager(llm);

                    photoAdapter = new PhotoAdapter(UploadPrescription.this,list);
                    rvPhotos.setAdapter(photoAdapter);
                }
                break;
        }
    }

    private void myCompressImage(Uri uri){
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = getFileFromInputStream(inputStream);
//            File fileToUpload = new File(compressImage(file.getAbsolutePath()));
            uploadFilesList.add(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                this,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String string = cursor.getString(column_index);
        cursor.close();
        return string;
    }

    //--Image scale down started--
    public String compressImage(String filePath) {

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;

        int actualWidth = options.outWidth;

        float maxHeight = 1200.0f;
        float maxWidth = 900.0f;

        float imgRatio = actualWidth / actualHeight;

        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {

                imgRatio = maxHeight / actualHeight;

                actualWidth = (int) (imgRatio * actualWidth);

                actualHeight = (int) maxHeight;

            } else if (imgRatio > maxRatio) {

                imgRatio = maxWidth / actualWidth;

                actualHeight = (int) (imgRatio * actualHeight);

                actualWidth = (int) maxWidth;

            } else {

                actualHeight = (int) maxHeight;

                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth,
                actualHeight);

        //      inJustDecodeBounds set to false to load the actual bitmap

        options.inJustDecodeBounds = false;

        //      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;

        options.inInputShareable = true;

        options.inTempStorage = new byte[16 * 1024];

        try {

            //          load the bitmap from its path

            bmp = BitmapFactory.decodeFile(filePath, options);

        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        try {

            scaledBitmap = Bitmap.createBitmap(actualWidth,
                    actualHeight, Bitmap.Config.ARGB_8888);

        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;

        float ratioY = actualHeight / (float) options.outHeight;

        float middleX = actualWidth / 2.0f;

        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();

        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);

        canvas.setMatrix(scaleMatrix);

        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        //      check the rotation of the image and display it properly

        ExifInterface exif = null;
        try {
            try {
                exif = new ExifInterface(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);

            Log.d("EXIF", "Exif: " + orientation);

            Matrix matrix = new Matrix();

            if (orientation == 6) {

                matrix.postRotate(90);

                Log.d("EXIF", "Exif: " + orientation);

            } else if (orientation == 3) {

                matrix.postRotate(180);

                Log.d("EXIF", "Exif: " + orientation);

            } else if (orientation == 8) {

                matrix.postRotate(270);

                Log.d("EXIF", "Exif: " + orientation);

            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);

        } catch (Exception e) {

            e.printStackTrace();
        }

        FileOutputStream out = null;

        String filename = getFilename();

        try {
            out = new FileOutputStream(filename);

            //          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private File getFileFromInputStream(InputStream inputStream) {
        try {
            File file = new File(getFilesDir(),TEMP_PRES_PHOTO_NAME);
            OutputStream output = new FileOutputStream(file);
            try {
                try {
                    byte[] buffer = new byte[4 * 1024]; // or other buffer size
                    int read;

                    while ((read = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                } finally {
                    output.close();
                }
                return file;
            } catch (Exception e) {
                e.printStackTrace(); // handle exception, define IOException and others
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;

        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height / (float)
                    reqHeight);

            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

        }
        final float totalPixels = width * height;

        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getFilename() {
//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "AllEvent/Images");
        File file = new File(getFilesDir(), "NewLifeMedicines/Images");
//        File file = new File(Environment.getDataDirectory(), "AllEvent/Images");

        if (!file.exists()) {
            file.mkdirs();
        }

        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");

        return uriSting;

    }

    //--ended--

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putStringArrayList("list1", (ArrayList<String>) list);
        savedInstanceState.putStringArrayList("uploadFilesList", (ArrayList<String>) uploadFilesList);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list = savedInstanceState.getStringArrayList("list1");
        uploadFilesList = savedInstanceState.getStringArrayList("uploadFilesList");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}

