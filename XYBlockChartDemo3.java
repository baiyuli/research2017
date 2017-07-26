/* ----------------------
* XYBlockChartDemo3.java
* ----------------------
* (C) Copyright 2006, 2007, by Object Refinery Limited.
*
*/

// package demo;

import java.awt.Color;

import javax.swing.JPanel;

import java.lang.*;
import java.util.*;
import java.io.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
* A simple demonstration application showing another use for the
* {@link XYBlockRenderer} class.
*/
public class XYBlockChartDemo3 extends ApplicationFrame {

    /**
     * Constructs the demo application.
     *
     * @param title  the frame title.
     */
    public XYBlockChartDemo3(String title, String file) {
        super(title);
        JPanel chartPanel = createDemoPanel(title, file);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Creates a chart for the specified dataset.
     *
     * @param dataset  the dataset.
     *
     * @return A chart instance.
     */
    private static JFreeChart createChart(String title, XYZDataset dataset) {
        NumberAxis xAxis = new NumberAxis("X");
        xAxis.setRange(1920.0, 2018.0);
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        NumberAxis yAxis = new NumberAxis("Y");
        // yAxis.autoAdjustRange();
        yAxis.setInverted(false);
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYBlockRenderer renderer = new XYBlockRenderer();
        LookupPaintScale paintScale = new LookupPaintScale(0.0, 1000000.0,
                new Color (254, 254, 0));
        paintScale.add(10, new Color (254, 254, 0));
        paintScale.add(20.0, new Color (254, 230, 0));
        paintScale.add(40.0, new Color (254, 210, 0));
        paintScale.add(80.0, new Color (254, 190, 0));
        paintScale.add(160.0, new Color (254, 170, 0));
        paintScale.add(320.0, new Color (254, 150, 0));
        paintScale.add(640.0, new Color (254, 130, 0));
        paintScale.add(1280.0, new Color (254, 110, 0));
        paintScale.add(2560.0, new Color (254, 90, 0));
        paintScale.add(5120.0, new Color (254, 70, 0));
        paintScale.add(10240.0, new Color (254, 50, 0));
        paintScale.add(20480.0, new Color (254, 30, 0));
        paintScale.add(40960.0, new Color (254, 10, 0));
        paintScale.add(81920.0, new Color (254, 0, 0));
        // paintScale.add(50000.0, new Color (10, 100, 0));
        // paintScale.add(60000.0, new Color (10, 150, 0));
        // paintScale.add(70000.0, new Color (10, 200, 0));
        renderer.setPaintScale(paintScale);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setForegroundAlpha(0.66f);
        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        JFreeChart chart = new JFreeChart(title, plot);
        chart.removeLegend();
        chart.setBackgroundPaint(Color.white);
        SymbolAxis scaleAxis = new SymbolAxis(null, new String[] {"", "OK",
                "Uncertain", "Bad"});
        scaleAxis.setRange(0.5, 3.5);
        scaleAxis.setPlot(new PiePlot());
        scaleAxis.setGridBandsVisible(false);
        PaintScaleLegend psl = new PaintScaleLegend(paintScale, scaleAxis);
        psl.setAxisOffset(5.0);
        psl.setPosition(RectangleEdge.BOTTOM);
        psl.setMargin(new RectangleInsets(5, 5, 5, 5));
        chart.addSubtitle(psl);
        return chart;
    }

    /**
     * Creates a sample dataset.
     */
    private static XYZDataset createDataset(String file) {
      DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset();

     String csvFile = file;
     String line = "";
     String csvSplitBy = ",";
     double lengthArr[] = new double[11707];
     double startTime[] = new double[11707];
     double countArr[] = new double[11707];
     int counter = 0;
     try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
         line = br.readLine();
         while ((line = br.readLine()) != null) {

             // use comma as separator
             String[] event = line.split(csvSplitBy);
             int start_time = Integer.valueOf(event[0].substring(1, event[0].length() - 1));
             startTime[counter] = start_time;
             int length = Integer.valueOf(event[1].substring(1, event[1].length() - 1));
             lengthArr[counter] = length;
             int count = Integer.valueOf(event[2].substring(1, event[2].length() - 1));
             countArr[counter] = count;
             counter++;



         }
         double ad3[][] = {startTime, lengthArr, countArr};
         defaultxyzdataset.addSeries("Series 1", ad3);

     } catch (IOException e) {
         e.printStackTrace();
     }
     return defaultxyzdataset;
    }

    /**
     * Creates a panel for the demo.
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel(String title, String file) {
        return new ChartPanel(createChart(title, createDataset(file)));
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      System.out.print("CSV File: ");
      String fileName = scan.next();
        XYBlockChartDemo3 demo = new XYBlockChartDemo3("Length vs Start Time", fileName);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
