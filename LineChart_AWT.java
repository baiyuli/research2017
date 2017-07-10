import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class LineChart_AWT extends ApplicationFrame {

   public LineChart_AWT( String applicationTitle , String chartTitle, ArrayList<Event> e ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Start time","Lengths",
         createDataset(e),
         PlotOrientation.VERTICAL,
         true,true,false);

      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset(ArrayList<Event> e ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      for (int x = 0; x < e.size(); x++) {
        dataset.addValue(e.get(x).getLength(), "length", e.get(x).getStartTime() + "");
      }
      return dataset;
   }

  //  public static void main( String[ ] args ) {
  //     LineChart_AWT chart = new LineChart_AWT(
  //        "School Vs Years" ,
  //        "Number of Schools vs years");
   //
  //     chart.pack( );
  //     RefineryUtilities.centerFrameOnScreen( chart );
  //     chart.setVisible( true );
  //  }
}
