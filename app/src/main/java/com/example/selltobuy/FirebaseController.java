package com.example.selltobuy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FirebaseController {

    private static FirebaseAuth MAUTH;
    private static FirebaseDatabase DATABASE;
    private static DatabaseReference MYREF;
    private Context context;

    private static FirebaseStorage STORAGE;
    private static StorageReference STORAGEREFERENCE;

    public FirebaseController(Context context) {
        this.context = context;
    }

    public static FirebaseAuth getAuth()
    {
        if(MAUTH == null)
            MAUTH =FirebaseAuth.getInstance();
        return MAUTH;
    }

     public static FirebaseDatabase getDATABASE()
     {
        if(DATABASE==null)
        {
            DATABASE = FirebaseDatabase.getInstance();
        }
        return DATABASE;
     }

     public static DatabaseReference getMYREF(String key)
     {
         MYREF = getDATABASE().getReference(key);
         return MYREF;
     }

    public boolean currentUser()
    {
        if(getAuth().getCurrentUser()==null)
            return false;
        return true;
    }


    public void saveProduct(Product product ) {
        DatabaseReference data = getMYREF("products").push();
        uploadImage(product.getImage(),data.getKey());
        Product product1 = new Product(product.getPrice(),product.getName(),product.getInfo(),product.getStratDate(),product.getFinalDate());
       product1.setPid(data.getKey());
        data.setValue(product1);
    }

    public void saveTechProduct(TechProduct product) {
        DatabaseReference data = getMYREF("techProducts").push();
        uploadImage(product.getImage(),data.getKey());
        TechProduct product1 = new TechProduct(product.getPrice(),product.getName(),product.getInfo(),product.getStratDate(),product.getFinalDate(),product.getSociety());
        product1.setPid(data.getKey());
        data.setValue(product1);
    }

    public void updateProduct(String id, int price)
    {
        getMYREF("products").child(id).child("price").setValue(price);
    }

    public void updateTechProduct(String id, int price)
    {
        getMYREF("techProducts").child(id).child("price").setValue(price);
    }


    public void retrieveData( IFirebaseCallback firebaseCallback)
    {
        getMYREF("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList productList = new ArrayList<Product>();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    final Product p = data.getValue(Product.class);
                    p.setPid(data.getKey());
                    final String key = p.getPid();
                    final long ONE_MEGABYTE = 1024*1024;
                    StorageReference mRef = getSTORAGEREFERENCE().child(  key + ".jpg");
                    mRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            productList.add(new Product(p.getPrice(),p.getName(),p.getInfo(),p.getStratDate(),p.getFinalDate(),bitmap));
                            firebaseCallback.onCallbackList(productList);
                        }
                    });

                   // productList.add(p);
                }
               //firebaseCallback.onCallbackList(productList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


     public void readTechProducts( IFirebaseCallback firebaseCallback)
    {
        getMYREF("techProducts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList techProductList = new ArrayList<TechProduct>();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    final TechProduct p = data.getValue(TechProduct.class);
                    p.setPid(data.getKey());
                    final String key = p.getPid();
                    final long ONE_MEGABYTE = 1024*1024;
                    StorageReference mRef = getSTORAGEREFERENCE().child(  key + ".jpg");
                    mRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            techProductList.add(new TechProduct(p.getPrice(),p.getName(),p.getInfo(),p.getStratDate(),p.getFinalDate(),bitmap,p.getSociety()));
                            firebaseCallback.onCallbackList(techProductList);

                        }
                    });
                    //techProductList.add(p);
                }
                //firebaseCallback.onCallbackTechList(techProductList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void createUser(User user, String password) {
        getAuth().createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setId(task.getResult().getUser().getUid());
                            getMYREF("users").child(task.getResult().getUser().getUid()).setValue(user);
                            context.startActivity(new Intent(context,Buyproduct.class));
                        } else {

                            Toast.makeText(context, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void lonIn (String email , String password)
    {
        getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            context.startActivity(new Intent(context,Buyproduct.class));
                        } else {
                            Toast.makeText(context, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut()
    {
        getAuth().signOut();
        context.startActivity(new Intent(context,MainActivity.class));
    }

    public void swich()
    {
        getAuth().signOut();
    }

    public void read(IFirebaseCallback firebaseCallback)
    {
        FirebaseUser user = getAuth().getCurrentUser();
        getMYREF("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                firebaseCallback.onCallbackUser(value);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    public static FirebaseStorage getSTORAGE()
    {
        if (STORAGE==null)
            STORAGE = FirebaseStorage.getInstance();
        return STORAGE;
    }

    public static StorageReference getSTORAGEREFERENCE()
    {
        STORAGEREFERENCE = getSTORAGE().getReference();
        return STORAGEREFERENCE;
    }

    public void uploadImage(Bitmap bitmap , String name)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();
        StorageReference mRef = getSTORAGEREFERENCE().child(name+".jpg");
        UploadTask uploadTask = mRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }
}
