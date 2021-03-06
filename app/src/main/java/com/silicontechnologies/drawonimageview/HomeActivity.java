package com.silicontechnologies.drawonimageview;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int SAVE_REQUEST = 1000;


    @BindView(R.id.imageview)
    ImageView imageView;

    @BindView(R.id.framelayout)
    FrameLayout frameLayout;

    @BindView(R.id.clear_image)
    Button clearImage;

    RadioGroup colorRadioGroup;
    RadioGroup pointerRadioGroup;
    RadioGroup selectionRadioGroup;


    private DrawView drawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawonimage);
        ButterKnife.bind(this);
        addDrawView();
        colorRadioGroup = (RadioGroup) findViewById(R.id.color_radiogroup);
        selectionRadioGroup = (RadioGroup) findViewById(R.id.radio_selection);
        pointerRadioGroup = (RadioGroup) findViewById(R.id.pointer_radiogroup);
        pointerRadioGroup.setOnCheckedChangeListener(pointerCheckedChangeListener);
        colorRadioGroup.setOnCheckedChangeListener(colorCheckedChangeListener);
        selectionRadioGroup.setOnCheckedChangeListener(selectionChangedListenere);

    }


    OnCheckedChangeListener selectionChangedListenere = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.radio_color:
                    colorRadioGroup.setVisibility(View.VISIBLE);
                    pointerRadioGroup.setVisibility(View.GONE);
                    break;
                case R.id.radio_pointer:
                    colorRadioGroup.setVisibility(View.GONE);
                    pointerRadioGroup.setVisibility(View.VISIBLE);
                    break;
                case R.id.radio_text:
                    addTextView();
                    break;
            }
        }
    };

    OnCheckedChangeListener colorCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.color_blue:
                    drawView.setColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary));
                    Toast.makeText(HomeActivity.this, "Blue color Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.color_green:
                    drawView.setColor(ContextCompat.getColor(HomeActivity.this, R.color.color_green));
                    Toast.makeText(HomeActivity.this, "Green color Selected", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.color_orange:
                    drawView.setColor(ContextCompat.getColor(HomeActivity.this, R.color.color_orange));
                    Toast.makeText(HomeActivity.this, "Orange color Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.color_red:
                    drawView.setColor(ContextCompat.getColor(HomeActivity.this, R.color.color_red));
                    Toast.makeText(HomeActivity.this, "Red color Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.color_yellow:
                    drawView.setColor(ContextCompat.getColor(HomeActivity.this, R.color.color_yellow));
                    Toast.makeText(HomeActivity.this, "Yellow color Selected", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    OnCheckedChangeListener pointerCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.pointer_small:
                    drawView.setStrokeWidth(3);
                    Toast.makeText(HomeActivity.this, "Small pointer selected", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.pointer_medium:
                    drawView.setStrokeWidth(6);
                    Toast.makeText(HomeActivity.this, "Medium pointer selected", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.pointer_large:
                    drawView.setStrokeWidth(10);
                    Toast.makeText(HomeActivity.this, "Large pointer selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pointer_xlarge:
                    drawView.setStrokeWidth(15);
                    Toast.makeText(HomeActivity.this, "XLarge pointer selected", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @OnClick({R.id.action_gallery,
            R.id.action_camera,
            R.id.save_image,
            R.id.clear_image}
    )
    public void OnActionClick(View view) {
        switch (view.getId()) {
            case R.id.action_camera:
                askForCameraPermission();
                break;
            case R.id.action_gallery:
                selectImageFromGallery();
                break;
            case R.id.save_image:
                askForWriteExternalStoragePermission();
                break;
            case R.id.clear_image:
                drawView.clear();
                break;
        }
    }

    public void addTextView() {
        final EditText editText = new EditText(this);
        editText.setText("Your Text");
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float dx = 0, dy = 0, x = 0, y = 0;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        x = event.getX();
                        y = event.getY();
                        dx = x - editText.getX();
                        dy = y - editText.getY();
                    }
                    break;
                    case MotionEvent.ACTION_MOVE: {
                        editText.setX(event.getX() - dx);
                        editText.setY(event.getY() - dy);
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        //your stuff
                    }
                    return true;
                }
                return false;
            }
        });
        frameLayout.addView(editText);
    }

    private void saveImage() {

        try {
            frameLayout.setDrawingCacheEnabled(true);
            frameLayout.buildDrawingCache();
            Bitmap drawingCache = frameLayout.getDrawingCache();
            String path = Environment.getExternalStorageDirectory().toString();
            File file = new File(path, new Random().nextInt() + ".jpg");
            OutputStream fOut = new FileOutputStream(file);
            drawingCache.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            Toast.makeText(this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void selectImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearImage.setVisibility(View.VISIBLE);
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            clearImage.setVisibility(View.VISIBLE);
            addDrawView();
        }

    }

    private void askForCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
        } else {
            selectImageFromCamera();
        }
    }

    private void askForWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_REQUEST);
            }
        } else {
            saveImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST) {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                selectImageFromCamera();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SAVE_REQUEST) {
            if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                Toast.makeText(this, "Unable to save images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addDrawView() {
        drawView = new DrawView(this);
        frameLayout.addView(drawView);
    }

    public void removeDrawView() {
        frameLayout.removeView(drawView);
    }

}
