package com.mrhi2020.ex86firebasestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.local.QueryResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Firebase Cloud FireStore : 실시간 DB처럼 DB목적이지만 실시간 리스너는 아닌 방식

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
    }

    public void clickSave(View view) {
        //firestore DB에 저장 [ Map Collection 단위로 저장 ]
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
//        Map<String, Object> user= new HashMap<>();
//        user.put("name", "sam");
//        user.put("age", 20);
//        user.put("address", "seoul");

//        String name= etName.getText().toString();
//        int age;
//        String address;

        PersonVO p= new PersonVO("aaa", 20, "seoul");
        Map<String, PersonVO> user= new HashMap<>();
        user.put(" ", p);

        CollectionReference userRef= firebaseFirestore.collection("persons");//노드라는 이름대신에 Collection 이라는 명칭을 사용한다고 보면 됨.
        Task task= userRef.add(user);
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "aaa");
            }
        });

    }

    final String TAG= "my";

    public void clickLoad(View view) {

        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        CollectionReference userRef= firebaseFirestore.collection("users");

        Task<QuerySnapshot> task= userRef.get();
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot snapshots= task.getResult();
                for(QueryDocumentSnapshot snapshot : snapshots){

                    Map<String, Object> user= snapshot.getData();
                    String name= user.get("name").toString();
                    int age= Integer.parseInt(user.get("age").toString());
                    String address= user.get("address").toString();

                    tv.append(name+", "+age +", "+address+"\n");
                }

            }
        });

    }
}