package gr.codelearn.eshop.base.suites;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("gr.codelearn.eshop")
@IncludeTags("production")
//@IncludeClassNamePatterns(".+[.$]Customer.*")
public class ProductionSuite {
}
