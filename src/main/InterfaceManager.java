import java.util.HashMap;

import javafx.scene.control.TextArea;

public class InterfaceManager 
{
	HashMap<String, TextArea> Code;
	HashMap<String, TextArea> TestCode;
	TextArea Console;
	
	public InterfaceManager(HashMap<String, TextArea> Code, HashMap<String, TextArea> TestCode, TextArea Console)
	{
		this.Code = Code;
		this.TestCode = TestCode;
		this.Console = Console;
	}
	
	// Zugriff auf das Coding-Area
	public String getCode(String dataName)
	{
		return Code.get(dataName).getText();
	}
	
	public void setCode(String dataName, String code)
	{
		Code.get(dataName).setText(code);
	}
	
	// Zugriff auf das Test-Coding-Area
	public String getTestCode(String dataName)
	{
		return TestCode.get(dataName).getText();
	}
	
	public void setTestCode(String dataName, String code)
	{
		TestCode.get(dataName).setText(code);
	}
	
	// Zugriff auf die Console
	public void writeToConsole(String message)
	{
		Console.appendText(message);
	}
	
}
