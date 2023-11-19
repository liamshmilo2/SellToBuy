package com.example.selltobuy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseController {

    private static FirebaseAuth MAUTH;
    private static FirebaseDatabase DATABASE;
    private static DatabaseReference MYREF;
    private Context context;

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


    public void saveProduct(Product product) {
              getMYREF("products").push().setValue(product);

    }

    public void saveTechProduct(TechProduct product) {
        getMYREF("techProducts").push().setValue(product);

    }

    public void updateProduct(String id, int price)
    {
        getMYREF("products").child(id).child("price").setValue(price);
    }


    public void retrieveData( IFirebaseCallback firebaseCallback)
    {
        getMYREF("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList productList = new ArrayList<Product>();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Product p = data.getValue(Product.class);
                    p.setPid(data.getKey());
                    productList.add(p);
                }
                firebaseCallback.onCallbackList(productList);
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
                    Product p = data.getValue(Product.class);
                    p.setPid(data.getKey());
                    techProductList.add(p);
                }
                firebaseCallback.onCallbackList(techProductList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateTechProduct(String id, int price)
    {
        getMYREF("techProducts").child(id).child("price").setValue(price);
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
}
