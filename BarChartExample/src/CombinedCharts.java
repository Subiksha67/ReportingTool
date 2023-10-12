import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombinedCharts extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultCategoryDataset barDataset;
    private DefaultCategoryDataset areaDataset;
    private ChartPanel barChartPanel;
    private ChartPanel areaChartPanel;

    public CombinedCharts(String title) {
        super(title);
        barDataset = new DefaultCategoryDataset();
        areaDataset = new DefaultCategoryDataset();

       // create panel
        JFreeChart barChart = createBarChart(barDataset);
        barChartPanel = new ChartPanel(barChart);
        JFreeChart areaChart = createAreaChart(areaDataset);
        areaChartPanel = new ChartPanel(areaChart);

        JPanel chartsPanel = new JPanel(new GridLayout(1, 2));
        chartsPanel.add(barChartPanel);
        chartsPanel.add(areaChartPanel);
        setLayout(new BorderLayout());
        add(chartsPanel, BorderLayout.CENTER);
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.SOUTH);
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "TOOLS DEPARTMENT - Bar Chart",
                "Tools Topics",
                "Knowledge on the Topics",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    
        CategoryPlot cplot = (CategoryPlot) chart.getPlot();
        cplot.setBackgroundPaint(SystemColor.controlDkShadow);
        BarRenderer r = (BarRenderer) cplot.getRenderer();
        r.setSeriesPaint(0, Color.YELLOW);
        return chart;
    }

    private JFreeChart createAreaChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createAreaChart(
                "TOOLS DEPARTMENT - Area Chart",
                "Tools Topics",
                "Knowledge on the Topics",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        CategoryPlot cplot = (CategoryPlot) chart.getPlot();
        cplot.setBackgroundPaint(SystemColor.controlDkShadow);

        AreaRenderer r = (AreaRenderer) cplot.getRenderer();
        r.setSeriesPaint(0, Color.RED);
        return chart;
    
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        JTextField topicField = new JTextField(20);
        JTextField knowledgeField = new JTextField(10);
        JButton button = new JButton("Submit");

        inputPanel.add(new JLabel("Tools Topics: "));
        inputPanel.add(topicField);
        inputPanel.add(new JLabel("Knowledge on the Topics:   %"));
        inputPanel.add(knowledgeField);
        inputPanel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String topics = topicField.getText();
                int knowledge = Integer.parseInt(knowledgeField.getText());
                updateBarChart(topics, knowledge);
                updateAreaChart(topics, knowledge);
                topicField.setText("");
                knowledgeField.setText("");
            }
        });

        return inputPanel;
    }

    private void updateBarChart(String topics, int knowledge) {
        barDataset.setValue(knowledge, "KNOWLEDGE", topics);
    }

    private void updateAreaChart(String topics, int knowledge) {
        areaDataset.setValue(knowledge, "KNOWLEDGE", topics);
    }

    public static void main(String[] args) {
            CombinedCharts combinedCharts = new CombinedCharts("Tools Department Charts");
            combinedCharts.setSize(1000, 600);
            combinedCharts.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            combinedCharts.setVisible(true);
    }
    }


