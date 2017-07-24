import java.awt.Color;
import java.awt.Dimension;

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

   public BubbleChart_AWT( String s, String file ) {
      super( s );
      JPanel jpanel = createDemoPanel(file );
      jpanel.setPreferredSize(new Dimension( 560 , 370 ) );
      setContentPane( jpanel );
   }

   private static JFreeChart createChart( XYZDataset xyzdataset ) {
      JFreeChart jfreechart = ChartFactory.createBubbleChart(
         "LENGTH VS START TIME VS COUNT",
         "LENGTH",
         "START TIME",
         xyzdataset,
         PlotOrientation.HORIZONTAL,
         true, true, false);

      XYPlot xyplot = ( XYPlot )jfreechart.getPlot( );
      xyplot.setForegroundAlpha( 0.65F );
      XYItemRenderer xyitemrenderer = xyplot.getRenderer( );
      xyitemrenderer.setSeriesPaint( 0 , Color.blue );
      NumberAxis numberaxis = ( NumberAxis )xyplot.getDomainAxis( );
      numberaxis.setLowerMargin( 0.2 );
      numberaxis.setUpperMargin( 0.5 );
      NumberAxis numberaxis1 = ( NumberAxis )xyplot.getRangeAxis( );
      numberaxis1.setLowerMargin( 0.8 );
      numberaxis1.setUpperMargin( 0.9 );

      return jfreechart;
   }

   public static XYZDataset createDataset(String file ) {

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
        double ad3[][] = {lengthArr, startTime, countArr};
        defaultxyzdataset.addSeries("Series 1", ad3);

 		} catch (IOException e) {
 				e.printStackTrace();
 		}
    return defaultxyzdataset;
   }

   public static JPanel createDemoPanel(String file ) {
      JFreeChart jfreechart = createChart( createDataset(file ) );
      ChartPanel chartpanel = new ChartPanel( jfreechart );

      chartpanel.setDomainZoomable( true );
      chartpanel.setRangeZoomable( true );

      return chartpanel;
   }

   public static void main( String args[ ] ) {
     Scanner scan = new Scanner(System.in);
     System.out.print("CSV File: ");
     String fileName = scan.next();

     BubbleChart_AWT bubblechart = new BubbleChart_AWT( "Bubble Chart_frame", fileName );
     bubblechart.pack( );
     RefineryUtilities.centerFrameOnScreen( bubblechart );
     bubblechart.setVisible( true );
   }
}
