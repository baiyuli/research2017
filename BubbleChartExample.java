
import java.io.*;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BubbleXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * @author imssbora
 */
public class BubbleChartExample extends JFrame {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public BubbleChartExample(String title, String chartTitle, String file) {
    super(title);

    // Create dataset
    XYZDataset dataset = createDataset(file);

    // Create chart
    JFreeChart chart = ChartFactory.createBubbleChart(
        "Start Time vs Length vs Number of Events", 
        "Length", 
        "Start Time", dataset);

    
    // Set range for X-Axis
    XYPlot plot = chart.getXYPlot();
    NumberAxis domain = (NumberAxis) plot.getDomainAxis();
    domain.setRange(1936, 2014);

    // Set range for Y-Axis
    NumberAxis range = (NumberAxis) plot.getRangeAxis();
    range.setRange(0, 50);
    
    //Format label
    XYBubbleRenderer renderer=(XYBubbleRenderer)plot.getRenderer();
    BubbleXYItemLabelGenerator generator=new BubbleXYItemLabelGenerator(
        "({1},{2},{3}) ",new DecimalFormat("0"),
        new DecimalFormat("0"),
        new DecimalFormat("0"));
    renderer.setBaseItemLabelGenerator(generator);
    renderer.setBaseItemLabelsVisible(true);
    
    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  /**
   * @return
   */
  private XYZDataset createDataset(String file) {
    DefaultXYZDataset dataset = new DefaultXYZDataset();

    // dataset.addSeries("INDIA", new double[][] { 
    //   { 40 }, // X-Value 
    //   { 65 }, // Y-Value 
    //   { 70 }  // Z-Value 
    //  });
    // dataset.addSeries("USA", new double[][] { { 30 }, { 20 }, { 50 } });
    // dataset.addSeries("CHINA", new double[][] { { 80 }, { 50 }, { 80 } });
    // dataset.addSeries("JAPAN", new double[][] { { 11 }, { 50 }, { 20 } });

    
    String csvFile = file;
    String line = "";
    String csvSplitBy = ",";
    
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] entry = line.split(csvSplitBy);
        int startTime = Integer.parseInt(entry[0].substring(1, entry[0].length() - 1));
        int length = Integer.parseInt(entry[1].substring(1, entry[1].length() - 1));
        int numEvents = Integer.parseInt(entry[2].substring(1, entry[2].length() - 1));
        dataset.addSeries(" ", new double[][]{ { startTime }, { length }, { numEvents } });
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataset;
  }

  // public static void main(String[] args) {
  //   SwingUtilities.invokeLater(() -> {
  //     BubbleChartExample example = new BubbleChartExample("Bubble Chart Example | BORAJI.COM");
  //     example.setSize(800, 400);
  //     example.setLocationRelativeTo(null);
  //     example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  //     example.setVisible(true);
  //   });
  // }
}