package com.example.selltobuy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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



    public void createUser(User user, String password) {
        getAuth().createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setId(task.getResult().getUser().getUid());
                            getMYREF("users").child(task.getResult().getUser().getUid()).setValue(user);
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            context.startActivity(new Intent(context,Buyproduct.class));
                        } else {

                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
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
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            context.startActivity(new Intent(context,Buyproduct.class));
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
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

    public void read()
    {
        FirebaseUser user = getAuth().getCurrentUser();
        getMYREF("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
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
