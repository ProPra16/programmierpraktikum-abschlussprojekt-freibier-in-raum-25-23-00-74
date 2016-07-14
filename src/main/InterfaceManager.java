import java.util.HashMap;

import javafx.scene.control.TextArea;

public class InterfaceManager 
{
	TextArea Code;
	TextArea TestCode;
	TextArea Console;
	
	public InterfaceManager(TextArea Code, TextArea TestCode, TextArea Console)
	{
		this.Code = Code;
		this.TestCode = TestCode;
		this.Console = Console;
	}
	
	// Zugriff auf das Coding-Area
	public String getCode()
	{
		return Code.getText();
	}
	
	public void setCode(String code) { Code.setText(code); }
	
	// Zugriff auf das Test-Coding-Area
	public String getTestCode()
	{
		return TestCode.getText();
	}
	
	public void setTestCode(String code) { TestCode.setText(code); }

	// Zugriff auf die Console
	public void writeToConsole(String message)
	{
		Console.appendText(message);
	}
}
