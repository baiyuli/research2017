import java.awt.Color;
import java.util.*;
import java.awt.BasicStroke;
import java.io.*;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class Chart_AWT extends ApplicationFrame {

   public Chart_AWT( String applicationTitle, String chartTitle, String fileName ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createScatterPlot(
         chartTitle ,
         "Time" ,
         "Number of Event Pairs Satisfying 'During'" ,
         createDataset(fileName) ,
         PlotOrientation.VERTICAL ,
         true , true , false);

         ChartPanel chartPanel = new ChartPanel( xylineChart );
         chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
         setContentPane( chartPanel );
   }

   private static XYDataset createDataset(String fileName ) {
      XYSeriesCollection result = new XYSeriesCollection();
      XYSeries series = new XYSeries("Collaborations in CS Research");
      String csvFile = fileName;
      String line = "";
      String csvSplitBy = ",";
      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] entry = line.split(csvSplitBy);
        int startTime = Integer.parseInt(entry[0].substring(1, entry[0].length() - 1));
        int numEvents = Integer.parseInt(entry[1].substring(1, entry[1].length() - 1));

        series.add((double)startTime, (double)numEvents);
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
      result.addSeries(series);
      return result;
   }
   
   public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      System.out.print("CSV File: ");
      String fileName = scan.next();
      Chart_AWT chart = new Chart_AWT("Scatter Plot",
         "Number of Event Pairs satisfying 'During' vs. Time", fileName);
      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}
