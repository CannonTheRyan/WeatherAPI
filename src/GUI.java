import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BoxLayout;

public class GUI implements ActionListener, ItemListener{

	private JTextField zipCodeField;
	private JCheckBox celsiusCheckBox;
	private JLabel pictureLabel;
	private JPanel picturePanel;
	private JLabel temperatureLabel;
	private JFrame frame;
	private Networking client;
	private Weather weather;

	public GUI()
	{
		client = new Networking();
	}

	public void setUpGUI(){
		JPanel textFieldPanel = new JPanel();
		JLabel zipCode = new JLabel("Enter Zip Code: ");
		zipCodeField = new JTextField(5);
		JButton submitButton = new JButton("Submit");
		JButton clearButton = new JButton("Clear");
		celsiusCheckBox = new JCheckBox("Show Celsius");
		textFieldPanel.add(zipCode);
		textFieldPanel.add(zipCodeField);
		textFieldPanel.add(submitButton);
		textFieldPanel.add(clearButton);
		textFieldPanel.add(celsiusCheckBox);

		frame = new JFrame("Weather App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel titlePanel = new JPanel();
		JLabel titleText = new JLabel("Current Weather");
		titleText.setFont(new Font("Helvetica", Font.PLAIN, 30));
		titleText.setForeground(Color.pink);
		titlePanel.add(titleText);
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.add(textFieldPanel);


		frame.add(titlePanel, BorderLayout.NORTH);
		ImageIcon image = new ImageIcon("src//placeholder.jpg");
		Image imageData = image.getImage();
		Image scaledImage = imageData.getScaledInstance(100, 100,       java.awt.Image.SCALE_SMOOTH);
		image = new ImageIcon(scaledImage);
		picturePanel = new JPanel();
		pictureLabel = new JLabel(image);
		temperatureLabel = new JLabel();
		picturePanel.add(pictureLabel);
		frame.add(picturePanel, BorderLayout.CENTER);

		submitButton.addActionListener(this);
		clearButton.addActionListener(this);
		celsiusCheckBox.addItemListener(this);

		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		JButton button = (JButton) e.getSource();
		String text = button.getText();

		if (text.equals("Submit")){
			int zipCode = Integer.parseInt(zipCodeField.getText());
			weather = client.getWeather(zipCode);
			pictureLabel.setVisible(false);
			picturePanel = new JPanel();
			frame.add(picturePanel, BorderLayout.CENTER);
			try {
				URL imageURL = new URL("https:" + weather.getImageURL());
				BufferedImage image = ImageIO.read(imageURL);
				JLabel imageLabel = new JLabel(new ImageIcon(image));
				temperatureLabel.setText("Temperature: " + weather.getTempF() + "F Condition: " + weather.getCondition());
				picturePanel.add(temperatureLabel, BorderLayout.NORTH);
				picturePanel.add(imageLabel);
			} catch (Exception ee) {
				System.out.println(ee.getMessage());
			}
		}
		else if(text.equals("Clear")) {
			zipCodeField.setText("");
		}

	}

	public void itemStateChanged(ItemEvent e){
		if (e.getStateChange() == ItemEvent.SELECTED){
			temperatureLabel.setText(("Temperature: " + weather.getTempC() + "C      Condition: " + weather.getCondition()));
		} else {
			temperatureLabel.setText("Temperature: " + weather.getTempF() + "F      Condition: " + weather.getCondition());
		}
	}
}