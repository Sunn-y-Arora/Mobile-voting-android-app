package myapp.app.com.mobilevoting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class CastVote extends Fragment {
    FirebaseDatabase database= FirebaseDatabase. getInstance ();;
    DatabaseReference myRef = database.getReference();
    static Button b;
    static TextView t1,t2;
    static String candiname,pname;
    static ItemData i;
    static String vote_c;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_cast_vote, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");

        Bundle bl=getArguments();
        String f=bl.getString("flag");
        String c=bl.getString("cons");
       final String mob=bl.getString("mob");

        b=(Button) getView().findViewById(R.id.vote);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference use= database.getReference().child("users").child(mob);


               DatabaseReference f=use.child("voted to");

                f.setValue(candiname+ "("+pname+")" );

                Toast.makeText(getContext(),"You have voted successfully",Toast.LENGTH_SHORT).show();

                f=use.child("flag");
                f.setValue("1");
                Integer temp_c=Integer.parseInt(vote_c)+1;
                DatabaseReference candid=database.getReference().child("candidates").child(i.getText()).child("votes");

                candid.setValue(temp_c.toString());
                Toast.makeText(getContext(),candid.toString(),Toast.LENGTH_LONG).show();

            }
        });



        if(f.compareTo("0")!=0)
        {
         Toast.makeText(getContext(),"You have Already Voted",Toast.LENGTH_LONG).show();
        }
        else {
            DatabaseReference consti = myRef.child("constituency").child(c).child("Candidates");
            ;

            final Spinner sp = (Spinner) getView().findViewById(R.id.spinner);
            consti.addValueEventListener(new ValueEventListener() {

                ArrayList<ItemData> list = new ArrayList<>();

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String a1 = dataSnapshot.getValue().toString();
                    //EditText ed1=(EditText) getView().findViewById(R.id.test);
                    String a[] = a1.split(",");
                    Arrays.sort(a);
                    list.add(new ItemData(a[0], R.drawable.aap));
                    list.add(new ItemData(a[1], R.drawable.bjp));
                    list.add(new ItemData(a[2], R.drawable.bsp));
                    list.add(new ItemData(a[3], R.drawable.inc));
                    SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), R.layout.spinner_layout, R.id.txt, list);

                    sp.setAdapter(adapter);
                    sp.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            b=(Button) getView().findViewById(R.id.vote);
            t1=(TextView) getView().findViewById(R.id.party);
            t2=(TextView) getView().findViewById(R.id.candidate);

            b.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            i=(ItemData)parent.getItemAtPosition(pos);

            final  DatabaseReference c=myRef.child("candidates").child(i.getText());
            Toast.makeText(getContext(),c.toString(),Toast.LENGTH_SHORT).show();
            c.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Map<String,Object> val1 = (Map<String, Object>) dataSnapshot.getValue();
                    candiname=val1.get("Name").toString();
                    t1.setText("Vote For: "+candiname);
                    String t= val1.get("Party").toString();
                    vote_c=val1.get("votes").toString();
                    t2.setText("Party Name: "+t);
                    t=val1.get("Party code").toString();
                    pname=t;
                    b.setText("Vote for "+t);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });




        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

}