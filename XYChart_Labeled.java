import java.awt.Color;
import java.io.*;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.TextAnchor;

/** @see https://stackoverflow.com/a/14459322/230513 */
public class XYChart_Labeled {


    public XYChart_Labeled(String file) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XYDataset dataset = createDataset(file);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 320);
            }
        };

        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static XYDataset createDataset(String file) {
        LabeledXYDataset ds = new LabeledXYDataset();
        // ds.add(11,  0, "");
        // ds.add(12, 68, "A");
        // ds.add(13, 65, "B");
        // ds.add(14, 67, "C");
        // ds.add(15, 69, "D");
        // ds.add(16, 60, "F");
        // ds.add(17, 83, "G");
        // ds.add(18, 86, "H");
        // ds.add(19, 70, "I");
        // ds.add(20, 60, "J");
        // ds.add(21, 55, "K");
        // ds.add(22, 54, "L");
        // ds.add(23, 50, "M");
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
        ds.add(startTime,length,""+numEvents);
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
           return ds;
    }

    private static class LabeledXYDataset extends AbstractXYDataset {

        private static final int N = 26;
        private List<Number> x = new ArrayList<Number>(N);
        private List<Number> y = new ArrayList<Number>(N);
        private List<String> label = new ArrayList<String>(N);

        public void add(double x, double y, String label){
            this.x.add(x);
            this.y.add(y);
            this.label.add(label);
        }

        public String getLabel(int series, int item) {
            return label.get(item);
        }

        @Override
        public int getSeriesCount() {
            return 1;
        }

        @Override
        public Comparable getSeriesKey(int series) {
            return "Unit";
        }

        @Override
        public int getItemCount(int series) {
            return label.size();
        }
        public int getLabel (int item){
            return Integer.parseInt(label.get(item));
        }

        @Override
        public Number getX(int series, int item) {
            return x.get(item);
        }

        @Override
        public Number getY(int series, int item) {
            return y.get(item);
        }
    }

    private static class LabelGenerator implements XYItemLabelGenerator {

        @Override
        public String generateLabel(XYDataset dataset, int series, int item) {
            LabeledXYDataset labelSource = (LabeledXYDataset) dataset;
            return labelSource.getLabel(series, item);
        }

    }

    private static JFreeChart createChart(final XYDataset dataset) {
        NumberAxis domain = new NumberAxis("Start Time");
        NumberAxis range = new NumberAxis("Length");
        domain.setAutoRangeIncludesZero(false);
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setBaseItemLabelGenerator(new LabelGenerator());
        renderer.setBaseItemLabelPaint(Color.green.darker());
        renderer.setBasePositiveItemLabelPosition(
            new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        renderer.setBaseItemLabelFont(
            renderer.getBaseItemLabelFont().deriveFont(14f));
        renderer.setBaseItemLabelsVisible(true);
        //renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
        JFreeChart chart = new JFreeChart(
            "Start Time vs Length vs Number of Events", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        return chart;
    }

    // public static void main(String[] args) {
    //     JFrame f = new JFrame();
    //     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
    //     f.add(chartPanel);
    //     f.pack();
    //     f.setLocationRelativeTo(null);
    //     f.setVisible(true);
    // }
}