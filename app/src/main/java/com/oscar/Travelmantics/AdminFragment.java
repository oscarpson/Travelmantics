package com.oscar.Travelmantics;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oscar.Travelmantics.model.DealModel;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment implements View.OnClickListener {

    private static final int SELECT_PICTURE = 100;
    StorageReference storageRef;
    Button btnSelectImg;
    ImageView imgDeal;
    Bitmap bitmap;
    String imagePath;
    String imgDownloadUrl;
    Uri selectedImageUri;
    EditText edtTitle;
    EditText edtDescription;
    EditText edtPrice;
    Button btnSave;
    String dealFirebaseId;

    public AdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://youthapp-3987d.appspot.com/");
        btnSelectImg=view.findViewById(R.id.btnImage);
        imgDeal=view.findViewById(R.id.img_deal);
        edtTitle=view.findViewById(R.id.edtTitle);
        edtDescription=view.findViewById(R.id.edtdescription);
        edtPrice=view.findViewById(R.id.edtprice);
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnSelectImg.setOnClickListener(this);
        setHasOptionsMenu(true);
//        Log.e("userfirebase",FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"if null");

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnImage:
                selectImage();
                break;
            case R.id.btnSave:
                saveDeal();
        }
    }

    private void saveDeal() {
        if (imgDeal.getDrawable() != null) {
            Random rnd = new Random();
            final int randoms = 100000 + rnd.nextInt(900000);
            final StorageReference myfileRef = storageRef.child(randoms + "quizimg.jpg");
            imgDeal.setDrawingCacheEnabled(true);
            imgDeal.buildDrawingCache();
            bitmap = imgDeal.getDrawingCache();

           /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data2 = baos.toByteArray();
            imgDeal.setImageDrawable(null);
            imgDeal.setBackground(null);
            imgDeal.destroyDrawingCache();*/
            // UploadTask uploadTask = myfileRef.putBytes(data);

            UploadTask uploadTask = myfileRef.putFile(selectedImageUri);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), taskSnapshot.getBytesTransferred() + "", Toast.LENGTH_SHORT).show();

                }
            });
             uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return myfileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                       // Uri downloadUrl = myfileRef.getDownloadUrl().getResult();//taskSnapshot.getDownloadUrl();
                        //Uri downloadUrl=urlTask.getResult()
                        String DOWNLOAD_URL = downloadUrl.getPath();
                        Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                        Log.d("Downx", DOWNLOAD_URL + "\n" + downloadUrl);
                        imgDownloadUrl = downloadUrl.toString();
                        Toast.makeText(getActivity(), DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
                        //String userId = dbref.push().getKey();
                        //Date today = Calendar.getInstance().getTime();
                        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
                        //final String reportDate = df.format(today);
                        // ChatsReal chatsReal = new ChatsReal(userKey, photourl, reportDate, sedtchat);
                        // dbref.child("chats").child(userId).setValue(chatsReal);
                        DealModel deal = new DealModel(edtTitle.getText().toString(), edtDescription.getText().toString(), edtPrice.getText().toString(), imgDownloadUrl);
                        FirebaseUtil.addDeal(deal);
                        ((MainActivity)getActivity()).replaceFragment(new UserFragment());
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });


        }
    }

    private void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"),SELECT_PICTURE );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                 selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    imagePath = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + imagePath);
                    // Set the image in ImageView
                    imgDeal.setImageURI(selectedImageUri);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveDeal();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
