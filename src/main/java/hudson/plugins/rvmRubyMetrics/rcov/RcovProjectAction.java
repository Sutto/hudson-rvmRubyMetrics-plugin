package hudson.plugins.rvmRubyMetrics.rcov;

import hudson.model.AbstractProject;
import hudson.plugins.rvmRubyMetrics.AbstractRubyMetricsProjectAction;

public class RcovProjectAction<RcovBuildAction> extends AbstractRubyMetricsProjectAction {

  public RcovProjectAction(AbstractProject <? , ? > project) {
    super(project);
  }

  public String getDisplayName() {
    return "Rcov report";
  }

  public String getUrlName() {
    return "rcov";
  }

  @Override
  protected Class getBuildActionClass() {
    return hudson.plugins.rvmRubyMetrics.rcov.RcovBuildAction.class;
  }

}
