package com.univ.hesiris.universityandroid;


import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import DataStructures.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements View.OnClickListener {
    Button buttonInsert;
    Button buttonSearch;
    Button buttonEdit;
    Button buttonRemove;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextYear;
    TextView textViewID;
    View thisView;

    ArrayList<Student> studentList=new ArrayList<Student>();
    //this needs to be declared for whole clase in order to be able to interpret the results from
    //the SearchStudentActivity

    public StudentFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_student, container,
                false);


        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        buttonInsert = (Button) thisView.findViewById(R.id.buttonInsert);
        buttonSearch = (Button) thisView.findViewById(R.id.buttonSearch);
        buttonEdit = (Button) thisView.findViewById(R.id.buttonEdit);
        buttonRemove = (Button) thisView.findViewById(R.id.buttonRemove);
        editTextFirstName = (EditText) thisView.findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) thisView.findViewById(R.id.editTextLastName);
        editTextYear = (EditText) thisView.findViewById(R.id.editTextYear);
        textViewID = (TextView) thisView.findViewById(R.id.textViewIDValue);

        thisView.refreshDrawableState();

        buttonInsert.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);


        return thisView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonInsert:
                if (!editTextFirstName.getText().toString().equals("") &&
                        !editTextLastName.getText().toString().equals("") &&
                        !editTextYear.getText().toString().equals("")) {
                    new InsertStudent().execute(editTextFirstName.getText().toString(),
                            editTextLastName.getText().toString(), editTextYear.getText().toString());


                    textViewID.setText("");
                    buttonEdit.setEnabled(false);
                    buttonEdit.setClickable(false);

                    buttonRemove.setEnabled(false);
                    buttonRemove.setClickable(false);
                }
                else
                    Toast.makeText(thisView.getContext(),"No field may be left empty",Toast.LENGTH_LONG).show();

                break;
            case R.id.buttonSearch:

                String fname = editTextFirstName.getText().toString();
                String lname = editTextLastName.getText().toString();
                String year = editTextYear.getText().toString();

                if (fname.equals(""))
                    fname = "%";
                if (lname.equals(""))
                    lname = "%";
                if (year.equals(""))
                    year = "-1";

                new SearchStudents().execute(fname,lname,year);

                break;
            case R.id.buttonEdit:
                if (textViewID.getText().equals(""))
                    Toast.makeText(thisView.getContext(),"You should not have been able to do this\n" +
                            "Search for a student first",Toast.LENGTH_LONG).show();
                else{
                    fname = editTextFirstName.getText().toString();
                    lname = editTextLastName.getText().toString();
                    year = editTextYear.getText().toString();
                    String id = textViewID.getText().toString();
                    new UpdateStudent().execute(fname,lname,year,id);
                }
                break;
            case R.id.buttonRemove:
                if (textViewID.getText().equals(""))
                    Toast.makeText(thisView.getContext(),"You should not have been able to do this\n" +
                            "Search for a student first",Toast.LENGTH_LONG).show();
                else {
                    fname = editTextFirstName.getText().toString();
                    lname = editTextLastName.getText().toString();
                    year = editTextYear.getText().toString();
                    String id = textViewID.getText().toString();
                    new RemoveStudent().execute(fname,lname,year,id);
                }

                break;
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {
            if (resultCode == -1) {
                //Toast.makeText(thisView.getContext(), "Response from AuthFormActivity: " + data.getExtras().get("index"),
                  //      Toast.LENGTH_SHORT).show();
                if (data!=null && data.getExtras()!=null && data.getExtras().get("index")!=null) {
                    int i = Integer.parseInt(data.getExtras().get("index").toString());
                    Student student = studentList.get(i);
                    editTextFirstName.setText(student.prenume);
                    editTextLastName.setText(student.nume);
                    editTextYear.setText(Integer.toString(student.an));
                    textViewID.setText(Long.toString(student.getId()));

                    buttonEdit.setEnabled(true);
                    buttonEdit.setClickable(true);

                    buttonRemove.setEnabled(true);
                    buttonRemove.setClickable(true);
                }
            }
        }
    }

    /*
    the first set of parameters should be the new data, and the last parameter the id of the modifie
     */

    public class RemoveStudent extends Client {
        @Override
        protected String doInBackground(String... params) {
            String[] result = Arrays.copyOf(new String[]{super.REMOVE_STUDENT}, 1 + params.length);
            System.arraycopy(params, 0, result, 1, params.length);
            return super.doInBackground(result);
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            textViewID.setText("");
            editTextFirstName.setText("");
            editTextLastName.setText("");
            editTextYear.setText("");

            buttonEdit.setEnabled(false);
            buttonEdit.setClickable(false);

            buttonRemove.setEnabled(false);
            buttonRemove.setClickable(false);

        }
    }

    public class UpdateStudent extends Client {
        @Override
        protected String doInBackground(String... params) {
            String[] result = Arrays.copyOf(new String[]{super.UPDATE_STUDENT}, 1 + params.length);
            System.arraycopy(params, 0, result, 1, params.length);
            return super.doInBackground(result);
        }
    }

    public class InsertStudent extends Client{

        @Override
        protected String doInBackground(String... params){
            String[] result = Arrays.copyOf(new String[]{super.INSERT_STUDENT}, 1 + params.length);
            System.arraycopy(params, 0, result, 1, params.length);
            return super.doInBackground(result);
        }
    }

    public class Client extends AsyncTask<String,String,String> {

        public static final String INSERT_STUDENT = "InsertStudent";
        public static final String UPDATE_STUDENT = "UpdateStudent";
        public static final String REMOVE_STUDENT = "RemoveStudent";

        @Override
        protected void onProgressUpdate(String... text) {
            Toast.makeText(thisView.getContext(), text[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(thisView.getContext(),result,Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... params) {
            String msg = "";
            Socket socket = null;
            //publishProgress("TCP Connection started");

            try {
                socket = new Socket("10.0.2.2", 2000);
                //publishProgress("Socket created \n");

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                //publishProgress("IO ready for transmission \n");

                out.println(params[0]);
                out.println(new Student(params[1],params[2],Integer.parseInt(params[3])).serialize());
                if (params[0].equals(UPDATE_STUDENT) || params[0].equals(REMOVE_STUDENT))
                    out.println(params[4]);


                msg= in.readLine();

                in.close();
                out.close();
                socket.close();
                //publishProgress("Connection closed.");
                return msg;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public class SearchStudents extends AsyncTask<String,String,ArrayList<Student>> {


        @Override
        protected void onProgressUpdate(String... text) {
            Toast.makeText(thisView.getContext(), text[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(ArrayList<Student> result) {
            if (!result.isEmpty()) {
                Intent intent = new Intent(thisView.getContext(), SearchStudentActivity.class);
                ArrayList<String> stringified = new ArrayList<String>();
                for (Student s : result) {
                    stringified.add(s.toString());
                }
                intent.putStringArrayListExtra("students", stringified);
                startActivityForResult(intent,111);
            }
            else
                Toast.makeText(thisView.getContext(), "No students match the criteria", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected ArrayList<Student> doInBackground(String... params) {
            String msg = "";
            Socket socket = null;
            //publishProgress("TCP Connection started");

            try {
                socket = new Socket("10.0.2.2", 2000);
                //publishProgress("Socket created \n");

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                //publishProgress("IO ready for transmission \n");

                out.println("SearchStudent");
                out.println(new Student(params[0],params[1],Integer.parseInt(params[2])).serialize());

                publishProgress("Searching for students");

                if (!studentList.isEmpty())
                    studentList = new ArrayList<Student>();
                msg= in.readLine();
                while (!msg.equals("END")){
                    studentList.add(Student.deserialize(msg));
                    msg= in.readLine();
                }


                in.close();
                out.close();
                socket.close();
                //publishProgress("Connection closed.");
                return studentList;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
