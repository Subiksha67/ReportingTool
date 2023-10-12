import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reporttool extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultPieDataset pieDataset;
    private DefaultCategoryDataset barDataset;
    private DefaultCategoryDataset lineDataset;
    private JTextField[] inputFields;
    private JLabel[] labels;
    private JComboBox[] comboBoxes;
    private ChartPanel chartPanel;
    private JFreeChart currentChart;

	public class CustomRenderer extends BarRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Paint getItemPaint(final int row,final int column){
            return (Color) comboBoxes[column].getSelectedItem();
        }
    }

    public Reporttool(String title) {
        super(title);
        pieDataset = createPieDataset();
        barDataset = createBarDataset();
        lineDataset = createLineDataset();
        currentChart = createPieChart(pieDataset);

        chartPanel = new ChartPanel(currentChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);

        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.SOUTH);
    }

    private DefaultPieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        return dataset;
    }

    private DefaultCategoryDataset createBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        return dataset;
    }

    private DefaultCategoryDataset createLineDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        return dataset;
    }

    private JFreeChart createPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Visteon's Customers",
                dataset,
                true,
                true,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        if(labels == null) return chart;
        for(int i=0;i<labels.length;i++){
            plot.setSectionPaint(labels[i].getText(),(Color) comboBoxes[i].getSelectedItem());
        }
        return chart;
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart",
                "CUSTOMERS",
                "PROJECTS PERCENTAGE",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CustomRenderer renderer = new CustomRenderer();
        plot.setRenderer(renderer);
        return chart;
    }

    private JFreeChart createLineChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                "Line Chart",
                "CUSTOMERS",
                "PROJECTS PERCENTAGE ",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }

	private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 3));
        labels = new JLabel[]{
                new JLabel("   Mazda (%):"),
                new JLabel("   BMW (%):"),
                new JLabel("   Nissan (%):"),
                new JLabel("   Ford (%):"),
                new JLabel("   Mahindra (%):"),
                new JLabel("   Toyota (%):"),
                new JLabel("   Volkswagen (%):"),
                new JLabel("   Renault (%):"),
                new JLabel("   JLR (%):")
        };

        inputFields = new JTextField[labels.length];
        comboBoxes = new JComboBox[labels.length];
        for (int i = 0; i < labels.length; i++) {
            inputFields[i] = new JTextField();
            inputPanel.add(labels[i]);
            inputPanel.add(inputFields[i]);
            comboBoxes[i] = new JComboBox<Color>();
            comboBoxes[i].addItem(new Color(194,196,197));
            comboBoxes[i].addItem(new Color(125,158,173));
            comboBoxes[i].addItem(new Color(38,132,173));
            comboBoxes[i].addItem(new Color(11,105,154));
            comboBoxes[i].addItem(new Color(12,61,96));
            comboBoxes[i].addItem(new Color(229,114,0));
            inputPanel.add(comboBoxes[i]);
        }

        JButton pieButton = new JButton("Show Pie Chart");
        pieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentChart = createPieChart(pieDataset);
                chartPanel.setChart(currentChart);
            }
        });
        inputPanel.add(pieButton);

        JButton barButton = new JButton("Show Bar Chart");
        barButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentChart = createBarChart(barDataset);
                chartPanel.setChart(currentChart);
            }
        });
        inputPanel.add(barButton);

        JButton lineButton = new JButton("Show Line Chart");
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentChart = createLineChart(lineDataset);
                chartPanel.setChart(currentChart);
            }
        });
        inputPanel.add(lineButton);


        JButton updateButton = new JButton("Update Chart");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateChart();
            }
        });
        inputPanel.add(updateButton);




        return inputPanel;
    }

    private void updateChart() {
        for (int i = 0; i < labels.length; i++) {
            String customerName = labels[i].getText();
            try {
                double percentage = Double.parseDouble(inputFields[i].getText());
                pieDataset.setValue(customerName, percentage);
                barDataset.setValue(percentage, "PERCENTAGE", customerName);
                lineDataset.setValue(percentage, "PERCENTAGE", customerName);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for " + customerName, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        currentChart = createPieChart(pieDataset);
        chartPanel.setChart(currentChart);
    }

    public static void main(String[] args) {
            Reporttool pie = new Reporttool("Visteon's Charts");
            pie.setSize(1200, 600);
            pie.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pie.setVisible(true);
        }
    }

