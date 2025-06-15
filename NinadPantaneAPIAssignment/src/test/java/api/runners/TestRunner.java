package api.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/featureFiles",
		glue = {"api.stepDefinitions"},
		monochrome = true,
		dryRun = false,
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
		publish=true)
public class TestRunner {

}
