package myapp.app.com.mobilevoting;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPollStatus.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPollStatus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPollStatus extends Fragment {
    private Spinner spinner1;
    static String c_name=" ";
    static String res="";
    static boolean flag=false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_fragment_poll_status, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Poll Status");

        addListenerOnSpinnerItemSelection();
        //addListenerOnButton();
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) getView().findViewById(R.id.spinner2);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String cons=parent.getItemAtPosition(pos).toString();
            String arr[]=cons.split(" ");
            final FirebaseDatabase database= FirebaseDatabase. getInstance ();;
            DatabaseReference myRef = database.getReference();
            DatabaseReference candi = myRef.child("constituency").child(arr[0]);

           //Toast.makeText(getContext(),candi.toString(),Toast.LENGTH_LONG).show();

            candi.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,Object> val1 = (Map<String, Object>) dataSnapshot.getValue();
                    c_name=val1.get("Candidates").toString();

                    res="";
                    flag=false;
                    //Toast.makeText(getContext(),c_name,Toast.LENGTH_LONG).show();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            Button b= getView().findViewById(R.id.button3);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //res="";
                    String cname_arr[]=c_name.split(",");
                    Arrays.sort(cname_arr);

                    for(String i:c_name.split(",")) {

                        DatabaseReference temp=database.getReference().child("candidates").child(i).child("votes");
                        //Toast.makeText(getContext(),temp.toString(),Toast.LENGTH_SHORT).show();

                        temp.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String s=dataSnapshot.getValue(String.class);
                            //Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                                res+=s+",";
                                flag=true;

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }

                    /***********************Drawing Graph******************************************/
                    if(flag) {
                        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
                        PieChart pieChart = (PieChart) getView().findViewById(R.id.piechart);
                        pieChart.setUsePercentValues(true);

                        String te[] = res.split(",");

                        ArrayList<Entry> yvalues = new ArrayList<Entry>();
                        yvalues.add(new Entry(Float.parseFloat(te[0]), 0));
                        yvalues.add(new Entry(Float.parseFloat(te[1]), 1));
                        yvalues.add(new Entry(Float.parseFloat(te[2]), 2));
                        yvalues.add(new Entry(Float.parseFloat(te[3]), 3));


                        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

                        ArrayList<String> xVals = new ArrayList<String>();

                        xVals.add("INC");
                        xVals.add("BSP");
                        xVals.add("BJP");
                        xVals.add("AAP");



                        PieData data = new PieData(xVals, dataSet);
                        // In Percentage term
                        data.setValueFormatter(new PercentFormatter());

                        pieChart.setData(data);
                        pieChart.setDescription("Constituency no :");

                        pieChart.setDrawHoleEnabled(true);
                        pieChart.setTransparentCircleRadius(25f);
                        pieChart.setHoleRadius(25f);

                        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                        data.setValueTextSize(13f);
                        data.setValueTextColor(Color.DKGRAY);
                        pieChart.animateXY(1400, 1400);
                    }
                }
            });

        }


        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}