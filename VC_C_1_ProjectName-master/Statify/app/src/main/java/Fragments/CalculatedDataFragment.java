package Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DataProcessing.ExpressionCalculator;
import DataProcessing.VariableGraph;
import communication.ClientCommHttp;
import vc_c_1.statify.MainActivity;
import vc_c_1.statify.R;

/**
 * Fragment responsible for managing calculated data points.
 * From here, the user can add or edit data points for any given activity.
 * The will have the ability to write expressions based on other variables.
 */
public class CalculatedDataFragment extends Fragment {

    private ArrayList<String> expressionNames = new ArrayList<String>();
    private ArrayList<String> expressions = new ArrayList<String>();

    private static ClientSideDBManager lm;
    private String currentActivity = MainActivity.getcurrentActivity();
    private String currentUserID = MainActivity.getCurrentUserID();

    /**
     * Required default constructor
     */
    public CalculatedDataFragment() { }


    /**
     * Runs on creation and intstatiation of the fragment
     * @param inflater Default fragment layout inflater
     * @param container Default ViewGroup container
     * @param savedInstanceState Unused save-state of fragment. Required by override.
     * @return The initialized inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculated_data, container, false);

        Button btn_add_data_point = (Button)v.findViewById(R.id.btn_add_data_point);
        btn_add_data_point.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataPointDialog();
            }
        });

        lm = new ClientSideDBManager(getActivity());
        lm.createCalculateDataTable(currentActivity);

        expressionNames = lm.pullCalculateDataPointTitles(currentActivity);
        expressions = lm.pullCalculateDataPointExpressions(currentActivity);

        for(String str: expressionNames) System.out.println("ABC NAMES: " + str);
        for(String str: expressions) System.out.println("ABC EXPRESSIONS: " + str);

        for(int i = 0; i < expressions.size(); i++){
            final String name = expressionNames.get(i);
            final String expression = expressions.get(i);
            createDataPoint(name, expression, v);
        }

        return v;
    }

    /**
     * Creates a new data point for the module
     * @param name Name of data point to be added
     * @param expression Expression of data point to be added
     */
    private void createDataPoint(final String name, final String expression, View view){
        View placeholder = getView();
        if(view != null){
            placeholder = view;
        }

        ViewGroup vg = (ViewGroup) placeholder.findViewById(R.id.parent_data_viewgroup);
        final int locationID = vg.getChildCount() - 1;
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;

        TextView tv1 = new TextView(getActivity());
        tv1.setText(name);
        tv1.setLayoutParams(layoutParams);
        ll.addView(tv1);

        TextView tv2 = new TextView(getActivity());
        String numericalExpression = ExpressionCalculator.processExpression(expression, expressionNames, expressions);
        String expressionOutput = ExpressionCalculator.calculate(numericalExpression);
        tv2.setText(expressionOutput);
        tv2.setLayoutParams(layoutParams);

        ll.addView(tv2);

        Button btn = new Button(getActivity());
        btn.setText("Edit");
        btn.setLayoutParams(layoutParams);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDataPointDialog(name, expression, locationID);
            }
        });
        ll.addView(btn);

        vg.addView(ll,locationID);


        if(view == null){
            expressionNames.add(name);
            expressions.add(expression);
            lm.updateCalculatedDataPoint(currentActivity, name, expression);
            String toSend = currentUserID + ";" + currentActivity  + ";" + name + ";" + expression;
            ClientCommHttp c = new ClientCommHttp();
            try {
                c.sendData("add%20calcData", toSend);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Modifies an existing data point on the module
     * @param name Name of the data point to be modified
     * @param expression New expression of data point for update
     * @param locationID Integer address of targeted data point
     */
    private void modifyDataPoint(final String name, final String expression, final int locationID){
        ViewGroup vg = (ViewGroup) getView().findViewById(R.id.parent_data_viewgroup);
        LinearLayout ll = (LinearLayout) vg.getChildAt(locationID);

        TextView tv1 = (TextView) ll.getChildAt(0);
        tv1.setText(name);

        TextView tv2 = (TextView) ll.getChildAt(1);
        String numericalExpression = ExpressionCalculator.processExpression(expression,expressionNames, expressions);
        String expressionOutput = ExpressionCalculator.calculate(numericalExpression);
        tv2.setText(expressionOutput);

        Button btn = (Button) ll.getChildAt(2);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDataPointDialog(name, expression, locationID);
            }
        });

        expressionNames.set(locationID, name);
        expressions.set(locationID, expression);
        lm.updateCalculatedDataPoint(currentActivity, name, expression);

        updateExpressions();


    }

    private void updateExpressions(){
        ViewGroup vg = (ViewGroup) getView().findViewById(R.id.parent_data_viewgroup);
        for(int i = 0; i < expressionNames.size(); i++){
            LinearLayout ll = (LinearLayout) vg.getChildAt(i);

            TextView expressionResult = (TextView) ll.getChildAt(1);
            String numericalExpression = ExpressionCalculator.processExpression(expressions.get(i), expressionNames, expressions);
            String expressionOutput = ExpressionCalculator.calculate(numericalExpression);
            expressionResult.setText(expressionOutput);
        }
    }

    /**
     * Creates and handles the dialog for adding a new data point
     */
    private void addDataPointDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_calcualted_data_point, null));
        builder.setTitle("Add Data")
                .setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = Dialog.class.cast(dialog);
                        EditText et1 = (EditText) d.findViewById(R.id.equation_name);
                        EditText et2 = (EditText) d.findViewById(R.id.equation_entry);
                        String newName = et1.getText().toString();
                        String newExpression = et2.getText().toString();

                        ArrayList<String> tempNames = expressionNames;
                        ArrayList<String> tempExpressions = expressions;
                        tempNames.add(newName);
                        tempExpressions.add(newExpression);

                        if(!VariableGraph.testIfValidGraph(tempNames,tempExpressions)){
                            addDataPointDialog();
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid Expression Combination", Toast.LENGTH_SHORT).show();
                        } else {
                            createDataPoint(newName, newExpression, null);
                        }


                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText et1 = (EditText) dialog.findViewById(R.id.equation_name);
        final EditText et2 = (EditText) dialog.findViewById(R.id.equation_entry);

        final Spinner spinner = (Spinner) dialog.findViewById(R.id.existing_data_selector);

        List<String> list = new ArrayList<String>();
        list.add("Add existing data point"); // Used as prompt for user

        final ViewGroup vg = (ViewGroup) getView().findViewById(R.id.parent_data_viewgroup);
        final int numDataPoints = vg.getChildCount();
        for(int i = 0; i < numDataPoints - 1; i++){
            list.add(expressionNames.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            private boolean statusFlag = false;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(statusFlag) {
                    int index = adapterView.getSelectedItemPosition() - 1;
                    LinearLayout ll = (LinearLayout) vg.getChildAt(index);
                    TextView tv = (TextView) ll.getChildAt(0);
                    String name = "$" + tv.getText().toString() + " ";
                    int start = Math.max(et2.getSelectionStart(), 0);
                    int end = Math.max(et2.getSelectionEnd(), 0);
                    et2.getText().replace(Math.min(start, end), Math.max(start, end), name, 0, name.length());
                    spinner.setSelection(0);
                    statusFlag = false;
                } else {
                    statusFlag = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
    }

    /**
     * Creates and handles the dialog for editing and existing data point
     */
    private void editDataPointDialog(final String name, final String expression, final int locationID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_calcualted_data_point, null));
        builder.setTitle(name)
                .setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Dialog d = Dialog.class.cast(dialog);
                        EditText et2 = (EditText) d.findViewById(R.id.equation_entry);
                        String newExpression = et2.getText().toString();

                        ArrayList<String> tempExpressions = expressions;
                        tempExpressions.set(locationID,newExpression);

                        if(!VariableGraph.testIfValidGraph(expressionNames,tempExpressions)){
                            editDataPointDialog(name, expression, locationID);
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid Expression Combination", Toast.LENGTH_SHORT).show();
                        } else {
                            modifyDataPoint(name, newExpression, locationID);
                            sendUpdate(name, newExpression);
                        }


                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText et1 = (EditText) dialog.findViewById(R.id.equation_name);
        final EditText et2 = (EditText) dialog.findViewById(R.id.equation_entry);

        et1.setVisibility(View.GONE);
        et2.setText(expression);

        final Spinner spinner = (Spinner) dialog.findViewById(R.id.existing_data_selector);

        List<String> list = new ArrayList<String>();
        list.add("Add existing data point"); // Used as prompt for user

        final ViewGroup vg = (ViewGroup) getView().findViewById(R.id.parent_data_viewgroup);
        final int numDataPoints = vg.getChildCount();
        int tempLocation = 0;
        for(int i = 0; i < numDataPoints - 1; i++){
            String toAdd = expressionNames.get(i);
            if(!toAdd.equals(name))
                list.add(toAdd);
            else
                tempLocation = i;
        }
        final int editLocation = tempLocation;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            private boolean statusFlag = false;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(statusFlag) {
                    int index = adapterView.getSelectedItemPosition() - 1;
                    if(index >= editLocation){
                        index++;
                    }
                    LinearLayout ll = (LinearLayout) vg.getChildAt(index);
                    TextView tv = (TextView) ll.getChildAt(0);
                    String name = "$" + tv.getText().toString() + " ";
                    int start = Math.max(et2.getSelectionStart(), 0);
                    int end = Math.max(et2.getSelectionEnd(), 0);
                    et2.getText().replace(Math.min(start, end), Math.max(start, end), name, 0, name.length());
                    spinner.setSelection(0);
                    statusFlag = false;
                } else {
                    statusFlag = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
    }

    /**
     * Prepares and sends an update to the server of changed data point
     * @param expressionName Name of data point to be changed
     * @param expression Expression of hte data point to be changed
     */
    private void sendUpdate(String expressionName, String expression){
        String data = currentUserID + "%20" + currentActivity + "%20" + expressionName + "%20" + expression;
        ClientCommHttp c = new ClientCommHttp();
        try {
            c.sendData("update", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}