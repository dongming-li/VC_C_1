package Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import DataProcessing.ExpressionCalculator;
import vc_c_1.statify.MainActivity;
import vc_c_1.statify.R;

/**
 * Module for displaying the graph to the user
 */
public class GraphFragment extends Fragment {

    /**
     * Expression names for all expressions. Maps directly to expressions.
     */
    ArrayList<String> expressionNames;

    /**
     * Calculated values of all of the expressions.
     */
    ArrayList<Double> calculatedExpressions;


    /**
     * Required empty constructor
     */
    public GraphFragment() { }

    /**
     * On creation, this will make a graph and an update button. it will also take datapoints from the local storage to
     * assemble points for the graph to display.
     * @param inflater Default fragment layout inflater
     * @param container Default ViewGroup container
     * @param savedInstanceState Unused save-state of fragment. Required by override.
     * @return The initialized inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graphs, container, false);

        GraphView graph = (GraphView) v.findViewById(R.id.graph);
        Button updateButton = (Button) v.findViewById(R.id.graphUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGraph();
            }
        });

        ClientSideDBManager lm = MainActivity.getLm();
        ArrayList<String> expressions = lm.pullCalculateDataPointExpressions(MainActivity.getcurrentActivity());
        expressionNames = lm.pullCalculateDataPointTitles(MainActivity.getcurrentActivity());
        calculatedExpressions = new ArrayList<Double>();

        for(String expression: expressions){
            String processedExpression = ExpressionCalculator.processExpression(expression, expressionNames, expressions);
            Double calculatedExpression = Double.parseDouble(ExpressionCalculator.calculate(processedExpression));
            calculatedExpressions.add(calculatedExpression);

        }




        return v;
    }

    /**
     * When called, this method will update the graph with any new data inputted by the user
     */
    private void updateGraph(){
        EditText editText = (EditText) getView().findViewById(R.id.graphTextPattern);
        String searchPattern = editText.getText().toString();

        GraphView graph = (GraphView) getView().findViewById(R.id.graph);
        graph.removeAllSeries();

        ArrayList<DataPoint> tempDataPoints = new ArrayList<DataPoint>();
        int counter = 0;
        for(int i = 0; i < expressionNames.size(); i++){
            if (expressionNames.get(i).contains(searchPattern)){
                tempDataPoints.add(new DataPoint(counter, calculatedExpressions.get(i)));
                counter++;
            }
        }

        DataPoint dataPoints[] = new DataPoint[tempDataPoints.size()];
        for(int i = 0; i < tempDataPoints.size(); i++){
            dataPoints[i] = tempDataPoints.get(i);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(dataPoints.length - 1);
    }
}
