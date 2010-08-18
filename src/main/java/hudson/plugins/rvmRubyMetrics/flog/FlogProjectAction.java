package hudson.plugins.rvmRubyMetrics.flog;

import hudson.model.AbstractProject;
import hudson.plugins.rvmRubyMetrics.AbstractRubyMetricsProjectAction;

public class FlogProjectAction<FlogBuildAction> extends AbstractRubyMetricsProjectAction {

  public FlogProjectAction(AbstractProject <? , ? > project) {
    super(project);
  }

  @Override
  protected Class getBuildActionClass() {
    return hudson.plugins.rvmRubyMetrics.flog.FlogBuildAction.class;
  }

  public String getDisplayName() {
    return "Flog report";
  }

  public String getUrlName() {
    return "flog";
  }

}
