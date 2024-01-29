package com.example.selltobuy.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.selltobuy.classes.Check;
import com.example.selltobuy.FirebaseController;
import com.example.selltobuy.IFirebaseCallback;
import com.example.selltobuy.classes.MyDate;
import com.example.selltobuy.classes.Product;
import com.example.selltobuy.R;
import com.example.selltobuy.classes.TechProduct;
import com.example.selltobuy.classes.User;
import com.example.selltobuy.receiver.SaleReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Sell extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener , IFirebaseCallback {

    ImageView imageView, imageFromGallery;

    FloatingActionButton button;
    Button btnSell;
    EditText editTextName,editTextPrice,editTextInfo;
    Check check;
    String text;
    Bitmap bitmap;
    ActivityResultLauncher<Intent>getPicActivityResultLauncher;

    ActivityResultLauncher<Intent>getGalleryActivityResultLauncher;
    FirebaseController firebaseController;
    String sellId;
    String name,info,price,society;
    Product product;
    TechProduct techProduct;
    IFirebaseCallback iFirebaseCallback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.black)));



        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(this);

        imageFromGallery=findViewById(R.id.imageFromGallery);
        imageFromGallery.setOnClickListener(this);
        getPicActivityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK)
                {
                    Intent data = result.getData();

                    bitmap = data.getParcelableExtra("data");
                    imageView.setImageBitmap(bitmap);
                }
            }
        });


        getGalleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Intent filePath=result.getData();
                            try
                            {
                                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),filePath.getData());
                                imageFromGallery.setImageBitmap(bitmap);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });



        button=findViewById(R.id.floatingActionButton);
        btnSell=findViewById(R.id.buttonsell);
        btnSell.setOnClickListener(this);
        editTextName=findViewById(R.id.editTextName);
        editTextInfo=findViewById(R.id.editTextInfo);
        editTextPrice=findViewById(R.id.editTextPrice);
        check=new Check();
        firebaseController=new FirebaseController(this);
        Spinner chooseType = findViewById(R.id.chooseType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseType.setAdapter(adapter);
        chooseType.setOnItemSelectedListener(this);

        firebaseController.read(this);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImagePicker.with(this)
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                        .start()
//
//            }
//        });
    }

    private static final int PICK_IMAGE_REQUEST = 234;
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent, "Select an File"), PICK_IMAGE_REQUEST);
        getGalleryActivityResultLauncher.launch(intent);

    }


    //הפולה יוצרת את ה menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //פעולת ההצגה של ה menu שבודקת מה המשתמש בחר
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.setting){
            Intent intent1 = new Intent(Sell.this , SettingActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId()==R.id.user){
            Intent intent2 = new Intent(Sell.this , Userdetails.class);
            startActivity(intent2);
        }
        if (item.getItemId()==R.id.buy){
            Intent intent3 = new Intent(Sell.this , Buyproduct.class);
            startActivity(intent3);
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        text = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == imageView) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getPicActivityResultLauncher.launch(intent);
        }
        if (view==imageFromGallery)
        {
            showFileChooser();

        }
        if (text.equals("Tech product"))
        {
            Dialog dialog = new Dialog(Sell.this);
            dialog.setContentView(R.layout.societydiolog);
            Button btnSociety = dialog.findViewById(R.id.btnSociety);
            EditText societyText = dialog.findViewById(R.id.editSociety);
            btnSociety.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    society=societyText.getText().toString();
                    if (view==btnSociety)
                        dialog.dismiss();
                }
            });
            dialog.show();
        }
        if (view == btnSell) {
            name = editTextName.getText().toString();
            info = editTextInfo.getText().toString();
            price = editTextPrice.getText().toString();
            int price2;

            if (check.checkName(name) == false) {
                Toast.makeText(this, "please write a real name", Toast.LENGTH_SHORT).show();
            } else if (check.chekInpo(info) == false) {
                Toast.makeText(this, "please write any info about the product", Toast.LENGTH_SHORT).show();
            } else if (check.chekPrice(price) == false) {
                Toast.makeText(this, "please write price", Toast.LENGTH_SHORT).show();
            } else if (check.chekBitmap(bitmap)==false) {
                Toast.makeText(this, "please take any picture of your product", Toast.LENGTH_SHORT).show();
            } else {

                price2 = Integer.parseInt(price);
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                MyDate date1 = new MyDate(year, month + 1, day);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.DAY_OF_YEAR, 7);
                MyDate date2 = new MyDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));
                if (text.equals("General product")) {

                    product = new Product(price2, name, info, date1, date2,bitmap);
                    firebaseController.saveProduct(product,sellId);
                    Intent intent = new Intent(Sell.this, Buyproduct.class);
                    startActivity(intent);
                    //getAlarmProduct(product);

//                    Intent intent3 = new Intent(this, NotificationReceiver.class);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, intent3, PendingIntent.FLAG_IMMUTABLE);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (3000), pendingIntent);

                }
                if (text.equals("Tech product")) {
                    techProduct = new TechProduct(price2, name, info, date1, date2,bitmap, society);
                    firebaseController.saveTechProduct(techProduct,sellId);
                    Intent intent = new Intent(Sell.this, Buyproduct.class);
                    startActivity(intent);
                    //getAlarmTechProduct(techProduct);

//                    Intent intent3 = new Intent(this, NotificationReceiver.class);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, intent3, PendingIntent.FLAG_IMMUTABLE);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (3000), pendingIntent);
                }

            }
        }

    }

    @Override
    public void onCallbackUser(User user) {
        sellId=user.getId();
    }

    @Override
    public void onCallbackList(ArrayList<Product> products) {

    }

    @Override
    public void onCallbackTechList(ArrayList<TechProduct> techProducts) {

    }

    public void getAlarmProduct(Product product)
    {
        //Intent intent2 = new Intent("com.example.selltobuy.ACTION_NAME");
        Intent intent2 = new Intent(this, SaleReceiver.class);
       // String id = product.getPid();
        //Log.i("PROD", product.getPid());
        intent2.putExtra("productId" , product.getPid());
        intent2.putExtra("techProductId" , "");
           // intent2.addFlags(0);

//        if(techProduct!=null)
//        {
//            intent2.putExtra("techProductId",techProduct.getPid());
//        }
       // sendBroadcast(intent2);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,intent2, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(5000),pendingIntent);
        Toast.makeText(this, "in alarm", Toast.LENGTH_SHORT).show();
    }

    public void getAlarmTechProduct(TechProduct product)
    {
        //Intent intent2 = new Intent("com.example.selltobuy.ACTION_NAME");
        Intent intent2 = new Intent(this,SaleReceiver.class);
        intent2.putExtra("techProductId" , product.getPid());
        intent2.putExtra("productId" , "");
        // intent2.addFlags(0);

//        if(techProduct!=null)
//        {
//            intent2.putExtra("techProductId",techProduct.getPid());
//        }
        // sendBroadcast(intent2);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,intent2, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(5000),pendingIntent);
        Toast.makeText(this, "in alarm", Toast.LENGTH_SHORT).show();
    }
}