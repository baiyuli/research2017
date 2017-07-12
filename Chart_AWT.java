import java.awt.Color;
import java.util.*;
import java.awt.BasicStroke;

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

   public Chart_AWT( String applicationTitle, String chartTitle, ArrayList<Event> e ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createScatterPlot(
         chartTitle ,
         "Start Time" ,
         "Length" ,
         createDataset(e) ,
         PlotOrientation.VERTICAL ,
         true , true , false);

         ChartPanel chartPanel = new ChartPanel( xylineChart );
         chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
         setContentPane( chartPanel );
   }

   private XYDataset createDataset(ArrayList<Event> event ) {
      XYSeriesCollection result = new XYSeriesCollection();
      XYSeries series = new XYSeries("TEST");
      for (int x = 0; x < event.size(); x++) {
        series.add((double)(event.get(x).getStartTime()), (double)(event.get(x).getLength()));
      }
      result.addSeries(series);

      return result;
   }
   //
  //  public static void main( String[ ] args ) {
  //     Chart_AWT chart = new Chart_AWT("Scatter Plot",
  //        "Length vs. Start Time");
  //     chart.pack( );
  //     RefineryUtilities.centerFrameOnScreen( chart );
  //     chart.setVisible( true );
  //  }
}
