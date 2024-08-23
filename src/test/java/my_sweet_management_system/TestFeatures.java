package my_sweet_management_system;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(features="use_cases",
plugin = { "summary", "html:target/cucumber/report.html" }, 
monochrome = true, 
snippets = SnippetType.CAMELCASE, 
glue="my_sweet_management_system")
public class TestFeatures {

}