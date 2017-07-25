import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import java.awt.BasicStroke;

import javax.swing.JPanel;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BubbleChart_AWT extends ApplicationFrame {

   public BubbleChart_AWT( String applicationTitle, String chartTitle, ArrayList<Event> e) {
      super(applicationTitle);

      JPanel jpanel = createDemoPanel(e);
      jpanel.setPreferredSize(new Dimension( 560 , 370 ) );
      setContentPane( jpanel );

   }
   public static JFreeChart createChart(XYZDataset xyzdataset){
      JFreeChart jfreechart = ChartFactory.createBubbleChart(

         "Start Time vs Length vs Number of Events",
         "Length",
         "Start Time",

         xyzdataset,
         PlotOrientation.HORIZONTAL,
         true, true, false);



      XYPlot xyplot = ( XYPlot )jfreechart.getPlot( );
      xyplot.setForegroundAlpha( 0.65F );
      XYItemRenderer xyitemrenderer = xyplot.getRenderer( );
      xyitemrenderer.setSeriesPaint( 0 , Color.blue );
      NumberAxis numberaxis = ( NumberAxis )xyplot.getDomainAxis( );
      numberaxis.setLowerMargin( 0.2 );
      numberaxis.setUpperMargin( 0.3 );
      NumberAxis numberaxis1 = ( NumberAxis )xyplot.getRangeAxis( );
      numberaxis1.setLowerMargin( 0.3 );
      numberaxis1.setUpperMargin( 0.4 );

      return jfreechart;
}



// <<<<<<< HEAD
//    public static XYZDataset createDataset(String file ) {
//
//      DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset();
//
//     String csvFile = file;
//  		String line = "";
//  		String csvSplitBy = ",";
//     double lengthArr[] = new double[11707];
//     double startTime[] = new double[11707];
//     double countArr[] = new double[11707];
//     int counter = 0;
//  		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//         line = br.readLine();
//  				while ((line = br.readLine()) != null) {
//
//  						// use comma as separator
//  						String[] event = line.split(csvSplitBy);
//  						int start_time = Integer.valueOf(event[0].substring(1, event[0].length() - 1));
//             startTime[counter] = start_time;
//  						int length = Integer.valueOf(event[1].substring(1, event[1].length() - 1));
//             lengthArr[counter] = length;
//  						int count = Integer.valueOf(event[2].substring(1, event[2].length() - 1));
//             countArr[counter] = count;
//             counter++;
//
//
//
//  				}
//         double ad3[][] = {lengthArr, startTime, countArr};
//         defaultxyzdataset.addSeries("Series 1", ad3);
//
//  		} catch (IOException e) {
//  				e.printStackTrace();
//  		}
//     return defaultxyzdataset;
//    }
//
//    public static JPanel createDemoPanel(String file ) {
//       JFreeChart jfreechart = createChart( createDataset(file ) );
// =======
   public static XYZDataset createDataset(ArrayList<Event> event) {
      DefaultXYZDataset defaultxyzdataset = new DefaultXYZDataset();

      ArrayList<Double> timeArrLst = new ArrayList<Double>();
      ArrayList<Double> lengthArrLst = new ArrayList<Double>();
      ArrayList<Double> numArrLst = new ArrayList<Double>();

      double startTime = (double) event.get(0).getStartTime();
      double length = (double) event.get(0).getLength();
      double eventNum = 0;
      for (int x = 0; x < event.size(); x++){
         if(event.get(x).getStartTime()==startTime &&
            event.get(x).getLength()==length){
            eventNum++;
         }
         else{
            startTime=event.get(x).getStartTime();
            length=event.get(x).getLength();
            numArrLst.add(eventNum);
            eventNum=1;
            timeArrLst.add((double) event.get(x).getStartTime());
            lengthArrLst.add((double) event.get(x).getLength());
         }

      }
      double[] timeArray = new double[timeArrLst.size()];
      double[] lengthArray = new double[lengthArrLst.size()];
      double[] numArray = new double[numArrLst.size()];
      for(int x = 0; x<timeArrLst.size(); x++){
         timeArray[x]=timeArrLst.get(x);
         lengthArray[x]=lengthArrLst.get(x);
         numArray[x]=numArrLst.get(x);

      }
      double[][] totalArray = {lengthArray, timeArray, numArray};
      System.out.println(Arrays.toString(numArray));
      System.out.println(Arrays.toString(timeArray));
      System.out.println(Arrays.toString(lengthArray));


      defaultxyzdataset.addSeries( "Series 1" , totalArray);
      return defaultxyzdataset;
   }

   public static JPanel createDemoPanel(ArrayList<Event> e ) {
      JFreeChart jfreechart = createChart( createDataset( e) );

      ChartPanel chartpanel = new ChartPanel( jfreechart );

      chartpanel.setDomainZoomable( true );
      chartpanel.setRangeZoomable( true );

      return chartpanel;
   }

// <<<<<<< HEAD
//    public static void main( String args[ ] ) {
//      Scanner scan = new Scanner(System.in);
//      System.out.print("CSV File: ");
//      String fileName = scan.next();
//
//      BubbleChart_AWT bubblechart = new BubbleChart_AWT( "Bubble Chart_frame", fileName );
//      bubblechart.pack( );
//      RefineryUtilities.centerFrameOnScreen( bubblechart );
//      bubblechart.setVisible( true );
//    }
// =======
   // public static void main( String args[ ] ) {
   //    BubbleChart_AWT bubblechart = new BubbleChart_AWT( "Bubble Chart_frame" );
   //    bubblechart.pack( );
   //    RefineryUtilities.centerFrameOnScreen( bubblechart );
   //    bubblechart.setVisible( true );
   // }
