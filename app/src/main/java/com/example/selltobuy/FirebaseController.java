package com.example.selltobuy;



import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.selltobuy.receiver.SaleReceiver;
import com.example.selltobuy.activities.BuyOneProduct;
import com.example.selltobuy.activities.Buyproduct;
import com.example.selltobuy.activities.MainActivity;
import com.example.selltobuy.classes.Product;
import com.example.selltobuy.classes.TechProduct;
import com.example.selltobuy.classes.User;
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
import com.google.firebase.database.Query;
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


    //פונקצייה זו מבטיחה שמופע יחיד של מחלקת FirebaseAuth נשמר, מאתחל אותו במידת הצורך ומחזיר את המופע.
    public static FirebaseAuth getAuth()
    {
        if(MAUTH == null)
            MAUTH =FirebaseAuth.getInstance();
        return MAUTH;
    }


    //פונקצייה זו מבטיחה שמופע יחיד של מחלקת FirebaseDatabase נשמר, מאתחל אותו במידת הצורך ומחזיר את המופע.
     public static FirebaseDatabase getDATABASE()
     {
        if(DATABASE==null)
        {
            DATABASE = FirebaseDatabase.getInstance();
        }
        return DATABASE;
     }


     //פונקצייה זו מבטיחה שמופע יחיד של מחלקת DatabaseReference נשמר, מאתחל אותו במידת הצורך ומחזיר את המופע
     public static DatabaseReference getMYREF(String key)
     {
         MYREF = getDATABASE().getReference(key);
         return MYREF;
     }

     //הפעולה בודקת אם קיים משתמש מחובר ומחזירה בהתאם
    public boolean currentUser()
    {
        if(getAuth().getCurrentUser()==null)
            return false;
        return true;
    }



    //הפעולה שומרת עצם מהמחלקה Product בפיירבייס
    public void saveProduct(Product product , String idSell) {
        DatabaseReference data = getMYREF("products").push();
        uploadImage(product.getImage(),data.getKey());
        Product product1 = new Product(product.getPrice(),product.getName(),product.getInfo(),product.getStratDate(),product.getFinalDate());
        product1.setPid(data.getKey());
        product1.setSellId(idSell);
        product1.setBuyId("null");
        data.setValue(product1);


        Intent intent2 = new Intent(context, SaleReceiver.class);

        intent2.putExtra("productId" ,product1.getPid());
        intent2.putExtra("techProductId" , "");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,intent2, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(3600000),pendingIntent);
        Toast.makeText(context, "in alarm", Toast.LENGTH_SHORT).show();
    }

    //הפעולה שומרת עצם מהמחלקה TechProduct בפיירבייס
    public void saveTechProduct(TechProduct product, String idSell) {
        DatabaseReference data = getMYREF("techProducts").push();
        uploadImage(product.getImage(),data.getKey());
        TechProduct product1 = new TechProduct(product.getPrice(),product.getName(),product.getInfo(),product.getStratDate(),product.getFinalDate(),product.getSociety());
        product1.setPid(data.getKey());
        product1.setSellId(idSell);
        product1.setBuyId("null");
        data.setValue(product1);

        Intent intent2 = new Intent(context,SaleReceiver.class);

        intent2.putExtra("productId" ,"");
        intent2.putExtra("techProductId" , product1.getPid());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,intent2, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(3600000),pendingIntent);
        Toast.makeText(context, "in alarm", Toast.LENGTH_SHORT).show();
    }

    //הפעולה מעדכנת את מחירו של מוצר בפיירבייס
    public void updateProduct(String id, int price,String idBuy)
    {
        getMYREF("products").child(id).child("sellId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sellId = snapshot.getValue(String.class);
                getMYREF("users").child(idBuy).child("coin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int coins = snapshot.getValue(Integer.class);
                        if(!sellId.equals(idBuy) && coins>=price)
                        {
                            getMYREF("products").child(id).child("price").setValue(price);
                            getMYREF("products").child(id).child("buyId").setValue(idBuy);

                            getMYREF("products").child(id).child("sellId").removeEventListener(this);
                            getMYREF("users").child(idBuy).child("coin").removeEventListener(this);
                        }
                        else
                        {
                            if (sellId.equals(idBuy))
                            {
                                Toast.makeText(context, "this is your product", Toast.LENGTH_SHORT).show();
                            }
                            else if (coins<price) {
                                Toast.makeText(context, "you don't have enough coins", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //הפעולה מעדכנת את מחירו של המוצר הטכנולוגי
    public void updateTechProduct(String id, int price,String idBuy)
    {
        getMYREF("techProducts").child(id).child("sellId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sellId = snapshot.getValue(String.class);
                getMYREF("users").child(idBuy).child("coin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int coins = snapshot.getValue(Integer.class);
                        if(!sellId.equals(idBuy) && coins>=price)
                        {
                            getMYREF("techProducts").child(id).child("price").setValue(price);
                            getMYREF("techProducts").child(id).child("buyId").setValue(idBuy);

                            getMYREF("techProducts").child(id).child("sellId").removeEventListener(this);
                            getMYREF("users").child(idBuy).child("coin").removeEventListener(this);
                        }
                        else
                        {
                            if (sellId.equals(idBuy))
                            {
                                Toast.makeText(context, "this is your product", Toast.LENGTH_SHORT).show();
                            }
                            else if (coins<price) {
                                Toast.makeText(context, "you don't have enough coins", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removeProduct(String id)
    {
                getMYREF("products").child(id).child("buyId").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String buyId = snapshot.getValue(String.class);

                        getMYREF("products").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Product product = snapshot.getValue(Product.class);
                                if(buyId.equals("null"))
                                {
                                    getMYREF("products").child(id).removeValue();
                                }
                                else
                                {
                                    getMYREF("products").child(id).child("price").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int price = snapshot.getValue(int.class);
                                            getMYREF("products").child(id).child("sellId").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String sellId = snapshot.getValue(String.class);
                                                    DatabaseReference data = getMYREF("sold").child(id);
                                                    data.setValue(product);
                                                    updateCoins(price,sellId,buyId);
                                                    getMYREF("products").child(id).removeValue();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    public void removeTechProduct(String id)
    {
        getMYREF("techProducts").child(id).child("buyId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String buyId = snapshot.getValue(String.class);

                getMYREF("techProducts").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Product product = snapshot.getValue(Product.class);
                        if(buyId.equals("null"))
                        {
                            getMYREF("techProducts").child(id).removeValue();
                        }
                        else
                        {
                            getMYREF("techProducts").child(id).child("price").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int price = snapshot.getValue(int.class);
                                    getMYREF("techProducts").child(id).child("sellId").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String sellId = snapshot.getValue(String.class);
                                            DatabaseReference data = getMYREF("sold").child(id);
                                            data.setValue(product);
                                            updateCoins(price,sellId,buyId);
                                            getMYREF("techProducts").child(id).removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    public void updateCoins (int price , String sellId , String buyId)
    {
        getMYREF("users").child(sellId).child("coin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sellCoin = snapshot.getValue(int.class);
                getMYREF("users").child(sellId).child("coin").setValue(sellCoin+price);
                getMYREF("users").child(buyId).child("coin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int buyCoin = snapshot.getValue(int.class);
                        getMYREF("users").child(buyId).child("coin").setValue(buyCoin-price);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //הפעולה המציגה את רשימת המוצרים
    public void retrieveData( IFirebaseCallback firebaseCallback)
    {
        Query query = getMYREF("products").orderByChild("stratDate");
        query.addValueEventListener(new ValueEventListener() {
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
                            Product product = new Product(p.getPrice(),p.getName(),p.getInfo(),p.getStratDate(),p.getFinalDate(),bitmap);
                            product.setPid(key);
                            product.setSellId(p.getSellId());
                            product.setBuyId(p.getBuyId());
                            productList.add(product);
                            firebaseCallback.onCallbackList(productList);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //הפעולה המציגה את רשימת המוצרים הטכנולוגיים
     public void readTechProducts( IFirebaseCallback firebaseCallback)
    {
        Query query = getMYREF("techProducts").orderByChild("stratDate");
        query.addValueEventListener(new ValueEventListener() {
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
                            TechProduct techProduct = new TechProduct(p.getPrice(),p.getName(),p.getInfo(),p.getStratDate(),p.getFinalDate(),bitmap,p.getSociety());
                            techProduct.setPid(key);
                            techProductList.add(techProduct);
                            firebaseCallback.onCallbackTechList(techProductList);

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //הפעולה יוצרת משתמש חדש ושומרת אותו בפיירבייס
    public void createUser(User user, String password) {
        getAuth().createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.setId(task.getResult().getUser().getUid());
                            getMYREF("users").child(task.getResult().getUser().getUid()).setValue(user);
                            context.startActivity(new Intent(context, Buyproduct.class));
                        } else {

                            Toast.makeText(context, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //פעולת התחברות של משתמש קיים לאפליקציה
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

    //הפעולה מנתקת את המשתמש המחובר
    public void logOut()
    {
        getAuth().signOut();
        context.startActivity(new Intent(context, MainActivity.class));
    }

    //פעולה המנתקת את המשתמש המחובר כדי לאפשר להחליף למשתמש אחר
    public void swich()
    {
        getAuth().signOut();
    }


    //הפעולה לקריאת המשתמש המחובר כדי להציג את פרטיו
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


    //פונקצייה זו מבטיחה שמופע יחיד של מחלקת FireBaseStorage נשמר, מאתחל אותו במידת הצורך ומחזיר את המופע.
    public static FirebaseStorage getSTORAGE()
    {
        if (STORAGE==null)
            STORAGE = FirebaseStorage.getInstance();
        return STORAGE;
    }

    //פונקצייה זו מבטיחה שמופע יחיד של מחלקת StorageReference נשמר, מאתחל אותו במידת הצורך ומחזיר את המופע.
    public static StorageReference getSTORAGEREFERENCE()
    {
        STORAGEREFERENCE = getSTORAGE().getReference();
        return STORAGEREFERENCE;
    }

    //הפעולה שומרת תמונה שהתקבלה ב storage של הפיירבייס
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


    public void myProducts( IFirebaseCallback firebaseCallback , FirebaseUser firebaseUser)
    {

        Query query = getMYREF("sold").orderByChild("stratDate");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList productList = new ArrayList<Product>();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    final Product p = data.getValue(Product.class);
                    p.setPid(data.getKey());
                    final String key = p.getPid();
                    if(p.getSellId().equals(firebaseUser.getUid()))
                    {
                        final long ONE_MEGABYTE = 1024*1024;
                        StorageReference mRef = getSTORAGEREFERENCE().child(  key + ".jpg");
                        mRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                Product product = new Product(p.getPrice(),p.getName(),p.getInfo(),p.getStratDate(),p.getFinalDate(),bitmap);
                                product.setPid(key);
                                product.setSellId(p.getSellId());
                                product.setBuyId(p.getBuyId());
                                productList.add(product);
                                firebaseCallback.onCallbackList(productList);
                            }
                        });
                    }
                    }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
