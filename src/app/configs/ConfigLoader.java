package app.configs;

import annotations.InitializeClass;
import annotations.InitializeMethod;

@InitializeClass
public class ConfigLoader {
	@InitializeMethod
	public void loadAllConfigs() {
		System.out.println("All configs loaded successfully");
	}
}
